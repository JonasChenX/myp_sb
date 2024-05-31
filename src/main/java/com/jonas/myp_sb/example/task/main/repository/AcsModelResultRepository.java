package com.jonas.myp_sb.example.task.main.repository;

import com.jonas.myp_sb.example.task.main.model.AcsModelResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcsModelResultRepository extends JpaRepository<AcsModelResult, Long> {
    Optional<AcsModelResult> findByTaskId(long taskId);
}
