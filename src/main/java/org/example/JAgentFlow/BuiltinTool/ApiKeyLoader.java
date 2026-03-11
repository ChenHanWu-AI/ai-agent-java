package org.example.JAgentFlow.BuiltinTool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ApiKeyLoader {

    public static String loadApiKey() {

        try {
            return Files.readString(Paths.get("C:\\Users\\15771\\Desktop\\key\\deepseek.txt")).trim();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read API key", e);
        }
    }
}