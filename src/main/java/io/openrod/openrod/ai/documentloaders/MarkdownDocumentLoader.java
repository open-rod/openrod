package io.openrod.openrod.ai.documentloaders;

import io.openrod.openrod.memory.FileMemoryDTO;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarkdownDocumentLoader implements RodDocumentLoader {

    private MarkdownDocumentReaderConfig config;

    public MarkdownDocumentLoader() {
        this.config = MarkdownDocumentReaderConfig.builder()
            .withHorizontalRuleCreateDocument(true)
            .withIncludeCodeBlock(true)
            .withIncludeBlockquote(true)
            .build();
    }

    @Override
    public List<Document> loadDocuments(FileMemoryDTO memory) {
        MarkdownDocumentReader reader = new MarkdownDocumentReader(new FileSystemResource(memory.getFile().getPath()), this.config);

        return reader.get();
    }

    @Override
    public boolean accepts(final String extension) {
        return "md".equalsIgnoreCase(extension);
    }
}
