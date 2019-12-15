package com.vane.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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


    private String description;

    // je přijatá
    @NotNull
    private Boolean isAccepted;

    // datum vystavění faktury
    @NotNull
    private LocalDateTime issuedDate;

    // datum splatnosti
    @NotNull
    private LocalDateTime dueDate;

    // hotově
    @NotNull
    private Boolean isCash;

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

    @NotNull
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
                String description,
                @NotNull Boolean isAccepted,
                @NotNull LocalDateTime issuedDate,
                @NotNull LocalDateTime dueDate,
                @NotNull Boolean isCash) {
        this.name = name;
        this.description = description;
        this.isAccepted = isAccepted;
        this.issuedDate = issuedDate;
        this.dueDate = dueDate;
        this.isCash = isCash;
        this.isDeleted = false;
    }

    public String getIssuedCzDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return issuedDate.format(formatter);
    }

    public String getDueCzDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return dueDate.format(formatter);
    }
}
