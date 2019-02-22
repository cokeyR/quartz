package quartz.listen;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class HelloJobListener implements JobListener{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "hello world,I am a job listenner";
	}

	//job被执行前被调用，如果job被阻断，那么该方法不会被调用，~~Voted方法将被调用
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println(context.getJobDetail().getKey().getName()+"将被执行");
		
	}
	//job被阻断时被调用（TriggerListener可以阻断job的执行）
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		System.out.println(context.getJobDetail().getKey().getName()+"被阻止执行");
		
	}
	
	//job被执行完成后被调用，如果job被阻断，也就不会得到执行，也就不会被执行完成，此方法也就不会被调用
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// TODO Auto-generated method stub
		System.out.println(context.getJobDetail().getKey().getName()+"执行完毕");
		
	}

}
