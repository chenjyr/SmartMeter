public class TerminalThread extends Thread {

	private Terminal terminal;
	private boolean print = false;
	private String line;

	public TerminalThread(Terminal t) {
		terminal = t;
	}

	public void run() {
		while (true) {
			if (print) {
				terminal.output.append(line + "\n");
				print = false;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void println(Object l) {
		line = l.toString();
		print = true;
	}

}
