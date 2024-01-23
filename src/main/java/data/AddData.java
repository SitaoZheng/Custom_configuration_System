package data;

import lang.LanguageManager;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class AddData {
    public AddData() {
    }

    public void add(JTextArea log_textarea, String hardware, String item, String value) {
        LanguageManager lang = new LanguageManager();
        File filePath = new File("src/resource/data/database/" + hardware + ".txt");
        String addData = item + ";" + Double.parseDouble(value);
        // 检查文件是否存在
        if (filePath.exists()) {
            try {
                // 读取现有内容
                List<String> existingData = Files.readAllLines(Paths.get(String.valueOf(filePath)));

                // 检查是否已经存在相同的硬件，项目和价格
                boolean alreadyExists = false;
                for (String line : existingData) {
                    String[] parts = line.split(";");
                    if (parts.length == 2 && parts[0].equals(item)) {
                        alreadyExists = true;
                        break;
                    }
                }
                if (alreadyExists) {
                    Time nowTime = new Time();
                    log_textarea.append("[" + nowTime.getTimePoint() + "] " + lang.getLang("Error_Add") + "\n");
                } else {
                    // 添加新内容
                    existingData.add(addData);
                    // 写回文件
                    Files.write(Paths.get(String.valueOf(filePath)), existingData, StandardOpenOption.WRITE);

                    Time nowTime = new Time();
                    log_textarea.append("[" + nowTime.getTimePoint() + "] " + lang.getLang("Tip_Add") + "\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
