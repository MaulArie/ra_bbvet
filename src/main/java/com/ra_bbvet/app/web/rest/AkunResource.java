package com.ra_bbvet.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ra_bbvet.app.domain.Akun;
import com.ra_bbvet.app.repository.AkunRepository;
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
 * REST controller for managing Akun.
 */
@RestController
@RequestMapping("/api")
public class AkunResource {

    private final Logger log = LoggerFactory.getLogger(AkunResource.class);
        
    @Inject
    private AkunRepository akunRepository;
    
    /**
     * POST  /akuns : Create a new akun.
     *
     * @param akun the akun to create
     * @return the ResponseEntity with status 201 (Created) and with body the new akun, or with status 400 (Bad Request) if the akun has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/akuns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Akun> createAkun(@RequestBody Akun akun) throws URISyntaxException {
        log.debug("REST request to save Akun : {}", akun);
        if (akun.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("akun", "idexists", "A new akun cannot already have an ID")).body(null);
        }
        Akun result = akunRepository.save(akun);
        return ResponseEntity.created(new URI("/api/akuns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("akun", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /akuns : Updates an existing akun.
     *
     * @param akun the akun to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated akun,
     * or with status 400 (Bad Request) if the akun is not valid,
     * or with status 500 (Internal Server Error) if the akun couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/akuns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Akun> updateAkun(@RequestBody Akun akun) throws URISyntaxException {
        log.debug("REST request to update Akun : {}", akun);
        if (akun.getId() == null) {
            return createAkun(akun);
        }
        Akun result = akunRepository.save(akun);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("akun", akun.getId().toString()))
            .body(result);
    }

    /**
     * GET  /akuns : get all the akuns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of akuns in body
     */
    @RequestMapping(value = "/akuns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Akun> getAllAkuns() {
        log.debug("REST request to get all Akuns");
        List<Akun> akuns = akunRepository.findAll();
        return akuns;
    }

    /**
     * GET  /akuns/:id : get the "id" akun.
     *
     * @param id the id of the akun to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the akun, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/akuns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Akun> getAkun(@PathVariable Long id) {
        log.debug("REST request to get Akun : {}", id);
        Akun akun = akunRepository.findOne(id);
        return Optional.ofNullable(akun)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /akuns/:id : delete the "id" akun.
     *
     * @param id the id of the akun to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/akuns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAkun(@PathVariable Long id) {
        log.debug("REST request to delete Akun : {}", id);
        akunRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("akun", id.toString())).build();
    }

}
