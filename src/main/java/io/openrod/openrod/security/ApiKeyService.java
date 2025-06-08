package io.openrod.openrod.security;

import io.openrod.openrod.app.AppDTO;

public interface ApiKeyService {

    AppDTO getAppByApiKey(final String apiKey);

}
