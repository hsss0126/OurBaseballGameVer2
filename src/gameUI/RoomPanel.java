package gameUI;

import javax.swing.JPanel;

import dto.RoomInfo;

public class RoomPanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private RoomInfo myRoomInfo;
	private Thread th;
	public RoomPanel(MainFrame mf) {
		this.mainFrame = mf;
		th = new Thread(this);
		th.start();
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
