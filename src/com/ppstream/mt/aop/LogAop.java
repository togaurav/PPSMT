package com.ppstream.mt.aop;

import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component("logAop")
@Aspect 
public class LogAop {
	
	/**
	 * spring aop 的前置通知、后置通知、异常通知、环绕通知，当进入action包或子包下的所有方法时他们都会起作用,其中异常通知，只有在该方法出现异常时才会调用
	 * advice是你想向别的程序内部不同的地方注入的代码
	 * pointcut定义了需要注入advice的位置，通常是某个特定的类的一个public方法
	 * advisor是pointcut和advice的装配器，是将advice注入主程序中预定义位置的代码
	 * 
	 * 第一个*表示返回值为任意类型
	 * com.ppstream.mt.action..  两个点表示包路径下的子包的类也要拦截
	 * com.ppstream.mt.action..*.*   子包的所有类中的所有方法,第一个*表示方法,第二个*是类
	 * (..) 代表方法参数随意,可有可无可多可少
	 */

	/** 
	 * 定义控制器方法切入点
     */
	@Pointcut("execution(* com.ppstream.mt.action..*.*(..))")
    public void inActionMethod(){} 
	
	/** 
	 * 定义业务方法切入点
     */
	@Pointcut("execution(* com.ppstream.mt.service..*.*(..))")
    public void inServiceMethod(){} 
	
	
	/** 
	 * 定义总切入点
     */
    @Pointcut("inActionMethod() && inServiceMethod()")
    public void supportAOP(){} 
	
    /** 
     * 切入点执行范围
     * @param pjp       切入点
     * @throws Throwable 切入点抛出的异常
     */
    @Around("inActionMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable
    {
        Logger log = Logger.getLogger(pjp.getTarget().getClass());
        StringBuilder sb = new StringBuilder();
        sb.append("[")
                .append(pjp.getTarget().getClass().getName())
                .append(".")
                .append(pjp.getSignature().getName())
                .append("]");
       
        long begin = System.currentTimeMillis();
        sb.append("开始时间：[").append(new Date().toString()).append("]");
        System.out.println(sb.toString());
        //实际方法执行           -------------------  [增： 在此权限控制]
        Object result = pjp.proceed();  // pjp.getArgs()
       
        long end = System.currentTimeMillis();
        sb.append("结束时间：[")
                .append(new Date().toString())
                .append("]");
        sb.append("共耗费：[").append((end - begin)).append("ms]");
        log.info(sb.toString());
        return result;
    }
   
    /** 
     * 切入点抛出异常
     * @param jp        切入点
     * @param ex        抛出的异常
     */
    @AfterThrowing(pointcut = "supportAOP()", throwing = "ex")
    public void doThrowing(JoinPoint jp, Throwable ex)
    {
    	System.out.println("异常");
        Logger log = Logger.getLogger(jp.getTarget().getClass());
        log.error(ex.getMessage(), ex);
    } 
}
