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

    public static Object calculateTotal(JTable table_1) {
        int valueColumnIndex = 3;
        int numColumnIndex = 2;
        double sum = 0.0;
        // 遍历二维数组的行
        for (int i = 0; i < 8; i++) {
            Object cellValue = table_1.getValueAt(i, valueColumnIndex);
            Object cellNum = table_1.getValueAt(i, numColumnIndex);
            if (cellValue != null && !cellValue.toString().isEmpty() && cellNum != null && !cellNum.toString().isEmpty()) {
                // 只有在数据不为null且不为空的情况下才进行累加
                sum += Double.parseDouble(cellValue.toString()) * Double.parseDouble(cellNum.toString());
            }
        }
        return sum;
    }

    public static void clearValue(int row, JTable table_1) {
        table_1.setValueAt("", row, 2);
    }

    // 找 item 对应的 value
    public String getValue(JTable table, int row, int column) {
        // 获取指定行和列的数据
        String data = table.getValueAt(row, column).toString();

        // 获取整个表格的数据
        List<List<String>> dataArray = this.getData();

        // 初始化返回值
        String value = null;

        // 遍历表格数据
        for (List<String> rowList : dataArray) {
            // 检查当前行的长度是否大于指定列，并检查指定列的数据是否与目标数据相匹配
            if (rowList.size() > column && rowList.get(column).equals(data)) {
                // 如果匹配，获取指定列的值
                if (rowList.size() > (column + 1)) {
                    value = rowList.get(column + 1); // 获取下一列的值
                    break; // 找到匹配项后退出循环
                }
            }
        }

        // 返回匹配项的值，如果没有匹配项，则返回null
        return value;
    }
}
