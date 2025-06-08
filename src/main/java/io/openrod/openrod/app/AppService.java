package io.openrod.openrod.app;

import java.util.List;
import java.util.UUID;

public interface AppService {

    List<AppDTO> getApps();

    AppDTO getApp(final UUID id);

    AppDTO getAppByApiKey(final String apiKey);

    AppDTO createApp(final AppDTO app);

    AppDTO updateApp(final AppDTO app);

    void deleteApp(final UUID id);

    List<AppDTO> getAppsByCategoryId(final UUID categoryId);

    List<AppDTO> getAppsByTagId(final UUID tagId);

}
