package com.delcapital.service;

import com.delcapital.entity.AuditLog;

import java.util.List;

public interface AuditService {
    public void logAction(String entityType, String entityId, String action, String details);
    public List<AuditLog> getAuditLogs(String entityType, String entityId);
}
