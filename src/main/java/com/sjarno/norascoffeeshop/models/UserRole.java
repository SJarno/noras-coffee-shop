package com.sjarno.norascoffeeshop.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(columnNames = "role_type"))
public class UserRole extends AbstractPersistable<Long> {

    
    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<UserAccount> users;
}
