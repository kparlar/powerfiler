package com.ph.powerfiler.model.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "PF_HAS_CONNECTION", indexes = {@Index(name = "PF_HAS_CONNECTION_FI", columnList = "FROM_ID"),
        @Index(name = "PF_HAS_CONNECTION_TI", columnList = "TO_ID")})
@DiscriminatorValue(value = "PF_HAS_CONNECTION")
public class HasConnection extends PowerfilerObject {

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "TO_ID", insertable = true, updatable = true, referencedColumnName = "ID")
    private Connection toConnection;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "FROM_ID", insertable = true, updatable = true, referencedColumnName = "ID")
    private Profile fromProfile;

    public Connection getToConnection() {
        return toConnection;
    }

    public void setToConnection(Connection toConnection) {
        this.toConnection = toConnection;
    }

    public Profile getFromProfile() {
        return fromProfile;
    }

    public void setFromProfile(Profile fromProfile) {
        this.fromProfile = fromProfile;
    }
}
