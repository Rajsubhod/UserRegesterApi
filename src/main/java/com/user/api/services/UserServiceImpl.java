package com.user.api.services;

import com.sun.jdi.InternalException;
import com.user.api.entities.Name;
import com.user.api.entities.Photo;
import com.user.api.entities.User;
import com.user.api.model.PhotoModel;
import com.user.api.model.UserModel;
import com.user.api.repository.PhotoRepository;
import com.user.api.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public User saveUser(String fName, String mName, String lName,
                         Integer age, String email, List<MultipartFile> file) {

        User user = User.builder()
                .name(Name.builder()
                        .f_name(fName)
                        .m_name(mName)
                        .l_name(lName)
                        .build())
                .age(age)
                .email(email)
                .build();
        List<Photo> photos = file.stream().map(p -> {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(p.getOriginalFilename()));
                if(fileName.contains("..")){
                    throw new InvalidFileNameException(fileName," file contains invalid path sequence ");
                }

            Photo photo = null;
            try {
                photo = Photo.builder()
                        .photoName(fileName)
                        .photoType(p.getContentType())
                        .data(p.getBytes())
                        .user(user)
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            photoRepository.save(photo);
                return photo;
        }).toList();

        return user;
    }

    @Override
    public Photo getFile(String fileName) throws FileNotFoundException {
        return photoRepository.findByPhotoName(fileName).orElseThrow(() ->new FileNotFoundException(fileName+"Does not exist " +
                "or link broken"));
    }

    @Override
    public List<UserModel> getAllUsers() {
        List<UserModel> user = userRepository.findAll().stream().map(p->{
            return UserModel.builder()
                    .name(p.getName())
                    .email(p.getEmail())
                    .photo(p.getPhoto().stream().map(e-> PhotoModel.builder()
                            .photoName(e.getPhotoName())
                            .photoType(e.getPhotoType())
                            .build()).toList()).build();
        }).toList();
        return user;
    }

    @Override
    public UserModel getUsers(String userNameOrEmail) {
        Optional<User> user = userRepository.findByNameOrEmail(userNameOrEmail,userNameOrEmail);
        UserModel userModel = null;
        if(user.isPresent()) {
            userModel = UserModel.builder()
                    .name(user.get().getName())
                    .email(user.get().getEmail())
                    .photo(user.get().getPhoto().stream().map(e->PhotoModel.builder()
                            .photoName(e.getPhotoName())
                            .photoType(e.getPhotoType())
                            .build()).toList())
                    .build();
        }
        else {
//            throw new UserNotFoundException();
        }
        return userModel;
    }
}
