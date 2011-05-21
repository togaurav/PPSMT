package com.ppstream.mt.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SpringEmailService {
	
	private MailSender emailSender;
    private SimpleMailMessage mailMessage;

    public SimpleMailMessage getMailMessage() {
       return mailMessage;
    }

    @Autowired
    public void setMailMessage(SimpleMailMessage mailMessage) {
       this.mailMessage = mailMessage;
    }

    public MailSender getEmailSender() {
       return emailSender;
    }

    @Autowired
    public void setEmailSender(MailSender emailSender) {
       this.emailSender = emailSender;
    }
    

    public void sendAMessage(String subject,String text){
       mailMessage.setText(text);
       mailMessage.setSubject(subject);
       emailSender.send(mailMessage);
    }

}
