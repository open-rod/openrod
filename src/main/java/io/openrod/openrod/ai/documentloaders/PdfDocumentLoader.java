package io.openrod.openrod.ai.documentloaders;

import io.openrod.openrod.memory.FileMemoryDTO;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PdfDocumentLoader implements RodDocumentLoader {

    @Override
    public List<Document> loadDocuments(FileMemoryDTO memory) {
        final PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
            new FileSystemResource(memory.getFile().getPath()),
            PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(
                    ExtractedTextFormatter.builder()
                        .withNumberOfTopTextLinesToDelete(0)
                        .build()
                )
                .withPagesPerDocument(1)
                .build()
        );

        return pdfReader.read();
    }

    @Override
    public boolean accepts(final String extension) {
        return "pdf".equalsIgnoreCase(extension);
    }
}
