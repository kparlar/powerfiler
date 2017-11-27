package com.ph.powerfiler.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.text.DateFormatSymbols;
import java.util.UUID;

@Component
public class PowerfilerUtil {


    public int convertMonthToInteger(String month){
        if(month == null)
            return -1;

        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        int i=1;
        for(String tempMonth: months){
            if(!PowerfilerConstants.EMPTY_STRING.equalsIgnoreCase(tempMonth.trim())&&tempMonth.substring(0, 3).equalsIgnoreCase(month)){
                return i;
            }
            i++;
        }
        i = -1;
        return i;
    }
    public String convertCalendarMonthToDBMonth(String month){
        if(month==null)
            return null;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        for(String tempMonth: months){
            if(!PowerfilerConstants.EMPTY_STRING.equalsIgnoreCase(tempMonth.trim())&&tempMonth.equalsIgnoreCase(month)){
                return tempMonth.substring(0, 3).toUpperCase();
            }
        }
        return null;
    }
    public boolean isValidMonth(String month){
        int monthValue = convertMonthToInteger(month);
        if(monthValue < 0)
            return false;
        return true;
    }

    public String findPreviousMonth(String month){
        if(month == null)
            return null;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        int i=0;
        int monthFound = -1;
        for(String tempMonth: months){
            if(!tempMonth.trim().equalsIgnoreCase(tempMonth.trim())&&tempMonth.substring(0, 3).equalsIgnoreCase(month)){
                monthFound=i;
                break;
            }
            i++;
        }
        if(monthFound!=-1 && monthFound !=0){
            return months[i-1].substring(0,3).toUpperCase();
        }
        return null;
    }
    public String findNextMonth(String month){
        if(month == null)
            return null;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        int i=0;
        int monthFound = -1;
        for(String tempMonth: months){
            if(!tempMonth.trim().equalsIgnoreCase(tempMonth.trim())&&tempMonth.substring(0, 3).equalsIgnoreCase(month)){
                monthFound=i;
                break;
            }
            i++;
        }
        if(monthFound!=-1 && monthFound !=11){
            return months[i+1].substring(0,3).toUpperCase();
        }
        return null;
    }

    public File keptFile(File[] listOfFolders){
        if(listOfFolders == null)
            return null;
        File newFile;
        boolean isRenamed;
        for (int i = 0; i < listOfFolders.length; i++) {
            File unKeptFolder = listOfFolders[i];
            String fileName = unKeptFolder.getName();
            newFile = new File(String.format("%s/%s_%s_%s.%s",unKeptFolder.getParent(),fileName.split("\\.")[0], UUID.randomUUID(),PowerfilerConstants.KEPT_EXTENSION, fileName.split("\\.")[1]));
            isRenamed = unKeptFolder.renameTo(newFile);
            if(isRenamed){
                return newFile;
            }
        }
        return null;
    }
}
