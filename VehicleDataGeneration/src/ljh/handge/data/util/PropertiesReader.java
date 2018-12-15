package ljh.handge.data.util;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {
  protected static Properties props = new Properties();

  public static void init(String fileName) throws Exception {
    FileInputStream fis = new FileInputStream(fileName);
    props.load(fis);
  }

  public static String getProperty(String key) {
    return props.getProperty(key);
  }

  public static String getProperty(String key, String def) {
    return props.getProperty(key, def);
  }
}
