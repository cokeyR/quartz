package quartz.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Trigger;

@PersistJobDataAfterExecution//在周期任务中，如果使用此注解，则每次调度都使用同一个jobDateMap（有状态）。否则每次都会新建一个jobDateMap（无状态）
public class ContextJob implements Job{
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//scheduler在根据trigger调用该任务时，传入context对象
		//scheduler持有trigger对象和JobDetail对象
		//每个job对应一个JobDetail对象。JobDetail包含一些Job的数据，如可以定义器任务名，组名，持有一些数据等
		//context对象持有JobDetail对象和trigger对象
		//context对象被传入Job中，以此实现了job，trigger之间的通信
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//1:获取trigger和jobDetail
		
		Trigger trigger = context.getTrigger();
		JobDetail jobDetail=context.getJobDetail();
		int times = jobDetail.getJobDataMap().getInt("times");
		System.err.println("第"+times+"次被调用");
		times++;
		jobDetail.getJobDataMap().put("times", times);
		
		//2:获取调度时设置的数据
		
		//JobDetail.getKey()方法返回当前jobDetail的标识对象，可以设置和获取name,group等信息
		//Trgger.getKey()方法与上类似
		String jobDetailname=jobDetail.getKey().getName();
		String triggername=trigger.getKey().getName();
		System.out.println("jobName:"+jobDetailname);
		System.out.println("triggername:"+triggername);

		//jobDetall.getJobDateMap()返回一个对象，该对象是Map的子类，可以设置和获取里面的值（
		//trigger.getJobDateMap()与上类似
		System.out.println("任务key数据"+jobDetail.getJobDataMap().getString("key"));
		System.out.println("触发器key数据："+trigger.getJobDataMap().getString("key"));
		
		//context中还包含了许多其他的信息。如任务的执行时间、下次执行时间等
		
		System.out.println("上次触发时间："+context.getPreviousFireTime());
		System.out.println("本次触发时间："+context.getFireTime());
		System.out.println("下次触发时间："+context.getNextFireTime());
	}

}
