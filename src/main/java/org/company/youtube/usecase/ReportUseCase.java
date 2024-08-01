package org.company.youtube.usecase;

import org.company.youtube.dto.ApiResponse;
import org.springframework.data.domain.Page;

public interface ReportUseCase<REQUEST, RESPONSE> {

    ApiResponse<String> create(REQUEST request);

    ApiResponse<Page<RESPONSE>> pagination( int page, int size, String profileId);

    ApiResponse<String> delete(String reportId);
}
