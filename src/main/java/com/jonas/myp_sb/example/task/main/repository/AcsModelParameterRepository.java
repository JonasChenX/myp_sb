package com.jonas.myp_sb.example.task.main.repository;

import com.jonas.myp_sb.example.task.main.model.AcsModelParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcsModelParameterRepository extends JpaRepository<AcsModelParameter, Long> {
    List<AcsModelParameter> findByTaskId(long taskId);
}
