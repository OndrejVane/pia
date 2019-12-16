package com.vane.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Bill extends EntityParent {

    @NotNull
    private String name;


    @NotNull
    private String description;

    // je přijatá
    @NotNull
    private Boolean isAccepted;

    // datum vystavění faktury
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate issuedDate;

    // datum splatnosti
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;

    // hotově
    @NotNull
    private Boolean isCash;

    @NotNull
    private Boolean isPaid;

    // Start - Calculated field
    @Column(unique = true)
    private String billNumber;

    private Float price;

    private Float vat;

    private Float totalPrice;
    // End - Calculated field

    @NotNull
    private boolean isDeleted;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_company")
    private Company company;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_contact")
    private Contact contact;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user")
    private User user;

    @OneToMany(mappedBy = "bill")
    private List<Item> items = new LinkedList<>();


    public Bill() {
        this.isDeleted = false;
    }

    public Bill(@NotNull String name,
                @NotNull String description,
                @NotNull Boolean isAccepted,
                @NotNull LocalDate issuedDate,
                @NotNull LocalDate dueDate,
                @NotNull Boolean isCash,
                @NotNull Boolean isPaid) {
        this.name = name;
        this.description = description;
        this.isAccepted = isAccepted;
        this.issuedDate = issuedDate;
        this.dueDate = dueDate;
        this.isCash = isCash;
        this.isDeleted = false;
        this.isPaid = isPaid;
    }

    public String getIssuedCzDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return issuedDate.format(formatter);
    }

    public String getDueCzDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return dueDate.format(formatter);
    }

    @Override
    public String toString(){
        return "Bill";
    }
}
