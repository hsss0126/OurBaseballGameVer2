package gameUI;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import connection.UserConnection;
import etc.ResponseCode;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginFrame extends JFrame{

	private static final long serialVersionUID = 1L;
//------------------------------------------------------------
	private JPanel imagePanel;
		private JPanel one;
	
	private JPanel loginPanel;
		private JPanel two;
		private JLabel nickNameLabel;
		private JLabel pwLabel;
		private JTextField nickNameField;
		private JPasswordField passwordField;
		private JButton loginBtn;
		private JButton joinBtn;

	private Font font = new Font("맑은 고딕",Font.BOLD,18);
	
	private Color color1 = new Color(30,204,208);	//청록

	private UserConnection userConnection;
	
	private String nickName;
	private String password;
//-------------------------------------------------------------

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginFrame();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public LoginFrame() {
		initialize();
	}

	private void initialize() {
		userConnection = new UserConnection();
		
		setLayout(new GridLayout(2,1));
		setBackground(Color.WHITE);
		setSize(400,600);
		//setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		setLocation(1100,100);
		setResizable(false);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//이미지 들어갈 자리
		imagePanel = new JPanel();
			imagePanel.setBackground(Color.WHITE);
			imagePanel.setLayout(null);
		add(imagePanel);
		
		//로그인 정보 입력 란
		loginPanel = new JPanel();
			loginPanel.setBackground(Color.WHITE);
			loginPanel.setLayout(null);
		add(loginPanel);
		
		details();	

	}
	
	void details() {
		
		/*두번째 패널 - 로그인 정보 입력 */
		two = new JPanel();
			two.setBackground(Color.white);
			two.setLayout(null);
			two.setSize(400, 300);
			two.setLocation(0, 0);
		loginPanel.add(two);
		
		// 닉네임 & 패스워드
		nickNameLabel = new JLabel("닉 네 임  :");
			nickNameLabel.setBackground(Color.WHITE);
			nickNameLabel.setFont(font);
			nickNameLabel.setSize(90, 30);
			nickNameLabel.setLocation(60, 50);
		two.add(nickNameLabel);
		
		pwLabel = new JLabel("패스워드 :");
			pwLabel.setBackground(Color.WHITE);
			pwLabel.setFont(font);
			pwLabel.setSize(90, 30);
			pwLabel.setLocation(60, 100);
		two.add(pwLabel);
		
		nickNameField = new JTextField();
			nickNameField.setFont(font);
			nickNameField.setSize(190, 30);
			nickNameField.setLocation(155, 53);
		two.add(nickNameField);
		
		passwordField = new JPasswordField();
			passwordField.setFont(font);
			passwordField.setSize(190, 30);
			passwordField.setLocation(155, 103);
		two.add(passwordField);
		
		//회원가입 버튼
		joinBtn = new JButton("회 원 가 입");
			joinBtn.setFont(font);
			joinBtn.setSize(140, 50);
			joinBtn.setLocation(55, 200);
			joinBtn.setBackground(color1);
			joinBtn.setBorder(new MatteBorder(1,1,1,1, Color.black));
			
			joinBtn.addActionListener(new ActionListener() {
				@Override
	 			public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
	 				new JoinFrame();	//회원가입버튼 클릭시 회원가입 창 띄우기
	 			}
	 		});
		two.add(joinBtn);
		
		//로그인 버튼
		loginBtn = new JButton("로 그 인");
			loginBtn.setFont(font);
			loginBtn.setSize(140, 50);
			loginBtn.setLocation(210, 200);
			loginBtn.setBackground(color1);
			loginBtn.setBorder(new MatteBorder(1,1,1,1, Color.black));
			
			loginBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					nickName = nickNameField.getText();
					password = new String(passwordField.getPassword());
					String result;
					
					result = userConnection.loginConnection(nickName, password.toLowerCase());
					
					switch(Integer.parseInt(result)) {
					case ResponseCode.connect_error:
						System.out.println("서버 연결 오류");
						break;
					case ResponseCode.user_login_success:
						System.out.println("로그인 성공");
						new MainFrame(nickName);
						dispose();
						break;
					case ResponseCode.user_id_error:
						System.out.println("해당 닉네임 중복");
						break;
					case ResponseCode.user_pwd_error:
						System.out.println("패스워드 오류");
						break;
					}
	            }
			});
		two.add(loginBtn);

	}

}
