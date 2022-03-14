package com.example.websitedemo.map;

import com.example.websitedemo.entity.Benefit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BenefitMapper {
    Benefit getBenefitByName(String material);
}
