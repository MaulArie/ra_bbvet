package com.ra_bbvet.app.repository;

import com.ra_bbvet.app.domain.Subkomponen;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subkomponen entity.
 */
@SuppressWarnings("unused")
public interface SubkomponenRepository extends JpaRepository<Subkomponen,Long> {

}
