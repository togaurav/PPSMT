package com.ppstream.mt.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component("logIntercepter")
@Aspect
public class LogIntercepter {
	
	/**
	 * spring aop 的前置通知、后置通知、异常通知、环绕通知，当进入service包或子包下的所有方法时他们都会起作用,其中异常通知，只有在该方法出现异常时才会调用
	 */
	
//	@Before("execution(* service..*.*(..))")
//	public void before(){
//        System.out.println("----------before-------------");
//    }
//    
//	@After("execution(* service..*.*(..))")
//    public void after(){
//        System.out.println("----------after-------------");
//    }
//    
//	@AfterThrowing("execution(* service..*.*(..))")
//    public void exception(){
//        System.out.println("----------exception-------------");
//    }
//    
//	@Around("execution(* service..*.*(..))")
//    public void around(){
//        System.out.println("----------around-------------");
//    }
}
