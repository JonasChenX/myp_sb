package com.jonas.myp_sb.example.task.main.worker;

import com.google.common.collect.ImmutableMap;
import com.jonas.myp_sb.example.task.main.context.TaskParameter;
import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@link TaskWorkerRepository} 的實作， 將所有的 TaskWorker 都交由 Spring Framework 的
 * {@link ApplicationContext} 來管理。
 */
@Service("taskWorkerRepository")
public class AppCtxTaskWorkerRepository implements TaskWorkerRepository {
    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(AppCtxTaskWorkerRepository.class);

    private final Map<String, TaskWorker> typeWorkerMap;
    private final Set<String> stagedWorkers;
    private final ApplicationContext applicationContext;

    public AppCtxTaskWorkerRepository(Map<String, TaskWorker> workerMap, ApplicationContext applicationContext) {
        for (Map.Entry<String, TaskWorker> entry : workerMap.entrySet()) {
            String workerName = entry.getKey();
            if (workerName.isEmpty() || workerName.length() > AcsTaskDetails.TYPE_LENGTH_LIMIT) {
                throw new RuntimeException("TaskWorker "+entry.getValue().getClass().getName()+" 的名稱 "+workerName+" 無效，名稱長度限制為 "+AcsTaskDetails.TYPE_LENGTH_LIMIT+" 個字元的作業。");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Loaded task workers {}.", workerMap.keySet());
        }
        this.typeWorkerMap = ImmutableMap.copyOf(workerMap);

        this.stagedWorkers =
                typeWorkerMap
                        .entrySet()
                        .stream()
                        .filter(entry -> AnnotationUtils.findAnnotation(entry.getValue().getClass(), Staged.class) != null)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toSet());

        this.applicationContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSupportedWorker(TaskParameter parameter) {
        if (parameter == null) {
            throw new RuntimeException("TaskParameter 參數不能為 null");
        }
        Set<String> candidateWorkers = new HashSet<>();
        for (Map.Entry<String, TaskWorker> workerEntry : typeWorkerMap.entrySet()) {
            if (workerEntry.getValue().supports(parameter)) {
                candidateWorkers.add(workerEntry.getKey());
            }
        }

        if (candidateWorkers.isEmpty()) {
            throw new RuntimeException("找不到可以處理 "+parameter.getClass().getName()+" 參數的 TaskWorker");
        }

        if (candidateWorkers.size() > 1) {
            throw new RuntimeException("預期只有 1 個 TaskWorker 可以處理 "+parameter.getClass().getName()+" 參數，但是找到了: "+candidateWorkers);
        }

        return candidateWorkers.iterator().next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskWorker getWorkerInstance(String workerName) {
        return applicationContext.getBean(workerName, TaskWorker.class);
    }

    @Override
    public boolean isStaged(String workerName) {
        return stagedWorkers.contains(workerName);
    }
}
