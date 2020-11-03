package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.UserOperation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserOperationDao extends CrudRepository<UserOperation, Long> {

    List<UserOperation> findAllByUser_UserId(Long userId);
}
