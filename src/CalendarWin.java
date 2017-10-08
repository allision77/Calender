import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
/*
 * ���࣬��ʾ�������±���ҳ��
 */

public class CalendarWin extends JFrame implements FocusListener{
	
	private JLabel preYear,preMonth,center,nextMonth,nextYear;
	private String dayText;
	private int year,month,day;
	private CalendarInfomation CalendarInfomation;//������Ϣ��
	private CalendarPad calenderPad;//����
	private CalendarNotePad notePad;//�ռǱ���
	private JTextField[] showDay;
	//���棬ɾ������ȡ��
	private JButton saveDailyRecord,deleteDailyRecord,readDailyRecord;
	private File dir;
	
	/*
	 * ���췽������ɴ��ڳ�ʼ��
	 */
	public CalendarWin(){
		dir = new File("./dailyRecord");//�����洢�ռǵ��ļ�
		dir.mkdir();//����Ŀ¼
		showDay = new JTextField[42];
		for(int i = 0;i<showDay.length;i++){
			showDay[i] = new JTextField();
			showDay[i].setBackground(Color.white);
			showDay[i].setLayout(new GridLayout(3, 3));
			showDay[i].addFocusListener(this);
			showDay[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					JTextField text = (JTextField) e.getSource();
					String str = text.getText().trim();
					try {
						day = Integer.parseInt(str);
					} catch (NumberFormatException exp) {
						// TODO: handle exception
					}
					CalendarInfomation.setDay(day);
					notePad.setShowMessage(year,month,day);
					notePad.setText("");
					
					
					
				}
			});
		}
		CalendarInfomation = new CalendarInfomation();
		calenderPad = new CalendarPad();
		notePad = new CalendarNotePad();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONDAY)+1;
		day = calendar.get(Calendar.DAY_OF_MONTH);
		JPanel titlePanel = new JPanel();
		initTitlePanel(titlePanel);
		CalendarInfomation.setYear(year);
		CalendarInfomation.setMonth(month);
		CalendarInfomation.setDay(day);
		calenderPad.setCalendarInfomation(CalendarInfomation);
		calenderPad.setShowDayTextField(showDay);
		notePad.setShowMessage(year, month, day);
		calenderPad.showMonthCalendar();
		doMark();//������־�����������
		add(titlePanel,BorderLayout.NORTH);
		JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT,calenderPad,notePad);
		add(splitV, BorderLayout.CENTER);
		saveDailyRecord = new JButton("������־");
		deleteDailyRecord = new JButton("ɾ����־");
		readDailyRecord = new JButton("��ȡ��־");
		saveDailyRecord.addActionListener(new ActionListener() {
			//��������־��ť��Ӷ���������
			
			@Override
			public void actionPerformed(ActionEvent e) {
				notePad.save(dir,year,month,day);//������־
				doMark();
			}
		});
		deleteDailyRecord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				notePad.delete(dir,year,month,day);
				doMark();
				
			}
		});
		readDailyRecord.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				notePad.read(dir,year,month,day);
				doMark();
				
			}
		});
		JPanel pSouth = new JPanel();
		pSouth.setBackground(new Color(216, 224, 231));
		pSouth.add(saveDailyRecord);
		pSouth.add(deleteDailyRecord);
		pSouth.add(readDailyRecord);
		add(pSouth, BorderLayout.SOUTH);
		setBounds(60,60,400,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		validate();
				
	}
	/*
	 * ��ʼ���޸���ݺ��·ݵĽ���
	 */
	public void initTitlePanel(JPanel titlePanel) {
		dayText = year+"��"+month+"��";
		preYear = new JLabel("<<",JLabel.CENTER);
		preMonth = new JLabel("<",JLabel.CENTER);
		center = new JLabel(dayText,JLabel.CENTER);
		nextMonth = new JLabel(">",JLabel.CENTER);
		nextYear = new JLabel(">>",JLabel.CENTER);
		preYear.setToolTipText("��һ��");
		preMonth.setToolTipText("��һ��");
		nextMonth.setToolTipText("��һ��");
		nextYear.setToolTipText("��һ��");
		preYear.setBorder(javax.swing.BorderFactory.createEmptyBorder(2,10,0,0));
		preMonth.setBorder(javax.swing.BorderFactory.createEmptyBorder(2,15,0,0));
		nextMonth.setBorder(javax.swing.BorderFactory.createEmptyBorder(2,0,0,15));
		nextYear.setBorder(javax.swing.BorderFactory.createEmptyBorder(2,0,0,10));
		preYear.addMouseListener(new MyMouseListener(preYear));
		preMonth.addMouseListener(new MyMouseListener(preMonth));
		nextMonth.addMouseListener(new MyMouseListener(nextMonth));
		nextYear.addMouseListener(new MyMouseListener(nextYear));
		titlePanel.add(preYear);
		titlePanel.add(preMonth);
		titlePanel.add(center);
		titlePanel.add(nextMonth);
		titlePanel.add(nextYear);
		titlePanel.setBackground(new Color(216, 224, 231));
		titlePanel.setPreferredSize(new java.awt.Dimension(210,25));
		
	}
	/*
	 * ������������
	 */
	class MyMouseListener implements MouseListener{
		
		JLabel label;
		 public MyMouseListener(final JLabel label) {
			this.label = label;
		}
		 //������ʱ������ǩ��ɫ��Ϊ��ɫ
		 @Override
			public void mouseEntered(MouseEvent me) {
				label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
				label.setForeground(Color.BLUE);
				
			}
		 //����뿪������ǩ��ɫ��Ϊ��ɫ
		 @Override
			public void mouseExited(MouseEvent me) {
				label.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
				label.setForeground(java.awt.Color.BLACK);
				
			}
		 //��갴�£�����ǩ��ɫ��Ϊ��ɫ
		 @Override
			public void mousePressed(MouseEvent me) {
			 label.setForeground(java.awt.Color.WHITE);	
			}
		 //���ſ�������ǩ��ɫ��Ϊ��ɫ
		 @Override
			public void mouseReleased(MouseEvent me) {
				label.setForeground(java.awt.Color.BLACK);
				
			}
			//��������޸�����
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == nextYear){
				year++;
			}else if (e.getSource() == preYear) {
				year--;
				
			}else if (e.getSource() == nextMonth) {
				month++;
				if(month>12){
					month = 1;
					year++;
				}
				
			}else if (e.getSource() == preMonth) {
				month--;
				if(month<1){
					month = 12;
					year--;
				}
				
			}
			center.setText(year+"��"+month+"��");
			CalendarInfomation.setYear(year);
			CalendarInfomation.setMonth(month);
			calenderPad.setCalendarInfomation(CalendarInfomation);
			calenderPad.showMonthCalendar();
			notePad.setShowMessage(year, month, 1);
			notePad.setText("");
			doMark();
			
			
		}
		
	}
	//�����ý��㣬�ı���ɫ
	@Override
	public void focusGained(FocusEvent e) {
		Component com = (Component) e.getSource();
		com.setBackground(new Color(255, 187, 255));
		
	}
	//���ʧȥ���㣬�ı���ɫ
	@Override
	public void focusLost(FocusEvent e) {
		Component com = (Component) e.getSource();
		com.setBackground(Color.white);
		
	}
	//������������ӱ��

	public void doMark() {
		for(int i = 0;i<showDay.length;i++){
			showDay[i].removeAll();
			String str = showDay[i].getText().trim();
			try {
				int n = Integer.parseInt(str);
				if(isHaveDailRecord(n)==true){
					JLabel mess = new JLabel("��");
					mess.setFont(new Font("timesroman", Font.PLAIN, 11));
					mess.setForeground(Color.RED);
					showDay[i].add(mess);
				}
				
			} catch (Exception exp) {
				
			}
		}
		calenderPad.repaint();
		calenderPad.validate();
		
	}
	//�жϸ������Ƿ�����־���з���true�����򷵻�false
	public boolean isHaveDailRecord(int n) {
		String key = ""+year+""+month+""+n;
		String[] dayFile = dir.list();
		boolean boo = false;
		for(int k = 0;k<dayFile.length;k++){
			if(dayFile[k].equals(key+".tat")){
				boo = true;
				break;
			}
		}
		return boo;
	}
	public static void main(String[] args) {
		new CalendarWin();
		
	}

}
