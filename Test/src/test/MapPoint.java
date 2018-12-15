package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MapPoint {

	public static void main(String[] args) {
		File file = new File("F://1.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			FileWriter writer = new FileWriter("F://2.txt", true);
           
			String str = null;
			String jindu = null;
			String weidu = null;
			String content = null;
			String[] arr = new String[2];
	        // 一次读入一行，直到读入null为文件结束
	        while ((str = reader.readLine()) != null) {
	        	arr = str.split(",");
	        	jindu = arr[1];
	        	weidu = arr[0];
	        	content ="new BMap.Point(" + jindu +"," + weidu + "),\n";
	        	writer.write(content);     
	        }
	        reader.close();
	        writer.close();
		} catch(IOException e) {	
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        

	}

}
