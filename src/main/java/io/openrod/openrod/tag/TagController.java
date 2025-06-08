package io.openrod.openrod.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(
        final TagService tagService
    ) {
        this.tagService = tagService;
    }

    @GetMapping()
    public ResponseEntity<List<TagInfoDO>> getTags() {
        return ResponseEntity.ok(
            this.tagService.getTags()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTag(
        @PathVariable("id") final UUID id
    ) {
        return ResponseEntity.ok(
            this.tagService.getTag(id)
        );
    }

    @PostMapping()
    public ResponseEntity<TagDTO> createTag(
        @RequestBody final TagDTO tag
    ) {
        var createdTag = this.tagService.createTag(tag);

        var location = fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdTag.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdTag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(
        @PathVariable("id") final UUID id,
        @RequestBody final TagDTO tag
    ) {
        tag.setId(id);

        var updatedTag = this.tagService.updateTag(tag);

        return ResponseEntity.accepted().body(updatedTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(
        @PathVariable("id") final UUID id
    ) {
        this.tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
