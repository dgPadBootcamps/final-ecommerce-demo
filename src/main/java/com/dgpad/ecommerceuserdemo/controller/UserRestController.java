package com.dgpad.ecommerceuserdemo.controller;

import com.dgpad.ecommerceuserdemo.model.User;
import com.dgpad.ecommerceuserdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    private UserRestController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/create",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(userService.userNameExists(user.getUsername()))
            return new ResponseEntity<>("User Name is Already in Use", HttpStatus.CONFLICT);
        if(userService.userEmailExists(user.getEmail()))
            return new ResponseEntity<>("Email is Already in Use", HttpStatus.CONFLICT);

        try {
            User newUser = userService.createUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers(){
        try{
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("id") String id){
        try{
            return new ResponseEntity<>(userService.getUserById(UUID.fromString(id)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/deactivate/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deActivateUser(@PathVariable("id") String id){
        try{
            return new ResponseEntity<>(userService.deActivateUser(UUID.fromString(id)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/activate/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> activateUser(@PathVariable("id") String id){
        try{
            return new ResponseEntity<>(userService.activate(UUID.fromString(id)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
