import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GameClientView extends JFrame {

	private JPanel contentPane;
    private JTextField txtInput;
    private String UserName;
    private JButton btnSend;
    private JTextArea textArea;
    private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
    private Socket socket; // 연결소켓
    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private DataOutputStream dos;
    private JLabel lblUserName;

	/**
	 * Create the frame.
	 */
	public GameClientView(String username, String ip, String port) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 392, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 352, 340);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		txtInput = new JTextField();
		txtInput.setBounds(91, 365, 56, 40);
		contentPane.add(txtInput);
		txtInput.setColumns(10);

		btnSend = new JButton("Play");
		btnSend.setBounds(288, 364, 76, 40);
		contentPane.add(btnSend);
		
		lblUserName = new JLabel("Name");
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(12, 364, 67, 40);
		contentPane.add(lblUserName);
		setVisible(true);
		
		UserName = username;
		lblUserName.setText(username + ">");
		
		JButton Bettingbtn = new JButton("배팅(10단위)");
		Bettingbtn.setBounds(174, 364, 102, 40);
		contentPane.add(Bettingbtn);
		AppendText("User " + username + " connecting " + ip + " " + port + "\n");
		
		
		
		 try {
	            socket = new Socket(ip, Integer.parseInt(port));
	            is = socket.getInputStream();
	            dis = new DataInputStream(is);
	            os = socket.getOutputStream();
	            dos = new DataOutputStream(os);

	            SendMessage("/login " + UserName);
	            Serveraccept net = new Serveraccept(socket);
	            net.start();
	            PlayShow show = new PlayShow();
	            Betting bett = new Betting();
	            
	            btnSend.addActionListener(show); //게임시작 버튼으로 눌렀을때 서버로 데이터 받아옴
	            Bettingbtn.addActionListener(bett); //배팅 버튼으로 눌렀을때 서버로 값 올리라는 요청 보냄
	        } catch (NumberFormatException | IOException e) {
	            e.printStackTrace();
	            AppendText("connect error");
	        }

	}
	
	 // 화면에 출력
    public void AppendText(String msg) {
        textArea.append(msg);
        textArea.setCaretPosition(textArea.getText().length());
    }
    
    // Server Message를 수신해서 화면에 표시
    class Serveraccept extends Thread {
    	//서버 받는 부분 스레드로 계속 받음
    	private DataInputStream dis;

        public Serveraccept(Socket socket) {
            try {
                dis = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                System.out.println("입력 스트림 에러");
            }
        }
    	
    	public void run() {
            while (true) {
                try {
                    // Use readUTF to read messages
                    String msg = dis.readUTF();
                    AppendText(msg);
                } catch (IOException e) {
                    AppendText("dis.read() error");
                    try {
                        dos.close();
                        dis.close();
                        socket.close();
                        break;
                    } catch (Exception ee) {
                        break;
                    }
                }
            }
        }
    }
    
 // Server에게 데이터로 전송(처음에 있는 username, 포트, ip전달할때만 사용
    public void SendMessage(String msg) {
        try {
            // Use writeUTF to send messages
            dos.writeUTF(msg);
        } catch (IOException e) {
            AppendText("dos.write() error");
            try {
                dos.close();
                dis.close();
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.exit(0);
            }
        }
    }
    class PlayShow implements ActionListener // 내부클래스로 서버로부터 정보 받는 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 눌렀을때 서버로부터 새로운 정보 받기
			try {
		        dos.writeUTF("/requestCard");  //  서버에게 새 카드 요청 "/requestCard"로 보내기에 이 문자열로 서버에서 처리하면됌
		        dos.flush();
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
			
		}
	}
    class Betting implements ActionListener //내부클래스로 배팅 이벤트 처리 클래스
    {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		// Bettingbtn을 눌렀을때 서버로 발생 이벤트 문자 전달
    		try {
    			dos.writeUTF("/BettingUp"); //서버에게 배팅 값 올리라는 요청 보냄
    			dos.flush();
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    	}
    }
}
