package com.ph.powerfiler.model.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PF_FRACTION")
@DiscriminatorValue(value = "PF_FRACTION")
public class Fraction extends PowerfilerItem {
    @Column(name = "FRACTION")
    private Double fraction;

    public Double getFraction() {
        return fraction;
    }

    public void setFraction(Double fraction) {
        this.fraction = fraction;
    }
}
