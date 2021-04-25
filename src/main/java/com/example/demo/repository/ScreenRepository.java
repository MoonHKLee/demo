package com.example.demo.repository;

import com.example.demo.domain.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

    Screen findScreenById(Long ScreenId);

}
