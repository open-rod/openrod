package io.openrod.openrod.file.impl;

import io.openrod.openrod.file.FileDTO;
import io.openrod.openrod.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final String uploadDir;

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Autowired
    public FileServiceImpl(
        @Value("${file.upload-dir:uploads/}") final String uploadDir,
        final FileRepository fileRepository,
        final FileMapper fileMapper
    ) {
        this.uploadDir = uploadDir;

        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    @Override
    public FileDTO storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "File is empty");
        }

        Path uploadPath = Paths.get(this.uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = "%s_%s".formatted(System.currentTimeMillis(), file.getOriginalFilename());
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        var dto = new FileDTO();
        dto.setPath(filePath.toString());
        dto.setMimeType(file.getContentType());
        dto.setName(file.getOriginalFilename());
        dto.setSize(file.getSize());

        if (file.getOriginalFilename() != null && file.getOriginalFilename().contains(".")) {
            dto.setExtension(
                    file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
            );
        }

        return dto;
    }

    @Override
    public FileDTO getFile(UUID id) {
        return this.fileRepository.findById(id)
            .map(this.fileMapper::toDTO)
            .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(400), "Could not find file"));
    }
}


