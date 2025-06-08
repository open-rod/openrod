package io.openrod.openrod.ai;

import io.openrod.openrod.app.AppService;
import io.openrod.openrod.category.CategoryDTO;
import io.openrod.openrod.memory.ContentMemoryDTO;
import io.openrod.openrod.memory.MemoryDTO;
import io.openrod.openrod.memory.MemoryService;
import io.openrod.openrod.security.ApiKeyAuthentication;
import io.openrod.openrod.setting.SettingService;
import io.openrod.openrod.tag.TagDTO;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final RodVectorStore vectorStore;
    private final MemoryService memoryService;
    private final AiService aiService;
    private final AiMemoryService aiMemoryService;
    private final SettingService settingService;
    private final AppService appService;

    @Autowired
    public AiController(
        final RodVectorStore vectorStore,
        final MemoryService memoryService,
        final AiService aiService,
        final AiMemoryService aiMemoryService,
        final SettingService settingService,
        final AppService appService
    ) {
        this.vectorStore = vectorStore;
        this.memoryService = memoryService;
        this.aiService = aiService;
        this.aiMemoryService = aiMemoryService;

        this.settingService = settingService;

        this.appService = appService;
    }

    @GetMapping()
    public ResponseEntity<List<RodDocument>> getDocuments(
        @RequestParam("query") final String query
    ) {
        var app = ((ApiKeyAuthentication)SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        app.setLastActive(LocalDateTime.now());
        this.appService.updateApp(app);

        return ResponseEntity.ok(
            this.vectorStore.searchMemories(query, app)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createDocument(
        @RequestBody final ContentMemoryDTO memory,
        @RequestHeader("X-Username") final String username
    ) {
        var app = ((ApiKeyAuthentication)SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        memory.setAddedThrough(app.getTitle());
        memory.setAddedBy(username);
        memory.setAddedThroughId(app.getId());

        var aiSettings = this.settingService.getAiSettings();

        if (Objects.isNull(memory.getTitle())) {
            memory.setTitle(
                this.aiService.generateTitle(memory.getContent())
            );
        }

        if (Objects.isNull(memory.getDescription())) {
            memory.setDescription(
                this.aiService.generateDescription(memory.getContent())
            );
        }

        if (aiSettings.getAutomaticCategory()) {
            var categoryId = this.aiMemoryService.getMostRelevantCategory(app, memory);
            if (Objects.nonNull(categoryId)) {
                memory.setCategory(new CategoryDTO());
                memory.getCategory().setId(categoryId);
            }
        }

        if (aiSettings.getAutomaticTags()) {
            var tags = this.aiMemoryService.getMostRelevantTags(app, memory, 2);
            if (!tags.isEmpty()) {
                memory.setTags(
                    tags.stream().map((id) -> {
                        var tag = new TagDTO();
                        tag.setId(id);
                        return tag;
                    }).toList()
                );
            }
        }

        app.setLastActive(LocalDateTime.now());
        this.appService.updateApp(app);
        this.memoryService.createMemory(memory);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/memories/{id}/documents")
    public ResponseEntity<List<Document>> getMemoryDocuments(
        @PathVariable("id") final UUID memoryId
    ) {
        return ResponseEntity.ok(
            this.vectorStore.getDocumentsForMemory(memoryId)
        );
    }
}
