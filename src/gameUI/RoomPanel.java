package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connection.GameInfoConnection;
import connection.RoomInfoConnection;
import connection.UserConnection;
import dto.GameInfo;
import dto.RoomInfo;
import dto.User;

public class RoomPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private String[] initCase = {"HOST", "AWAY", "BOTH"};
	
	private MainFrame mainFrame;
	private User myInfo;
	private RoomInfo myRoomInfo;
	private GameInfo myGameInfo;
	
	//방주인,상대방 프로필 & 방 정보
		private JPanel onePanel;
			private JPanel userPanel;
				private JPanel uPanel;
				private JLabel userNameLabel;		//user닉네임
				private JLabel userNameText;
				private JLabel userRecordLabel;		//user전적
				private JLabel userRecordText;
				private JLabel userStateText;
				
			private JPanel enemyPanel;
				private JPanel ePanel;
				private JLabel enemyNameLabel;		//enemy닉네임
				private JLabel enemyNameText;
				private JLabel enemyRecordLabel;		//enemy전적
				private JLabel enemyRecordText;
				private JLabel enemyStateText;
				
			private JPanel controlPanel;
				private JLabel roomNameLabel;		//방이름
				private JLabel roomNameText;
				private JLabel roomNumLabel;		//방번호
				private JLabel roomNumText;
				private JPanel levelPanel;			//난이도수정
					private JButton editBtn;
					private JButton okBtn;
					private JPanel panel;
					private ButtonGroup group;
					private JRadioButton level1;
					private JRadioButton level2;
					private JRadioButton level3;
				private JLabel numInputLabel1;		//숫자입력
				private JLabel numInputLabel2;
				private JTextField numInputText;
				private JButton readyBtn;
				private JButton exitBtn;
		
		//채팅창 패널
		private JPanel twoPanel;
			private JPanel talkingPanel;
				private JTextArea talkArea;
				private JPanel talkListPanel;	//대화창(리스트뷰)
				private JTextField talkInput;	//대화입력
				private JButton talkBtn;		//대화전송
		
		private Color color1 = new Color(202,236,244);	//연하늘
		private Color color2 = new Color(30,204,208);	//청록
		private Color color5 = new Color(217,211,210);	//연그레이
		private Color color6 = new Color(92,84,82);		//조금 더 진한 연그레이
		
		private BevelBorder border;
		
		private JSONParser parser;
		private UserConnection userConnection;
		private RoomInfoConnection roomInfoConnection;
		private GameInfoConnection gameInfoConnection;
		
		private User enemyInfo;
		private int caseIndex;
		private Boolean rockFlag;
		
		private HostThread hostThread;
		private LevelThread levelThread;
		private ExitThread exitThread;
		private ReadyThread readyThread;
		private Boolean hostFlag;
		private Boolean levelFlag;
		private Boolean exitFlag;
		private Boolean readyFlag;
	
	public RoomPanel(MainFrame mf, int index) {
		this.mainFrame = mf;
		parser = new JSONParser();
		initialize(index);
	}
	
	private void initialize(int index) {
		setLayout(new GridLayout(2,1));
		setBackground(Color.WHITE);
		setSize(800,600);
		setVisible(true);
		Dimension frameSize = this.getSize();	//프레임크기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	//모니터크기
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
		myInfo = mainFrame.getMyInfo();
		myRoomInfo = mainFrame.getMyRoomInfo();
		
		userConnection = mainFrame.getUserConnection();
		roomInfoConnection = mainFrame.getRoomInfoConnection();
		gameInfoConnection = mainFrame.getGameInfoConnection();
		
		myGameInfo = mainFrame.gameInfoParse(gameInfoConnection.findByRoomIdConnection(myRoomInfo.getId()));
		mainFrame.setMyGameInfo(myGameInfo);
		
		border = new BevelBorder(BevelBorder.RAISED);//3차원적인 테두리 효과를 위한것이고 양각의 옵션을 줌
		
		/* 유저 정보 & 방 정보 */
		onePanel = new JPanel();
			onePanel.setBackground(Color.lightGray);
			onePanel.setLayout(new GridLayout(1,3));
			onePanel.setSize(400, 300);
			onePanel.setLocation(0, 0);
		add(onePanel);
		
		/* 대화창  */
		twoPanel = new JPanel();
			twoPanel.setBackground(Color.white);
			twoPanel.setLayout(null);
			twoPanel.setSize(400, 300);
			twoPanel.setLocation(0, 0);
		add(twoPanel);
		
		details_1(index);
		details_2();
		
		
		//방 클라이언트 소켓 생성
		mainFrame.connectClient(myRoomInfo.getId(), myInfo.getNickName(), this);
	}
	
	void details_1(int index) {
		//user패널
		userPanel = new JPanel();
			userPanel.setBackground(Color.white);
			userPanel.setLayout(null);
			userPanel.setSize(250, 300);
			userPanel.setBounds(20,20,250,300);
			userPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
	
			uPanel = new JPanel();
				uPanel.setLayout(null);
				uPanel.setBackground(Color.white);
				uPanel.setFont(new Font("맑은 고딕",Font.BOLD,20));
				uPanel.setSize(250, 270);
				uPanel.setLocation(7, 7);
				uPanel.setBorder(new TitledBorder(new LineBorder(color1, 8), "   나   ", 0, 0, new Font("맑은 고딕",Font.BOLD,20),Color.black));
				
				userNameLabel = new JLabel("닉 네 임");
				userNameLabel.setOpaque(true);
				userNameLabel.setBackground(Color.white);
				userNameLabel.setFont(new Font("맑은 고딕",Font.BOLD,18));
				userNameLabel.setSize(200, 30);
				userNameLabel.setLocation(25, 40);
			uPanel.add(userNameLabel);
			
			userNameText = new JLabel(myInfo.getNickName());
				userNameText.setOpaque(true);
				userNameText.setBackground(Color.white);
				userNameText.setFont(new Font("맑은 고딕",Font.BOLD,16));
				userNameText.setSize(150, 30);
				userNameText.setLocation(55, 75);
			uPanel.add(userNameText);
			
			userRecordLabel = new JLabel("전 적");
				userRecordLabel.setOpaque(true);
				userRecordLabel.setBackground(Color.white);
				userRecordLabel.setFont(new Font("맑은 고딕",Font.BOLD,18));
				userRecordLabel.setSize(200, 30);
				userRecordLabel.setLocation(25, 120);
			uPanel.add(userRecordLabel);
			
			userRecordText = new JLabel(myInfo.getRecord());
				userRecordText.setOpaque(true);
				userRecordText.setBackground(Color.white);
				userRecordText.setFont(new Font("맑은 고딕",Font.BOLD,16));
				userRecordText.setSize(150, 30);
				userRecordText.setLocation(55, 155);
			uPanel.add(userRecordText);
				
				userStateText = new JLabel("  준 비 완 료  ");
					userStateText.setForeground(Color.white);
					userStateText.setBackground(color1);
					userStateText.setOpaque(true);
					userStateText.setFont(new Font("맑은 고딕",Font.BOLD,28));
					userStateText.setSize(240, 50);
					userStateText.setLocation(5, 200);
					userStateText.setHorizontalAlignment(JLabel.CENTER);
					userStateText.setVisible(false);//처음에는 안보이게
				uPanel.add(userStateText);
			userPanel.add(uPanel);
			
		onePanel.add(userPanel);
		
		//enemy패널
		enemyPanel = new JPanel();
			enemyPanel.setBackground(Color.white);
			enemyPanel.setLayout(null);
			enemyPanel.setSize(250, 300);
			enemyPanel.setBounds(20,20,250,300);
			enemyPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
			
			ePanel = new JPanel();
			ePanel.setLayout(null);
			ePanel.setBackground(Color.white);
			ePanel.setFont(new Font("맑은 고딕",Font.BOLD,20));
			ePanel.setSize(250, 270);
			ePanel.setLocation(7, 7);
			ePanel.setBorder(new TitledBorder(new LineBorder(color5, 8), "  상 대 방   ", 0, 0, new Font("맑은 고딕",Font.BOLD,20), Color.black));
			
			enemyNameLabel = new JLabel("닉 네 임");
			enemyNameLabel.setOpaque(true);
			enemyNameLabel.setBackground(Color.white);
			enemyNameLabel.setFont(new Font("맑은 고딕",Font.BOLD,18));
			enemyNameLabel.setSize(200, 30);
			enemyNameLabel.setLocation(25, 40);
			
		ePanel.add(enemyNameLabel);
		
		enemyNameText = new JLabel();
			enemyNameText.setOpaque(true);
			enemyNameText.setBackground(Color.white);
			enemyNameText.setFont(new Font("맑은 고딕",Font.BOLD,16));
			enemyNameText.setSize(150, 30);
			enemyNameText.setLocation(55, 75);
			
		ePanel.add(enemyNameText);
	
		enemyRecordLabel = new JLabel("전 적");
			enemyRecordLabel.setOpaque(true);
			enemyRecordLabel.setBackground(Color.white);
			enemyRecordLabel.setFont(new Font("맑은 고딕",Font.BOLD,18));
			enemyRecordLabel.setSize(200, 30);
			enemyRecordLabel.setLocation(25, 120);
		ePanel.add(enemyRecordLabel);
	
		enemyRecordText = new JLabel();
			enemyRecordText.setOpaque(true);
			enemyRecordText.setBackground(Color.white);
			enemyRecordText.setFont(new Font("맑은 고딕",Font.BOLD,16));
			enemyRecordText.setSize(150, 30);
			enemyRecordText.setLocation(55, 155);
		ePanel.add(enemyRecordText);
	
			enemyStateText = new JLabel("  준 비 완 료  ");
				enemyStateText.setForeground(Color.white);
				enemyStateText.setBackground(color5);
				enemyStateText.setOpaque(true);
				enemyStateText.setFont(new Font("맑은 고딕",Font.BOLD,28));
				enemyStateText.setSize(240, 50);
				enemyStateText.setLocation(5, 200);
				enemyStateText.setHorizontalAlignment(JLabel.CENTER);
				enemyStateText.setVisible(false);
			ePanel.add(enemyStateText);
			
			enemyPanel.add(ePanel);
		onePanel.add(enemyPanel);
		
		//control패널
		controlPanel = new JPanel();
			controlPanel.setBackground(Color.white);
			controlPanel.setLayout(null);
			controlPanel.setSize(250, 300);
			controlPanel.setBounds(20,20,250,300);
			controlPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
			
			roomNumLabel = new JLabel(" No. ");
				roomNumLabel.setBackground(color1);
				roomNumLabel.setOpaque(true);
				roomNumLabel.setFont(new Font("맑은 고딕",Font.BOLD|Font.ITALIC,12));
				roomNumLabel.setSize(35, 30);
				roomNumLabel.setLocation(5, 10);
				roomNumLabel.setBorder(border);
			controlPanel.add(roomNumLabel);
			
			roomNumText = new JLabel(Integer.toString(myRoomInfo.getId()));
				roomNumText.setBackground(Color.white);
				roomNumText.setFont(new Font("맑은 고딕",Font.BOLD,14));
				roomNumText.setSize(20, 30);
				roomNumText.setLocation(45, 10);
			controlPanel.add(roomNumText);
			
			
			roomNameLabel = new JLabel(" 방이름 ");
				roomNameLabel.setBackground(color1);
				roomNameLabel.setOpaque(true);
				roomNameLabel.setFont(new Font("맑은 고딕",Font.BOLD|Font.ITALIC,12));
				roomNameLabel.setSize(50, 30);
				roomNameLabel.setLocation(70, 10);
				roomNameLabel.setBorder(border);
			controlPanel.add(roomNameLabel);
			
			roomNameText = new JLabel(myRoomInfo.getRoomName());
				roomNameText.setBackground(Color.white);
				roomNameText.setFont(new Font("맑은 고딕",Font.BOLD, 13));
				roomNameText.setSize(130, 30);
				roomNameText.setLocation(125, 10);
			controlPanel.add(roomNameText);
			
			levelPanel = new JPanel();
				levelPanel.setBackground(Color.white);
				levelPanel.setLayout(null);
				levelPanel.setSize(240, 100);
				levelPanel.setLocation(13, 50);
				levelPanel.setBorder(new TitledBorder(new LineBorder(color6, 3), "  게임 난 이 도   ", 0, 0, new Font("맑은 고딕",Font.BOLD,15), Color.black));
				
				panel = new JPanel();
					panel.setBackground(Color.white);
					panel.setLayout(new GridLayout(1,3));
					panel.setSize(220, 30);
					panel.setLocation(10, 30);
					
					group = new ButtonGroup();
					level1 = new JRadioButton("3자리 수");
						level1.setBackground(Color.WHITE);
						level1.setFont(new Font("맑은 고딕",Font.BOLD,12));
						level1.setSize(90,30);
						level1.setEnabled(false);
						level1.setActionCommand("3");
						if(myRoomInfo.getLevel()==3) {
							level1.setSelected(true);
						}
						group.add(level1);
						
					panel.add(level1);
				
					level2 = new JRadioButton("4자리 수");
						level2.setBackground(Color.WHITE);
						level2.setFont(new Font("맑은 고딕",Font.BOLD,12));
						level2.setSize(90,30);
						level2.setEnabled(false);
						level2.setActionCommand("4");
						if(myRoomInfo.getLevel()==4) {
							level2.setSelected(true);
						}
						group.add(level2);
						
					panel.add(level2);
				
					level3 = new JRadioButton("5자리 수");
						level3.setBackground(Color.WHITE);
						level3.setFont(new Font("맑은 고딕",Font.BOLD,12));
						level3.setSize(90,30);
						level3.setEnabled(false);
						level3.setActionCommand("5");
						if(myRoomInfo.getLevel()==5) {
							level3.setSelected(true);
						}
						group.add(level3);
						
					panel.add(level3);
					
					
					//난이도 수정버튼
					numInputText = new JTextField();
					editBtn = new JButton("수 정");
						editBtn.setBackground(color2);
						editBtn.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 11));
						editBtn.setForeground(Color.black);
						editBtn.setSize(55, 20);
						editBtn.setLocation(115, 70);
						editBtn.setBorder(new MatteBorder(1,1,1,1, color6));
						
						editBtn.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
									level1.setEnabled(true);
									level2.setEnabled(true);
									level3.setEnabled(true);
							}
						});
					levelPanel.add(editBtn);
					
					//수정완료 버튼
					okBtn = new JButton("확 인");
						okBtn.setBackground(color2);
						okBtn.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 11));
						okBtn.setForeground(Color.black);
						okBtn.setSize(55, 20);
						okBtn.setLocation(172, 70);
						okBtn.setBorder(new MatteBorder(1,1,1,1, color6));
						
						okBtn.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								if(group.getSelection().getActionCommand().equals(
										Integer.toString(myRoomInfo.getLevel()))){
									level1.setEnabled(false);
									level2.setEnabled(false);
									level3.setEnabled(false);
								} else {
									String updateResult;
									myRoomInfo.setLevel(Integer.parseInt(group.getSelection().getActionCommand()));
									updateResult = roomInfoConnection.updateConnection(myRoomInfo);
									
									try {
										parser = new JSONParser();	
										JSONObject json = (JSONObject) parser.parse(updateResult);
										myRoomInfo.setLevel(Integer.parseInt((String)json.get("level")));
										System.out.println("level변경 "+myRoomInfo.toString());
										mainFrame.setMyRoomInfo(myRoomInfo);
									} catch(ParseException pe) {
										pe.printStackTrace();
									}
									
									level1.setEnabled(false);
									level2.setEnabled(false);
									level3.setEnabled(false);
								}
								initLevel();
							}
						});
					levelPanel.add(okBtn);
					
				levelPanel.add(panel);
			controlPanel.add(levelPanel);
			
			numInputLabel1 = new JLabel("나 의");
				numInputLabel1.setBackground(Color.white);
				numInputLabel1.setFont(new Font("맑은고딕",Font.BOLD,15));
				numInputLabel1.setSize(50, 20);
				numInputLabel1.setLocation(40, 170);
			controlPanel.add(numInputLabel1);
			
			numInputLabel2 = new JLabel("숫 자");
				numInputLabel2.setBackground(Color.white);
				numInputLabel2.setFont(new Font("맑은고딕",Font.BOLD,15));
				numInputLabel2.setSize(50, 20);
				numInputLabel2.setLocation(40, 190);
			controlPanel.add(numInputLabel2);
			
			//숫자입력 란
			numInputText = new JTextField();
				numInputText.setFont(new Font("맑은고딕",Font.BOLD,35));
				numInputText.setSize(150, 40);
				numInputText.setLocation(85, 170);
				numInputText.setHorizontalAlignment(JTextField.CENTER);
				
				numInputText.addKeyListener(new KeyListener() {
					
					//숫자만 입력받음
					@Override
					public void keyTyped(KeyEvent e) {
						char c = e.getKeyChar();
						
						if (!Character.isDigit(c)) {
							System.out.println(c+"입력제거");
							e.consume();
							return;
						}
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						
					}
				});
			controlPanel.add(numInputText);
			
			//레디버튼
			readyBtn = new JButton();
				readyBtn.setBackground(color2);
				readyBtn.setSize(120,50);
				readyBtn.setFont(new Font("맑은 고딕",Font.BOLD,20));
				readyBtn.setLocation(10, 225);
				readyBtn.setBorder(new MatteBorder(3,3,3,3, color6));
		
				readyBtn.addActionListener(new ActionListener() {
			
					@Override
					public void actionPerformed(ActionEvent e) {
						String number;
						switch(initCase[caseIndex]) {
						case "HOST":
							JOptionPane.showMessageDialog(mainFrame, "아직 상대방이 입장하지 않았습니다!", "확인 메세지", JOptionPane.WARNING_MESSAGE);
							break;
						//방 레벨에 맞는 숫자(중복확인해야함)를 입력 후 방의 gameInfo에 저장(setting)
						case "AWAY":
							//준비 상태일 때 해제하기 (나가기 불가 해제 / 숫자 변경 불가 해제)
							if(rockFlag) {
								myGameInfo = mainFrame.gameInfoParse(gameInfoConnection.gameEndConnection(myGameInfo.getId()));
								rockFlag = false;
								numInputText.setEnabled(true);
								userStateText.setVisible(false);
								exitBtn.setEnabled(true);
							} 
							//준비 하기 (숫자 입력, 나가기 불가, 숫자변경 불가)
							else {
								number = numInputText.getText();
								System.out.println("입력 숫자:"+number);
								//아무 숫자도 입력하지 않았다면
								if(number.isEmpty()) {
									JOptionPane.showMessageDialog(mainFrame, "숫자를 입력해주세요", "경고", JOptionPane.WARNING_MESSAGE);
								}
								//레벨과 숫자길이가 같지 않다면
								else if(number.length() != myRoomInfo.getLevel()) {
									JOptionPane.showMessageDialog(mainFrame, myRoomInfo.getLevel()+"자리 숫자를 입력해주세요", "경고", JOptionPane.WARNING_MESSAGE);
								}
								//레벨과 숫자길이가 일치한다면
								else if(number.length() == myRoomInfo.getLevel()) {
									//숫자 중복확인(true시 중복 x)
									if(checkInputNum(number)) {
										//gameInfo에 저장
										myGameInfo.setAwayNumber(number);
										myGameInfo = mainFrame.gameInfoParse(gameInfoConnection.settingConnection(myGameInfo));
										mainFrame.setMyGameInfo(myGameInfo);
										
										rockFlag = true;
										numInputText.setEnabled(false);
										userStateText.setVisible(true);
										exitBtn.setEnabled(false);
									} 
									//입력한 숫자가 중복이라면
									else {
										JOptionPane.showMessageDialog(mainFrame, "중복되지 않는 숫자를 입력해주세요", "경고", JOptionPane.WARNING_MESSAGE);
									}
								}
							}
							break;
						//away의 준비여부를 확인 후 방 레벨에 맞는 숫자 입력 후 game시작(setting&update)
						case "BOTH":
							number = numInputText.getText();
							System.out.println("입력 숫자:"+number);
							//아무 숫자도 입력하지 않았다면
							if(number.isEmpty()) {
								JOptionPane.showMessageDialog(mainFrame, "숫자를 입력해주세요", "경고", JOptionPane.WARNING_MESSAGE);
							}
							//레벨과 숫자길이가 같지 않다면
							else if(number.length() != myRoomInfo.getLevel()) {
								JOptionPane.showMessageDialog(mainFrame, myRoomInfo.getLevel()+"자리 숫자를 입력해주세요", "경고", JOptionPane.WARNING_MESSAGE);
							}
							//레벨과 숫자길이가 일치한다면
							else if(number.length() == myRoomInfo.getLevel()) {
								//숫자 중복확인(true시 중복 x)
								if(checkInputNum(number)) {
									//상대가 준비상태라면
									if(enemyStateText.isVisible()) {
										//gameInfo에 저장 후 게임 시작
										myGameInfo.setHostNumber(number);
										myGameInfo = mainFrame.gameInfoParse(gameInfoConnection.settingConnection(myGameInfo));
										mainFrame.setMyGameInfo(myGameInfo);
										
										if(exitThread.isAlive()) exitFlag = false;
										if(readyThread.isAlive()) readyFlag = false;
										
										mainFrame.closeClient();
										mainFrame.addGamePanel();
										mainFrame.getCardLayout().show(mainFrame.getContentPane(), "GamePanel");	//대기실로 나가~
										mainFrame.removePanel(RoomPanel.this);
									}
									//상대가 준비상태가 아니라면
									else {
										JOptionPane.showMessageDialog(mainFrame, "상대방이 아직 숫자를 입력하지 않았습니다.\n", "경고", JOptionPane.WARNING_MESSAGE);
									}
								} 
								//입력한 숫자가 중복이라면
								else {
									JOptionPane.showMessageDialog(mainFrame, "중복되지 않는 숫자를 입력해주세요", "경고", JOptionPane.WARNING_MESSAGE);
								}
							}
							break;
						}
						
					}
				});
			controlPanel.add(readyBtn);
				//나가기 버튼
				exitBtn = new JButton(" E X I T ");
				exitBtn.setBackground(color2);
				exitBtn.setSize(120,50);
				exitBtn.setFont(new Font("맑은 고딕",Font.BOLD, 20));
				exitBtn.setLocation(135, 225);
				exitBtn.setBorder(new MatteBorder(3,3,3,3, color6));
				
				exitBtn.addActionListener(new ActionListener() {
					
						@Override
						public void actionPerformed(ActionEvent e) {
							switch(initCase[caseIndex]) {
							//나가는 사람이 host일 경우 중 상대방이 없을 때 방 삭제
							case "HOST":
								if(hostThread.isAlive()) hostFlag = false;
								
								roomInfoConnection.deleteConnection(myRoomInfo.getId());
								break;
							//나가는 사람이 away일 경우 그냥 나가기
							case "AWAY":
								if(levelThread.isAlive()) levelFlag = false;
								if(exitThread.isAlive()) exitFlag = false;
								
								myRoomInfo.setAwayId(0);
								myRoomInfo.setUserCount(1);
								roomInfoConnection.updateConnection(myRoomInfo);	//awayId를 0으로 업데이트
								break;
							//나가는 사람이 host일 경우 중 상대방이 존재할 때 host위임
							case "BOTH":
								if(exitThread.isAlive()) exitFlag = false;
								if(readyThread.isAlive()) readyFlag = false;
								
								myRoomInfo.setHostId(myRoomInfo.getAwayId());
								myRoomInfo.setAwayId(0);
								myRoomInfo.setUserCount(1);
								roomInfoConnection.updateConnection(myRoomInfo);	//hostId를 awayId로 수정 후 awayId를 0으로 업데이트
								break;
							
							}
							
							mainFrame.setMyRoomInfo(null);
							//방 나갈 때 방 클라이언트 소켓 닫기
							mainFrame.closeClient();
							mainFrame.addWaitingPanel();
							mainFrame.getCardLayout().show(mainFrame.getContentPane(), "WaitingPanel");	//대기실로 나가~
							mainFrame.removePanel(RoomPanel.this);
						}
					});
				controlPanel.add(exitBtn);			
			
		onePanel.add(controlPanel);

		//"HOST","AWAY","BOTH"조건에 맞게 초기화
		initByCase(index);
		initLevel();
	}
	
	void details_2() {
		
		//대화창
		talkingPanel = new JPanel();
			talkingPanel.setBackground(color1);
			talkingPanel.setLayout(null);
			talkingPanel.setSize(795, 295);
			talkingPanel.setLocation(0, 0);
			talkingPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
					
			talkListPanel = new JPanel();
				talkListPanel.setBackground(color1);
				talkListPanel.setLayout(new BorderLayout());
				talkListPanel.setSize(795, 240);
				talkListPanel.setLocation(0,0);
			talkingPanel.add(talkListPanel);
					
			talkInput = new JTextField("");
				talkInput.setFont(new Font("맑은 고딕",Font.BOLD,20));
				talkInput.setSize(698, 43);
				talkInput.setLocation(0, 240);
				talkInput.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String msg = talkInput.getText();
						mainFrame.sendMessage(msg);
					}
				});
			talkingPanel.add(talkInput);
					
			talkBtn = new JButton("전 송");
				talkBtn.setBackground(color2);
				talkBtn.setFont(new Font("맑은 고딕",Font.BOLD,15));
				talkBtn.setForeground(Color.white);
				talkBtn.setSize(93, 40);
				talkBtn.setLocation(698, 240);
				talkBtn.setBorder(new MatteBorder(3,3,3,3, color6));
						
				talkBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String msg = talkInput.getText();
						mainFrame.sendMessage(msg);
					}
				});
			talkingPanel.add(talkBtn);
					
			talkArea = new JTextArea();
			talkArea.setBackground(color1);
			talkArea.setFont(new Font("맑은 고딕",Font.BOLD,15));
			talkListPanel.add(new JScrollPane(talkArea),"Center");	//대화창패널에 리스트붙이기
		twoPanel.add(talkingPanel);
	}
	//-----------------------채팅 메소드------------------------------------
	public void chatSetting(String roomName) {
		talkArea.setText(roomName + "에 입장하였습니다.\n");
		talkInput.setText("");
	}
	
	public void joinRoomChat(String nickName) {
		talkArea.append(nickName + "님이 입장하셨습니다.\n");
	}
	
	public void addChatText(String text) {
		talkArea.append(text+"\n");
		talkInput.setText("");
	}
	//--------------------------------------------------------------
	public void initLevel() {
		myRoomInfo = mainFrame.getMyRoomInfo();
		switch(myRoomInfo.getLevel()) {
		case 3 : level1.setSelected(true); break;
		case 4 : level2.setSelected(true); break;
		case 5 : level3.setSelected(true); break;
		}
		numInputText.setDocument(new JTextFieldLimit(myRoomInfo.getLevel()));	//글자수 입력 제한 
	}
	
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public void initByCase(int index) {
		this.caseIndex = index;
		rockFlag = false;
		System.out.println("당신은 "+initCase[caseIndex]+"입니다.");
		myRoomInfo = mainFrame.getMyRoomInfo();
		//HOST가 아닌 경우에만 (HOST일 땐 상대방이 없음)
		if(!initCase[caseIndex].equals("HOST")) {
			//BOTH인 경유에 away정보를 받아옴
			if(initCase[caseIndex].equals("BOTH")) {
				enemyInfo = mainFrame.userParse(userConnection.findByIdConnection(myRoomInfo.getAwayId()));
			}
			//AWAY인 경우에 host정보를 받아옴
			else if(initCase[caseIndex].equals("AWAY")){
				enemyInfo = mainFrame.userParse(userConnection.findByIdConnection(myRoomInfo.getHostId()));
			}
		}
				
		if(initCase[caseIndex].equals("AWAY")) {
			readyBtn.setText(" R E A D Y ");
			editBtn.setVisible(false);
			okBtn.setVisible(false);
		} else {
			readyBtn.setText(" S T A R T ");
			editBtn.setVisible(true);
			okBtn.setVisible(true);
		}
		
		if(initCase[caseIndex].equals("HOST")) {
			enemyNameLabel.setVisible(false);
			enemyRecordLabel.setVisible(false);
			enemyNameText.setText(null);
			enemyRecordText.setText(null);
		} else {
			enemyNameLabel.setVisible(true);
			enemyRecordLabel.setVisible(true);
			enemyNameText.setText(enemyInfo.getNickName());
			enemyRecordText.setText(enemyInfo.getRecord());
		}
		
		if(initCase[caseIndex].equals("HOST")) {
			hostThread = new HostThread();
			hostThread.start();
		}
		if(initCase[caseIndex].equals("AWAY")) {
			levelThread = new LevelThread();
			levelThread.start();
			exitThread = new ExitThread();
			exitThread.start();
			readyThread = new ReadyThread();
			readyThread.start();
		}
		if(initCase[caseIndex].equals("BOTH")) {
			exitThread = new ExitThread();
			exitThread.start();
			readyThread = new ReadyThread();
			readyThread.start();
		}
	}
	
	public boolean checkInputNum(String number) {
		for(int i=0; i<number.length(); i++) {
			char ch = number.charAt(i);
			for(int j=i+1; j<number.length(); j++) {
				if(ch == number.charAt(j)) {
					return false;
				}
			}
		}
		return true;
	}
	
	//JTextField에서 글자수 입력제한하기
	class JTextFieldLimit extends PlainDocument {
		private static final long serialVersionUID = 1L;
		private int limit;
	   private boolean toUppercase = false;
	    JTextFieldLimit(int limit) {
	      super();
	      this.limit = limit;
	   }
	    JTextFieldLimit(int limit, boolean upper) {
	      super();
	      this.limit = limit;
	      this.toUppercase = upper;
	   }
	 
	   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
	      if (str == null) {
	         return;
	      }
	      if ( (getLength() + str.length()) <= limit) {
	         if (toUppercase) {
	            str = str.toUpperCase();
	         }
	         super.insertString(offset, str, attr);
	      }
	   }
	}
	
	/* 
	 * 방장이 상대방 접속을 기다리는 스레드
	 */
	class HostThread extends Thread{
		private RoomInfo roomInfo;
		@Override
		public void run() {
			hostFlag = true;
			while(hostFlag) {
				try {
					roomInfo = mainFrame.roomInfoParse(roomInfoConnection.findByIdConnection(myRoomInfo.getId()));
					if(roomInfo != null) {
						if(roomInfo.getUserCount() == 2) {
							mainFrame.setMyRoomInfo(roomInfo);
							joinRoomChat(roomInfo.getAwayName());
							initByCase(2);
							hostFlag = false;
						}
					}
					Thread.sleep(100);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * 방에 입장한 사람이 
	 * 방의 레벨 변경을 기다리는 스레드
	 */
	class LevelThread extends Thread {
		private RoomInfo roomInfo;
		@Override
		public void run() {
			levelFlag = true;
			while(levelFlag) {
				try {
					roomInfo = mainFrame.roomInfoParse(roomInfoConnection.findByIdConnection(myRoomInfo.getId()));
					if(roomInfo != null) {
						if(myRoomInfo.getLevel() != roomInfo.getLevel()) {
							mainFrame.setMyRoomInfo(roomInfo);
							initLevel();
							
							JOptionPane.showMessageDialog(mainFrame, "게임 설정이 변경되었습니다.\n 숫자를 다시 설정해주세요.", "확인 메세지", JOptionPane.INFORMATION_MESSAGE);
							//레벨변경했다고 알림창 띄우기
						}
					}
					Thread.sleep(100);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * 상대방이 나갔는지 확인하는 스레드
	 */
	class ExitThread extends Thread {
		private RoomInfo roomInfo;
		@Override
		public void run() {
			exitFlag = true;
			while(exitFlag) {
				try {
					roomInfo = mainFrame.roomInfoParse(roomInfoConnection.findByIdConnection(myRoomInfo.getId()));
					if(roomInfo != null) {
						//HOST 입장에서 상대방이 나갔는지 확인
						if(initCase[RoomPanel.this.caseIndex].equals("BOTH")) {
							if(roomInfo.getAwayId() == 0) {
								exitFlag = false;
								readyFlag = false;
								mainFrame.setMyRoomInfo(roomInfo);
								initByCase(0);
	 						}
						} 
						//AWAY 입장에서 상대방이 나갔는지 확인
						else if(initCase[RoomPanel.this.caseIndex].equals("AWAY")) {
							//새로 조회한 방의 hostId 정보가 나의 id와 같을 경우 host가 나간 것 이므로 새로 초기화
							if(roomInfo.getHostId() == myInfo.getId()) {
								levelFlag = false;
								exitFlag = false;
								mainFrame.setMyRoomInfo(roomInfo);
								initByCase(0);
								JOptionPane.showMessageDialog(mainFrame, "방장이 되었습니다.", "확인 메세지", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
					Thread.sleep(100);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * BOTH입장에서 상대방이 준비상태인지 확인하는 스레드
	 * AWAY입장에서 게임 시작했는지 확인하는 스레드
	 */
	class ReadyThread extends Thread {
		GameInfo gameInfo;
		@Override
		public void run() {
			readyFlag = true;
			while(readyFlag) {
				try {
					gameInfo = mainFrame.gameInfoParse(gameInfoConnection.findByIdConnection(myGameInfo.getId()));
					if(gameInfo != null) {
						if(initCase[RoomPanel.this.caseIndex].equals("BOTH")) {
							//away가 준비상태라면
							if(gameInfo.getAwayNumber() != null) {
								if(!enemyStateText.isVisible()) {
									enemyStateText.setVisible(true);
								}
							} 
							//away가 준비상태가 아니라면
							else {
								if(enemyStateText.isVisible()) {
									enemyStateText.setVisible(false);
								}
							}
						}
						else if(initCase[RoomPanel.this.caseIndex].equals("AWAY")) {
							//host가 숫자를 입력했다면 게임 시작
							if(gameInfo.getHostNumber() != null) {
								enemyStateText.setVisible(true);
								if(exitThread.isAlive()) exitFlag = false;
								if(levelThread.isAlive()) levelFlag = false;
								readyFlag = false;
								
								mainFrame.closeClient();
								mainFrame.addGamePanel();
								mainFrame.getCardLayout().show(mainFrame.getContentPane(), "GamePanel");	//게임화면으로
								mainFrame.removePanel(RoomPanel.this);
							}
						}
						mainFrame.setMyGameInfo(gameInfo);
						myGameInfo = mainFrame.getMyGameInfo();
					}
					Thread.sleep(100);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
