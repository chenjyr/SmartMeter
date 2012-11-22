import java.util.Queue;
public class TiltSensorAgent extends Agent
{
	final long threshInterval = 2*1000;
	private long lastTime = 0;
	private boolean tiltAdulterated = false;
	public TiltSensorAgent(Blackboard bb, WatchDog wd) throws Exception
	{
		super(bb,wd);		
		lastTime = System.currentTimeMillis();
	}
	
	public void execute() throws Exception
	{
		Tuple<Object,String> tiltT = mBlackboard.get("tilt");
		Double tilt = (Double) tiltT.x;
		mBlackboard.release("tilt", tiltT.y);
		
		if ((tilt) > 9.5 && !tiltAdulterated && System.currentTimeMillis() -  lastTime > threshInterval)
		{
			Tuple<Object,String> outboxT = mBlackboard.get("outbox");
			Queue<String> outbox = (Queue<String>) outboxT.x;
			outbox.add("PUSH\tTILT ADULTERATED\n");
			lastTime = System.currentTimeMillis();		
			tiltAdulterated = true;
		}
		else
		{
			lastTime = System.currentTimeMillis();			
			tiltAdulterated = false;
		}
	}
	
}
