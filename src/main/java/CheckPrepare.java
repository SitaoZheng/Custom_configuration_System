import gui.Maingui;
import lang.LanguageManager;
import org.yaml.snakeyaml.Yaml;
import unit.Version;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class CheckPrepare {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Files Checker");
            frame.setSize(400, 250);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setResizable(false);
            frame.setLocationRelativeTo(null);

            // 设置窗口图标
            Image icon = Toolkit.getDefaultToolkit().getImage("src/Resource/ima/icon.png");
            frame.setIconImage(icon);

            // 加载动画图标
            ImageIcon loadingIcon = new ImageIcon("src/resource/ima/loading.gif");

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setBounds(10, 10, 367, 140);

            JLabel loadingLabel = new JLabel(loadingIcon);
            loadingLabel.setBounds(150, 140, 80, 80);

            frame.getContentPane().setLayout(null);
            frame.getContentPane().add(scrollPane);
            frame.getContentPane().add(loadingLabel);

            frame.setVisible(true);

            // 定时器1：检测config.yml文件
            Timer timer_1 = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    final boolean[] allFilesAvailable = {true};

                    String fileName_1 = "config.yml";
                    String filePath_1 = "src/Resource/" + fileName_1;
                    try (InputStream input_1 = new FileInputStream(filePath_1)) {
                        Yaml yaml_1 = new Yaml();
                        Map<String, Object> yamlData_1 = yaml_1.load(input_1);
                        String versionValue_1 = String.valueOf(yamlData_1.get("Version"));
                        Version version = new Version();
                        if (Objects.equals(versionValue_1, version.getVersion())) {
                            textArea.append("File: " + fileName_1 + " available. Version: " + versionValue_1 + "\n");
                        } else {
                            allFilesAvailable[0] = false;
                            textArea.append("File: " + fileName_1 + " unavailable. Error Info: " + fileName_1 + " Version: " + versionValue_1 + " allowed Version: " + version.getVersion() + "\n");
                        }
                    } catch (IOException e1) {
                        allFilesAvailable[0] = false;
                        textArea.append("File: " + fileName_1 + " unavailable. Error Info: " + e1.getMessage() + "\n");
                    }

                    // 定时器2：检测语言文件
                    String configPath = "src/Resource/config.yml";
                    String language = new LanguageManager().getLanguageFromConfig(configPath);
                    String fileName_2 = language.toUpperCase() + ".yml";
                    String filePath_2 = "src/Resource/lang/Locale_" + language.toUpperCase() + ".yml";
                    Timer timer_2 = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try (InputStream input_2 = new FileInputStream(filePath_2)) {
                                Yaml yaml_2 = new Yaml();
                                Map<String, Object> yamlData_2 = yaml_2.load(input_2);
                                String versionValue_2 = String.valueOf(yamlData_2.get("Version_Number"));
                                Version version = new Version();
                                if (Objects.equals(versionValue_2, version.getVersion())) {
                                    textArea.append("File: Locale_" + fileName_2 + " available. Version: " + versionValue_2 + "\n");
                                } else {
                                    allFilesAvailable[0] = false;
                                    textArea.append("File: Locale_" + fileName_2 + " unavailable. Error Info: " + fileName_2 + " Version: " + versionValue_2 + " allowed Version: " + version.getVersion() + "\n");
                                }
                            } catch (IOException e2) {
                                allFilesAvailable[0] = false;
                                textArea.append("File: Locale_" + fileName_2 + " unavailable. Error Info: " + e2.getMessage() + "\n");
                            }

                            // 判断所有文件是否可用
                            if (allFilesAvailable[0]) {
                                Timer timer_3 = new Timer(1000, new ActionListener() {
                                    // 定时器3：延迟3秒显示提示信息
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        textArea.append("All files are available. Three seconds into the mainframe." + "\n");
                                        textArea.append("About to enter the main program..." + "\n");
                                    }
                                });
                                timer_3.setRepeats(false);
                                timer_3.start();

                                // 定时器4：延迟6秒执行主程序
                                Timer timer_4 = new Timer(3000, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        frame.dispose();
                                        try {
                                            new Maingui();
                                            Maingui.maingui();
                                        } catch (Exception ex) {
                                        }
                                    }
                                });
                                timer_4.setRepeats(false);
                                timer_4.start();
                            } else {
                                // 移除loading.gif，更换为png
                                frame.getContentPane().remove(loadingLabel);
                                ImageIcon newIcon = new ImageIcon("src/resource/ima/error.png");
                                JLabel newLabel = new JLabel(newIcon);
                                newLabel.setBounds(150, 140, 80, 80);
                                frame.getContentPane().add(newLabel);
                                frame.revalidate();
                                frame.repaint();
                                textArea.append("The file is incomplete and cannot continue." + "\n");
                                textArea.append("Please stop the program and check the file." + "\n");
                            }
                        }
                    });
                    timer_2.setRepeats(false);
                    timer_2.start();
                }
            });
            timer_1.setRepeats(false);
            timer_1.start();
        });
    }
}
