package org.t1.timechecker.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.t1.timechecker.entity.LogTime;
import org.t1.timechecker.services.logtime.LogTimeService;

import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Slf4j
public class SyncAbstractTimeCheckerAspect extends AbstractTimeCheckerAspect {

    public SyncAbstractTimeCheckerAspect(LogTimeService logTimeService){
        super(logTimeService);
    }

    @Around("@annotation(org.t1.timechecker.annotation.TrackTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return super.executeMethod(joinPoint);
    }

    @Override
    void save(LogTime logTime) {
        LogTime lg = logTimeService.saveLogTime(logTime);
        log.info("Тип: {} метод: {} время вызова: {}, длительность: {};", lg.getType(), lg.getMethod(), lg.getStartTime(), lg.getDuration());
    }
}
