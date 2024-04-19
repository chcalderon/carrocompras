package com.backprinc.rps.usersapp.backendusersapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backprinc.rps.usersapp.backendusersapp.models.dto.UserDTO;
import com.backprinc.rps.usersapp.backendusersapp.models.entities.User;
import com.backprinc.rps.usersapp.backendusersapp.models.requests.UserRequest;
import com.backprinc.rps.usersapp.backendusersapp.services.UserService;

import jakarta.validation.Valid;

@RestController
// @CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(originPatterns = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDTO> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<UserDTO> UserOptional = service.findById(id);

        if (UserOptional.isPresent()){
            return ResponseEntity.ok(UserOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    // @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // public User create(@RequestBody User user) {
    //     return service.save(user);
    // }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
    //     Optional<User> o = service.findById(id);
    //     if (o.isPresent()){
    //         User userDb = o.orElseThrow();
    //         userDb.setUsername(user.getUsername());
    //         userDb.setEmail(user.getEmail());
    //         return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));

    //     }
        
    //     return ResponseEntity.notFound().build();
    // }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()){
            return validation(result);
        }
        Optional<UserDTO> o = service.update(user, id);
        return o.isPresent()? 
                ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow()) :
                ResponseEntity.notFound().build();
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove( @PathVariable Long id) {
        Optional<UserDTO> o = service.findById(id);
        if (o.isPresent()){
            service.remove(id);
            return ResponseEntity.noContent().build(); //204
        }
        return ResponseEntity.notFound().build();
    }
    
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo: " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
