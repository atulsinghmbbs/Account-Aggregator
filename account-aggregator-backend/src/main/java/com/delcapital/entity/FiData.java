package com.delcapital.entity;


import com.delcapital.Util.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "fi_data", indexes = {
        @Index(name = "idx_fi_request_id", columnList = "fiRequestId"),
        @Index(name = "idx_account_id", columnList = "accountId")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiData extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fiRequestId;

    @Column(nullable = false)
    private String accountId;

    private String fiDataId;

    @Column(nullable = false)
    private String fipId;

    @Column(nullable = false)
    private String fiType;

    private String maskedAccountNumber;

    @Column(columnDefinition = "TEXT")
    private String rawFiData;

    private Boolean accountAnalyticsAvailable;

    private Boolean accountSubAnalyticsAvailable;


}
