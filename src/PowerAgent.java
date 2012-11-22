import java.util.ArrayList;
import java.util.Queue;

/**
 * This agent calculates power and puts it in the blackboard
 */
public class PowerAgent extends Agent
{
	/**
	 * interval between hours
	 */
	/**.
	 * reference to central blackboard object 
	 */
	
	/**
	 * Constructor 
	 * @param b the blackboard to act upon.	
	 * 
	 * 
	 * 	 * @throws Exception 
	 */
	public PowerAgent(Blackboard b, WatchDog wd) throws Exception
	{
		super(b, wd);
		ArrayList<Tuple<Long,Double>> tmp =new ArrayList<Tuple<Long,Double>>();
		mBlackboard.set("power", tmp);
		mBlackboard.set("powerinterval", 5000L);
	}

	public void execute() throws Exception
	{
		Tuple<Object, String> intervalT = mBlackboard.get("powerinterval");
		Long interval = (Long) intervalT.x;
		mBlackboard.release("powerinterval", intervalT.y);
		
		// acquire the current voltage 
		Tuple<Object, String> vT = mBlackboard.get("voltage");
		Double v = (Double) vT.x;

		// acquire the current current 		System.out.println("tick");

		Tuple<Object, String> iT = mBlackboard.get("current");
		Double i = (Double) iT.x;

		// acquire the current time  
		Tuple<Object, String> timeT = mBlackboard.get("time");
		Long time = (Long) timeT.x;

		// acquire the current power 
		Tuple<Object, String> powerListT = mBlackboard.get("power");
		ArrayList<Tuple<Long,Double>> powerList = (ArrayList<Tuple<Long,Double>>) powerListT.x;
		mBlackboard.release("time", timeT.y);
		mBlackboard.release("voltage", vT.y);
		mBlackboard.release("current", iT.y);
		
		// only execute if the first times
		// v's and i's have been read and added to the blackboard.
		if (v != null && i != null && time != null)
		{
			// calculate the instantaneous power and add it to the power list
			Tuple<Long,Double> data = new Tuple<Long,Double>(time, v*i);
			powerList.add(data);
			// release the lease on the power list object 
			
			if (time - powerList.get(0).x >= interval)
			{
				//Get the mean
				long prevTime = powerList.get(0).x;
				Double cumPower = 0.0;
				for (int j = 1; j < powerList.size();j++)
				{
					cumPower+=powerList.get(j).y * (powerList.get(j).x-prevTime);
					prevTime = powerList.get(j).x;
				}
				Double mean = cumPower / (powerList.get(powerList.size()-1).x - powerList.get(0).x);
				//Store mean blackboard
				mBlackboard.set("meanpower", mean);

				double variance = 0;
				//Calculate Variance
				for (int j = 1; j < powerList.size();j++)
				{
					variance += (powerList.get(j).y-mean)*(powerList.get(j).y-mean) * (powerList.get(j).x-prevTime);
				}

				variance /= (powerList.get(powerList.size()-1).x - prevTime);
				mBlackboard.set("variancepower", variance);
						
				//Send message
//				System.out.println("Push Power " + mean.toString());
				String message = "PUSH\tPOWER\t" + mean.toString() + "\n";
				Tuple<Object, String> outboxT = mBlackboard.get("outbox");
				Queue<String> outbox = (Queue<String>) outboxT.x;
				outbox.add(message);	
				
				
				//Reset Power List
				mBlackboard.release("outbox", outboxT.y);
				ArrayList<Tuple<Long,Double>> tmp = new ArrayList<Tuple<Long,Double>>();
				mBlackboard.set("power", tmp);			
			}			
		}

		// release the leases 
		mBlackboard.release("power", powerListT.y);
	}
}