package com.delcapital.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConsentStatus {
    PENDING,
    REQUESTED,
    APPROVED,
    ACTIVE,
    PAUSED,
    REJECTED,
    REVOKED,
    REQUEST_EXPIRED,
    EXPIRED,
    FAILED,
    QUEUED_FOR_RETRY
}
