package com.ra_bbvet.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ra_bbvet.app.domain.Komponen;
import com.ra_bbvet.app.repository.KomponenRepository;
import com.ra_bbvet.app.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Komponen.
 */
@RestController
@RequestMapping("/api")
public class KomponenResource {

    private final Logger log = LoggerFactory.getLogger(KomponenResource.class);
        
    @Inject
    private KomponenRepository komponenRepository;
    
    /**
     * POST  /komponens : Create a new komponen.
     *
     * @param komponen the komponen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new komponen, or with status 400 (Bad Request) if the komponen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/komponens",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Komponen> createKomponen(@RequestBody Komponen komponen) throws URISyntaxException {
        log.debug("REST request to save Komponen : {}", komponen);
        if (komponen.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("komponen", "idexists", "A new komponen cannot already have an ID")).body(null);
        }
        Komponen result = komponenRepository.save(komponen);
        return ResponseEntity.created(new URI("/api/komponens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("komponen", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /komponens : Updates an existing komponen.
     *
     * @param komponen the komponen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated komponen,
     * or with status 400 (Bad Request) if the komponen is not valid,
     * or with status 500 (Internal Server Error) if the komponen couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/komponens",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Komponen> updateKomponen(@RequestBody Komponen komponen) throws URISyntaxException {
        log.debug("REST request to update Komponen : {}", komponen);
        if (komponen.getId() == null) {
            return createKomponen(komponen);
        }
        Komponen result = komponenRepository.save(komponen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("komponen", komponen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /komponens : get all the komponens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of komponens in body
     */
    @RequestMapping(value = "/komponens",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Komponen> getAllKomponens() {
        log.debug("REST request to get all Komponens");
        List<Komponen> komponens = komponenRepository.findAll();
        return komponens;
    }

    /**
     * GET  /komponens/:id : get the "id" komponen.
     *
     * @param id the id of the komponen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the komponen, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/komponens/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Komponen> getKomponen(@PathVariable Long id) {
        log.debug("REST request to get Komponen : {}", id);
        Komponen komponen = komponenRepository.findOne(id);
        return Optional.ofNullable(komponen)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /komponens/:id : delete the "id" komponen.
     *
     * @param id the id of the komponen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/komponens/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKomponen(@PathVariable Long id) {
        log.debug("REST request to delete Komponen : {}", id);
        komponenRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("komponen", id.toString())).build();
    }

}
