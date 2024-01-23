package data;

import lang.LanguageManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import unit.Placeholder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Import {
    public static void readExcelAndUpdateTable(String filePath, DefaultTableModel tableModel_1, DefaultTableModel tableModel_2, JTextArea ps) {
        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            // 获取第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);

            // 存储A2:D9的数据
            Map<String, Map<String, Map<Integer, Double>>> dataMap = new HashMap<>();
            // 遍历行
            for (int rowNum = 1; rowNum <= 8; rowNum++) {
                Row row;
                row = sheet.getRow(rowNum);

                // 获取第一列（Hardware）
                Cell hardwareCell = row.getCell(0);
                String hardware = hardwareCell.getStringCellValue();

                // 获取第二列（Item）
                Cell itemCell = row.getCell(1);
                String item = itemCell.getStringCellValue();

                // 获取第三列（Num）
                Cell numCell = row.getCell(2);
                Integer cellNum = (int) numCell.getNumericCellValue();

                // 获取第四列（Value）
                Cell valueCell = row.getCell(3);
                double cellValue = valueCell.getNumericCellValue();

                // 存储到dataMap
                Map<String, Map<Integer, Double>> innerMap = dataMap.computeIfAbsent(hardware, k -> new HashMap<>());
                innerMap.put(item, new HashMap<>());
                innerMap.get(item).put(cellNum, cellValue);
            }

            // 获取 Total 的数据
            Row total_Row = sheet.getRow(9);
            Cell total_Cell = total_Row.getCell(3);
            Double totalValue = total_Cell.getNumericCellValue();
            // 获取 ps 的数据
            Row ps_Row = sheet.getRow(10);
            Cell ps_Cell = ps_Row.getCell(0);
            String pstext = ps_Cell.getStringCellValue();

            // 更新到table1
            updateTableFromDataMap(tableModel_1, dataMap);

            // 更新到table2
            tableModel_2.setValueAt(totalValue, 0, 1);
            tableModel_2.fireTableDataChanged();

            // 更新到ps
            ps.setText("");
            ps.append(pstext);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateTableFromDataMap(DefaultTableModel tableModel_1, Map<String, Map<String, Map<Integer, Double>>> dataMap) {
        for (int row = 0; row < dataMap.size(); row++) {
            // 获取表格第0行第0列的字符串
            String targetHardware = tableModel_1.getValueAt(row, 0).toString();

            // 查找 map 中匹配的行
            Map<String, Map<Integer, Double>> matchingRow = dataMap.get(targetHardware);

            // 将匹配行的第二列和第三列和第四列数据存储到表格的第0行第1列和第0行第2列和第0行第3列
            if (matchingRow != null) {
                // 获取匹配行的第二列和第三列和第四列数据
                String itemKey = matchingRow.keySet().iterator().next();
                Map<Integer, Double> innerMap = matchingRow.get(itemKey);
                Integer cellNum = innerMap.keySet().iterator().next();
                Double cellValue = innerMap.get(cellNum);

                // 更新表格数据
                tableModel_1.setValueAt(itemKey, row, 1);
                tableModel_1.setValueAt(cellNum, row, 2);
                tableModel_1.setValueAt(cellValue, row, 3);
            }
        }

        // 在循环外部调用
        tableModel_1.fireTableDataChanged();
    }

    public void Import(String file, JTextArea log_textarea, DefaultTableModel tableModel_1, DefaultTableModel tableModel_2, JTextArea ps) {
        LanguageManager lang = new LanguageManager();
        String name = file + ".xlsx";
        // 指定文件夹路径
        Path folderPath = Paths.get("src/resource/data/file/");
        try {
            // 遍历文件夹中的每个文件
            boolean state = Files.walk(folderPath)
                    .filter(Files::isRegularFile)
                    .anyMatch(filePath -> {
                        // 获取文件名
                        String fileName = filePath.getFileName().toString();
                        // 判断是否为exel文件名
                        return fileName.equals(name);
                    });

            if (state) {
                tableModel_1.setColumnIdentifiers(new Object[]{lang.getLang("Hardware"), lang.getLang("Item"), lang.getLang("Num"), lang.getLang("Value")});
                readExcelAndUpdateTable(folderPath.resolve(name).toString(), tableModel_1, tableModel_2, ps);

                Time nowTime = new Time();
                String log = lang.getLang("Tip_Imported");
                log_textarea.append("[" + nowTime.getTimePoint() + "] " + Placeholder.replaceVariables(log, "file_name", name) + "\n");
            } else {
                Time nowTime = new Time();
                String log = lang.getLang("Error_Imported");
                log_textarea.append("[" + nowTime.getTimePoint() + "] " + Placeholder.replaceVariables(log, "file_name", name) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
