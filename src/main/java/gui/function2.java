package gui;

import data.AddData;
import data.DeleteData;
import data.ModifyData;
import data.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class function2 {
    public static JPanel function2(Font font_1, Font font_2, Font font_3, Font font_4, Font font_5) {
        JPanel function2 = new JPanel();

        // 添加/删除
        // top
        JLabel addDelete_Tag_1 = new JLabel("Add / Delete");
        addDelete_Tag_1.setFont(font_5);
        addDelete_Tag_1.setForeground(new Color(Integer.parseInt("#008080".substring(1), 16)));
        addDelete_Tag_1.setBounds(35, 70, 165, 30);
        function2.add(addDelete_Tag_1);
        // 配件
        JLabel hardware_Tag_1 = new JLabel("Hardware:");
        hardware_Tag_1.setFont(font_3);
        hardware_Tag_1.setForeground(Color.black);
        hardware_Tag_1.setBounds(35, 106, 135, 25);
        function2.add(hardware_Tag_1);
        // 配件输入框
        UIManager.put("ComboBox.selectionBackground", new Color(Integer.parseInt("#4169E1".substring(1), 16)));
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        JComboBox<String> choi_1 = new JComboBox<>(new String[]{"","CPU", "MB", "MAR", "HDD", "GPU", "PSU", "HSF", "Case"});
        choi_1.setBounds(120, 105, 80, 30);
        choi_1.setFont(font_3);
        choi_1.setForeground(new Color(Integer.parseInt("#1E90FF".substring(1), 16)));
        choi_1.setBackground(Color.WHITE);
        function2.add(choi_1);
        // 名称
        JLabel item_Tag_1 = new JLabel("Item:");
        item_Tag_1.setFont(font_3);
        item_Tag_1.setForeground(Color.black);
        item_Tag_1.setBounds(220, 106, 135, 25);
        function2.add(item_Tag_1);
        // 物品输入框
        JTextField item_1 = new JTextField();
        item_1.setBounds(265, 105, 460, 30);
        item_1.setFont(font_3);
        item_1.setForeground(new Color(Integer.parseInt("#1E90FF".substring(1), 16)));
        function2.add(item_1);
        // 价格
        JLabel value_Tag_1 = new JLabel("Value:");
        value_Tag_1.setFont(font_3);
        value_Tag_1.setForeground(Color.black);
        value_Tag_1.setBounds(745, 106, 135, 25);
        function2.add(value_Tag_1);
        // 价格输入框
        JTextField value_1 = new JTextField();
        value_1.setBounds(800, 105, 100, 30);
        value_1.setFont(font_3);
        value_1.setForeground(new Color(Integer.parseInt("#1E90FF".substring(1), 16)));
        function2.add(value_1);

        //修改
        JLabel modify_Tag = new JLabel("Modify");
        modify_Tag.setFont(font_5);
        modify_Tag.setForeground(new Color(Integer.parseInt("#008080".substring(1), 16)));
        modify_Tag.setBounds(65, 158, 165, 35);
        function2.add(modify_Tag);
        // 名称
        JLabel item_Tag_2 = new JLabel("Item:");
        item_Tag_2.setFont(font_3);
        item_Tag_2.setForeground(Color.black);
        item_Tag_2.setBounds(220, 166, 135, 25);
        function2.add(item_Tag_2);
        // 物品输入框
        JTextField item_2 = new JTextField();
        item_2.setBounds(265, 165, 460, 30);
        item_2.setFont(font_3);
        item_2.setForeground(new Color(Integer.parseInt("#1E90FF".substring(1), 16)));
        function2.add(item_2);
        // 价格
        JLabel value_Tag_2 = new JLabel("Value:");
        value_Tag_2.setFont(font_3);
        value_Tag_2.setForeground(Color.black);
        value_Tag_2.setBounds(745, 166, 135, 25);
        function2.add(value_Tag_2);
        // 价格输入框
        JTextField value_2 = new JTextField();
        value_2.setBounds(800, 165, 100, 30);
        value_2.setFont(font_3);
        value_2.setForeground(new Color(Integer.parseInt("#1E90FF".substring(1), 16)));
        function2.add(value_2);

        // 添加按钮
        JButton add_button = new JButton("Add");
        add_button.setBounds(710, 350, 200, 35);
        add_button.setFont(font_4);
        add_button.setForeground(new Color(Integer.parseInt("#008000".substring(1), 16)));
        add_button.setBackground(Color.white);
        function2.add(add_button);

        // 删除按钮
        JButton delete_button = new JButton("Delete");
        delete_button.setBounds(710, 400, 200, 35);
        delete_button.setFont(font_4);
        delete_button.setForeground(new Color(Integer.parseInt("#B22222".substring(1), 16)));
        delete_button.setBackground(Color.white);
        function2.add(delete_button);

        // 修改按钮
        JButton modify_button = new JButton("Modify");
        modify_button.setBounds(710, 450, 200, 35);
        modify_button.setFont(font_4);
        modify_button.setForeground(new Color(Integer.parseInt("#DAA520".substring(1), 16)));
        modify_button.setBackground(Color.white);
        function2.add(modify_button);

        // log
        JTextArea log_textarea = new JTextArea();
        JScrollPane textAreaScrollPane = new JScrollPane(log_textarea);
        log_textarea.setFont(font_2);
        log_textarea.setForeground(new Color(Integer.parseInt("#4B0082".substring(1), 16)));
        log_textarea.setEditable(false);
        log_textarea.setLineWrap(true);
        log_textarea.setWrapStyleWord(true);
        textAreaScrollPane.setBounds(35, 240, 650, 245);
        function2.add(textAreaScrollPane);

        // 按钮监听
        // 添加按钮
        add_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Time nowTime;
                try {
                    nowTime = new Time();
                    //获取输入
                    String hardware_1_Name = (String) choi_1.getSelectedItem();
                    String item_1_Name = item_1.getText();
                    String value_1_Name = value_1.getText();
                    if (hardware_1_Name.isEmpty() || item_1_Name.isEmpty() || value_1_Name.isEmpty()) {
                        log_textarea.append("[" + nowTime.getTimePoint() + "] " + "Please enter the information on the first line.\n");
                    } else {
                        AddData addData = new AddData();
                        addData.add(log_textarea,hardware_1_Name,item_1_Name,value_1_Name);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 删除按钮
        delete_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Time nowTime;
                try {
                    nowTime = new Time();
                    //获取输入
                    String hardware_1_Name = (String) choi_1.getSelectedItem();
                    String item_1_Name = item_1.getText();
                    if (hardware_1_Name.isEmpty() || item_1_Name.isEmpty()) {
                        log_textarea.append("[" + nowTime.getTimePoint() + "] " + "Please enter the first two information of the first line.\n");
                    } else {
                        DeleteData deleteData = new DeleteData();
                        deleteData.delete(log_textarea,hardware_1_Name,item_1_Name);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 修改按钮
        modify_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Time nowTime;
                try {
                    nowTime = new Time();
                    //获取输入
                    String hardware_1_Name = (String) choi_1.getSelectedItem();
                    String item_1_Name = item_1.getText();
                    String value_1_Name = value_1.getText();
                    String item_2_Name = item_2.getText();
                    String value_2_Name = value_2.getText();

                    if (hardware_1_Name.isEmpty() || item_1_Name.isEmpty() || value_1_Name.isEmpty() || item_2_Name.isEmpty() || value_2_Name.isEmpty()) {
                        if (hardware_1_Name.isEmpty() || item_1_Name.isEmpty() || value_1_Name.isEmpty()) {
                            if (item_2_Name.isEmpty() || value_2_Name.isEmpty()) {
                                log_textarea.append("[" + nowTime.getTimePoint() + "] " + "Please enter the information.\n");
                            } else {
                                log_textarea.append("[" + nowTime.getTimePoint() + "] " + "Please enter the information on the first line.\n");
                            }
                        } else if (item_2_Name.isEmpty() || value_2_Name.isEmpty()) {
                            log_textarea.append("[" + nowTime.getTimePoint() + "] " + "Please enter the information on the second line.\n");
                        }
                    } else {
                        ModifyData modifyData = new ModifyData();
                        modifyData.modify(log_textarea,hardware_1_Name,item_1_Name,value_1_Name,item_2_Name,value_2_Name);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        return function2;
    }
}
