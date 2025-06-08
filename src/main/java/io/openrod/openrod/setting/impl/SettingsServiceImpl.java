package io.openrod.openrod.setting.impl;

import io.openrod.openrod.setting.AiSettings;
import io.openrod.openrod.setting.SettingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

@Service
@Transactional
public class SettingsServiceImpl implements SettingService {

    private final AiSettings aiSettings;

    private final ResourceLoader resourceLoader;

    @Autowired
    public SettingsServiceImpl(
        final AiSettings aiSettings,
        final ResourceLoader resourceLoader
    ) {
        this.aiSettings = aiSettings;

        this.resourceLoader = resourceLoader;
    }

    @Override
    public AiSettings getAiSettings() {
        return this.aiSettings;
    }

    @Override
    public void setAiSettings(final AiSettings settings) {
        Resource resource = resourceLoader.getResource("classpath:settings.properties");
        Properties properties = new Properties();

        try (InputStream in = resource.getInputStream()) {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        properties.setProperty("app.settings.ai.automatic-category", settings.getAutomaticCategory() ? "true" : "false");

        try (OutputStream out = Files.newOutputStream(resource.getFile().toPath())) {
            properties.store(out, "Updated properties");
        } catch (IOException e) {

        }
    }
}
