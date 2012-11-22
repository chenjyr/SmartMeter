import java.applet.Applet;
import java.awt.*;

/**
 * 
 * The actual display for the local display (GUI) with voltage, time, current
 * labels and 8 segment displays
 * 
 */
@SuppressWarnings("serial")
public class GUIVisual extends Applet {

	/**
	 * width and height of the window
	 */
	private int windowWidth;
	private int windowHeight;

	/**
	 * string representation of the most up-to-date time,voltage,current
	 */
	private String time;
	private String voltage;
	private String current;

	/**
	 * offset for the decimal place in voltage/current
	 */
	private int offset;

	/**
	 * colour of the display background and text
	 */
	private Color lText = Color.GREEN;
	private Color nlText = Color.BLACK;

	/**
	 * For the applet
	 */
	private Graphics2D g;
	private static int type = AlphaComposite.SRC_OVER;

	/**
	 * Constructor
	 * 
	 * @param w
	 * @param h
	 */
	public GUIVisual(int w, int h) {
		windowWidth = w;
		windowHeight = h;
		setLayout(null);
		setBackground(new Color(0, 0, 0));
		setSize(new Dimension(windowWidth, windowHeight));
	}

	/**
	 * Kills the display
	 */
	public void destroy() {
		this.destroy();
	}

	/*
	 * Function for mapping numbers to the 7 segment display
	 */
	public void drawNumber(int num, int x, int y) {

		switch (num) {
		case 0:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.fillRect(x, y + 25, 40, 5); // seg6
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 35, 5, 20); // seg5
			break;
		case 1:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 35, 5, 20); // seg5
			g.fillRect(x, y + 25, 40, 5); // seg6
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			break;
		case 2:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			g.fillRect(x, y, 5, 20); // seg0
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 35, 5, 20); // seg5

			g.fillRect(x, y + 25, 40, 5); // seg6
			break;
		case 3:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x, y + 35, 5, 20); // seg5
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 25, 40, 5); // seg6
			break;
		case 4:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 35, 5, 20); // seg5
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			g.fillRect(x, y + 25, 40, 5); // seg6
			break;
		case 5:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x, y + 35, 5, 20); // seg5
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 25, 40, 5); // seg6
			break;
		case 6:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 35, 5, 20); // seg5
			g.fillRect(x, y + 25, 40, 5); // seg6
			break;
		case 7:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 35, 5, 20); // seg5
			g.fillRect(x, y + 25, 40, 5); // seg6
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			break;
		case 8:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 35, 5, 20); // seg5
			g.fillRect(x, y + 25, 40, 5); // seg6
			break;
		case 9:
			g.setColor(nlText);
			g.setComposite(AlphaComposite.getInstance(type, 0.10f));
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 35, 5, 20); // seg5
			g.setColor(lText);
			g.setComposite(AlphaComposite.getInstance(type, 1.0f));
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			g.fillRect(x, y + 25, 40, 5); // seg6
			break;
		default:
			g.fillRect(x, y, 5, 20); // seg0
			g.fillRect(x, y - 10, 40, 5); // seg1
			g.fillRect(x + 35, y, 5, 20); // seg2
			g.fillRect(x + 35, y + 35, 5, 20); // seg3
			g.fillRect(x, y + 60, 40, 5); // seg4
			g.fillRect(x, y + 35, 5, 20); // seg5
			g.fillRect(x, y + 25, 40, 5); // seg6
			break;
		}

	}

	/**
	 * For painting the display
	 * 
	 */
	public void paint(Graphics g1) {

		g = (Graphics2D) g1;

		g.setColor(lText);
		g.setComposite(AlphaComposite.getInstance(type, 1.0f));
		g.setFont(new Font("Monospaced", Font.BOLD + Font.ROMAN_BASELINE, 30));

		/*
		 * Draw labels
		 */
		g.drawString("TIME", 25, 100);
		g.drawString("VOLTAGE", 20, 200);
		g.drawString("CURRENT", 20, 300);

		g.setColor(nlText);
		g.setComposite(AlphaComposite.getInstance(type, 0.10f));

		/*
		 * Compute values for the voltage, time, current, and positions on the
		 * display and display them
		 */
		if (voltage != null && current != null & time != null) {
			for (int i = 0; i < 3; i++) {
				offset = 0;
				for (int j = 0; j < 5; j++) {

					if (i > 0) {
						g.setColor(nlText);
						g.setComposite(AlphaComposite.getInstance(type, 0.10f));
						g.fillRect(255 + (i * 80), 105 + (j * 100), 10, 10);
					}

					if (i == 1) {
						if (!voltage.substring(j, j + 1).contains("."))
							this.drawNumber(Integer.valueOf(voltage.substring(
									j, j + 1)),
									(int) ((200 + ((j - offset) * 80))),
									(int) ((50 + (i * 100))));
						if (voltage.substring(j, j + 1).contains(".")) {
							if (j < 4) {
								g.setColor(lText);
								g.setComposite(AlphaComposite.getInstance(type,
										1.0f));
								g.fillRect(255 + ((j - 1) * 80),
										105 + (i * 100), 10, 10);
								offset = 1;
							}
						}
					} else if (i == 2) {
						if (!current.substring(j, j + 1).contains("."))
							this.drawNumber(Integer.valueOf(current.substring(
									j, j + 1)),
									(int) ((200 + ((j - offset) * 80))),
									(int) ((50 + (i * 100))));
						if (current.substring(j, j + 1).contains(".")) {
							if (j < 4) {
								g.setColor(lText);
								g.setComposite(AlphaComposite.getInstance(type,
										1.0f));
								g.fillRect(255 + ((j - 1) * 80),
										105 + (i * 100), 10, 10);
								offset = 1;
							}
						}
					} else {
						if (!time.substring(j, j + 1).contains(":"))
							this.drawNumber(
									Integer.valueOf(time.substring(j, j + 1)),
									(int) ((200 + ((j - offset) * 80))),
									(int) ((50 + (i * 100))));
						if (time.substring(j, j + 1).contains(":")) {
							g.setColor(lText);
							g.setComposite(AlphaComposite.getInstance(type,
									1.0f));
							g.fillRect(255 + ((j - 1) * 80), 100 + (i * 100),
									10, 10);
							g.fillRect(255 + ((j - 1) * 80), 50 + (i * 100),
									10, 10);

							offset = 1;
						}
					}
				}
			}
		}
	}

	/*
	 * Function for the GUIAgent class to set the values for display
	 */
	public void setParameters(String v, String i, String t) {
		voltage = v;
		current = i;
		time = t;
	}

}
