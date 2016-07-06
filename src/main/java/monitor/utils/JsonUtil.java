package monitor.utils;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Json工具类
 * User: zhangb
 * Date: 12-4-24
 * Time: 下午4:52
 */
public class JsonUtil {
    private static final Logger log = Logger.getLogger(JsonUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将任意对象转换成json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            if (object != null) {
                return objectMapper.writeValueAsString(object);
            }
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json字符串转换成指定类型的对象
     * @param jsonString  json字符串
     * @param clazz 指定类型
     * @param <T>   返回类型
     * @return
     */
    public static <T> T toObject(String jsonString, Class<T> clazz) {
        try {
            if (StringUtil.isEmpty(jsonString)) {
                return null;
            }
            if (jsonString.indexOf("'") > 0) { // json字符串中，必须使用双引号，不然objectMapper将无法转换
                jsonString = jsonString.replaceAll("'", "\"");
            }
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.warn("parse json string error:" + jsonString, e);
        }
        return null;
    }
}
