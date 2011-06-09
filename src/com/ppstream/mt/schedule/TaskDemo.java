package com.ppstream.mt.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
 * @author liupeng
 * 
 */
@Component
public class TaskDemo {
	
//	@Scheduled(fixedDelay = 5000)
//	void doSomethingWithDelay() {
//		System.out.println("I'm doing with delay now!");
//	}

//	@Scheduled(fixedRate = 5000)
//	void doSomethingWithRate() {
//		System.out.println("I'm doing with rate now!");
//	}

	@Scheduled(cron = "0 13 11 * * ?")
	void doSomethingWith() {
		System.out.println("I'm doing with cron now!");
	}
}
