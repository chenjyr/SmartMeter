
/**
 * 
 * Class for holding the display in a frame
 *
 */
@SuppressWarnings("serial")
public class GUI extends javax.swing.JFrame {

	/*
	 * Frame contains the local display
	 */
	private GUIVisual mGUIVisual;
	private int windowWidth, windowHeight;

	private javax.swing.JLayeredPane Display;
	
	/**
	 * Constructor
	 * @param t
	 * @param w
	 * @param h
	 */
	public GUI(GUIVisual t, int w, int h) {
		mGUIVisual = t;
		windowWidth = w;
		windowHeight = h;
		initComponents();
	}

	/*
	 * Initliaze components in the frame and start the display
	 */
	private void initComponents() {
		Display = new javax.swing.JLayeredPane();
		
		mGUIVisual.setVisible(true);
		mGUIVisual.init();

		mGUIVisual.setBounds(0, 0, windowWidth, windowHeight);
		Display.add(mGUIVisual, javax.swing.JLayeredPane.DEFAULT_LAYER);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addComponent(Display,
						javax.swing.GroupLayout.PREFERRED_SIZE, windowWidth,
						javax.swing.GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														Display,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														windowHeight,
														javax.swing.GroupLayout.PREFERRED_SIZE))));
		pack();
	}
}
