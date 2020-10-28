package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.dto.GroupDto;
import com.kodilla.ecommercee.exception.group.GroupConflictException;
import com.kodilla.ecommercee.exception.group.GroupNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
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
    @Autowired
    private GroupMapper groupMapper;

    @GetMapping(value = "groups")
    public List<GroupDto> getGroups() {
        return groupMapper.mapToGroupDtoList(groupDbService.getAllGroups());
    }

    @GetMapping(value = "group")
    public GroupDto getGroupById(@RequestParam Long groupId) throws GroupNotFoundException {
        return groupMapper.mapToGroupDto(groupDbService.getGroup(groupId).orElseThrow(GroupNotFoundException::new));
    }

    @PostMapping(value = "group")
    public void createGroup(@RequestBody GroupDto groupDto) throws GroupConflictException {
        groupDbService.saveGroup(groupMapper.mapToGroup(groupDto));
    }

    @PutMapping(value = "group")
    public GroupDto updateGroup(@RequestBody GroupDto groupDto) throws GroupConflictException {
        return groupMapper.mapToGroupDto(groupDbService.saveGroup(groupMapper.mapToGroup(groupDto)));
    }

    @DeleteMapping(value = "group")
    public void deleteGroup(@RequestParam Long groupId) throws GroupNotFoundException {
        groupDbService.deleteGroup(groupId);
    }
}
