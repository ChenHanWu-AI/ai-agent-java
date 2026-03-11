package org.example.JAgentFlow.tool;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ComputerTools {

    @Tool("打开计算器")
    public String openCalculator() {
        try {
            Runtime.getRuntime().exec("calc.exe");
            System.out.printf("计算器已经被打开");
            return "计算器已打开";
        } catch (IOException e) {
            return "打开计算器失败：" + e.getMessage();
        }
    }
    @Tool("打开默认浏览器")
    public String openBrowser() {
        try {
            String url = "https://www.baidu.com";
            String os = System.getProperty("os.name").toLowerCase();
            Runtime runtime = Runtime.getRuntime();

            if (os.contains("win")) {
                // Windows
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                // macOS
                runtime.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Linux/Unix
                runtime.exec("xdg-open " + url);
            } else {
                return "不支持的操作系统";
            }
            return "默认浏览器已打开";
        } catch (Exception e) {
            return "打开浏览器失败：" + (e.getMessage() != null ? e.getMessage() : "未知错误");
        }
    }
}