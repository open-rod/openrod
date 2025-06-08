package io.openrod.openrod.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileService {

    FileDTO storeFile(MultipartFile file) throws IOException;

    FileDTO getFile(UUID id);
}
