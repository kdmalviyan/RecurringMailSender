package com.kd;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.kd.bwisher.quartz.EmailSenderJob;

@SpringBootApplication
@ComponentScan(basePackages = { "com.kd" })
public class BirthDayWisher {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BirthDayWisher.class, args);
		ScheduleJob();
		clenup();
	}

	private static void ScheduleJob() throws SchedulerException, InterruptedException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler sched = schedulerFactory.getScheduler();
		JobDetail job = JobBuilder.newJob(EmailSenderJob.class).withIdentity("job", "group").build();
		// Daily
		/*
		 * Trigger trigger =
		 * TriggerBuilder.newTrigger().withIdentity("trigger", "group")
		 * .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(7,
		 * 0)).startAt(new Date()).build();
		 */

		// Every 60 sec
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).startAt(new Date()).build();

		sched.scheduleJob(job, trigger);
		sched.start();
		System.out.println(trigger.getNextFireTime());
		Thread.sleep(90L * 1000L);
	}

	private static void clenup() {
	}
}
