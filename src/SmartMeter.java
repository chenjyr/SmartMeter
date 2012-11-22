
public class SmartMeter {

	static final int port = 10106;

	private static final int windowWidth = 550;
	private static final int windowHeight = 350;
	public static TerminalThread terminalThread;
	
	public static boolean GlobalReset;
	
	public static void main(String[] args) throws Exception
	{
		
		
		// Set up the blackboard and remote control server
		Server mServer = new Server(port);
		mServer.start();
		IOSocket mIOSocket = new IOSocket(port);
		Terminal t = new Terminal(mServer);
		terminalThread = new TerminalThread(t);
		terminalThread.start();
		t.setVisible(true);

		
		while(true)
		{
			// collect
			System.gc();			
			//reset reset flag
			GlobalReset = false;
			System.out.println("NEW RUN");
			Blackboard mBlackboard = new Blackboard();
			
			WatchDog mWatchDog = new WatchDog(mBlackboard);
			// create the sockets for reading the voltage and current sensors
			SensorSocket voltageSocket = new SensorSocket(Sensor.VOLTAGE);
			SensorSocket currentSocket = new SensorSocket(Sensor.CURRENT);
			SensorSocket tiltSocket = new SensorSocket(Sensor.TILT);
			
			// creates agents for reading the voltage and current
			SensorAgent voltageAgent = new SensorAgent(mBlackboard, mWatchDog, voltageSocket);
			SensorAgent currentAgent = new SensorAgent(mBlackboard, mWatchDog, currentSocket);
			SensorAgent tiltAgent = new SensorAgent(mBlackboard, mWatchDog, tiltSocket);

			// create the agent threads 
			Agent[] sensors = {voltageAgent, currentAgent,tiltAgent};
			AgentThread sensorThread = new AgentThread(sensors);
			
			// create the agent that calculates power and add to its agent thread
			PowerAgent mPowerAgent = new PowerAgent(mBlackboard,mWatchDog);
			Agent[] processing = {mPowerAgent};
			AgentThread processThread = new AgentThread(processing);
			
			// create clock agent for handling time and add it to an agent thread 
			ClockAgent mClockAgent = new ClockAgent(mBlackboard, mWatchDog);
			Agent[] clock = {mClockAgent};
			AgentThread clockThread = new AgentThread(clock);
			
			// create agent for handling I/O agent and add to its agent thread 
			IOAgent mIOAgent = new IOAgent(mBlackboard,mWatchDog, mIOSocket);
			AmbassadorAgent mAmbassadorAgent = new AmbassadorAgent(mBlackboard,mWatchDog);
			Agent[] comm = {mIOAgent,mAmbassadorAgent};
			AgentThread commThread = new AgentThread(comm);
			
			GUIAgent mGUIAgent = new GUIAgent(mBlackboard, mWatchDog, windowWidth, windowHeight);
			Agent[] gui = {mGUIAgent};
			AgentThread guiThread = new AgentThread(gui);
			
			// start all of the agent threads
			clockThread.start();
			sensorThread.start();
			processThread.start();
			commThread.start();
			guiThread.start();
			Thread.sleep(1000);
			mWatchDog.start();
			while(!GlobalReset);			
			clockThread.interrupt();
			sensorThread.interrupt();
			processThread.interrupt();
			commThread.interrupt();
			mWatchDog.interrupt();
			guiThread.interrupt();
			mGUIAgent.destroy();
			Agent.SerialNum = 0;
		}
	}
}
