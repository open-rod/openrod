package io.openrod.openrod.ai.documentloaders;

import io.openrod.openrod.memory.FileMemoryDTO;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

public class TextDocumentLoader implements RodDocumentLoader {

    @Override
    public List<Document> loadDocuments(FileMemoryDTO memory) {
        TextReader textReader = new TextReader(new FileSystemResource(memory.getFile().getPath()));
        textReader.getCustomMetadata().put("filename", memory.getFile().getName());

        return textReader.read();
    }

    @Override
    public boolean accepts(String extension) {
        return "txt".equalsIgnoreCase(extension);
    }
}
