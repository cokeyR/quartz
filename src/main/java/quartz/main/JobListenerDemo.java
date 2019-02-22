package quartz.main;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import quartz.job.ContextJob;
import quartz.listen.HelloJobListener;

public class JobListenerDemo {
	public static void main(String[] args) throws SchedulerException, InterruptedException {
		//schedule
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		//trigger
		Trigger trigger=TriggerBuilder.newTrigger()
									  .startNow()		 //设置立即开始，可用startAt()指定开始时间  
									  .withDescription("我是一个触发器")//触发器的描述
									  .withIdentity("triger1","groupT1")//触发器的名称和组名，组名默认为DEFAULT
									  .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))//设置调度计划
									  .usingJobData("key","I am a trigger value")
									  .build();
		
		//jobDetail
		JobDetail jobDetail=JobBuilder.newJob(ContextJob.class)//每次调度都会新建一个实例
									  .withDescription("我是一个Job")
									  .withIdentity("job1", "groupJ1")
									  .usingJobData("key", "I am a jobDetail value")
									  .usingJobData("times", 0)
									  .build();
		
		
		//启动调度
		scheduler.scheduleJob(jobDetail, trigger);
		
		//添加一个全局Joblistener（和所有job匹配）
		//scheduler.getListenerManager().addJobListener(new HelloListenner(),EverythingMatcher.allJobs());
		
		//添加一个局部Joblistener（根据jobKey匹配）
		scheduler.getListenerManager().addJobListener(new HelloJobListener(),KeyMatcher.keyEquals(jobDetail.getKey()));
		
		scheduler.start();
		
	}

}
