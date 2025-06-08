package io.openrod.openrod.setting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class AiSettings {

    @Value("${app.settings.ai.automatic-category}")
    private boolean automaticCategory;

    @Value("${app.settings.ai.automatic-tags}")
    private boolean automaticTags;

    public void setAutomaticCategory(final boolean automaticCategory) {
        this.automaticCategory = automaticCategory;
    }

    public boolean getAutomaticCategory() {
        return this.automaticCategory;
    }

    public void setAutomaticTags(final boolean automaticTags) {
        this.automaticTags = automaticTags;
    }

    public boolean getAutomaticTags() {
        return this.automaticTags;
    }
}
