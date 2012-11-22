import java.util.Date;

/**
 * 
 * Agent for updating the values to display on the local display (GUI)
 * 
 */
public class GUIAgent extends Agent {

	/**
	 * The local display (8 segment displays)
	 */
	private GUIVisual mGUIVisual;

	/**
	 * Constructor
	 * 
	 * @param b
	 * @param wd
	 * @param w
	 * @param h
	 * @throws Exception
	 */
	public GUIAgent(Blackboard b, WatchDog wd, int w, int h) throws Exception {
		super(b, wd);
		mGUIVisual = new GUIVisual(w, h);
		GUI mainGUI = new GUI(mGUIVisual, w, h);
		mainGUI.setVisible(true);
	}

	/**
	 * Kills the agent
	 */
	public void destroy() {
		mGUIVisual.destroy();
	}

	/**
	 * For execution, gets values from the blackboard and updates the display
	 * every 200ms
	 */
	@SuppressWarnings("deprecation")
	public void execute() throws Exception {

		Tuple<Object, String> timeT = mBlackboard.get("time");
		Long time = (Long) timeT.x;
		mBlackboard.release("time", timeT.y);

		if (time % 200 == 0) {

			Tuple<Object, String> vT = mBlackboard.get("voltage");
			Double v = (Double) vT.x;
			mBlackboard.release("voltage", vT.y);

			Tuple<Object, String> iT = mBlackboard.get("current");
			Double i = (Double) iT.x;
			mBlackboard.release("current", iT.y);

			if (v != null && i != null && time != null) {
				Date d = new Date(time);

				String voltage = String.valueOf(v).substring(0, 5);

				String current = String.valueOf(i).substring(0, 5);

				String curTime = String.format("%02d", d.getHours()) + ":"
						+ String.format("%02d", d.getMinutes());

				mGUIVisual.setParameters(voltage, current, curTime);

				mGUIVisual.repaint();
			}
		}
	}
}
