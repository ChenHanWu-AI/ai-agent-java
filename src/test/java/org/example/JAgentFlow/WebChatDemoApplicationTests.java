package org.example.JAgentFlow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
class WebChatDemoApplicationTests {

    @Test
    void contextLoads() throws IOException {
        System.out.println("API KEY = " + Files.readString(Paths.get("C:\\Users\\15771\\Desktop\\key\\deepseek.txt")));
    }

}
