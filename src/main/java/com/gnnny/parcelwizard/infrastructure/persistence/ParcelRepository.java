package com.gnnny.parcelwizard.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<ParcelEntity, Long> {

}
