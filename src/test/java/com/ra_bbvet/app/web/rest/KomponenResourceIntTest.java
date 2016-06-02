package com.ra_bbvet.app.web.rest;

import com.ra_bbvet.app.RaBbvetApp;
import com.ra_bbvet.app.domain.Komponen;
import com.ra_bbvet.app.repository.KomponenRepository;

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
 * Test class for the KomponenResource REST controller.
 *
 * @see KomponenResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RaBbvetApp.class)
@WebAppConfiguration
@IntegrationTest
public class KomponenResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_KD_KOMPONEN = "AAAAA";
    private static final String UPDATED_KD_KOMPONEN = "BBBBB";
    private static final String DEFAULT_KOMPONEN = "AAAAA";
    private static final String UPDATED_KOMPONEN = "BBBBB";

    private static final Long DEFAULT_BIAYA_KOMPONEN = 1L;
    private static final Long UPDATED_BIAYA_KOMPONEN = 2L;

    private static final ZonedDateTime DEFAULT_KOMPONEN_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_KOMPONEN_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_KOMPONEN_UPDATE_STR = dateTimeFormatter.format(DEFAULT_KOMPONEN_UPDATE);

    @Inject
    private KomponenRepository komponenRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKomponenMockMvc;

    private Komponen komponen;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KomponenResource komponenResource = new KomponenResource();
        ReflectionTestUtils.setField(komponenResource, "komponenRepository", komponenRepository);
        this.restKomponenMockMvc = MockMvcBuilders.standaloneSetup(komponenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        komponen = new Komponen();
        komponen.setKd_komponen(DEFAULT_KD_KOMPONEN);
        komponen.setKomponen(DEFAULT_KOMPONEN);
        komponen.setBiaya_komponen(DEFAULT_BIAYA_KOMPONEN);
        komponen.setKomponen_update(DEFAULT_KOMPONEN_UPDATE);
    }

    @Test
    @Transactional
    public void createKomponen() throws Exception {
        int databaseSizeBeforeCreate = komponenRepository.findAll().size();

        // Create the Komponen

        restKomponenMockMvc.perform(post("/api/komponens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(komponen)))
                .andExpect(status().isCreated());

        // Validate the Komponen in the database
        List<Komponen> komponens = komponenRepository.findAll();
        assertThat(komponens).hasSize(databaseSizeBeforeCreate + 1);
        Komponen testKomponen = komponens.get(komponens.size() - 1);
        assertThat(testKomponen.getKd_komponen()).isEqualTo(DEFAULT_KD_KOMPONEN);
        assertThat(testKomponen.getKomponen()).isEqualTo(DEFAULT_KOMPONEN);
        assertThat(testKomponen.getBiaya_komponen()).isEqualTo(DEFAULT_BIAYA_KOMPONEN);
        assertThat(testKomponen.getKomponen_update()).isEqualTo(DEFAULT_KOMPONEN_UPDATE);
    }

    @Test
    @Transactional
    public void getAllKomponens() throws Exception {
        // Initialize the database
        komponenRepository.saveAndFlush(komponen);

        // Get all the komponens
        restKomponenMockMvc.perform(get("/api/komponens?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(komponen.getId().intValue())))
                .andExpect(jsonPath("$.[*].kd_komponen").value(hasItem(DEFAULT_KD_KOMPONEN.toString())))
                .andExpect(jsonPath("$.[*].komponen").value(hasItem(DEFAULT_KOMPONEN.toString())))
                .andExpect(jsonPath("$.[*].biaya_komponen").value(hasItem(DEFAULT_BIAYA_KOMPONEN.intValue())))
                .andExpect(jsonPath("$.[*].komponen_update").value(hasItem(DEFAULT_KOMPONEN_UPDATE_STR)));
    }

    @Test
    @Transactional
    public void getKomponen() throws Exception {
        // Initialize the database
        komponenRepository.saveAndFlush(komponen);

        // Get the komponen
        restKomponenMockMvc.perform(get("/api/komponens/{id}", komponen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(komponen.getId().intValue()))
            .andExpect(jsonPath("$.kd_komponen").value(DEFAULT_KD_KOMPONEN.toString()))
            .andExpect(jsonPath("$.komponen").value(DEFAULT_KOMPONEN.toString()))
            .andExpect(jsonPath("$.biaya_komponen").value(DEFAULT_BIAYA_KOMPONEN.intValue()))
            .andExpect(jsonPath("$.komponen_update").value(DEFAULT_KOMPONEN_UPDATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingKomponen() throws Exception {
        // Get the komponen
        restKomponenMockMvc.perform(get("/api/komponens/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKomponen() throws Exception {
        // Initialize the database
        komponenRepository.saveAndFlush(komponen);
        int databaseSizeBeforeUpdate = komponenRepository.findAll().size();

        // Update the komponen
        Komponen updatedKomponen = new Komponen();
        updatedKomponen.setId(komponen.getId());
        updatedKomponen.setKd_komponen(UPDATED_KD_KOMPONEN);
        updatedKomponen.setKomponen(UPDATED_KOMPONEN);
        updatedKomponen.setBiaya_komponen(UPDATED_BIAYA_KOMPONEN);
        updatedKomponen.setKomponen_update(UPDATED_KOMPONEN_UPDATE);

        restKomponenMockMvc.perform(put("/api/komponens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKomponen)))
                .andExpect(status().isOk());

        // Validate the Komponen in the database
        List<Komponen> komponens = komponenRepository.findAll();
        assertThat(komponens).hasSize(databaseSizeBeforeUpdate);
        Komponen testKomponen = komponens.get(komponens.size() - 1);
        assertThat(testKomponen.getKd_komponen()).isEqualTo(UPDATED_KD_KOMPONEN);
        assertThat(testKomponen.getKomponen()).isEqualTo(UPDATED_KOMPONEN);
        assertThat(testKomponen.getBiaya_komponen()).isEqualTo(UPDATED_BIAYA_KOMPONEN);
        assertThat(testKomponen.getKomponen_update()).isEqualTo(UPDATED_KOMPONEN_UPDATE);
    }

    @Test
    @Transactional
    public void deleteKomponen() throws Exception {
        // Initialize the database
        komponenRepository.saveAndFlush(komponen);
        int databaseSizeBeforeDelete = komponenRepository.findAll().size();

        // Get the komponen
        restKomponenMockMvc.perform(delete("/api/komponens/{id}", komponen.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Komponen> komponens = komponenRepository.findAll();
        assertThat(komponens).hasSize(databaseSizeBeforeDelete - 1);
    }
}
