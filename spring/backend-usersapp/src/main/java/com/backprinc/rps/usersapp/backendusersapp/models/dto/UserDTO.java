package com.backprinc.rps.usersapp.backendusersapp.models.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private boolean admin;
    
    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, boolean admin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getusername() {
        return username;
    }
    public void setusername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
}
