/**
 * 
 */
public class SensorAgent extends Agent
{
	private SensorSocket mSensor;

	public SensorAgent(Blackboard b, WatchDog wd, SensorSocket ss) throws Exception
	{
		super(b,wd);
		mSensor = ss;
	}
	
	public void execute() throws Exception
	{
		double data = mSensor.read();
		switch (mSensor.device)
		{
		case TILT:
			mBlackboard.set("tilt", data);		
			break;
		case VOLTAGE:
			mBlackboard.set("voltage", data);
			break;
		case CURRENT:
			mBlackboard.set("current", data);		
			break;
		case TEMPERATURE:
			mBlackboard.set("temperature", data);					
			break;
		}
	}
	
}
