package com.spring.ai;

import com.spring.ai.helper.Helper;
import com.spring.ai.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAiRAGApplicationTests {

	@Test
	void contextLoads() {
	}

    @Autowired
    ChatService chatService;

    Helper helper;


}
