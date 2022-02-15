package com.sjarno.norascoffeeshop.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class UserRole extends AbstractPersistable<Long> {

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
