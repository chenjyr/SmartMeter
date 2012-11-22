import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

public class AmbassadorAgent extends Agent {

	public AmbassadorAgent(Blackboard bb, WatchDog wd) throws Exception
	{
		super(bb,wd);
	}

	public void execute() throws Exception 
	{
		Tuple<Object,String> inboxT = mBlackboard.get("inbox");
		ArrayList<String> inbox = (ArrayList<String>) inboxT.x;

		Tuple<Object, String> outboxT = mBlackboard.get("outbox");
		Queue<String> outbox = (Queue<String>) outboxT.x;
		
		for (int i = 0 ; i < inbox.size(); i++)
		{
			String[] tokens = inbox.get(i).split("\t");
			if (tokens[0].equals("GET"))
			{
				inbox.remove(i);
				Tuple<Object,String> targetT= mBlackboard.get(tokens[1]);

				try {
					Object target = targetT.x;
					String out = "PULL\t" + tokens[1]+"\t"+ target.toString();
					if (tokens[1].equals("time"))
					{
						Date tmp = new Date((Long)target);
						out = "PULL\t" + tokens[1] + "\t" + tmp.toString();
					}
					outbox.add(out);
				}catch (Exception e){
					outbox.add("ERROR_GET\t"+tokens[1]);				
				}finally
				{
					mBlackboard.release(tokens[1],targetT.y);
				}
			}
			if (tokens[0].equals("SET"))
			{
				inbox.remove(inbox.get(i));
				Tuple<Object,String> targetT= mBlackboard.get(tokens[1]);
				try {
					String label = tokens[1];
					String value = tokens[2];
					Object target = targetT.x;
					Class targetClass = target.getClass();
					
					if (targetClass.getName().equals("java.lang.Long"))
					{
						mBlackboard.set(tokens[1], Long.parseLong(tokens[2]));
						outbox.add("PULL\tSET SUCCEEDED: " + tokens[1] + "\t" + tokens[2] + "\n");
					}
					else if (targetClass.getName().equals("java.lang.Double"))
					{
						mBlackboard.set(tokens[1], Double.parseDouble(tokens[2]));
						outbox.add("PULL\tSET SUCCEEDED: " + tokens[1] + "\t" + tokens[2] + "\n");

					}
					else if (targetClass.getName().equals("java.lang.Integer"))
					{
						mBlackboard.set(tokens[1], Integer.parseInt(tokens[2]));
						outbox.add("PULL\tSET SUCCEEDED: " + tokens[1] + "\t" + tokens[2] + "\n");

					}
					else if (targetClass.getName().equals("java.lang.Boolean"))
					{
						mBlackboard.set(tokens[1], Boolean.parseBoolean(tokens[2]));
						outbox.add("PULL\tSET SUCCEEDED: " + tokens[1] + "\t" + tokens[2] + "\n");

					}else if (targetClass.getName().equals("java.lang.String")) {
						mBlackboard.set(tokens[1], tokens[2]);
						outbox.add("PULL\tSET SUCCEEDED: " + tokens[1] + "\t" + tokens[2] + "\n");

					}
					else
					{
						outbox.add("PULL\tERROR_SET_TYPE\t"+tokens[1]);
					}						
					//set value @ label to value of type 
					
				}catch (Exception e){
					outbox.add("PULL\tERROR_SET\t"+tokens[1]);				
				}finally
				{
					mBlackboard.release(tokens[1],targetT.y);
				}

			}
			
		}
		mBlackboard.release("inbox", inboxT.y);
		mBlackboard.release("outbox", outboxT.y);

	}
}
