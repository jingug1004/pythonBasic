package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Version;


@Repository
@Qualifier(value = "VersionRepository")
public interface VersionRepository extends CrudRepository<Version, Long>{

	public Version findByOsType(String osType);
}
