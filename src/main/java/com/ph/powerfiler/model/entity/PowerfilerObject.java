package com.ph.powerfiler.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "PF_OBJECT")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE")
public class PowerfilerObject {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
    private String id;

    @JsonIgnore
    @Transient
    public String getDiscriminatorValue() {
        DiscriminatorValue val = this.getClass().getAnnotation(DiscriminatorValue.class);

        return val == null ? null : val.value();
    }

    public String getId() {
        return id;
    }
}
