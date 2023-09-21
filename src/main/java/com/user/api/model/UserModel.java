package com.user.api.model;

import com.user.api.entities.Name;
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

    private Name name;
    private String email;
    private List<PhotoModel> photo;

}
