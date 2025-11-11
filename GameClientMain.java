import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GameClientMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUserName;
	private JTextField clientIP;
	private JTextField Port_Number;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameClientMain frame = new GameClientMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameClientMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 10, 478, 275);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setBounds(24, 40, 107, 28);
		panel.add(lblNewLabel);
		
		textUserName = new JTextField();
		textUserName.setBounds(113, 44, 107, 20);
		panel.add(textUserName);
		textUserName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("IPAddress");
		lblNewLabel_1.setBounds(245, 33, 85, 14);
		panel.add(lblNewLabel_1);
		
		clientIP = new JTextField();
		clientIP.setText("127.0.0.1");
		clientIP.setBounds(342, 30, 107, 20);
		panel.add(clientIP);
		clientIP.setColumns(10);
		
		JButton gameStartBtn = new JButton("게임 시작");
		gameStartBtn.setBounds(183, 186, 90, 22);
		panel.add(gameStartBtn);
		
		JLabel lblNewLabel_2 = new JLabel("인디언 포커 게임");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 23));
		lblNewLabel_2.setBounds(139, 124, 178, 20);
		panel.add(lblNewLabel_2);
		
		Port_Number = new JTextField();
		Port_Number.setText("3000");
		Port_Number.setBounds(342, 64, 107, 20);
		panel.add(Port_Number);
		Port_Number.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Port Number");
		lblNewLabel_3.setBounds(245, 67, 72, 14);
		panel.add(lblNewLabel_3);
		
		Myaction action = new Myaction();
		gameStartBtn.addActionListener(action);
		textUserName.addActionListener(action);
		clientIP.addActionListener(action);
		Port_Number.addActionListener(action);
		
		

	}
	
	class Myaction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String userName = textUserName.getText().trim();
			String ip = clientIP.getText().trim();
			String port = Port_Number.getText().trim();
			setVisible(false);
		}
		
		
		
	}
}
