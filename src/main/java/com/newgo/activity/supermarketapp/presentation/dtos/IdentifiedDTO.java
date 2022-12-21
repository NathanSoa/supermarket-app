package com.newgo.activity.supermarketapp.presentation.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class IdentifiedDTO implements IDTO {

    private Long id;
}
