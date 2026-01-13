package org.recruitert.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.recruitert.models.JobPostingExtractor;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ScrapingAspect {
    @Around("execution(* org.recruitert.models.JobPosting.from(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (final Exception e) {
            final JobPostingExtractor extractor = (JobPostingExtractor) joinPoint.getArgs()[0];
            System.out.println("Exception was raised while trying to obtain data from " + extractor.url());
            throw e;
        }
    }
}
