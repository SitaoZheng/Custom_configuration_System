package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Request {
    public Request() {
    }
    public List<List<String>> getItem(String enter, String choice) {
        List<List<String>> dataArray = new ArrayList<>();
        List<List<String>> temp;
        File folderPath;
        File[] files;
        dataArray.clear();

        switch (choice) {
            case "Hardware":
                dataArray.clear();
                // 创建文件对象
                File filePath = new File("src/resource/data/database/" + enter + ".txt");

                // 检查文件是否存在
                if (filePath.exists()) {
                    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                        // 读取每行内容
                        String line;
                        while ((line = br.readLine()) != null) {
                            // 查找分号的位置
                            int semicolonIndex = line.indexOf(";");
                            // 找分号
                            if (semicolonIndex != -1) {
                                // 获取分号之前和之后
                                String beforeSemicolon = line.substring(0, semicolonIndex);
                                String afterSemicolon = line.substring(semicolonIndex + 1);

                                List<String> rowData = new ArrayList<>();
                                rowData.add(enter.toUpperCase());
                                rowData.add(beforeSemicolon);
                                rowData.add(afterSemicolon);
                                dataArray.add(rowData);
                            }
                        }
                        return dataArray;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            case "Item":
                dataArray.clear();
                temp = new ArrayList<>();
                folderPath = new File("src/resource/data/database/");

                // 获取目录下的所有文件及数据
                files = folderPath.listFiles();
                if (files != null) {
                    // 遍历文件数组
                    for (File file : files) {
                        // 判断是否是文件
                        if (file.isFile()) {
                            try (BufferedReader br = new BufferedReader(new FileReader("src/resource/data/database/" + file.getName()))) {
                                // 读取每行内容
                                String line;
                                while ((line = br.readLine()) != null) {
                                    // 查找分号的位置
                                    int semicolonIndex = line.indexOf(";");
                                    // 找分号
                                    if (semicolonIndex != -1) {
                                        // 获取分号之前和之后
                                        String beforeSemicolon = line.substring(0, semicolonIndex);
                                        String afterSemicolon = line.substring(semicolonIndex + 1);

                                        List<String> rowData = new ArrayList<>();
                                        rowData.add(file.getName().substring(0, file.getName().indexOf(".")));
                                        rowData.add(beforeSemicolon);
                                        rowData.add(afterSemicolon);
                                        temp.add(rowData);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                for (List<String> row : temp) {
                    // 获取第二列
                    String secondColumnValue = row.get(1);

                    // 检查第二列数据是否以特定字符串开头
                    if (secondColumnValue instanceof String &&
                            ((String) secondColumnValue).startsWith(enter)) {
                        // 如果是，则将整行数据添加到 dataArray 中
                        dataArray.add(new ArrayList<>(row));
                    }

                }
                return dataArray;
            case "Value":
                dataArray.clear();
                temp = new ArrayList<>();
                folderPath = new File("src/resource/data/database/");

                // 获取目录下的所有文件及数据
                files = folderPath.listFiles();
                if (files != null) {
                    // 遍历文件数组
                    for (File file : files) {
                        // 判断是否是文件
                        if (file.isFile()) {
                            try (BufferedReader br = new BufferedReader(new FileReader("src/resource/data/database/" + file.getName()))) {
                                // 读取每行内容
                                String line;
                                while ((line = br.readLine()) != null) {
                                    // 查找分号的位置
                                    int semicolonIndex = line.indexOf(";");
                                    // 找分号
                                    if (semicolonIndex != -1) {
                                        // 获取分号之前和之后
                                        String beforeSemicolon = line.substring(0, semicolonIndex);
                                        String afterSemicolon = line.substring(semicolonIndex + 1);

                                        List<String> rowData = new ArrayList<>();
                                        rowData.add(file.getName().substring(0, file.getName().indexOf(".")));
                                        rowData.add(beforeSemicolon);
                                        rowData.add(afterSemicolon);
                                        temp.add(rowData);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                for (List<String> row : temp) {
                    // 获取第二列
                    String secondColumnValue = row.get(2);

                    // 检查第二列数据是否以特定字符串开头
                    if (secondColumnValue instanceof String &&
                            ((String) secondColumnValue).startsWith(enter)) {
                        // 如果是，则将整行数据添加到 dataArray 中
                        dataArray.add(new ArrayList<>(row));
                    }
                }
                return dataArray;
        }
        return dataArray;
    }
}
