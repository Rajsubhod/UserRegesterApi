package com.user.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoModel {

    private String photoName;
    private String photoType;
    private String downloadURL;
    private String fileSize;

}
