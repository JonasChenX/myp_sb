package com.jonas.myp_sb.example.task.test;

import com.jonas.myp_sb.example.task.main.manager.AcsTaskManager;
import com.jonas.myp_sb.example.task.main.mapper.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class prepareTaskTest {

    @Autowired
    private AcsTaskManager acsTaskManager;

    @GetMapping("/taskTest")
    public String test(){
        ReportModuleModelTaskParameter reportModuleModelTaskParameter = new ReportModuleModelTaskParameter();
        reportModuleModelTaskParameter.setModelId("modelId");
        reportModuleModelTaskParameter.setParameters("parameters");

        Task task = acsTaskManager.createTask(reportModuleModelTaskParameter, "description", "modelId");

        long taskId = task.getTaskId();
        return "成功"+taskId;
    }
}
