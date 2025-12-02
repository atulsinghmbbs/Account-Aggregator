package com.delcapital.service.serviceImpl;

import com.delcapital.entity.AuditLog;
import com.delcapital.repository.AuditLogRepository;
import com.delcapital.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void logAction(String entityType, String entityId, String action, String details) {
        AuditLog auditLog = AuditLog.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .details(details)
                .build();

        auditLogRepository.save(auditLog);
        log.debug("Audit log created: {} - {} - {}", entityType, entityId, action);
    }

    @Transactional(readOnly = true)
    public List<AuditLog> getAuditLogs(String entityType, String entityId) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }
}
