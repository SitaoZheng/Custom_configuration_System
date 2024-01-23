package unit;

public class Placeholder {
    // 自定义方法，根据参数替换字符串中的占位符
    public static String replaceVariables(String input, String... variables) {
        if (variables.length % 2 != 0) {
            throw new IllegalArgumentException("Variables must be provided in pairs (name, value).");
        }
        for (int i = 0; i < variables.length; i += 2) {
            String variableName = "%" + variables[i] + "%";
            String variableValue = variables[i + 1];
            input = input.replace(variableName, variableValue);
        }
        return input;
    }
}