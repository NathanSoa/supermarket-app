package com.newgo.activity.supermarketapp.domain;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotNull(message = "You should provide at least 1 photo!")
    private Set<Byte> photos;

    @Size(min = 20)
    @NotBlank(message = "Message cannot be null!")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Byte> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Byte> photos) {
        this.photos = photos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
