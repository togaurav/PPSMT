package com.ppstream.mt.aop;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.ppstream.mt.bean.GPrivilege;

//@Component("logAop")
//@Aspect 
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
	 * 
	 * execution(* com.ppstream.mt.action.PrivilegeAdmin.*(..))    com.ppstream.mt.action.PrivilegeAdmin类下任意方法的执行
	 */

	/** 
	 * 定义控制器方法切入点
     */
	@Pointcut("execution(* com.ppstream.mt.action.PrivilegeAdmin.*(..)) || execution(* com.ppstream.mt.action.RoleAdmin.*(..)) || execution(* com.ppstream.mt.action.UserAdmin.*(..))")
    public void inActionMethod(){} 

	
    /** 
     * 切入点执行范围
     * @param pjp       切入点
     * @throws Throwable 切入点抛出的异常
     */
    @Around("inActionMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable
    {
//        Logger log = Logger.getLogger(pjp.getTarget().getClass());
        Logger log = LoggerFactory.getLogger(pjp.getTarget().getClass());
        // 动作的名称以及时间
        StringBuilder sb = new StringBuilder();
        sb.append("[")
                .append(pjp.getTarget().getClass().getName())
                .append(".")
                .append(pjp.getSignature().getName())
                .append("]");
        ActionContext ac = ActionContext.getContext();
        // 当前执行的action
        StringBuilder actionSB = new StringBuilder();
        actionSB.append("/default/").append(pjp.getSignature().getName()).append(".action");
        String action = actionSB.toString();
      
        HttpSession session = ServletActionContext.getRequest().getSession();
        // 登录验证 (默认30分钟,session清空)
        if(session.getAttribute("userId") == null || 
        		(session.getAttribute("userId") != null && "".equalsIgnoreCase(session.getAttribute("userId").toString()))){	
        	ac.put("loginTips", "请先登录！");      
        	log.debug("登录拦截： " + sb.toString());
            return Action.LOGIN;  
        }
        // 最后请求时间超过20分钟,重新登录
        long lastAccessTime = session.getLastAccessedTime(); // 会话时间内web容器接收到客户最后发出的请求的时间
        long nowTime = new Date().getTime();
        long seconds = (nowTime - lastAccessTime) / 1000;
        if(seconds >= 1200){   
        	ac.put("loginTips", "您已很长时间未操作本系统，请重新登录！");      
        	log.debug("20分钟内未进行操作,拦截： " + sb.toString());
            return Action.LOGIN;  
        }
        // 权限集合
        HashMap<String, HashMap<String, HashSet<GPrivilege>>> maps = (HashMap<String, HashMap<String, HashSet<GPrivilege>>>)session.getAttribute("maps");
        boolean flag = hasPrivilege(maps,action);
        Object result = null;
        if(true){ // 权限拦截 flag
        	try{  
        		// 方法执行
        		result = pjp.proceed(pjp.getArgs());
        	}catch(Exception ex){  // 全局异常处理
            	ac.put("exceptionTips", "程序发生异常！");   
            	log.error(sb.toString() + "Exception : " + ex.getMessage());
        		return Action.INPUT;
        	}
        }else{
        	ac.put("privilegeTips", "您无此权限，请联系上级主管！");      
        	log.debug("权限拦截： " + sb.toString());
            return Action.INPUT;  
        }
        log.info(sb.toString());
        return result;
    }
   
    public boolean hasPrivilege(HashMap<String, HashMap<String, HashSet<GPrivilege>>> maps,String action){
    	boolean flag = false; // 默认是无权限的
        // 遍历
        Set<String> typeSet = maps.keySet();
        Iterator<String> typeIterator = typeSet.iterator();
        while(typeIterator.hasNext()){
        	String type = typeIterator.next();
        	HashMap<String, HashSet<GPrivilege>> cateMaps = maps.get(type);
        	Collection<HashSet<GPrivilege>> gPrivileges = cateMaps.values();
        	Iterator<HashSet<GPrivilege>> gPrivilegeSetIterator = gPrivileges.iterator();
        	while(gPrivilegeSetIterator.hasNext()){
        		HashSet<GPrivilege> gPrivilegeSet = gPrivilegeSetIterator.next();
        		Iterator<GPrivilege> gPrivilegeIterator = gPrivilegeSet.iterator();
        		while(gPrivilegeIterator.hasNext()){
        			GPrivilege gPrivilege = gPrivilegeIterator.next();
        			if(action.equalsIgnoreCase(gPrivilege.getAction())){
        				flag = true;
        			}
        		}
        	}
        }
        return flag;
    }
    
}
