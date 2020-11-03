package com.kodilla.ecommercee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ShopComponent {
    @JsonIgnore
    Long getComponentId();
}
