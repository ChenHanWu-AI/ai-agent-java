package org.example.JAgentFlow.entity;

import java.util.Map;

/**
 * 表示一个待执行的任务，由AI解析用户指令后生成。
 */
public class Task {
    private String toolName;          // 工具名称，如 "openCalculator"
    private Map<String, Object> args; // 参数，如 {"browser":"edge"}

    // 必须要有无参构造方法（Jackson反序列化时需要）
    public Task() {}

    // getter 和 setter
    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }
}