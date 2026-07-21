package com.spring.ai.service;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class ChatService {

    private final ChatClient chatClient;

    @Value("classpath:prompts/system-message.st")
    private Resource systemMessage;

    @Value("classpath:prompts/user-message.st")
    private Resource userMessage;

    private VectorStore vectorStore;

    private final Logger logger= LoggerFactory.getLogger(ChatService.class);

    public ChatService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore=vectorStore;
    }

    public String chatTemplate(String query, String conversationId) {
        return this.chatClient
                .prompt()
                .advisors(a->a.param(CONVERSATION_ID, conversationId))
                .system(system-> system.text(this.systemMessage))
                .user(user->user.text(this.userMessage)
                        .param("concept",query))
                .call()
                .content();

    }

    public Flux<String> streamChat(String q, String conversationId) {
        return  this.chatClient
                .prompt()
                .advisors(a->a.param(CONVERSATION_ID, conversationId))
                .system(system-> system.text(this.systemMessage))
                .user(user->user.text(this.userMessage)
                        .param("concept",q))
                .stream()
                .content();

    }

    public void saveData(List<String> list){
        List<Document> documentList = list.stream().map(Document::new).collect(Collectors.toList());
        this.vectorStore.add(documentList);

    }

    public String searchData(String query, String conversationId) {

//        logger.info("Received query='{}', conversationId='{}'", query, conversationId);
//
//        SearchRequest searchRequest = SearchRequest.builder().query(query).topK(5).build();
//
//        List<Document> documents = vectorStore.similaritySearch(searchRequest);
//
//        logger.info("Retrieved {} documents from Vector Store", documents.size());
//
//        documents.forEach(doc -> logger.info("Document: {}", doc.getText()));
//
//        String contextData = documents.stream().map(Document::getText).collect(Collectors.joining("\n\n"));
//
//        logger.info("Context sent to LLM:\n{}", contextData);

        String response = chatClient
                .prompt()
                .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
//                .advisors(a -> a.param(CONVERSATION_ID, conversationId))
//                .system(system -> system
//                        .text(systemMessage)
//
                .advisors(a -> a.param(CONVERSATION_ID, conversationId))
                .user(user -> user
                        .text(userMessage)
                        .param("query", query))
                .call()
                .content();

        logger.info("LLM Response: {}", response);

        return response;
    }

    public @Nullable String searchThroughRetrievalAugmentationAdvisor(String query, String conversationId) {

        var advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever
                .builder().vectorStore(vectorStore)
                .topK(3)
                .similarityThreshold(0.5).build())
                .queryAugmenter(ContextualQueryAugmenter.builder().allowEmptyContext(true).build())
                .build();


        return chatClient
                .prompt()
                .advisors(advisor)
                .advisors(a -> a.param(CONVERSATION_ID, conversationId))
                .user(user->user.text(userMessage)
                        .param("query",query))
                .call()
                .content();

    }
}
