package com.ph.powerfiler.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PowerfilerItem extends PowerfilerObject{

    @Column(name = "MONTH")
    private String month;



    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}
