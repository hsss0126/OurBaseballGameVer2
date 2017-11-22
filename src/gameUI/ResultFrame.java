package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

public class ResultFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private MainFrame mainFrame;
	
	private JPanel mainPanel;
	
	private JLabel resultLabel;
	private JPanel resultPanel;
		private JLabel hostLabel;		//userNickName표시
		
		private JLabel recordLabel;		//전적
		private JLabel recordText;
		
		private JLabel awayNumLabel;	//상대방 숫자
		private JLabel awayNumText;

		private JButton backBtn;		//방으로 돌아가기
		private JButton exitBtn;		//대기실로 나가기
	
	//이미지
	JPanel alphaPanel = new JPanel();
	private JLabel drawLabel1, drawLabel2;
	
	private Color color1 = new Color(202,236,244);	//연하늘
	private Color color2 = new Color(30,204,208);	//청록
	private Color color5 = new Color(217,211,210);	//연그레이
	private Color color6 = new Color(92,84,82);		//조금 더 진한 연그레이
	
	public ResultFrame(MainFrame mf) {
		mainFrame = mf;
		initialize();
	}
	
	private void initialize() {
		setLayout(null);
		setBackground(color1);
		setSize(400,500);
		setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		double width = this.getSize().getWidth()/2;		//프레임크기
		double height = this.getSize().getHeight()/2;		//프레임크기
		double x = mainFrame.getLocation().getX();	//모니터크기
		double y = mainFrame.getLocation().getY();	//모니터크기
		double mainWidth = mainFrame.getSize().getWidth()/2;
		double mainHeight = mainFrame.getSize().getHeight()/2;
		setLocation((int)(x+mainWidth-width), (int)(y+mainHeight-height));
		
		mainPanel = new JPanel();
			mainPanel.setBackground(color1);
			mainPanel.setLayout(null);
			mainPanel.setSize(400,500);
			mainPanel.setBorder(new MatteBorder(5,5,5,5, color5));
			mainPanel.setLocation(0, 0);
			
			resultLabel = new JLabel("★ 게 임 결 과 ★");
				resultLabel.setBackground(Color.white);
				resultLabel.setFont(new Font("맑은 고딕",Font.BOLD,35));
				resultLabel.setSize(300, 70);
				resultLabel.setLocation(70, 35);
			mainPanel.add(resultLabel);
			
			/* 나 vs 너 결과판 */
			resultPanel = new JPanel();
				resultPanel.setBackground(Color.WHITE);
				resultPanel.setLayout(null);
				resultPanel.setSize(400, 300);
				resultPanel.setBorder(new MatteBorder(0,5,0,5, color5));
				resultPanel.setLocation(0, 130);
			mainPanel.add(resultPanel);
			
			//이미지 넣기
			drawLabel1 = new JLabel();
			drawLabel2 = new JLabel();
			
				//배경
				ImageIcon icon1 = new ImageIcon(getClass().getClassLoader().getResource("image4.jpg"));
				drawLabel1.setIcon(icon1);
				drawLabel1.setSize(370,300);
				drawLabel1.setLocation(90,30);

				//위에 투명한 패널 붙임-----------------------------------------
				alphaPanel.setLayout(null);
				alphaPanel.setOpaque(true);
				alphaPanel.setBackground(new Color(255,255,255,170));
				alphaPanel.setSize(380, 300);
				alphaPanel.setLocation(5,70);
				resultPanel.add(alphaPanel);
			resultPanel.add(drawLabel1);
			
			//방으로 돌아가기 버튼
			backBtn = new JButton("다 시 하 기");
			backBtn.setFont(new Font("맑은 고딕",Font.BOLD,20));
			backBtn.setSize(130, 40);
			backBtn.setLocation(60, 445);
			backBtn.setBackground(color2);
			backBtn.setBorder(new MatteBorder(1,1,1,1, color6));
				
			backBtn.addActionListener(new ActionListener() {
					@Override
		 			public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						mainFrame.getCardLayout().show(mainFrame.getContentPane(), "RoomPanel");	//시작버튼클릭시 게임화면으로 전환
		 				dispose();
		 			}
		 		});
			mainPanel.add(backBtn);
			
			//대기실로 나가기 버튼
			exitBtn = new JButton("방 나 가 기");
			exitBtn.setFont(new Font("맑은 고딕",Font.BOLD,20));
			exitBtn.setSize(130, 40);
			exitBtn.setLocation(210, 445);
			exitBtn.setBackground(color2);
			exitBtn.setBorder(new MatteBorder(1,1,1,1, color6));
				
			exitBtn.addActionListener(new ActionListener() {
					@Override
		 			public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						mainFrame.getCardLayout().show(mainFrame.getContentPane(), "WaitingPanel");	//시작버튼클릭시 게임화면으로 전환
		 				dispose();
		 			}
		 		});
			mainPanel.add(exitBtn);
		add(mainPanel);
		
		//win();		//승
		lose();		//패
	}

	
	//win
	void win() {
		
		hostLabel = new JLabel("userName");
			hostLabel.setBackground(Color.white);
			//hostLabel.setOpaque(true);
			hostLabel.setHorizontalAlignment(JLabel.CENTER);
			hostLabel.setFont(new Font("맑은 고딕",Font.BOLD,30));
			hostLabel.setSize(180, 50);
			hostLabel.setLocation(110, 15);
		resultPanel.add(hostLabel);

		ImageIcon icon2 = new ImageIcon(getClass().getClassLoader().getResource("win2.png"));
			drawLabel2.setIcon(icon2);
			drawLabel2.setSize(250,170);
			drawLabel2.setLocation(125,0);
		alphaPanel.add(drawLabel2);
		
		recordLabel = new JLabel("전 적 :");
			recordLabel.setBackground(Color.white);
			recordLabel.setHorizontalAlignment(JLabel.CENTER);
			recordLabel.setFont(new Font("맑은 고딕",Font.BOLD, 26));
			recordLabel.setForeground(color6);
			recordLabel.setSize(100, 30);
			recordLabel.setLocation(90, 190);
		alphaPanel.add(recordLabel);		
		
		recordText = new JLabel("00 / 00");
			recordText.setBackground(Color.white);
			recordText.setHorizontalAlignment(JLabel.CENTER);
			recordText.setFont(new Font("맑은 고딕",Font.BOLD, 26));
			recordText.setForeground(color6);
			recordText.setSize(100, 30);
			recordText.setLocation(200, 190);
		alphaPanel.add(recordText);		
	}

	//lose
	void lose() {
		
		hostLabel = new JLabel("userName");
			hostLabel.setBackground(Color.white);
			//hostLabel.setOpaque(true);
			hostLabel.setHorizontalAlignment(JLabel.CENTER);
			hostLabel.setFont(new Font("맑은 고딕",Font.BOLD,30));
			hostLabel.setSize(180, 50);
			hostLabel.setLocation(110, 15);
		resultPanel.add(hostLabel);

		ImageIcon icon2 = new ImageIcon(getClass().getClassLoader().getResource("lose2.png"));
			drawLabel2.setIcon(icon2);
			drawLabel2.setSize(250,150);
			drawLabel2.setLocation(120,0);
		alphaPanel.add(drawLabel2);
		
		awayNumLabel = new JLabel("상대방 숫 자 :");
			awayNumLabel.setHorizontalAlignment(JLabel.CENTER);
			awayNumLabel.setFont(new Font("맑은 고딕",Font.BOLD, 23));
			awayNumLabel.setForeground(color6);
			awayNumLabel.setSize(150, 30);
			awayNumLabel.setLocation(70, 150);
		alphaPanel.add(awayNumLabel);	
		
		awayNumText = new JLabel("0 0 0 0 0");
			awayNumText.setHorizontalAlignment(JLabel.CENTER);
			awayNumText.setFont(new Font("맑은 고딕",Font.BOLD, 23));
			awayNumText.setForeground(color6);
			awayNumText.setSize(150, 30);
			awayNumText.setLocation(200, 150);
		alphaPanel.add(awayNumText);	
		
		recordLabel = new JLabel("전 적 :");
		recordLabel.setBackground(Color.white);
		recordLabel.setHorizontalAlignment(JLabel.CENTER);
		recordLabel.setFont(new Font("맑은 고딕",Font.BOLD, 26));
		recordLabel.setForeground(color6);
		recordLabel.setSize(100, 30);
		recordLabel.setLocation(95, 190);
		alphaPanel.add(recordLabel);		
	
		recordText = new JLabel("00 / 00");
		recordText.setBackground(Color.white);
		recordText.setHorizontalAlignment(JLabel.CENTER);
		recordText.setFont(new Font("맑은 고딕",Font.BOLD, 26));
		recordText.setForeground(color6);
		recordText.setSize(100, 30);
		recordText.setLocation(195, 190);
		alphaPanel.add(recordText);	
	}
	
}
