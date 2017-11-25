package com.ph.powerfiler.task;

import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.util.MessageCodeConstants;
import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.ConnectionsDto;
import com.ph.powerfiler.model.dto.FractionDto;
import com.ph.powerfiler.model.dto.MeterDto;
import com.ph.powerfiler.operation.ConnectionOperation;
import com.ph.powerfiler.util.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvMigrateTask {
    private static final Logger log = LoggerFactory
            .getLogger(CsvMigrateTask.class);

    @Value("${powerfiler.csv-migrate-task.root-folder}")
    private String rootFolder;

    @Autowired
    private PowerfilerUtil powerfilerUtil;

    @Autowired
    private ConnectionOperation connectionOperation;




    /**
     * This task works at every pre-defined seconds. property variable used
     * directly because if we get it from cnfServiceProperties it gives static
     * final error
     */
    @Scheduled(fixedDelayString = "${powerfiler.csv-migrate-task.schedule.read-rate}", initialDelay = 1000)
    public void fileCheckSchedule() {
        processCsvFiles();
    }
    private void processCsvFiles(){

        List<String> fileList = new ArrayList<>();

        File[] listOfFolders = getUnkeptFolderList();


        File keptFile =  powerfilerUtil.keptFile(listOfFolders);
        if(keptFile==null){
            return;
        }



        try {
             ConnectionsDto connectionsDto =  getAllData(keptFile);
            ExceptionMessage exceptionMessage = connectionOperation.saveCsvConnections(connectionsDto);
            if(exceptionMessage.getErrors().size()>0){
                log.error(exceptionMessage.getErrors().toString());
            }else{
                keptFile.delete();
            }
        } catch (PowerfilerException e) {
            log.error("Error occured",e);
        }



    }
    private File[] getUnkeptFolderList() {
        if (rootFolder == null || rootFolder.isEmpty()) {
            log.error("Folder path is empty or null.");
            return new File[]{};
        }

        File folder = new File(rootFolder);
        File[] listOfFolders = folder.listFiles(new KeptCsvFileFilter());
        if (listOfFolders == null) {
            log.error("Temp folder is empty");
            return new File[]{};
        }
        return listOfFolders;
    }

    public ConnectionsDto getAllData(
            File file) throws PowerfilerException {
        List<ConnectionsDto> connectionsDtos = new ArrayList<>();
        try {
            return readFromCsv(file, true);
        } catch (IOException e) {
            log.error(MessageCodeConstants.FILE_COULD_NOT_READ_MESSAGE
                    + e);
            throw new PowerfilerException(
                    MessageCodeConstants.FILE_COULD_NOT_READ_MESSAGE,
                    MessageCodeConstants.FILE_COULD_NOT_READ_MESSAGE_ERROR_CODE,
                    true);

        }
    }


    public ConnectionsDto readFromCsv(File file, boolean isMeter) throws IOException,
            PowerfilerException {
         Reader in = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        List<List<String>> datas = null;
        ConnectionsDto connectionsDto = processCsvMeterContent(records);
        in.close();
        return connectionsDto;
    }


    private  ConnectionsDto processCsvMeterContent(Iterable<CSVRecord> records)
            throws PowerfilerException {

        List<MeterDto> meterDtos = new ArrayList<>();
        List<FractionDto> fractionDtos = new ArrayList<>();
        ConnectionsDto connectionsDto = new ConnectionsDto(meterDtos, fractionDtos);
        boolean parseMeter = false;
        boolean parseFraction = false;
        for (CSVRecord record : records) {
            if(record.get(0).equalsIgnoreCase("CONNECTION_ID")){
                    parseMeter = true;
                    parseFraction = false;
                    continue;
            }
            if(record.get(0).equalsIgnoreCase("MONTH")){
                parseMeter = false;
                parseFraction = true;
                continue;
            }

            if(parseMeter)
                meterDtos.add(processCsvMeterRows(record));
            if(parseFraction)
                fractionDtos.add(processCsvFractionRows(record));
        }
        return connectionsDto;
    }

    private List<FractionDto> processCsvFractionContent(Iterable<CSVRecord> records)
            throws PowerfilerException {
        List<FractionDto> fractionDatas = new ArrayList<>();
        for (CSVRecord record : records) {
            fractionDatas.add(processCsvFractionRows(record));

        }
        return fractionDatas;
    }


    private MeterDto processCsvMeterRows(CSVRecord record) throws PowerfilerException {
        return new MeterDto(
                record.get(CsvMeterData.CONNECTION_ID.cellOrder()),
                record.get(CsvMeterData.PROFILE.cellOrder()),
                record.get(CsvMeterData.MONTH.cellOrder()),
                record.get(CsvMeterData.METER_READING.cellOrder())
                );

    }
    private FractionDto processCsvFractionRows(CSVRecord record) throws PowerfilerException {

        return new FractionDto(
                record.get(CsvFractionData.PROFILE.cellOrder()),
                record.get(CsvFractionData.MONTH.cellOrder()),
                record.get(CsvFractionData.FRACTION.cellOrder())
                );
    }




}
