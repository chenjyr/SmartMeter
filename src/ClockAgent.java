import java.util.Date;

public class ClockAgent extends Agent{
	
	public ClockAgent(Blackboard b, WatchDog wd) throws Exception
	{
		super(b,wd);
	}
	
	public void execute() throws Exception
	{
		//TODO: FIX THIS FOR 2030 (long overflow)		
		mBlackboard.set("time", System.currentTimeMillis());
		
	}

}
