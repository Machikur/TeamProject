package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.dto.GroupDto;
import com.kodilla.ecommercee.exception.group.GroupConflictException;
import com.kodilla.ecommercee.exception.group.GroupNotFoundException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.repository.GroupDao;
import com.kodilla.ecommercee.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupDbService {

    private GroupDao groupDao;
    private GroupMapper groupMapper;
    private ProductDao productDao;

    @Autowired
    public GroupDbService(GroupDao groupDao, GroupMapper groupMapper, ProductDao productDao) {
        this.groupDao = groupDao;
        this.groupMapper = groupMapper;
        this.productDao = productDao;
    }

    public List<GroupDto> getAllGroups() {
        return groupMapper.mapToGroupDtoList(groupDao.findAll());
    }

    public GroupDto getGroup(Long id) throws GroupNotFoundException {
        return groupMapper.mapToGroupDto(groupDao.findById(id).orElseThrow(GroupNotFoundException::new));
    }

    public GroupDto saveGroup(GroupDto group) throws GroupConflictException {
        if (!groupDao.existsGroupByGroupName(group.getGroupName())) {
            return groupMapper.mapToGroupDto(groupDao.save(groupMapper.mapToGroup(group)));
        } else {
            throw new GroupConflictException();
        }
    }

    public void deleteGroup(Long id) throws GroupNotFoundException {
        if (groupDao.existsById(id)) {
            groupDao.deleteById(id);
        } else {
            throw new GroupNotFoundException();
        }
    }

    public GroupDto updateGroup(GroupDto groupDto) throws GroupNotFoundException {
        Group group = groupDao.findById(groupDto.getGroupId()).orElseThrow(GroupNotFoundException::new);
        group.setGroupName(groupDto.getGroupName());
        group.setProducts(groupDto.getProducts().stream()
                .map(productDto -> new Product(productDto.getProductId(), productDto.getProductName(),
                        productDto.getProductPrice(), productDto.getQuantity()))
                .collect(Collectors.toList()));
        return groupMapper.mapToGroupDto(groupDao.save(group));
    }

    public GroupDto addProductToGroup(Long groupId, Long productId)
            throws GroupNotFoundException, ProductNotFoundException {
        Group group = groupDao.findById(groupId).orElseThrow(GroupNotFoundException::new);
        Product p = productDao.findById(productId).orElseThrow(ProductNotFoundException::new);
        group.getProducts().add(p);
        p.setGroup(group);
        return groupMapper.mapToGroupDto(groupDao.save(group));

    }
}
