package com.todo.todo.Services;

import com.todo.todo.Entity.User;
import com.todo.todo.Reposeteries.UserReposeteries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailServices implements UserDetailsService {

    @Autowired
    private UserReposeteries userReposeteries;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> OptionUser = userReposeteries.findByUserName(username);
        User user = null;

        if (OptionUser.isPresent()) {
             user = OptionUser.get();
             System.out.println(user);
            if (user == null) {
                throw new UsernameNotFoundException("user not found");
            }
        }

        return new MyUserDetailPrinciple(user);
    }
}
