package com.jonas.myp_sb.example.task.main.service;

import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import com.jonas.myp_sb.example.task.main.repository.AcsTaskDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
public class AcsTaskDetailsService {

    private final AcsTaskDetailsRepository acsTaskDetailsRepository;

    public AcsTaskDetailsService(AcsTaskDetailsRepository acsTaskDetailsRepository) {
        this.acsTaskDetailsRepository = acsTaskDetailsRepository;
    }

    public AcsTaskDetails findById(Long taskId) {
        return acsTaskDetailsRepository.findById(taskId).orElseThrow(() -> new RuntimeException("找不到代碼為"+ taskId +"的作業"));
    }

    public AcsTaskDetails findByJobId(String jobId) {
        return acsTaskDetailsRepository.findByJobId(jobId).orElseThrow(() -> new RuntimeException("找不到代碼為"+ jobId +"的作業") );
    }

    @Transactional(propagation = REQUIRED)
    public AcsTaskDetails save(AcsTaskDetails details) {
        return acsTaskDetailsRepository.saveAndFlush(details);
    }
}
