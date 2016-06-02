package com.ra_bbvet.app.repository;

import com.ra_bbvet.app.domain.Akun;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Akun entity.
 */
@SuppressWarnings("unused")
public interface AkunRepository extends JpaRepository<Akun,Long> {

}
