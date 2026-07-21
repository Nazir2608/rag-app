package com.spring.ai.controller;

import com.spring.ai.helper.Helper;
import com.spring.ai.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam(value = "q", required = true) String q,@RequestParam String conversationId) {
        return ResponseEntity.ok(chatService.chatTemplate(q, conversationId));
    }

    @GetMapping("/stream-chat")
    public ResponseEntity<Flux<String>> streamChat(@RequestParam(value = "q", required = true) String q, @RequestParam String conversationId) {
        return ResponseEntity.ok(chatService.streamChat(q,conversationId));
    }

    @GetMapping("/load-data")
    public String loadData() {
        chatService.saveData(Helper.getData());
        return "Data saved successfully into MariaDB Vector Store.";
    }

    @GetMapping("/search-data")
    public ResponseEntity<String> searchData(@RequestParam(value="q", required = true) String query, @RequestParam String conversationId) {
        return ResponseEntity.ok(chatService.searchData(query, conversationId));
    }

    @GetMapping("/retrieval-data")
    public ResponseEntity<String> searchThroughRetrievalAugmentationAdvisor(@RequestParam(value="q", required = true) String query, @RequestParam String conversationId) {
        return ResponseEntity.ok(chatService.searchThroughRetrievalAugmentationAdvisor(query, conversationId));
    }



}
