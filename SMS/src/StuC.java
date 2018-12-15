import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StuC {
	ArrayList <Student> al=new ArrayList<Student>();
	File file = new File("./mydata.dat");
	//添加
	public void adds(Student s){
		
			al.add(s);
	}
	//删除
	public void del(int n){
		for(int i=0;i<al.size();i++){
			if(al.get(i).getNum()==n)
				al.remove(i);	
		}
	}
	//求总分
	public void sum(){
		for(int i=0;i<al.size();i++){
			al.get(i).setSum(al.get(i).getJava()+al.get(i).getShuxue()+al.get(i).getYuwen());
		}	
	}
	//排序
	public void sort() {
		  for (int i = 0; i < al.size(); i++) {
			  for (int j = 0; j < al.size()-1-i; j++) {
				  if (al.get(j).getSum() < al.get(j+1).getSum()) {
					  Object o=al.get(j);
					 al.set(j, al.get(j+1));
					 al.set(j+1, (Student) o);
				  }
			  }
		  }
	}
	public void paint(){
		for(int i=0;i<al.size();i++){
			System.out.println(al.get(i));
		}
	}
	public String toString() {
		return "StuC [al=" + al + "]";
	}
	//输出流
	public void stor()
	{
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(al);
			out.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
	//输入流
	public void read()
	{
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(file));
			try {
				al = (ArrayList<Student>)in.readObject();
			} catch (ClassNotFoundException e) {
				
				al=null;
			}
			in.close();
		} catch (FileNotFoundException e) {
			
			File file = new File("./mydata.dat");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	//查找
	public Student find(int n)
	{
		for(int i=0;i<al.size();i++){
			if(al.get(i).getNum()==n){
				return al.get(i);
			}
		}
		return null;
	}
}
