package com.branciard.paza.pazachaingateway.web.rest;

import com.branciard.paza.pazachaingateway.PazachaingatewayApp;

import com.branciard.paza.pazachaingateway.config.SecurityBeanOverrideConfiguration;

import com.branciard.paza.pazachaingateway.domain.ChainUser;
import com.branciard.paza.pazachaingateway.repository.ChainUserRepository;
import com.branciard.paza.pazachaingateway.service.ChainUserService;
import com.branciard.paza.pazachaingateway.service.dto.ChainUserDTO;
import com.branciard.paza.pazachaingateway.service.mapper.ChainUserMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChainUserResource REST controller.
 *
 * @see ChainUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PazachaingatewayApp.class, SecurityBeanOverrideConfiguration.class})
public class ChainUserResourceIntTest {

    private static final Long DEFAULT_U_AA_USER_ID = 1L;
    private static final Long UPDATED_U_AA_USER_ID = 2L;

    private static final String DEFAULT_E_CERT = "AAAAAAAAAA";
    private static final String UPDATED_E_CERT = "BBBBBBBBBB";

    @Inject
    private ChainUserRepository chainUserRepository;

    @Inject
    private ChainUserMapper chainUserMapper;

    @Inject
    private ChainUserService chainUserService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restChainUserMockMvc;

    private ChainUser chainUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChainUserResource chainUserResource = new ChainUserResource();
        ReflectionTestUtils.setField(chainUserResource, "chainUserService", chainUserService);
        this.restChainUserMockMvc = MockMvcBuilders.standaloneSetup(chainUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChainUser createEntity(EntityManager em) {
        ChainUser chainUser = new ChainUser()
                .uAaUserId(DEFAULT_U_AA_USER_ID)
                .eCert(DEFAULT_E_CERT);
        return chainUser;
    }

    @Before
    public void initTest() {
        chainUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createChainUser() throws Exception {
        int databaseSizeBeforeCreate = chainUserRepository.findAll().size();

        // Create the ChainUser
        ChainUserDTO chainUserDTO = chainUserMapper.chainUserToChainUserDTO(chainUser);

        restChainUserMockMvc.perform(post("/api/chain-users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chainUserDTO)))
                .andExpect(status().isCreated());

        // Validate the ChainUser in the database
        List<ChainUser> chainUsers = chainUserRepository.findAll();
        assertThat(chainUsers).hasSize(databaseSizeBeforeCreate + 1);
        ChainUser testChainUser = chainUsers.get(chainUsers.size() - 1);
        assertThat(testChainUser.getuAaUserId()).isEqualTo(DEFAULT_U_AA_USER_ID);
        assertThat(testChainUser.geteCert()).isEqualTo(DEFAULT_E_CERT);
    }

    @Test
    @Transactional
    public void getAllChainUsers() throws Exception {
        // Initialize the database
        chainUserRepository.saveAndFlush(chainUser);

        // Get all the chainUsers
        restChainUserMockMvc.perform(get("/api/chain-users?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(chainUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].uAaUserId").value(hasItem(DEFAULT_U_AA_USER_ID.intValue())))
                .andExpect(jsonPath("$.[*].eCert").value(hasItem(DEFAULT_E_CERT.toString())));
    }

    @Test
    @Transactional
    public void getChainUser() throws Exception {
        // Initialize the database
        chainUserRepository.saveAndFlush(chainUser);

        // Get the chainUser
        restChainUserMockMvc.perform(get("/api/chain-users/{id}", chainUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chainUser.getId().intValue()))
            .andExpect(jsonPath("$.uAaUserId").value(DEFAULT_U_AA_USER_ID.intValue()))
            .andExpect(jsonPath("$.eCert").value(DEFAULT_E_CERT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChainUser() throws Exception {
        // Get the chainUser
        restChainUserMockMvc.perform(get("/api/chain-users/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChainUser() throws Exception {
        // Initialize the database
        chainUserRepository.saveAndFlush(chainUser);
        int databaseSizeBeforeUpdate = chainUserRepository.findAll().size();

        // Update the chainUser
        ChainUser updatedChainUser = chainUserRepository.findOne(chainUser.getId());
        updatedChainUser
                .uAaUserId(UPDATED_U_AA_USER_ID)
                .eCert(UPDATED_E_CERT);
        ChainUserDTO chainUserDTO = chainUserMapper.chainUserToChainUserDTO(updatedChainUser);

        restChainUserMockMvc.perform(put("/api/chain-users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chainUserDTO)))
                .andExpect(status().isOk());

        // Validate the ChainUser in the database
        List<ChainUser> chainUsers = chainUserRepository.findAll();
        assertThat(chainUsers).hasSize(databaseSizeBeforeUpdate);
        ChainUser testChainUser = chainUsers.get(chainUsers.size() - 1);
        assertThat(testChainUser.getuAaUserId()).isEqualTo(UPDATED_U_AA_USER_ID);
        assertThat(testChainUser.geteCert()).isEqualTo(UPDATED_E_CERT);
    }

    @Test
    @Transactional
    public void deleteChainUser() throws Exception {
        // Initialize the database
        chainUserRepository.saveAndFlush(chainUser);
        int databaseSizeBeforeDelete = chainUserRepository.findAll().size();

        // Get the chainUser
        restChainUserMockMvc.perform(delete("/api/chain-users/{id}", chainUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ChainUser> chainUsers = chainUserRepository.findAll();
        assertThat(chainUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
