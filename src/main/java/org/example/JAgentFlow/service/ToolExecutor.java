package org.example.JAgentFlow.service;

import org.example.JAgentFlow.entity.Task;
import org.example.JAgentFlow.tool.ComputerTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ToolExecutor {

    @Autowired
    private ComputerTools computerTools;  // 注入工具类

    public String execute(Task task) {
        String toolName = task.getToolName();
        switch (toolName) {
            case "openCalculator":
                // 调用 ComputerTools 中的方法
                return computerTools.openCalculator();
            case "openBrowser":
                // 注意：openBrowser 现在是无参方法，不需要从 args 取参数
                return computerTools.openBrowser();
            default:
                return "未知工具：" + toolName;
        }
    }
}