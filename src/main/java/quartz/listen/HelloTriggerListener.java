package quartz.listen;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

public class HelloTriggerListener implements TriggerListener{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "TriggerListener YY";
	}

	//触发器先于任务（触发器触发任务），因此触发器监听器先于任务监听器

	//触发器被触发时调用该方法
	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		System.out.println(trigger.getKey().getName()+"被触发了");
	}

	//···Fired方法被调用完成后，此方法会被调用，如果返回true，就会阻断job，jobListener的```voted方法就会被调用
	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		System.out.println(trigger.getKey().getName()+"被投票了，本次通过");
		return true;
	}

	
	@Override
	public void triggerMisfired(Trigger trigger) {
		// TODO Auto-generated method stub
		System.out.println(trigger.getKey().getName()+"错过执行了");
	}

	//触发器触发的任务被完成后调用该方法，该方法后于jobListener的```wasExecuted方法。
	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		// TODO Auto-generated method stub
		System.out.println(trigger.getKey().getName()+"触发完成");
		
	}

}
