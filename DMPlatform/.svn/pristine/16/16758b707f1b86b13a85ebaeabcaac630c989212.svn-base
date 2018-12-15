package com.goldtel.dmp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.Writable;

public class Utils
{
	/**
	 * 获取算法用的临时路径
	 * @param className
	 * @return
	 */
	public static String getTempPath(String className)
	{
		String tmpPath = Const.HDFS_MYCLU + Const.TEMPPATH + Const.SPLIT_PATH + className + Const.SPLIT_PATH + System.currentTimeMillis();
		return tmpPath;
	}

	/**
	 * 构造输入参数
	 * @param para
	 */
	public static String[] createDescription(Map<String, String[]> para)
	{
		List<String> lst = new ArrayList<String>();

		for (String key : para.keySet())
		{
			String[] val = para.get(key);
			lst.add(key.trim());
			if (null != val && val.length > 0)
			{
				for (int i = 0; i < val.length; i++)
				{
					lst.add(val[i].trim());
				}
			}
		}
		
		String[] arg = (String[])lst.toArray(new String[0]);
		return arg;
	}
	
	public static String[] createDescriptionStr(Map<String, String> para)
	{
		List<String> lst = new ArrayList<String>();

		for (String key : para.keySet())
		{
			String val = para.get(key).trim();
			lst.add(key.trim());
			if (null != val && val.length() > 0)
			{
				lst.add(val);
			}
		}
		
		String[] arg = (String[])lst.toArray(new String[0]);
		return arg;
	}

	/**
	 * 将输入字符串参数，转化为参数名:参数值[,参数值2...]格式的参数
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public static String[] convertParaToArr(String para)
	{
	    String[] res = null;
	    if (null != para && para.length() > 0)
	    {
	        res = para.split(Const.SPLIT_ANALYZE_S);
	    }

	    return res;
	}

	/**
	 * 创建一个新路径
	 * @param destDirName
	 * @throws Exception
	 */
	public static void createNewPath(String destDirName) throws Exception
	{
	    File dir = new File(destDirName);  
        if (dir.exists())
        {  
            System.out.println("创建目录" + destDirName + "，目标目录已经存在，删除之");  
            deleteDir(dir);
        }  
        if (!destDirName.endsWith(File.separator))
        {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs())
        {  
            System.out.println("创建目录" + destDirName + "成功");  
        }
        else
        {  
            System.out.println("创建目录" + destDirName + "失败");  
            throw new Exception("创建目录" + destDirName + "失败");
        }  
	}
	
	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            //  递归删除目录中的子目录下
            for (int i=0; i<children.length; i++)
            {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success)
                {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 设置数据到map，如果key下已有值，将新值加在后面
     * @param map
     * @param key
     * @param value
     */
    public static void putMap(Map<String, LinkedList<String>> map, String key, String value)
    {
        if (map.containsKey(key))
        {
            LinkedList<String> link = map.get(key);
            if (!link.contains(value))
            {
                link.add(value);
                map.put(key, link);
            }
        }
        else
        {
            LinkedList<String> link = new LinkedList<String>();
            link.add(value);
            map.put(key, link);
        }
    }

    /**
     * 根据分隔符将字符串转化成List
     * @param para
     * @param split
     * @return
     */
    public static List<String> putList(String para, String split)
    {
        List<String> lst = new LinkedList<String>();

        if (null != para && null != split)
        {
            String[] arr = para.split(split);
            for (int i = 0; i < arr.length; i++)
            {
                lst.add(arr[i].trim());
            }
        }

        return lst;
    }

    /**
     * 根据分隔符将字符串转化成List，如果连续2个,，则认为前面的,是参数，后面的才是分隔符
     * @param para
     * @param split
     * @return
     */
    public static List<String> putListSplit(String para, String split) throws Exception
    {
        List<String> lst = new LinkedList<String>();

        if (Const.SPLIT_ANALYZE_S.equalsIgnoreCase(split))  //  如果分隔符不是,，则不考虑重复分隔符的问题
        {
            int is = para.indexOf("-regex:");
            int ie = para.lastIndexOf("-regex:");
            if (is != ie)   //  Check duplication regex
            {
                throw new Exception("Inputted parameter error. Duplication parameters.");
            }

            if (para.indexOf("-regex:,") >= 0)
            {
                String strSub1 = para.substring(0, para.indexOf("-regex:,")).trim();
                String strSub2 = para.substring(para.indexOf("-regex:,") + "-regex:,".length(), para.length()).trim();
                if (strSub1.endsWith(","))
                {
                    strSub1 = strSub1.substring(0, strSub1.length() - 1);
                }
                if (strSub2.startsWith(","))
                {
                    strSub2 = strSub2.substring(1, strSub2.length());
                }

                if (strSub1.length() > 1)
                {
                    String[] arr = strSub1.split(split);
                    for (int i = 0; i < arr.length; i++)
                    {
                        lst.add(arr[i].trim());
                    }
                }
                lst.add("-regex:,");
                if (strSub2.length() > 1)
                {
                    String[] arr = strSub2.split(split);
                    for (int i = 0; i < arr.length; i++)
                    {
                        lst.add(arr[i].trim());
                    }
                }
            }
        }
        else
        {
            if (null != para && null != split)
            {
                String[] arr = para.split(split);
                for (int i = 0; i < arr.length; i++)
                {
                    lst.add(arr[i].trim());
                }
            }
        }

        return lst;
    }

    /**
     * 根据分隔符将字符串转化成Map，如果连续2个,，则认为前面的,是参数，后面的才是分隔符
     * @param para
     * @param split
     * @return
     */
    public static Map<String, String> putMap(List<String> para, String split)
    {
        Map<String, String> paraDm = new HashMap<String, String>();

        for (String str : para)
        {
            String[] value = str.split(split);
            if (value.length == 2)
            {
                paraDm.put(value[0].trim(), value[1].trim());
            }
        }

        return paraDm;
    }
}