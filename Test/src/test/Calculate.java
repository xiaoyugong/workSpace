package test;

//-------------------------------------------------
//Сѧ�������������V1.3
//Author:hufeiya ZJUT  2013��12��7��21:52:50
//������ý���ʵ������������Ļ������ܣ���ѡ����Ŀ������
//λ�����������ͣ����з�ҳ����ʾҳ�롢�������ݵȹ��ܡ�
//V1.1��
//�޸�ʱ�䣺2013-12-8 15:12:58
//�޸����ݣ� �����˴������BUG,��������Ϊ0����ҳ���⡢
//����Ĭ�ϡ��쳣�����ȡ�
//V1.2
//�޸�ʱ�䣺2013-12-21 21:38:31
//�޸����ݣ����Ӽ�ʱ���̣߳�����ʹ�û��Զ�������ʱ��
//V1.3
//�޸�ʱ�䣺2013-12-27 12:54:48
//�޸����ݣ�����������Ϣ���ڲ���hashmapʵ��
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

class Expression// ���ʽ�࣬��ű��ʽ
{
	int a;// ��һ����
	int b;// �ڶ�����
	int c;// ��ȷ��
	int d;// �����
	char operator;// �����
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
			temp.b = (temp.b == 0 ? // �ų�����������Ϊ0�����
			temp.b = 1 + (int) (Math.random() * Math.pow(10, bit) - 1)
					: temp.b);
			temp.c = temp.a / temp.b;
			break;
		default:
			mixExpression(temp, bit);// �������
		}

		v.add(temp);
	}

	// ������㣬���������
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
			temp.b = (temp.b == 0 ? // �ų�����������Ϊ0�����
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
					"��ȷ��", "��Ĵ�", "�ж�"));
			ps.println();
			for (int i = 0; i < num; i++) {

				ps.println(String.format("%5d%5s%5d%5s%10d%10d%10s",
						v.get(i).a, "+", v.get(i).b, "=", v.get(i).c,
						v.get(i).d, (v.get(i).is_right ? "��ȷ" : "����")));
				ps.println();
				if (v.get(i).is_right) {
					score += 100.0 / num;
				}
			}
			ps.println("����ܷ֣�" + score);
			ps.close();
			fos.close();
			// ���汣���û����͵÷ֵ�UserNameAndScore.data�ļ�;
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
		OverAllFrame oaf = new OverAllFrame("Сѧ�������������V1.3 By hufeiya ZJUT");
	}
}

// ������
class OverAllFrame extends JFrame {
	TimeThread timer; // ��ʱ���߳�
	Calculate ar;// �ڲ��㷨
	JPanel headPanel;// ͷ���
	JLabel welcomeLabel;// ��ӭ��Ϣ
	JLabel timeUse; // ��ʱ��Ϣ
	StartPanel st;
	String userName;
	RunningPanel rp;
	EndPanel ep;
	Vector<Expression> v;// �����ʽ

	public OverAllFrame(String s) {
		super(s);
		userName = JOptionPane.showInputDialog("�������û���", "");
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

	public void initialize()// ��ʼ���ؼ�
	{
		ar = new Calculate();
		headPanel = new JPanel();
		welcomeLabel = new JLabel("��ӭ!  �û���" + userName);
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

	// ��ʼ���
	class TimeThread extends Thread // ��ʱ���߳�
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
				display.setText("����ʱ:" + min + ":" + sec + ":" + millis);
				display.setVisible(true);

			} while (timeUsed <= timeSeted && !stop);
			if (!stop)// ��������ֶ�ֹͣ�ľ�����complete
				rp.complete();
		}

		public void setStop() {
			stop = true;
		}
	}

	class StartPanel extends JPanel {
		JPanel inputOptionPanel; // ����ѡ������
		JPanel selectOperatorOptionPanel;// ѡ�����������
		JPanel bottomPanel; // �ײ�����

		JLabel inputBitLabel; // ����λ����ʾ��-1
		JTextField inputBitField; // ����λ�������-2
		JLabel inputNumLabel; // ������Ŀ������ʾ��-3
		JTextField inputNumField; // ������Ŀ���������-4
		JLabel inputTimeLabel; // ��������ʱ����ʾ��-5
		JPanel inputTimePanel; // ��������ʱ�������-6

		ButtonGroup operatorGroup;
		JRadioButton addButton; // �ӷ���ť��-1
		JRadioButton minusButton; // ������ť��-2
		JRadioButton multiplyButton; // �˷���ť��-3
		JRadioButton divideButton; // ������ť��-4
		JRadioButton mixButton; // ������㰴ť��-5

		JLabel errorInfo; // ������Ϣ��-1
		JButton startButton; // ��ʼ��ť��-2

		JTextField inputByminutes; // ������������-6-1
		JLabel printMinute; // ��ӡ��ʾ���֡���-6-2
		JTextField inputByseconds; // �������������-6-3
		JLabel printSecond; // ��ӡ��ʾ���롱��-6-4

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

		public void initialize() // ��ʼ�����пؼ�
		{
			inputOptionPanel = new JPanel();
			selectOperatorOptionPanel = new JPanel();
			bottomPanel = new JPanel();
			inputBitLabel = new JLabel("������Ŀλ��:");
			inputBitField = new JTextField("1");// Ĭ��һλ
			inputNumLabel = new JLabel("������Ŀ����:");
			inputNumField = new JTextField("10");// Ĭ�ϲ���10��
			operatorGroup = new ButtonGroup();
			addButton = new JRadioButton("�ӷ�");
			minusButton = new JRadioButton("����");
			multiplyButton = new JRadioButton("�˷�");
			divideButton = new JRadioButton("����");
			mixButton = new JRadioButton("���");
			errorInfo = new JLabel();
			startButton = new JButton("��ʼ���⣡");
			inputTimeLabel = new JLabel("��������ʱ��:");
			inputTimePanel = new JPanel();
			inputByminutes = new JTextField();
			printMinute = new JLabel("��");
			inputByseconds = new JTextField();
			printSecond = new JLabel("��");
		}

		// ��������ʱ�����
		public void setInputTimePanel() {
			inputTimePanel.setLayout(new GridLayout(1, 0));
			printMinute.setHorizontalAlignment(SwingConstants.CENTER);
			printSecond.setHorizontalAlignment(SwingConstants.CENTER);
			inputTimePanel.add(inputByminutes);
			inputTimePanel.add(printMinute);
			inputTimePanel.add(inputByseconds);
			inputTimePanel.add(printSecond);
		}

		// ��������ѡ�����
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

		// ����ѡ����������
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

		// ��ӿ�ʼ��ť�����¼�
		public void addStartEvent() {
			startButton.addActionListener(new StartButtonListener());
		}

		// �ڲ��࣬ʵ�ֿ�ʼ�¼�
		class StartButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				ar.v.clear();// �������
				char operator = '+';// Ĭ�ϼӷ�
				if (minusButton.isSelected()) {
					operator = '-';
				} else if (multiplyButton.isSelected()) {
					operator = '*';
				} else if (divideButton.isSelected()) {
					operator = '/';
				} else if (mixButton.isSelected())// �������
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
						throw new NumberTooBigException("��ô���⣬����������");
					rp.getTotalPage(numOfEquation);// �����ҳ��
					rp.setPageLabel();
					if (bigOfEquation > 9)
						throw new NumberTooBigException("�����λ��̫����");
					else if (bigOfEquation == 0)
						throw new NumberTooBigException("λ������Ϊ0");
					while (ar.v.size() < numOfEquation) {
						ar.produceExpression(bigOfEquation, operator);
					}
					OverAllFrame.this.v = ar.v;
					rp.writeEquationToLabel();

					OverAllFrame.this.st.setVisible(false);// ��ʼ����Ϊ���ɼ�
					OverAllFrame.this.add(rp, BorderLayout.CENTER);
					OverAllFrame.this.setVisible(true);// ���´���
					OverAllFrame.this.rp.setVisible(true);// ��ʾ�������
				} catch (NumberFormatException ex) {
					errorInfo.setText("�����������ʹ��������������ֵ����!,���������룺");
				} catch (NumberTooBigException ex) {
					errorInfo.setText(ex.getMessage());
				} catch (Exception ex) {

				}

			}
		}
	}

	// �����е����
	class RunningPanel extends JPanel {
		JPanel contentPanel;// �����Ŀ�ʹ𰸢�
		JPanel buttonsPanel;// ��Ű�ť��
		JLabel pageLabel; // ��ʾ��ǰҳ/����ҳ
		Vector<JLabel> expressionGroup;// ��ʾ��Ŀ��-1
		Vector<JTextField> answerGroup;// ��Ŵ𰸢�-2

		JButton backButton;// ��һҳ��-1
		JButton nextButton;// ��һҳ��-2
		JButton completeButton; // ��ɢ�-3

		int totalPage = 1; // ��ҳ��
		int currentPage = 1;// ��ǰҳ

		public RunningPanel() {
			this.setLayout(new BorderLayout());
			this.initialize();
			this.addItem();
			this.setContentPanel();
			this.setButtonsPanel();
		}

		public void initialize()// ��ʼ��
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
			backButton = new JButton("��һҳ");
			nextButton = new JButton("��һҳ");
			completeButton = new JButton("���!");
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
			pageLabel.setText("ҳ��" + currentPage + '/' + totalPage);
		}

		public void setButtonsPanel() {
			buttonsPanel.add(backButton);
			buttonsPanel.add(nextButton);
			buttonsPanel.add(completeButton);
		}

		public void writeEquationToLabel()// д����ʽ
		{
			// ��Ϊֻ��ʮ����ǩ��n����Ŀ������һһ��Ӧ�������Ǳ�ǩҪ���ϵ�������Ŀ�Ŷ�Ӧ
			int numNeededToAdd = (currentPage - 1) * 10;
			for (int i = 0; i < 10; i++) {
				if (i + numNeededToAdd >= v.size())// û�и�����Ŀ��
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

		public void addItem()// ��ӿؼ�
		{
			this.add(pageLabel, BorderLayout.NORTH);
			this.add(contentPanel, BorderLayout.CENTER);
			this.add(buttonsPanel, BorderLayout.SOUTH);
		}

		public void getTotalPage(int num)// �����ҳ��
		{
			totalPage = num / 10 + (num % 10 == 0 ? 0 : 1);
		}

		// ���õ�ǰҳΪ1�������ͼ�����ʱ���õ�
		public void setCurrentPageToOne() {
			currentPage = 1;
		}

		public void writeInputToVector() throws NumberFormatException// д��𰸽�����
		{
			// ��Ϊֻ��ʮ����ǩ��n����Ŀ������һһ��Ӧ�������Ǳ�ǩҪ���ϵ�������Ŀ�Ŷ�Ӧ
			int numNeededToAdd = (currentPage - 1) * 10;
			for (int i = 0; i < 10 && i + numNeededToAdd < v.size(); i++) {
				try {
					if (!answerGroup.get(i).getText().equals(""))// �������ɶҲûд�Ļ�
					{
						v.get(i + numNeededToAdd).d = Integer
								.parseInt(answerGroup.get(i).getText());
						if (v.get(i + numNeededToAdd).d == v.get(i
								+ numNeededToAdd).c)
							v.get(i + numNeededToAdd).is_right = true;
					}
					// �������Ĭ�ϵ�-1

				} catch (NumberFormatException e) {
					answerGroup.get(i).setText("�����������ʹ������������룺");
					throw e;// ������ôд������ֹ����
				} catch (Exception e) {
					answerGroup.get(i).setText("δ֪�������������룺");
					throw e;// ������ôд������ֹ����
				}
			}
		}

		public void clearAnswer()// ��մ�
		{
			for (int i = 0; i < answerGroup.size(); i++) {
				answerGroup.get(i).setText("");
			}
		}

		// д��𰸽�����������ԭ����ҳ�����Ĵ���
		public void writeAnswerTofield() {
			// ��Ϊֻ��ʮ����ǩ��n����Ŀ������һһ��Ӧ�������Ǳ�ǩҪ���ϵ�������Ŀ�Ŷ�Ӧ
			int numNeededToAdd = (currentPage - 1) * 10;
			for (int i = 0; i < 10 && i + numNeededToAdd < v.size(); i++) {
				if (v.get(i + numNeededToAdd).d != -1)// ˵���������ֵ
				{
					answerGroup.get(i).setText(
							Integer.toString(v.get(i + numNeededToAdd).d));
				}
			}
		}

		public void complete()// ������⣬�û������ɻ��� ��ʱ�䵽��
		{
			try {
				// ��Ϊ��һ����ܻ�����쳣�������ھ����Ѿ������������
				// try������ֹͣ������һҳ
				timer.setStop();// ��ʱ���߳�ֹͣ
				OverAllFrame.this.rp.writeInputToVector();// д��𰸽�����
				OverAllFrame.this.ar.writeInfo(v.size(), userName);
				OverAllFrame.this.rp.setVisible(false);// ����������治�ɼ�
				OverAllFrame.this.ep.setDisplayAnswer(); // ����Ҫ��ʾ�Ľ��
				OverAllFrame.this.add(ep, BorderLayout.CENTER);
				OverAllFrame.this.setVisible(true);
				OverAllFrame.this.ep.setVisible(true);// ��ʾ�����

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
					writeInputToVector();// ������ҳ����
					clearAnswer();// ɾ����ҳ��
					currentPage--;
					setPageLabel();// ����ҳ��
					writeEquationToLabel();// ������ҳ��ʽ
					writeAnswerTofield();// ������ҳ����д��
					RunningPanel.this.setVisible(true);// ���´���
				}
			}
		}

		class NextButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				if (currentPage < totalPage) {
					writeInputToVector();
					clearAnswer();// ɾ����ҳ��
					currentPage++;
					setPageLabel();
					writeEquationToLabel();
					writeAnswerTofield();
					RunningPanel.this.setVisible(true);
				}
			}
		}

	}

	// ��ʾ������
	class EndPanel extends JPanel {
		JScrollPane displayArea;// ��ʾ����
		JPanel bottomArea; // �ײ����򣬴�Ű�ť
		JTextArea displayAnswer;// ��ʾ�𰸺�����
		JTextArea displayRank; // ��ʾ�����͵÷�

		JButton reDoButton;// ����
		JButton continueButton;// ������ϰ
		JButton viewRankButton;// �鿴����

		Font answerFont; // ��������
		boolean isRankViewed = false;// �Ƿ�鿴������
		boolean alreadyWrite = false;// �Ѿ�д��������Ϣ

		public EndPanel() {
			this.setLayout(new BorderLayout());// �߽粼��
			this.initialize();
			this.setBottomArea();
			this.addItem();

		}

		public void initialize()// ��ʼ��
		{
			answerFont = new Font("����", Font.PLAIN, 15);
			displayAnswer = new JTextArea();
			displayAnswer.setFont(answerFont);// ��������
			displayArea = new JScrollPane(displayAnswer);
			displayRank = new JTextArea();
			displayRank.setFont(answerFont);
			bottomArea = new JPanel();
			reDoButton = new JButton("����һ��");
			continueButton = new JButton("������ϰ");
			viewRankButton = new JButton("�鿴����");
			reDoButton.addActionListener(new ReDoButtonListener());
			continueButton.addActionListener(new ContinueButtonListener());
			viewRankButton.addActionListener(new viewRankButtonListener());

		}

		public void setDisplayAnswer()// ��Ӵ�
		{
			displayAnswer.setEditable(false);// ֻ��ʾ�𰸣����ɱ༭
			String temp;
			int score = 0;
			temp = (String.format("%5s%5s%5s%5s%5s%10s%5s", "", "", "", "",
					"��ȷ��", "��Ĵ�", "�ж�")) + "\r\n";
			displayAnswer.append(temp);
			for (int i = 0; i < v.size(); i++) {
				temp = (String.format("%5d%5s%5d%5s%10d%10d%10s", v.get(i).a,
						v.get(i).operator, v.get(i).b, "=", v.get(i).c,
						v.get(i).d, (v.get(i).is_right ? "��ȷ" : "����")))
						+ "\r\n";
				displayAnswer.append(temp);
				if (v.get(i).is_right) {
					score += 100.0 / v.size();
				}
			}
			temp = ("����ܷ֣�" + score + "\r\n");
			displayAnswer.append(temp);

		}

		public void setDisplayRank() {
			if (alreadyWrite)// ����Ѿ�ִ�й���
				return;
			alreadyWrite = true;
			viewRankButton.setText("�鿴��");
			displayRank.setEditable(false);
			FileReader file;
			BufferedReader bf;
			String tempString;// ��ʱ�û���
			String tempIntedString;// ��ʱ������String��ʽ
			double tempInt;// ��ʱ����
			// �û������û���߷ֵ�ӳ���ϵ
			HashMap<String, Double> map = new HashMap<String, Double>();
			try {
				file = new FileReader("UserNameAndScore.data");
				if (!file.ready()) {
					displayRank.setText("����Ч����");
				}
				bf = new BufferedReader(file);

				tempString = bf.readLine();
				tempIntedString = bf.readLine();
				while (tempString != null && tempIntedString != null) {
					tempInt = Double.parseDouble(tempIntedString);
					// ȷ����ʾ��Ϊÿ������ʷ������߷�
					if (!(map.containsKey(tempString) && tempInt <= map
							.get(tempString))) {
						map.put(tempString, tempInt);
					}
					tempString = bf.readLine();
					tempIntedString = bf.readLine();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				viewRankButton.setText("�ļ��쳣");
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
			displayRank.append(String.format("%10s%10s%10s", "����", "�û���", "�÷�")
					+ "\r\n");
			for (UserRankInfo u : v) {
				displayRank.append(String.format("%10d%15s%15f", rank++,
						u.userName, u.score) + "\r\n");
			}

		}

		public void setBottomArea()// ��ӵײ���ť
		{
			bottomArea.setLayout(new FlowLayout());
			bottomArea.add(reDoButton);
			bottomArea.add(continueButton);
			bottomArea.add(viewRankButton);
		}

		public void addItem()// ������
		{

			this.add(displayArea, BorderLayout.CENTER);
			this.add(bottomArea, BorderLayout.SOUTH);
		}

		public void recoverAnswerArea()// �ָ�������
		{
			viewRankButton.setText("�鿴����");
			displayArea.setViewportView(displayAnswer);
			displayAnswer.setVisible(true);
			isRankViewed = false;
		}

		class ReDoButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				OverAllFrame.this.rp.setCurrentPageToOne();// ���õ�ǰҳ�� Ϊ1
				OverAllFrame.this.ep.setVisible(false);// ���ؽ�����
				OverAllFrame.this.rp.clearAnswer();// �����������������
				OverAllFrame.this.rp.setVisible(true);// ��ʾ�������
				if (isRankViewed)
					recoverAnswerArea();
			}
		}

		class ContinueButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				OverAllFrame.this.rp.setCurrentPageToOne();// ���õ�ǰҳ�� Ϊ1
				OverAllFrame.this.rp.clearAnswer();// �����������������
				OverAllFrame.this.ep.setVisible(false);// ���ؽ�����
				OverAllFrame.this.st.setVisible(true);// ��ʾ��ʼ���
				if (isRankViewed)
					recoverAnswerArea();

			}
		}

		class viewRankButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isRankViewed) {

					setDisplayRank();// д��������Ϣ
					displayAnswer.setVisible(false);
					displayArea.setViewportView(displayRank);
					displayRank.setVisible(true);
					isRankViewed = true;
				} else {
					recoverAnswerArea();
				}

			}
		}

		// ʵ�ֱȽϽӿڵ��û�������
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
