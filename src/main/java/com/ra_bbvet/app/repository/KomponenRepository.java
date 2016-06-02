package com.ra_bbvet.app.repository;

import com.ra_bbvet.app.domain.Komponen;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Komponen entity.
 */
@SuppressWarnings("unused")
public interface KomponenRepository extends JpaRepository<Komponen,Long> {

}
