package org.example.JAgentFlow.service;


import dev.langchain4j.service.spring.AiService;

@AiService
public interface Assistant {
    String chat(String userMessage);
}