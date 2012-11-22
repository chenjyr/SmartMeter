import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Agent that monitors for last breath
 */
public class PowerLossAgent extends Agent{
	/**
	 * the number of consecutive zero powers read until a last breath is signaled   
	 */
	private final long timeThreshold = 2*1000;
	/**
	 * current count of consecutive zeros read 
	 */
	private Long lastPower;
	
	/**
	 * Constructor
	 * @param bb reference to the Smart Meter BlackBoard
	 */
	public PowerLossAgent(Blackboard bb,WatchDog wd) throws Exception
	{
		super(bb,wd);
		lastPower = System.currentTimeMillis();
	}
	
	public void execute() throws Exception
	{
		//  password for power entry lease 
		String power_psw;
		
		// get the list of powers from over the last hour from the black board
		Tuple<Object, String> powerListT = mBlackboard.get("power");
		ArrayList<Tuple<Long,Double>> powerList = (ArrayList<Tuple<Long,Double>>) powerListT.x;
		power_psw = powerListT.y;

		// check if the last power reading was zero, if it was incremeent the number of power failures. 
		if (powerList.get(powerList.size()-1).y <= 0 && System.currentTimeMillis() - lastPower > timeThreshold)
		{
				Queue<String> tmpOutbox = new LinkedList<String>();
				// add a last breath method to to out box. 
				tmpOutbox.add("POWER FAILED!!!!");
				mBlackboard.set("outbox", tmpOutbox);
		}
		else
		{
			lastPower = System.currentTimeMillis();
		}
		
		// return the lease on the power entry
		mBlackboard.release("power", power_psw);
				
	}
}
