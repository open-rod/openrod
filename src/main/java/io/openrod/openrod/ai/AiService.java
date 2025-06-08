package io.openrod.openrod.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatModel chatModel;

    @Autowired
    public AiService(
        final ChatModel chatModel
    ) {
        this.chatModel = chatModel;
    }

    public String generateDescription(final String input) {
        return ChatClient.create(this.chatModel).prompt()
                .user(u -> u.text("Create a short description (max 100 characters) for the following input: {input}. only return the description. Don't wrap the response in quotation marks.")
                        .param("input", input))
                .call()
                .content();
    }

    public String generateTitle(final String input) {
        return ChatClient.create(this.chatModel).prompt()
                .user(u -> u.text("Create a short (max 40 characters) title for the following input: {input}. only return the title. Don't wrap the response in quotation marks.")
                        .param("input", input))
                .call()
                .content();
    }
}
