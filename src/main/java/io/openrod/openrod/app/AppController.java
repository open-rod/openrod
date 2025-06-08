package io.openrod.openrod.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/apps")
public class AppController {

    private final AppService appService;

    @Autowired
    public AppController(
        final AppService appService
    ) {
        this.appService = appService;
    }

    @GetMapping()
    public ResponseEntity<List<AppDTO>> getApps() {
        return ResponseEntity.ok(
            this.appService.getApps()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppDTO> getApp(
        @PathVariable("id") final UUID id
    ) {
        return ResponseEntity.ok(
            this.appService.getApp(id)
        );
    }

    @PostMapping()
    public ResponseEntity<AppDTO> createApp(
        @RequestBody final AppDTO dto
    ) {
        var createdApp = this.appService.createApp(dto);

        var location = fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdApp.getId())
                .toUri();

        return ResponseEntity
            .created(location)
            .body(createdApp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppDTO> updateApp(
        @PathVariable("id") final UUID id,
        @RequestBody final AppDTO app
    ) {
        app.setId(id);

        var updatedApp = this.appService.updateApp(app);

        return ResponseEntity.accepted()
            .body(updatedApp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApp(
        @PathVariable("id") final UUID id
    ) {
        this.appService.deleteApp(id);

        return ResponseEntity.noContent().build();
    }
}
