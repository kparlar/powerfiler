package com.ph.powerfiler.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PF_METER")
@DiscriminatorValue(value = "PF_METER")
public class Meter extends PowerfilerItem{
    @Column(name = "READING")
    private Long reading;

    public Long getReading() {
        return reading;
    }

    public void setReading(Long reading) {
        this.reading = reading;
    }
}
