package com.ra_bbvet.app.repository;

import com.ra_bbvet.app.domain.Detail;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Detail entity.
 */
@SuppressWarnings("unused")
public interface DetailRepository extends JpaRepository<Detail,Long> {

}
