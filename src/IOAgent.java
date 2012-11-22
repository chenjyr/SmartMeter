import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class IOAgent extends Agent {

	IOSocket mIOSocket;
	private String partialMessage;

	public IOAgent(Blackboard b, WatchDog wd, IOSocket ios) throws Exception {
		super(b,wd);
		partialMessage = "";
		try {
			mIOSocket = ios;
			Queue<String> outbox = new LinkedList<String>();
			ArrayList<String> inbox = new ArrayList<String>();

			mBlackboard.set("outbox", outbox);
			mBlackboard.set("inbox", inbox);
		} catch (Exception e) {
			System.out.println("Could not initialize IO Socket:\n" + e);
		}
	}

	public void close() throws Exception
	{
		mIOSocket.close();
	}
	public void execute() throws Exception {
		// send messages
		// check the outbox for a ready message
		//		System.out.println("IOAGENT");

		Tuple<Object, String> outboxT = mBlackboard.get("outbox");
		Queue<String> outbox = (Queue<String>) outboxT.x;

		//If there's something to send, send something
		while (!outbox.isEmpty())
		{
			String tmp = outbox.poll();
			if (tmp != null) {
				try {
//					System.out.println(tmp);
					mIOSocket.send(tmp+"\n");
				} catch (SocketException e){
					System.out.println("Socket Exception on Smart Meter:  " + e.toString());
					outbox.add(tmp);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		mBlackboard.release("outbox", outboxT.y);

		Tuple<Object,String> inboxT = mBlackboard.get("inbox");
		ArrayList<String> inbox = (ArrayList<String>) inboxT.x;
		
		try {
				//receive a message from the socket, append the partial message from before
				String msg = partialMessage+mIOSocket.receive();
				int lastDelimiter = msg.lastIndexOf("\n");
				if (msg.length()>0  && lastDelimiter > 0)
				{
					String[] messages = msg.substring(0, lastDelimiter).split("\n");
					int firstNonChar = msg.indexOf(0);
					if (firstNonChar > 0)
					{
						partialMessage = msg.substring(lastDelimiter,firstNonChar);
					}
					else 
					{
						partialMessage = msg.substring(lastDelimiter);
						
					}
					for (int i = 0; i < messages.length; ++i)
					{
						if (messages[i].length() >= 1)
						{
//							System.out.println("Received:"+messages[i]);
							inbox.add(messages[i]);
						}
					}				

				}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mBlackboard.release("inbox", inboxT.y);			
		}

	}
}
