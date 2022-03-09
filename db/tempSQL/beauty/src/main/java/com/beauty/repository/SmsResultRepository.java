package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.SmsResult;

@Repository
@Qualifier(value = "SmsResultRepository")
public interface SmsResultRepository extends CrudRepository<SmsResult, Long> {

}
