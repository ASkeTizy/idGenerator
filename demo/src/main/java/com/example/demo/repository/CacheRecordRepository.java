package com.example.demo.repository;

import com.example.demo.entity.CacheRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CacheRecordRepository extends JpaRepository<CacheRecord, String> {
}
