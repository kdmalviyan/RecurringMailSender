package com.kd.example.mailing.bwisher.quartz;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.apache.velocity.exception.VelocityException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.kd.example.mailing.bwisher.beans.EmailNotificationService;
import com.kd.example.mailing.bwisher.beans.PivotService;
import com.kd.example.mailing.bwisher.beans.config.ConfigBeans;
import com.kd.example.mailing.bwisher.helper.EmailHelper;

@Service
public class EmailSenderJob implements Job {

	static Logger logger = LoggerFactory
			.getLogger(EmailSenderJob.class);

	/**
	 * Execute actual logic of job, Here it iterating over all available
	 * employees for a specific day and send birrthday mail to all of them.
	 * 
	 * @param context
	 */
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		ConfigBeans beans = new ConfigBeans();
		EmailNotificationService emailNotificationService = null;
		try {
			emailNotificationService = new EmailNotificationService(
					beans.getMailSender(), beans.getVelocityEngine());
			PivotService pivotService = new PivotService();
			ArrayNode employeesJson = pivotService.getEmployees(new Date());
			Iterator<JsonNode> itr = employeesJson.elements();
			while (itr.hasNext()) {
				JsonNode employee = itr.next();
				emailNotificationService.sendEmail(EmailHelper
						.prepareBirthDayEmail(employee));
			}
		} catch (VelocityException | IOException ex) {
			logger.error("error :" + ex);
			throw new JobExecutionException("Email sending failed.", ex);
		}
	}
}
