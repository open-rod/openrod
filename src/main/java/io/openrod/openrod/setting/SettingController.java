package io.openrod.openrod.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class SettingController {

    private final SettingService settingService;

    @Autowired
    public SettingController(
        final SettingService settingService
    ) {
        this.settingService = settingService;
    }

    @GetMapping("/ai")
    public ResponseEntity<AiSettings> getAiSettings() {
        return ResponseEntity.ok(
            this.settingService.getAiSettings()
        );
    }

    @PostMapping("/ai")
    public ResponseEntity<AiSettings> setAiSettings(
        @RequestBody final AiSettings aiSettings
    ) {
        this.settingService.setAiSettings(aiSettings);
        return ResponseEntity.ok(
            this.settingService.getAiSettings()
        );
    }
}
