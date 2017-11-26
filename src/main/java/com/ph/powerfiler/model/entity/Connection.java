package com.ph.powerfiler.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PF_CONNECTION",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"CONNECTION_ID"})}, indexes = {@Index(
        name = "PF_CONNECTION_TNR_IDX", columnList = "CONNECTION_ID", unique = true)})
@DiscriminatorValue(value = "PF_CONNECTION")
public class Connection extends PowerfilerObject{

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fromConnection")
    private List<HasMeter> hasMeters;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fromConnection")
    private List<HasFraction> hasFractions;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PF_HAS_METER", joinColumns = {@JoinColumn(name = "FROM_ID",
            referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "TO_ID",
            referencedColumnName = "ID")})
    private List<Meter> meters;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PF_HAS_FRACTION", joinColumns = {@JoinColumn(name = "FROM_ID",
            referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "TO_ID",
            referencedColumnName = "ID")})
    private List<Fraction> fractions;

    @Column(name = "CONNECTION_ID", length = 128)
    private String connectionId;

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }




    public List<HasMeter> getHasMeters() {
        return hasMeters;
    }

    public void setHasMeters(List<HasMeter> hasMeters) {
        this.hasMeters = hasMeters;
    }

    public List<HasFraction> getHasFractions() {
        return hasFractions;
    }

    public void setHasFractions(List<HasFraction> hasFractions) {
        this.hasFractions = hasFractions;
    }

    public List<Meter> getMeters() {
        return meters;
    }

    public void setMeters(List<Meter> meters) {
        this.meters = meters;
    }

    public List<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(List<Fraction> fractions) {
        this.fractions = fractions;
    }
}
