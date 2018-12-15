package competition;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/**
 * 
 *	EnergyContent类：完成对能耗数据的XML解析并保存解析结果
 *	三个保存解析结果的HashMap：
 *	commonMap 存储XML文件中的公共信息，Key是标签名，Value是标签值
 *	meterMap 存储XML文件中电表信息，Key是电表编号，Value是functionMap
 *	functionMap 存储每个电表中的所有能耗信息，Key是标签的属性名，Value是属性值
 *	注意：functionMap中存储能耗值数据的Key是function_*，*表示取值范围为1-6(表示6个function)
 *	三个API：
 *	initXml()：解析接收到的XML文本，并将解析结果存入上述定义的HashMap
 *	getCommonMap()：获取commonMap
 *	getMeterMap()：获取meterMap
 */
public class EnergyContent {
	
	//commonMap：存储XML文件中的公共部分标签数据，Key是标签名，Value是标签值
	private HashMap<String, String> commonMap = new HashMap<>();
	//MeterMap：存储XML文件中Meter数据，Key是Meter的编号，Value是energyMap
	private static HashMap<String,HashMap<String,String>> meterMap = new HashMap<>();
	//EnergyMap：存储一个Meter中的所有function信息，key是标签的属性名，value是属性值
	private static HashMap<String,String> functionMap = new HashMap<>();
	
	//全局统计MapReduce程序执行次数,给定代码已使用，便于观察和调试
	public static int runCounter = 0;

	
	//初始化需解析的XML的方法，需传递一个string类型的XML参数
	public void initXml(String xml){
		try {
			XMLStreamReader reader = XMLInputFactory.newInstance()
					.createXMLStreamReader(
							new ByteArrayInputStream(xml.getBytes()));
			/* currentElement 当前标签名
				  attrName 标签属性名
				  attrValue 标签属性值
				  attrText 标签文本值
			 */
			String currentElement = "";
			String attrName = "";
			String attrValue = "";
			String attrText = "";
			//	读到meter时重新赋值0，START_ELEMENT状态下当读取到function时加1
			int start_FuncCounter = 0;
			//	读到meter时重新赋值0，CHARACTERS状态下当读取到function时加1
			int char_FuncCounter = 0;
			//	meterId：用来保存meter标签的ID属性
			String meterId = "";
			while(reader.hasNext()){
				//	获取标签的事件类型
				int code = reader.next();
				switch(code){
				//	读取XML文本中的开始标签
				case XMLStreamConstants.START_ELEMENT: // 读取XML文本中的开始标签
					//	获取当前标签的名字
					currentElement=reader.getLocalName();
					//	获取当前标签的所有属性数量
					int attrCount=reader.getAttributeCount();
					//	当读取到meter标签时将funcMap重新初始化，将start_FuncCounter和char_FuncCounter的计数变量重置为0，并获取到meterId的属性值
					if(currentElement.equalsIgnoreCase("meter")){
						start_FuncCounter = 0;
						char_FuncCounter = 0;
						//	第一个属性值赋值给meterId
						meterId = reader.getAttributeValue(0);
						functionMap = new HashMap<String,String>();
					}
					//	当读取到function标签时，将开始标签状态下的function计数变量+1，将获取到的function的属性和值保存到functionMap中，再将functionMap保存到meterMap中
					else if(currentElement.equalsIgnoreCase("function")){
						start_FuncCounter++;
						for(int i = 0; i < attrCount; i++){
							attrName = reader.getAttributeName(i).toString();
							attrValue = reader.getAttributeValue(i);
							functionMap.put(attrName + "_" + start_FuncCounter, attrValue);
							meterMap.put(meterId, functionMap);
						}
					}
					//	将得到的公共部分标签的信息保存到commonMap中
					else{
						for(int i = 0; i < attrCount; i++){
							attrName = reader.getAttributeName(i).toString();
							attrValue = reader.getAttributeValue(i);
							commonMap.put(currentElement + "_" + attrName, attrValue);
						}
					}
					break;
				case XMLStreamConstants.CHARACTERS: // 读取XML的文本节点
					//	过滤文本为空的标签
					if (!reader.getText().trim().isEmpty()) {
						//	将得到的标签的文本值赋值给attrText
						attrText = reader.getText(); 
						//	当读到function标签时，将当前标签文本状态下的function计数变量+1，将获取到的function的属性和值保存到functionMap中，再将functionMap保存到meterMap中
						if(currentElement.equalsIgnoreCase("function")){
							char_FuncCounter++;
							functionMap.put("function_" + char_FuncCounter, attrText);
							meterMap.put(meterId, functionMap);
						}else{
							//	将读到公共部分的标签值保存到commonMap中
							commonMap.put(currentElement, attrText);
						}
					}
					break;
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}	
	
	//	获取解析结果数据的API，返回保存meterId和functionMap的HashMap
	public HashMap<String, HashMap<String, String>> getMeterMap(){
		return meterMap;
	}
	
	//	获取解析结果数据的API，返回保存公共标签数据的HashMap
	public HashMap<String, String> getCommonMap(){
		return commonMap;
	}
}
