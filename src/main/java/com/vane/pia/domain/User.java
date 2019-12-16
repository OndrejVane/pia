package com.vane.pia.domain;

import com.vane.pia.utils.generator.Generator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends EntityParent {

    @Column(unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String personalIdentificationNumber;

    private String degree;

    private String street;

    private String city;

    private String houseNumber;

    @Pattern(regexp = "[0-9]{5}")
    private String zipCode;

    @NotNull
    @Email
    private String email;

    @Pattern(regexp = "(\\+420)[0-9]{9}")
    private String telephoneNumber;

    @NotNull
    @Pattern(regexp = "[0-9]{16}")
    private String cardNumber;

    @NotNull
    @Pattern(regexp = "[0-9]{16}")
    private String accountNumber;

    @NotNull
    @Pattern(regexp = "[0-9]{4}")
    private String bankNumber;

    @NotNull
    private boolean isDeleted;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @OneToMany(mappedBy = "user")
    private List<Bill> bills = new LinkedList<>();

    @OneToMany(mappedBy = "user")
    private List<TempItem> tempItems = new LinkedList<>();

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new LinkedList<>();

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companies = new LinkedList<>();

    public User(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
        this.isDeleted = false;
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String firstName, @NotNull String lastName,
                @NotNull String personalIdentificationNumber,
                String degree, String street, String city,
                String houseNumber, String zipCode, @NotNull @Email String email,
                @Pattern(regexp = "(\\+420)[0-9]{9}") String telephoneNumber,
                @NotNull @Pattern(regexp = "[0-9]{16}") String cardNumber,
                @NotNull @Pattern(regexp = "[0-9]{16}") String accountNumber,
                @NotNull @Pattern(regexp = "[0-9]{4}") String bankNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalIdentificationNumber = personalIdentificationNumber;
        this.degree = degree;
        this.street = street;
        this.city = city;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.bankNumber = bankNumber;
        this.isDeleted = false;
    }

    public User() {
        this.setPassword(Generator.generatePassword());
        this.setUsername(Generator.generateUsername());
        this.isDeleted = false;
    }

    public String getAddress() {
        return this.street + " " + this.houseNumber + "," +
                this.city + " " + this.zipCode;
    }

}
