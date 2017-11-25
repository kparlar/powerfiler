package com.ph.powerfiler.exception;

public class ExceptionMessageCodeConstants {

    public static final String REST_CLIENT_EXCEPTION_MESSAGE = "org.springframework.web.client.RestClientException: Code: {}: id {}";
    public static final String REST_CLIENT_EXCEPTION_CODE = "Ex01";

    public static final String NOT_VALID_MONTHS_EXCEPTION_MESSAGE = "Months are not valid Calendar Month";
    public static final String NOT_VALID_MONTHS_EXCEPTION_CODE = "Ex02";

    public static final String NOT_VALID_ENTITY_EXCEPTION_MESSAGE = "Entity not found %s %s";
    public static final String NOT_VALID_ENTITY_EXCEPTION_CODE = "Ex03";

    public static final String NOT_VALID_MONTH_EXCEPTION_MESSAGE = "Month is not valid. Expected : JAN, FEB , ... , DEC";
    public static final String NOT_VALID_MONTH_EXCEPTION_CODE = "Ex04";

    public static final String DELETE_FIRST_DATA_EXCEPTION_MESSAGE = "%s Data Found. Please delete it first. %s Id %s";
    public static final String DELETE_FIRST_DATA_EXCEPTION_CODE = "Ex05";

    public static final String NEXT_PREVIOUS_MONTH_NOT_FOUND_EXCEPTION_MESSAGE = "Meter previous or next months are not found";
    public static final String NEXT_PREVIOUS_MONTH_NOT_FOUND_EXCEPTION_CODE = "Ex06";

    public static final String METER_HAS_TO_BE_BIGGER_OR_EQ_TO_PREVIOUS_READING_EXCEPTION_MESSAGE = "Meter has to be bigger or equal to previous reading Entity: %s; Profile: %s; ConnectionId: %s; Month: %s;";
    public static final String METER_HAS_TO_BE_BIGGER_OR_EQ_TO_PREVIOUS_READING_EXCEPTION_CODE = "Ex07";

    public static final String METER_HAS_TO_BE_SMALLER_OR_EQ_TO_NEXT_READING_EXCEPTION_MESSAGE = "Meter has to be smaller  or equal to next reading Entity: %s; Profile: %s; ConnectionId: %s; Month: %s;";
    public static final String METER_HAS_TO_BE_SMALLER_OR_EQ_TO_NEXT_READING_EXCEPTION_CODE = "Ex08";

    public static final String DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_MESSAGE = "Entity could not be deleted %s %s";
    public static final String DELETE_IS_NOT_SUCCESSFUL_EXCEPTION_CODE = "Ex09";

    public static final String TOTAL_FRACTION_NOT_ONE_EXCEPTION_MESSAGE = "Profile %s; Connection: %s; Total fraction is not 1.";
    public static final String TOTAL_FRACTION_NOT_ONE_EXCEPTION_CODE = "Ex10";

    public static final String CSV_FILE_DATA_FORMAT_ERROR = "Csv file data format is incompatible!";
    public static final String CSV_FILE_DATA_FORMAT_ERROR_CODE ="Ex11";

    public static final String FILE_COULD_NOT_READ_MESSAGE = "Error occurred while reading file content";
    public static final String FILE_COULD_NOT_READ_MESSAGE_ERROR_CODE ="Ex012";

    public static final String CONSUMPTION_TOLERANCE_ERROR_MESSAGE = "Profile: %s; Connection:%s; Month: %s, Consumption limit is not in tolerance range.";
    public static final String CONSUMPTION_TOLERANCE_ERROR_CODE = "Ex013";

    public static final String DATA_SENT_EMPTY_ERROR_MESSAGE = "%s data is sent empty or null";
    public static final String DATA_SENT_EMPTY_ERROR_CODE = "Ex014";



}
