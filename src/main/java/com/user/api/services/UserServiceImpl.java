package com.user.api.services;

import com.sun.jdi.InternalException;
import com.user.api.entities.Name;
import com.user.api.entities.Photo;
import com.user.api.entities.User;
import com.user.api.repository.PhotoRepository;
import com.user.api.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

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
            try {
                if(fileName.contains("..")){
                    throw new InvalidFileNameException(fileName," file contains invalid path sequence ");
                }

                Photo photo =  Photo.builder()
                        .photoName(fileName)
                        .photoType(p.getContentType())
                        .data(p.getBytes())
                        .user(user)
                        .build();
                photoRepository.save(photo);
                return photo;
            }catch (Exception e){
                throw new InternalException();
            }
        }).toList();

        return user;
    }
}
