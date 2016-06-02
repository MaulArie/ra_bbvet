package com.ra_bbvet.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ra_bbvet.app.domain.Subkomponen;
import com.ra_bbvet.app.repository.SubkomponenRepository;
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
 * REST controller for managing Subkomponen.
 */
@RestController
@RequestMapping("/api")
public class SubkomponenResource {

    private final Logger log = LoggerFactory.getLogger(SubkomponenResource.class);
        
    @Inject
    private SubkomponenRepository subkomponenRepository;
    
    /**
     * POST  /subkomponens : Create a new subkomponen.
     *
     * @param subkomponen the subkomponen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subkomponen, or with status 400 (Bad Request) if the subkomponen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subkomponens",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subkomponen> createSubkomponen(@RequestBody Subkomponen subkomponen) throws URISyntaxException {
        log.debug("REST request to save Subkomponen : {}", subkomponen);
        if (subkomponen.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("subkomponen", "idexists", "A new subkomponen cannot already have an ID")).body(null);
        }
        Subkomponen result = subkomponenRepository.save(subkomponen);
        return ResponseEntity.created(new URI("/api/subkomponens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subkomponen", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subkomponens : Updates an existing subkomponen.
     *
     * @param subkomponen the subkomponen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subkomponen,
     * or with status 400 (Bad Request) if the subkomponen is not valid,
     * or with status 500 (Internal Server Error) if the subkomponen couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subkomponens",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subkomponen> updateSubkomponen(@RequestBody Subkomponen subkomponen) throws URISyntaxException {
        log.debug("REST request to update Subkomponen : {}", subkomponen);
        if (subkomponen.getId() == null) {
            return createSubkomponen(subkomponen);
        }
        Subkomponen result = subkomponenRepository.save(subkomponen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subkomponen", subkomponen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subkomponens : get all the subkomponens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subkomponens in body
     */
    @RequestMapping(value = "/subkomponens",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Subkomponen> getAllSubkomponens() {
        log.debug("REST request to get all Subkomponens");
        List<Subkomponen> subkomponens = subkomponenRepository.findAll();
        return subkomponens;
    }

    /**
     * GET  /subkomponens/:id : get the "id" subkomponen.
     *
     * @param id the id of the subkomponen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subkomponen, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/subkomponens/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Subkomponen> getSubkomponen(@PathVariable Long id) {
        log.debug("REST request to get Subkomponen : {}", id);
        Subkomponen subkomponen = subkomponenRepository.findOne(id);
        return Optional.ofNullable(subkomponen)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subkomponens/:id : delete the "id" subkomponen.
     *
     * @param id the id of the subkomponen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/subkomponens/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubkomponen(@PathVariable Long id) {
        log.debug("REST request to delete Subkomponen : {}", id);
        subkomponenRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subkomponen", id.toString())).build();
    }

}
