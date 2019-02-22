package quartz.main;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;

import quartz.job.HelloWorldJob;
import quartz.listen.HelloJobListener;
import quartz.listen.HelloTriggerListener;

public class TriggerListenerDemo {

	public static void main(String[] args) throws SchedulerException {
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
				 						 .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(15))
				 						 .build();
		 //触发器的监听先于任务的监听执行
		 //触发器的监听可以阻止任务执行
		 //正确执行的顺序如下：
		 //1:triggerListener的fire方法
		 //2:triggerListener的vote方法（返回true,则job调度被阻断，直接调用JobListener的Voted方法，然后结束）
		 //3：jobListener的ToBeExecuted方法
		 //4：job的execute方法
		 //5：jobListener的wasExecuted方法
		 //6：triggerListener的Complete方法
		 
		 //触发器监听的注册和任务监听的注册类似，可以注册全局和局部监听器
		 scheduler.scheduleJob(jobDetail, trigger);
		 scheduler.getListenerManager().addJobListener(new HelloJobListener(),EverythingMatcher.allJobs());
		 scheduler.getListenerManager().addTriggerListener(new HelloTriggerListener(),EverythingMatcher.allTriggers());
		 scheduler.start();
	}
}
