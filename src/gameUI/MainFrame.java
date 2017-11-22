package gameUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connection.UserConnection;
import dto.RoomInfo;
import dto.User;


public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
//----------------------------------------------------
	private UserConnection userConnection;
	
	private CardLayout cards = new CardLayout();
//----------------------------------------------------
	private JSONParser parser;
	private String result;
	private User myInfo;
	private RoomInfo myRoomInfo;
	
	public MainFrame(String nickName) {
		myInfo = new User();
		myInfo.setNickName(nickName);
		initialize();
	}
	
	private void initialize() {
	
		getContentPane().setLayout(cards);	//카드레이아웃
		setBackground(Color.WHITE);
		setSize(800,600);
		//setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		Dimension frameSize = this.getSize();	//프레임크기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	//모니터크기
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setResizable(false);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		userConnection = new UserConnection();
		
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
		getContentPane().add("WaitingPanel", new WaitingPanel(this));

	}
	public void addRoomPanel(int initIndex) {
		getContentPane().add("RoomPanel", new RoomPanel(this, initIndex));
	}
	
	public void addGamePanel() {
		getContentPane().add("GamePanel", new GamePanel(this));			//게임 화면 GamePanel패널 부착
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
}
