package com.williams.springsecuritycifrado.rest;

import com.williams.springsecuritycifrado.dto.CountDTO;
import com.williams.springsecuritycifrado.dto.UserListDTO;
import com.williams.springsecuritycifrado.entities.Car;
import com.williams.springsecuritycifrado.entities.User;
import com.williams.springsecuritycifrado.entities.util.Role;
import com.williams.springsecuritycifrado.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        log.info("REST request to find one user");
        Optional<User> userOpt = this.userService.findById(id);

        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<User> findAll() {
        log.info("REST request to find all user");
        return userService.findAll();
    }

    @PutMapping("/permission/{id}")
    public ResponseEntity<User> updateRole(@PathVariable Long id) {
        if (this.userService.findById(id).isPresent()) {
            User user = this.userService.findById(id).get();

            if (user.getRole().equals(Role.CUSTOMER)) {
                user.setRole(Role.ADMINISTRATOR);
                log.info("REST request to update Role ADMINISTRATOR a user with id: " + user.getId());

                return ResponseEntity.ok(this.userService.save(user));
            }
        }
        log.warn("Update Role failed");
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/permission/revoque/{id}")
    public ResponseEntity<User> revoqueRole(@PathVariable Long id) {
        if (this.userService.findById(id).isPresent()) {
            User user = this.userService.findById(id).get();

            if (!user.getRole().equals(Role.CUSTOMER)) {
                user.setRole(Role.CUSTOMER);
                log.info("REST request to revoque Role ADMINISTRATOR a user with id: " + user.getId());

                return ResponseEntity.ok(this.userService.save(user));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id){
        log.info("REST request to delete an existing car");

        this.userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // delete all

    @DeleteMapping
    public ResponseEntity<User> deleteAll(){
        log.info("REST request to delete all cars");

        this.userService.deleteAll();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<CountDTO> count(){
        log.info("REST request to count all cars");
        Long count = this.userService.count();
        CountDTO dto = new CountDTO(count);
        dto.setMessage("Que tenga usted un feliz dia :)");
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/deletemany")
    public ResponseEntity<User> deleteMany(@RequestBody UserListDTO userListDTO){

        this.userService.deleteAll(userListDTO.getUsers());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deletemany/{ids}")
    public ResponseEntity<User> deleteMany(@PathVariable List<Long> ids){
        this.userService.deleteAllById(ids);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{role}")
    public List<User> findByRole(@PathVariable Role role) {
        log.info("REST request to find user by Role: " + role.name());
        return this.userService.findByRole(role);
    }
}
