package com.kodilla.ecommercee.controller;

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

    @Autowired
    private GroupDbService groupDbService;

    @GetMapping(value = "groups")
    public List<GroupDto> getGroups() {
        return groupDbService.getAllGroups();
    }

    @GetMapping(value = "group")
    public GroupDto getGroupById(@RequestParam Long groupId) throws GroupNotFoundException {
        return groupDbService.getGroup(groupId);
    }

    @PostMapping(value = "group")
    public void createGroup(@RequestParam Long userId, @RequestBody GroupDto groupDto) throws GroupConflictException {
        groupDbService.saveGroup(userId, groupDto);
    }

    @PutMapping(value = "group")
    public GroupDto updateGroup(@RequestParam Long userId, @RequestBody GroupDto groupDto) throws GroupConflictException {
        return groupDbService.saveGroup(userId, groupDto);
    }

    @DeleteMapping(value = "group")
    public void deleteGroup(@RequestParam Long userId, @RequestParam Long groupId) throws GroupNotFoundException {
        groupDbService.deleteGroup(userId, groupId);
    }

    @PutMapping(value = "addProductToGroup")
    public GroupDto addProductToGroup(@RequestParam Long userId, @RequestParam Long groupId, @RequestParam Long productId)
            throws GroupNotFoundException, ProductNotFoundException {

        return groupDbService.addProductToGroup(userId, groupId, productId);
    }
}
