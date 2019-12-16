package com.vane.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class TempItem extends EntityParent {

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


    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    // Start - Calculated field
    private Float vatAmount;

    private Float totalPrice;

    private Float priceWithoutVat;
    // End - Calculated field

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User user;

    public TempItem() {
    }

    public TempItem(@NotNull Float price,
                    Float vatAmount,
                    Float totalPrice) {
        this.price = price;
        this.vatAmount = vatAmount;
        this.totalPrice = totalPrice;
    }

    public TempItem(@NotNull String name,
                    String description,
                    @NotNull Float price,
                    @NotNull Float count,
                    @NotNull Integer vatPercentage) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
        this.vatPercentage = vatPercentage;
    }


}

