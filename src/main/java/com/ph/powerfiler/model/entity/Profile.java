package com.ph.powerfiler.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PF_PROFILE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})}, indexes = {@Index(
        name = "PF_PROFILE_TNR_IDX", columnList = "NAME", unique = true)})
@DiscriminatorValue(value = "PF_PROFILE")
public class Profile extends PowerfilerObject{
    @Column(name = "NAME", length = 4000)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "fromProfile")
    private List<HasConnection> hasConnections;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PF_HAS_CONNECTION", joinColumns = {@JoinColumn(name = "FROM_ID",
            referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "TO_ID",
            referencedColumnName = "ID")})
    private List<Connection> connections;

    public List<HasConnection> getHasConnections() {
        return hasConnections;
    }

    public void setHasConnections(List<HasConnection> hasConnections) {
        this.hasConnections = hasConnections;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
