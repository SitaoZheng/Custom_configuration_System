package data;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Time {
    private java.util.Date Date1 = new Date();
    String x = Date1.toString();
    public Time() {}
    public String getTimePoint() {
        String x = Date1.toString();
        int y1 = x.indexOf(":");
        int y2 = x.lastIndexOf(":");
        String time = x.substring(y1 - 2, y2+3);
        return time;
    }

    // 在GUI组件中，使用LanguageManager.getMessage("key")获取本地化字符串
    public static class LanguageManager {
        private static ResourceBundle messages;

        static {
            loadLanguageSettings();
        }

        private static void loadLanguageSettings() {
            // 从配置中读取语言设置的代码。并加载相应的语言文件
            // 假设语言文件命名为“Messages_en”。'Messages_zh.yml'和'Messages_zh.yml'
            String languageSetting = "en"; // This should be read from config.yml
            messages = ResourceBundle.getBundle("Messages", new Locale(languageSetting));
        }

        public static String getMessage(String key) {
            return messages.getString(key);
        }
    }
}