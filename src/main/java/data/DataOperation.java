package data;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataOperation {
    public DataOperation() {
    }

    public List<List<String>> getData() {
        List<List<String>> dataArray = new ArrayList<>();
        File folderPath;
        File[] files;
        dataArray.clear();
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
                                dataArray.add(rowData);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return dataArray;
    }

    public String getValue(JTable table_1, int row) {
        String data = table_1.getValueAt(row, 1).toString();
        List<List<String>> dataArray = this.getData();
        String value = null;
        for (List<String> rowList : dataArray) {
            // 检查第二列是否匹配
            if (rowList.size() > 1 && rowList.get(1).equals(data)) {
                // 如果匹配，获取第三列的值
                if (rowList.size() > 2) {
                    value = rowList.get(2);
                    break; // 找到匹配项后可以退出循环
                }
            }
        }
        return value;
    }
    public static void clearValue(int row, JTable table_1) {
        table_1.setValueAt("", row, 2);
    }
    public static Object calculateTotal(JTable table_1) {
        int columnIndex = 2;
        double sum = 0.0;
        // 遍历二维数组的行
        for (int i = 0; i < 8; i++) {
            Object cellValue = table_1.getValueAt(i, columnIndex);

            if (cellValue != null && !cellValue.toString().isEmpty()) {
                // 只有在数据不为null且不为空的情况下才进行累加
                sum += Double.parseDouble(cellValue.toString());
            }
        }
        return sum;
    }

}
