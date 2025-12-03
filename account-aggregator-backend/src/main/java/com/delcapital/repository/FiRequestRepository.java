package com.delcapital.repository;

import com.delcapital.entity.FiRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FiRequestRepository extends JpaRepository<FiRequest, Long> {

    Optional<FiRequest> findByFiRequestId(String fiRequestId);

    List<FiRequest> findByConsentRequestId(String consentRequestId);

}
