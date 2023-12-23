package data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Export {
    public void Export(JTextArea log_textarea, JTable table_1, String file, String CPU, String MB, String RAM, String HDD, String GPU, String PSU, String HSF, String Case, String total, String ps) {
        String name = file + ".xlsx";
        DataOperation dataOperation = new DataOperation();

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
                String[] columns = {"Hardware", "Item", "Value"};
                for (int i = 0; i < 3; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                    cell.setCellStyle(headerStyle);
                }

                // 添加数据
                Map<String, Map<String, Double>> dataMap = new HashMap<>();
                // CPU
                Map<String, Double> cpuData = new HashMap<>();
                cpuData.put(CPU, Double.parseDouble(dataOperation.getValue(table_1, 0)));
                dataMap.put("CPU", cpuData);
                // MB
                Map<String, Double> mbData = new HashMap<>();
                mbData.put(MB, Double.parseDouble(dataOperation.getValue(table_1, 1)));
                dataMap.put("MB", mbData);
                // RAM
                Map<String, Double> ramData = new HashMap<>();
                ramData.put(RAM, Double.parseDouble(dataOperation.getValue(table_1, 2)));
                dataMap.put("RAM", ramData);
                // HDD
                Map<String, Double> hddData = new HashMap<>();
                hddData.put(HDD, Double.parseDouble(dataOperation.getValue(table_1, 3)));
                dataMap.put("HDD", hddData);
                // GPU
                Map<String, Double> gpuData = new HashMap<>();
                gpuData.put(GPU, Double.parseDouble(dataOperation.getValue(table_1, 4)));
                dataMap.put("GPU", gpuData);
                // PSU
                Map<String, Double> psuData = new HashMap<>();
                psuData.put(PSU, Double.parseDouble(dataOperation.getValue(table_1, 5)));
                dataMap.put("PSU", psuData);
                // HSF
                Map<String, Double> hsfData = new HashMap<>();
                hsfData.put(HSF, Double.parseDouble(dataOperation.getValue(table_1, 6)));
                dataMap.put("HSF", hsfData);
                // Case
                Map<String, Double> caseData = new HashMap<>();
                caseData.put(Case, Double.parseDouble(dataOperation.getValue(table_1, 7)));
                dataMap.put("Case", caseData);

                // 将数据写入表格
                dataToExcel(dataMap, folderPath.resolve(name).toString(), headerStyle, hardwareStyle, dataStyle, totalStyle, psStyle, sheet, total, ps);

                Time nowTime = new Time();
                log_textarea.append("[" + nowTime.getTimePoint() + "] " + name + " is successfully exported.\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dataToExcel(Map<String, Map<String, Double>> data, String filePath, CellStyle headerStyle, CellStyle hardwareStyle, CellStyle dataStyle, CellStyle totalStyle, CellStyle psStyle, Sheet sheet, String total, String ps) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            // 创建表头
            Row headerRow = sheet.getRow(0);
            for (int i = 0; i < 3; i++) {
                Cell cell = headerRow.getCell(i);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (Map.Entry<String, Map<String, Double>> entry : data.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                // 在第一列添加类型
                Cell typeCell = row.createCell(colNum++);
                typeCell.setCellValue(entry.getKey());
                typeCell.setCellStyle(hardwareStyle);

                // 在后续列添加产品数据
                for (Map.Entry<String, Double> productEntry : entry.getValue().entrySet()) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(productEntry.getKey());
                    cell.setCellStyle(dataStyle);

                    cell = row.createCell(colNum++);
                    cell.setCellValue(productEntry.getValue());
                    cell.setCellStyle(dataStyle);
                }
            }

            // 调整列宽
            for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
                sheet.autoSizeColumn(i);
            }

            // 合并total单元格 (A10:B10)
            CellRangeAddress mergedRegion1 = new CellRangeAddress(9, 9, 0, 1);
            sheet.addMergedRegion(mergedRegion1);
            Row mergedRow1 = sheet.getRow(9);
            if (mergedRow1 == null) {
                mergedRow1 = sheet.createRow(9);
            }
            Cell mergedCell1 = mergedRow1.createCell(0);
            mergedCell1.setCellValue("Total");
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

            // 合并ps单元格 (A11:C12)
            CellRangeAddress mergedRegion2 = new CellRangeAddress(10, 11, 0, 2);
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
