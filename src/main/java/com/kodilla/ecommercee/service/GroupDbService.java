package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.exception.group.GroupConflictException;
import com.kodilla.ecommercee.exception.group.GroupNotFoundException;
import com.kodilla.ecommercee.repository.GroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupDbService {
    @Autowired
    private GroupDao groupDao;

    public List<Group> getAllGroups() {
        return groupDao.findAll();
    }

    public Optional<Group> getGroup(final Long id) {
        return groupDao.findById(id);
    }

    public Group saveGroup(final Group group) throws GroupConflictException {
        if (!groupDao.existsById(group.getId())) {
            return groupDao.save(group);
        } else {
            throw new GroupConflictException();
        }
    }

    public void deleteGroup(final Long id) throws GroupNotFoundException {
        if (groupDao.existsById(id)) {
            groupDao.deleteById(id);
        } else {
            throw new GroupNotFoundException();
        }

    }
}
