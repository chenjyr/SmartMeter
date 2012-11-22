import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread {
	/**
	 *  port number
	 */
	int port;
	/**
	 * Socket for input data
	 */
	ServerSocket inputSocket;
	/**
	 *  Some socket object
	 */
	Socket socket;
	/**
	 * Input Reader Object
	 */
	BufferedReader clientInput;
	/**
	 * Output Object
	 */
	DataOutputStream clientOutput;
	/**
	 * temporary variable for dealing with partial messages
	 */
	String partialMessage;
	/**
	 *  List of messages returned from pull requests
	 */
	ArrayList<String> pullList;
	/**
	 * Lit of messages pushed to the server
	 */
	ArrayList<String> pushList;

	/**
	 * Creates the server at the supplied port
	 * @param portNum
	 * @throws Exception
	 */
	public Server(int portNum) throws Exception {
		if (portNum == 0)
			port = 5555;
		else
			port = portNum;
		System.out.println(port);
	}

	/**
	 * Writes the supplied message to the smart meter
	 * @param msg
	 * @throws IOException
	 */
	public void write(String msg) throws IOException {
//		System.out.println("Server sending :" + msg);
		clientOutput.writeBytes("\n\n"+msg);
	}
	
	/**
	 * Returns all the pushed data accumulated so far
	 * @return pushed data
	 */
	public String getPushed()
	{
		String tmp = "";
		while (!pushList.isEmpty())
		{
			tmp += pushList.remove(0) + "\n";
		}
		return tmp;
		
	}
	
	/**
	 * Attempts to read a line from the pull list, but times out if nothing comes after the specified seconds
	 * 
	 * @param seconds
	 * @return the line, or null if timeout
	 */
	public String readLine(int seconds) {
		long time = System.currentTimeMillis();
		while (pullList.isEmpty()) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (System.currentTimeMillis() - time >= seconds * 1000)
				return "Request Timed Out";
		}
		if (pullList.size() > 0) {
			while (pullList.size() > 1)
				pullList.remove(0);
			String tmp = pullList.remove(0);
			return tmp;
		} else {
			return null;
		}
	}

	/**
	 * Threaded method which constantly reads incoming data and  
	 * puts in the relevant list
	 * 	 
	 **/
	public void run() {
		try {
			inputSocket = new ServerSocket(port);
			socket = inputSocket.accept();
			clientInput = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			clientOutput = new DataOutputStream(socket.getOutputStream());
			partialMessage = "";
			pullList = new ArrayList<String>();
			pushList = new ArrayList<String>();

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			char buffer[] = new char[10000];
			while (true) {
				if (clientInput.ready()) {
					int len = clientInput.read(buffer);
					String msg = new String(buffer, 0, len);
					//System.out.println("SERVER" + msg);
					msg = partialMessage + msg;
					int lastDelimiter = msg.lastIndexOf("\n");

					if (msg.length() > 0 && lastDelimiter > 0) {
						String[] messages = msg.substring(0, lastDelimiter)
								.split("\n");
						int firstNonChar = msg.indexOf(0);
						if (firstNonChar > 0) {
							partialMessage = msg.substring(lastDelimiter,
									firstNonChar);
						} else {
							partialMessage = msg.substring(lastDelimiter);
							
						}
						for (int i = 0; i < messages.length; ++i) {
							if (messages[i].contains("PUSH"))
							{
								pushList.add(messages[i]);
								System.out.println("SERVER: " + messages[i]);
							}
							else if (messages[i].contains("PULL")) {
								pullList.add(messages[i]);
								System.out.println("SERVER: " + messages[i]);
							}
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
