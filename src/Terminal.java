import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("serial")
/**
 * 
 * Creates the Terminal
 *
 */
public class Terminal extends JFrame {

	/**
	 * Pane for displaying the Terminal
	 */
	private javax.swing.JLayeredPane Display;
	/**
	 * Text field for user input to the terminal
	 */
	public JTextField input = new JTextField();
	/**
	 * Text area for output for the terminal
	 */
	public JTextArea output = new JTextArea();
	Server mServer;

	/**
	 * Creates the terminal for interaction with client from server
	 * @param s
	 */
	public Terminal(Server s) {
		mServer = s;
		
		TextFieldListener tfListener = new TextFieldListener();
		input.addActionListener(tfListener);

		Display = new javax.swing.JLayeredPane();

		input.setColumns(50);
		output.setColumns(50);
		output.setRows(30);
		output.setEditable(false);

		output.setBackground(Color.BLACK);
		output.setForeground(Color.WHITE);

		input.requestFocus(); // start with focus on this field

		output.setBounds(0, 0, 550, 310);
		Display.add(output, javax.swing.JLayeredPane.DEFAULT_LAYER);

		input.setBounds(0, 320, 550, 25);
		Display.add(input, javax.swing.JLayeredPane.DEFAULT_LAYER);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addComponent(Display,
						javax.swing.GroupLayout.PREFERRED_SIZE, 550,
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
														350,
														javax.swing.GroupLayout.PREFERRED_SIZE))));

		pack();
	}

	/**
	 * Listener for input to the terminal
	 *
	 */
	private class TextFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			String inputString = input.getText();
			String[] inputs = inputString.split(" ");

			if (inputs[0].equals("bg")) {
				if (inputs.length == 4) {
					int r = Integer.valueOf(inputs[1]);
					int g = Integer.valueOf(inputs[2]);
					int b = Integer.valueOf(inputs[3]);
					if (r >= 0 && r <= 255 && b >= 0 && b <= 255 && g >= 0
							&& g <= 255) {
						output.setBackground(new Color(r, g, b));
						output.append("Background set to (" + r + "," + g + ","
								+ b + ")\n");
					}
				} else {
					output.append("usage: bg r g b (sets background to RGB)\n");
				}
			} else if (inputs[0].equals("font")) {
				if (inputs.length == 4) {
					int r = Integer.valueOf(inputs[1]);
					int g = Integer.valueOf(inputs[2]);
					int b = Integer.valueOf(inputs[3]);
					if (r >= 0 && r <= 255 && b >= 0 && b <= 255 && g >= 0
							&& g <= 255) {
						output.setForeground(new Color(r, g, b));
						output.append("Font set to (" + r + "," + g + "," + b
								+ ")\n");
					}
				} else {
					output.append("usage: )font r g b (sets font to RGB)\n");
				}
			} else if (inputs[0].equals("help")) {
				output.append("Commands:\n"
						+ "bg r g b (sets background to RGB)\n"
						+ "font r g b (sets font to RGB)\n"
						+ "get label (returns given field from smart meter)\n"
						+ "set label value (sets given field to value in smart meter)\n"
						+ "pushed (prints any accumulated pushed data)\n"
						+ "clear (clears the terminal\n"
						+ "exit (exits the program)\n");
			} else if (inputs[0].equals("exit")) {
				System.exit(0);
			} else if (inputs[0].equals("clear")) {
				output.setText("");
			} else if (inputs[0].equals("get")) {
				try {
					mServer.write("GET\t"+inputs[1]+"\n");
					String x = mServer.readLine(5);
					output.append(x+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (inputs[0].equals("set")) {
				try {
					mServer.write("SET\t"+inputs[1]+"\t"+inputs[2]+"\n");
					String x = mServer.readLine(5);
					output.append(x+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}				
			} else if ( inputs[0].equals("pushed")) {
				String x = mServer.getPushed();
				output.append(x+"\n");
			} else {
				output.append("Command doesn't exist!\n");
			}

			input.setText("");
		}
	}

}
