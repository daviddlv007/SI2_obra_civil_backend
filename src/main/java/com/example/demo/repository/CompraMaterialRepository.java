package com.example.demo.repository;

import com.example.demo.entity.CompraMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraMaterialRepository extends JpaRepository<CompraMaterial, Long> {
}
