package interfaceUI;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import scheduling.Constants;

public class UI extends Canvas {

	/**
	 * Construção do método main e da interface WEB
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
			
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					// User Friendly
					UI window = new UI();
					window.frame.setVisible(true);
					
					//  Scheduling Expert //
					//Scheduling newScheduling = new Scheduling();
					//newScheduling.doExpertSimulation();

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
		final int SLIDER_MIN = 0;
		final int SLIDER_MAX = 200;
		
		frame = new JFrame();
		frame.setTitle("UFRGS - LTE DL Scheduler");
		frame.setResizable(true);
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 546, 456);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Button btnStart = new Button("Start");
		btnStart.setBounds(356, 337, 121, 23);
		frame.getContentPane().add(btnStart);
			
		final JSlider sliderSimulationTime = new JSlider(0,1000);
		sliderSimulationTime.setBounds(53, 325, 184, 47);
		sliderSimulationTime.setMinorTickSpacing(SLIDER_MIN);
		sliderSimulationTime.setMajorTickSpacing(SLIDER_MAX);
		sliderSimulationTime.setPaintTicks(true);
		sliderSimulationTime.setPaintLabels(true);
		frame.getContentPane().add(sliderSimulationTime);
		
		Label lblSimulationTime = new Label("Simulation Time");
		lblSimulationTime.setBounds(110, 378, 97, 22);
		frame.getContentPane().add(lblSimulationTime);
		
		final JRadioButton rdbtnCQI = new JRadioButton("alpha");
		rdbtnCQI.setBounds(145, 75, 62, 23);
		frame.getContentPane().add(rdbtnCQI);

		final JRadioButton rdbtnDelay = new JRadioButton("beta");
		rdbtnDelay.setBounds(145, 101, 62, 23);
		frame.getContentPane().add(rdbtnDelay);
		
		final JRadioButton rdbtnOmega = new JRadioButton("omega");
		rdbtnOmega.setBounds(145, 127, 70, 23);
		frame.getContentPane().add(rdbtnOmega);

		final JSlider sliderDataPkts = new JSlider(0,1000);
		sliderDataPkts.setBounds(53, 228, 184, 47);
		sliderDataPkts.setMinorTickSpacing(SLIDER_MIN);
		sliderDataPkts.setMajorTickSpacing(SLIDER_MAX);
		sliderDataPkts.setPaintTicks(true);
		sliderDataPkts.setPaintLabels(true);
		frame.getContentPane().add(sliderDataPkts);
		
		final JSlider sliderUsers = new JSlider(0,1000);
		sliderUsers.setBounds(316, 228, 184, 47);
		sliderUsers.setMinorTickSpacing(SLIDER_MIN);
		sliderUsers.setMajorTickSpacing(SLIDER_MAX);
		sliderUsers.setPaintTicks(true);
		sliderUsers.setPaintLabels(true);
		frame.getContentPane().add(sliderUsers);
		
		Label label_5 = new Label("Data Pkts");
		label_5.setBounds(121, 281, 62, 22);
		frame.getContentPane().add(label_5);
		
		Label label_6 = new Label("Users");
		label_6.setBounds(388, 281, 52, 22);
		frame.getContentPane().add(label_6);
		
		final JRadioButton rdbtn1_4_MHZ = new JRadioButton("1,4 MHz");
		rdbtn1_4_MHZ.setBounds(32, 75, 70, 23);
		frame.getContentPane().add(rdbtn1_4_MHZ);
		
		final JRadioButton rdbtn5_MHZ = new JRadioButton("5 MHz");
		rdbtn5_MHZ.setBounds(32, 101, 70, 23);
		frame.getContentPane().add(rdbtn5_MHZ);
		
		final JRadioButton rdbtn10_MHZ = new JRadioButton("10 MHz");
		rdbtn10_MHZ.setBounds(32, 127, 70, 23);
		frame.getContentPane().add(rdbtn10_MHZ);
		
		final JRadioButton rdbtn20_MHZ = new JRadioButton("20 MHz");
		rdbtn20_MHZ.setBounds(32, 153, 70, 23);
		frame.getContentPane().add(rdbtn20_MHZ);
		
		JPanel panelFrequency = new JPanel();
		panelFrequency.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		panelFrequency.setBounds(21, 32, 97, 158);
		frame.getContentPane().add(panelFrequency);
		
		Label lblFrequency = new Label("LTE frequency");
		panelFrequency.add(lblFrequency);
		
		JPanel panelSortOption = new JPanel();
		panelSortOption.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		panelSortOption.setBounds(131, 32, 91, 158);
		frame.getContentPane().add(panelSortOption);
		
		Label lblSortOption = new Label("Sort By");
		panelSortOption.add(lblSortOption);
		
		final JRadioButton rdbtnFairnessXUser = new JRadioButton("User x Fairness");
		rdbtnFairnessXUser.setBounds(245, 74, 121, 23);
		frame.getContentPane().add(rdbtnFairnessXUser);
		rdbtnFairnessXUser.setHorizontalAlignment(SwingConstants.LEFT);
		
		final JRadioButton rdbtnRateXUser = new JRadioButton("User x Rate");
		rdbtnRateXUser.setBounds(245, 127, 121, 23);
		frame.getContentPane().add(rdbtnRateXUser);
		rdbtnRateXUser.setHorizontalAlignment(SwingConstants.LEFT);
		
		final JRadioButton rdbtnAtrasoXSbs = new JRadioButton("Delay x SBs");
		rdbtnAtrasoXSbs.setBounds(245, 100, 121, 23);
		frame.getContentPane().add(rdbtnAtrasoXSbs);
		rdbtnAtrasoXSbs.setVerticalAlignment(SwingConstants.BOTTOM);
		rdbtnAtrasoXSbs.setHorizontalAlignment(SwingConstants.LEFT);
		
		ButtonGroup groupFrequencies = new ButtonGroup();
		groupFrequencies.add(rdbtn10_MHZ);
		groupFrequencies.add(rdbtn1_4_MHZ);
		groupFrequencies.add(rdbtn20_MHZ);
		groupFrequencies.add(rdbtn5_MHZ);
        
        ButtonGroup groupSortOptions = new ButtonGroup();
        groupSortOptions.add(rdbtnCQI);
        groupSortOptions.add(rdbtnDelay);
        groupSortOptions.add(rdbtnOmega);
        
        final JRadioButton rdbtnSchedulingStatus = new JRadioButton("Scheduling Status");
        rdbtnSchedulingStatus.setBounds(368, 102, 132, 23);
        frame.getContentPane().add(rdbtnSchedulingStatus);
        
        final JRadioButton rdbtnSBxUser = new JRadioButton("SBs x User");
        rdbtnSBxUser.setBounds(368, 127, 109, 23);
        frame.getContentPane().add(rdbtnSBxUser);
        
        final JRadioButton rdbtnSbsXMcss = new JRadioButton("SBs x MCSs");
        rdbtnSbsXMcss.setBounds(368, 76, 109, 23);
        frame.getContentPane().add(rdbtnSbsXMcss);
        
        JPanel panelPlotOption = new JPanel();
        panelPlotOption.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panelPlotOption.setBounds(232, 32, 279, 158);
        frame.getContentPane().add(panelPlotOption);
        
        Label lblNewLabel = new Label("Plot");
        panelPlotOption.add(lblNewLabel);
        
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
							
				Scheduling newSch = new Scheduling();
				
				Constants.setN_TOTAL_PKTS(sliderDataPkts.getValue());
				Constants.setN_USERS(sliderUsers.getValue());
				Constants.setTTI_TOTAL(sliderSimulationTime.getValue());

				if (rdbtnOmega.isSelected())
					Constants.setSORT_MODE(1);
				if (rdbtnCQI.isSelected())
					Constants.setSORT_MODE(2);
				if (rdbtnDelay.isSelected())
					Constants.setSORT_MODE(3);

				if (rdbtn1_4_MHZ.isSelected())
					Constants.setSB_EACH_TTI(6);
				if (rdbtn5_MHZ.isSelected())
					Constants.setSB_EACH_TTI(25);
				if (rdbtn10_MHZ.isSelected())
					Constants.setSB_EACH_TTI(50);
				if (rdbtn20_MHZ.isSelected())
					Constants.setSB_EACH_TTI(100);

				Boolean a=false,b=false,c=false,d=false,e=false,f=false;
				if (rdbtnAtrasoXSbs.isSelected())
					a=true;
				if (rdbtnFairnessXUser.isSelected())
					b=true;
				if (rdbtnRateXUser.isSelected())
					c=true;
				if (rdbtnSbsXMcss.isSelected())
					d=true;
				if (rdbtnSBxUser.isSelected())
					e=true;
				if (rdbtnSchedulingStatus.isSelected())
					f=true;
				
				try {
					newSch.doUserFriendlySimulation(a,b,c,d,e,f);
				} catch (IOException g) {
					g.printStackTrace();
				}
				
			}
		});
	}
}
