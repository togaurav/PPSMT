package com.ppstream.mt.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ImportResource("classpath:emailConfig.xml")
public class SpringEmailAppConf {

	private @Value("${email.host}") String emailHost;

    private @Value("${email.username}") String userName;

    private @Value("${email.password}") String password;

    private @Value("${email.from}") String from;

    private @Value("${email.to}") String to;

    private @Value("${mail.smtp.auth}") String mailAuth;

   

    public @Bean MailSender mailSender(){

       JavaMailSenderImpl ms = new JavaMailSenderImpl();

       ms.setHost(emailHost);

       ms.setUsername(userName);

       ms.setPassword(password);

       Properties pp = new Properties();

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
