package org.t1.timechecker.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.t1.timechecker.entity.LogTime;
import org.t1.timechecker.services.logtime.LogTimeService;

public abstract class AbstractTimeCheckerAspect {

    protected LogTimeService logTimeService;
    public AbstractTimeCheckerAspect(LogTimeService logTimeService){
        this.logTimeService = logTimeService;
    }
    public Object executeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object val = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        save(LogTimeService.createLogTime(joinPoint, startTime, executionTime));
        return val;
    }

    abstract void save(LogTime logTime);
}
