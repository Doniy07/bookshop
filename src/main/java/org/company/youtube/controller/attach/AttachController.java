package org.company.youtube.controller.attach;


import org.company.youtube.dto.attach.AttachDTO;
import org.company.youtube.service.attach.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachController {

    private final AttachService attachService;

    public AttachController(AttachService attachService) {
        this.attachService = attachService;
    }


    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("image") MultipartFile file) {
        AttachDTO fileName = attachService.saveAttach(file);
        return ResponseEntity.ok().body(fileName);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("fileName") String fileName) {
        attachService.deleteAttach(fileName);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] openGeneral(@PathVariable("fileName") String fileName) {
        return attachService.open_general(fileName);
    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        return attachService.download(fileName);
    }


}
