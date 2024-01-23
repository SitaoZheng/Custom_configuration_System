package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Function extends JFrame {
    public Function() {
        setTitle("Client System    -by Sitao Zheng & Yiming Yang & Jiapeng Wu");
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // 设置窗口图标
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Resource/ima/icon.png");
        setIconImage(icon);

        // 创建字体
        Font font_1 = new Font("微软雅黑", Font.BOLD, 14);
        Font font_2 = new Font("微软雅黑", Font.BOLD, 10);
        Font font_3 = new Font("微软雅黑", Font.BOLD, 15);
        Font font_4 = new Font("微软雅黑", Font.BOLD, 20);
        Font font_5 = new Font("微软雅黑", Font.BOLD, 25);

        // 创建选项卡面板
        JTabbedPane tabbedPane = new JTabbedPane();
        JLabel function1_tab = new JLabel("Input item name, value be filled automatically. Input the export (import) file name in the \"file name\"; You can add comments below.");
        //1：Fill in the name of the item and automatically fill in the value according to the data in the database
        //2：Enter the filename of the export (import) in the "filename" field. Comments can be made below the total
        function1_tab.setFont(font_1);
        function1_tab.setForeground(new Color(Integer.parseInt("#800000".substring(1), 16)));
        tabbedPane.addTab("Function 1", function1_tab);

        JLabel function2_tab = new JLabel("Data add/Delete, first line is required; for modification, contents in matching data are updated based on values in second row.");
        //1：Adding (removing) data requires only the first line.
        //2:Modification Based on the contents of the first row, the contents in the matching database are modified to the contents of the second row
        function2_tab.setFont(font_1);
        function2_tab.setForeground(new Color(Integer.parseInt("#800000".substring(1), 16)));
        tabbedPane.addTab("Function 2", function2_tab);

        JLabel function3_tab = new JLabel("Select the query type and search for all relevant data based on the keywords you entered.");
        function3_tab.setFont(font_1);
        function3_tab.setForeground(new Color(Integer.parseInt("#800000".substring(1), 16)));
        tabbedPane.addTab("Function 3", function3_tab);
        tabbedPane.setBounds(0, 0, getWidth(), 55);
        add(tabbedPane);

        // 创建 Function-1 的面板
        JPanel function_1 = function1.function1(font_1, font_2, font_3, font_4, font_5);
        function_1.setLayout(null);
        function_1.setVisible(true);
        // 调用 Function-1 的面板
        function_1.setBounds(0, 0, getWidth(), getHeight());
        add(function_1);

        // 创建 Function-2 的面板
        JPanel function_2 = function2.function2(font_1, font_2, font_3, font_4, font_5);
        function_2.setLayout(null);
        function_2.setVisible(false);
        // 调用 Function-2 的面板
        function_2.setBounds(0, 0, getWidth(), getHeight());
        add(function_2);

        // 创建 Function-3 的面板
        JPanel function_3 = function3.function3(font_1, font_2, font_3, font_4, font_5);
        function_3.setLayout(null);
        function_3.setVisible(false);
        // 调用 Function-3 的面板
        function_3.setBounds(0, 0, getWidth(), getHeight());
        add(function_3);

        // 选项卡监听
        // 设置选项卡切换监听器
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // 获取当前选中的选项卡索引
                int selectedIndex = tabbedPane.getSelectedIndex();
                // 根据索引切换显示的面板
                switch (selectedIndex) {
                    case 0:
                        function_1.setVisible(true);
                        function_2.setVisible(false);
                        function_3.setVisible(false);
                        break;
                    case 1:
                        function_2.setVisible(true);
                        function_1.setVisible(false);
                        function_3.setVisible(false);
                        break;
                    case 2:
                        function_1.setVisible(false);
                        function_2.setVisible(false);
                        function_3.setVisible(true);
                        break;
                }
            }
        });
    }
}