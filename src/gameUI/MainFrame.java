package gameUI;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connection.UserConnection;
import dto.User;

public class MainFrame extends JFrame{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//----------------------------------------------------
	private UserConnection userConnection;
	private JFrame frame;
	static MakeRoomFrame makeRoom;
	
	private CardLayout cards = new CardLayout();
	
//----------------------------------------------------

	private JSONParser parser;
	private String myNickName;
	private String result;
	private User user;
	

	/**
	 * Create the application.
	 */
	public MainFrame(String nickName) {
		this.myNickName = nickName;
		user = new User();
		initialize();
	}
	
	private void initialize() {
		userConnection = new UserConnection();
	
		getContentPane().setLayout(cards);	//카드레이아웃
		setBackground(Color.WHITE);
		setSize(800,600);
		//setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		setLocation(1100,100);
		setResizable(false);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userConnection = new UserConnection();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add("WaitingPanel", new WaitingPanel(this));
		getContentPane().add("RoomPanel", new RoomPanel());
		
		result = userConnection.infoConnection(myNickName);
		System.out.println(result);
		
		try {
			parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(result);
			user.setId(Integer.parseInt((String)json.get("id")));
			user.setNickName((String) json.get("nickName"));
			user.setWin(Integer.parseInt((String)json.get("win")));
			user.setLose(Integer.parseInt((String)json.get("lose")));
			user.setStateName((String) json.get("stateName"));
			System.out.println(user.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public void  changePanel() {
		cards.next(this.getContentPane());
	}
	
	public CardLayout getCardLayout() {
		return cards;
	}


}
