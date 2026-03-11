package org.example.JAgentFlow.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.JAgentFlow.entity.Task;
import org.example.JAgentFlow.service.TaskParser;
import org.example.JAgentFlow.service.ToolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private TaskParser taskParser;
    @Autowired
    private ToolExecutor toolExecutor;
    @Autowired
    private ObjectMapper objectMapper;

    // 线程安全的发送方法
    private synchronized void sendMessage(WebSocketSession session, String message) throws IOException {
        session.sendMessage(new TextMessage(message));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userInput = message.getPayload();

        sendMessage(session, "📨 已收到指令，正在分析...");
        //根据ai分析用户需求
        String tasksJson = taskParser.parseTasks(userInput);
        List<Task> tasks = objectMapper.readValue(tasksJson, new TypeReference<List<Task>>() {});
        if (tasks.isEmpty()) {
            sendMessage(session, "🤖 无法理解您的指令，请尝试其他说法。");
            return;
        }

        String toolNames = tasks.stream()
                .map(Task::getToolName)
                .collect(Collectors.joining("、"));
        sendMessage(session, "🔍 AI分析需要执行：" + toolNames);

        List<CompletableFuture<String>> futures = tasks.stream()
                .map(task -> CompletableFuture.supplyAsync(() -> {
                    try {
                        sendMessage(session, "▶️ 开始执行：" + task.getToolName());

                        String result = toolExecutor.execute(task);

                        sendMessage(session, "✅ 完成：" + task.getToolName() + " → " + result);

                        return result;
                    } catch (Exception e) {
                        try {
                            sendMessage(session, "❌ 执行失败：" + task.getToolName() + " → " + e.getMessage());
                        } catch (IOException ignored) {}
                        return "错误";
                    }
                }))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenAccept(v -> {
                    try {
                        sendMessage(session, "🎉 所有任务执行完毕！");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}