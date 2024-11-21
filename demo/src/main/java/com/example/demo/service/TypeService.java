package com.example.demo.service;

import com.example.demo.entity.CacheRecord;
import com.example.demo.entity.SerialPool;
import com.example.demo.repository.CacheRecordRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final CacheRecordRepository cacheRecordRepository;

    private final Map<String, SerialPool> currentValues = new HashMap<String, SerialPool>();


   @Transactional
    public Long generateId(String type) {
       var serialPool = currentValues.get(type);
       var limit = serialPool.getMaxId();
       var currentValue = serialPool.getCurrent();
       currentValue++;
       serialPool.setCurrent(currentValue);
        currentValues.put(type, serialPool);
        if (limit <= currentValue) {
            var record = cacheRecordRepository.findById(type).get();
            record.setId(record.getId()+100L);
            serialPool.setMaxId(limit + 100L);
            currentValues.put(type, serialPool);
        }
        return currentValues.get(type).getCurrent();
    }

    @PostConstruct
    public void loadData() {
        var records = cacheRecordRepository.findAll();
        var values = records.stream().collect(Collectors.toMap(CacheRecord::getRole, CacheRecord::getId));
//        limitValues.putAll(values);
        var map = values.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                el->new SerialPool(el.getKey(),el.getValue(),el.getValue())));
        currentValues.putAll(map);
    }
}
