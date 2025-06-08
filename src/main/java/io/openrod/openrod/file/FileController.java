package io.openrod.openrod.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(
        final FileService fileService
    ) {
        this.fileService = fileService;
    }
    @PostMapping
    public ResponseEntity<FileDTO> uploadFile(
        @RequestParam("file") MultipartFile file
    ) {
        try {
            var uploadedFile = this.fileService.storeFile(file);

            return ResponseEntity.ok(uploadedFile);
        } catch (IOException e) {
            throw new HttpServerErrorException(HttpStatusCode.valueOf(500), e.getMessage());
        }
    }
}
