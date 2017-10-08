import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * ʵ�ֱ༭��ɾ������ʾ��־�Ĺ���
 */
public class CalendarNotePad extends JPanel{
	
	private JTextArea text;//�༭��־���ı���
	private JTextField showMessaga;//��ʾ����
	private JPopupMenu menu;//�˵�
	private Color color;
	private JMenuItem itemCopy,itemCut,itemPaste,itemClear;//�˵���
	
	/*
	 * ���췽����������������
	 */
	public CalendarNotePad() {
		menu = new JPopupMenu();
		itemCut = new JMenuItem("����");
		itemCopy = new JMenuItem("����");
		itemPaste = new JMenuItem("ճ��");
		itemClear = new JMenuItem("���");
		itemCut.addActionListener(new ActionListen());
		itemCopy.addActionListener(new ActionListen());
		itemPaste.addActionListener(new ActionListen());
		itemClear.addActionListener(new ActionListen());
		menu.add(itemCut);
		menu.add(itemCopy);
		menu.add(itemPaste);
		menu.add(itemClear);
		showMessaga = new JTextField();
		showMessaga.setHorizontalAlignment(JTextField.CENTER);
		showMessaga.setFont(new Font("timesRoman", Font.BOLD, 16));
		showMessaga.setForeground(Color.blue);
		showMessaga.setBackground(new Color(216,224,231));
		showMessaga.setBorder(BorderFactory.createRaisedBevelBorder());
		showMessaga.setEditable(false);
		text = new JTextArea(10,10);
		text.addMouseListener(new MouseAdapter() {
			public void monsePressed(MouseEvent e){
				if(e.getModifiers()==InputEvent.BUTTON3_MASK)
					menu.show(text, e.getX(), e.getY());
			}
		});
		setLayout(new BorderLayout());
		add(showMessaga,BorderLayout.NORTH);
		add(new JScrollPane(text), BorderLayout.CENTER);
		
	}
	//������־
	public void save(File dir, int year, int month, int day) {
		String dailyContent = text.getText();//��־����
		String fileName = ""+year+""+month+""+day+".txt";
		String key = ""+year+""+month+""+day;
		String[] dayFile = dir.list();
		boolean boo = false;
		//�鿴ĳ�����־�Ƿ����
		for(int k = 0;k<dayFile.length;k++){
			if(dayFile[k].startsWith(key)){
				boo = true;
				break;
			}
		}
		String m;
		if(boo){
			//��־���ڣ��޸���־
			m = ""+year+"��"+month+"��"+day+"����־��ȷ���޸���־��";
		}else{
			//��������־��������־
			m = ""+year+"��"+month+"��"+day+"��û����־��������־��";
		}
		int ok = JOptionPane.showConfirmDialog(this,m, "ѯ��",
				JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(ok == JOptionPane.YES_OPTION){
			try {
				File f = new File(dir,fileName);
				RandomAccessFile out = new RandomAccessFile(f, "rw");
				long fileEnd = out.length();
				byte[] bb = dailyContent.getBytes();
				out.write(bb);
				out.close();
			} catch (IOException exp) {
				
			}
		}
		
	}
	//ɾ����־
	public void delete(File dir, int year, int month, int day) {
		String key = ""+year+""+month+""+day;
		String[] dayFile = dir.list();
		boolean boo = false;
		for(int k = 0;k<dayFile.length;k++){
			if(dayFile[k].startsWith(key)){
				boo = true;
				break;
			}
		}
		if(boo){
			String m = "ɾ��"+year+"��"+month+"��"+day+"����־��";
			int ok = JOptionPane.showConfirmDialog(this,m, "ѯ��",JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if(ok==JOptionPane.YES_OPTION){
				String fileName = ""+year+""+month+""+day+".txt";
				File deleteFile = new File(dir,fileName);
				
			}			
			}else {
				String m = ""+year+"��"+month+"��"+day+"����־��¼";
				JOptionPane.showMessageDialog(this,m, "��ʾ",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		
	
	//��ȡ��־ 
	public void read(File dir, int year, int month, int day) {
		String fileName =""+year+""+month+""+day+".txt";
		String key = ""+year+""+month+""+day;
		String[] dayFile = dir.list();
		boolean boo = false;
		for(int k = 0;k<dayFile.length;k++){
			if(dayFile[k].startsWith(key)){
				boo = true;
				break;
			}
		}
		if(boo){
			String m = ""+year+"��"+month+"��"+day+"����־��Ҫ��ʾ������";
			int ok = JOptionPane.showConfirmDialog(this,m, "ѯ��",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(ok == JOptionPane.YES_OPTION){
				text.setText(null);
				try {
					File f = new File(dir,fileName);
					FileReader inOne = new FileReader(f);
					BufferedReader inTwo = new BufferedReader(inOne);
					String s = null;
					while((s = inTwo.readLine())!=null)
						text.append(s+"\n");
					inOne.close();
					inTwo.close();
				} catch (IOException exp) {
					
				}
			}
		}else{
			String m = ""+year+"��"+month+"��"+day+"����־��¼";
			JOptionPane.showMessageDialog(this,m, "��ʾ",JOptionPane.WARNING_MESSAGE);
		}
		
	}

    //������ʾ������
	public void setShowMessage(int year, int month, int day) {
		showMessaga.setText(""+year+"��"+month+"��"+day+"��");
		
		
	}
	//����ʾ��־���ı���

	public void setText(String str) {
		text.setText(str);
		
	}

	
	/*
	 * ����˵�������
	 */
	class ActionListen implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == itemCut)
				text.cut();
			else if (e.getSource()==itemCopy) 
				text.copy();
			else if (e.getSource() ==itemPaste) 
				text.paste();
			else if (e.getSource()== itemClear) 
				text.setText(null);
			
			
		}
		
	}


}
