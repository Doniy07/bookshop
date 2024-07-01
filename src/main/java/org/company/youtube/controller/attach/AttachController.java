package org.company.youtube.controller.attach;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.service.attach.AttachService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
@AllArgsConstructor
public class AttachController {

    private final AttachService attachService;

    //   1. Create Attach (upload)

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("image") MultipartFile file) {
        AttachDTO fileName = attachService.saveAttach(file);
        return ResponseEntity.ok().body(fileName);
    }

    //    5. Delete Attach (delete from db and system) (ADMIN)

    @DeleteMapping("/adm/delete/{fileName}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<HttpStatus> delete(@PathVariable("fileName") String fileName) {
        attachService.deleteAttach(fileName);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //    2. Get Attach By Id (Open)

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return this.attachService.load(fileName);
    }

    //    3. Download Attach (Download)

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        return attachService.download(fileName);
    }

    //    4. Attach pagination (ADMIN)
//        id,origen_name,size,url

    @GetMapping("/adm/pagination")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PageImpl<AttachDTO>> pageable(@RequestParam(value = "page", defaultValue = "1") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        PageImpl<AttachDTO> articleDTOList = attachService.pagination(page - 1, size);
        return ResponseEntity.ok().body(articleDTOList);
    }
}
