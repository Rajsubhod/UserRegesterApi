package com.user.api.repository;

import com.user.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("select u form User u where u.name.f_name =?1 or u.email =?2")
    public Optional<User> findByNameOrEmail(String userName,String userEmail);
}
