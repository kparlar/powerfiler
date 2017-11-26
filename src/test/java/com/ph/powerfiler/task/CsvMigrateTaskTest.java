package com.ph.powerfiler.task;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.ph.powerfiler.exception.ExceptionMessage;
import com.ph.powerfiler.exception.PowerfilerException;
import com.ph.powerfiler.model.dto.ConnectionDto;
import com.ph.powerfiler.model.dto.ConnectionsDto;
import com.ph.powerfiler.operation.ConnectionOperation;
import com.ph.powerfiler.util.FileUtil;
import com.ph.powerfiler.util.PowerfilerTestConstants;
import com.ph.powerfiler.util.PowerfilerUtil;
import com.ph.powerfiler.util.provider.object.ExceptionMessageProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CsvMigrateTaskTest {

    @InjectMocks
    private CsvMigrateTask csvMigrateTask;

    @Mock
    private PowerfilerUtil powerfilerUtil;

    @Mock
    private ConnectionOperation connectionOperation;

    private FileUtil fileUtil;
    private File fileDirectory;
    private File keptFile;
    private final Appender mockAppender = mock(Appender.class);
    private ExceptionMessageProvider exceptionMessageProvider;


    @Before
    public void setup() throws IOException, PowerfilerException {
        MockitoAnnotations.initMocks(this);
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        when(mockAppender.getName()).thenReturn("MOCK");
        root.addAppender(mockAppender);
        exceptionMessageProvider = new ExceptionMessageProvider();

    }

    private void copyTestFile(String fileName) throws PowerfilerException, IOException {
        fileUtil = new FileUtil();
        fileDirectory = new File(PowerfilerTestConstants.TEMP_DIRECTORY);
        fileUtil.deleteFileTree(fileDirectory);
        if(!fileDirectory.exists())
            fileDirectory.mkdir();
        keptFile = new File(fileDirectory, fileName);
        fileUtil.copyTestDataToTempFolder(fileName);
    }
    @Test
    public void fileCheckScheduleGivenRootFolderNullWhenProcessingCsvThenLogError() throws PowerfilerException, IOException {
        copyTestFile(PowerfilerTestConstants.TEST_FILE);
        Mockito.when(powerfilerUtil.keptFile(Matchers.any(File[].class))).thenReturn(keptFile);
        Mockito.when(connectionOperation.saveCsvConnections(Matchers.any(ConnectionsDto.class))).thenReturn(new ExceptionMessage());
        csvMigrateTask.setRootFolder(null);
        csvMigrateTask.fileCheckSchedule();
        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("Folder path is empty or null.");
            }
        }));
    }

    @Test
    public void fileCheckScheduleGivenValidDataWhenProcessingCsvThenReturn() throws PowerfilerException, IOException {
        copyTestFile(PowerfilerTestConstants.TEST_FILE);
        Mockito.when(powerfilerUtil.keptFile(Matchers.any(File[].class))).thenReturn(null);
        csvMigrateTask.setRootFolder(PowerfilerTestConstants.TEMP_DIRECTORY);
        csvMigrateTask.fileCheckSchedule();
        Mockito.verify(connectionOperation, Mockito.never()).saveCsvConnections(Matchers.any(ConnectionsDto.class));
    }

    @Test
    public void fileCheckScheduleGivenValidDataWhenProcessingCsvThenSuccess() throws PowerfilerException, IOException {
        copyTestFile(PowerfilerTestConstants.TEST_FILE);
        Mockito.when(powerfilerUtil.keptFile(Matchers.any(File[].class))).thenReturn(keptFile);
        Mockito.when(connectionOperation.saveCsvConnections(Matchers.any(ConnectionsDto.class))).thenReturn(new ExceptionMessage());
        csvMigrateTask.setRootFolder(PowerfilerTestConstants.TEMP_DIRECTORY);
        csvMigrateTask.fileCheckSchedule();
        Mockito.verify(connectionOperation, Mockito.atMost(1)).saveCsvConnections(Matchers.any(ConnectionsDto.class));
    }
    @Test
    public void fileCheckScheduleGivenValidDataWhenProcessingCsvThenExceptionMessage() throws PowerfilerException, IOException {
        ExceptionMessage exceptionMessage = exceptionMessageProvider.createExceptionMessage();
        copyTestFile(PowerfilerTestConstants.TEST_FILE);
        Mockito.when(powerfilerUtil.keptFile(Matchers.any(File[].class))).thenReturn(keptFile);
        Mockito.when(connectionOperation.saveCsvConnections(Matchers.any(ConnectionsDto.class))).thenReturn(exceptionMessage);
        csvMigrateTask.setRootFolder(PowerfilerTestConstants.TEMP_DIRECTORY);
        csvMigrateTask.fileCheckSchedule();
        Mockito.verify(connectionOperation, Mockito.atMost(1)).saveCsvConnections(Matchers.any(ConnectionsDto.class));
        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains(exceptionMessage.getErrors().toString());
            }
        }));
    }
    @Test
    public void fileCheckScheduleGivenValidDataWhenProcessingCsvThenError() throws PowerfilerException, IOException {
        copyTestFile(PowerfilerTestConstants.TEST_FILE_NOT_VALID);
        Mockito.when(powerfilerUtil.keptFile(Matchers.any(File[].class))).thenReturn(keptFile);
        Mockito.when(connectionOperation.saveCsvConnections(Matchers.any(ConnectionsDto.class))).thenReturn(new ExceptionMessage());
        csvMigrateTask.setRootFolder(PowerfilerTestConstants.TEMP_DIRECTORY);
        csvMigrateTask.fileCheckSchedule();
        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent) argument).getFormattedMessage().contains("Error occured");
            }
        }));
    }


}
