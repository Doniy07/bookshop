package org.company.youtube.service.attach;

import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.entity.attach.AttachEntity;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.repository.attach.AttachRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AttachService {

    private final AttachRepository attachRepository;

    public AttachService(AttachRepository attachRepository) {
        this.attachRepository = attachRepository;
    }

    @Value("${server.url}")
    private String serverUrl;

    @Value("${attach.upload.url}")
    private String attachUrl;

    public AttachDTO saveAttach(MultipartFile file) {
        try {
            String pathFolder = getYmDString(); // 2024/06/08
            File folder = new File(attachUrl + pathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String type = getType(Objects.requireNonNull(file.getOriginalFilename())); // dasda.asdas.dasd.jpg
            // save to system
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachUrl + pathFolder + "/" + key + "." + type);
            Files.write(path, bytes);
            // save to db
            AttachEntity entity = new AttachEntity();
            entity.setId(key + "." + type); // dasdasd-dasdasda-asdasda-asdasd.jpg
            entity.setPath(pathFolder); // 2024/06/08
            entity.setOriginalName(file.getOriginalFilename());
            entity.setSize(file.getSize());
            entity.setType(type);
            attachRepository.save(entity);

            return toDTO(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] open_general(String attachId) {
        byte[] data;
        try {
            AttachEntity entity = getAttach(attachId);
            String path = entity.getPath() + "/" + attachId;
            Path file = Paths.get(attachUrl + path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public AttachEntity getAttach(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Attach not found");
        });
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2024/06/08
    }

    public String getType(String fileName) { // mp3/jpg/npg/mp4.....
        // zari.mazgi.jpg
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setOriginalName(entity.getOriginalName());
        dto.setSize(entity.getSize());
//        dto.setType(entity.getType());
//        dto.setCreatedData(entity.getCreatedData());
        dto.setUrl(serverUrl + "/attach/open/" + entity.getId());
        return dto;
    }

    public ResponseEntity download(String attachId) {
        try {
            AttachEntity entity = getAttach(attachId);
            String path = entity.getPath() + "/" + attachId;
            Path file = Paths.get(attachUrl + path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public AttachDTO getDTOWithURL(String attachId) {
        AttachDTO dto = new AttachDTO();
        dto.setId(attachId);
        dto.setUrl(serverUrl + "/attach/open/" + attachId);
        return dto;
    }

    public void deleteAttach(String fileName) {
        try {
            AttachEntity entity = getAttach(fileName);
            File file = new File(attachUrl  + entity.getPath() + "/" + fileName);

            if (!file.delete()) {
                throw new AppBadException("File not deleted");
            }else {
                attachRepository.delete(entity);
                System.out.println("File deleted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PageImpl<AttachDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<AttachEntity> mapperList = attachRepository.findAll(pageable);
        return new PageImpl<>(iterateStream(mapperList.getContent()), mapperList.getPageable(), mapperList.getTotalElements());

    }

    private List<AttachDTO> iterateStream(List<AttachEntity> attaches) {
        return attaches.stream()
                .map(this::toDTO)
                .toList();
    }

    public byte[] load(String attachId) {
        BufferedImage originalImage;
        try {
            // read from db
            AttachEntity entity = getAttach(attachId);
            originalImage = ImageIO.read(new File(attachUrl + entity.getPath() + "/" + attachId));
            // read from system
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, entity.getType(), baos);

            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
