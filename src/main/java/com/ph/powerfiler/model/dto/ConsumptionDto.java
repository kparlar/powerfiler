package com.ph.powerfiler.model.dto;

public class ConsumptionDto {

    String connectionId;
    String startMonth;
    String endMonth;
    String result;
    public ConsumptionDto(String connectionId, String startMonth, String endMonth, String result){
        this.connectionId = connectionId;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.result = result;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
