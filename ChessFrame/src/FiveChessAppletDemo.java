import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

class ChessFrame extends JFrame implements ActionListener {
	public static boolean iscomputer = true, checkcomputer = true;
	private ChessModel cm;
	private MainPanel mp;

	public ChessFrame() {
		this.setTitle("五子棋游戏");
		cm = new ChessModel(1);
		mp = new MainPanel(cm);
		Container con = this.getContentPane();
		con.add(mp, "Center");
		this.setResizable(false);
		this.addWindowListener(new ChessWindowEvent());
		MapSize(14, 14);
		JMenuBar mbar = new JMenuBar();
		this.setJMenuBar(mbar);
		JMenu gameMenu = new JMenu("游戏");
		mbar.add(makeMenu(gameMenu, new Object[] { "再来一局", "退出" }, this));
	}

	public JMenu makeMenu(Object parent, Object items[], Object target) {
		JMenu m = null;
		if (parent instanceof JMenu)
			m = (JMenu) parent;
		else if (parent instanceof String)
			m = new JMenu((String) parent);
		else
			return null;
		for (int i = 0; i < items.length; i++)
			if (items[i] == null)
				m.addSeparator();
			else
				m.add(makeMenuItem(items[i], target));
		return m;
	}

	public JMenuItem makeMenuItem(Object item, Object target) {
		JMenuItem r = null;
		if (item instanceof String)
			r = new JMenuItem((String) item);
		else if (item instanceof JMenuItem)
			r = (JMenuItem) item;
		else
			return null;
		if (target instanceof ActionListener)
			r.addActionListener((ActionListener) target);
		return r;
	}

	public JRadioButtonMenuItem makeRadioButtonMenuItem(Object item, Object target) {
		JRadioButtonMenuItem r = null;
		if (item instanceof String)
			r = new JRadioButtonMenuItem((String) item);
		else if (item instanceof JRadioButtonMenuItem)
			r = (JRadioButtonMenuItem) item;
		else
			return null;
		if (target instanceof ActionListener)
			r.addActionListener((ActionListener) target);
		return r;
	}

	public void MapSize(int w, int h) {
		setSize(w * 24, h * 27);
		if (this.checkcomputer)
			this.iscomputer = true;
		else
			this.iscomputer = false;
		mp.setModel(cm);
		mp.repaint();
	}

	public boolean getiscomputer() {
		return this.iscomputer;
	}

	public void restart() {
		int modeChess = cm.getModeChess();
		if (modeChess <= 3 && modeChess >= 0) {
			cm = new ChessModel(modeChess);
			MapSize(cm.getWidth(), cm.getHeight());
		}
	}

	public void actionPerformed(ActionEvent e) {
		String arg = e.getActionCommand();
		if (arg.equals("再来一局")) {
			restart();
		}
		if (arg.equals("退出"))
			System.exit(0);
	}
}

class ChessModel {
	private int width, height, modeChess;
	private int x = 0, y = 0;
	private int[][] arrMapShow;
	private boolean isOdd, isExist;

	public ChessModel() {
	}

	public ChessModel(int modeChess) {
		this.isOdd = true;
		if (modeChess == 1) {
			PanelInit(14, 14, modeChess);
		}
		if (modeChess == 2) {
			PanelInit(18, 18, modeChess);
		}
		if (modeChess == 3) {
			PanelInit(22, 22, modeChess);
		}
	}

	private void PanelInit(int width, int height, int modeChess) {
		this.width = width;
		this.height = height;
		this.modeChess = modeChess;
		arrMapShow = new int[width + 1][height + 1];
		for (int i = 0; i <= width; i++)
			for (int j = 0; j <= height; j++)
				arrMapShow[i][j] = -1;
	}

	public boolean getisOdd() {
		return this.isOdd;
	}

	public void setisOdd(boolean isodd) {
		if (isodd)
			this.isOdd = true;
		else
			this.isOdd = false;
	}

	public boolean getisExist() {
		return this.isExist;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getModeChess() {
		return this.modeChess;
	}

	public int[][] getarrMapShow() {
		return arrMapShow;
	}

	private boolean badxy(int x, int y) {
		if (x >= width + 20 || x < 0)
			return true;
		return y >= height + 20 || y < 0;
	}

	public boolean chessExist(int i, int j) {
		if (this.arrMapShow[i][j] == 1 || this.arrMapShow[i][j] == 2)
			return true;
		return false;
	}

	public void readyplay(int x, int y) {
		if (badxy(x, y))
			return;
		if (chessExist(x, y))
			return;
		this.arrMapShow[x][y] = 3;
	}

	public void play(int x, int y) {
		if (badxy(x, y))
			return;
		if (chessExist(x, y)) {
			this.isExist = true;
			return;
		} else
			this.isExist = false;
		if (getisOdd()) {
			setisOdd(false);
			this.arrMapShow[x][y] = 1;
		} else {
			setisOdd(true);
			this.arrMapShow[x][y] = 2;
		}
	}

	public void computerDo(int width, int height) {
		int max_black, max_white, max_temp, max = 0;
		setisOdd(true);
		System.out.println("计算机走棋 ...");
		for (int i = 0; i <= width; i++) {
			for (int j = 0; j <= height; j++) {
				if (!chessExist(i, j)) {
					max_white = checkMax(i, j, 2);
					max_black = checkMax(i, j, 1);
					max_temp = Math.max(max_white, max_black);
					if (max_temp > max) {
						max = max_temp;
						this.x = i;
						this.y = j;
					}
				}
			}
		}
		setX(this.x);
		setY(this.y);
		this.arrMapShow[this.x][this.y] = 2;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int checkMax(int x, int y, int black_or_white) {
		int num = 0, max_num, max_temp = 0;
		int x_temp = x, y_temp = y;
		int x_temp1 = x_temp, y_temp1 = y_temp;
		for (int i = 1; i < 5; i++) {
			x_temp1 += 1;
			if (x_temp1 > this.width)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == black_or_white)
				num++;
			else
				break;
		}
		x_temp1 = x_temp;
		for (int i = 1; i < 5; i++) {
			x_temp1 -= 1;
			if (x_temp1 < 0)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == black_or_white)
				num++;
			else
				break;
		}
		if (num < 5)
			max_temp = num;
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		num = 0;
		for (int i = 1; i < 5; i++) {
			y_temp1 -= 1;
			if (y_temp1 < 0)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == black_or_white)
				num++;
			else
				break;
		}
		y_temp1 = y_temp;
		for (int i = 1; i < 5; i++) {
			y_temp1 += 1;
			if (y_temp1 > this.height)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == black_or_white)
				num++;
			else
				break;
		}
		if (num > max_temp && num < 5)
			max_temp = num;
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		num = 0;
		for (int i = 1; i < 5; i++) {
			x_temp1 -= 1;
			y_temp1 -= 1;
			if (y_temp1 < 0 || x_temp1 < 0)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == black_or_white)
				num++;
			else
				break;
		}
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		for (int i = 1; i < 5; i++) {
			x_temp1 += 1;
			y_temp1 += 1;
			if (y_temp1 > this.height || x_temp1 > this.width)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == black_or_white)
				num++;
			else
				break;
		}
		if (num > max_temp && num < 5)
			max_temp = num;
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		num = 0;
		for (int i = 1; i < 5; i++) {
			x_temp1 += 1;
			y_temp1 -= 1;
			if (y_temp1 < 0 || x_temp1 > this.width)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == black_or_white)
				num++;
			else
				break;
		}
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		for (int i = 1; i < 5; i++) {
			x_temp1 -= 1;
			y_temp1 += 1;
			if (y_temp1 > this.height || x_temp1 < 0)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == black_or_white)
				num++;
			else
				break;
		}
		if (num > max_temp && num < 5)
			max_temp = num;
		max_num = max_temp;
		return max_num;
	}

	public boolean judgeSuccess(int x, int y, boolean isodd) {
		int num = 1;
		int arrvalue;
		int x_temp = x, y_temp = y;
		if (isodd)
			arrvalue = 2;
		else
			arrvalue = 1;
		int x_temp1 = x_temp, y_temp1 = y_temp;
		for (int i = 1; i < 6; i++) {
			x_temp1 += 1;
			if (x_temp1 > this.width)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == arrvalue)
				num++;
			else
				break;
		}
		x_temp1 = x_temp;
		for (int i = 1; i < 6; i++) {
			x_temp1 -= 1;
			if (x_temp1 < 0)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == arrvalue)
				num++;
			else
				break;
		}
		if (num == 5)
			return true;
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		num = 1;
		for (int i = 1; i < 6; i++) {
			y_temp1 -= 1;
			if (y_temp1 < 0)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == arrvalue)
				num++;
			else
				break;
		}
		y_temp1 = y_temp;
		for (int i = 1; i < 6; i++) {
			y_temp1 += 1;
			if (y_temp1 > this.height)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == arrvalue)
				num++;
			else
				break;
		}
		if (num == 5)
			return true;
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		num = 1;
		for (int i = 1; i < 6; i++) {
			x_temp1 -= 1;
			y_temp1 -= 1;
			if (y_temp1 < 0 || x_temp1 < 0)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == arrvalue)
				num++;
			else
				break;
		}
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		for (int i = 1; i < 6; i++) {
			x_temp1 += 1;
			y_temp1 += 1;
			if (y_temp1 > this.height || x_temp1 > this.width)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == arrvalue)
				num++;
			else
				break;
		}
		if (num == 5)
			return true;
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		num = 1;
		for (int i = 1; i < 6; i++) {
			x_temp1 += 1;
			y_temp1 -= 1;
			if (y_temp1 < 0 || x_temp1 > this.width)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == arrvalue)
				num++;
			else
				break;
		}
		x_temp1 = x_temp;
		y_temp1 = y_temp;
		for (int i = 1; i < 6; i++) {
			x_temp1 -= 1;
			y_temp1 += 1;
			if (y_temp1 > this.height || x_temp1 < 0)
				break;
			if (this.arrMapShow[x_temp1][y_temp1] == arrvalue)
				num++;
			else
				break;
		}
		if (num == 5)
			return true;
		return false;
	}

	public void showSuccess(JPanel jp) {
		JOptionPane.showMessageDialog(jp, "你赢了", "结果", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showDefeat(JPanel jp) {
		JOptionPane.showMessageDialog(jp, "你输了", "结果", JOptionPane.INFORMATION_MESSAGE);
	}
}

class ChessWindowEvent extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	ChessWindowEvent() {
	}
}

public class FiveChessAppletDemo {
	public static Boolean isBlack = true;

	public static void main(String args[]) {
		ChessFrame cf = new ChessFrame();
		cf.show();
	}
}

class MainPanel extends JPanel implements MouseListener, MouseMotionListener {
	private int width, height;
	private ChessModel cm;

	MainPanel(ChessModel mm) {
		cm = mm;
		width = cm.getWidth();
		height = cm.getHeight();
		addMouseListener(this);
	}

	public void setModel(ChessModel mm) {
		cm = mm;
		width = cm.getWidth();
		height = cm.getHeight();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int j = 0; j <= height; j++) {
			for (int i = 0; i <= width; i++) {
				int v = cm.getarrMapShow()[i][j];
				draw(g, i, j, v);
			}
		}
	}

	public void draw(Graphics g, int i, int j, int v) {
		int x = 20 * i + 20;
		int y = 20 * j + 20;
		if (i != width && j != height) {
			g.setColor(Color.darkGray);
			g.drawRect(x, y, 20, 20);
		}
		if (v == 1) {
			g.setColor(Color.gray);
			g.drawOval(x - 8, y - 8, 16, 16);
			g.setColor(Color.black);
			g.fillOval(x - 8, y - 8, 16, 16);
			FiveChessAppletDemo.isBlack = true;
		}
		if (v == 2) {
			g.setColor(Color.gray);
			g.drawOval(x - 8, y - 8, 16, 16);
			g.setColor(Color.white);
			g.fillOval(x - 8, y - 8, 16, 16);
			FiveChessAppletDemo.isBlack = false;
		}
		if (v == 3) {
			g.setColor(Color.cyan);
			g.drawOval(x - 8, y - 8, 16, 16);
		}
	}

	public void mousePressed(MouseEvent evt) {
		int x = (evt.getX() - 10) / 20;
		int y = (evt.getY() - 10) / 20;
		System.out.println(x + " " + y);
		if (evt.getModifiers() == MouseEvent.BUTTON1_MASK) {
			cm.play(x, y);
			System.out.println(cm.getisOdd() + " " + cm.getarrMapShow()[x][y]);
			repaint();
			if (cm.judgeSuccess(x, y, cm.getisOdd())) {
				cm.showSuccess(this);
				evt.consume();
				ChessFrame.iscomputer = false;
			}
			if (ChessFrame.iscomputer && !cm.getisExist()) {
				cm.computerDo(cm.getWidth(), cm.getHeight());
				repaint();

				if (cm.judgeSuccess(cm.getX(), cm.getY(), cm.getisOdd())) {
					cm.showDefeat(this);
					evt.consume();
				}
			}
		}
	}

	public void mouseClicked(MouseEvent evt) {
	}

	public void mouseReleased(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent mouseevt) {
	}

	public void mouseExited(MouseEvent mouseevent) {
	}

	public void mouseDragged(MouseEvent evt) {
	}

	public void mouseMoved(MouseEvent moveevt) {
		int x = (moveevt.getX() - 10) / 20;
		int y = (moveevt.getY() - 10) / 20;
		cm.readyplay(x, y);
		repaint();
	}
}
