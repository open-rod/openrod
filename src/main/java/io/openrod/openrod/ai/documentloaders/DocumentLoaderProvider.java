package io.openrod.openrod.ai.documentloaders;

import io.openrod.openrod.memory.FileMemoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.DocumentLoader;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Component
public class DocumentLoaderProvider {

    private final List<RodDocumentLoader> documentLoaders;

    @Autowired
    public DocumentLoaderProvider(
        final List<RodDocumentLoader> documentLoaders
    ) {
        this.documentLoaders = documentLoaders;
    }

    public RodDocumentLoader getDocumentLoader(final FileMemoryDTO memory) {
        var documentLoaders = this.documentLoaders.stream().filter((d) -> d.accepts(memory.getFile().getExtension())).toList();

        if (documentLoaders.isEmpty()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "No document loader found for extension %s".formatted(memory.getFile().getExtension()));
        }

        return documentLoaders.get(0);
    }
}
