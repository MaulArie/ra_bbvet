package com.ra_bbvet.app.web.rest;

import com.ra_bbvet.app.RaBbvetApp;
import com.ra_bbvet.app.domain.Akun;
import com.ra_bbvet.app.repository.AkunRepository;

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
 * Test class for the AkunResource REST controller.
 *
 * @see AkunResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RaBbvetApp.class)
@WebAppConfiguration
@IntegrationTest
public class AkunResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_KD_AKUN = "AAAAA";
    private static final String UPDATED_KD_AKUN = "BBBBB";
    private static final String DEFAULT_AKUN = "AAAAA";
    private static final String UPDATED_AKUN = "BBBBB";

    private static final Long DEFAULT_BIAYA_AKUN = 1L;
    private static final Long UPDATED_BIAYA_AKUN = 2L;

    private static final ZonedDateTime DEFAULT_AKUN_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_AKUN_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_AKUN_UPDATE_STR = dateTimeFormatter.format(DEFAULT_AKUN_UPDATE);

    @Inject
    private AkunRepository akunRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAkunMockMvc;

    private Akun akun;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AkunResource akunResource = new AkunResource();
        ReflectionTestUtils.setField(akunResource, "akunRepository", akunRepository);
        this.restAkunMockMvc = MockMvcBuilders.standaloneSetup(akunResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        akun = new Akun();
        akun.setKd_akun(DEFAULT_KD_AKUN);
        akun.setAkun(DEFAULT_AKUN);
        akun.setBiaya_akun(DEFAULT_BIAYA_AKUN);
        akun.setAkun_update(DEFAULT_AKUN_UPDATE);
    }

    @Test
    @Transactional
    public void createAkun() throws Exception {
        int databaseSizeBeforeCreate = akunRepository.findAll().size();

        // Create the Akun

        restAkunMockMvc.perform(post("/api/akuns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(akun)))
                .andExpect(status().isCreated());

        // Validate the Akun in the database
        List<Akun> akuns = akunRepository.findAll();
        assertThat(akuns).hasSize(databaseSizeBeforeCreate + 1);
        Akun testAkun = akuns.get(akuns.size() - 1);
        assertThat(testAkun.getKd_akun()).isEqualTo(DEFAULT_KD_AKUN);
        assertThat(testAkun.getAkun()).isEqualTo(DEFAULT_AKUN);
        assertThat(testAkun.getBiaya_akun()).isEqualTo(DEFAULT_BIAYA_AKUN);
        assertThat(testAkun.getAkun_update()).isEqualTo(DEFAULT_AKUN_UPDATE);
    }

    @Test
    @Transactional
    public void getAllAkuns() throws Exception {
        // Initialize the database
        akunRepository.saveAndFlush(akun);

        // Get all the akuns
        restAkunMockMvc.perform(get("/api/akuns?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(akun.getId().intValue())))
                .andExpect(jsonPath("$.[*].kd_akun").value(hasItem(DEFAULT_KD_AKUN.toString())))
                .andExpect(jsonPath("$.[*].akun").value(hasItem(DEFAULT_AKUN.toString())))
                .andExpect(jsonPath("$.[*].biaya_akun").value(hasItem(DEFAULT_BIAYA_AKUN.intValue())))
                .andExpect(jsonPath("$.[*].akun_update").value(hasItem(DEFAULT_AKUN_UPDATE_STR)));
    }

    @Test
    @Transactional
    public void getAkun() throws Exception {
        // Initialize the database
        akunRepository.saveAndFlush(akun);

        // Get the akun
        restAkunMockMvc.perform(get("/api/akuns/{id}", akun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(akun.getId().intValue()))
            .andExpect(jsonPath("$.kd_akun").value(DEFAULT_KD_AKUN.toString()))
            .andExpect(jsonPath("$.akun").value(DEFAULT_AKUN.toString()))
            .andExpect(jsonPath("$.biaya_akun").value(DEFAULT_BIAYA_AKUN.intValue()))
            .andExpect(jsonPath("$.akun_update").value(DEFAULT_AKUN_UPDATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingAkun() throws Exception {
        // Get the akun
        restAkunMockMvc.perform(get("/api/akuns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAkun() throws Exception {
        // Initialize the database
        akunRepository.saveAndFlush(akun);
        int databaseSizeBeforeUpdate = akunRepository.findAll().size();

        // Update the akun
        Akun updatedAkun = new Akun();
        updatedAkun.setId(akun.getId());
        updatedAkun.setKd_akun(UPDATED_KD_AKUN);
        updatedAkun.setAkun(UPDATED_AKUN);
        updatedAkun.setBiaya_akun(UPDATED_BIAYA_AKUN);
        updatedAkun.setAkun_update(UPDATED_AKUN_UPDATE);

        restAkunMockMvc.perform(put("/api/akuns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAkun)))
                .andExpect(status().isOk());

        // Validate the Akun in the database
        List<Akun> akuns = akunRepository.findAll();
        assertThat(akuns).hasSize(databaseSizeBeforeUpdate);
        Akun testAkun = akuns.get(akuns.size() - 1);
        assertThat(testAkun.getKd_akun()).isEqualTo(UPDATED_KD_AKUN);
        assertThat(testAkun.getAkun()).isEqualTo(UPDATED_AKUN);
        assertThat(testAkun.getBiaya_akun()).isEqualTo(UPDATED_BIAYA_AKUN);
        assertThat(testAkun.getAkun_update()).isEqualTo(UPDATED_AKUN_UPDATE);
    }

    @Test
    @Transactional
    public void deleteAkun() throws Exception {
        // Initialize the database
        akunRepository.saveAndFlush(akun);
        int databaseSizeBeforeDelete = akunRepository.findAll().size();

        // Get the akun
        restAkunMockMvc.perform(delete("/api/akuns/{id}", akun.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Akun> akuns = akunRepository.findAll();
        assertThat(akuns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
