package com.vane.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Contact extends EntityParent{

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

    @NotNull
    @Pattern(regexp = "[0-9]{6,16}")
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

    @OneToMany(mappedBy = "contact")
    private List<Bill> bills = new LinkedList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_company")
    private Company company;


    public Contact() {
        this.isDeleted = false;
    }

    public Contact(@NotNull String name,
                   @NotNull String street,
                   @NotNull String houseNumber,
                   @NotNull String city,
                   @NotNull @Pattern(regexp = "[0-9]{5}") String zipCode,
                   @NotNull @Pattern(regexp = "[0-9]{8}") String identificationNumber,
                   @Pattern(regexp = "(cz|CZ|Cz)[0-9]{8,10}") String taxIdentificationNumber,
                   @NotNull @Pattern(regexp = "[0-9]{16}") String accountNumber,
                   @NotNull @Pattern(regexp = "[0-9]{4}") String bankNumber) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.identificationNumber = identificationNumber;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.accountNumber = accountNumber;
        this.bankNumber = bankNumber;
        this.isDeleted = false;
    }

    public Contact(@NotNull String name,
                   @NotNull String street,
                   @NotNull String houseNumber,
                   @NotNull String city,
                   @NotNull @Pattern(regexp = "[0-9]{5}") String zipCode,
                   @NotNull @Pattern(regexp = "[0-9]{8}") String identificationNumber,
                   @NotNull @Pattern(regexp = "[0-9]{16}") String accountNumber,
                   @NotNull @Pattern(regexp = "[0-9]{4}") String bankNumber) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.identificationNumber = identificationNumber;
        this.accountNumber = accountNumber;
        this.bankNumber = bankNumber;
        this.isDeleted = false;
    }

    public String getAddress() {
        return this.street + " " + this.houseNumber + "," +
                this.city + " " + this.zipCode;
    }

    @Override
    public String toString(){
        return "Contact";
    }
}
