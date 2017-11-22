package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
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

import client.ClientBackground;
import connection.RoomInfoConnection;
import connection.UserConnection;
import dto.RoomInfo;
import dto.User;

public class RoomPanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	private String[] initCase = {"HOST", "AWAY", "BOTH"};
	
	private MainFrame mainFrame;
	private User myInfo;
	private RoomInfo myRoomInfo;
	
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
				private JButton startBtn;
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
		
		private ClientBackground client;
		private JSONParser parser;
		private UserConnection userConnection;
		private RoomInfoConnection roomInfoConnection;
		private User enemyInfo;
		private String enemyResult;
	
	public RoomPanel(MainFrame mf, int index) {
		this.mainFrame = mf;
		userConnection = new UserConnection();
		roomInfoConnection = new RoomInfoConnection();
		parser = new JSONParser();
		
		initialize(index);
	}
	
	private void initialize(int caseIndex) {
		System.out.println(initCase[caseIndex]);
		setLayout(new GridLayout(2,1));
		setBackground(Color.WHITE);
		setSize(800,600);
		//setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		Dimension frameSize = this.getSize();	//프레임크기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	//모니터크기
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		
		myInfo = mainFrame.getMyInfo();
		myRoomInfo = mainFrame.getMyRoomInfo();
		
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
		
		details_1(caseIndex);
		details_2();
		
		if(initCase[caseIndex].equals("HOST")) {
			new Thread(this).start();
		}
		
		client = new ClientBackground();
		client.connect(myRoomInfo.getId(), myInfo.getNickName(), this);
	}
	
	void details_1(int caseIndex) {
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
					userStateText.setVisible(true);//처음에는 안보이게
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
			ePanel.add(enemyStateText);
			
			enemyPanel.add(ePanel);
		onePanel.add(enemyPanel);
		//"HOST","AWAY","BOTH"조건에 맞게 초기화
		caseByInit(caseIndex);
		
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
						if(myRoomInfo.getLevel()==5) {
							level3.setSelected(true);
						}
						group.add(level3);
						
					panel.add(level3);
					
					if(myInfo.getId()==myRoomInfo.getHostId()) {
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
									// TODO Auto-generated method stub
									if(e.getSource() == editBtn) {
										level1.setEnabled(true);
										level2.setEnabled(true);
										level3.setEnabled(true);
										numInputText.setEnabled(false);	//수정완료 전까지는 숫자입력 불가
									}
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
									// TODO Auto-generated method stub
									if(e.getSource() == okBtn) {
										level1.setEnabled(false);
										level2.setEnabled(false);
										level3.setEnabled(false);
										numInputText.setEnabled(true);	//수정완료 후 숫자입력 가능
									}
								}
							});
						levelPanel.add(okBtn);
					}
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
				numInputText.setDocument(new JTextFieldLimit(myRoomInfo.getLevel()));	//글자수 입력 제한 
				numInputText.setHorizontalAlignment(JTextField.CENTER);
			controlPanel.add(numInputText);
			
			//레디버튼
			readyBtn = new JButton(" R E A D Y ");
				readyBtn.setBackground(color2);
				readyBtn.setSize(228,50);
				readyBtn.setFont(new Font("맑은 고딕",Font.BOLD,23));
				readyBtn.setLocation(20, 225);
				readyBtn.setBorder(new MatteBorder(3,3,3,3, color6));
		
				readyBtn.addActionListener(new ActionListener() {
			
					@Override
					public void actionPerformed(ActionEvent e) {
						
						//난이도가 3자리 수 일 경우
						if(level1.isSelected() == true) {
							if(numInputText.getText().length() >=3) {
								if(readyBtn.getText().toString() == " E D I T ") {
									userStateText.setVisible(false);
									readyBtn.setText(" R E A D Y ");
									numInputText.setEnabled(true);
								}else {
									userStateText.setVisible(true);
									numInputText.setEnabled(false);
									readyBtn.setText(" E D I T ");
								}
							}else {
								JOptionPane.showMessageDialog(mainFrame, "자릿수에 맞게 숫자를 입력헤주세요!", "확인 메세지", JOptionPane.WARNING_MESSAGE);
							}
						}
						
						//난이도가 4자리 수 일 경우
						if(level2.isSelected() == true) {
							if(numInputText.getText().length() >=4) {
								if(readyBtn.getText().toString() == " E D I T ") {
									userStateText.setVisible(false);
									readyBtn.setText(" R E A D Y ");
									numInputText.setEnabled(true);
								}else {
									userStateText.setVisible(true);
									numInputText.setEnabled(false);
									readyBtn.setText(" E D I T ");
								}
							}else {
								JOptionPane.showMessageDialog(mainFrame, "자릿수에 맞게 숫자를 입력헤주세요!", "확인 메세지", JOptionPane.WARNING_MESSAGE);
							}
						}
						
						//난이도가 5자리 수 일 경우
						if(level3.isSelected() == true) {
							if(numInputText.getText().length() >=5) {
								if(readyBtn.getText().toString() == " E D I T ") {
									userStateText.setVisible(false);
									readyBtn.setText(" R E A D Y ");
									numInputText.setEnabled(true);
								}else {
									userStateText.setVisible(true);
									numInputText.setEnabled(false);
									readyBtn.setText(" E D I T ");
								}
							}else {
								JOptionPane.showMessageDialog(mainFrame, "자릿수에 맞게 숫자를 입력헤주세요!", "확인 메세지", JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				});
			controlPanel.add(readyBtn);
			
			//시작버튼
			startBtn = new JButton(" S T A R T ");
				startBtn.setBackground(color2);
				startBtn.setSize(120,50);
				startBtn.setFont(new Font("맑은 고딕",Font.BOLD,20));
				startBtn.setLocation(10, 225);
				startBtn.setBorder(new MatteBorder(3,3,3,3, color6));
			
				startBtn.addActionListener(new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						mainFrame.getCardLayout().show(mainFrame.getContentPane(), "GamePanel");	//시작버튼클릭시 게임화면으로 전환
					}
				});
			//controlPanel.add(startBtn);
				
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
							// TODO Auto-generated method stub
							mainFrame.getCardLayout().show(mainFrame.getContentPane(), "WaitingPanel");	//대기실로 나가~
						}
					});
				controlPanel.add(exitBtn);			
			
		onePanel.add(controlPanel);

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
						client.sendMessage(msg);
					}
				});
			talkingPanel.add(talkBtn);
					
			talkArea = new JTextArea();
			talkArea.setBackground(color1);
			talkArea.setFont(new Font("맑은 고딕",Font.BOLD,15));
			talkListPanel.add(new JScrollPane(talkArea),"Center");	//대화창패널에 리스트붙이기
		twoPanel.add(talkingPanel);
	}
	
	public void addChatText(String text) {
		talkArea.append(text+"\n");
		talkInput.setText("");
	}
	
	void caseByInit(int caseIndex) {
		myRoomInfo = mainFrame.getMyRoomInfo();
		//HOST가 아닌 경우에만 (HOST일 땐 상대방이 없음)
		if(!initCase[caseIndex].equals("HOST")) {
			//BOTH인 경유에 away정보를 받아옴
			if(initCase[caseIndex].equals("BOTH")) {
				System.out.println(myInfo.getId() +" : "+myRoomInfo.getHostId() +" : " + myRoomInfo.getAwayId());
				enemyResult = userConnection.findByIdConnection(Integer.toString(myRoomInfo.getAwayId()));
			}
			//AWAY인 경우에 host정보를 받아옴
			else if(initCase[caseIndex].equals("AWAY")){
				System.out.println(myInfo.getId() +" : "+myRoomInfo.getAwayId());
				enemyResult = userConnection.findByIdConnection(Integer.toString(myRoomInfo.getHostId()));
			}
			System.out.println("RoomPanel결과값"+enemyResult);
			try {
				JSONObject json = (JSONObject) parser.parse(enemyResult);
				enemyInfo = new User();
				enemyInfo.setId(Integer.parseInt((String)json.get("id")));
				enemyInfo.setNickName((String)json.get("nickName"));
				enemyInfo.setWin(Integer.parseInt((String)json.get("win")));
				enemyInfo.setLose(Integer.parseInt((String)json.get("lose")));
				enemyInfo.setStateName((String) json.get("stateName"));
				System.out.println(enemyInfo.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
				
		if(initCase[caseIndex].equals("HOST")) {
			enemyNameLabel.setVisible(false);
			enemyRecordLabel.setVisible(false);
		} else {
			enemyNameLabel.setVisible(true);
			enemyRecordLabel.setVisible(true);
			enemyNameText.setText(enemyInfo.getNickName());
			enemyRecordText.setText(enemyInfo.getRecord());
		}
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			String result = roomInfoConnection.findByIdConnection(Integer.toString(myRoomInfo.getId()));
			System.out.println(result);
			try {
				JSONObject json = (JSONObject) parser.parse(result);
				if(json.get("awayName") != null) {
					RoomInfo updatedInfo = new RoomInfo();
					updatedInfo.setId(Integer.parseInt((String)json.get("id")));
					updatedInfo.setRoomName((String)json.get("roomName"));
					updatedInfo.setHostId(Integer.parseInt((String)json.get("hostId")));
					updatedInfo.setHostName((String)json.get("hostName"));
					updatedInfo.setAwayId(Integer.parseInt((String)json.get("awayId")));
					updatedInfo.setAwayName((String)json.get("awayName"));
					updatedInfo.setLevel(Integer.parseInt((String)json.get("level")));
					updatedInfo.setUserCount(Integer.parseInt((String)json.get("userCount")));
					mainFrame.setMyRoomInfo(updatedInfo);
					caseByInit(2);
					break;
				}
				Thread.sleep(2000);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
