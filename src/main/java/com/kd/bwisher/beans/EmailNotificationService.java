package com.kd.bwisher.beans;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.kd.bwisher.helper.EmailHelper;

@Service("mailService")
public class EmailNotificationService {

	JavaMailSender mailSender;

	VelocityEngine velocityEngine;

	public EmailNotificationService(JavaMailSender mailSender, VelocityEngine velocityEngine) {
		this.mailSender = mailSender;
		this.velocityEngine = velocityEngine;
	}

	public void sendEmail(Employee birthdayEmail) {

		MimeMessagePreparator preparator = EmailHelper.getMessagePreparator(velocityEngine, birthdayEmail);
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			ex.printStackTrace();
		}
	}
}