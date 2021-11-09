package utils;

import com.example.config.Constants;
import com.example.utils.PropertiesUtil;
import org.junit.Test;

public class PropertiesUtilTest {

    @Test
    public void testReadProp() {
        PropertiesUtil.readProperties("test");
        System.out.println(PropertiesUtil.getProperty(Constants.KafkaConfigFileName,"name"));
        System.out.println(PropertiesUtil.getProperty(Constants.KafkaConfigFileName,"pwd"));
    }

}