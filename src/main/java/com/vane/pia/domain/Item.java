package com.vane.pia.domain;

import com.vane.pia.utils.Calculator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Item extends EntityParent {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Float price;

    @NotNull
    private Float count;

    // DPH [%]
    @NotNull
    private Integer vatPercentage;

    @NotNull
    private boolean isDeleted;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    // Start - Calculated field
    private Float vatAmount;

    private Float totalPrice;

    private Float priceWithoutVat;
    // End - Calculated field

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_bill")
    private Bill bill;

    public Item() {
        this.isDeleted = false;
    }

    public Item(@NotNull String name,
                String description,
                @NotNull Float price,
                @NotNull Float count,
                @NotNull Integer vatPercentage) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
        this.vatPercentage = vatPercentage;
        this.isDeleted = false;
    }




}
