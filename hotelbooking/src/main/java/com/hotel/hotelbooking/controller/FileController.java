package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.service.FileService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/report")
    @ResponseBody
    public ResponseEntity<byte[]> getFile()
            throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(stream));
        Document doc = fileService.generateReport(pdfDoc);
        doc.close();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(stream.toByteArray());
    }

}
