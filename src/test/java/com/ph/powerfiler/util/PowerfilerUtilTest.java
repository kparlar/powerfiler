package com.ph.powerfiler.util;

import com.ph.powerfiler.exception.PowerfilerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.util.List;

public class PowerfilerUtilTest {

    @InjectMocks
    private PowerfilerUtil powerfilerUtil;

    private FileUtil fileUtil;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        fileUtil = new FileUtil();
    }


    @Test
    public void convertMonthToIntegerGivenNullMonthWhenConvertingToIntThenMinusOne(){
        int result = powerfilerUtil.convertMonthToInteger(null);
        Assert.assertTrue("-1 is expected", result == -1);
    }
    @Test
    public void convertMonthToIntegerGivenNotValidMonthWhenConvertingToIntThenMinusOne(){
        int result = powerfilerUtil.convertMonthToInteger(PowerfilerTestConstants.MONTH_NOT_VALID);
        Assert.assertTrue("-1 is expected", result == -1);
    }

    @Test
    public void convertMonthToIntegerGivenValidMonthWhenConvertingToIntThenResult(){
        int result = powerfilerUtil.convertMonthToInteger(PowerfilerTestConstants.MONTH_DEC);
        Assert.assertTrue("12 is expected", result == PowerfilerTestConstants.MONTH_DEC_DECIMAL);
    }

    @Test
    public void convertCalendarMonthToDBMonthGivenNullMonthWhenConvertingThenNull(){
        String result = powerfilerUtil.convertCalendarMonthToDBMonth(null);
        Assert.assertTrue("Null is expected", result == null);
    }

    @Test
    public void convertCalendarMonthToDBMonthGivenNotValidMonthWhenConvertingThenNull(){
        String result = powerfilerUtil.convertCalendarMonthToDBMonth(PowerfilerTestConstants.MONTH_NOT_VALID);
        Assert.assertTrue("Null is expected", result == null);
    }
    @Test
    public void convertCalendarMonthToDBMonthGivenValidMonthWhenConvertingThenResult(){
        String result = powerfilerUtil.convertCalendarMonthToDBMonth(PowerfilerTestConstants.MONTH_FEB_CALENDAR);
        Assert.assertTrue("FEB is expected", result.equalsIgnoreCase(PowerfilerTestConstants.MONTH_FEB));
    }

    @Test
    public void isValidMonthGivenNullMonthWhenValidatingThenFalse(){
        boolean result = powerfilerUtil.isValidMonth(null);
        Assert.assertTrue("False is expected", !result);
    }
    @Test
    public void isValidMonthGivenNotValidMonthWhenValidatingThenFalse(){
        boolean result = powerfilerUtil.isValidMonth(PowerfilerTestConstants.MONTH_NOT_VALID);
        Assert.assertTrue("False is expected", !result);
    }
    @Test
    public void isValidMonthGivenValidMonthWhenValidatingThenTrue(){
        boolean result = powerfilerUtil.isValidMonth(PowerfilerTestConstants.MONTH_FEB);
        Assert.assertTrue("True is expected", result);
    }

    @Test
    public void findPreviousMonthGivenNullMonthWhenFindingPreviousMonthThenNull(){
        String result = powerfilerUtil.findPreviousMonth(null);
        Assert.assertTrue("Null is expected", result == null);
    }
    @Test
    public void findPreviousMonthGivenNotValidMonthWhenFindingPreviousMonthThenNull(){
        String result = powerfilerUtil.findPreviousMonth(PowerfilerTestConstants.MONTH_NOT_VALID);
        Assert.assertTrue("Null is expected", result == null);
    }
    @Test
    public void findPreviousMonthGivenMonthJANWhenFindingPreviousMonthThenNull(){
        String result = powerfilerUtil.findPreviousMonth(PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("Null is expected", result == null);
    }
    @Test
    public void findPreviousMonthGivenValidMonthWhenFindingPreviousMonthThenNull(){
        String result = powerfilerUtil.findPreviousMonth(PowerfilerTestConstants.MONTH_DEC);
        Assert.assertTrue("NOV is expected", result.equalsIgnoreCase(PowerfilerTestConstants.MONTH_NOV));
    }

    @Test
    public void findNextMonthGivenNullMonthWhenFindingNextMonthThenNull(){
        String result = powerfilerUtil.findNextMonth(null);
        Assert.assertTrue("Null is expected", result == null);
    }
    @Test
    public void findNextMonthGivenNotValidMonthWhenFindingNextMonthThenNull(){
        String result = powerfilerUtil.findNextMonth(PowerfilerTestConstants.MONTH_NOT_VALID);
        Assert.assertTrue("Null is expected", result == null);
    }
    @Test
    public void findNextMonthGivenValidMonthWhenFindingNextMonthThenResult(){
        String result = powerfilerUtil.findNextMonth(PowerfilerTestConstants.MONTH_JAN);
        Assert.assertTrue("FEB is expected", result.equalsIgnoreCase(PowerfilerTestConstants.MONTH_FEB));
    }
    @Test
    public void findNextMonthGivenMonthDECWhenFindingNextMonthThenNull(){
        String result = powerfilerUtil.findNextMonth(PowerfilerTestConstants.MONTH_DEC);
        Assert.assertTrue("Null is expected", result == null);
    }
    @Test
    public void keptFileGivenNullFileListWhenKeptingFilesThenNull(){
        File result = powerfilerUtil.keptFile(null);
        Assert.assertTrue("Null is expected", result == null);
    }
    @Test
    public void keptFileGivenEmptyFileListWhenKeptingFilesThenNull(){
        File result = powerfilerUtil.keptFile(new File[0]);
        Assert.assertTrue("Null is expected", result == null);
    }
    @Test
    public void keptFileGivenValidFileListWhenKeptingFilesThenResult() throws PowerfilerException {
        List<File> files = fileUtil.createFiles();
        File result = powerfilerUtil.keptFile(files.toArray(new File[0]));
        Assert.assertTrue("Not Null is expected", result != null);
        fileUtil.deleteFileTree(result);
    }

}
