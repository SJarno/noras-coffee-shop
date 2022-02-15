package com.sjarno.norascoffeeshop.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRole extends AbstractPersistable<Long> {

    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}
