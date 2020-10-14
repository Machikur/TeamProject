package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.GroupDto;
import com.kodilla.ecommercee.exception.group.GroupConflictException;
import com.kodilla.ecommercee.exception.group.GroupNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.GroupDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class GroupController {
    @Autowired
    private GroupDbService groupDbService;
    @Autowired
    private GroupMapper groupMapper;

    @GetMapping(value = "getAllGroups")
    public List<GroupDto> getGroups() {
        return groupMapper.mapToGroupDtoList(groupDbService.getAllGroups());
    }

    @GetMapping(value = "getGroup")
    public GroupDto getGroupById(@RequestParam Long groupId) throws GroupNotFoundException {
        return groupMapper.mapToGroupDto(groupDbService.getGroup(groupId).orElseThrow(GroupNotFoundException::new));
    }

    @PostMapping(value = "createGroup")
    public void createGroup(@RequestBody GroupDto groupDto) throws GroupConflictException {
         groupDbService.saveGroup(groupMapper.mapToGroup(groupDto));
    }

    @PutMapping(value = "updateGroup")
    public GroupDto updateGroup(@RequestBody GroupDto groupDto) throws GroupConflictException {
        return groupMapper.mapToGroupDto(groupDbService.saveGroup(groupMapper.mapToGroup(groupDto)));
    }

    @DeleteMapping(value = "deleteGroup")
    public void deleteGroup(@RequestParam Long groupId) throws GroupNotFoundException {
        groupDbService.deleteGroup(groupId);
    }
}
