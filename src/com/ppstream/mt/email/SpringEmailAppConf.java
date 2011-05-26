package com.ppstream.mt.email;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SpringEmailAppConf {

	private @Value("${email.host}") String emailHost;

    private @Value("${email.username}") String userName;

    private @Value("${email.password}") String password;

    private @Value("${email.from}") String from;

    private @Value("${email.to}") String to;

    private @Value("${mail.smtp.auth}") String mailAuth;
	
    Logger log = LoggerFactory.getLogger(SpringEmailAppConf.class);
    
    public @Bean MailSender mailSender(){
       Properties pp = new Properties();
    	
       JavaMailSenderImpl ms = new JavaMailSenderImpl();

       ms.setHost(emailHost);

       ms.setUsername(userName);

       ms.setPassword(password);

       pp.setProperty("mail.smtp.auth", mailAuth);

       ms.setJavaMailProperties(pp);

       return ms;   

    }

   

    public @Bean SimpleMailMessage mailMessage(){

       SimpleMailMessage sm = new SimpleMailMessage();

       sm.setFrom(from);

       sm.setTo(to);

       return sm;

    }

   

    public @Bean SpringEmailService springEmailService(){
       return new SpringEmailService();
    }
}
