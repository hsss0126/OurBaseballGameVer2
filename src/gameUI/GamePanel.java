package gameUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	MainFrame mainFrame;
	
	private JPanel onePanel;
		private JLabel myNumLabel;	//내 숫자
		private JPanel myNumPanel; 	//그리드3,1
			private JLabel num1Text;
			private JLabel num2Text;
			private JLabel num3Text;
		private JPanel numPanel;	//예상숫자표시
			private String[] numString = { "0","1","2","3","4","5","6","7","8","9"};
			private JLabel[] numLabel = new JLabel[10];
		private JLabel numInputLabel;	//숫자입력
		private JTextField numInputText;
		private JButton sendBtn;
	
	private JPanel twoPanel;
		private JLabel myRecordLabel;	//내 기록화면
		private JPanel myRecordPanel;	//기록내용이 들어갈 패널
			private JList<String> myRecordList;
			private DefaultListModel<String> myRecordListModel = new DefaultListModel<>();
			private JLabel memoLabel;	//내 메모장
			private JTextArea memo;	
		private JLabel awayRecordLabel;	//상대방 기록화면
			private JPanel awayRecordPanel;
			private JList<String> awayRecordList;
			private DefaultListModel<String> awayRecordListModel = new DefaultListModel<>();
		
		private Color color1 = new Color(202,236,244);	//연하늘
		private Color color2 = new Color(46,38,79);		//네이비
	
	public GamePanel(MainFrame mf) {
		mainFrame = mf;
		initialize();
	}
	
	private void initialize() {
		setLayout(null);
		setBackground(Color.WHITE);
		setSize(800,600);
		//setUndecorated(true); //프레임 타이틀바 없애기
		setVisible(true);
		setLocation(1100,100);
		
		/* 유저 정보 & 방 정보 */
		onePanel = new JPanel();
			onePanel.setBackground(Color.lightGray);
			onePanel.setLayout(null);
			onePanel.setSize(800, 200);
			onePanel.setLocation(0, 0);
		add(onePanel);
		
		/* 대화창  */
		twoPanel = new JPanel();
			twoPanel.setBackground(Color.gray);
			twoPanel.setLayout(null);
			twoPanel.setSize(800, 400);
			twoPanel.setLocation(0, 200);
		add(twoPanel);
		
		details_1();
		details_2();
		
	}
	
	void details_1() {
		
		myNumLabel = new JLabel("My NUMBER");
			myNumLabel.setBackground(Color.white);
			myNumLabel.setFont(new Font("맑은 고딕",Font.BOLD,28));
			myNumLabel.setSize(200, 50);
			myNumLabel.setLocation(60, 30);
		onePanel.add(myNumLabel);
		
		myNumPanel = new JPanel();
			myNumPanel.setBackground(Color.white);
			myNumPanel.setLayout(new GridLayout(1,3));
			myNumPanel.setSize(210, 70);
			myNumPanel.setLocation(50, 85);
			myNumPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
			
			num1Text = new JLabel("1");
			num1Text.setBackground(Color.white);
			num1Text.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num1Text.setSize(70, 70);
			num1Text.setBorder(new MatteBorder(5,5,5,5,Color.black));
			num1Text.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel.add(num1Text);
			
			num2Text = new JLabel("1");
			num2Text.setBackground(Color.white);
			num2Text.setFont(new Font("맑은 고딕",Font.BOLD,40));
			num2Text.setSize(70, 70);
			num2Text.setBorder(new MatteBorder(5,0,5,5,Color.black));
			num2Text.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel.add(num2Text);
			
			num3Text = new JLabel("1");
				num3Text.setBackground(Color.white);
				num3Text.setFont(new Font("맑은 고딕",Font.BOLD,40));
				num3Text.setSize(70, 70);
				num3Text.setBorder(new MatteBorder(5,0,5,5,Color.black));
				num3Text.setHorizontalAlignment(JLabel.CENTER);
			myNumPanel.add(num3Text);
			
		onePanel.add(myNumPanel);
		
		numPanel = new JPanel();
			numPanel.setBackground(Color.white);
			numPanel.setLayout(new GridLayout(1,10));
			numPanel.setSize(450, 50);
			numPanel.setLocation(310, 50);
			numPanel.setBorder(new BevelBorder(BevelBorder.RAISED)); //테두리설정
				
				//0123456789
				for(int i = 0; i < numString.length; i++) {
					numPanel.add(numLabel[i] = new JLabel(numString[i]));	//생성
					
					numLabel[i].setBackground(Color.white);
					numLabel[i].setFont(new Font("맑은 고딕",Font.BOLD,30));
					numLabel[i].setSize(70, 70);
					numLabel[i].setHorizontalAlignment(JLabel.CENTER);
					
					//클릭시 색 변경
					numLabel[i].addMouseListener(new MouseListener() {
						public void mouseReleased(MouseEvent e) {
						}
						public void mousePressed(MouseEvent e) {
						}
						public void mouseExited(MouseEvent e) {
						}
						public void mouseEntered(MouseEvent e) {
						}
						@Override
						public void mouseClicked(MouseEvent e) {
							// TODO Auto-generated method stub
							JLabel ob = (JLabel)e.getSource();
							
							if(ob.getForeground() ==  Color.red) {
								ob.setForeground(Color.black);
							}else {
								ob.setForeground(Color.red);
							}
							
						}
					});
					
					//테두리 설정
					if(i == 0) {	
						numLabel[i].setBorder(new MatteBorder(3,3,3,3,Color.black));
					}else {
						numLabel[i].setBorder(new MatteBorder(3,0,3,3,Color.black));
					}
				}
				
		onePanel.add(numPanel);
		
		numInputLabel = new JLabel("숫자입력 :");
			numInputLabel.setBackground(Color.white);
			numInputLabel.setFont(new Font("맑은 고딕",Font.BOLD,25));
			numInputLabel.setSize(130, 70);
			numInputLabel.setLocation(310, 110);
		onePanel.add(numInputLabel);
		
		numInputText = new JTextField();
			numInputText.setBackground(Color.white);
			numInputText.setFont(new Font("맑은 고딕",Font.BOLD,40));
			numInputText.setSize(200, 50);
			numInputText.setLocation(442, 120);
			numInputText.setHorizontalAlignment(JLabel.CENTER);
		onePanel.add(numInputText);
		
		sendBtn = new JButton("전 송");
			sendBtn.setBackground(Color.pink);
			sendBtn.setFont(new Font("맑은 고딕",Font.BOLD,20));
			sendBtn.setSize(100, 50);
			sendBtn.setLocation(650, 120);
		onePanel.add(sendBtn);
		
	}
	
	void details_2() {
		
		//내 기록판
		myRecordPanel = new JPanel();
			myRecordPanel.setBackground(Color.white);
			myRecordPanel.setLayout(new GridLayout(1,2));
			myRecordPanel.setSize(500, 290);
			myRecordPanel.setLocation(20,60);
			
			myRecordLabel = new JLabel("My RECORD");
				myRecordLabel.setForeground(color2);
				myRecordLabel.setBackground(Color.white);
				myRecordLabel.setFont(new Font("맑은 고딕",Font.BOLD,25));
				myRecordLabel.setSize(250, 70);
				myRecordLabel.setLocation(20, 0);
			twoPanel.add(myRecordLabel);
			
			//나의 기록판 List뷰
			myRecordList = new JList<String>(myRecordListModel);
				myRecordList.setBackground(color1);
				myRecordList.setFont(new Font("맑은 고딕",Font.BOLD,20));
			myRecordPanel.add(new JScrollPane(myRecordList),"Center");	//대화창패널에 리스트붙이기
				
			//나의 메모장
			memoLabel = new JLabel("MEMO");
				memoLabel.setForeground(color2);
				memoLabel.setBackground(Color.white);
				memoLabel.setFont(new Font("맑은 고딕",Font.BOLD,25));
				memoLabel.setSize(150, 70);
				memoLabel.setLocation(270, 0);
			twoPanel.add(memoLabel);
				
			memo = new JTextArea();
				memo.setBackground(Color.white);
				memo.setFont(new Font("맑은 고딕",Font.BOLD,22));
			myRecordPanel.add(new JScrollPane(memo),"Center");
			
		twoPanel.add(myRecordPanel);
		
		//상대방 기록판
		awayRecordPanel = new JPanel();
			awayRecordPanel.setBackground(Color.white);
			awayRecordPanel.setLayout(new GridLayout(1, 1));
			awayRecordPanel.setSize(240, 290);
			awayRecordPanel.setLocation(540,60);
			
			awayRecordLabel = new JLabel("Away RECORD");
				awayRecordLabel.setForeground(color2);
				awayRecordLabel.setBackground(Color.white);
				awayRecordLabel.setFont(new Font("맑은 고딕",Font.BOLD,25));
				awayRecordLabel.setSize(250, 70);
				awayRecordLabel.setLocation(540, 0);
			twoPanel.add(awayRecordLabel);
			
			//나의 기록판 List뷰
			awayRecordList = new JList<String>(awayRecordListModel);
				awayRecordList.setBackground(color1);
				awayRecordList.setFont(new Font("맑은 고딕",Font.BOLD,20));
			awayRecordPanel.add(new JScrollPane(awayRecordList),"Center");	//대화창패널에 리스트붙이기
		
		twoPanel.add(awayRecordPanel);
		
		
	}
	
}
