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
	JComboBox Month = new JComboBox(); // 月份下拉列表框
	JComboBox Year = new JComboBox(); // 年份下拉列表框
	JLabel Year_l = new JLabel("年份:"); // 定义标签
	JLabel Month_l = new JLabel("月份:"); // 定义标签
	Date now_date = new Date(); // 获取今天的日期
	JButton[] button_day = new JButton[49]; // 定义一个数组用来存放日期
	JButton button_jump = new JButton("日期跳转"); // 现实选择日期
	JButton button_today = new JButton("现在日期"); // 显示今天日期按钮
	int now_year = now_date.getYear() + 1900; // 获取年份值
	int now_month = now_date.getMonth(); // 获取月份值(当前月份-1)
	boolean bool = false;
	String year_int = null; // 存放年份
	int month_int; // 存放月份
	JPanel pane_ym = new JPanel(); // 放置下拉列表框和控制按钮面板
	JPanel pane_day = new JPanel(); // 放置日期面板
	JPanel pane_parent = new JPanel(); // 放置以上两个面板
	// 定义方法绘制面板

	public Calendar() {
		super("JAVA日历程序"); // 设定面板标题
		// ---以下几行使得关闭面板时退出程序
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			public void windowClose(WindowEvent e) {
				// System.out.print("CLOSING THE WIN");

				System.exit(0);
			}
		});

		setResizable(false); // 面板的大小不能变化
		// 设定年月
		/*
		 * 年份的区间是当前年份的过去10年到当前年份的未来20年 月份正常1-12月
		 */
		for (int i = now_year - 10; i <= now_year + 20; i++) {
			Year.addItem(i + "");
		}
		for (int i = 1; i < 13; i++) {
			Month.addItem(i + "");
		}
		Year.setSelectedIndex(10); // 设定年份下拉列表为当前年份
		// Year.setText()
		pane_ym.add(Year_l); // 添加年份标签
		pane_ym.add(Year); // 添加年份下拉列表框
		Month.setSelectedIndex(now_month); // 设定月份下拉列表为当前月份
		pane_ym.add(Month_l); // 添加月份标签
		pane_ym.add(Month); // 添加月份下拉列表框
		pane_ym.add(button_jump); // 添加跳转按钮
		pane_ym.add(button_today); // 添加“现在日期”按钮
		button_jump.addActionListener(this); // 跳转按钮添加 监听事件
		button_today.addActionListener(this); // “现在日期”按钮添加 监听事件
		// 年月设定结束
		// 初始化日期按钮并绘制
		pane_day.setLayout(new GridLayout(7, 7));
		for (int i = 0; i < 49; i++) {
			button_day[i] = new JButton(" ");
			pane_day.add(button_day[i]);
		}
		this.setDay(); // 调用setDay()方法
		pane_parent.setLayout(new BorderLayout()); // 设定布局管理器
		setContentPane(pane_day);
		setContentPane(pane_ym);
		pane_parent.add(pane_day, BorderLayout.SOUTH);
		pane_parent.add(pane_ym, BorderLayout.NORTH);
		setContentPane(pane_parent);
		pack();
		show();
	}

	// SET DAY 方法，显示日期
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

		int year_sel = Integer.parseInt(year_int) - 1900; // 获得年份值
		Date dt = new Date(year_sel, month_int, 1); // 构造一个日期
		GregorianCalendar cal = new GregorianCalendar(); // 创建一个Calendar实例
		cal.setTime(dt);
		String week[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		int day = 0; // day中存放某个月份的天数
		int day_week = 0; // 用来存放某个月的第一天是星期几的数值
		// --将星期添加到前7个按钮中
		for (int i = 0; i < 7; i++) {
			button_day[i].setText(week[i]);
		}
		// --
		/*
		 * 判断是几月份,根据它来设定day的值 其中二月份要判断是否是闰年
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
		 * 绘制按钮 首先要根据选定的月份的第一天是星期几来确定我们绘制按钮的起始位置 其中day_week就是我们要绘制的起始位置
		 * 对于那些没有数值可以显示的按钮要置空
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

		// --对于没有日期数值显示的按钮进行置空处理
		if (day_week == 0) {
			for (int i = day; i < 49; i++) {
				button_day[i].setText(" ");
			}
		} else {
			// 第一天前面的按钮置空
			for (int i = 7; i < day_week; i++) {
				button_day[i].setText(" ");
			} // 最后一天后面的按钮置空
			for (int i = day_week + day; i < 49; i++) {
				button_day[i].setText(" ");
			}
		}
	}

	// 点击按钮产生的效果
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button_jump) {
			bool = false;
			this.setDay(); // 如果点击跳转按钮就调用setDay()方法重新绘制按钮

		} else if (e.getSource() == button_today) {
			bool = true;
			this.setDay(); // 如果点击现在日期按钮，得到今天的日期
			Month.setSelectedIndex(now_month);// 将月份置为当前月份
			Year.setSelectedIndex(10); // 将年份置为当前年份

		}
	}

	public static void main(String[] args) {
		Calendar ct = new Calendar();
	}
}