package data;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

public class ModifyData {
    public ModifyData() {
    }

    public void modify(JTextArea log_textarea, String hardware, String item, String value, String newItem, String newValue) {
        File filePath = new File("src/resource/data/database/" + hardware + ".txt");
        if (filePath.exists()) {
            try {
                List<String> existingData = Files.readAllLines(Paths.get(filePath.getAbsolutePath()));

                //用于存储修改后的数据
                HashSet<String> newData = new HashSet<>();

                // 检查是否已经存在相同的硬件和项目
                boolean itemAlreadyExists = false;
                for (String line : existingData) {
                    String[] parts = line.split(";");
                    if (parts.length == 2 && parts[0].equals(item) && Double.parseDouble(value)==Double.parseDouble(parts[1])) {
                        itemAlreadyExists = true;
                        break;
                    }
                }

                boolean newItemAlreadyExists = false;
                for (String line : existingData) {
                    String[] parts = line.split(";");
                    if (parts.length == 2 && parts[0].equals(newItem) && Double.parseDouble(newValue)==Double.parseDouble(parts[1])) {
                        newItemAlreadyExists = true;
                        break;
                    }
                }

                if (itemAlreadyExists && newItemAlreadyExists) {
                    Time nowTime = new Time();
                    log_textarea.append("[" + nowTime.getTimePoint() + "] " + "The data is the same before and after the change.\n");
                }
                else if ((!itemAlreadyExists && newItemAlreadyExists) || (!itemAlreadyExists && !newItemAlreadyExists)) {
                    Time nowTime = new Time();
                    log_textarea.append("[" + nowTime.getTimePoint() + "] " + "The data was not found.\n");
                }
                else if (itemAlreadyExists && !newItemAlreadyExists) {
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
                    newData.add(newItem + ";" + newValue);
                    // 写入新数据到文件
                    Files.write(Paths.get(filePath.getAbsolutePath()), newData);
                    Time nowTime = new Time();
                    log_textarea.append("[" + nowTime.getTimePoint() + "] " + "The data has been modified successfully.\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}