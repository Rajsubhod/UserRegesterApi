package com.user.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoId;
    private String photoName;
    private String photoType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "user_id")
    private User user;
}
