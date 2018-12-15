import java.io.Serializable;
public class Student implements Serializable{
	private String name;
	private int num;
	private int yuwen;
	private int shuxue;
	private int java;
	private int sum=0;
	public Student(){
	}
	public Student(String name, int num, int yuwen, int shuxue,
			int java) {
		super();
		this.name = name;
		this.num = num;
		this.yuwen = yuwen;
		this.shuxue = shuxue;
		this.java = java;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getYuwen() {
		return yuwen;
	}
	public void setYuwen(int yuwen) {
		this.yuwen = yuwen;
	}
	public int getShuxue() {
		return shuxue;
	}
	public void setShuxue(int shuxue) {
		this.shuxue = shuxue;
	}
	public int getJava() {
		return java;
	}
	public void setJava(int java) {
		this.java = java;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String toString() {
		return "Student [name=" + name + ", num=" + num + ", yuwen=" + yuwen
				+ ", shuxue=" + shuxue + ", java=" + java + ", sum=" + sum
				+ "]";
	}
}