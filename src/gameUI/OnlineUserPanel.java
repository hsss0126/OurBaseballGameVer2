package gameUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OnlineUserPanel {

	private JPanel panel;
	private JLabel imgLabel, nickNameLabel, stateNameLabel;
	private Font font4 = new Font("맑은 고딕", Font.PLAIN, 15);

	public OnlineUserPanel(String nickName, String stateName) {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		imgLabel = new JLabel();
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("user.png"));
		imgLabel.setIcon(icon);
		panel.add(imgLabel, BorderLayout.WEST);
		
		nickNameLabel = new JLabel(" "+nickName);
		nickNameLabel.setFont(font4);
		panel.add(nickNameLabel, BorderLayout.CENTER);
		
		stateNameLabel = new JLabel(stateName + " ");
		stateNameLabel.setFont(font4);
		stateNameLabel.setHorizontalAlignment(JLabel.RIGHT);
		panel.add(stateNameLabel, BorderLayout.EAST);
		
	}

	public JPanel getPanel() {
		return panel;
	}

	
}
