package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.dto.GroupDto;
import com.kodilla.ecommercee.service.ProductDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    @Autowired
    private ProductMapper productMapper;


    public Group mapToGroup(final GroupDto groupDto) {
        return new Group(
                groupDto.getGroupId(),
                groupDto.getName(),
                productMapper.mapToProductList(groupDto.getProducts()));
    }

    public GroupDto mapToGroupDto(final Group group) {
        return new GroupDto(
                group.getId(),
                group.getGroupName(),
                productMapper.mapToProductDtoList(group.getProducts()));
    }

    public List<GroupDto> mapToGroupDtoList(final List<Group> groupList) {
        return groupList.stream()
                .map(g -> new GroupDto(g.getId(), g.getGroupName(), productMapper.mapToProductDtoList(g.getProducts())))
                .collect(Collectors.toList());
    }


}
