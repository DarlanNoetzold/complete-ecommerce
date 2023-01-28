package tech.noetzold.ecommerce.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;


import static org.junit.jupiter.api.Assertions.*;

class FileUploadControllerTest {

    @InjectMocks
    FileUploadController fileUploadController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void handleFileUpload() {
        fileUploadController.handleFileUpload()
    }

    @Test
    void getListFiles() {
    }

    @Test
    void getFile() {
    }
}