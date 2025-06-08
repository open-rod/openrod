package io.openrod.openrod.ai.documentloaders;

import io.openrod.openrod.memory.FileMemoryDTO;
import org.springframework.ai.document.Document;

import java.util.List;

public interface RodDocumentLoader {

    List<Document> loadDocuments(final FileMemoryDTO memory);

    boolean accepts(final String extension);
}
