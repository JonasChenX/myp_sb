package com.jonas.myp_sb.workTest;

import com.jonas.myp_sb.example.task.main.model.AcsModelParameter;
import com.jonas.myp_sb.example.task.main.model.AcsModelResult;
import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import com.jonas.myp_sb.example.task.main.repository.AcsModelParameterRepository;
import com.jonas.myp_sb.example.task.main.repository.AcsModelResultRepository;
import com.jonas.myp_sb.example.task.main.repository.AcsTaskDetailsRepository;
import com.jonas.myp_sb.example.task.test.ReportModuleModelTaskParameter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class work {

    @Autowired
    private AcsTaskDetailsRepository acsTaskDetailsRepository;

    @Autowired
    private AcsModelResultRepository acsModelResultRepository;

    @Autowired
    private AcsModelParameterRepository acsModelParameterRepository;

    @Test
    public void test1(){
        Optional<AcsTaskDetails> byTaskId = acsTaskDetailsRepository.findByTaskId(1);
        System.out.println(byTaskId);
    }

    @Test
    public void test2(){
        Optional<AcsModelResult> byTaskId = acsModelResultRepository.findByTaskId(1);
        System.out.println(byTaskId);
    }

    @Test
    public void test3(){
//        List<AcsModelParameter> byTaskId = acsModelParameterRepository.findByTaskId(1);
//        AcsModelParameter acsModelParameter = byTaskId.get(0);
//        System.out.println(acsModelParameter);
//        System.out.println(acsModelParameter.getModelId());

        ReportModuleModelTaskParameter reportModuleModelPBITaskParameter = new ReportModuleModelTaskParameter();

        reportModuleModelPBITaskParameter.setParameters("112");
        reportModuleModelPBITaskParameter.setModelId("modelId");
        reportModuleModelPBITaskParameter.setTaskId(1L);

        acsModelParameterRepository.save(reportModuleModelPBITaskParameter);
    }
}
