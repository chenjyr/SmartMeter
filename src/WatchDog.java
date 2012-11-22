import java.util.ArrayList;


public class WatchDog extends Thread{
	
	Blackboard mBlackboard;
	public WatchDog(Blackboard bb) throws Exception
	{
		mBlackboard = bb;
		ArrayList<Integer> agentList = new ArrayList<Integer>();
		mBlackboard.set("agentlist",agentList);
		mBlackboard.set("watchdoginterval", 10000L);
	}
	
	
	public void register(int agentNum) throws Exception
	{
		Tuple<Object, String> agentListT = mBlackboard.get("agentlist");
		ArrayList<Integer> agentList = (ArrayList<Integer>) agentListT.x;
		agentList.add(agentNum);
		mBlackboard.set(("agentheartbeat"+agentNum), System.currentTimeMillis());
		mBlackboard.release("agentlist", agentListT.y);
	}
	
	public void run() //throws Exception
	{
		while(!Tylissa.GlobalReset)
		{
			try
			{
				Tuple<Object,String> deadIntervalT = mBlackboard.get("watchdoginterval");
				Long deadInterval = (Long) deadIntervalT.x;
				mBlackboard.release("watchdoginterval", deadIntervalT.y);
				Tuple<Object, String> agentListT = mBlackboard.get("agentlist");
				ArrayList<Integer> agentList = (ArrayList<Integer>) agentListT.x;
				Tuple<Object, String> timeT = mBlackboard.get("time");
				Long time = (Long) timeT.x;
				for (int a: agentList)
				{
					String agentID = "agent"+a;
					Tuple<Object, String> agentT = mBlackboard.get(agentID);
					long agentHeartBeat = (Long) agentT.x;
					if (time - agentHeartBeat > deadInterval)
					{	
						System.out.println("RESETTING SYSTEM BECAUSE IT WAS HANGING: " + agentID);
						Tylissa.GlobalReset = true;
						break;
					}
					mBlackboard.release(agentID,agentT.y);		
				}
				
				mBlackboard.release("agentlist",agentListT.y);
				mBlackboard.release("time", timeT.y);
				//Thread.sleep(500);
			}catch (Exception  e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
}
