import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
public class Login extends JFrame  {
	private TextField f1;
	private TextField f2;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	StuC scs=new StuC();
	//登陆界面
	public Login(){
		Container cp=getContentPane();//容器
		cp.setLayout(new GridLayout(3,1));//三行一列布局
		Label l1=new Label("用户名");
		Label l2=new Label("密   码");
		Panel p1=new Panel();
		Panel p2=new Panel();
		Panel p3=new Panel();
		f1=new TextField(10);
		f2=new TextField(10);
		f2.setEchoChar('*');//回显字符为*
		b1=new JButton("登录");
		b2=new JButton("重置");
		b3=new JButton("退出");
		p1.add(l1);//第一行添加label 1
		p1.add(f1);
		p2.add(l2);
		p2.add(f2);
		p3.add(b1);
		p3.add(b2);
		p3.add(b3);
		cp.add(p1);
		cp.add(p2);
		cp.add(p3);
		b1.addActionListener(new Enter());
		b2.addActionListener(new ReWrite());
		b3.addActionListener(new Close());
		}
	class Enter implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{      
         		if((f1.getText()).equals("admin")&&(f2.getText()).equals("123456"))
         		{		
         				scs.read();//初始化，从文件读入信息
        	 			XueSheng  frame1 = new XueSheng();
        	 			frame1.setBounds(200, 200, 300, 300);
        	 			frame1.setVisible(true);
         		}
             	else JOptionPane.showMessageDialog(null, "用户名或密码错误，请重新登录！");
		}
	}
	class ReWrite implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			f1.setText("");
			f2.setText("");
			f1.requestFocus();
		}
	}
	class Close implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			JButton bt=(JButton)e.getSource();
			if(bt==b3){
				System.exit(0);
			}
		}
}
//主函数 程序开始
	public static void main(String[] args) {
		Login log=new Login();
		log.setTitle("系统登录");
		log.setBounds(200, 200, 300, 300);
		log.setBackground(Color.blue);
		log.setVisible(true);
	}
	//信息管理界面内部类 进行初始化和保存
	class XueSheng extends JFrame implements ActionListener {
		private JButton cx, zj, tc, sc,xg,tj;
		public XueSheng() 
		{
			Container c = this.getContentPane();
			c.setLayout(new GridLayout(3, 1));
			c.setFont(new Font("true",Font.TRUETYPE_FONT,13));
			JPanel panel2 = new JPanel();
			JPanel panel1 = new JPanel();
			JLabel label1 = new JLabel("欢迎进入成绩管理",SwingConstants.CENTER);
			label1.setFont(new Font("true",Font.TRUETYPE_FONT,13));
			label1.setForeground(Color.blue);
			c.add(label1);
			//添加按钮
			cx = new JButton("查询");
			panel2.add(cx);
			zj = new JButton("增加");
			panel2.add(zj);
			sc = new JButton("删除");
			panel2.add(sc);
			tc = new JButton("退出");
			panel2.add(tc);
			xg = new JButton("修改");
			panel1.add(xg);
			tj = new JButton("统计");
			panel1.add(tj);
			c.add(panel2);
			c.add(panel1);
			cx.addActionListener(this);
			zj.addActionListener(this);
			sc.addActionListener(this);
			xg.addActionListener(this);
			tc.addActionListener(this);
			tj.addActionListener(this);
			this.setVisible(true);
		}
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == cx) {
				Find f = new Find();
			}
	        if(e.getSource()==zj){
	        	AddFI f = new AddFI();
	        }
	        if(e.getSource()==sc){
	        	Delet d = new Delet();
	        }
	        if(e.getSource()==xg){
	        	XiuGai x=new XiuGai();
	        }
	        if(e.getSource()==tc){
				shutDown();
			}
	        if(e.getSource()==tj){
			    Tongji t=new Tongji();
			}
		}
		private void shutDown()
		{
			scs.stor();
			JOptionPane.showMessageDialog(null, "信息已保存");
			this.dispose();
		}
	}
	//增加信息界面内部类，捕获文本框中信息创建Student对象，添加到Arraylist中，如果已存在该学号//给出提示信息，并重新添加。
	class AddFI extends JFrame implements ActionListener {
		private JTextField STNOText, SNAMEText, MAText, CHIText, JAVAText;
		private JButton b1, b2, b3;
		private String STNO, SNAME,MAT, CHI, JAVA;
		public AddFI() {
			super("添加学生信息");
			Container c2 = this.getContentPane();
			c2.setLayout(new GridLayout(3, 1));
			JPanel center = new JPanel(new GridLayout(5, 2));
			JPanel low = new JPanel(new FlowLayout());
			JLabel label1 = new JLabel("添加学生信息", SwingConstants.CENTER);
			label1.setFont(new Font("TRUE", Font.TRUETYPE_FONT, 20));
			c2.add(label1);
			STNOText = new JTextField(30);//30列文本框
			SNAMEText = new JTextField(30);
			CHIText = new JTextField(30);
			MAText = new JTextField(30);
			JAVAText = new JTextField(30);
			center.add(new JLabel("学号", SwingConstants.CENTER));//添加标签学号写在标签中间
			center.add(STNOText);//添加文本框
			center.add(new JLabel("姓名", SwingConstants.CENTER));
			center.add(SNAMEText);
			center.add(new JLabel("语文", SwingConstants.CENTER));
			center.add(CHIText);
			center.add(new JLabel("数学", SwingConstants.CENTER));
			center.add(MAText);
			center.add(new JLabel("java", SwingConstants.CENTER));
			center.add(JAVAText);
			c2.add(center);
			b1 = new JButton("添加");
			b2 = new JButton("清除");
			b3 = new JButton("退出");
			low.add(b1);
			low.add(b2);
			low.add(b3);
			c2.add(low);
			// 添加监听
			b1.addActionListener(this);
			b2.addActionListener(this);
			b3.addActionListener(this);
			this.setBounds(200, 200, 600, 400);
			this.setVisible(true);
			this.setTitle("添加学生信息");
		}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == b1) {
				try {
					addFI();
				} catch (FileNotFoundException e1) {	
					e1.printStackTrace();
				} catch (IOException e1) {	
					e1.printStackTrace();
				}
			}
			if (e.getSource() == b2) {
				clearForm();
			}
			if (e.getSource() == b3) {
				this.dispose();
			}
		}
		private void addFI() throws FileNotFoundException, IOException {
			STNO = STNOText.getText();
			SNAME = SNAMEText.getText();
			CHI = CHIText.getText();
			MAT = MAText.getText();
			JAVA = JAVAText.getText();
			if (STNO.length() == 0 || SNAME.length() == 0 || MAT.length() == 0
					|| JAVA.length() == 0 || CHI.length() == 0)
				JOptionPane.showMessageDialog(this, "请添加完全信息");
			else {
					Student a=new Student(SNAME,Integer.parseInt(STNO),
							Integer.parseInt(CHI),Integer.parseInt(MAT),Integer.parseInt(JAVA));
					int b=0;
					for(int i=0;i<scs.al.size();i++){
						if(scs.al.get(i).getNum()==Integer.parseInt(STNO)){
							b=1;
						}
					}
					if(b==0){
						scs.adds(a);
						JOptionPane.showMessageDialog(this, "添加成功");
					}
					else{
						JOptionPane.showMessageDialog(this, "已存在");	
					}
			}
		}
		private void clearForm() {
			STNOText.setText("");
			SNAMEText.setText("");
			MAText.setText("");
			CHIText.setText("");
			JAVAText.setText("");	
		}
	}
	//查询信息界面内部类,根据输入的学号，在arraylist中查找对应学号的学生信息，分别输出
	class Find extends JFrame implements ActionListener {

		private JTextField STNOText, SNAMEText, MAText, CHIText, JAVAText;
		private String STNO;
		private JButton b1, b2;
		public Find() {
			Container c1 = this.getContentPane();
			c1.setLayout(new GridLayout(4, 1));
			JLabel label1 = new JLabel("查询学生信息", SwingConstants.CENTER);
			JLabel label0 = new JLabel("请输入你的学号",SwingConstants.CENTER);
			JPanel pp = new JPanel(new GridLayout(2, 1));
			pp.add(label1);
			pp.add(label0);
			c1.add(pp);
			JPanel p1 = new JPanel();
			STNOText = new JTextField(10);
			p1.add(STNOText);
			c1.add(p1);
			JPanel p2 = new JPanel();
			b1 = new JButton("查询");
			b2 = new JButton("退出");
			b1.addActionListener(this);
			b2.addActionListener(this);
			p2.add(b1);
			p2.add(b2);
			c1.add(p2);
			JPanel center = new JPanel(new GridLayout(4, 2));
			SNAMEText = new JTextField(30);
			CHIText = new JTextField(30);
			MAText = new JTextField(30);
			JAVAText = new JTextField(30);
			center.add(new JLabel("姓名", SwingConstants.CENTER));
			center.add(SNAMEText);
			center.add(new JLabel("语文", SwingConstants.CENTER));
			center.add(CHIText);
			center.add(new JLabel("数学", SwingConstants.CENTER));
			center.add(MAText);
			center.add(new JLabel("java", SwingConstants.CENTER));
			center.add(JAVAText);
			c1.add(center);
			this.setVisible(true);
			this.setBounds(200, 200, 400, 300);
		}
		public void actionPerformed(ActionEvent e){
				if (e.getSource() == b1) {
						STNO = STNOText.getText();
						int k=0;
						for(int i=0;i<scs.al.size();i++)
						{
							if(Integer.parseInt(STNO)==scs.al.get(i).getNum())
							{
								SNAMEText.setText(scs.al.get(i).getName());
								MAText.setText(String.valueOf(scs.al.get(i).getShuxue()));
								CHIText.setText(String.valueOf(scs.al.get(i).getYuwen()));
								JAVAText.setText(String.valueOf(scs.al.get(i).getJava()));
								k=1;
							}
						}
						if(k==0){
							JOptionPane.showMessageDialog(this, "查无此人");
						}	
				}
				if (e.getSource() == b2) {
					this.dispose();
				}
		}
	}
	
	//删除信息界面，通过输入的学号进行查找并在arraylist中移除
	class Delet extends JFrame implements ActionListener{
		private JButton yes;
		private JButton cancle;
		private JTextField text1;
		private String STNO;
		public Delet(){
			Container c3 = this.getContentPane();
			c3.setLayout(new GridLayout(3, 1));
			c3.setFont(new Font("true",Font.TRUETYPE_FONT,13));
			JPanel p1 = new JPanel();
			JPanel p2 = new JPanel();
			JLabel label1 = new JLabel("删除学生信息",SwingConstants.CENTER);
			label1.setFont(new Font("true",Font.TRUETYPE_FONT,13));
			label1.setForeground(Color.blue);
			c3.add(label1);
			JLabel label2 = new JLabel("请输入学号");
			text1 = new JTextField(10);
			p1.add(label2);
			p1.add(text1);
			c3.add(p1);
			yes = new JButton("确定");
			cancle = new JButton("退出");
			p2.add(yes);
			p2.add(cancle);
			c3.add(p2);
			yes.addActionListener(this);
			cancle.addActionListener(this);
			this.setTitle("删除学生信息");
			this.setBounds(200,200,400,300);
			this.setVisible(true);
		}
	   public void actionPerformed(ActionEvent e){
				if(e.getSource()==yes){	
					delt();
				}
				if(e.getSource()==cancle){
					this.dispose();
				}
		}
		private void delt(){
				STNO  = text1.getText();
				scs.del(Integer.parseInt(STNO));
				JOptionPane.showMessageDialog(this, "删除成功");
			}
	}
	//统计界面，对arraylist中所有对象进行求总分，并按总分从达到小排序
	class Tongji extends JFrame implements ActionListener{
		private JButton b1,b2;
		JTextArea t;
		public Tongji(){
		Container c1 = this.getContentPane();
		c1.setLayout(new GridLayout(3, 1));
		JPanel p1 =new JPanel();
		JLabel label1 = new JLabel("统计信息", SwingConstants.CENTER);
		p1.add(label1);
		c1.add(p1);
		t=new JTextArea();
		JScrollPane scroll=new JScrollPane(t);
		c1.add(scroll);
		JPanel p3=new JPanel();
		b1 = new JButton("统计");
		b2 = new JButton("退出");
		p3.add(b1);
		p3.add(b2);
		c1.add(p3);
		this.setBounds(200,200,400,300);
		this.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == b1) 
			{
				scs.sum();
			    scs.sort();
			    String[] s=new String[100];
			    String s1="总分从大到小\n姓名 学号 数学 语文 java 总分";
			    for(int i=0;i<scs.al.size();i++){
			    	 s[i]=scs.al.get(i).getName()+"   "+scs.al.get(i).getNum()+"   "+scs.al.get(i).getShuxue()
		    			+"   "+scs.al.get(i).getYuwen()+"   "+scs.al.get(i).getJava()+"   "+scs.al.get(i).getSum();
			    }
			    for(int i=0;i<scs.al.size();i++){
			    	s1=s1+"\n"+s[i];
			    }
			    t.append(s1);
			}
			if (e.getSource() == b2) 
			{
			this.dispose();
			}
		}
	}
	//修改信息界面，捕获输入的学号，查找该生信息，直接在文本框中修改，类似添加功能冲新添加该生信息
	class XiuGai extends JFrame implements ActionListener{
		private JTextField STNOText, SNAMEText, MAText, CHIText, JAVAText;
		private JButton b1,b2,b3;
		public XiuGai(){
			Container c4 = this.getContentPane();
			c4.setLayout(new GridLayout(4, 1));
			c4.setFont(new Font("true",Font.TRUETYPE_FONT,13));
			JPanel up = new JPanel();
			JPanel center1 = new JPanel();
			JPanel center2 = new JPanel(new GridLayout(4, 2));
			JPanel low = new JPanel();
			JLabel label11 = new JLabel("需要修改的学号");
			STNOText=new JTextField(15);
			up.add(label11);
			up.add(STNOText);
			c4.add(up);
			b1=new JButton("查找");
			center1.add(b1);
			c4.add(center1);
			JLabel label21 = new JLabel("姓名", SwingConstants.CENTER);
			JLabel label22 = new JLabel("数学", SwingConstants.CENTER);
			JLabel label23 = new JLabel("语文", SwingConstants.CENTER);
			JLabel label24 = new JLabel("java", SwingConstants.CENTER);
			SNAMEText=new JTextField(22);
			MAText=new JTextField(22);
			CHIText=new JTextField(22);
			JAVAText=new JTextField(22);
			center2.add(label21);
			center2.add(SNAMEText);
			center2.add(label22);
			center2.add(MAText);
			center2.add(label23);
			center2.add(CHIText);
			center2.add(label24);
			center2.add(JAVAText);
			c4.add(center2);
			b2=new JButton("修改");
			b3=new JButton("退出");
			low.add(b2);
			low.add(b3);
			c4.add(low);
			b1.addActionListener(this);
			b2.addActionListener(this);
			b3.addActionListener(this);
			this.setTitle("修改信息");
			this.setBounds(200, 200, 600, 400);
			this.setVisible(true);
		}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == b1) {
				for(int i=0;i<scs.al.size();i++){
					if(Integer.parseInt(STNOText.getText())==scs.al.get(i).getNum()){
						SNAMEText.setText(scs.al.get(i).getName());
						MAText.setText(String.valueOf(scs.al.get(i).getShuxue()));
						CHIText.setText(String.valueOf(scs.al.get(i).getYuwen()));
						JAVAText.setText(String.valueOf(scs.al.get(i).getJava()));
					}
				}
				
			}
			if (e.getSource() == b2) {
				if (SNAMEText.getColumns() == 0 || MAText.getColumns() == 0
						|| JAVAText.getColumns() == 0 || CHIText.getColumns() == 0)
					JOptionPane.showMessageDialog(this, "请添加完全信息");
				else {		
					scs.del(Integer.parseInt(STNOText.getText()));
					Student a=new Student(SNAMEText.getText(),Integer.parseInt(STNOText.getText()),
								Integer.parseInt(CHIText.getText()),Integer.parseInt(MAText.getText()),
								Integer.parseInt(JAVAText.getText()));
					scs.adds(a);
					JOptionPane.showMessageDialog(this, "修改成功");	
				}
			}
			if (e.getSource() == b3) {
				this.dispose();
			}
		}
	}
}
