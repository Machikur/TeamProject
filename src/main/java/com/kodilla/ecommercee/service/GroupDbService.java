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
import com.kodilla.ecommercee.validation.AuthorizationRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GroupDbService {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private ProductDao productDao;

    public List<GroupDto> getAllGroups() {
        return groupMapper.mapToGroupDtoList(groupDao.findAll());
    }

    public GroupDto getGroup(Long id) throws GroupNotFoundException {
        return groupMapper.mapToGroupDto(groupDao.findById(id).orElseThrow(GroupNotFoundException::new));
    }

    @AuthorizationRequired
    public GroupDto saveGroup(Long userId, GroupDto group) throws GroupConflictException {
        if (!groupDao.existsGroupByGroupName(group.getGroupName())) {
            return groupMapper.mapToGroupDto(groupDao.save(groupMapper.mapToGroup(group)));
        } else {
            throw new GroupConflictException();
        }
    }

    @AuthorizationRequired
    public void deleteGroup(Long userId, Long id) throws GroupNotFoundException {
        if (groupDao.existsById(id)) {
            groupDao.deleteById(id);
        } else {
            throw new GroupNotFoundException();
        }
    }

    @AuthorizationRequired
    public GroupDto addProductToGroup(Long userId, Long groupId, Long productId)
            throws GroupNotFoundException, ProductNotFoundException {

        Group group = groupDao.findById(groupId).orElseThrow(GroupNotFoundException::new);
        Product p = productDao.findById(productId).orElseThrow(ProductNotFoundException::new);
        group.getProducts().add(p);
        p.setGroup(group);
        return groupMapper.mapToGroupDto(groupDao.save(group));

    }
}
