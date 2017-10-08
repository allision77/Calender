import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/*
 * ��ʾ���ڣ��Լ��޸�calendarinfomation�е�����
 */
public class CalendarPad extends JPanel{
	private CalendarInfomation calendarInfomation;//�������ռ��йص�����
	private int year,month,day;//�꣬�£���
	private JLabel title[];//������ʾ���ڼ��ı�ǩ
	private JTextField[] showDay;//������ʾ���ڵ��ı���
	private JPanel north,center;
	private String[] weeks = {"��","һ","��","��","��","��","��"};
	private File dir;
	
	/*
	 * ���췽����������������
	 */
	public CalendarPad(){
		dir = new File("./dailyRecord");
		dir.mkdir();
		setLayout(new BorderLayout());
		north = new JPanel();
		north.setLayout(new GridLayout(1, 7));
		north.setBackground(new Color(216, 224, 231));
		center = new JPanel();
		center.setLayout(new GridLayout(6, 7));
		add(north,BorderLayout.NORTH);
		add(center,BorderLayout.SOUTH);
		title = new JLabel[7];
		for(int j = 0;j<7;j++){
			title[j] = new JLabel();
			title[j].setFont(new Font("TimeRoman", Font.BOLD, 12));
			title[j].setText(weeks[j]);
			title[j].setHorizontalAlignment(JLabel.CENTER);
			title[j].setBorder(BorderFactory.createRaisedBevelBorder());
			north.add(title[j]);
		}
		title[0].setForeground(Color.red);
		title[6].setForeground(Color.red);
		
	}
	/*
	 * ��������showDay����
	 */
	public void setShowDayTextField(JTextField[] text){
		showDay = text;
		for(int i = 0;i<showDay.length;i++){
			showDay[i].setFont(new Font("TimeRoman", Font.BOLD, 15));
			showDay[i].setHorizontalAlignment(JTextField.CENTER);
			showDay[i].setEditable(false);
			if(i%7==0)
				showDay[i].setForeground(Color.red);//�����������óɺ�ɫ
			if((i+1)%7==0)
				showDay[i].setForeground(Color.red);
			center.add(showDay[i]);
		}
	}
	/*
	 * ����������Ϣ
	 */
	public void setCalendarInfomation(CalendarInfomation calendarInfomation){
		this.calendarInfomation = calendarInfomation;
	}
	/*
	 * ��ʾ����
	 */
	public void showMonthCalendar(){
		String[] a = calendarInfomation.getMonthCalendar();
		for(int i = 0;i<42;i++)
			showDay[i].setText(a[i]);
		validate();
	}
	
	

}
