package com.sjarno.norascoffeeshop.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class UserAccount extends AbstractPersistable<Long> {

    @Length(min = 4, max = 40, message = "Username length must be between 4 and 40 characters")
    @NotBlank
    @Column(name = "username")
    private String username;

    @Length(min = 4, max = 100)
    @NotBlank
    @Column(name = "password")
    private String password;

    // enum typed roles as string
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> roles = new ArrayList<>();


}
