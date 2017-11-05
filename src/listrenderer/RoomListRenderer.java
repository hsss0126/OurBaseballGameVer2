package listrenderer;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import gameUI.RoomListPanel;

public class RoomListRenderer implements ListCellRenderer<RoomListPanel>{

	private JPanel panel;
	@Override
	public Component getListCellRendererComponent(JList<? extends RoomListPanel> list, RoomListPanel roomListPanel, int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		panel = roomListPanel.getPanel();
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
