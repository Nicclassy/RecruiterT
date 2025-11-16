package org.recruitert.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StringAspect {
    @Pointcut("within(org.recruitert..*) && execution(public String *.*(..))")
    private void stringReturning() {}

    @After("stringReturning()")
    public void after(JoinPoint joinPoint) {
        System.out.printf(
            "Method %s was called and returned a string%n",
            joinPoint.getSignature().getName()
        );
    }
}
