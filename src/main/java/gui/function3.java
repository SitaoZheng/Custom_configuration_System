package gui;

import data.Request;
import data.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class function3 {
    public static JPanel function3(Font font_1, Font font_2, Font font_3, Font font_4, Font font_5) {
        JPanel function3 = new JPanel();

        // 查询类型
        JLabel typeQuery = new JLabel("Type Query");
        typeQuery.setFont(font_4);
        typeQuery.setForeground(new Color(Integer.parseInt("#8B008B".substring(1), 16)));
        typeQuery.setBounds(35, 70, 150, 30);
        function3.add(typeQuery);

        // 配件勾选
        JRadioButton hardware_Radio = new JRadioButton("Hardware");
        hardware_Radio.setFont(font_3);
        hardware_Radio.setForeground(Color.black);
        hardware_Radio.setBounds(175, 75, 100, 30);
        function3.add(hardware_Radio);

        JRadioButton item_Radio = new JRadioButton("Item");
        item_Radio.setFont(font_3);
        item_Radio.setForeground(Color.black);
        item_Radio.setBounds(285, 75, 61, 30);
        function3.add(item_Radio);

        JRadioButton value_Radio = new JRadioButton("Value");
        value_Radio.setFont(font_3);
        value_Radio.setForeground(Color.black);
        value_Radio.setBounds(353, 75, 70, 30);
        function3.add(value_Radio);
        // 创建按钮组
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(hardware_Radio);
        radioButtonGroup.add(item_Radio);
        radioButtonGroup.add(value_Radio);

        JTextField text = new JTextField();
        text.setBounds(445, 75, 465, 35);
        text.setFont(font_4);
        text.setForeground(Color.black);
        function3.add(text);

        // 创建表格
        String[] columnNames = {"Hardware", "Item", "Value"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        // 设置单元格居中对齐
        DefaultTableCellRenderer centerRenderer_1 = new DefaultTableCellRenderer();
        centerRenderer_1.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer_1);
        // 设置表格字体
        table.setFont(font_4);
        // 设置表头字体
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(font_3);
        // 设置表格文字颜色
        table.setForeground(Color.BLACK);
        // 设置表格背景颜色
        table.setBackground(Color.WHITE);
        // 设置表头文字颜色
        tableHeader.setForeground(Color.WHITE);
        // 设置表头背景颜色
        tableHeader.setBackground(new Color(Integer.parseInt("#4169E1".substring(1), 16)));
        // 设置行高
        table.setRowHeight(30);
        // 设置列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(450);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        // 设置单独选择
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        // 禁止全表格选中
        table.setCellSelectionEnabled(false);
        // 设置不可编辑
        table.setEnabled(false);
        // 滚动条
        JScrollPane scrollPane_1 = new JScrollPane(table);
        scrollPane_1.setBounds(35, 125, 650, 357);
        function3.add(scrollPane_1);

        // log
        JTextArea log_textarea = new JTextArea();
        JScrollPane textAreaScrollPane = new JScrollPane(log_textarea);
        log_textarea.setFont(font_2);
        log_textarea.setForeground(new Color(Integer.parseInt("#4B0082".substring(1), 16)));
        log_textarea.setEditable(false);
        log_textarea.setLineWrap(true);
        log_textarea.setWrapStyleWord(true);
        textAreaScrollPane.setBounds(710, 150, 200, 245);
        function3.add(textAreaScrollPane);

        // 查询按钮
        JButton request_button = new JButton("Request");
        request_button.setBounds(710, 425, 200, 35);
        request_button.setFont(font_4);
        request_button.setForeground(new Color(Integer.parseInt("#32CD32".substring(1), 16)));
        request_button.setBackground(Color.white);
        function3.add(request_button);

        // 按钮监听
        // 添加ItemListener来监听选择变化
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // 根据选中的JRadioButton获取文本并处理
                    String selectedText = ((JRadioButton) e.getSource()).getText();
                    // 移除之前的ActionListener
                    ActionListener[] actionListeners = request_button.getActionListeners();
                    for (ActionListener listener : actionListeners) {
                        request_button.removeActionListener(listener);
                    }
                    // 查询按钮
                    request_button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            clearTable(tableModel);
                            Time nowTime;
                            try {
                                nowTime = new Time();
                                //获取输入
                                String input_text = text.getText();
                                String logMessage = null; // 用于保存日志消息
                                if (input_text.isEmpty()) {
                                    log_textarea.append("[" + nowTime.getTimePoint() + "] " + "Please enter the info.\n");
                                } else {
                                    Request request = new Request();

                                    if (request.getItem(input_text, selectedText).isEmpty()) {
                                        logMessage="[" + nowTime.getTimePoint() + "] " + " No data was retrieved for " + "\"" + input_text + "\"" + "\n";
                                    }
                                    else {
                                        // 遍历 dataArray 并将数据添加到表格模型中
                                        for (List<String> rowData : request.getItem(input_text, selectedText)) {
                                            tableModel.addRow(rowData.toArray());
                                        }
                                        tableModel.fireTableDataChanged();

                                        logMessage ="[" + nowTime.getTimePoint() + "] " + "\"" + input_text.toUpperCase() + "\"" + " has been requested.\n";
                                    }
                                }
                                log_textarea.append(logMessage);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        };
        // 查询按钮
        request_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Time nowTime;
                try {
                    nowTime = new Time();
                    if (!hardware_Radio.isSelected() && !item_Radio.isSelected() && !value_Radio.isSelected()) {
                        log_textarea.append("[" + nowTime.getTimePoint() + "] " + "Please select a choice.\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        hardware_Radio.addItemListener(itemListener);
        item_Radio.addItemListener(itemListener);
        value_Radio.addItemListener(itemListener);
        return function3;
    }
    private static void clearTable(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }
}
