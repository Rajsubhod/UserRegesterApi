package com.user.api.repository;

import com.user.api.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo,Integer> {
    Optional<Photo> findByPhotoName(String fileName);
}
