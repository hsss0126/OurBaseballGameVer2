package listrenderer;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import gameUI.OnlineUserPanel;

public class OnlineUserRenderer implements ListCellRenderer<OnlineUserPanel>{

	JPanel panel;
	@Override
	public Component getListCellRendererComponent(JList <? extends OnlineUserPanel> list, OnlineUserPanel onlinePanel, int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		panel = onlinePanel.getPanel();
		panel.setOpaque(true);
		
		if (isSelected) {
		    panel.setBackground(list.getSelectionBackground());
		    panel.setForeground(list.getSelectionForeground());
		} else {
			panel.setBackground(list.getBackground());
			panel.setForeground(list.getForeground());
		}
		
		return panel;
	}

}
