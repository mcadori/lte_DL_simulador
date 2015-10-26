package interfaceUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.TextField;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Button;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextField;
import java.awt.Canvas;
import javax.swing.JSlider;
import java.awt.Label;
import javax.swing.JRadioButton;
import javax.swing.JLabel;

public class UI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 710, 456);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(557, 358, 89, 23);
		frame.getContentPane().add(btnStart);
		
		JTextArea txtrJavaDlFrame = new JTextArea();
		txtrJavaDlFrame.setBackground(SystemColor.menu);
		txtrJavaDlFrame.setEditable(false);
		txtrJavaDlFrame.setFont(new Font("Calibri", Font.ITALIC, 17));
		txtrJavaDlFrame.setText("LTE DL Frame Scheduler");
		txtrJavaDlFrame.setBounds(29, 23, 216, 22);
		frame.getContentPane().add(txtrJavaDlFrame);
		
		Canvas canvas = new Canvas();
		canvas.setBackground(SystemColor.inactiveCaptionBorder);
		canvas.setForeground(SystemColor.controlShadow);
		canvas.setBounds(313, 87, 333, 254);
		frame.getContentPane().add(canvas);
		
		JSlider slider = new JSlider();
		slider.setBounds(313, 355, 146, 26);
		frame.getContentPane().add(slider);
		
		Label label_1 = new Label("LTE frequency");
		label_1.setBounds(32, 100, 86, 22);
		frame.getContentPane().add(label_1);
		
		Label label_3 = new Label("Simulation Time");
		label_3.setBounds(349, 387, 80, 22);
		frame.getContentPane().add(label_3);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("a");
		rdbtnNewRadioButton.setBounds(148, 124, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("b");
		rdbtnNewRadioButton_1.setBounds(148, 151, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("c");
		rdbtnNewRadioButton_2.setBounds(148, 176, 109, 23);
		frame.getContentPane().add(rdbtnNewRadioButton_2);
		
		Label label_4 = new Label("Sort By");
		label_4.setBounds(148, 100, 62, 22);
		frame.getContentPane().add(label_4);
		
		JLabel lblTrafficGenerator = new JLabel("Traffic Generator");
		lblTrafficGenerator.setBounds(10, 248, 89, 14);
		frame.getContentPane().add(lblTrafficGenerator);
		
		JSlider slider_1 = new JSlider();
		slider_1.setBounds(10, 285, 121, 26);
		frame.getContentPane().add(slider_1);
		
		JSlider slider_2 = new JSlider();
		slider_2.setBounds(10, 345, 121, 26);
		frame.getContentPane().add(slider_2);
		
		Label label_5 = new Label("Data Pkts");
		label_5.setBounds(42, 317, 62, 22);
		frame.getContentPane().add(label_5);
		
		Label label_6 = new Label("Users");
		label_6.setBounds(52, 381, 34, 22);
		frame.getContentPane().add(label_6);
		
		JRadioButton radioButton = new JRadioButton("14");
		radioButton.setBounds(32, 128, 109, 23);
		frame.getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("5");
		radioButton_1.setBounds(32, 151, 109, 23);
		frame.getContentPane().add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("10");
		radioButton_2.setBounds(32, 176, 109, 23);
		frame.getContentPane().add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("20");
		radioButton_3.setBounds(32, 201, 109, 23);
		frame.getContentPane().add(radioButton_3);
	}
}
