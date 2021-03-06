package com.kodilla.ecommercee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto implements ShopComponent {
    private Long groupId;
    private String groupName;
    private List<ProductDto> products;

    @Override
    public Long getComponentId() {
        return groupId;
    }
}