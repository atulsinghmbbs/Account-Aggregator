package com.delcapital.repository;

import com.delcapital.entity.FiData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiDataRepository extends JpaRepository<FiData, Long> {

    List<FiData> findByFiRequestId(String fiRequestId);

    List<FiData> findByAccountId(String accountId);
}
