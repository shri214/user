package com.todo.todo.Reposeteries;

import com.todo.todo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReposeteries extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM todo_app WHERE user_name= :userName", nativeQuery = true)
   Optional<User> findByUserName(@Param("userName") String userName);
}
