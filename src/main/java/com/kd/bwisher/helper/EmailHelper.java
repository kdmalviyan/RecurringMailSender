package com.kd.bwisher.helper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.kd.bwisher.beans.Employee;

public class EmailHelper {
	public static MimeMessagePreparator getMessagePreparator(VelocityEngine velocityEngine,
			final Employee birthdayEmail) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
				messageHelper.setSubject(birthdayEmail.getSubject());
				messageHelper.setFrom("kuldeep.singh@impetus.co.in");
				messageHelper.setTo(birthdayEmail.getEmailId());
				Map<String, Object> model = new HashMap<>();
				model.put("name", birthdayEmail.getName());
				model.put("picUrl", birthdayEmail.getPicUrl());
				messageHelper.setText(geVelocityTemplateContent(velocityEngine, model, birthdayEmail.getTemplateName()),
						true);
			}
		};
		return preparator;
	}

	public static String geVelocityTemplateContent(VelocityEngine velocityEngine, Map<String, Object> model,
			String templateName) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model));
			return content.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Employee prepareBirthDayEmail(JsonNode employee) {
		Employee birthdayEmail = new Employee();
		birthdayEmail.setEmailId(employee.get("emailId").asText());
		birthdayEmail.setName(employee.get("name").asText());
		birthdayEmail.setPicUrl(employee.get("picUrl").asText());
		birthdayEmail.setSubject("Happy Birth Day...");
		birthdayEmail.setTemplateName(selectTemplateName());
		return birthdayEmail;
	}

	private static String selectTemplateName() {
		String templatePath = "mail/templates";
		File file = new File(EmailHelper.class.getClassLoader().getResource(templatePath).getFile());
		File[] templates = null;
		if (file.isDirectory()) {
			templates = file.listFiles();
			int index = RandomUtils.nextInt(templates.length - 1);
			return "/" + templatePath + "/" + templates[index].getName();
		} else {
			return "/mail/templates/default/default.vm";
		}
	}
}
