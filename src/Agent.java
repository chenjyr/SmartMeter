/**
 * Agent represents an actor in the system. It's execute methods performs some action on blackboard entries and 
 * sockets
 */
public class Agent {
	/**
	 * entry point for agent
	 * @throws Exception various things like invalid casts
	 */
	public static int SerialNum = 0;
	protected Blackboard mBlackboard;
	WatchDog mWatchDogAgent;
	private int mSerialNum;

	public Agent( Blackboard b, WatchDog wd) throws Exception
	{

		mBlackboard = b;
		mWatchDogAgent = wd;
		mSerialNum = SerialNum++;
		mWatchDogAgent.register(mSerialNum);
	}
	
	public void heartBeat() throws Exception
	{
		mBlackboard.set(("agent"+mSerialNum), System.currentTimeMillis());
	}
	public void execute() throws Exception
	{
		
	}
}
