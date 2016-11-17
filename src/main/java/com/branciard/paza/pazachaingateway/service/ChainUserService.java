package com.branciard.paza.pazachaingateway.service;

import com.branciard.paza.pazachaingateway.domain.ChainUser;
import com.branciard.paza.pazachaingateway.repository.ChainUserRepository;
import com.branciard.paza.pazachaingateway.service.dto.ChainUserDTO;
import com.branciard.paza.pazachaingateway.service.mapper.ChainUserMapper;
import com.branciard.paza.pazachaingateway.web.rest.SomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ChainUser.
 */
@Service
@Transactional
public class ChainUserService {

    private final Logger log = LoggerFactory.getLogger(ChainUserService.class);

    @Inject
    private ChainUserRepository chainUserRepository;

    @Inject
    private ChainUserMapper chainUserMapper;


    @Inject
    private SomeController someController;




    /**
     * Save a chainUser.
     *
     * @param chainUserDTO the entity to save
     * @return the persisted entity
     */
    public ChainUserDTO save(ChainUserDTO chainUserDTO) {
        log.debug("Request to save ChainUser : {}", chainUserDTO);
        ChainUser chainUser = chainUserMapper.chainUserDTOToChainUser(chainUserDTO);
        chainUser = chainUserRepository.save(chainUser);
        try{
            log.error(" pingFabricSdkService start");

            log.error("pingFabricSdkService"+ someController.profileInfo());
            log.error(" pingFabricSdkService end");
        }
        catch(Throwable throwable){
            log.error("pingFabricSdkService",throwable);
        }

        ChainUserDTO result = chainUserMapper.chainUserToChainUserDTO(chainUser);
        return result;
    }

    /**
     *  Get all the chainUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChainUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChainUsers");
        Page<ChainUser> result = chainUserRepository.findAll(pageable);
        return result.map(chainUser -> chainUserMapper.chainUserToChainUserDTO(chainUser));
    }

    /**
     *  Get one chainUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ChainUserDTO findOne(Long id) {
        log.debug("Request to get ChainUser : {}", id);
        ChainUser chainUser = chainUserRepository.findOne(id);
        ChainUserDTO chainUserDTO = chainUserMapper.chainUserToChainUserDTO(chainUser);
        return chainUserDTO;
    }

    /**
     *  Delete the  chainUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChainUser : {}", id);
        chainUserRepository.delete(id);
    }
}
