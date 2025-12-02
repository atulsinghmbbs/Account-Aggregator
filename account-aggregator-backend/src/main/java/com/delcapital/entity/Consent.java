package com.delcapital.entity;

import com.delcapital.Util.AuditEntity;
import com.delcapital.enumeration.ConsentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "consent_requests", indexes = {
        @Index(name = "idx_consent_request_id", columnList = "consentRequestId"),
        @Index(name = "idx_customer_identifier", columnList = "customerIdentifier"),
        @Index(name = "idx_status", columnList = "status")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consent  extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String consentRequestId;

    @Column(nullable = false)
    private String customerName;

    private String customerEmail;

    @Column(nullable = false)
    private String customerMobile;

    @Column(nullable = false)
    private String customerIdentifier;

    private String customerRefId;

    @Column(nullable = false)
    private String templateId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsentStatus status;

    @Column(nullable = false)
    private LocalDateTime consentStartDate;

    @Column(nullable = false)
    private LocalDateTime consentExpiryDate;

    @Column(nullable = false)
    private LocalDateTime fiStartDate;

    @Column(nullable = false)
    private LocalDateTime fiEndDate;

    @Column(columnDefinition = "TEXT")
    private String metaData;

    private String aaId;

    private String gatewayTokenId;

}