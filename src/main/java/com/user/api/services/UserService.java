package com.user.api.services;

import com.user.api.entities.Photo;
import com.user.api.entities.User;
import com.user.api.model.UserModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(String fName, String mName, String lName, Integer age, String email, List<MultipartFile> file);

    Photo getFile(String fileName) throws FileNotFoundException;

    List<UserModel> getAllUsers();

    UserModel getUsers(String userNameOrEmail);
}
