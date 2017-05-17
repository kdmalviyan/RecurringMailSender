package com.kd.bwisher.quartz;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.apache.velocity.exception.VelocityException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.kd.bwisher.beans.EmailNotificationService;
import com.kd.bwisher.beans.PivotService;
import com.kd.bwisher.beans.config.ConfigBeans;
import com.kd.bwisher.helper.EmailHelper;

@Service
public class EmailSenderJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		ConfigBeans beans = new ConfigBeans();
		EmailNotificationService emailNotificationService = null;
		try {
			emailNotificationService = new EmailNotificationService(beans.getMailSender(), beans.getVelocityEngine());
			PivotService pivotService = new PivotService();
			ArrayNode employeesJson = pivotService.getEmployees(new Date());
			Iterator<JsonNode> itr = employeesJson.elements();
			while(itr.hasNext()){
				JsonNode employee = itr.next();
				emailNotificationService.sendEmail(EmailHelper.prepareBirthDayEmail(employee));
			}
		} catch (VelocityException | IOException e) {
			e.printStackTrace();
		}
	}
}
