package com.todo.todo.Services;

import com.todo.todo.Entity.User;
import com.todo.todo.ErrorHandler.UserAlreadyExitsException;
import com.todo.todo.Reposeteries.UserReposeteries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServices {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserReposeteries userReposeteries;

    @Autowired
    private JwtServices jwtServices;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);


    public List<User> getAllUser(){
        return userReposeteries.findAll();
    }
    public User addNewUsers( User user){
        if (userReposeteries.findById(user.getId()).isPresent()) {
            throw new UserAlreadyExitsException("User already exists with username: " + user.getUserName());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userReposeteries.save(user);
    }

    public User updateUser(String id, User user) {
        if (!userReposeteries.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        User existingUser=userReposeteries.findById(id).orElseThrow(()->new RuntimeException("User not found ): "+id));

       if(user.getUserName()!=null && user.getUserName()!=""){
           existingUser.setUserName(user.getUserName());
       }
       if(user.getDescription()!=null && user.getDescription()!=""){
           existingUser.setDescription(user.getDescription());
       }
        return userReposeteries.save(existingUser);
    }

    public void deleteUser(String id) {
        if (!userReposeteries.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userReposeteries.deleteById(id);
    }


    public Optional<User> getUserById(String id){

        Optional<User> existingUser=userReposeteries.findById(id);
        if(!existingUser.isPresent()){
            throw new UserAlreadyExitsException("invalid id !");
        }
        return existingUser;

    }

    public Optional<User> getByUserName(String userName){
        Optional<User> reposeteriesByUserName = userReposeteries.findByUserName(userName);
        System.out.println(reposeteriesByUserName);
        if(!reposeteriesByUserName.isPresent()){
            throw new RuntimeException("user not found");
        }
        return reposeteriesByUserName;
    }

    public String verify(User user) {
        System.out.println(user.getUserName()+"  "+ user.getPassword());
        Authentication authentication=
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtServices.getJsonToken(user.getUserName());
        }
        return "fail";
    }
}
