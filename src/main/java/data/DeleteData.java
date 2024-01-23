package data;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

public class DeleteData {
    public DeleteData() {
    }

    public void delete(JTextArea log_textarea, String hardware, String item) {
        File filePath = new File("src/resource/data/database/" + hardware + ".txt");

        // 检查文件是否存在
        if (filePath.exists()) {
            try {
                // 读取现有内容
                List<String> existingData = Files.readAllLines(Paths.get(String.valueOf(filePath)));

                // 用于存储删除后的数据
                HashSet<String> newData = new HashSet<>();

                // 检查是否存在相同的硬件和项目
                boolean alreadyExists = false;
                for (String line : existingData) {
                    String[] parts = line.split(";");
                    if (parts.length == 2 && parts[0].equals(item)) {
                        alreadyExists = true;
                        break;
                    }
                }
                if (alreadyExists) {
                    for (String line : existingData) {
                        // 获取每行第一个字符开始到第一个“;”结束的部分
                        int indexOfSemicolon = line.indexOf(";");
                        String existingItem;

                        if (indexOfSemicolon != -1) {
                            existingItem = line.substring(0, indexOfSemicolon);

                            // 比较 existingItem 和 item
                            if (!existingItem.equals(item)) {
                                newData.add(line);
                            }
                        }
                    }
                    // 写入新数据到文件
                    Files.write(Paths.get(filePath.getAbsolutePath()), newData);
                    Time nowTime = new Time();
                    log_textarea.append("[" + nowTime.getTimePoint() + "] " + "The data has been deleted successfully.\n");
                } else {
                    Time nowTime = new Time();
                    log_textarea.append("[" + nowTime.getTimePoint() + "] " + "Data not found.\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
