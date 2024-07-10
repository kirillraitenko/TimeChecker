package org.t1.timechecker.services.logtime;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;
import org.t1.timechecker.dao.ILogTimeDao;
import org.t1.timechecker.entity.LogTime;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LogTimeService {

    private final ILogTimeDao repository;

    public LogTimeService(ILogTimeDao repository) {
        this.repository = repository;
    }

    public LogTime saveLogTime(LogTime logTime) {
        try {
            return repository.save(logTime);
        } finally {
            log.info("Тип {}, Метод {};", logTime.getType(), logTime.getMethod());
        }
    }

    public static LogTime createLogTime(ProceedingJoinPoint joinPoint, long startTime, long executionTime) {
        var signature = joinPoint.getSignature();
        return createLogTime(signature.getDeclaringTypeName(), signature.getName(), startTime, executionTime);
    }

    public static LogTime createLogTime(String nameType, String nameMethod, long startTime, long executionTime) {
        return LogTime.builder()
                .type(nameType)
                .method(nameMethod)
                .startTime(new Timestamp(startTime))
                .duration(executionTime)
                .build();
    }

    public Double getAverageDurationByMethod(String method) {
        return repository.avgDurationByMethod(method);
    }

    public Long getMinDurationByMethod(String method) {
        return repository.minDurationByMethod(method);
    }

    public Long getMaxDurationByMethod(String method) {
        return repository.maxDurationByMethod(method);
    }

    public Long countMethodСallsByNameMethod(String method) {
        return repository.countMethodСallsByNameMethod(method);
    }

    public List<String> getAllMethods() {
        return repository.getAllMethods();
    }

    public List<String> getAllMethodByType(String type) {
        return repository.getAllMethodByType(type);
    }

    public List<String> getAllTypes() {
        return repository.getAllTypes();
    }

    public Map<String, Object> getAllMethodStatistics(String method) {
        return Map.of(
                "count", countMethodСallsByNameMethod(method),
                "avg", getAverageDurationByMethod(method),
                "min", getMinDurationByMethod(method),
                "max", getMaxDurationByMethod(method)
        );
    }

    /**
     * Получение всей статистики по всем типам и методам
     */
    public Map<String, Object> getAllStatistics() {
        Map<String, Object> resultMapStatistics = new HashMap<>();

        var types = getAllTypes();
        for (var type : types) {
            var mapMethodStatistics = this.getAllMethodByType(type).stream()
                    .collect(Collectors.toMap(
                                    (method) -> method,
                                    this::getAllMethodStatistics
                            )
                    );
            resultMapStatistics.put(type, mapMethodStatistics);
        }
        return resultMapStatistics;
    }
}
