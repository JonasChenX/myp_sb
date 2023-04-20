package com.jonas.myp_sb.example.task.main.repository;

import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcsTaskDetailsRepository extends JpaRepository<AcsTaskDetails, Long> {

    Optional<AcsTaskDetails> findByJobId(String jobId);

    List<AcsTaskDetails> findByModelId(String modelId);

    Optional<AcsTaskDetails> findByTaskId(long taskId);
}
