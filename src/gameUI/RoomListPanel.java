package gameUI;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class RoomListPanel {

	private JPanel panel;
	private JLabel roomIdLabel, roomNameLabel, levelLabel, userCountLabel;
	
	private Font font4 = new Font("맑은 고딕", Font.PLAIN, 15);
	
	public RoomListPanel(int roomId, String roomName, int level, int userCount) {
		panel = new JPanel();
		panel.setLayout(null);
		
		roomIdLabel = new JLabel(Integer.toString(roomId));
		roomIdLabel.setHorizontalAlignment(JLabel.CENTER);
		roomIdLabel.setFont(font4);
		roomIdLabel.setSize(50, 30);
		roomIdLabel.setLocation(0, 0);
		panel.add(roomIdLabel);
		
		roomNameLabel = new JLabel(roomName);
		roomNameLabel.setHorizontalAlignment(JLabel.CENTER);
		roomNameLabel.setFont(font4);
		roomNameLabel.setSize(290, 30);
		roomNameLabel.setLocation(50, 0);
		panel.add(roomNameLabel);
		
		levelLabel = new JLabel(Integer.toString(level));
		levelLabel.setHorizontalAlignment(JLabel.CENTER);
		levelLabel.setFont(font4);
		levelLabel.setSize(90, 30);
		levelLabel.setLocation(340, 0);
		panel.add(levelLabel);
		
		userCountLabel = new JLabel(Integer.toString(userCount)+"/2");
		userCountLabel.setHorizontalAlignment(JLabel.CENTER);
		userCountLabel.setFont(font4);
		userCountLabel.setSize(80, 30);
		userCountLabel.setLocation(420, 0);
		panel.add(userCountLabel);
	}

	public JPanel getPanel() {
		return panel;
	}
	
	public int getRoomId() {
		return Integer.parseInt(roomIdLabel.getText());
	}
	
}
