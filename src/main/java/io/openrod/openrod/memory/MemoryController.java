package io.openrod.openrod.memory;

import io.openrod.openrod.category.CategoryDTO;
import io.openrod.openrod.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/memories")
public class MemoryController {

    private final MemoryService memoryService;
    private final FileService fileService;

    @Autowired
    public MemoryController(
        final MemoryService memoryService,
        final FileService fileService
    ) {
        this.memoryService = memoryService;
        this.fileService = fileService;
    }

    @GetMapping()
    public ResponseEntity<List<MemoryDTO>> getMemories() {
        return ResponseEntity.ok(
            this.memoryService.getMemories()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MemoryDTO>> searchMemories(
        @ModelAttribute final MemorySearchCriteria searchCriteria
    ) {
        return ResponseEntity.ok(
            this.memoryService.searchMemories(searchCriteria)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoryDTO> getMemory(
        @PathVariable("id") final UUID id
    ) {
        return ResponseEntity.ok(
            this.memoryService.getMemory(id)
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemoryDTO> createMemory(
        @RequestBody ContentMemoryDTO memory
    ) {
        memory.setAddedThrough("WEB_APP");
        if (Objects.isNull(memory.getAddedBy())) {
            memory.setAddedBy("USER");
        }

        var createdMemory = this.memoryService.createMemory(memory);

        return ResponseEntity.ok(createdMemory);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MemoryDTO> createMemory(
        @ModelAttribute final MemoryFileForm memoryFile
    ) {
        try {
            var memory = new FileMemoryDTO();
            memory.setFile(this.fileService.storeFile(memoryFile.getFile()));
            memory.setStatus(MemoryStatus.ACTIVE);
            memory.setTitle(memoryFile.getTitle());
    //        memory.setTags(memoryFile.getTags());
            memory.setDescription(memoryFile.getDescription());

            var category = new CategoryDTO();
            category.setId(memoryFile.getCategoryId());

            memory.setCategory(category);

            var createdMemory = this.memoryService.createMemory(memory);

            return ResponseEntity.ok(createdMemory);
        } catch (IOException e) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(500), e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoryDTO> updateMemory(
        @PathVariable("id") final UUID id,
        @RequestBody final MemoryDTO memory
    ) {
        memory.setId(id);

        var updatedMemory = this.memoryService.updateMemory(memory);

        return ResponseEntity.accepted().body(updatedMemory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemory(
        @PathVariable("id") final UUID id
    ) {
        this.memoryService.deleteMemory(id);

        return ResponseEntity.noContent().build();
    }
}
