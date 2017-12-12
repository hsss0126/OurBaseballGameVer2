package gameUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import connection.GameInfoConnection;
import dto.GameInfo;
import dto.RoomInfo;
import dto.User;

public class GamePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private String[] initCase = {"HOST", "AWAY"};
	private int caseIndex;
	
	private MainFrame mainFrame;
	private User myInfo;
	private RoomInfo myRoomInfo;
	private GameInfo myGameInfo;
	
	private JPanel onePanel;
		private JLabel myNumLabel;	//내 숫자
		
		private JPanel myNumPanel_level3; 	 //그리드3x1
			private JLabel num1Text_level3;
			private JLabel num2Text_level3;
			private JLabel num3Text_level3;
			
		private JPanel myNumPanel_level4; 	 //그리드4x1
			private JLabel num1Text_level4;
			private JLabel num2Text_level4;
			private JLabel num3Text_level4;
			private JLabel num4Text_level4;
			
		private JPanel myNumPanel_level5;	 //그리드5x1
			private JLabel num1Text_level5;
			private JLabel num2Text_level5;
			private JLabel num3Text_level5;
			private JLabel num4Text_level5;
			private JLabel num5Text_level5;
			
		private JPanel numPanel;	//예상숫자표시
			private String[] numString = { "0","1","2","3","4","5","6","7","8","9"};
			private JLabel[] numLabel = new JLabel[10];
		private JLabel numInputLabel;	//숫자입력
		private JTextField numInputText;
		private JButton sendBtn;
	
	private JPanel twoPanel;
		private JLabel myRecordLabel;	//내 기록화면
		private JPanel myRecordPanel;	//기록내용이 들어갈 패널
			private JList<String> myRecordList;
			private DefaultListModel<String> myRecordListModel = new DefaultListModel<>();
			private JLabel memoLabel;	//내 메모장
			private JTextArea memo;	
		private JLabel enemyRecordLabel;	//상대방 기록화면
			private JPanel enemyRecordPanel;
			private JList<String> enemyRecordList;
			private DefaultListModel<String> enemyRecordListModel = new DefaultListModel<>();
		
		private Color color1 = new Color(202,236,244);	//연하늘
		private Color color2 = new Color(46,38,79);		//네이비
		private Color color3 = new Color(30,204,208);	//청록
		private Color color5 = new Color(217,211,210);	//연그레이
		private Color color6 = new Color(92,84,82);		//조금 더 진한 연그레이
		private Color color7 = new Color(245,252,254);	//완전 연 하늘
		private Color color8 = new Color(240,238,238);	//완전 연 그레이
		
		private GameInfoConnection gameInfoConnection;
		
		private OrderThread orderThread;
		private Boolean orderFlag;
	
	public GamePanel(MainFrame mf) {
		mainFrame = mf;
		myInfo = mainFrame.getMyInfo();
		myRoomInfo = mainFrame.getMyRoomInfo();
		myGameInfo = mainFrame.getMyGameInfo();
		gameInfoConnection = mainFrame.getGameInfoConnection();
		initialize();
	}
	
	private void initialize() {
		setLayout(null);
		setBackground(Color.WHITE);
		setSize(800,600);
		setVisible(true);
		setLocation(1100,100);
		
		onePanel = new JPanel();
			onePanel.setBackground(color7);
			onePanel.setLayout(null);
			onePanel.setSize(795, 200);
			onePanel.setLocation(0, 0);
			onePanel.setBorder(new MatteBorder(5,5,4,5, color5));
		add(onePanel);
		
		twoPanel = new JPanel();
			twoPanel.setBackground(color1);
			twoPanel.setLayout(null);
			twoPanel.setSize(795, 365);
			twoPanel.setLocation(0, 200);
			twoPanel.setBorder(new MatteBorder(0,5,4,5, color5));
		add(twoPanel);
		
		details_1();
		details_2();
		
		//MyNumber 패널 초기화
		initMyNumber();
		//host, away에 맞게 초기화
		initByCase();
	}
	
	void details_1() {
		
		myNumLabel = new JLabel("My NUMBER");
			myNumLabel.setBackground(Color.white);
			myNumLabel.setFont(new Font("맑은 고딕",Font.BOLD,28));
			myNumLabel.setSize(200, 50);
			myNumLabel.setLocation(60, 30);
		onePanel.add(myNumLabel);
		
		//3자리수 일 경우 숫자 패널
		myNumPanel_level3 = new JPanel();
			myNumPanel_level3.setBackground(Color.white);
			myNumPanel_level3.setLayout(new GridLayout(1,3));
			myNumPanel_level3.setSize(210, 70);
			myNumPanel_level3.setLocation(50, 85);
			myNumPanel_level3.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
			
			num1Text_level3 = new JLabel("x");
			num1Text_level3.setBackground(Color.white);
			num1Text_level3.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num1Text_level3.setSize(70, 70);
			num1Text_level3.setBorder(new MatteBorder(5,5,5,5, color6));
			num1Text_level3.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level3.add(num1Text_level3);
			
			num2Text_level3 = new JLabel("x");
			num2Text_level3.setBackground(Color.white);
			num2Text_level3.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num2Text_level3.setSize(70, 70);
			num2Text_level3.setBorder(new MatteBorder(5,0,5,5, color6));
			num2Text_level3.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level3.add(num2Text_level3);
			
			num3Text_level3 = new JLabel("x");
				num3Text_level3.setBackground(Color.white);
				num3Text_level3.setFont(new Font("맑은 고딕",Font.BOLD,40));
				num3Text_level3.setSize(70, 70);
				num3Text_level3.setBorder(new MatteBorder(5,0,5,5, color6));
				num3Text_level3.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level3.add(num3Text_level3);
		//onePanel.add(myNumPanel_level3);
		
		//4자리수 일 경우 숫자 패널
		myNumPanel_level4 = new JPanel();
			myNumPanel_level4.setBackground(Color.white);
			myNumPanel_level4.setLayout(new GridLayout(1,4));
			myNumPanel_level4.setSize(210, 70);
			myNumPanel_level4.setLocation(40, 85);
			myNumPanel_level4.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
					
			num1Text_level4 = new JLabel("x");
			num1Text_level4.setBackground(Color.white);
			num1Text_level4.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num1Text_level4.setSize(70, 70);
			num1Text_level4.setBorder(new MatteBorder(5,5,5,5, color6));
			num1Text_level4.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level4.add(num1Text_level4);
					
			num2Text_level4 = new JLabel("x");
			num2Text_level4.setBackground(Color.white);
			num2Text_level4.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num2Text_level4.setSize(70, 70);
			num2Text_level4.setBorder(new MatteBorder(5,0,5,5, color6));
			num2Text_level4.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level4.add(num2Text_level4);
					
			num3Text_level4 = new JLabel("x");
			num3Text_level4.setBackground(Color.white);
			num3Text_level4.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num3Text_level4.setSize(70, 70);
			num3Text_level4.setBorder(new MatteBorder(5,0,5,5, color6));
			num3Text_level4.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level4.add(num3Text_level4);
			
			num4Text_level4 = new JLabel("x");
			num4Text_level4.setBackground(Color.white);
			num4Text_level4.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num4Text_level4.setSize(70, 70);
			num4Text_level4.setBorder(new MatteBorder(5,0,5,5, color6));
			num4Text_level4.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level4.add(num4Text_level4);
			
		//onePanel.add(myNumPanel_level4);
				
		//5자리수 일 경우 숫자 패널
		myNumPanel_level5 = new JPanel();
			myNumPanel_level5.setBackground(Color.white);
			myNumPanel_level5.setLayout(new GridLayout(1,5));
			myNumPanel_level5.setSize(240, 70);
			myNumPanel_level5.setLocation(30, 85);
			myNumPanel_level5.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
					
			num1Text_level5 = new JLabel("x");
			num1Text_level5.setBackground(Color.white);
			num1Text_level5.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num1Text_level5.setSize(70, 70);
			num1Text_level5.setBorder(new MatteBorder(5,5,5,5, color6));
			num1Text_level5.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level5.add(num1Text_level5);
					
			num2Text_level5 = new JLabel("x");
			num2Text_level5.setBackground(Color.white);
			num2Text_level5.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num2Text_level5.setSize(70, 70);
			num2Text_level5.setBorder(new MatteBorder(5,0,5,5, color6));
			num2Text_level5.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level5.add(num2Text_level5);
					
			num3Text_level5 = new JLabel("x");
			num3Text_level5.setBackground(Color.white);
			num3Text_level5.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num3Text_level5.setSize(70, 70);
			num3Text_level5.setBorder(new MatteBorder(5,0,5,5, color6));
			num3Text_level5.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level5.add(num3Text_level5);
			
			num4Text_level5 = new JLabel("x");
			num4Text_level5.setBackground(Color.white);
			num4Text_level5.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num4Text_level5.setSize(70, 70);
			num4Text_level5.setBorder(new MatteBorder(5,0,5,5, color6));
			num4Text_level5.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level5.add(num4Text_level5);
			
			num5Text_level5 = new JLabel("x");
			num5Text_level5.setBackground(Color.white);
			num5Text_level5.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num5Text_level5.setSize(70, 70);
			num5Text_level5.setBorder(new MatteBorder(5,0,5,5, color6));
			num5Text_level5.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel_level5.add(num5Text_level5);
			
		//onePanel.add(myNumPanel_level5);
			
		//0123456789
		numPanel = new JPanel();
			numPanel.setBackground(Color.white);
			numPanel.setLayout(new GridLayout(1,10));
			numPanel.setSize(450, 50);
			numPanel.setLocation(310, 50);
			numPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
				
				//0123456789
				for(int i = 0; i < numString.length; i++) {
					numPanel.add(numLabel[i] = new JLabel(numString[i]));	//생성
					
					numLabel[i].setBackground(Color.white);
					numLabel[i].setFont(new Font("맑은 고딕",Font.BOLD,30));
					numLabel[i].setSize(70, 70);
					numLabel[i].setHorizontalAlignment(JLabel.CENTER);
					
					//클릭시 색 변경
					numLabel[i].addMouseListener(new MouseListener() {
						public void mouseReleased(MouseEvent e) {
						}
						public void mousePressed(MouseEvent e) {
						}
						public void mouseExited(MouseEvent e) {
						}
						public void mouseEntered(MouseEvent e) {
						}
						@Override
						public void mouseClicked(MouseEvent e) {
							JLabel ob = (JLabel)e.getSource();
							
							if(ob.getForeground() ==  Color.blue) {
								ob.setForeground(Color.black);
							}else {
								ob.setForeground(Color.blue);
							}
							
						}
					});
					
					//테두리 설정
					if(i == 0) {	
						numLabel[i].setBorder(new MatteBorder(3,3,3,3, color6));
					}else {
						numLabel[i].setBorder(new MatteBorder(3,0,3,3, color6));
					}
				}
				
		onePanel.add(numPanel);
		
		numInputLabel = new JLabel("숫자입력 :");
			numInputLabel.setBackground(Color.white);
			numInputLabel.setFont(new Font("맑은 고딕",Font.BOLD,25));
			numInputLabel.setSize(130, 70);
			numInputLabel.setLocation(310, 110);
		onePanel.add(numInputLabel);
		
		numInputText = new JTextField();
			numInputText.setBackground(Color.white);
			numInputText.setFont(new Font("맑은 고딕",Font.BOLD,40));
			numInputText.setSize(200, 50);
			numInputText.setLocation(442, 120);
			numInputText.setHorizontalAlignment(JLabel.CENTER);
			
			numInputText.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					  
					  if (!Character.isDigit(c)) {
					   e.consume();
					   return;
					  }
				}
				@Override public void keyReleased(KeyEvent e) {}
				@Override public void keyPressed(KeyEvent e) {}
			});
		onePanel.add(numInputText);
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@전송시 게임 진행@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		sendBtn = new JButton("전 송");
			sendBtn.setBackground(color3);
			sendBtn.setFont(new Font("맑은 고딕",Font.BOLD,20));
			sendBtn.setSize(100, 50);
			sendBtn.setLocation(650, 120);
			sendBtn.setBorder(new MatteBorder(1,1,1,1, color6));
			
			sendBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String number = numInputText.getText();
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
							//숫자 전송 후 계산 된 카운트 리턴받아오기
							myGameInfo.setInputNum(number);
							myGameInfo.setOrderUserId(myInfo.getId());
							//숫자를 보내고 결과값을 받아옴
							myGameInfo = mainFrame.gameInfoParse(gameInfoConnection.updateConnection(myGameInfo));
							mainFrame.setMyGameInfo(myGameInfo);
							myRecordListModel.addElement(number + "               " + myGameInfo.getResultCount());

							if(initCase[caseIndex].equals("HOST")) {
								if(myGameInfo.getAwayNumber().equals(number)) {
									new ResultThread(0,0).start();
//									new ResultFrame(mainFrame, GamePanel.this, 0, 0);
//									mainFrame.createResultFrame(GamePanel.this, 0, 0);
									gameInfoConnection.gameEndConnection(myGameInfo.getId());
								} else {
									numInputText.setText("");
									sendBtn.setEnabled(false);
									
									orderThread = new OrderThread();
									orderThread.start();
								}
							} else if(initCase[caseIndex].equals("AWAY")) {
								if(myGameInfo.getHostNumber().equals(number)) {
									new ResultThread(1,0).start();
//									new ResultFrame(mainFrame, GamePanel.this, 1, 0);
//									mainFrame.createResultFrame(GamePanel.this, 1, 0);
									gameInfoConnection.gameEndConnection(myGameInfo.getId());
								} else {
									numInputText.setText("");
									sendBtn.setEnabled(false);
									
									orderThread = new OrderThread();
									orderThread.start();
								}
							}
							
						} 
						//입력한 숫자가 중복이라면
						else {
							JOptionPane.showMessageDialog(mainFrame, "중복되지 않는 숫자를 입력해주세요", "경고", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			});
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@전송시 게임 진행@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		onePanel.add(sendBtn);
		
	}
	
	void details_2() {
		
		//내 기록판
		myRecordPanel = new JPanel();
			myRecordPanel.setBackground(Color.white);
			myRecordPanel.setLayout(new GridLayout(1,2));
			myRecordPanel.setSize(500, 290);
			myRecordPanel.setLocation(20,60);
			
			myRecordLabel = new JLabel("나의 기록");
				myRecordLabel.setForeground(color2);
				myRecordLabel.setBackground(Color.white);
				myRecordLabel.setFont(new Font("맑은 고딕",Font.BOLD,25));
				myRecordLabel.setSize(250, 70);
				myRecordLabel.setLocation(20, 0);
			twoPanel.add(myRecordLabel);
			
			//나의 기록판 List뷰
			myRecordList = new JList<String>(myRecordListModel);
				myRecordList.setBackground(color1);
				myRecordList.setFont(new Font("맑은 고딕",Font.BOLD,20));
			myRecordPanel.add(new JScrollPane(myRecordList),"Center");	//대화창패널에 리스트붙이기
				
			//나의 메모장
			memoLabel = new JLabel("MEMO");
				memoLabel.setForeground(color2);
				memoLabel.setBackground(Color.white);
				memoLabel.setFont(new Font("맑은 고딕",Font.BOLD,25));
				memoLabel.setSize(150, 70);
				memoLabel.setLocation(270, 0);
			twoPanel.add(memoLabel);
				
			memo = new JTextArea();
				memo.setBackground(Color.white);
				memo.setFont(new Font("맑은 고딕",Font.BOLD,22));
			myRecordPanel.add(new JScrollPane(memo),"Center");
			
		twoPanel.add(myRecordPanel);
		
		//상대방 기록판
		enemyRecordPanel = new JPanel();
			enemyRecordPanel.setBackground(color8);
			enemyRecordPanel.setLayout(new GridLayout(1, 1));
			enemyRecordPanel.setSize(240, 290);
			enemyRecordPanel.setLocation(540,60);
			
			enemyRecordLabel = new JLabel("상대방 기록");
				enemyRecordLabel.setForeground(color2);
				enemyRecordLabel.setBackground(Color.white);
				enemyRecordLabel.setFont(new Font("맑은 고딕",Font.BOLD,25));
				enemyRecordLabel.setSize(250, 70);
				enemyRecordLabel.setLocation(540, 0);
			twoPanel.add(enemyRecordLabel);
			
			//상대 기록판 List뷰
			enemyRecordList = new JList<String>(enemyRecordListModel);
				enemyRecordList.setBackground(color8);
				enemyRecordList.setFont(new Font("맑은 고딕",Font.BOLD,20));
			enemyRecordPanel.add(new JScrollPane(enemyRecordList),"Center");	//대화창패널에 리스트붙이기
		
		twoPanel.add(enemyRecordPanel);
		
	}
	
	private void initMyNumber() {
		char[] myNum;
		//host일 경우
		if(myRoomInfo.getHostId() == myInfo.getId()) {
			myNum = myGameInfo.getHostNumber().toCharArray();
		}
		//away일 경우
		else {
			myNum = myGameInfo.getAwayNumber().toCharArray();
		}
		
		switch(myRoomInfo.getLevel()) {
		case 3:
			num1Text_level3.setText(Character.toString(myNum[0]));
			num2Text_level3.setText(Character.toString(myNum[1]));
			num3Text_level3.setText(Character.toString(myNum[2]));
			onePanel.add(myNumPanel_level3);
			numInputText.setDocument(new JTextFieldLimit(3));
			break;
		case 4:
			num1Text_level4.setText(Character.toString(myNum[0]));
			num2Text_level4.setText(Character.toString(myNum[1]));
			num3Text_level4.setText(Character.toString(myNum[2]));
			num4Text_level4.setText(Character.toString(myNum[3]));
			onePanel.add(myNumPanel_level4);
			numInputText.setDocument(new JTextFieldLimit(4));
			break;
		case 5:
			num1Text_level5.setText(Character.toString(myNum[0]));
			num2Text_level5.setText(Character.toString(myNum[1]));
			num3Text_level5.setText(Character.toString(myNum[2]));
			num4Text_level5.setText(Character.toString(myNum[3]));
			num5Text_level5.setText(Character.toString(myNum[4]));
			onePanel.add(myNumPanel_level5);
			numInputText.setDocument(new JTextFieldLimit(5));
			break;
		}
	}
	
	private void initByCase() {
		if(myRoomInfo.getHostId() == myInfo.getId()) {
			caseIndex = 0;
		} else {
			caseIndex = 1;
		}
		
		if(initCase[caseIndex].equals("HOST")) {
			sendBtn.setEnabled(true);
		}
		
		else if(initCase[caseIndex].equals("AWAY")) {
			sendBtn.setEnabled(false);
			orderThread = new OrderThread();
			orderThread.start();
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
	 * 순서, 결과를 알아내는 스레드
	 */
	class OrderThread extends Thread {
		GameInfo gameInfo;
		@Override
		public void run() {
			orderFlag = true;
			while(orderFlag) {
				try {
					gameInfo = mainFrame.gameInfoParse(gameInfoConnection.findByIdConnection(myGameInfo.getId()));
					if(gameInfo != null) {
//						System.out.println(gameInfo.toString() + gameInfo.getOrderUserId() + " : " + gameInfo.getInputNum());
						//orderId는 마지막으로 입력한 유저의 id이므로 나의 id와 같다면 아직 내차례가 아닌 것(아무도 입력하지 않았을 때 기본값 0)
						if(gameInfo.getOrderUserId() != 0 && gameInfo.getOrderUserId() != myInfo.getId()) {
							enemyRecordListModel.addElement(gameInfo.getInputNum() +"               " + gameInfo.getResultCount());
							sendBtn.setEnabled(true);
							orderFlag = false;
							mainFrame.setMyGameInfo(gameInfo);	
							myGameInfo = mainFrame.getMyGameInfo();
							
							if(initCase[caseIndex].equals("HOST")) {
								if(gameInfo.getHostNumber().equals(gameInfo.getInputNum())) {
									System.out.println("HOST : "+ gameInfo.getHostNumber() + " : " + gameInfo.getInputNum());
//									new ResultFrame(mainFrame, GamePanel.this, 0, 1);
									new ResultThread(0,1).start();
								}
							} 
							else if(initCase[caseIndex].equals("AWAY")) {
								if(gameInfo.getAwayNumber().equals(gameInfo.getInputNum())) {
									System.out.println("AWAY : "+ gameInfo.getAwayNumber() + " : " + gameInfo.getInputNum());
//									new ResultFrame(mainFrame, GamePanel.this, 1, 1);
									new ResultThread(1,1).start();
								}
							}
						}
					}
//					Thread.sleep(100);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class ResultThread extends Thread {
		
		int initIndex;
		int resultIndex;
		public ResultThread(int init, int result) {
			this.initIndex = init;
			this.resultIndex = result;
		}
		@Override
		public void run() {
			SwingUtilities.invokeLater(new ResultFrame(mainFrame, GamePanel.this, initIndex, resultIndex));
		}
	}
	
	
}
