package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import dto.RoomInfo;

public class RoomPanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private RoomInfo myRoomInfo;
	private Thread th;
	
	//방주인,상대방 프로필 & 방 정보
		private JPanel onePanel;
			private JPanel hostPanel;
				private JPanel hPanel;
				private JLabel hostNameLabel;		//host닉네임
				private JLabel hostNameText;
				private JLabel hostRecordLabel;		//host전적
				private JLabel hostRecordText;
				private JLabel hostStateLabel;		//host상태
				private JLabel hostStateText;
				
			private JPanel awayPanel;
				private JPanel aPanel;
				private JLabel awayNameLabel;		//away닉네임
				private JLabel awayNameText;
				private JLabel awayRecordLabel;		//away전적
				private JLabel awayRecordText;
				private JLabel awayStateLabel;		//away상태
				private JLabel awayStateText;
				
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
				private JCheckBox check;			//숫자입력 후 체크
				private JLabel checkLabel;			//경고문
				private JButton readyBtn;
				private JButton startBtn;
		
		//채팅창 패널
		private JPanel twoPanel;
			private JPanel talkingPanel;
				private JList<String> talkList;
				private DefaultListModel<String> talkListModel = new DefaultListModel<>();
				private JPanel talkListPanel;	//대화창(리스트뷰)
				private JTextField talkInput;	//대화입력
				private JButton talkBtn;		//대화전송
		
		private Color color1 = new Color(202,236,244);	//연하늘
		private Color color2 = new Color(30,204,208);	//청록
		private Color color3 = new Color(255,201,221);	//연핑
		private Color color4 = new Color(216,167,216);	//연보라
		private Color color5 = new Color(217,211,210);	//연그레이
		private Color color6 = new Color(92,84,82);		//조금 더 진한 연그레이
		
		private BevelBorder border;
	
	public RoomPanel(MainFrame mf) {
		this.mainFrame = mf;
		th = new Thread(this);
		th.start();
		initialize();
	}
	
	private void initialize() {
		setLayout(new GridLayout(2,1));
		setBackground(Color.WHITE);
		setSize(800,600);
		//setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		setLocation(1100,100);
		
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
		
		details_1();
		details_2();
	}
	
	void details_1() {
		//host패널
		hostPanel = new JPanel();
			hostPanel.setBackground(Color.white);
			hostPanel.setLayout(null);
			hostPanel.setSize(250, 300);
			hostPanel.setBounds(20,20,250,300);
			hostPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
	
			hPanel = new JPanel();
				hPanel.setLayout(null);
				hPanel.setBackground(Color.white);
				hPanel.setFont(new Font("맑은 고딕",Font.BOLD,20));
				hPanel.setSize(250, 270);
				hPanel.setLocation(7, 7);
				hPanel.setBorder(new TitledBorder(new LineBorder(color1, 8), "  내 정 보   ", 0, 0, new Font("맑은 고딕",Font.BOLD,20),Color.black));
				
				hostStateText = new JLabel("  준 비 완 료  ");
					hostStateText.setForeground(Color.white);
					hostStateText.setBackground(color1);
					hostStateText.setOpaque(true);
					hostStateText.setFont(new Font("맑은 고딕",Font.BOLD,28));
					hostStateText.setSize(240, 50);
					hostStateText.setLocation(5, 200);
					hostStateText.setHorizontalAlignment(JLabel.CENTER);
					hostStateText.setVisible(false);//처음에는 안보이게
				hPanel.add(hostStateText);
			hostPanel.add(hPanel);
			
		onePanel.add(hostPanel);
		
		//away패널
		awayPanel = new JPanel();
			awayPanel.setBackground(Color.white);
			awayPanel.setLayout(null);
			awayPanel.setSize(250, 300);
			awayPanel.setBounds(20,20,250,300);
			awayPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
			
			aPanel = new JPanel();
			aPanel.setLayout(null);
			aPanel.setBackground(Color.white);
			aPanel.setFont(new Font("맑은 고딕",Font.BOLD,20));
			aPanel.setSize(250, 270);
			aPanel.setLocation(7, 7);
			aPanel.setBorder(new TitledBorder(new LineBorder(color5, 8), "  상 대 방   ", 0, 0, new Font("맑은 고딕",Font.BOLD,20), Color.black));
			
			awayStateText = new JLabel("  준 비 완 료  ");
			awayStateText.setForeground(Color.white);
			awayStateText.setBackground(color5);
			awayStateText.setOpaque(true);
			awayStateText.setFont(new Font("맑은 고딕",Font.BOLD,28));
			awayStateText.setSize(240, 50);
			awayStateText.setLocation(5, 200);
			awayStateText.setHorizontalAlignment(JLabel.CENTER);
			aPanel.add(awayStateText);
			
			awayPanel.add(aPanel);
			
		onePanel.add(awayPanel);
		
		//control패널
		controlPanel = new JPanel();
			controlPanel.setBackground(Color.white);
			controlPanel.setLayout(null);
			controlPanel.setSize(250, 300);
			controlPanel.setBounds(20,20,250,300);
			controlPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
			
			roomNumLabel = new JLabel(" No. ");
				roomNumLabel.setBackground(Color.white);
				roomNumLabel.setFont(new Font("맑은 고딕",Font.BOLD|Font.ITALIC,12));
				roomNumLabel.setSize(35, 30);
				roomNumLabel.setLocation(5, 10);
				roomNumLabel.setBorder(border);
			controlPanel.add(roomNumLabel);
			
			roomNumText = new JLabel("01");
				roomNumText.setBackground(Color.white);
				roomNumText.setFont(new Font("맑은 고딕",Font.BOLD,14));
				roomNumText.setSize(20, 30);
				roomNumText.setLocation(45, 10);
			controlPanel.add(roomNumText);
			
			
			roomNameLabel = new JLabel(" 방이름 ");
				roomNameLabel.setBackground(Color.white);
				roomNameLabel.setFont(new Font("맑은 고딕",Font.BOLD|Font.ITALIC,12));
				roomNameLabel.setSize(50, 30);
				roomNameLabel.setLocation(70, 10);
				roomNameLabel.setBorder(border);
			controlPanel.add(roomNameLabel);
			
			roomNameText = new JLabel("아아아아아아아아아아");
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
						group.add(level1);
						
						level1.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								// TODO Auto-generated method stub
								numInputText.setDocument(new JTextFieldLimit(3));	//3자리 수
							}
						});
					panel.add(level1);
				
					level2 = new JRadioButton("4자리 수");
						level2.setBackground(Color.WHITE);
						level2.setFont(new Font("맑은 고딕",Font.BOLD,12));
						level2.setSize(90,30);
						level2.setEnabled(false);
						group.add(level2);
						
						level2.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								// TODO Auto-generated method stub
								numInputText.setDocument(new JTextFieldLimit(4));	//4자리 수
							}
						});
					panel.add(level2);
				
					level3 = new JRadioButton("5자리 수");
						level3.setBackground(Color.WHITE);
						level3.setFont(new Font("맑은 고딕",Font.BOLD,12));
						level3.setSize(90,30);
						level3.setEnabled(false);
						group.add(level3);
						
						level3.addItemListener(new ItemListener() {
							
							@Override
							public void itemStateChanged(ItemEvent e) {
								numInputText.setDocument(new JTextFieldLimit(5));	//5자리 수
							}
						});
					panel.add(level3);
					
					//난이도 수정버튼
					editBtn = new JButton("edit");
						editBtn.setBackground(Color.lightGray);
						editBtn.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 10));
						editBtn.setForeground(Color.black);
						editBtn.setSize(55, 20);
						editBtn.setLocation(115, 70);
						
						editBtn.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								if(e.getSource() == editBtn) {
									level1.setEnabled(true);
									level2.setEnabled(true);
									level3.setEnabled(true);
								}else {
									level1.setEnabled(false);
									level2.setEnabled(false);
									level3.setEnabled(false);
								}
							}
						});
					levelPanel.add(editBtn);
					
					//수정완료 버튼
					okBtn = new JButton("ok");
						okBtn.setBackground(Color.lightGray);
						okBtn.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 10));
						okBtn.setForeground(Color.black);
						okBtn.setSize(55, 20);
						okBtn.setLocation(172, 70);
						
						okBtn.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								if(e.getSource() == okBtn) {
									level1.setEnabled(false);
									level2.setEnabled(false);
									level3.setEnabled(false);
								}
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
				numInputText.setDocument(new JTextFieldLimit(5));	//글자수 입력 제한 
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
									hostStateText.setVisible(false);
									readyBtn.setText(" R E A D Y ");
									numInputText.setEnabled(true);
								}else {
									hostStateText.setVisible(true);
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
									hostStateText.setText("  대 기 상 태 ");
									hostStateText.setForeground(Color.BLUE);
									readyBtn.setText(" R E A D Y ");
									numInputText.setEnabled(true);
								}else {
									hostStateText.setText("  준 비 완 료  ");
									hostStateText.setForeground(Color.red);
									numInputText.setEnabled(false);
									readyBtn.setText(" E D I T ");
								}
							}else {
								hostStateText.setText("  대 기 상 태  ");
								JOptionPane.showMessageDialog(mainFrame, "자릿수에 맞게 숫자를 입력헤주세요!", "확인 메세지", JOptionPane.WARNING_MESSAGE);
							}
						}
						
						//난이도가 5자리 수 일 경우
						if(level3.isSelected() == true) {
							if(numInputText.getText().length() >=5) {
								if(readyBtn.getText().toString() == " E D I T ") {
									hostStateText.setText("  대 기 상 태 ");
									hostStateText.setForeground(Color.BLUE);
									readyBtn.setText(" R E A D Y ");
									numInputText.setEnabled(true);
								}else {
									hostStateText.setText("  준 비 완 료  ");
									hostStateText.setForeground(Color.red);
									numInputText.setEnabled(false);
									readyBtn.setText(" E D I T ");
								}
							}else {
								hostStateText.setText("  대 기 상 태  ");
								JOptionPane.showMessageDialog(mainFrame, "자릿수에 맞게 숫자를 입력헤주세요!", "확인 메세지", JOptionPane.WARNING_MESSAGE);
							}
						}
					}
				});
			//controlPanel.add(readyBtn);
			
			//시작버튼
			startBtn = new JButton(" S T A R T ");
				startBtn.setBackground(color2);
				startBtn.setSize(228,50);
				startBtn.setFont(new Font("맑은 고딕",Font.BOLD,23));
				startBtn.setLocation(20, 225);
				startBtn.setBorder(new MatteBorder(3,3,3,3, color6));
			
				startBtn.addActionListener(new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						mainFrame.getCardLayout().show(mainFrame.getContentPane(), "GamePanel");	//시작버튼클릭시 게임화면으로 전환
					}
				});
			controlPanel.add(startBtn);
			
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
						// TODO Auto-generated method stub
							String s = talkInput.getText();
							talkListModel.addElement(s);
							talkInput.setText("");
					}
				});
			talkingPanel.add(talkBtn);
					
			talkList = new JList<String>(talkListModel);
			talkList.setBackground(color1);
			talkList.setFont(new Font("맑은 고딕",Font.BOLD,15));
			talkListPanel.add(new JScrollPane(talkList),"Center");	//대화창패널에 리스트붙이기
		twoPanel.add(talkingPanel);
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
			try {
				
				if(mainFrame.getMyRoomInfo() != null) {
					myRoomInfo = mainFrame.getMyRoomInfo();
					System.out.println(myRoomInfo.toString());
					break;
				}
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
