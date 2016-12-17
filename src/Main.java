import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Main extends JDialog {
	private boolean isJiggle = false;
	private JButton start = new JButton("Start");
	private JButton stop = new JButton("Stop");
	private Runnable r = new Runnable() {
		public void run() {
			Main.this.startRobot();
		}
	};
	private JSpinner jSpin;
	private Thread t;

	public Main() {
		this.stop.setEnabled(false);
		this.jSpin = new JSpinner(new SpinnerNumberModel(5, 1, 60, 1));
		add(this.jSpin, "North");
		setTitle("lololol");
		setLocationRelativeTo(null);
		// setAlwaysOnTop(true);
		this.start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.this.startJiggle();
			}
		});
		this.stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.this.stopJiggle();
			}
		});
		add(this.start);
		add(this.stop, "South");
		setDefaultCloseOperation(2);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		pack();
		setVisible(true);
	}

	private void startJiggle() {
		// System.out.println("Starting jiggle...");
		this.isJiggle = true;
		this.start.setEnabled(false);
		this.stop.setEnabled(true);
		this.t = new Thread(this.r);
		this.t.start();
	}

	private void stopJiggle() {
		// System.out.println("Stopping jiggle...");
		this.isJiggle = false;
		this.start.setEnabled(true);
		this.stop.setEnabled(false);
		this.t.interrupt();
	}

	private void startRobot() {
		try {
			Robot r = new Robot();
			boolean posA = false;
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int) screenSize.getWidth();
			int height = (int) screenSize.getHeight();
			Random rand = new Random();

			while (this.isJiggle) {
				r.mouseMove(rand.nextInt(width), rand.nextInt(height));
				Thread.sleep(((Integer) this.jSpin.getValue()).intValue() * 1000 * 60);
			}
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Stopping");
		}
	}

	public static void main(String[] args) {
		String pass = JOptionPane.showInputDialog("Entrar mi wed:");
		if (!pass.equals("hrispy kreme eat"))
			System.exit(0);
		int time = -1;
		try {
			time = Integer.parseInt(
					JOptionPane.showInputDialog("Cuanto tiempo en minutos?\n(1h:60,2:120,3:180,4:240,5:300)"));
		} catch (NumberFormatException e) {
		}
		if (time != -1) {
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					System.exit(0);
				}
			}, time * 60 * 1000);
		}
		new Main();
	}
}
