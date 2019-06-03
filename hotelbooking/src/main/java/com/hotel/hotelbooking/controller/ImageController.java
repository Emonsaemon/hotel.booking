package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/images")
public class ImageController {

    @Autowired
    FileStorageService storageService;

    @GetMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String name)
            throws IOException {
        Resource file = storageService.loadFile(name);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(file);
    }

}

