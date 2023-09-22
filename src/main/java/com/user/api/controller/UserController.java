package com.user.api.controller;

import com.user.api.entities.Photo;
import com.user.api.entities.User;
import com.user.api.model.PhotoModel;
import com.user.api.model.UserModel;
import com.user.api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> findAllUser(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userNameOrEmail}")
    public ResponseEntity<?> findUser(@PathVariable String userNameOrEmail){

        return ResponseEntity.ok(userService.getUsers(userNameOrEmail));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> findUserId(@PathVariable int id){
//
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestParam("file") List<MultipartFile> file,
            @Valid @RequestParam("f_name") String f_name,@Valid @RequestParam("m_name") String m_name,
            @Valid @RequestParam("l_name") String l_name,@Valid @RequestParam("age") Integer age,
            @Valid @RequestParam("email") String email){
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
//        return ResponseEntity.ok(UserModel.builder()
//                .name(user.getName())
//                .email(user.getEmail())
//                .photo(photoModelList)
//                .build());
        return new ResponseEntity<UserModel>(UserModel.builder()
                .name(user.getName())
                .email(user.getEmail())
                .photo(photoModelList)
                .build(),HttpStatus.CREATED);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName) throws FileNotFoundException {
        Photo photo=userService.getFile(fileName);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(photo.getPhotoType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\""+photo.getPhotoName()+"\"")
                .body(new ByteArrayResource(photo.getData()));
    }
}
