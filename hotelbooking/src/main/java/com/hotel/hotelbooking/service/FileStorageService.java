package com.hotel.hotelbooking.service;
import com.hotel.hotelbooking.HotelbookingApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class FileStorageService {

    private final String IMAGES_DIRECTORY = "images";
    private final String DEFAULT_IMAGE = "default_image.png";
    private final int nameGeneratorBound = 100;
    private DateFormat dateFormat;
    private Random nameGenerator;
    private Path rootLocation;


    @Autowired
    public FileStorageService() {
        nameGenerator = new Random();
        dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String path = HotelbookingApplication.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath();
        path = path.substring(1, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/") + 1);
        rootLocation = Paths.get(path, IMAGES_DIRECTORY);
    }

    public String store(MultipartFile file) throws IOException {
        try {
            String fileName = file.getOriginalFilename();
            Date now = Calendar.getInstance().getTime();
            String filepath = dateFormat.format(now) +
                    String.valueOf(nameGenerator.nextInt(nameGeneratorBound)) +
                    fileName.substring(fileName.lastIndexOf("."),
                        fileName.length());
            filepath = filepath.replaceAll(":", "_");
            filepath = filepath.replaceAll(" ", "_");
            Files.copy(file.getInputStream(), Paths.get(rootLocation.toString(),
                    filepath));
            return filepath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to save file!");
        }
    }

    public Resource loadFile(String name) throws IOException {
        try {
            Path file = Paths.get(rootLocation.toString(), name);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                file = Paths
                        .get(rootLocation.toString(), DEFAULT_IMAGE );
                resource = new UrlResource(file.toUri());
                return resource;
            }
        } catch (MalformedURLException e) {
            throw new IOException("Wrong file path format!");
        }
    }

    public void deleteFile(String name) throws IOException {
        try {;
            Files.delete(Paths.get(rootLocation.toString(), name));
        } catch (IOException e) {
            throw new IOException("No such file!");
        }
    }

    public void init() throws IOException {
        try {
            if(!Files.exists(rootLocation)) {
                Files.createDirectory(rootLocation);
            }
        } catch (Exception e) {
            throw new IOException("Could not initialize storage!");
        }
    }
}

