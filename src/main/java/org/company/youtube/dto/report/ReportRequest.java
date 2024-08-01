package org.company.youtube.dto.report;

import org.company.youtube.enums.ReportType;

public record ReportRequest(
        String content,
        String entityId,
        ReportType type
) {
}
