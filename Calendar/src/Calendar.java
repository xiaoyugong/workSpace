import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Tyler
 *
 */
public class Calendar extends JFrame implements ActionListener {
	JComboBox Month = new JComboBox(); // �·������б��
	JComboBox Year = new JComboBox(); // ��������б��
	JLabel Year_l = new JLabel("���:"); // �����ǩ
	JLabel Month_l = new JLabel("�·�:"); // �����ǩ
	Date now_date = new Date(); // ��ȡ���������
	JButton[] button_day = new JButton[49]; // ����һ�����������������
	JButton button_jump = new JButton("������ת"); // ��ʵѡ������
	JButton button_today = new JButton("��������"); // ��ʾ�������ڰ�ť
	int now_year = now_date.getYear() + 1900; // ��ȡ���ֵ
	int now_month = now_date.getMonth(); // ��ȡ�·�ֵ(��ǰ�·�-1)
	boolean bool = false;
	String year_int = null; // ������
	int month_int; // ����·�
	JPanel pane_ym = new JPanel(); // ���������б��Ϳ��ư�ť���
	JPanel pane_day = new JPanel(); // �����������
	JPanel pane_parent = new JPanel(); // ���������������
	// ���巽���������

	public Calendar() {
		super("JAVA��������"); // �趨������
		// ---���¼���ʹ�ùر����ʱ�˳�����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			public void windowClose(WindowEvent e) {
				// System.out.print("CLOSING THE WIN");

				System.exit(0);
			}
		});

		setResizable(false); // ���Ĵ�С���ܱ仯
		// �趨����
		/*
		 * ��ݵ������ǵ�ǰ��ݵĹ�ȥ10�굽��ǰ��ݵ�δ��20�� �·�����1-12��
		 */
		for (int i = now_year - 10; i <= now_year + 20; i++) {
			Year.addItem(i + "");
		}
		for (int i = 1; i < 13; i++) {
			Month.addItem(i + "");
		}
		Year.setSelectedIndex(10); // �趨��������б�Ϊ��ǰ���
		// Year.setText()
		pane_ym.add(Year_l); // �����ݱ�ǩ
		pane_ym.add(Year); // �����������б��
		Month.setSelectedIndex(now_month); // �趨�·������б�Ϊ��ǰ�·�
		pane_ym.add(Month_l); // ����·ݱ�ǩ
		pane_ym.add(Month); // ����·������б��
		pane_ym.add(button_jump); // �����ת��ť
		pane_ym.add(button_today); // ��ӡ��������ڡ���ť
		button_jump.addActionListener(this); // ��ת��ť��� �����¼�
		button_today.addActionListener(this); // ���������ڡ���ť��� �����¼�
		// �����趨����
		// ��ʼ�����ڰ�ť������
		pane_day.setLayout(new GridLayout(7, 7));
		for (int i = 0; i < 49; i++) {
			button_day[i] = new JButton(" ");
			pane_day.add(button_day[i]);
		}
		this.setDay(); // ����setDay()����
		pane_parent.setLayout(new BorderLayout()); // �趨���ֹ�����
		setContentPane(pane_day);
		setContentPane(pane_ym);
		pane_parent.add(pane_day, BorderLayout.SOUTH);
		pane_parent.add(pane_ym, BorderLayout.NORTH);
		setContentPane(pane_parent);
		pack();
		show();
	}

	// SET DAY ��������ʾ����
	void setDay() {
		if (bool) {
			year_int = now_year + "";
			month_int = now_month;
		} else {
			year_int = Year.getSelectedItem().toString();
			month_int = Month.getSelectedIndex();
			// year_int=Year.getText();
			// month_int=Integer.parseInt(Month.getText());
		}

		int year_sel = Integer.parseInt(year_int) - 1900; // ������ֵ
		Date dt = new Date(year_sel, month_int, 1); // ����һ������
		GregorianCalendar cal = new GregorianCalendar(); // ����һ��Calendarʵ��
		cal.setTime(dt);
		String week[] = { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
		int day = 0; // day�д��ĳ���·ݵ�����
		int day_week = 0; // �������ĳ���µĵ�һ�������ڼ�����ֵ
		// --��������ӵ�ǰ7����ť��
		for (int i = 0; i < 7; i++) {
			button_day[i].setText(week[i]);
		}
		// --
		/*
		 * �ж��Ǽ��·�,���������趨day��ֵ ���ж��·�Ҫ�ж��Ƿ�������
		 */
		if (month_int == 0 || month_int == 2 || month_int == 4 || month_int == 6 || month_int == 7 || month_int == 9
				|| month_int == 11) {
			day = 31;
		} else if (month_int == 3 || month_int == 5 || month_int == 8 || month_int == 10) {
			day = 30;
		} else {
			if (cal.isLeapYear(year_sel)) {
				day = 29;
			} else {
				day = 28;
			}
		}
		day_week = 7 + dt.getDay();
		int count = 1;
		/*
		 * ���ư�ť ����Ҫ����ѡ�����·ݵĵ�һ�������ڼ���ȷ�����ǻ��ư�ť����ʼλ�� ����day_week��������Ҫ���Ƶ���ʼλ��
		 * ������Щû����ֵ������ʾ�İ�ťҪ�ÿ�
		 */
		for (int i = day_week; i < day_week + day; count++, i++) {
			if (i % 7 == 0 || i == 13 || i == 20 || i == 27 || i == 48 || i == 34 || i == 41) {
				if (i == day_week + now_date.getDate() - 1) {
					button_day[i].setForeground(Color.blue);
					button_day[i].setText(count + "");
				} else {
					button_day[i].setForeground(Color.red);
					button_day[i].setText(count + "");
				}

			} else {
				if (i == day_week + now_date.getDate() - 1) {
					button_day[i].setForeground(Color.blue);
					button_day[i].setText(count + "");
				} else {
					button_day[i].setForeground(Color.black);
					button_day[i].setText(count + "");
				}
			}
		}

		// --����û��������ֵ��ʾ�İ�ť�����ÿմ���
		if (day_week == 0) {
			for (int i = day; i < 49; i++) {
				button_day[i].setText(" ");
			}
		} else {
			// ��һ��ǰ��İ�ť�ÿ�
			for (int i = 7; i < day_week; i++) {
				button_day[i].setText(" ");
			} // ���һ�����İ�ť�ÿ�
			for (int i = day_week + day; i < 49; i++) {
				button_day[i].setText(" ");
			}
		}
	}

	// �����ť������Ч��
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button_jump) {
			bool = false;
			this.setDay(); // ��������ת��ť�͵���setDay()�������»��ư�ť

		} else if (e.getSource() == button_today) {
			bool = true;
			this.setDay(); // �������������ڰ�ť���õ����������
			Month.setSelectedIndex(now_month);// ���·���Ϊ��ǰ�·�
			Year.setSelectedIndex(10); // �������Ϊ��ǰ���

		}
	}

	public static void main(String[] args) {
		Calendar ct = new Calendar();
	}
}