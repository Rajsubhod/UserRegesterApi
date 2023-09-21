package com.user.api.controller;

import com.user.api.entities.User;
import com.user.api.model.PhotoModel;
import com.user.api.model.UserModel;
import com.user.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> findAllUser(){

        return ResponseEntity.ok("?");
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> findUser(@PathVariable String userName){

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserId(@PathVariable String id){

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestParam("file") List<MultipartFile> file,
            @RequestParam("f_name") String f_name, @RequestParam("m_name") String m_name,
            @RequestParam("l_name") String l_name, @RequestParam("age") Integer age,
            @RequestParam("email") String email){
        User user= userService.saveUser(f_name,m_name,l_name,age,email,file);
        List<PhotoModel> photoModelList = file.stream().map(e -> {
            String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/download/")
                    .path(Objects.requireNonNull(e.getOriginalFilename())).toUriString();
            return PhotoModel.builder()
                    .photoName(e.getOriginalFilename())
                    .photoType(e.getContentType())
                    .fileSize(e.getSize())
                    .downloadURL(downloadURL)
                    .build();
        }).toList();
        return ResponseEntity.ok(UserModel.builder()
                .name(user.getName())
                .email(user.getEmail())
                .photo(photoModelList)
                .build());
    }
}