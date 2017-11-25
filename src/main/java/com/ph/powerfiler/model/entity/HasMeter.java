package com.ph.powerfiler.model.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "PF_HAS_METER", indexes = {@Index(name = "PF_HAS_METER_FI", columnList = "FROM_ID"),
        @Index(name = "PF_HAS_METER_TI", columnList = "TO_ID")})
@DiscriminatorValue(value = "PF_HAS_METER")
public class HasMeter extends PowerfilerObject {
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "TO_ID", insertable = true, updatable = true, referencedColumnName = "ID")
    private Meter toMeter;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "FROM_ID", insertable = true, updatable = true, referencedColumnName = "ID")
    private Connection fromConnection;


    public Meter getToMeter() {
        return toMeter;
    }

    public void setToMeter(Meter toMeter) {
        this.toMeter = toMeter;
    }

    public Connection getFromConnection() {
        return fromConnection;
    }

    public void setFromConnection(Connection fromConnection) {
        this.fromConnection = fromConnection;
    }
}
