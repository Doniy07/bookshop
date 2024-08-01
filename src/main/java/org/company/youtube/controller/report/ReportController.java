package org.company.youtube.controller.report;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.report.ReportRequest;
import org.company.youtube.dto.report.ReportResponse;
import org.company.youtube.usecase.ReportUseCase;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportUseCase<ReportRequest, ReportResponse> reportUseCase;

//      1. Create repost (USER)

    @GetMapping("/create")
    public ResponseEntity<ApiResponse<String>> create(ReportRequest request) {
        return ResponseEntity.ok().body(reportUseCase.create(request));
    }

//    2. ReportList Pagination ADMIN
//        ReportInfo

    @GetMapping("/adm/report-list")
    public ResponseEntity<ApiResponse<Page<ReportResponse>>> commentList(@RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(reportUseCase.pagination(page - 1, size, null));
    }

// 3. Remove Report by id (ADMIN)

    @PutMapping("/adm/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String id) {
        return ResponseEntity.ok(reportUseCase.delete(id));
    }

//    4. Report List By User id (ADMIN)
//        ReportInfo

    @GetMapping("/adm/report-list/{userId}")
    public ResponseEntity<ApiResponse<Page<ReportResponse>>> commentListByUser(@RequestParam(defaultValue = "1") int page,
                                                                               @RequestParam(defaultValue = "5") int size,
                                                                               @PathVariable String userId) {
        return ResponseEntity.ok(reportUseCase.pagination(page - 1, size, userId));
    }
}
