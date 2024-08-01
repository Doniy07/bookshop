package org.company.youtube.service.report;

import lombok.RequiredArgsConstructor;
import org.company.youtube.dto.ApiResponse;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.dto.report.ReportRequest;
import org.company.youtube.dto.report.ReportResponse;
import org.company.youtube.entity.report.ReportEntity;
import org.company.youtube.exception.AppBadException;
import org.company.youtube.mapper.RepostInfoMapper;
import org.company.youtube.repository.report.ReportRepository;
import org.company.youtube.usecase.ReportUseCase;
import org.company.youtube.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ReportService implements ReportUseCase<ReportRequest, ReportResponse> {

    private final ReportRepository reportRepository;


    @Override
    public ApiResponse<String> create(ReportRequest request) {

        ReportEntity entity = mapToEntity().apply(request);
        reportRepository.save(entity);
        return ApiResponse.ok(entity.getId());

    }

    @Override
    public ApiResponse<Page<ReportResponse>> pagination(int page, int size, String profileId) {
        Pageable pageable = PageRequest.of(page, size);

        Page<RepostInfoMapper> pageList = reportRepository.findAllBy(profileId, pageable);
        List<ReportResponse> list = pageList.getContent().stream().map(mapperMapToResponse()).toList();

        return ApiResponse.ok(new PageImpl<>(list, pageList.getPageable(), pageList.getTotalElements()));
    }

    @Override
    public ApiResponse<String> delete(String reportId) {
        ReportEntity entity = getReport(reportId);
        entity.setVisible(Boolean.FALSE);
        reportRepository.save(entity);

        return ApiResponse.ok(entity.getId());
    }

    private Function<ReportRequest, ReportEntity> mapToEntity() {
        return entity -> ReportEntity.builder().profileId(SecurityUtil.getProfileId()).content(entity.content()).entityId(entity.entityId()).type(entity.type()).build();
    }

    private Function<RepostInfoMapper, ReportResponse> mapperMapToResponse() {
        return response -> ReportResponse.builder().id(response.getId()).profile(ProfileDTO.builder().id(response.getProfileId()).name(response.getProfileName()).surname(response.getProfileSurname()).photoId(response.getProfilePhotoId()).build()).content(response.getContent()).entityId(response.getEntityId()).type(response.getType()).build();
    }

    private ReportEntity getReport(String reportId) {
        Optional<ReportEntity> optional = reportRepository.findByIdAndVisibleTrue(reportId);
        if (optional.isEmpty()) {
            throw new AppBadException("Report not found");
        }
        return optional.get();
    }

}
