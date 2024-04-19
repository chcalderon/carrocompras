package com.backprinc.rps.usersapp.backendusersapp.models.dto.mapper;

import com.backprinc.rps.usersapp.backendusersapp.models.dto.UserDTO;
import com.backprinc.rps.usersapp.backendusersapp.models.entities.User;

public class DtoMapperUser {

    private User user;

    private DtoMapperUser() {
    }

    public static DtoMapperUser builder() {
        return new DtoMapperUser();
    }

    public DtoMapperUser setUser(User user) {
        this.user = user;
        return this;
    }

    public UserDTO build() {
        if(user == null){
            throw new RuntimeException("Debe ingresar el entity User");
        }
        boolean isAdmin = user.getRoles().stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));
        return new UserDTO(this.user.getId(), user.getUsername(), user.getEmail(), isAdmin);
    }

}
