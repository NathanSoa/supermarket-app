package com.newgo.activity.supermarketapp.presentation.dtos;

public abstract class IdentifiedDTO implements IDTO {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}