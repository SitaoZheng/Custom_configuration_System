package data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static data.DataOperation.calculateTotal;
import static data.DataOperation.clearValue;

public class AutoCompleteCellEditor extends DefaultCellEditor {
    private List<List<String>> suggestions;
    private JPopupMenu popupMenu;
    private JTextField textField;
    private DataOperation dataOperation;

    // 构造函数
    public AutoCompleteCellEditor(List<List<String>> itemArray, JTable table_1, JTable table_2, DefaultTableModel tableModel_1) {
        super(new JTextField());
        this.suggestions = itemArray;
        this.textField = (JTextField) getComponent();
        this.popupMenu = new JPopupMenu();
        this.dataOperation = new DataOperation();

        // 为文本框添加键盘监听器
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = textField.getText();
                updatePopupMenu(input, tableModel_1, table_1, table_2);
                if (!popupMenu.isVisible() && input.length() > 0) {
                    // 如果弹出菜单不可见并且输入非空，则显示弹出菜单
                    popupMenu.setVisible(true);
                    popupMenu.show(textField, 0, textField.getHeight());
                    textField.requestFocusInWindow();
                } else if (input.isEmpty()) {
                    // 如果输入为空，则隐藏弹出菜单
                    popupMenu.setVisible(false);
                    int row = table_1.getSelectedRow();
                    if (row != -1) {
                        clearValue(row, table_1);
                        table_2.setValueAt(calculateTotal(table_1),0,1);
                    }
                }
            }
        });
    }

    // 更新弹出菜单内容
    public void updatePopupMenu(String input, DefaultTableModel tableModel_1, JTable table_1, JTable table_2) {
        popupMenu.removeAll();

        // 获取表格中调用popupMenu的行的第一列数据
        int selectedRow = table_1.getSelectedRow();
        if (selectedRow == -1) {
            return; // 如果没有选中行，则直接返回
        }
        String selectedTag = table_1.getValueAt(selectedRow, 0).toString();

        for (List<String> suggestionData : suggestions) {
            String tagInSuggestion = suggestionData.get(0);

            // 仅添加与表格中调用popupMenu的行的第一列数据匹配的建议项
            if (tagInSuggestion.equals(selectedTag) && suggestionData.size() > 1) {
                String suggestion = suggestionData.get(1);

                // 根据输入的字符过滤词条
                if (suggestion.toLowerCase().startsWith(input.toLowerCase())) {
                    JMenuItem item = new JMenuItem(suggestion);
                    item.addActionListener(e -> {
                        String selectedValue = ((JMenuItem) e.getSource()).getText();
                        int rowCount = tableModel_1.getRowCount();

                        // 添加焦点监听器，在文本框失去焦点时执行数据操作
                        textField.addFocusListener(new FocusAdapter() {
                            @Override
                            public void focusLost(FocusEvent e) {
                                for (int row = 0; row < rowCount; row++) {
                                    Object cellValue = tableModel_1.getValueAt(row, 1);
                                    if (cellValue != null && cellValue.toString().equals(selectedValue)) {
                                        tableModel_1.setValueAt(dataOperation.getValue(table_1, row, 1), row, 3);
                                        table_2.setValueAt(calculateTotal(table_1), 0, 1);
                                        break;
                                    }
                                }
                                textField.removeFocusListener(this); // 移除焦点监听器，确保只执行一次
                            }
                        });
                        textField.setText(selectedValue);
                        textField.requestFocusInWindow();
                    });
                    popupMenu.add(item);
                }
            }
        }

        // 设置弹出菜单的大小
        int width = Math.max(375, textField.getWidth());
        int itemHeight = 30;
        int maxHeight = 0;
        int popupHeight = Math.max(maxHeight, suggestions.size() * itemHeight);
        popupMenu.setPreferredSize(new Dimension(width, popupHeight));

        popupMenu.revalidate();
        popupMenu.repaint();
    }

    // 自动设置弹出菜单
    public void AutoPopupMenu(JTable table_1, JTable table_2, DefaultTableModel tableModel_1) {
        List<List<String>> itemArray = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            String tag = table_1.getValueAt(i, 0).toString();
            File filePath = new File("src/resource/data/database/" + tag + ".txt");

            if (filePath.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        int semicolonIndex = line.indexOf(";");
                        if (semicolonIndex != -1) {
                            List<String> rowData = new ArrayList<>();
                            rowData.add(tag);
                            rowData.add(line.substring(0, semicolonIndex));
                            rowData.add(line.substring(semicolonIndex + 1));
                            itemArray.add(rowData);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        table_1.getColumnModel().getColumn(1).setCellEditor(new AutoCompleteCellEditor(itemArray, table_1, table_2, tableModel_1));
    }
}
