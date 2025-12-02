package com.delcapital.entity;


import com.delcapital.Util.AuditEntity;
import com.delcapital.enumeration.FiRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "fi_requests", indexes = {
        @Index(name = "idx_fi_request_id", columnList = "fiRequestId"),
        @Index(name = "idx_consent_request_id", columnList = "consentRequestId"),
        @Index(name = "idx_status", columnList = "status")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiRequest extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String fiRequestId;

    @Column(nullable = false)
    private String consentRequestId;

    @Column(nullable = false)
    private LocalDateTime fiStartDate;

    @Column(nullable = false)
    private LocalDateTime fiEndDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FiRequestStatus status;

    @Column(columnDefinition = "TEXT")
    private String accountsData;


}