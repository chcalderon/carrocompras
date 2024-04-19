package com.backprinc.rps.usersapp.backendusersapp.services;

import java.util.List;
import java.util.Optional;

import com.backprinc.rps.usersapp.backendusersapp.models.dto.UserDTO;
import com.backprinc.rps.usersapp.backendusersapp.models.entities.User;
import com.backprinc.rps.usersapp.backendusersapp.models.requests.UserRequest;

public interface UserService {
    
    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    UserDTO save(User user);

    Optional<UserDTO> update(UserRequest user, Long id);

    void remove(Long id);
}
