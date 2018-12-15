package ljh.handge.data.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ljh.handge.data.util.PropertiesReader;

public class VehicleFactory {
	
	public static Random random;
	public static Map<String,String> types;
	public static String FNAME = "src/config.properties";
	public static int S_Ratio, M_Ratio, B_Ratio, O_Ratio;
	
	static {
		try {
			PropertiesReader.init(FNAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		random = new Random();
		types = new HashMap<String, String>();
		types.put("0", "SMALL");
		types.put("1", "MID");
		types.put("2", "BIG");
		types.put("3", "OTHER");
		
		String[] Ratios = PropertiesReader.getProperty("TYPE_RATIO").split(":");
		S_Ratio = Integer.valueOf(Ratios[0]);
		M_Ratio = Integer.valueOf(Ratios[1]);
		B_Ratio = Integer.valueOf(Ratios[2]);
		O_Ratio = Integer.valueOf(Ratios[3]);
	}
	
	//省份简称范围（31个省份）
	public static String[] PROVINCE = 
		{"京","津","沪","渝","冀","豫","云","辽","黑","湘","皖"
		,"鲁","新","苏","浙","赣","鄂","桂","甘","晋","蒙","陕"
		,"吉","闽","贵","粤","青","藏","川","宁","琼"};//0-30

	//区域代码范围（31个区域）
	public static String[] BJ_ZONE = {"A","C","E","F","B","G"};
	public static String[] TJ_ZONE = {"A","B","C","E"};
	public static String[] HEB_ZONE = {"A","B","C","D","E","F","G","H","J","R","T"};
	public static String[] S1X_ZONE = {"A","B","C","D","E","F","H","J","K","L","M"};
	public static String[] NMG_ZONE = {"A","B","C","D","E","F","G","H","J","K","L","M"};
	public static String[] JL_ZONE = {"A","B","C","D","E","F","G","H","J"};
	public static String[] GD_ZONE = 
		{"A","B","C","D","E","F","G","H","J","K","L","M"
		,"N","P","Q","R","S","T","U","V","W","X","Y","Z"};
	public static String[] SD_ZONE = 
		{"A","B","C","D","E","F","G","H","J","K","L","M"
		,"N","P","Q","R","S","U","V"};
	public static String[] GS_ZONE = {"A","B","C","D","E","F","G","H","J","K","L","M","N","P"};
	public static String[] SC_ZONE = 
		{"A","B","C","D","E","F","H","J","K","L","M",
		"Q","R","S","T","U","V","W","X","Y","Z"};
	public static String[] LN_ZONE = {"A","B","C","D","E","F","G","H","J","K","L","M","N","P","V"};
	public static String[] HLJ_ZONE = {"A","B","C","D","E","F","G","H","J","K","L","M","N","P","R"};
	public static String[] SH_ZONE = {"A","B","D","C","R"};
	public static String[] JS_ZONE = {"A","B","C","D","E","F","G","H","J","K","L","M","N"};
	public static String[] NX_ZONE = {"A","B","C","D"};
	public static String[] HEN_ZONE = 
		{"A","B","C","D","E","F","G","H","J"
		,"K","L","M","N","P","Q","R","S","U"};
	public static String[] HUB_ZONE = 
		{"A","B","C","D","E","F","G","H","J",
		"K","L","M","N","P","Q","R","S"};
	public static String[] HUN_ZONE = {"A","B","C","D","E","F","G","H","J","K","L","M","N","U"};
	public static String[] HAIN_ZONE = {"A","B","C","D","E"};
	public static String[] S2X_ZONE = {"A","B","C","D","E","F","G","H","J","K","U","V"};
	public static String[] XZ_ZONE = {"A","B","C","D","E","F","G","H","J"};
	public static String[] ZJ_ZONE = {"A","B","C","D","E","F","G","H","J","K","L"};
	public static String[] AH_ZONE = {"A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S"};
	public static String[] FJ_ZONE = {"A","B","C","D","E","F","G","H","J","K"};
	public static String[] JX_ZONE = {"A","B","C","D","E","F","G","H","J","K","L","M"};
	public static String[] GX_ZONE ={"A","B","C","D","E","F","G","H","J","K","L","M","N","P","R"};
	public static String[] YN_ZONE = {"A","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S"};
	public static String[] GZ_ZONE = {"A","B","C","D","E","F","G","H","J"};
	public static String[] CQ_ZONE = {"A","B","C","F","G","H"};
	public static String[] QH_ZONE = {"A","B","C","D","E","F","G","H"};
	public static String[] XJ_ZONE = {"A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R"};
	
	//颜色范围
	public static String[] COLORS = {"红","白","银","黑","金"};
	
	//品牌范围
	public static String[] BRANDS = {"宝马","大众","奔驰","本田","丰田","路虎","保时捷","比亚迪","宝骏","凯迪拉克","法拉利"};
	
	//生成车牌，提供两种模式：
	//第一种模式将均匀产生所有省份的车牌，此时第一个参数应为-1，第二个参数任意填写（但必须是double类型）
	//第二种模式将指定一个省份的比例来生成，此时第一个参数为省份下标，第二个参数为百分比
	public static String generatePlate(int p, double weight){
		String plate = "";
		String zone = "";
		int p_code;
		if(p != -1){
			double weight_factor = random.nextDouble();
			if(weight_factor >= 0 && weight_factor < weight)
				p_code = p;
			else
				do{
				p_code = random.nextInt(PROVINCE.length);
				} while(p_code == p);
		}
		else{
			p_code = random.nextInt(PROVINCE.length);
		}
		String province = PROVINCE[p_code];
		switch(p_code) {
		case 0 :  zone = BJ_ZONE[random.nextInt(BJ_ZONE.length)]; break ; //北京 京
		case 1 :  zone = TJ_ZONE[random.nextInt(TJ_ZONE.length)]; break ; //天津 津
		case 2 :  zone = SH_ZONE[random.nextInt(SH_ZONE.length)]; break ; //上海 沪
		case 3 :  zone = CQ_ZONE[random.nextInt(CQ_ZONE.length)]; break ; //重庆 渝
		case 4 :  zone = HEB_ZONE[random.nextInt(HEB_ZONE.length)]; break ; //河北 冀
		case 5 :  zone = HEN_ZONE[random.nextInt(HEN_ZONE.length)]; break ; //河南 豫
		case 6 :  zone = YN_ZONE[random.nextInt(YN_ZONE.length)]; break ; //云南 云
		case 7 :  zone = LN_ZONE[random.nextInt(LN_ZONE.length)]; break ; // 辽宁 辽
		case 8 :  zone = HLJ_ZONE[random.nextInt(HLJ_ZONE.length)]; break ; //黑龙江 黑
		case 9 :  zone = HUN_ZONE[random.nextInt(HUN_ZONE.length)]; break ; //湖南 湘
		case 10 : zone = AH_ZONE[random.nextInt(AH_ZONE.length)]; break ; //安徽 皖
		case 11 : zone = SD_ZONE[random.nextInt(SD_ZONE.length)]; break ; //山东 鲁
		case 12 : zone = XJ_ZONE[random.nextInt(XJ_ZONE.length)]; break ; //新疆 新
		case 13 : zone = JS_ZONE[random.nextInt(JS_ZONE.length)]; break ; //江苏 苏
		case 14 : zone = ZJ_ZONE[random.nextInt(ZJ_ZONE.length)]; break ; //浙江 浙
		case 15 : zone = JX_ZONE[random.nextInt(JX_ZONE.length)]; break ; //江西 赣
		case 16 : zone = HUB_ZONE[random.nextInt(HUB_ZONE.length)]; break ; //湖北 鄂
		case 17 : zone = GX_ZONE[random.nextInt(GX_ZONE.length)]; break ; //广西 桂
		case 18 : zone = GS_ZONE[random.nextInt(GS_ZONE.length)]; break ; //甘肃 甘
		case 19 : zone = S1X_ZONE[random.nextInt(S1X_ZONE.length)]; break ; //山西 晋
		case 20 : zone = NMG_ZONE[random.nextInt(NMG_ZONE.length)]; break ; //内蒙古 蒙
		case 21 : zone = S2X_ZONE[random.nextInt(S2X_ZONE.length)]; break ; //陕西 陕
		case 22 : zone = JL_ZONE[random.nextInt(JL_ZONE.length)]; break ; //吉林 吉
		case 23 : zone = FJ_ZONE[random.nextInt(FJ_ZONE.length)]; break ; //福建 闽
		case 24 : zone = GZ_ZONE[random.nextInt(GZ_ZONE.length)]; break ; //贵州 贵
		case 25 : zone = GD_ZONE[random.nextInt(GD_ZONE.length)]; break ; //广东 粤
		case 26 : zone = QH_ZONE[random.nextInt(QH_ZONE.length)]; break ; //青海 青
		case 27 : zone = XZ_ZONE[random.nextInt(XZ_ZONE.length)]; break ; //西藏 藏
		case 28 : zone = SC_ZONE[random.nextInt(SC_ZONE.length)]; break ; //四川 川
		case 29 : zone = NX_ZONE[random.nextInt(NX_ZONE.length)]; break ; //宁夏 宁
		case 30 : zone = HAIN_ZONE[random.nextInt(HAIN_ZONE.length)]; break ; //海南 琼			
		}
		String TailNumber = String.format("%05d", random.nextInt(100000));
		plate = province + zone + TailNumber;
		return plate;
	}
	
	//生成车型
	//需要设置小型车small、中型车middle、大型车big和其他车型other的数量比
	public static String generateType(int s, int m, int b,int o) {
		int sum_weight = s + m + b + o;
		int start = 0;
		int A = start + s;
		int B = A + m;
		int C = B + b;
		int end = sum_weight;
		int number = random.nextInt(sum_weight);
		if(number >= start && number < A)
			return String.valueOf(0);
		else if(number >= A && number < B)
			return String.valueOf(1);
		else if(number >= B && number < C)
			return String.valueOf(2);
		else if(number >= C && number < end)
			return String.valueOf(3);
		else
			return String.valueOf(-1);
	}
	
	//生成速度
	//计算因子：车辆限速（最大值）、最小值、高斯方差，注:限速因道路类型和车型不同而不同。
	public static String generateSpeed(String type, String speedlimit) {
		String speed = "";
		double overspeed_number_ratio = 0.001;
		double rnumber = random.nextDouble();
		String prefix = types.get(type);
//		double max = Double.valueOf(PropertiesReader.getProperty(prefix + "_MAX"));
		double max = Double.valueOf(speedlimit);
		
		if(rnumber <= overspeed_number_ratio){
			double overspeed_factor = 0;
			do{
				overspeed_factor = 1.0 + random.nextDouble();
			} while (overspeed_factor > 1.5);
			speed = String.valueOf((int)(max * overspeed_factor));
		}
		else{
			double mean = 0, variance = 0 , min = 0;

			variance = Double.valueOf(PropertiesReader.getProperty(prefix + "_VARIANCE"));
			min = Double.valueOf(PropertiesReader.getProperty(prefix + "_MIN"));
			mean = max - min;

			double x = 0;
			do {
				x = random.nextGaussian() * Math.sqrt(variance) + mean;
			} while (x < min || x > max);
			speed = String.valueOf((int)x);
		}
		return speed;
	}

	//生成一辆车
	public Vehicle getVehicle(int p_code, double p_ratio, String gate){
		Vehicle vehicle = new Vehicle();
		vehicle.setPlateNumber(generatePlate(p_code, p_ratio));
		vehicle.setColor(COLORS[random.nextInt(COLORS.length)]);
		vehicle.setBrand(BRANDS[random.nextInt(BRANDS.length)]);
		vehicle.setType(generateType(S_Ratio, M_Ratio, B_Ratio, O_Ratio));
		vehicle.setSpeed("0");
		vehicle.setCurrentGate(gate);
		vehicle.setPreviousGate("0");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vehicle.setDesireTime(df.format(new Date()));
		vehicle.setIllegalTimes("-1");
		return vehicle;
	}
	
	//生成指定车牌的“黑车”
	public Vehicle getVehicle(String plateNumber, String gate){
		Vehicle vehicle = new Vehicle();
		vehicle.setPlateNumber(plateNumber);
		vehicle.setColor(COLORS[random.nextInt(COLORS.length)]);
		vehicle.setBrand(BRANDS[random.nextInt(BRANDS.length)]);
		vehicle.setType(generateType(S_Ratio, M_Ratio, B_Ratio, O_Ratio));
		vehicle.setSpeed("0");
		vehicle.setCurrentGate(gate);
		vehicle.setPreviousGate("0");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vehicle.setDesireTime(df.format(new Date()));
		vehicle.setIllegalTimes(String.valueOf(random.nextInt(6) + 1));
		return vehicle;
	}
	
	//生成一批车
	public String[] startProduction(int num, int p_code, double p_ratio, String gate){
		Map<String,String> vehicles = new HashMap<String,String>();
		do{
			Vehicle vehicle = getVehicle(p_code, p_ratio, gate);
			String key = vehicle.getPlateNumber();
			String value = vehicle.toString();
			vehicles.put(key, value);
		} while(vehicles.size() < num);
		String[] vehicleInfo = new String[vehicles.size()];
		return vehicles.values().toArray(vehicleInfo);
	}
	
	//生成一批指定车牌的“黑车”
	public String[] startProduction(String[] plateNumbers, String gate){
		Map<String,String> vehicles = new HashMap<String,String>();
		for(int i = 0; i < plateNumbers.length; i++ ){
			Vehicle vehicle = getVehicle(plateNumbers[i], gate);
			String key = vehicle.getPlateNumber();
			String value = vehicle.toString();
			vehicles.put(key, value);
		}
		String[] vehicleInfo = new String[vehicles.size()];
		return vehicles.values().toArray(vehicleInfo);
	}
}