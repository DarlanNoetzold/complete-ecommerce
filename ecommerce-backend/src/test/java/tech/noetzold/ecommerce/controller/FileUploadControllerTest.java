package tech.noetzold.ecommerce.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.noetzold.ecommerce.model.FileInfo;


import java.util.List;
@ExtendWith(SpringExtension.class)
class FileUploadControllerTest {

    @InjectMocks
    FileUploadController fileUploadController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void handleFileUpload() {
    }

    @Test
    void getListFiles() {
        ResponseEntity<List<FileInfo>> files = fileUploadController.getListFiles();

        Assertions.assertThat(files).isNotNull();
    }

    @Test
    void getFile() {
        ResponseEntity<Resource> file = fileUploadController.getFile("aaaa");

        Assertions.assertThat(file.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}