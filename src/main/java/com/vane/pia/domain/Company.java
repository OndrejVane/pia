package com.vane.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Company extends EntityParent {

    @Column(unique = true)
    @NotNull
    private String name;

    @NotNull
    private String street;

    @NotNull
    private String houseNumber;

    @NotNull
    private String city;

    @NotNull
    @Pattern(regexp = "[0-9]{5}")
    private String zipCode;

    // IČO
    @NotNull
    @Pattern(regexp = "[0-9]{8}")
    private String identificationNumber;

    // DIČ
    @Pattern(regexp = "(cz|CZ|Cz)[0-9]{8,10}")
    private String taxIdentificationNumber;

    private Integer numberOfBills;

    @NotNull
    @Pattern(regexp = "[0-9]{16}")
    private String accountNumber;

    @NotNull
    @Pattern(regexp = "[0-9]{4}")
    private String bankNumber;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;


    @OneToMany(mappedBy = "company")
    private List<Contact> contacts = new LinkedList<>();

    @OneToMany(mappedBy = "company")
    private List<Bill> bills = new LinkedList<>();

    @ManyToMany(mappedBy = "companies")
    private List<User> users;

    public Company(@NotNull String name) {
        this.name = name;
    }

    public Company() {
    }

    public Company(@NotNull String name,
                   @NotNull String street,
                   @NotNull String houseNumber,
                   @NotNull String city,
                   @NotNull @Pattern(regexp = "[0-9]{5}") String zipCode,
                   @NotNull @Pattern(regexp = "[0-9]{8}") String identificationNumber,
                   @Pattern(regexp = "(cz|CZ|Cz)[0-9]{8,10}") String taxIdentificationNumber,
                   @NotNull Integer numberOfBills,
                   @NotNull @Pattern(regexp = "[0-9]{16}") String accountNumber,
                   @NotNull @Pattern(regexp = "[0-9]{4}") String bankNumber) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.identificationNumber = identificationNumber;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.numberOfBills = numberOfBills;
        this.accountNumber = accountNumber;
        this.bankNumber = bankNumber;
    }
}
