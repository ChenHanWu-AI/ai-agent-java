package org.example.JAgentFlow.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.example.JAgentFlow.service.TaskParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.example.JAgentFlow.BuiltinTool.ApiKeyLoader;
@Configuration
public class DeepSeekConfig {

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(ApiKeyLoader.loadApiKey())   // 直接填入你的密钥（也可以从配置文件读取）
                .baseUrl("https://api.deepseek.com")
                .modelName("deepseek-chat")
                .build();
    }
    @Bean
    public TaskParser taskParser(ChatLanguageModel model) {
        return AiServices.create(TaskParser.class, model);
    }
}