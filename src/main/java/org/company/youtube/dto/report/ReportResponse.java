package org.company.youtube.dto.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.company.youtube.dto.profile.ProfileDTO;
import org.company.youtube.enums.ReportType;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReportResponse(
    String id,
    String profileId,
    ProfileDTO profile,
    String content,
    String entityId,
    ReportType type,
    Boolean visible,
    LocalDateTime createDate
) {
}
