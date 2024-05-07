package com.example.ibteam7.repository;

import com.example.ibteam7.entity.RdsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RdsRepository extends JpaRepository<RdsEntity, Integer> {


}
