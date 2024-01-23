package data;

import lang.LanguageManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import unit.Placeholder;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Export {
    private static void dataToExcel(Map<String, Map<String, Map<Integer, Double>>> data, String filePath, CellStyle headerStyle, CellStyle hardwareStyle, CellStyle dataStyle, CellStyle totalStyle, CellStyle psStyle, Sheet sheet, String total, String ps, JTable table_2) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            LanguageManager lang = new LanguageManager();
            // 创建表头
            Row headerRow = sheet.getRow(0);
            for (int i = 0; i < 4; i++) {
                Cell cell = headerRow.getCell(i);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (Map.Entry<String, Map<String, Map<Integer, Double>>> entry : data.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                // 在第一列添加类型
                Cell typeCell = row.createCell(colNum++);
                typeCell.setCellValue(entry.getKey());
                typeCell.setCellStyle(hardwareStyle);

                // 在后续列添加产品数据
                for (Map.Entry<String, Map<Integer, Double>> productEntry : entry.getValue().entrySet()) {
                    for (Map.Entry<Integer, Double> valueEntry : productEntry.getValue().entrySet()) {
                        Cell cell = row.createCell(colNum++);
                        cell.setCellValue(productEntry.getKey());
                        cell.setCellStyle(dataStyle);

                        cell = row.createCell(colNum++);
                        cell.setCellValue(valueEntry.getKey());
                        cell.setCellStyle(dataStyle);

                        cell = row.createCell(colNum++);
                        cell.setCellValue(valueEntry.getValue());
                        cell.setCellStyle(dataStyle);
                    }
                }
            }

            // 调整列宽
            for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
                sheet.autoSizeColumn(i);
                // 设置“数量”列的最小宽度为4
                if (i == 2 && sheet.getColumnWidth(i) < 5 * 256) {
                    sheet.setColumnWidth(i, 5 * 256);
                }
            }

            // 合并total单元格 (A10:C10)
            CellRangeAddress mergedRegion1 = new CellRangeAddress(9, 9, 0, 2);
            sheet.addMergedRegion(mergedRegion1);
            Row mergedRow1 = sheet.getRow(9);
            if (mergedRow1 == null) {
                mergedRow1 = sheet.createRow(9);
            }
            Cell mergedCell1 = mergedRow1.createCell(0);
            mergedCell1.setCellValue((String) table_2.getValueAt(0, 0));
            mergedCell1.setCellStyle(totalStyle);

            // 对合并区域内的每个单元格应用边框
            for (int i = mergedRegion1.getFirstRow(); i <= mergedRegion1.getLastRow(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }
                for (int j = mergedRegion1.getFirstColumn(); j <= mergedRegion1.getLastColumn(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        cell = row.createCell(j);
                    }
                    cell.setCellStyle(totalStyle);
                }
            }

            // 获取合并后的单元格的右边一格（假设在合并后的单元格的右边添加数据）
            Cell nextCell1 = mergedRow1.createCell(mergedRegion1.getLastColumn() + 1);
            nextCell1.setCellValue(Double.parseDouble(total));
            nextCell1.setCellStyle(dataStyle);

            // 合并ps单元格 (A11:D12)
            CellRangeAddress mergedRegion2 = new CellRangeAddress(10, 11, 0, 3);
            sheet.addMergedRegion(mergedRegion2);
            Row mergedRow2 = sheet.getRow(10);
            if (mergedRow2 == null) {
                mergedRow2 = sheet.createRow(10);
            }
            Cell mergedCell2 = mergedRow2.createCell(0);
            mergedCell2.setCellValue(ps);
            mergedCell2.setCellStyle(psStyle);

            // 对合并区域内的每个单元格应用边框
            for (int i = mergedRegion2.getFirstRow(); i <= mergedRegion2.getLastRow(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    row = sheet.createRow(i);
                }
                for (int j = mergedRegion2.getFirstColumn(); j <= mergedRegion2.getLastColumn(); j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        cell = row.createCell(j);
                    }
                    cell.setCellStyle(psStyle);
                }
            }

            // 写入文件
            sheet.getWorkbook().write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Export(JTextArea log_textarea, JTable table_1, String file, String total, String ps, JTable table_2) {
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
                        // 判断是否为 Excel 文件名
                        return fileName.equals(name);
                    });

            if (state) {
                Path filepath = folderPath.resolve(name);
                Files.delete(filepath);
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                // 创建一个工作表
                Sheet sheet = workbook.createSheet("sheet");

                // 创建加粗字体
                Font boldFont = workbook.createFont();
                boldFont.setBold(true);

                // 创建表头样式
                CellStyle headerStyle = workbook.createCellStyle();
                // 格式化
                setStyle(headerStyle,boldFont);
                // 填充
                headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // 创建分类样式
                CellStyle hardwareStyle = workbook.createCellStyle();
                // 格式化
                setStyle(hardwareStyle,boldFont);
                // 填充
                hardwareStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
                hardwareStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // 创建数据样式
                CellStyle dataStyle = workbook.createCellStyle();
                // 格式化
                setStyle(dataStyle,boldFont);

                // 创建total样式
                CellStyle totalStyle = workbook.createCellStyle();
                // 格式化
                setStyle(totalStyle,boldFont);
                // 填充
                totalStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                totalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // 创建ps样式
                CellStyle psStyle = workbook.createCellStyle();
                // 格式化
                setStyle(psStyle,boldFont);
                // 填充
                psStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
                psStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // 创建表头
                Row headerRow = sheet.createRow(0);
                String[] columns = {lang.getLang("Hardware"), lang.getLang("Item"), lang.getLang("Num"), lang.getLang("Value")};
                for (int i = 0; i < 4; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerStyle);
                }

                // 添加数据
                Map<String, Map<String, Map<Integer, Double>>> dataMap = new HashMap<>();

                // 获取表格行数
                int rowCount = table_1.getRowCount();
                // 循环处理每一行数据
                for (int i = 0; i < rowCount; i++) {
                    // 获取Hardware、Item、Num、Value的值
                    String hardware = (String) table_1.getValueAt(i, 0);
                    String item = (String) table_1.getValueAt(i, 1);
                    Integer num = Integer.valueOf(table_1.getValueAt(i, 2).toString());
                    Double value = Double.valueOf(table_1.getValueAt(i, 3).toString());

                    // 如果dataMap中已经存在该Hardware，则获取其对应的Map
                    Map<String, Map<Integer, Double>> hardwareMap = dataMap.getOrDefault(hardware, new HashMap<>());

                    // 如果hardwareMap中已经存在该Item，则获取其对应的Map
                    Map<Integer, Double> itemMap = hardwareMap.getOrDefault(item, new HashMap<>());

                    // 将Num和Value存入itemMap
                    itemMap.put(num, value);

                    // 将itemMap存入hardwareMap
                    hardwareMap.put(item, itemMap);

                    // 将hardwareMap存入dataMap
                    dataMap.put(hardware, hardwareMap);
                }


                // 将数据写入表格
                dataToExcel(dataMap, folderPath.resolve(name).toString(), headerStyle, hardwareStyle, dataStyle, totalStyle, psStyle, sheet, total, ps, table_2);

                Time nowTime = new Time();
                String log = lang.getLang("Tip_Exported");
                log_textarea.append("[" + nowTime.getTimePoint() + "] " + Placeholder.replaceVariables(log, "file_name", name) + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static CellStyle setStyle(CellStyle cellStyle, Font boldFont){
        // 居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 自动换行
        cellStyle.setWrapText(true);
        // 加粗
        cellStyle.setFont(boldFont);
        // 加框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        return cellStyle;
    }
}
