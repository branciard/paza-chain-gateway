package com.branciard.paza.pazachaingateway.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.branciard.paza.pazachaingateway.service.ChainUserService;
import com.branciard.paza.pazachaingateway.web.rest.util.HeaderUtil;
import com.branciard.paza.pazachaingateway.web.rest.util.PaginationUtil;
import com.branciard.paza.pazachaingateway.service.dto.ChainUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ChainUser.
 */
@RestController
@RequestMapping("/api")
public class ChainUserResource {

    private final Logger log = LoggerFactory.getLogger(ChainUserResource.class);
        
    @Inject
    private ChainUserService chainUserService;

    /**
     * POST  /chain-users : Create a new chainUser.
     *
     * @param chainUserDTO the chainUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chainUserDTO, or with status 400 (Bad Request) if the chainUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chain-users")
    @Timed
    public ResponseEntity<ChainUserDTO> createChainUser(@RequestBody ChainUserDTO chainUserDTO) throws URISyntaxException {
        log.debug("REST request to save ChainUser : {}", chainUserDTO);
        if (chainUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chainUser", "idexists", "A new chainUser cannot already have an ID")).body(null);
        }
        ChainUserDTO result = chainUserService.save(chainUserDTO);
        return ResponseEntity.created(new URI("/api/chain-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chainUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chain-users : Updates an existing chainUser.
     *
     * @param chainUserDTO the chainUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chainUserDTO,
     * or with status 400 (Bad Request) if the chainUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the chainUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chain-users")
    @Timed
    public ResponseEntity<ChainUserDTO> updateChainUser(@RequestBody ChainUserDTO chainUserDTO) throws URISyntaxException {
        log.debug("REST request to update ChainUser : {}", chainUserDTO);
        if (chainUserDTO.getId() == null) {
            return createChainUser(chainUserDTO);
        }
        ChainUserDTO result = chainUserService.save(chainUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chainUser", chainUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chain-users : get all the chainUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chainUsers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/chain-users")
    @Timed
    public ResponseEntity<List<ChainUserDTO>> getAllChainUsers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ChainUsers");
        Page<ChainUserDTO> page = chainUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chain-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chain-users/:id : get the "id" chainUser.
     *
     * @param id the id of the chainUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chainUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/chain-users/{id}")
    @Timed
    public ResponseEntity<ChainUserDTO> getChainUser(@PathVariable Long id) {
        log.debug("REST request to get ChainUser : {}", id);
        ChainUserDTO chainUserDTO = chainUserService.findOne(id);
        return Optional.ofNullable(chainUserDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chain-users/:id : delete the "id" chainUser.
     *
     * @param id the id of the chainUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chain-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteChainUser(@PathVariable Long id) {
        log.debug("REST request to delete ChainUser : {}", id);
        chainUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chainUser", id.toString())).build();
    }

}
