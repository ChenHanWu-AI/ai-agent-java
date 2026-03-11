package org.example.JAgentFlow.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;


public interface TaskParser {
    @SystemMessage("你是一个指令解析器。你的任务是将用户的指令解析为一系列工具调用，以JSON数组格式返回。\n" +
            "可用工具（只能使用这些，不要自己发明）：\n" +
            "- openCalculator：无参数，打开系统计算器。用户可能会说'打开计算器'、'打开计算机'、'启动计算器'等，都对应此工具。\n" +
            "- openBrowser：无参数，打开默认浏览器并访问百度首页。用户可能会说'打开浏览器'、'上网'、'启动浏览器'等，都对应此工具。\n" +
            "规则：\n" +
            "1. 如果用户指令明确要求使用上述工具，则返回对应的JSON数组。可以返回多个工具，如果指令中提到多个操作。\n" +
            "2. 如果指令无法匹配任何工具，或者用户要求打开不存在的程序，则返回空数组 []。\n" +
            "3. 不要添加任何解释，只返回JSON。\n" +
            "示例：\n" +
            "用户：打开计算器 → [{\"toolName\":\"openCalculator\",\"args\":{}}]\n" +
            "用户：打开计算机 → [{\"toolName\":\"openCalculator\",\"args\":{}}]\n" +
            "用户：打开浏览器 → [{\"toolName\":\"openBrowser\",\"args\":{}}]\n" +
            "用户：打开计算器和浏览器 → [{\"toolName\":\"openCalculator\",\"args\":{}},{\"toolName\":\"openBrowser\",\"args\":{}}]\n" +
            "用户：打开小怪物 → []")
    @UserMessage("解析指令：{{it}}")
    String parseTasks(String userMessage);
}