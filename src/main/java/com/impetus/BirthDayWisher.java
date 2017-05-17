package com.impetus;

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

import com.impetus.bwisher.quartz.EmailSenderJob;

@SpringBootApplication
@ComponentScan(basePackages = { "com.impetus" })
public class BirthDayWisher {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(BirthDayWisher.class, args);
		ScheduleJob();
		clenup();
	}

	private static void ScheduleJob() throws SchedulerException, InterruptedException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler sched = schedulerFactory.getScheduler();
		JobDetail job = JobBuilder.newJob(EmailSenderJob.class).withIdentity("job1", "group1").build();
		// Daily
		/*
		 * Trigger trigger =
		 * TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
		 * .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(7,
		 * 0)).startAt(new Date()).build();
		 */

		// Every 60 sec
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/60 * * * * ?")).startAt(new Date()).build();

		sched.scheduleJob(job, trigger);
		sched.start();
		System.out.println(trigger.getNextFireTime());
		Thread.sleep(90L * 1000L);
	}

	private static void clenup() {
	}
}
