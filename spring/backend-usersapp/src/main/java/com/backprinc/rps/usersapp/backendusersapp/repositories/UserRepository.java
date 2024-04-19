package com.backprinc.rps.usersapp.backendusersapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.backprinc.rps.usersapp.backendusersapp.models.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByUsername(String username);

    // @Query("select u from User y where u.username=?1 and u.email=?2")
    // Optional<User> getUserByUsername(String username, String email);
}
