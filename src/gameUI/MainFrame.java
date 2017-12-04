package gameUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import client.ClientBackground;
import connection.RoomInfoConnection;
import connection.UserConnection;
import dto.RoomInfo;
import dto.User;


public class MainFrame extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;
//----------------------------------------------------
	private UserConnection userConnection;
	private RoomInfoConnection roomInfoConnection;
	
	private CardLayout cards = new CardLayout();
//----------------------------------------------------
	private JSONParser parser;
	private String result;
	private User myInfo;
	private RoomInfo myRoomInfo;
	private ClientBackground client;
	
	public MainFrame(String nickName) {
		myInfo = new User();
		myInfo.setNickName(nickName);
		initialize();
	}
	
	private void initialize() {
		
		//아이콘변경
		ImageIcon im = new ImageIcon(getClass().getClassLoader().getResource("icon6.png"));
		setIconImage(im.getImage()); 
	
		getContentPane().setLayout(cards);	//카드레이아웃
		setBackground(Color.WHITE);
		setSize(800,600);
		//setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		Dimension frameSize = this.getSize();	//프레임크기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	//모니터크기
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setResizable(false);	
		addWindowListener(this);
		
		userConnection = new UserConnection();
		roomInfoConnection = new RoomInfoConnection();
		
		result = userConnection.findByNickNameConnection(myInfo.getNickName());
		System.out.println(result);
		
		try {
			parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(result);
			myInfo.setId(Integer.parseInt((String)json.get("id")));
			myInfo.setWin(Integer.parseInt((String)json.get("win")));
			myInfo.setLose(Integer.parseInt((String)json.get("lose")));
			myInfo.setStateName((String) json.get("stateName"));
			System.out.println(myInfo.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		addWaitingPanel();
	}
	
	public void connectClient(int roomNo, String nickName, JPanel panel) {
		client = new ClientBackground();
		client.connect(roomNo, nickName, panel);
		if(roomNo == 0) {
			WaitingPanel wPanel = (WaitingPanel) panel;
			wPanel.chatSetting();
		} else {
			RoomPanel rPanel = (RoomPanel) panel;
			rPanel.chatSetting(myRoomInfo.getRoomName());
		}
	}
	public void sendMessage(String msg) {
		client.sendMessage(msg);
	}
	
	public void closeClient() {
		client.closeSocket();
	}
	
	public void addWaitingPanel() {
		getContentPane().add("WaitingPanel", new WaitingPanel(this));
	}
	
	public void addRoomPanel(int initIndex) {
		getContentPane().add("RoomPanel", new RoomPanel(this, initIndex));
	}
	
	public void addGamePanel() {
		getContentPane().add("GamePanel", new GamePanel(this));			//게임 화면 GamePanel패널 부착
	}
	
	public void removePanel(JPanel panel) {
		getContentPane().remove(panel);
	}
	
	public void changePanel() {
		cards.next(this.getContentPane());
	}
	
	public CardLayout getCardLayout() {
		return cards;
	}

	public User getMyInfo() {
		return myInfo;
	}

	public void setMyInfo(User myInfo) {
		this.myInfo = myInfo;
	}

	public RoomInfo getMyRoomInfo() {
		return myRoomInfo;
	}

	public void setMyRoomInfo(RoomInfo myRoomInfo) {
		this.myRoomInfo = myRoomInfo;
	}

	//프레임이 강제종료 될 때 클라이언트 소켓 제거
	@Override
	public void windowClosing(WindowEvent e) {
		//현재 방안에 있다면
		if(myRoomInfo != null) {
			//내가 host라면
			if(myRoomInfo.getHostId() == myInfo.getId()) {
				//상대방이 없다면 방 삭제
				if(myRoomInfo.getAwayId() == 0) {
					roomInfoConnection.deleteConnection(Integer.toString(myRoomInfo.getId()));
				} 
				//상대방이 있다면 host위임
				else {
					myRoomInfo.setHostId(myRoomInfo.getAwayId());
					myRoomInfo.setAwayId(0);
					myRoomInfo.setUserCount(1);
					roomInfoConnection.updateConnection(myRoomInfo);	//hostId를 awayId로 수정 후 awayId를 0으로 업데이트
				}
			}
			//내가 away라면
			else {
				myRoomInfo.setAwayId(0);
				myRoomInfo.setUserCount(1);
				roomInfoConnection.updateConnection(myRoomInfo);	//awayId를 0으로 업데이트
			}
		}
		myInfo.setStateId(0);
		userConnection.updateConnection(myInfo);
		client.closeSocket();
		System.exit(0);
	}
	
	@Override public void windowActivated(WindowEvent arg0) {}
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowOpened(WindowEvent e) {}
}
