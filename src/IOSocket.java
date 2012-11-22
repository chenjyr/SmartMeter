/**
 * encapsulates reading and writing over channel to server 
 * 
 */
public class IOSocket {

	/**
	 * Farid's communication socket
	 */
	private Communicator mCommunicator;

	/**
	 * Constructor 
	 * @param port TCP port to bind to 
	 * @throws Exception exceptions from underlying Communicator
	 */
	public IOSocket(int port) throws Exception
	{		
		mCommunicator = new Communicator("localhost",port);
	}
	
	/**
	 * Send a message to the server 
	 * @param message message to send 
	 * @throws Exception exceptions from underlying communicator
	 */
	public void send(String message) throws Exception
	{	
//		System.out.println("IOSocket attempting to send: "+message);
		//System.out.println("Sending: " + message);
		mCommunicator.sendMessage("\n\n"+message);
		//System.out.println("Sent");
	}
	
	/**
	 * Receive a message from the server 
	 * @return message from server 
	 * @throws Exception exceptions from underlying Communicator
	 */
	public String receive() throws Exception
	{
		return mCommunicator.recvMessage();
	}
	public void close() throws Exception
	{
		mCommunicator.close();
	}
}