package com.ph.powerfiler.util;

import com.ph.powerfiler.exception.PowerfilerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public void copyTestDataToTempFolder(String fileName) throws IOException {
        File testFileDirectory = new File(PowerfilerTestConstants.TEST_FILE_DIRECTORY);
        File file = new File(testFileDirectory, fileName);
        File fileDirectory = new File(PowerfilerTestConstants.TEMP_DIRECTORY);
        File newFile = new File(fileDirectory, fileName);
        Files.copy(file.toPath(), newFile.toPath());

    }
    public List<File> createFiles() throws PowerfilerException {
        File file = null;
        try {
            file = createFile();
        } catch (IOException e) {
            throw new PowerfilerException(String.format(MessageCodeConstants.FILE_NOT_CREATED_ERROR_MESSAGE, file.getName()),MessageCodeConstants.FILE_NOT_CREATED_ERROR_CODE, true);
        }
        List<File> files = new ArrayList<>();
        files.add(file);
        return files;
    }
    public File createFile() throws IOException {
        File fileDirectory = new File(PowerfilerTestConstants.TEMP_DIRECTORY);
        if(!fileDirectory.exists())
            fileDirectory.mkdir();
        File file = new File(fileDirectory, PowerfilerTestConstants.FILE_CSV);
        file.createNewFile();
        return file;
    }
    public void deleteFileTree(File file) throws PowerfilerException {
        if(!file.exists()){
            return;
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFileTree(f);
            }
        }
        boolean isDeleted = file.delete();
        if(!isDeleted)
            throw new PowerfilerException(String.format(MessageCodeConstants.FILE_NOT_DELETED_ERROR_MESSAGE, file.getName()),MessageCodeConstants.FILE_NOT_DELETED_ERROR_CODE, true);
    }
}
