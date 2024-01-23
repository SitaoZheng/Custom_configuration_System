package gui;

import data.AutoCompleteCellEditor;
import data.Export;
import data.Import;
import data.Time;
import lang.LanguageManager;
import unit.Placeholder;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static data.DataOperation.calculateTotal;

public class function1 {
    public static JPanel function1(Font font_1, Font font_2, Font font_3, Font font_4, Font font_5) {
        LanguageManager lang = new LanguageManager();

        JPanel function1 = new JPanel();
        // 创建表格-1
        String[] columnNames_1 = {lang.getLang("Hardware"), lang.getLang("Item"), lang.getLang("Num"), lang.getLang("Value")};
        DefaultTableModel tableModel_1 = new DefaultTableModel(columnNames_1, 8) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // 使第一列和第三列不可编辑
                return column != 0;
            }
        };
        JTable table_1 = new JTable(tableModel_1);
        // 设置配件
        tableModel_1.setValueAt(lang.getLang("CPU"), 0, 0);
        tableModel_1.setValueAt(lang.getLang("MB"), 1, 0);
        tableModel_1.setValueAt(lang.getLang("RAM"), 2, 0);
        tableModel_1.setValueAt(lang.getLang("HDD"), 3, 0);
        tableModel_1.setValueAt(lang.getLang("GPU"), 4, 0);
        tableModel_1.setValueAt(lang.getLang("PSU"), 5, 0);
        tableModel_1.setValueAt(lang.getLang("HSF"), 6, 0);
        tableModel_1.setValueAt(lang.getLang("Case"), 7, 0);
        // 设置单元格居中对齐
        DefaultTableCellRenderer centerRenderer_1 = new DefaultTableCellRenderer();
        centerRenderer_1.setHorizontalAlignment(JLabel.CENTER);
        table_1.setDefaultRenderer(Object.class, centerRenderer_1);
        // 设置表格字体
        table_1.setFont(font_4);
        // 设置表头字体
        JTableHeader tableHeader = table_1.getTableHeader();
        tableHeader.setFont(font_3);
        // 设置表格文字颜色
        table_1.setForeground(Color.BLACK);
        // 设置表格背景颜色
        table_1.setBackground(Color.WHITE);
        // 设置表头文字颜色
        tableHeader.setForeground(Color.WHITE);
        // 设置表头背景颜色
        tableHeader.setBackground(new Color(Integer.parseInt("#4169E1".substring(1), 16)));
        // 设置行高
        table_1.setRowHeight(30);
        // 设置列宽
        table_1.getColumnModel().getColumn(0).setPreferredWidth(100);
        table_1.getColumnModel().getColumn(1).setPreferredWidth(375);
        table_1.getColumnModel().getColumn(2).setPreferredWidth(50);
        table_1.getColumnModel().getColumn(3).setPreferredWidth(125);
        // 设置单独选择
        table_1.setRowSelectionAllowed(true);
        table_1.setColumnSelectionAllowed(true);
        // 禁止全表格选中
        table_1.setCellSelectionEnabled(false);

        // 创建表格-2
        String[] columnNames_2 = {"", ""};
        DefaultTableModel tableModel_2 = new DefaultTableModel(columnNames_2, 1);
        JTable table_2 = new JTable(tableModel_2);
        // 添加"total"
        tableModel_2.setValueAt(lang.getLang("TOTAL"), 0, 0);
        // 设置单元格居中对齐
        DefaultTableCellRenderer centerRenderer_2 = new DefaultTableCellRenderer();
        centerRenderer_2.setHorizontalAlignment(JLabel.CENTER);
        table_2.setDefaultRenderer(Object.class, centerRenderer_2);
        // 设置表格字体
        table_2.setFont(font_4);
        // 设置表格文字颜色
        table_2.setForeground(Color.BLACK);
        // 设置表格背景颜色
        table_2.setBackground(Color.WHITE);
        // 设置行高
        table_2.setRowHeight(30);
        // 设置列宽
        table_2.getColumnModel().getColumn(0).setPreferredWidth(525);
        table_2.getColumnModel().getColumn(1).setPreferredWidth(125);
        // 设置单独选择
        table_2.setRowSelectionAllowed(true);
        table_2.setColumnSelectionAllowed(true);
        // 移除表头
        table_2.setTableHeader(null);
        // 禁止全表格选中
        table_2.setCellSelectionEnabled(false);
        // 设置不可修改
        table_2.setEnabled(false);

        // 添加备注输入框
        JTextArea ps = new JTextArea();
        ps.setFont(font_4);
        ps.setForeground(Color.black);
        // 自动换行
        ps.setLineWrap(true);
        // 按照单词边界换行
        ps.setWrapStyleWord(true);

        // 设置表格-1位置
        JScrollPane scrollPane_1 = new JScrollPane(table_1);
        scrollPane_1.setBounds(35, 55, 650, 267);
        function1.add(scrollPane_1);
        // 设置表格-2位置
        JScrollPane scrollPane_2 = new JScrollPane(table_2);
        scrollPane_2.setBounds(35, 321, 650, 33);
        function1.add(scrollPane_2);
        // 设置ps位置
        JScrollPane scrollPane_3 = new JScrollPane(ps);
        scrollPane_3.setBounds(35, 353, 650, 120);
        function1.add(scrollPane_3);

        JLabel fileTag = new JLabel(lang.getLang("Excel_File") + ":");
        fileTag.setFont(font_3);
        fileTag.setForeground(Color.black);
        fileTag.setBounds(710, 55, 200, 15);
        function1.add(fileTag);

        JTextField fileName = new JTextField();
        fileName.setBounds(710, 75, 200, 35);
        fileName.setFont(font_4);
        fileName.setForeground(Color.black);
        function1.add(fileName);

        JButton import_button = new JButton(lang.getLang("Import"));
        import_button.setBounds(710, 120, 200, 35);
        import_button.setFont(font_4);
        import_button.setForeground(new Color(Integer.parseInt("#32CD32".substring(1), 16)));
        import_button.setBackground(Color.white);
        function1.add(import_button);

        JTextArea log_textarea = new JTextArea();
        JScrollPane textAreaScrollPane = new JScrollPane(log_textarea);
        log_textarea.setFont(font_2);
        log_textarea.setForeground(new Color(Integer.parseInt("#4B0082".substring(1), 16)));
        log_textarea.setEditable(false);
        log_textarea.setLineWrap(true);
        log_textarea.setWrapStyleWord(true);
        textAreaScrollPane.setBounds(710, 210, 200, 215);
        function1.add(textAreaScrollPane);

        JButton export_button = new JButton(lang.getLang("Export"));
        export_button.setBounds(710, 165, 200, 35);
        export_button.setFont(font_4);
        export_button.setForeground(new Color(Integer.parseInt("#DAA520".substring(1), 16)));
        export_button.setBackground(Color.white);
        function1.add(export_button);

        JButton reset_button = new JButton(lang.getLang("Reset"));
        reset_button.setBounds(710, 435, 200, 35);
        reset_button.setFont(font_4);
        reset_button.setForeground(new Color(Integer.parseInt("#8B0000".substring(1), 16)));
        reset_button.setBackground(Color.white);
        function1.add(reset_button);

        //自动补全索引
        AutoCompleteCellEditor autoCompleteCellEditor = new AutoCompleteCellEditor(new ArrayList<>(), table_1, table_2, tableModel_1);
        autoCompleteCellEditor.AutoPopupMenu(table_1, table_2, tableModel_1);

        function1.setVisible(true);

        // 按钮监听
        // Import 监听
        import_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Time nowTime;
                try {
                    nowTime = new Time();
                    // 获取文件名
                    String file = fileName.getText();
                    if (file.isEmpty()) {
                        log_textarea.append("[" + nowTime.getTimePoint() + "] " + lang.getLang("Tip_Enter_File_Name") + "\n");
                    } else {
                        Import Import = new Import();
                        Import.Import(file, log_textarea, tableModel_1, tableModel_2, ps);
                        // 更新表格数据后，再次设置列宽
                        table_1.getColumnModel().getColumn(0).setPreferredWidth(100);
                        table_1.getColumnModel().getColumn(1).setPreferredWidth(375);
                        table_1.getColumnModel().getColumn(2).setPreferredWidth(50);
                        table_1.getColumnModel().getColumn(3).setPreferredWidth(125);
                        tableModel_1.fireTableDataChanged();
                        tableModel_2.fireTableDataChanged();

                        AutoCompleteCellEditor autoCompleteCellEditor = new AutoCompleteCellEditor(new ArrayList<>(),table_1,table_2,tableModel_1);
                        autoCompleteCellEditor.AutoPopupMenu(table_1, table_2, tableModel_1);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Export 监听
        export_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Time nowTime;
                //获取文件名
                String file = fileName.getText();
                nowTime = new Time();
                if (file.isEmpty()) {
                    log_textarea.append("[" + nowTime.getTimePoint() + "] " + lang.getLang("Tip_Enter_File_Name") + "\n");
                } else {
                    Export export = new Export();
                    String total = table_2.getValueAt(0, 1).toString();
                    String ps_txt = ps.getText();

                    export.Export(log_textarea, table_1, file, total, ps_txt, table_2);
                }
            }
        });
        // 更新total监听
        tableModel_1.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 判断变化是否发生在第三四列
                if (e.getColumn() == 2 || e.getColumn() == 3) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    String value = String.valueOf(tableModel_1.getValueAt(row, column));
                    // 检查值是否为null
                    if (!Objects.equals(value, "")) {
                        try {
                            // 尝试将值转换为double类型
                            Double.parseDouble(value);
                            table_2.setValueAt(calculateTotal(table_1), 0, 1);
                        } catch (NumberFormatException ex) {
                            // 输出数据类型错误的信息到JTextArea
                            Time nowTime = new Time();
                            String log = lang.getLang("Error_Wrong_Data_Type");
                            log_textarea.append("[" + nowTime.getTimePoint() + "] " + Placeholder.replaceVariables(log, "row", String.valueOf(row + 1), "column", String.valueOf(column + 1)) + "\n");
                        }
                    }
                }
            }
        });
        // Reset 监听
        reset_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < tableModel_1.getRowCount(); i++) {
                    for (int j = 1; j <= 3; j++) {
                        tableModel_1.setValueAt("", i, j);
                    }
                }
                tableModel_2.setValueAt("", 0, 1);
                ps.setText("");

                Time nowTime = new Time();
                log_textarea.append("[" + nowTime.getTimePoint() + "] " + lang.getLang("Tip_Reset") + "\n");
            }
        });
        return function1;
    }

    public static boolean isValidDouble(String str) {
        // 使用正则表达式检查是否是有效的Double
        String doublePattern = "^\\d*\\.?\\d+$";
        Pattern pattern = Pattern.compile(doublePattern);
        Matcher matcher = pattern.matcher(str);

        // 检查是否匹配并且不是以小数点结尾的情况
        return matcher.matches() && !str.endsWith(".");
    }
}
