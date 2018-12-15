package test;

//-------------------------------------------------
//小学生四则运算软件V1.3
//Author:hufeiya ZJUT  2013年12月7日21:52:50
//本软件用界面实现了四则运算的基本功能，可选择题目数量、
//位数、运算类型，具有翻页、显示页码、保存数据等功能。
//V1.1：
//修改时间：2013-12-8 15:12:58
//修改内容： 修正了大多数的BUG,包括除数为0、翻页问题、
//空题默认、异常崩溃等。
//V1.2
//修改时间：2013-12-21 21:38:31
//修改内容：增加计时器线程，可以使用户自定义做题时间
//V1.3
//修改时间：2013-12-27 12:54:48
//修改内容：增加排名信息，内部用hashmap实现
//-------------------------------------------------
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class Expression// 表达式类，存放表达式
{
	int a;// 第一个数
	int b;// 第二个数
	int c;// 正确答案
	int d;// 输入答案
	char operator;// 运算符
	boolean is_right;

	public Expression() {
		a = b = c = 0;
		d = -1;
		is_right = false;
		operator = '+';
	}
}

class NumberTooBigException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public NumberTooBigException(String ErrorMessage) {
		message = ErrorMessage;
	}

	public String getMessage() {
		return message;
	}
}

public class Calculate {
	Vector<Expression> v;

	public Calculate() {
		v = new Vector<Expression>();
	}

	public void produceExpression(int bit, char operator) {
		Expression temp = new Expression();
		temp.operator = operator;
		temp.a = (int) (Math.random() * Math.pow(10, bit));
		temp.b = (int) (Math.random() * Math.pow(10, bit));
		switch (operator) {
		case '+':
			temp.c = temp.a + temp.b;
			break;
		case '-':
			temp.c = temp.a - temp.b;
			break;
		case '*':
			temp.c = temp.a * temp.b;
			break;
		case '/':
			temp.b = (temp.b == 0 ? // 排除除法被除数为0的情况
			temp.b = 1 + (int) (Math.random() * Math.pow(10, bit) - 1)
					: temp.b);
			temp.c = temp.a / temp.b;
			break;
		default:
			mixExpression(temp, bit);// 混合运算
		}

		v.add(temp);
	}

	// 混合运算，产生随机数
	public void mixExpression(Expression temp, int bit) {
		int rand = (int) (Math.random() * 4);
		switch (rand) {
		case 1:
			temp.c = temp.a + temp.b;
			temp.operator = '+';
			break;
		case 2:
			temp.c = temp.a - temp.b;
			temp.operator = '-';
			break;
		case 3:
			temp.c = temp.a * temp.b;
			temp.operator = '*';
			break;
		case 0:
			temp.b = (temp.b == 0 ? // 排除除法被除数为0的情况
			temp.b = 1 + (int) (Math.random() * Math.pow(10, bit) - 1)
					: temp.b);
			temp.c = temp.a / temp.b;
			temp.operator = '/';
			break;
		default:
		}
	}

	public void writeInfo(int num, String userName) {
		File myFile = new File(userName + ".his");
		FileOutputStream fos;
		File saveUserNameAndScore = new File("UserNameAndScore.data");
		try {
			fos = new FileOutputStream(myFile, true);
			PrintStream ps = new PrintStream(fos);
			double score = 0;
			ps.println(String.format("%5s%5s%5s%5s%5s%10s%5s", "", "", "", "",
					"正确答案", "你的答案", "判断"));
			ps.println();
			for (int i = 0; i < num; i++) {

				ps.println(String.format("%5d%5s%5d%5s%10d%10d%10s",
						v.get(i).a, "+", v.get(i).b, "=", v.get(i).c,
						v.get(i).d, (v.get(i).is_right ? "正确" : "错误")));
				ps.println();
				if (v.get(i).is_right) {
					score += 100.0 / num;
				}
			}
			ps.println("你的总分：" + score);
			ps.close();
			fos.close();
			// 下面保存用户名和得分到UserNameAndScore.data文件;
			fos = new FileOutputStream(saveUserNameAndScore, true);
			ps = new PrintStream(fos);
			ps.println(userName);
			ps.println(score);
			ps.close();
			fos.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	public static void main(String[] s) {
		OverAllFrame oaf = new OverAllFrame("小学生四则运算软件V1.3 By hufeiya ZJUT");
	}
}

// 主体框架
class OverAllFrame extends JFrame {
	TimeThread timer; // 计时器线程
	Calculate ar;// 内部算法
	JPanel headPanel;// 头面板
	JLabel welcomeLabel;// 欢迎信息
	JLabel timeUse; // 用时信息
	StartPanel st;
	String userName;
	RunningPanel rp;
	EndPanel ep;
	Vector<Expression> v;// 存放算式

	public OverAllFrame(String s) {
		super(s);
		userName = JOptionPane.showInputDialog("请输入用户名", "");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.initialize();
		setHeadPanel();
		this.setLocation(400, 100);
		this.setLayout(new BorderLayout());
		this.add(headPanel, BorderLayout.NORTH);
		this.add(st, BorderLayout.CENTER);
		this.setSize(500, 400);
		this.setVisible(true);
	}

	public void initialize()// 初始化控件
	{
		ar = new Calculate();
		headPanel = new JPanel();
		welcomeLabel = new JLabel("欢迎!  用户：" + userName);
		st = new StartPanel();
		rp = new RunningPanel();
		ep = new EndPanel();
		timeUse = new JLabel();
	}

	public void setHeadPanel() {
		headPanel.setLayout(new FlowLayout());
		headPanel.add(welcomeLabel);
		headPanel.add(timeUse);
	}

	// 开始面板
	class TimeThread extends Thread // 计时器线程
	{
		int min;
		int sec;
		int millis;
		long oldTime;
		long timeUsed;
		long timeSeted;
		JLabel display;
		boolean stop = false;

		public TimeThread(long timeSeted, JLabel display) {
			oldTime = System.currentTimeMillis();
			this.display = display;
			this.timeSeted = timeSeted;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();

			do {
				timeUsed = System.currentTimeMillis() - oldTime;
				min = (int) (timeUsed / 60000L);
				sec = (int) ((timeUsed % 60000L) / 1000L);
				millis = (int) ((timeUsed % 60000L) % 1000L);
				try {
					sleep(11);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}
				display.setText("已用时:" + min + ":" + sec + ":" + millis);
				display.setVisible(true);

			} while (timeUsed <= timeSeted && !stop);
			if (!stop)// 如果不是手动停止的就运行complete
				rp.complete();
		}

		public void setStop() {
			stop = true;
		}
	}

	class StartPanel extends JPanel {
		JPanel inputOptionPanel; // 输入选项面板①
		JPanel selectOperatorOptionPanel;// 选择运算符面板②
		JPanel bottomPanel; // 底部面板③

		JLabel inputBitLabel; // 输入位数提示①-1
		JTextField inputBitField; // 输入位数区域①-2
		JLabel inputNumLabel; // 输入题目个数提示①-3
		JTextField inputNumField; // 输入题目个数区域①-4
		JLabel inputTimeLabel; // 输入做题时间提示①-5
		JPanel inputTimePanel; // 输入做题时间区域①-6

		ButtonGroup operatorGroup;
		JRadioButton addButton; // 加法按钮②-1
		JRadioButton minusButton; // 减法按钮②-2
		JRadioButton multiplyButton; // 乘法按钮②-3
		JRadioButton divideButton; // 除法按钮②-4
		JRadioButton mixButton; // 混合运算按钮②-5

		JLabel errorInfo; // 错误信息③-1
		JButton startButton; // 开始按钮③-2

		JTextField inputByminutes; // 输入分钟区域①-6-1
		JLabel printMinute; // 打印提示“分”①-6-2
		JTextField inputByseconds; // 输入秒钟区域①-6-3
		JLabel printSecond; // 打印提示“秒”①-6-4

		public StartPanel() {
			// this.setLayout(new GridLayout(0,1));
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.initialize();
			this.setInputOptionPanel();
			this.setSelectOperatorOptionPanel();
			this.setBottomPanel();
			this.addStartEvent();
			this.add(inputOptionPanel);
			this.add(selectOperatorOptionPanel);
			this.add(bottomPanel);
		}

		public void initialize() // 初始化所有控件
		{
			inputOptionPanel = new JPanel();
			selectOperatorOptionPanel = new JPanel();
			bottomPanel = new JPanel();
			inputBitLabel = new JLabel("输入题目位数:");
			inputBitField = new JTextField("1");// 默认一位
			inputNumLabel = new JLabel("输入题目个数:");
			inputNumField = new JTextField("10");// 默认产生10题
			operatorGroup = new ButtonGroup();
			addButton = new JRadioButton("加法");
			minusButton = new JRadioButton("减法");
			multiplyButton = new JRadioButton("乘法");
			divideButton = new JRadioButton("除法");
			mixButton = new JRadioButton("混合");
			errorInfo = new JLabel();
			startButton = new JButton("开始做题！");
			inputTimeLabel = new JLabel("输入做题时间:");
			inputTimePanel = new JPanel();
			inputByminutes = new JTextField();
			printMinute = new JLabel("分");
			inputByseconds = new JTextField();
			printSecond = new JLabel("秒");
		}

		// 设置做题时间面板
		public void setInputTimePanel() {
			inputTimePanel.setLayout(new GridLayout(1, 0));
			printMinute.setHorizontalAlignment(SwingConstants.CENTER);
			printSecond.setHorizontalAlignment(SwingConstants.CENTER);
			inputTimePanel.add(inputByminutes);
			inputTimePanel.add(printMinute);
			inputTimePanel.add(inputByseconds);
			inputTimePanel.add(printSecond);
		}

		// 设置输入选项面板
		public void setInputOptionPanel() {
			inputOptionPanel.setLayout(new GridLayout(3, 2));
			inputBitLabel.setHorizontalAlignment(SwingConstants.CENTER);
			inputNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
			inputTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			inputOptionPanel.add(inputBitLabel);
			inputOptionPanel.add(inputBitField);
			inputOptionPanel.add(inputNumLabel);
			inputOptionPanel.add(inputNumField);
			inputOptionPanel.add(inputTimeLabel);
			setInputTimePanel();
			inputOptionPanel.add(inputTimePanel);
		}

		// 设置选择运算符面板
		public void setSelectOperatorOptionPanel() {
			// selectOperatorOptionPanel.setLayout(new GridLayout(1,0));
			selectOperatorOptionPanel.setLayout(new BoxLayout(
					selectOperatorOptionPanel, BoxLayout.X_AXIS));

			operatorGroup.add(addButton);
			operatorGroup.add(minusButton);
			operatorGroup.add(multiplyButton);
			operatorGroup.add(divideButton);
			operatorGroup.add(mixButton);
			selectOperatorOptionPanel.add(addButton);
			selectOperatorOptionPanel.add(minusButton);
			selectOperatorOptionPanel.add(multiplyButton);
			selectOperatorOptionPanel.add(divideButton);
			selectOperatorOptionPanel.add(mixButton);
		}

		public void setBottomPanel() {
			bottomPanel.setLayout(new GridLayout(0, 1));
			bottomPanel.add(errorInfo);
			bottomPanel.add(startButton);
		}

		// 添加开始按钮处理事件
		public void addStartEvent() {
			startButton.addActionListener(new StartButtonListener());
		}

		// 内部类，实现开始事件
		class StartButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ar.v.clear();// 清空向量
				char operator = '+';// 默认加法
				if (minusButton.isSelected()) {
					operator = '-';
				} else if (multiplyButton.isSelected()) {
					operator = '*';
				} else if (divideButton.isSelected()) {
					operator = '/';
				} else if (mixButton.isSelected())// 混合运算
				{
					operator = '.';
				}
				try {
					int numOfEquation = Integer.parseInt(inputNumField
							.getText());
					int bigOfEquation = Integer.parseInt(inputBitField
							.getText());
					int minSeted = Integer.parseInt(inputByminutes.getText());
					int secSeted = Integer.parseInt(inputByseconds.getText());
					long millisSeted = (minSeted * 60 + secSeted) * 1000;
					timer = new TimeThread(millisSeted, timeUse);
					timer.start();
					if (numOfEquation > 100)
						throw new NumberTooBigException("这么多题，你做的完吗？");
					rp.getTotalPage(numOfEquation);// 获得总页数
					rp.setPageLabel();
					if (bigOfEquation > 9)
						throw new NumberTooBigException("输入的位数太高了");
					else if (bigOfEquation == 0)
						throw new NumberTooBigException("位数不能为0");
					while (ar.v.size() < numOfEquation) {
						ar.produceExpression(bigOfEquation, operator);
					}
					OverAllFrame.this.v = ar.v;
					rp.writeEquationToLabel();

					OverAllFrame.this.st.setVisible(false);// 开始面板调为不可见
					OverAllFrame.this.add(rp, BorderLayout.CENTER);
					OverAllFrame.this.setVisible(true);// 更新窗口
					OverAllFrame.this.rp.setVisible(true);// 显示做题面板
				} catch (NumberFormatException ex) {
					errorInfo.setText("输入数据类型错误！你必须输入数值数据!,请重新输入：");
				} catch (NumberTooBigException ex) {
					errorInfo.setText(ex.getMessage());
				} catch (Exception ex) {

				}

			}
		}
	}

	// 做题中的面板
	class RunningPanel extends JPanel {
		JPanel contentPanel;// 存放题目和答案①
		JPanel buttonsPanel;// 存放按钮②
		JLabel pageLabel; // 显示当前页/所有页
		Vector<JLabel> expressionGroup;// 显示题目①-1
		Vector<JTextField> answerGroup;// 存放答案①-2

		JButton backButton;// 上一页②-1
		JButton nextButton;// 下一页②-2
		JButton completeButton; // 完成②-3

		int totalPage = 1; // 总页数
		int currentPage = 1;// 当前页

		public RunningPanel() {
			this.setLayout(new BorderLayout());
			this.initialize();
			this.addItem();
			this.setContentPanel();
			this.setButtonsPanel();
		}

		public void initialize()// 初始化
		{
			contentPanel = new JPanel();
			buttonsPanel = new JPanel();
			pageLabel = new JLabel();
			expressionGroup = new Vector<JLabel>();
			answerGroup = new Vector<JTextField>();
			for (int i = 0; i < 10; i++) {
				expressionGroup.add(new JLabel());
				answerGroup.add(new JTextField());
			}
			backButton = new JButton("上一页");
			nextButton = new JButton("下一页");
			completeButton = new JButton("完成!");
			backButton.addActionListener(new BackButtonListener());
			nextButton.addActionListener(new NextButtonListener());
			completeButton.addActionListener(new CompleteButtonListener());

		}

		public void setContentPanel() {
			contentPanel.setLayout(new GridLayout(6, 2));
			for (int i = 0; i < 10; i++) {
				contentPanel.add(expressionGroup.get(i));
				contentPanel.add(answerGroup.get(i));
			}
		}

		public void setPageLabel() {
			pageLabel.setText("页码" + currentPage + '/' + totalPage);
		}

		public void setButtonsPanel() {
			buttonsPanel.add(backButton);
			buttonsPanel.add(nextButton);
			buttonsPanel.add(completeButton);
		}

		public void writeEquationToLabel()// 写入算式
		{
			// 因为只有十个标签，n道题目，不能一一对应，下面是标签要加上的数和题目号对应
			int numNeededToAdd = (currentPage - 1) * 10;
			for (int i = 0; i < 10; i++) {
				if (i + numNeededToAdd >= v.size())// 没有更多题目了
				{
					expressionGroup.get(i)
							.setText(String.format("%17s", "N/A"));
				} else {
					String temp = String.format("%5d%5s%5d%5s",
							v.get(i + numNeededToAdd).a,
							v.get(i + numNeededToAdd).operator,
							v.get(i + numNeededToAdd).b, "=");
					expressionGroup.get(i).setText(temp);
				}
			}
		}

		public void addItem()// 添加控件
		{
			this.add(pageLabel, BorderLayout.NORTH);
			this.add(contentPanel, BorderLayout.CENTER);
			this.add(buttonsPanel, BorderLayout.SOUTH);
		}

		public void getTotalPage(int num)// 获得总页数
		{
			totalPage = num / 10 + (num % 10 == 0 ? 0 : 1);
		}

		// 设置当前页为1，重做和继续的时候用到
		public void setCurrentPageToOne() {
			currentPage = 1;
		}

		public void writeInputToVector() throws NumberFormatException// 写入答案进向量
		{
			// 因为只有十个标签，n道题目，不能一一对应，下面是标签要加上的数和题目号对应
			int numNeededToAdd = (currentPage - 1) * 10;
			for (int i = 0; i < 10 && i + numNeededToAdd < v.size(); i++) {
				try {
					if (!answerGroup.get(i).getText().equals(""))// 如果不是啥也没写的话
					{
						v.get(i + numNeededToAdd).d = Integer
								.parseInt(answerGroup.get(i).getText());
						if (v.get(i + numNeededToAdd).d == v.get(i
								+ numNeededToAdd).c)
							v.get(i + numNeededToAdd).is_right = true;
					}
					// 否则就是默认的-1

				} catch (NumberFormatException e) {
					answerGroup.get(i).setText("输入数据类型错误！请重新输入：");
					throw e;// 必须这么写才能终止。。
				} catch (Exception e) {
					answerGroup.get(i).setText("未知错误！请重新输入：");
					throw e;// 必须这么写才能终止。。
				}
			}
		}

		public void clearAnswer()// 清空答案
		{
			for (int i = 0; i < answerGroup.size(); i++) {
				answerGroup.get(i).setText("");
			}
		}

		// 写入答案进输入区，还原上下页做过的答案用
		public void writeAnswerTofield() {
			// 因为只有十个标签，n道题目，不能一一对应，下面是标签要加上的数和题目号对应
			int numNeededToAdd = (currentPage - 1) * 10;
			for (int i = 0; i < 10 && i + numNeededToAdd < v.size(); i++) {
				if (v.get(i + numNeededToAdd).d != -1)// 说明有输入过值
				{
					answerGroup.get(i).setText(
							Integer.toString(v.get(i + numNeededToAdd).d));
				}
			}
		}

		public void complete()// 完成做题，用户点击完成或者 是时间到了
		{
			try {
				// 因为第一句可能会产生异常，不过在句中已经处理，这里加上
				// try是让它停止进入下一页
				timer.setStop();// 计时器线程停止
				OverAllFrame.this.rp.writeInputToVector();// 写入答案进向量
				OverAllFrame.this.ar.writeInfo(v.size(), userName);
				OverAllFrame.this.rp.setVisible(false);// 设置做题界面不可见
				OverAllFrame.this.ep.setDisplayAnswer(); // 设置要显示的结果
				OverAllFrame.this.add(ep, BorderLayout.CENTER);
				OverAllFrame.this.setVisible(true);
				OverAllFrame.this.ep.setVisible(true);// 显示答案面板

			} catch (Exception e) {
				// do nothing
			}

		}

		class CompleteButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				complete();
			}
		}

		class BackButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {

				if (currentPage > 1) {
					writeInputToVector();// 保存上页数据
					clearAnswer();// 删除上页答案
					currentPage--;
					setPageLabel();// 更新页码
					writeEquationToLabel();// 读入下页算式
					writeAnswerTofield();// 读入下页已填写答案
					RunningPanel.this.setVisible(true);// 更新窗口
				}
			}
		}

		class NextButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				if (currentPage < totalPage) {
					writeInputToVector();
					clearAnswer();// 删除上页答案
					currentPage++;
					setPageLabel();
					writeEquationToLabel();
					writeAnswerTofield();
					RunningPanel.this.setVisible(true);
				}
			}
		}

	}

	// 显示结果面板
	class EndPanel extends JPanel {
		JScrollPane displayArea;// 显示区域
		JPanel bottomArea; // 底部区域，存放按钮
		JTextArea displayAnswer;// 显示答案和评分
		JTextArea displayRank; // 显示排名和得分

		JButton reDoButton;// 重做
		JButton continueButton;// 继续练习
		JButton viewRankButton;// 查看排名

		Font answerFont; // 字体属性
		boolean isRankViewed = false;// 是否查看过排名
		boolean alreadyWrite = false;// 已经写入排名信息

		public EndPanel() {
			this.setLayout(new BorderLayout());// 边界布局
			this.initialize();
			this.setBottomArea();
			this.addItem();

		}

		public void initialize()// 初始化
		{
			answerFont = new Font("宋体", Font.PLAIN, 15);
			displayAnswer = new JTextArea();
			displayAnswer.setFont(answerFont);// 设置字体
			displayArea = new JScrollPane(displayAnswer);
			displayRank = new JTextArea();
			displayRank.setFont(answerFont);
			bottomArea = new JPanel();
			reDoButton = new JButton("重做一遍");
			continueButton = new JButton("继续练习");
			viewRankButton = new JButton("查看排名");
			reDoButton.addActionListener(new ReDoButtonListener());
			continueButton.addActionListener(new ContinueButtonListener());
			viewRankButton.addActionListener(new viewRankButtonListener());

		}

		public void setDisplayAnswer()// 添加答案
		{
			displayAnswer.setEditable(false);// 只显示答案，不可编辑
			String temp;
			int score = 0;
			temp = (String.format("%5s%5s%5s%5s%5s%10s%5s", "", "", "", "",
					"正确答案", "你的答案", "判断")) + "\r\n";
			displayAnswer.append(temp);
			for (int i = 0; i < v.size(); i++) {
				temp = (String.format("%5d%5s%5d%5s%10d%10d%10s", v.get(i).a,
						v.get(i).operator, v.get(i).b, "=", v.get(i).c,
						v.get(i).d, (v.get(i).is_right ? "正确" : "错误")))
						+ "\r\n";
				displayAnswer.append(temp);
				if (v.get(i).is_right) {
					score += 100.0 / v.size();
				}
			}
			temp = ("你的总分：" + score + "\r\n");
			displayAnswer.append(temp);

		}

		public void setDisplayRank() {
			if (alreadyWrite)// 如果已经执行过了
				return;
			alreadyWrite = true;
			viewRankButton.setText("查看答案");
			displayRank.setEditable(false);
			FileReader file;
			BufferedReader bf;
			String tempString;// 临时用户名
			String tempIntedString;// 临时分数的String形式
			double tempInt;// 临时分数
			// 用户名和用户最高分的映射关系
			HashMap<String, Double> map = new HashMap<String, Double>();
			try {
				file = new FileReader("UserNameAndScore.data");
				if (!file.ready()) {
					displayRank.setText("无有效数据");
				}
				bf = new BufferedReader(file);

				tempString = bf.readLine();
				tempIntedString = bf.readLine();
				while (tempString != null && tempIntedString != null) {
					tempInt = Double.parseDouble(tempIntedString);
					// 确保显示的为每个人历史所得最高分
					if (!(map.containsKey(tempString) && tempInt <= map
							.get(tempString))) {
						map.put(tempString, tempInt);
					}
					tempString = bf.readLine();
					tempIntedString = bf.readLine();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				viewRankButton.setText("文件异常");
			}
			Vector<UserRankInfo> v = new Vector<UserRankInfo>();
			Iterator<String> it = map.keySet().iterator();
			UserRankInfo temp;

			while (it.hasNext()) {
				tempString = it.next();
				temp = new UserRankInfo(tempString, map.get(tempString));
				v.add(temp);
			}
			Collections.sort(v, new UserRankInfo());
			int rank = 1;
			displayRank.append(String.format("%10s%10s%10s", "排名", "用户名", "得分")
					+ "\r\n");
			for (UserRankInfo u : v) {
				displayRank.append(String.format("%10d%15s%15f", rank++,
						u.userName, u.score) + "\r\n");
			}

		}

		public void setBottomArea()// 添加底部按钮
		{
			bottomArea.setLayout(new FlowLayout());
			bottomArea.add(reDoButton);
			bottomArea.add(continueButton);
			bottomArea.add(viewRankButton);
		}

		public void addItem()// 添加组件
		{

			this.add(displayArea, BorderLayout.CENTER);
			this.add(bottomArea, BorderLayout.SOUTH);
		}

		public void recoverAnswerArea()// 恢复答案区域
		{
			viewRankButton.setText("查看排名");
			displayArea.setViewportView(displayAnswer);
			displayAnswer.setVisible(true);
			isRankViewed = false;
		}

		class ReDoButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				OverAllFrame.this.rp.setCurrentPageToOne();// 设置当前页码 为1
				OverAllFrame.this.ep.setVisible(false);// 隐藏结果面板
				OverAllFrame.this.rp.clearAnswer();// 先清除做题面板的内容
				OverAllFrame.this.rp.setVisible(true);// 显示做题面板
				if (isRankViewed)
					recoverAnswerArea();
			}
		}

		class ContinueButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				OverAllFrame.this.rp.setCurrentPageToOne();// 设置当前页码 为1
				OverAllFrame.this.rp.clearAnswer();// 先清除做题面板的内容
				OverAllFrame.this.ep.setVisible(false);// 隐藏结果面板
				OverAllFrame.this.st.setVisible(true);// 显示开始面板
				if (isRankViewed)
					recoverAnswerArea();

			}
		}

		class viewRankButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isRankViewed) {

					setDisplayRank();// 写入排名信息
					displayAnswer.setVisible(false);
					displayArea.setViewportView(displayRank);
					displayRank.setVisible(true);
					isRankViewed = true;
				} else {
					recoverAnswerArea();
				}

			}
		}

		// 实现比较接口的用户排名类
		class UserRankInfo implements Comparator<UserRankInfo> {
			public String userName;
			public double score;

			public UserRankInfo(String userName, double score) {
				this.userName = userName;
				this.score = score;
			}

			public UserRankInfo() {
				// none
			}

			@Override
			public int compare(UserRankInfo arg0, UserRankInfo arg1) {
				if (arg0.score < arg1.score)
					return 1;
				else if (arg0.score > arg1.score)
					return -1;
				return 0;
			}

		}
	}
}
