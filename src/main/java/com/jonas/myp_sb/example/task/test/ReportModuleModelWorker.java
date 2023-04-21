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
        log.info("1.persistParameter");
        log.info("TaskParameter --> " + parameter.toString());
        ReportModuleModelTaskParameter reportModuleModelPBITaskParameter = (ReportModuleModelTaskParameter) parameter;
        reportModuleModelPBITaskParameter.setTaskId(taskId);
        acsModelParameterRepository.save(reportModuleModelPBITaskParameter);
    }

    // 2
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public TaskParameter retrieveParameter(long taskId) {
        log.info("2.retrieveParameter");
        return acsModelParameterRepository.findById(taskId).orElse(null);
    }

    // 3
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TaskResult performWorker(long taskId, TaskParameter parameter) throws Exception {
        log.info("3.performWorker");
        AcsModelResult reportModuleModelResult = new AcsModelResult();
        reportModuleModelResult.setModelType("2");
        reportModuleModelResult.setResultCount(Float.valueOf(3));
        reportModuleModelResult.setResultPage("4");
        reportModuleModelResult.setParameterDetail("5");
        reportModuleModelResult.setTaskId(taskId);
        reportModuleModelResult.setTaxpayerList("9");

        return reportModuleModelResult;
    }

    // 4
    @Override
    public void persistResult(long taskId, TaskResult result) {
        log.info("4.persistResult");
        acsModelResultRepository.saveAndFlush((AcsModelResult) result);
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
