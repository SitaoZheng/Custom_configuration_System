package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Maingui extends JFrame {
    public static void main(String[] args) throws Exception {
        maingui();
    }
    @SuppressWarnings("SpellCheckingInspection")
    public static void maingui() throws Exception {
        // 设置字体
        Font font_1 = new Font("微软雅黑", Font.BOLD, 20);
        Font font_2 = new Font("微软雅黑", Font.BOLD, 10);

        // 程序标题
        JFrame maingui = new JFrame("Custom Configuration System");
        // 窗口大小
        maingui.setPreferredSize(new Dimension(668, 434));
        // 部件自适应大小
        maingui.pack();
        // 关闭窗口结束程序
        maingui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 绝对布局（窗口内的内容不会随窗口大小的改变而改变）
        maingui.setLayout(null);
        // 固定窗口大小
        maingui.setResizable(false);
        // 设置窗口在屏幕中间显示
        maingui.setLocationRelativeTo(null);

        // 获取当前窗口大小
        int width = (int) maingui.getSize().getWidth();
        int height = (int) maingui.getSize().getHeight();

        // 设置窗口图标
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Resource/ima/icon.png");
        maingui.setIconImage(icon);

        // 设置背景
        // 加载背景图
        ImageIcon background = new ImageIcon("src/Resource/ima/bg_1.png");
        // 创建显示背景图的JLabel
        JLabel backgroundLabel = new JLabel(background);
        // 设置JLabel的位置和大小
        backgroundLabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        // 将JLabel添加到窗口中
        maingui.getLayeredPane().add(backgroundLabel, Integer.valueOf(Integer.MIN_VALUE));

        // 将JLabel设置为透明，以显示背景图
        JPanel contentPane = (JPanel) maingui.getContentPane();
        contentPane.setOpaque(false);

        // 添加组件
        // info
        JLabel message_1 = new JLabel("Version: 1.0");
        message_1.setBounds(10, 355, 150, 15);
        message_1.setFont(font_2);
        message_1.setForeground(Color.BLACK);
        JLabel message_2 = new JLabel("Authors: Sitao Zheng & Yiming Yang & Jiapeng Wu");
        message_2.setBounds(10, 370, 300, 15);
        message_2.setFont(font_2);
        message_2.setForeground(Color.BLACK);

        // 启动按键
        JButton Launch_button = new JButton("Launch");
        Launch_button.setBounds((width / 2) - 75, (height / 2) - 80, 150, 50);
        Launch_button.setForeground(Color.RED);
        Launch_button.setFont(font_1);

        maingui.add(Launch_button, JLayeredPane.MODAL_LAYER);
        maingui.add(message_1, JLayeredPane.MODAL_LAYER);
        maingui.add(message_2, JLayeredPane.MODAL_LAYER);

        // 按钮监听
        // 查看监听
        Launch_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // 关闭（隐藏）窗口
                    maingui.dispose();
                    // 进入查看界面
                    Function frame = new Function();
                    // 窗口居中
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                    // 固定大小
                    frame.setResizable(false);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        maingui.setVisible(true);
    }
}
