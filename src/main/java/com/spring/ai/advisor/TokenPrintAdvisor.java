package com.spring.ai.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;

import java.util.Objects;

public class TokenPrintAdvisor implements CallAdvisor, StreamAdvisor {

     private Logger logger= LoggerFactory.getLogger(TokenPrintAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        logger.info("TokenPrintAdvisor called!!!");
        logger.info("Request:{}", chatClientRequest.prompt().getContents());
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        this.logger.info("Token Advisor Response receive from model");
        assert chatClientResponse.chatResponse() != null;
        this.logger.info("Response: {}", Objects.requireNonNull(chatClientResponse.chatResponse().getResult()).getOutput().getText());

        this.logger.info("Prompt Token Used: {}", chatClientResponse.chatResponse().getMetadata().getUsage().getPromptTokens());
        this.logger.info("Completion Token Used: {}", chatClientResponse.chatResponse().getMetadata().getUsage().getCompletionTokens());
        this.logger.info("Token Used: {}", chatClientResponse.chatResponse().getMetadata().getUsage().getTotalTokens());

        return chatClientResponse;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        logger.info("Stream advisor called!!!");
        return streamAdvisorChain.nextStream(chatClientRequest);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
