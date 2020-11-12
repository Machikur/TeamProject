package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.aop.userwatcher.OperationType;
import com.kodilla.ecommercee.aop.userwatcher.UserOperation;
import com.kodilla.ecommercee.dto.GroupDto;
import com.kodilla.ecommercee.exception.group.GroupConflictException;
import com.kodilla.ecommercee.exception.group.GroupNotFoundException;
import com.kodilla.ecommercee.exception.product.ProductNotFoundException;
import com.kodilla.ecommercee.service.GroupDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class GroupController {

    private final GroupDbService groupDbService;

    @Autowired
    public GroupController(GroupDbService groupDbService) {
        this.groupDbService = groupDbService;
    }

    @GetMapping("groups")
    public List<GroupDto> getGroups() {
        return groupDbService.getAllGroups();
    }

    @GetMapping("/group")
    public GroupDto getGroupById(@RequestParam Long groupId) throws GroupNotFoundException {
        return groupDbService.getGroup(groupId);
    }

    @UserOperation(operationtype = OperationType.CREATE)
    @PostMapping("/group")
    public GroupDto createGroup(@RequestParam Long userId, @RequestBody GroupDto groupDto) throws GroupConflictException {
        return groupDbService.saveGroup(groupDto);
    }

    @UserOperation(operationtype = OperationType.UPDATE)
    @PutMapping("/group")
    public GroupDto updateGroup(@RequestParam Long userId, @RequestBody GroupDto groupDto) throws GroupNotFoundException {
        return groupDbService.updateGroup(groupDto);
    }

    @UserOperation(operationtype = OperationType.DELETE)
    @DeleteMapping("/group")
    public void deleteGroup(@RequestParam Long userId, @RequestParam Long groupId) throws GroupNotFoundException {
        groupDbService.deleteGroup(groupId);
    }

    @UserOperation(operationtype = OperationType.UPDATE)
    @PutMapping("/productToGroup")
    public GroupDto addProductToGroup(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam Long productId)
            throws GroupNotFoundException, ProductNotFoundException {

        return groupDbService.addProductToGroup(groupId, productId);
    }
}
