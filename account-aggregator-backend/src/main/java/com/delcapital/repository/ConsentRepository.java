package com.delcapital.repository;

import com.delcapital.entity.Consent;
import com.delcapital.enumeration.ConsentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long> {

    Optional<Consent> findByConsentRequestId(String consentRequestId);

    List<Consent> findByCustomerIdentifier(String customerIdentifier);

    List<Consent> findByStatus(ConsentStatus status);

    boolean existsByConsentRequestId(String consentRequestId);
}
