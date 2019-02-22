package quartz.main;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import quartz.job.HelloWorldJob;

public class HelloScheduleDemo {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		//1:调度器，从工厂中获得
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		//2:任务实例
		JobDetail jobDetail = JobBuilder.newJob(HelloWorldJob.class)
										.withIdentity("job1", "group1")					
										.build();
		SimpleScheduleBuilder.simpleSchedule();
		//3：触发器
		 Trigger trigger = TriggerBuilder.newTrigger()
				 						 .withIdentity("trigger1","group1")
				 						 .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2))
				 						 .build();
		 
		 //4:启动调度
		 scheduler.scheduleJob(jobDetail, trigger);
		 scheduler.start();
		 Thread.sleep(3000);
		 
	}
}
