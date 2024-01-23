package lang;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageManager {
    private final Map<String, Object> languageData;
    private final Map<String, String> labelKeyMap;

    public LanguageManager() {
        String configPath = "src/Resource/config.yml";
        // 获取config.yml中的language值
        String language = getLanguageFromConfig(configPath);

        // 构建对应语言的文件路径
        String filePath = "src/Resource/lang/Locale_" + language.toUpperCase() + ".yml";

        // 如果语言文件不存在，则使用默认语言（英文）
        if (!Files.exists(Paths.get(filePath))) {
            filePath = "src/Resource/lang/Locale_EN.yml";
        }

        // 初始化 LanguageManager 时加载 YAML 文件
        this.languageData = loadLanguageFile(filePath);
        this.labelKeyMap = buildLabelKeyMap(languageData);
    }

    public String getLanguageFromConfig(String configPath) {
        String language = "en";  // 默认语言为英文

        try {
            // 读取config.yml文件，获取language值
            Path path = Paths.get(configPath);
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                if (line.startsWith("language:")) {
                    language = line.split(":")[1].trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return language;
    }

    private Map<String, Object> loadLanguageFile(String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {
            Yaml yaml = new Yaml();
            return yaml.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> buildLabelKeyMap(Map<String, Object> languageData) {
        Map<String, String> labelKeyMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : languageData.entrySet()) {
            String label = entry.getKey();
            String key = entry.getValue().toString();

            labelKeyMap.put(label, key);
        }
        return labelKeyMap;
    }

    public String getLang(String Lable) {
        // 直接查找预处理的 labelKeyMap
        return labelKeyMap.get(Lable);
    }
}
