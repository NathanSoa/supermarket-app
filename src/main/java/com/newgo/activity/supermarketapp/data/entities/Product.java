package com.newgo.activity.supermarketapp.data.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    @Size(min = 3)
    @NotBlank(message = "Name cannot be null!")
    private String name;

    @Lob
    private Byte[] photo;

    @Size(min = 10)
    @NotBlank(message = "Message cannot be null!")
    private String description;

    private Boolean active;

    @NotNull
    private BigDecimal price;
}
