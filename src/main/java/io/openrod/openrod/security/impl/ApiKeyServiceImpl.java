package io.openrod.openrod.security.impl;

import io.openrod.openrod.app.AppDTO;
import io.openrod.openrod.app.AppService;
import io.openrod.openrod.security.ApiKeyService;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    private final AppService appService;

    public ApiKeyServiceImpl(
        final AppService appService
    ) {
        this.appService = appService;
    }

    @Override
    public AppDTO getAppByApiKey(String apiKey) {
        return this.appService.getAppByApiKey(apiKey);
    }
}
