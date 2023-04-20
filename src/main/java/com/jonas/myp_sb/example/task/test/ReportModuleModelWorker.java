package com.jonas.myp_sb.example.task.test;

import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import com.jonas.myp_sb.example.task.main.context.TaskResult;
import com.jonas.myp_sb.example.task.main.model.AcsModelResult;
import com.jonas.myp_sb.example.task.main.repository.AcsModelParameterRepository;
import com.jonas.myp_sb.example.task.main.repository.AcsModelResultRepository;
import com.jonas.myp_sb.example.task.main.worker.AbstractDataLogTaskWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component("reportModuleModelWorker")
@Slf4j
public class ReportModuleModelWorker extends AbstractDataLogTaskWorker {

    private final AcsModelParameterRepository acsModelParameterRepository;

    private final AcsModelResultRepository acsModelResultRepository;

    public ReportModuleModelWorker(AcsModelParameterRepository acsModelParameterRepository, AcsModelResultRepository acsModelResultRepository) {
        this.acsModelParameterRepository = acsModelParameterRepository;
        this.acsModelResultRepository = acsModelResultRepository;
    }

    @Override
    public boolean supports(TaskParameter parameter) {
        return parameter instanceof ReportModuleModelTaskParameter;
    }

    // to do -> 1
    @Override
    public void persistParameter(long taskId, TaskParameter parameter) {
        System.err.println("AAAAA");
        log.info("TaskParameter --> " + parameter.toString());
        ReportModuleModelTaskParameter reportModuleModelPBITaskParameter = (ReportModuleModelTaskParameter) parameter;
        reportModuleModelPBITaskParameter.setTaskId(taskId);
        acsModelParameterRepository.save(reportModuleModelPBITaskParameter);
    }

    // 2
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public TaskParameter retrieveParameter(long taskId) {
        System.err.println("BBBBB");
        return acsModelParameterRepository.findById(taskId).orElse(null);
    }

    // 3
    @Override
    public TaskResult performWorker(long taskId, TaskParameter parameter) throws Exception {
        System.err.println("CCCCC");
        AcsModelResult reportModuleModelResult = new AcsModelResult();
        reportModuleModelResult.setModelType("2");
        reportModuleModelResult.setResultCount(Float.valueOf(3));
        reportModuleModelResult.setResultPage("4");
        reportModuleModelResult.setParameterDetail("5");
        reportModuleModelResult.setTaskId(Long.valueOf(3));
        reportModuleModelResult.setTaxpayerList("9");

        return reportModuleModelResult;
    }

    // 4
    @Override
    public void persistResult(long taskId, TaskResult result) {
        System.out.println("-----------AAAAAAAAAAAA------------------");
    }

    @Override
    public TaskResult retrieveResult(long taskId) {
        return acsModelResultRepository.findById(taskId).orElse(null);
    }

    @Override
    public void deleteParameter(long taskId) {
        acsModelParameterRepository.deleteById(taskId);
    }
}
