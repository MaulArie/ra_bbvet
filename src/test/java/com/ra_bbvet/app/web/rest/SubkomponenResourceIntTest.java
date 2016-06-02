package com.ra_bbvet.app.web.rest;

import com.ra_bbvet.app.RaBbvetApp;
import com.ra_bbvet.app.domain.Subkomponen;
import com.ra_bbvet.app.repository.SubkomponenRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SubkomponenResource REST controller.
 *
 * @see SubkomponenResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RaBbvetApp.class)
@WebAppConfiguration
@IntegrationTest
public class SubkomponenResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_KD_SUBKOMPONEN = "AAAAA";
    private static final String UPDATED_KD_SUBKOMPONEN = "BBBBB";
    private static final String DEFAULT_SUBKOMPONEN = "AAAAA";
    private static final String UPDATED_SUBKOMPONEN = "BBBBB";

    private static final Long DEFAULT_BIAYA_SUBKOMPONEN = 1L;
    private static final Long UPDATED_BIAYA_SUBKOMPONEN = 2L;

    private static final ZonedDateTime DEFAULT_SUBKOMPONEN_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SUBKOMPONEN_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SUBKOMPONEN_UPDATE_STR = dateTimeFormatter.format(DEFAULT_SUBKOMPONEN_UPDATE);

    @Inject
    private SubkomponenRepository subkomponenRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubkomponenMockMvc;

    private Subkomponen subkomponen;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubkomponenResource subkomponenResource = new SubkomponenResource();
        ReflectionTestUtils.setField(subkomponenResource, "subkomponenRepository", subkomponenRepository);
        this.restSubkomponenMockMvc = MockMvcBuilders.standaloneSetup(subkomponenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subkomponen = new Subkomponen();
        subkomponen.setKd_subkomponen(DEFAULT_KD_SUBKOMPONEN);
        subkomponen.setSubkomponen(DEFAULT_SUBKOMPONEN);
        subkomponen.setBiaya_subkomponen(DEFAULT_BIAYA_SUBKOMPONEN);
        subkomponen.setSubkomponen_update(DEFAULT_SUBKOMPONEN_UPDATE);
    }

    @Test
    @Transactional
    public void createSubkomponen() throws Exception {
        int databaseSizeBeforeCreate = subkomponenRepository.findAll().size();

        // Create the Subkomponen

        restSubkomponenMockMvc.perform(post("/api/subkomponens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subkomponen)))
                .andExpect(status().isCreated());

        // Validate the Subkomponen in the database
        List<Subkomponen> subkomponens = subkomponenRepository.findAll();
        assertThat(subkomponens).hasSize(databaseSizeBeforeCreate + 1);
        Subkomponen testSubkomponen = subkomponens.get(subkomponens.size() - 1);
        assertThat(testSubkomponen.getKd_subkomponen()).isEqualTo(DEFAULT_KD_SUBKOMPONEN);
        assertThat(testSubkomponen.getSubkomponen()).isEqualTo(DEFAULT_SUBKOMPONEN);
        assertThat(testSubkomponen.getBiaya_subkomponen()).isEqualTo(DEFAULT_BIAYA_SUBKOMPONEN);
        assertThat(testSubkomponen.getSubkomponen_update()).isEqualTo(DEFAULT_SUBKOMPONEN_UPDATE);
    }

    @Test
    @Transactional
    public void getAllSubkomponens() throws Exception {
        // Initialize the database
        subkomponenRepository.saveAndFlush(subkomponen);

        // Get all the subkomponens
        restSubkomponenMockMvc.perform(get("/api/subkomponens?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subkomponen.getId().intValue())))
                .andExpect(jsonPath("$.[*].kd_subkomponen").value(hasItem(DEFAULT_KD_SUBKOMPONEN.toString())))
                .andExpect(jsonPath("$.[*].subkomponen").value(hasItem(DEFAULT_SUBKOMPONEN.toString())))
                .andExpect(jsonPath("$.[*].biaya_subkomponen").value(hasItem(DEFAULT_BIAYA_SUBKOMPONEN.intValue())))
                .andExpect(jsonPath("$.[*].subkomponen_update").value(hasItem(DEFAULT_SUBKOMPONEN_UPDATE_STR)));
    }

    @Test
    @Transactional
    public void getSubkomponen() throws Exception {
        // Initialize the database
        subkomponenRepository.saveAndFlush(subkomponen);

        // Get the subkomponen
        restSubkomponenMockMvc.perform(get("/api/subkomponens/{id}", subkomponen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subkomponen.getId().intValue()))
            .andExpect(jsonPath("$.kd_subkomponen").value(DEFAULT_KD_SUBKOMPONEN.toString()))
            .andExpect(jsonPath("$.subkomponen").value(DEFAULT_SUBKOMPONEN.toString()))
            .andExpect(jsonPath("$.biaya_subkomponen").value(DEFAULT_BIAYA_SUBKOMPONEN.intValue()))
            .andExpect(jsonPath("$.subkomponen_update").value(DEFAULT_SUBKOMPONEN_UPDATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSubkomponen() throws Exception {
        // Get the subkomponen
        restSubkomponenMockMvc.perform(get("/api/subkomponens/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubkomponen() throws Exception {
        // Initialize the database
        subkomponenRepository.saveAndFlush(subkomponen);
        int databaseSizeBeforeUpdate = subkomponenRepository.findAll().size();

        // Update the subkomponen
        Subkomponen updatedSubkomponen = new Subkomponen();
        updatedSubkomponen.setId(subkomponen.getId());
        updatedSubkomponen.setKd_subkomponen(UPDATED_KD_SUBKOMPONEN);
        updatedSubkomponen.setSubkomponen(UPDATED_SUBKOMPONEN);
        updatedSubkomponen.setBiaya_subkomponen(UPDATED_BIAYA_SUBKOMPONEN);
        updatedSubkomponen.setSubkomponen_update(UPDATED_SUBKOMPONEN_UPDATE);

        restSubkomponenMockMvc.perform(put("/api/subkomponens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSubkomponen)))
                .andExpect(status().isOk());

        // Validate the Subkomponen in the database
        List<Subkomponen> subkomponens = subkomponenRepository.findAll();
        assertThat(subkomponens).hasSize(databaseSizeBeforeUpdate);
        Subkomponen testSubkomponen = subkomponens.get(subkomponens.size() - 1);
        assertThat(testSubkomponen.getKd_subkomponen()).isEqualTo(UPDATED_KD_SUBKOMPONEN);
        assertThat(testSubkomponen.getSubkomponen()).isEqualTo(UPDATED_SUBKOMPONEN);
        assertThat(testSubkomponen.getBiaya_subkomponen()).isEqualTo(UPDATED_BIAYA_SUBKOMPONEN);
        assertThat(testSubkomponen.getSubkomponen_update()).isEqualTo(UPDATED_SUBKOMPONEN_UPDATE);
    }

    @Test
    @Transactional
    public void deleteSubkomponen() throws Exception {
        // Initialize the database
        subkomponenRepository.saveAndFlush(subkomponen);
        int databaseSizeBeforeDelete = subkomponenRepository.findAll().size();

        // Get the subkomponen
        restSubkomponenMockMvc.perform(delete("/api/subkomponens/{id}", subkomponen.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Subkomponen> subkomponens = subkomponenRepository.findAll();
        assertThat(subkomponens).hasSize(databaseSizeBeforeDelete - 1);
    }
}
