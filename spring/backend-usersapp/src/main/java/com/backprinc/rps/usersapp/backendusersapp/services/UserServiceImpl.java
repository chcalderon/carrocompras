package com.backprinc.rps.usersapp.backendusersapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backprinc.rps.usersapp.backendusersapp.models.IUser;
import com.backprinc.rps.usersapp.backendusersapp.models.dto.UserDTO;
import com.backprinc.rps.usersapp.backendusersapp.models.dto.mapper.DtoMapperUser;
import com.backprinc.rps.usersapp.backendusersapp.models.entities.Role;
import com.backprinc.rps.usersapp.backendusersapp.models.entities.User;
import com.backprinc.rps.usersapp.backendusersapp.models.requests.UserRequest;
import com.backprinc.rps.usersapp.backendusersapp.repositories.RoleRepository;
import com.backprinc.rps.usersapp.backendusersapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = (List<User>) repository.findAll();

        return users
                .stream()
                .map(u -> DtoMapperUser.builder().setUser(u).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        return repository.findById(id).map(u -> DtoMapperUser.builder().setUser(u).build());
    }

    @Override
    @Transactional
    public UserDTO save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(getRoles(user));

        return DtoMapperUser.builder().setUser(repository.save(user)).build();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
        ;
    }

    // @Override
    // @Transactional
    // public Optional<User> update(User user, Long id) {
    // Optional<User> o = this.findById(id);
    // if (o.isPresent()){
    // User userDb = o.orElseThrow();
    // userDb.setUsername(user.getUsername());
    // userDb.setEmail(user.getEmail());
    // return Optional.of(this.save(userDb));

    // }
    // return Optional.empty();
    // }

    @Override
    @Transactional
    public Optional<UserDTO> update(UserRequest user, Long id) {
        Optional<User> o = repository.findById(id);
        User u = null;
        if (o.isPresent()) {
            User userDb = o.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            userDb.setRoles(getRoles(user));
            u = repository.save(userDb);
        }
        return Optional.ofNullable(DtoMapperUser.builder().setUser(u).build());
    }

    private List<Role> getRoles(IUser user) {
        Optional<Role> ou = roleRepository.findByName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        if (ou.isPresent()) {
            roles.add(ou.orElseThrow());
        }
        if (user.isAdmin()) {
            Optional<Role> oAdmin = roleRepository.findByName("ROLE_ADMIN");
            if (oAdmin.isPresent())
                roles.add(oAdmin.orElseThrow());
        }

        return roles;

    }

}
