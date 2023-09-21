package com.user.api.model;

import com.user.api.entities.Photo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {

    private String name;
    private String email;
    private List<PhotoModel> photo;

}
