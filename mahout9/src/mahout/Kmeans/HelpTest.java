package mahout.Kmeans;

import org.apache.commons.cli2.util.HelpFormatter;

public class HelpTest {
	public HelpFormatter hf ;

	public static void main(String[] args) {
		HelpFormatter hf = new HelpFormatter();
		hf.setFooter("lllllllllllllllllllllllllllllllllllllllll");
		hf.setHeader("222222222222222222");
		hf.print();
	}
}
