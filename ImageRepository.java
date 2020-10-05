package com.JpaEx1_example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JpaEx1_example.demo.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer>{

}
