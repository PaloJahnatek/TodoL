package com.palo.todolist.restControllers;

import com.palo.todolist.domain.User;
import com.palo.todolist.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/restuser")
public class RestUserController {
    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody User user) { // do tejto metódy príde object user, object user vstúpi do metódy userService add ktorá ho pridá do našej databázy a vráti nám Integer do id
        Integer id = userService.add(user);

        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) { // id ktoré je v GetMapping vstúpi do premennej int id, táto premenná vstúpi do userService.get(id),táto metóda vytiahne usera z databázy na základe jeho id,vytvorím si nového usera User user, do ktorého sa mi ten vytiahnutý user vloži
        User user = userService.get(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity getAll() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteUserWithId(@PathVariable("id") int id) {
        if (userService.get(id) != null) { // natiahne usera z databázy pozrie sa či nie je null, ak nie je null, tak userService.delete táto metóda nám vymaže toho usera, a vráti nám ResponseEntity.ok  chceme aby sa to vykonalo čiže build
            userService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("User with id : " + id + "doesn't  exist");
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity updateUser(@PathVariable("id") int id, @RequestBody User user) {
        if (userService.get(id) != null) {
            user.setId(id);
            userService.update(id, user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("User with id : " + id + "doesn't  exist");
        }
    }

}
