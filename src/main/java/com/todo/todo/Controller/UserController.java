package com.todo.todo.Controller;

import com.todo.todo.Entity.User;
import com.todo.todo.ErrorHandler.UserAlreadyExitsException;
import com.todo.todo.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
private final UserServices userServices;

@Autowired
public UserController(UserServices userServices){
    this.userServices=userServices;
}

@GetMapping("/getAllUser")
public ResponseEntity<List<User>> getAllUser(){
    List<User> user=userServices.getAllUser();
    if(user.isEmpty()){
        return ResponseEntity.noContent().build();
    }else{
        return ResponseEntity.ok(user);
    }
}

@PostMapping("/addNewUser")
public ResponseEntity<User> addNewUser(@RequestBody User user){
    try {
        User saveUser=userServices.addNewUsers(user);
        return ResponseEntity.ok(saveUser);
    }catch (UserAlreadyExitsException ex){
        throw ex;
    }catch (Exception ex){
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@PutMapping("/updateById/{id}")
public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user){
    try {
        User user1=userServices.updateUser(id,user);
        return ResponseEntity.ok(user1);
    }catch (RuntimeException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }catch (Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@DeleteMapping("/deleteByid/{id}")
public ResponseEntity<String> deleteById(@PathVariable String id){
    try {
        userServices.deleteUser(id);
        return ResponseEntity.noContent().build();
    }catch (RuntimeException ex){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }catch (Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@GetMapping("/getUserByIds/{id}")
public ResponseEntity<User> getUserById(@PathVariable String id){
    try {
        Optional<User>user =userServices.getUserById(id);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }catch (Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

@GetMapping("/getUserByUserName/{userName}")
public ResponseEntity<User> getUserByUserName(@PathVariable String userName){
        try {
            Optional<User>user =userServices.getByUserName(userName);
            System.out.println(user);
            if (user.isPresent()){
                return ResponseEntity.ok(user.get());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
