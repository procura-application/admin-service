package com.eprocurement.admin.common.repository;

import com.eprocurement.admin.common.entity.PrefixConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrefixConfigurationRepository extends JpaRepository<PrefixConfigurationEntity,String> {

    Optional<PrefixConfigurationEntity> findFirstByOrderByPrefixConfigurationIdAsc();
}
