package gameUI;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import connection.RoomInfoConnection;
import dto.RoomInfo;
import dto.User;

public class MakeRoomFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private User myInfo;
	private CardLayout cards = new CardLayout();
	
	private MainFrame mainFrame;
	private WaitingPanel waitingPanel;
	
	private JPanel roomPanel;
		private JLabel roomNameLabel;
		private JTextField roomNameText;
		private JPanel levelPanel;
		private ButtonGroup group;
		private JRadioButton level1, level2, level3;
		private JButton okBtn, closeBtn;
		
	private RoomInfoConnection roomInfoConnection;
	private String roomName;
	private int level;
		
	private Font font1 = new Font("맑은 고딕",Font.BOLD,15);
	private Font font2 = new Font("맑은 고딕",Font.BOLD,20);
	
	private Color color2 = new Color(30,204,208);	//청록
	private Color color6 = new Color(92,84,82);		//조금 더 진한 연그레이
	
	public MakeRoomFrame(MainFrame mf, WaitingPanel wp, User myInfo) {
		this.myInfo = myInfo;
		this.mainFrame = mf;
		this.waitingPanel = wp;
		initialize();
	}
	
	private void initialize() {
		roomInfoConnection = new RoomInfoConnection();
		
		setLayout(null);
		setBackground(Color.WHITE);
		setSize(345,265);
		setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		setAlwaysOnTop(true);
		double width = this.getSize().getWidth()/2;		//프레임크기
		double height = this.getSize().getHeight()/2;		//프레임크기
		double x = mainFrame.getLocation().getX();	//모니터크기
		double y = mainFrame.getLocation().getY();	//모니터크기
		double mainWidth = mainFrame.getSize().getWidth()/2;
		double mainHeight = mainFrame.getSize().getHeight()/2;
		setLocation((int)(x+mainWidth-width), (int)(y+mainHeight-height));
		setResizable(false);	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		roomPanel = new JPanel();
			roomPanel.setBackground(Color.white);
			roomPanel.setLayout(null);
			roomPanel.setSize(345, 265);
			roomPanel.setLocation(0, 0);
			roomPanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 5), "", 0, 0, font1,Color.black));
		add(roomPanel);
		
		details();
	}
	
	
	void details() {
		
		roomNameLabel = new JLabel("방 이 름");
			roomNameLabel.setBackground(Color.white);
			roomNameLabel.setFont(font2);
			roomNameLabel.setSize(100, 30);
			roomNameLabel.setLocation(35, 45);
		roomPanel.add(roomNameLabel);
		
		roomNameText = new JTextField("");
			roomNameText.setFont(font2);
			roomNameText.setSize(190, 35);
			roomNameText.setLocation(115, 45);
			roomNameText.setDocument(new JTextFieldLimit(10));	//글자수 입력 제한 10자리
		roomPanel.add(roomNameText);
		
		levelPanel = new JPanel();
			levelPanel.setBackground(Color.white);
			levelPanel.setLayout(new GridLayout());
			levelPanel.setSize(280, 100);
			levelPanel.setLocation(35, 100);
			levelPanel.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 3), " 난이도 설정   ", 0, 0, font2,Color.black));
			
			group = new ButtonGroup();
			level1 = new JRadioButton("3자리 수");
				level1.setBackground(Color.WHITE);
				level1.setFont(font1);
				level1.setSize(90,30);
				level1.setSelected(true);
				group.add(level1);
			levelPanel.add(level1);
			
			level2 = new JRadioButton("4자리 수");
				level2.setBackground(Color.WHITE);
				level2.setFont(font1);
				level2.setSize(90,30);
				group.add(level2);
			levelPanel.add(level2);
		
			level3 = new JRadioButton("5자리 수");
				level3.setBackground(Color.WHITE);
				level3.setFont(font1);
				level3.setSize(90,30);
				group.add(level3);
			levelPanel.add(level3);
		roomPanel.add(levelPanel);
		
		okBtn = new JButton("완 료");
			okBtn.setBackground(color2);
			okBtn.setFont(font2);
			okBtn.setSize(130, 40);
			okBtn.setLocation(40, 210);
			okBtn.setBorder(new MatteBorder(3,3,3,3, color6));
			okBtn.setActionCommand("make");
			okBtn.addActionListener(this);
		roomPanel.add(okBtn);
		
		closeBtn = new JButton("취 소");
			closeBtn.setBackground(color2);
			closeBtn.setFont(font2);
			closeBtn.setSize(130, 40);
			closeBtn.setLocation(180, 210);
			closeBtn.setBorder(new MatteBorder(3,3,3,3, color6));
			closeBtn.setActionCommand("cancel");
			closeBtn.addActionListener(this);
		roomPanel.add(closeBtn);
	}
	
	public CardLayout getCardLayout() {
		 return cards;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		switch(command) {
		case "make":
			roomName = roomNameText.getText();		//입력된 방 이름
			if(level1.isSelected()) {
				level = 3;
			}else if(level2.isSelected()) {
				level = 4;
			}else {
				level = 5;
			}
			RoomInfo roomInfo = new RoomInfo();
			roomInfo.setRoomName(roomName);
			roomInfo.setLevel(level);
			roomInfo.setHostId(myInfo.getId());
			
			roomInfo = mainFrame.roomInfoParse(roomInfoConnection.createConnection(roomInfo));
			mainFrame.setMyRoomInfo(roomInfo);
			//방 입장 시 대기실 클라이언트 소켓 닫기
			mainFrame.closeClient();
			//RoomPanel 불러오기
			mainFrame.addRoomPanel(0);
			mainFrame.getCardLayout().show(mainFrame.getContentPane(), "RoomPanel");
			mainFrame.removePanel(waitingPanel);
			dispose(); 
			break;
		case "cancel":
			dispose();
			break;
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

	
	 
}
