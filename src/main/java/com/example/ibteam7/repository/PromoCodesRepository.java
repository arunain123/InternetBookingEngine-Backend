package com.example.ibteam7.repository;

import com.example.ibteam7.entity.PromoCodesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodesRepository extends JpaRepository<PromoCodesEntity, String> {
}
