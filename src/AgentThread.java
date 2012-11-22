/**
 * Runs a set of agents
 */
public class AgentThread extends Thread
{
	/**
	 * Array of agents that it runs
	 */
	private Agent[] AgentSet;

	/**
	 * Constructor
	 * @param as array of agents that this AgentThread runs
	 */
	public AgentThread(Agent[] as)
	{
		AgentSet = as;
	}
	
	/**
	 * implementation of Thread.run()
	 */
	public void run()
	{
//		try{
		while(true)
		{
			for (Agent a: AgentSet)
			{
				// if execute throws an exception, remove agent
				// from the run array
				if(a == null) continue;
			try {
					a.execute();
					a.heartBeat();
					Thread.sleep(10);
			} catch(Exception e) {
					a = null;
					e.printStackTrace();
				}
			}
		}
/*		}catch(InterruptedException e)
		{
			
		}*/
	}
}