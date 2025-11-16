package org.recruitert.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import org.recruitert.utils.StringUtils;

@Aspect
@Component
public class StringAspect {
    @Pointcut("within(org.recruitert..*) && execution(public String *.*(..))")
    private void stringReturning() {}

    @After("stringReturning()")
    public void after(JoinPoint joinPoint) {
        System.out.println(StringUtils.concatenate(
            "Method",
            joinPoint.getSignature().getName(),
            "was called and returned a string"
        ));
    }
}
