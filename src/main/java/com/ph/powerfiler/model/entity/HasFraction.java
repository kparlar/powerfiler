package com.ph.powerfiler.model.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "PF_HAS_FRACTION", indexes = {@Index(name = "PF_HAS_FRACTION_FI", columnList = "FROM_ID"),
        @Index(name = "PF_HAS_FRACTION_TI", columnList = "TO_ID")})
@DiscriminatorValue(value = "PF_HAS_FRACTION")
public class HasFraction extends PowerfilerObject {

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "TO_ID", insertable = true, updatable = true, referencedColumnName = "ID")
    private Fraction toFraction;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "FROM_ID", insertable = true, updatable = true, referencedColumnName = "ID")
    private Connection fromConnection;

    public Fraction getToFraction() {
        return toFraction;
    }

    public void setToFraction(Fraction toFraction) {
        this.toFraction = toFraction;
    }

    public Connection getFromConnection() {
        return fromConnection;
    }

    public void setFromConnection(Connection fromConnection) {
        this.fromConnection = fromConnection;
    }
}
