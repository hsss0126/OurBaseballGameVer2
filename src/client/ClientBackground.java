package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JPanel;

import etc.URLs;
import gameUI.RoomPanel;
import gameUI.WaitingPanel;

public class ClientBackground implements Runnable{

	private DataOutputStream out;
	private DataInputStream in;
	private Socket socket;
	private WaitingPanel waitingPanel;
	private RoomPanel roomPanel;
	private String nickName;
	private int roomNo;
	
	public void connect(int roomNo, String nickName, JPanel panel) {
		//소켓생성해볼게
		try {
			this.roomNo = roomNo;
			//대기실은 방번호가 없어서 0으로 구별
			if(this.roomNo == 0) {
				this.waitingPanel = (WaitingPanel) panel;	
			} else {
				this.roomPanel = (RoomPanel) panel;
			}
			this.nickName = nickName;
			socket = new Socket(URLs.ipAddress, 9999);
			System.out.println("클라이언트 소켓 생성");
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			out.writeUTF(this.roomNo + "/" + nickName);
			System.out.println("클라이언트 메세지 전송완료!!! " + this.roomNo + "/" + nickName);
			new Thread(this).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String msg) {
		try {
			out.writeUTF(roomNo + "/" + nickName + "/" + msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while(in!=null) {
				String msg = in.readUTF();
				if(roomNo == 0) {
					waitingPanel.addChatText(msg);	
				} else {
					roomPanel.addChatText(msg);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
