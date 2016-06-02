package com.ra_bbvet.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ra_bbvet.app.domain.Detail;
import com.ra_bbvet.app.repository.DetailRepository;
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
 * REST controller for managing Detail.
 */
@RestController
@RequestMapping("/api")
public class DetailResource {

    private final Logger log = LoggerFactory.getLogger(DetailResource.class);
        
    @Inject
    private DetailRepository detailRepository;
    
    /**
     * POST  /details : Create a new detail.
     *
     * @param detail the detail to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detail, or with status 400 (Bad Request) if the detail has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/details",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Detail> createDetail(@RequestBody Detail detail) throws URISyntaxException {
        log.debug("REST request to save Detail : {}", detail);
        if (detail.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("detail", "idexists", "A new detail cannot already have an ID")).body(null);
        }
        Detail result = detailRepository.save(detail);
        return ResponseEntity.created(new URI("/api/details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("detail", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /details : Updates an existing detail.
     *
     * @param detail the detail to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detail,
     * or with status 400 (Bad Request) if the detail is not valid,
     * or with status 500 (Internal Server Error) if the detail couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/details",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Detail> updateDetail(@RequestBody Detail detail) throws URISyntaxException {
        log.debug("REST request to update Detail : {}", detail);
        if (detail.getId() == null) {
            return createDetail(detail);
        }
        Detail result = detailRepository.save(detail);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("detail", detail.getId().toString()))
            .body(result);
    }

    /**
     * GET  /details : get all the details.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of details in body
     */
    @RequestMapping(value = "/details",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Detail> getAllDetails() {
        log.debug("REST request to get all Details");
        List<Detail> details = detailRepository.findAll();
        return details;
    }

    /**
     * GET  /details/:id : get the "id" detail.
     *
     * @param id the id of the detail to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detail, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/details/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Detail> getDetail(@PathVariable Long id) {
        log.debug("REST request to get Detail : {}", id);
        Detail detail = detailRepository.findOne(id);
        return Optional.ofNullable(detail)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /details/:id : delete the "id" detail.
     *
     * @param id the id of the detail to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/details/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDetail(@PathVariable Long id) {
        log.debug("REST request to delete Detail : {}", id);
        detailRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("detail", id.toString())).build();
    }

}
