package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ListUI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connection.RoomInfoConnection;
import connection.UserConnection;
import dto.RoomInfo;
import dto.User;
import listrenderer.OnlineUserRenderer;
import listrenderer.RoomListRenderer;

public class WaitingPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MakeRoomFrame makeRoom;
	private MainFrame mainFrame;
	
	//내정보&방만들기
		private JPanel one; 				
			private JPanel myInfoPanel;
				private JLabel myInfoLabel;
				private JLabel nickNameLabel;
				private JLabel nickNameText;
				private JLabel recordLabel;
				private JLabel recordText;
			private JPanel roomInfoPanel;
				static JList<RoomListPanel> roomList;
				static DefaultListModel<RoomListPanel> roomListModel = new DefaultListModel<>();	//DefaultListModel 클래스는  JList에 채울 데이터를 담을 객체
				static JPanel roomListPanel;
				private JButton makeRoomBtn;
			
		//대화창&대기실접속자정보
		private JPanel two;					
			private JPanel waitingInfoPanel;
				private JList<OnlineUserPanel> waitingList;
				private DefaultListModel<OnlineUserPanel> waitingListModel = new DefaultListModel<>();
				private JPanel waitingListPanel;
				private JLabel waitingLabel1;
				private JLabel waitingLabel2;
			private JPanel talkingPanel;
				private JList<String> talkList;
				private DefaultListModel<String> talkListModel = new DefaultListModel<>();
				private JPanel talkListPanel;	//대화창(리스트뷰)
				private JTextField talkInput;	//대화입력
				private JButton talkBtn;		//대화전송
			
		private Font font = new Font("맑은 고딕",Font.BOLD,20);
		private Font font2 = new Font("맑은 고딕",Font.BOLD,15);
		private Font font3 = new Font("맑은 고딕",Font.BOLD,12);
		private Font font4 = new Font("맑은 고딕", Font.PLAIN, 15);

		static BevelBorder border;
		private JSONParser parser;
		
		private RoomInfoConnection roomInfoConnection;
		private List<RoomInfo> createdRoomList;
		private String createdRoom;
		
		private UserConnection userConnection;
		private List<User> onlineList;
		private User myInfo;
		private String record;
		private String onlineUser;
//------------------------------------------------------------------------------------------------------------
		
		
	public WaitingPanel(MainFrame mf, User myInfo){
		mainFrame = mf;
		this.myInfo = myInfo;
		initialize();
	}

	private void initialize() {
		
		userConnection = new UserConnection();
		roomInfoConnection = new RoomInfoConnection();
		record = String.format("%d승 / %d패 (%.1f%%)", myInfo.getWin(),myInfo.getLose(),myInfo.getRate());
		onlineUser = userConnection.listConnection();
		System.out.println(onlineUser);
		try {
			parser = new JSONParser();
			JSONArray userList =(JSONArray) parser.parse(onlineUser);
			onlineList = new ArrayList<User>();
            for(int i=0; i<userList.size(); i++) {
            	JSONObject obj = (JSONObject) userList.get(i);
            	System.out.println(obj.toString());
            	User user = new User();
            	user.setId(Integer.parseInt((String)obj.get("id")));
            	user.setNickName((String) obj.get("nickName"));
            	user.setWin(Integer.parseInt((String)obj.get("win")));
            	user.setLose(Integer.parseInt((String)obj.get("lose")));
            	user.setStateName((String) obj.get("stateName"));
            	onlineList.add(user);
            }
		} catch(ParseException e) {
			e.printStackTrace();
		}
		
		createdRoom = roomInfoConnection.listConnection(Integer.toString(0));
		try {
			JSONArray roomList = (JSONArray) parser.parse(createdRoom);
			createdRoomList = new ArrayList<RoomInfo>();
			for(int i=0; i<roomList.size(); i++) {
            	JSONObject obj = (JSONObject) roomList.get(i);
            	RoomInfo roomInfo = new RoomInfo();
            	roomInfo.setId(Integer.parseInt((String)obj.get("id")));
            	roomInfo.setRoomName((String) obj.get("roomName"));
            	roomInfo.setHostId(Integer.parseInt((String)obj.get("hostId")));
            	roomInfo.setHostName((String)obj.get("hostName"));
            	roomInfo.setLevel(Integer.parseInt((String)obj.get("level")));
            	roomInfo.setUserCount(Integer.parseInt((String) obj.get("userCount")));
            	createdRoomList.add(roomInfo);
            }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		border = new BevelBorder(BevelBorder.RAISED);//3차원적인 테두리 효과를 위한것이고 양각의 옵션을 줌
		
		setLayout(new GridLayout(2,1));
		setBackground(Color.WHITE);
		setSize(800,600);
		//setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		setLocation(1100,100);
		
		/* 내정보 & 방만들기 */
		one = new JPanel();
			one.setBackground(Color.white);
			one.setLayout(null);
			one.setSize(400, 300);
			one.setLocation(0, 0);
		add(one);
		
		/* 대화창 & 대기실접속자정보 */
		two = new JPanel();
			two.setBackground(Color.white);
			two.setLayout(null);
			two.setSize(400, 300);
			two.setLocation(0, 0);
		add(two);
		
		details_1();
		details_2();
		
	}
	
	
	/**
	 *  내정보 & 방만들기
	**/
	void details_1() {
		//내정보
		myInfoPanel = new JPanel();
			myInfoPanel.setBackground(Color.pink);
			myInfoPanel.setLayout(null);
			myInfoPanel.setSize(250, 270);
			myInfoPanel.setLocation(5, 5);
			myInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 5),"")); //테두리설정
		
			myInfoLabel = new JLabel("        내  정  보");
				myInfoLabel.setBackground(Color.white);
				myInfoLabel.setFont(font);
				myInfoLabel.setSize(200, 40);
				myInfoLabel.setLocation(25, 30);
				myInfoLabel.setBorder(border);
			myInfoPanel.add(myInfoLabel);
			
			nickNameLabel = new JLabel("닉 네 임");
				nickNameLabel.setBackground(Color.white);
				nickNameLabel.setFont(font);
				nickNameLabel.setSize(200, 30);
				nickNameLabel.setLocation(25, 90);
			myInfoPanel.add(nickNameLabel);
			
			nickNameText = new JLabel();
				nickNameText.setBackground(Color.white);
				nickNameText.setFont(font);
				nickNameText.setSize(200, 30);
				nickNameText.setLocation(50, 120);
				nickNameText.setText(myInfo.getNickName());
			myInfoPanel.add(nickNameText);
			
			recordLabel = new JLabel("전 적");
				recordLabel.setBackground(Color.white);
				recordLabel.setFont(font);
				recordLabel.setSize(200, 30);
				recordLabel.setLocation(25, 160);
			myInfoPanel.add(recordLabel);
			
			recordText = new JLabel();
				recordText.setBackground(Color.white);
				recordText.setFont(font);
				recordText.setSize(200, 30);
				recordText.setLocation(50, 190);
				recordText.setText(record);
			myInfoPanel.add(recordText);
		one.add(myInfoPanel);
		
		//방정보
		roomInfoPanel = new JPanel();
			roomInfoPanel.setBackground(Color.white);
			roomInfoPanel.setLayout(null);
			roomInfoPanel.setSize(530, 270);
			roomInfoPanel.setLocation(260, 5);
			roomInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 5),""));
		
			roomListPanel = new JPanel();	//방리스트
				roomListPanel.setBackground(Color.pink);
				roomListPanel.setLayout(new BorderLayout());
				roomListPanel.setSize(500, 200);
				roomListPanel.setLocation(15, 60);
				roomListPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3),""));		
		roomInfoPanel.add(roomListPanel);
		
		for(RoomInfo roomInfo : createdRoomList) {
				RoomListPanel panel = new RoomListPanel(roomInfo.getId(), roomInfo.getRoomName(), roomInfo.getLevel(), roomInfo.getUserCount());
				roomListModel.addElement(panel);	
		}
		roomList = new JList<RoomListPanel>(roomListModel);
			roomList.setCellRenderer(new RoomListRenderer());
			roomList.setBackground(Color.pink);
			roomList.setFont(font4);
			roomList.setFixedCellHeight(30);
			
			roomListPanel.add(new JScrollPane(roomList),"Center");	//대화창패널에 리스트붙이기
		roomInfoPanel.add(roomListPanel);
				
		makeRoomBtn = new JButton("방 만 들 기");
			makeRoomBtn.setBackground(Color.pink);
			makeRoomBtn.setFont(font);
			makeRoomBtn.setSize(150, 40);
			makeRoomBtn.setLocation(360, 15);
				
			makeRoomBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					makeRoom = new MakeRoomFrame(mainFrame, myInfo);
					
				}
			});
		roomInfoPanel.add(makeRoomBtn);
		one.add(roomInfoPanel);
	
	}
	
	/**
	 *  대화창 & 대기실접속자정보
	**/
	void details_2() {
		//대기실 정보
		waitingInfoPanel = new JPanel();
			waitingInfoPanel.setBackground(Color.white);
			waitingInfoPanel.setLayout(null);
			waitingInfoPanel.setSize(250, 270);
			waitingInfoPanel.setLocation(5, 5);
			waitingInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 5),"")); //테두리설정
			
			waitingLabel1 = new JLabel("대 기 실");
				waitingLabel1.setBackground(Color.white);
				waitingLabel1.setFont(font);
				waitingLabel1.setSize(80, 30);
				waitingLabel1.setLocation(13, 15);
			waitingInfoPanel.add(waitingLabel1);
			
			waitingLabel1 = new JLabel("( 접속중 )");
				waitingLabel1.setBackground(Color.white);
				waitingLabel1.setFont(font2);
				waitingLabel1.setSize(100, 30);
				waitingLabel1.setLocation(90, 15);
			waitingInfoPanel.add(waitingLabel1);
			
			waitingListPanel = new JPanel();
				waitingListPanel.setBackground(Color.pink);
				waitingListPanel.setLayout(new BorderLayout());
				waitingListPanel.setSize(230, 210);
				waitingListPanel.setLocation(10, 50);
				waitingListPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3),"")); //테두리설정
				
				OnlineUserPanel panel = new OnlineUserPanel(myInfo.getNickName(), myInfo.getStateName());
				waitingListModel.addElement(panel);
				for(User user : onlineList) {
					if(user.getId()!=myInfo.getId()) {
						panel = new OnlineUserPanel(user.getNickName(), user.getStateName());
						waitingListModel.addElement(panel);	
					}
				}
				waitingList = new JList<OnlineUserPanel>(waitingListModel);
					waitingList.setCellRenderer(new OnlineUserRenderer());
					waitingList.setBackground(Color.pink);
					waitingList.setFont(font4);
					waitingList.setFixedCellHeight(30);
					
				waitingListPanel.add(new JScrollPane(waitingList),"Center");	//대화창패널에 리스트붙이기
			waitingInfoPanel.add(waitingListPanel);
		two.add(waitingInfoPanel);
	
		//대화창
		talkingPanel = new JPanel();
			talkingPanel.setBackground(Color.white);
			talkingPanel.setLayout(null);
			talkingPanel.setSize(530, 270);
			talkingPanel.setLocation(260, 5);
			talkingPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 5),"")); //테두리설정
			
			talkListPanel = new JPanel();
				talkListPanel.setBackground(Color.pink);
				talkListPanel.setLayout(new BorderLayout());
				talkListPanel.setSize(520, 221);
				talkListPanel.setLocation(5, 5);
			talkingPanel.add(talkListPanel);
			
			talkInput = new JTextField("");
				talkInput.setFont(font4);
				talkInput.setSize(460, 40);
				talkInput.setLocation(5, 225);
			talkingPanel.add(talkInput);
			
			talkBtn = new JButton("전송");
				talkBtn.setBackground(Color.black);
				talkBtn.setFont(font3);
				talkBtn.setForeground(Color.white);
				talkBtn.setSize(60, 40);
				talkBtn.setLocation(465, 225);
				
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
			
			talkList = new JList<String>(talkListModel);	//JList 객체를 생성할 때, 생성자 파라미터로 DefaultListModel 객체를 전달해 주어야 함.
															//이 DefaultListModel 객체에 담겨진 데이터가, 자동으로 JList 객체에 보여짐.
			talkList.setBackground(Color.pink);
			talkList.setFont(font4);
			talkListPanel.add(new JScrollPane(talkList),"Center");	//대화창패널에 리스트붙이기
		two.add(talkingPanel);
		
	}
	
	
}
