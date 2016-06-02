package com.ra_bbvet.app.web.rest;

import com.ra_bbvet.app.RaBbvetApp;
import com.ra_bbvet.app.domain.Kegiatan;
import com.ra_bbvet.app.repository.KegiatanRepository;

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
 * Test class for the KegiatanResource REST controller.
 *
 * @see KegiatanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RaBbvetApp.class)
@WebAppConfiguration
@IntegrationTest
public class KegiatanResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_KD_KEGIATAN = "AAAAA";
    private static final String UPDATED_KD_KEGIATAN = "BBBBB";
    private static final String DEFAULT_KEGIATAN = "AAAAA";
    private static final String UPDATED_KEGIATAN = "BBBBB";

    private static final Long DEFAULT_BIAYA_KEGIATAN = 1L;
    private static final Long UPDATED_BIAYA_KEGIATAN = 2L;

    private static final ZonedDateTime DEFAULT_KEGIATAN_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_KEGIATAN_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_KEGIATAN_UPDATE_STR = dateTimeFormatter.format(DEFAULT_KEGIATAN_UPDATE);

    @Inject
    private KegiatanRepository kegiatanRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKegiatanMockMvc;

    private Kegiatan kegiatan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KegiatanResource kegiatanResource = new KegiatanResource();
        ReflectionTestUtils.setField(kegiatanResource, "kegiatanRepository", kegiatanRepository);
        this.restKegiatanMockMvc = MockMvcBuilders.standaloneSetup(kegiatanResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kegiatan = new Kegiatan();
        kegiatan.setKd_kegiatan(DEFAULT_KD_KEGIATAN);
        kegiatan.setKegiatan(DEFAULT_KEGIATAN);
        kegiatan.setBiaya_kegiatan(DEFAULT_BIAYA_KEGIATAN);
        kegiatan.setKegiatan_update(DEFAULT_KEGIATAN_UPDATE);
    }

    @Test
    @Transactional
    public void createKegiatan() throws Exception {
        int databaseSizeBeforeCreate = kegiatanRepository.findAll().size();

        // Create the Kegiatan

        restKegiatanMockMvc.perform(post("/api/kegiatans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kegiatan)))
                .andExpect(status().isCreated());

        // Validate the Kegiatan in the database
        List<Kegiatan> kegiatans = kegiatanRepository.findAll();
        assertThat(kegiatans).hasSize(databaseSizeBeforeCreate + 1);
        Kegiatan testKegiatan = kegiatans.get(kegiatans.size() - 1);
        assertThat(testKegiatan.getKd_kegiatan()).isEqualTo(DEFAULT_KD_KEGIATAN);
        assertThat(testKegiatan.getKegiatan()).isEqualTo(DEFAULT_KEGIATAN);
        assertThat(testKegiatan.getBiaya_kegiatan()).isEqualTo(DEFAULT_BIAYA_KEGIATAN);
        assertThat(testKegiatan.getKegiatan_update()).isEqualTo(DEFAULT_KEGIATAN_UPDATE);
    }

    @Test
    @Transactional
    public void getAllKegiatans() throws Exception {
        // Initialize the database
        kegiatanRepository.saveAndFlush(kegiatan);

        // Get all the kegiatans
        restKegiatanMockMvc.perform(get("/api/kegiatans?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kegiatan.getId().intValue())))
                .andExpect(jsonPath("$.[*].kd_kegiatan").value(hasItem(DEFAULT_KD_KEGIATAN.toString())))
                .andExpect(jsonPath("$.[*].kegiatan").value(hasItem(DEFAULT_KEGIATAN.toString())))
                .andExpect(jsonPath("$.[*].biaya_kegiatan").value(hasItem(DEFAULT_BIAYA_KEGIATAN.intValue())))
                .andExpect(jsonPath("$.[*].kegiatan_update").value(hasItem(DEFAULT_KEGIATAN_UPDATE_STR)));
    }

    @Test
    @Transactional
    public void getKegiatan() throws Exception {
        // Initialize the database
        kegiatanRepository.saveAndFlush(kegiatan);

        // Get the kegiatan
        restKegiatanMockMvc.perform(get("/api/kegiatans/{id}", kegiatan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kegiatan.getId().intValue()))
            .andExpect(jsonPath("$.kd_kegiatan").value(DEFAULT_KD_KEGIATAN.toString()))
            .andExpect(jsonPath("$.kegiatan").value(DEFAULT_KEGIATAN.toString()))
            .andExpect(jsonPath("$.biaya_kegiatan").value(DEFAULT_BIAYA_KEGIATAN.intValue()))
            .andExpect(jsonPath("$.kegiatan_update").value(DEFAULT_KEGIATAN_UPDATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingKegiatan() throws Exception {
        // Get the kegiatan
        restKegiatanMockMvc.perform(get("/api/kegiatans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKegiatan() throws Exception {
        // Initialize the database
        kegiatanRepository.saveAndFlush(kegiatan);
        int databaseSizeBeforeUpdate = kegiatanRepository.findAll().size();

        // Update the kegiatan
        Kegiatan updatedKegiatan = new Kegiatan();
        updatedKegiatan.setId(kegiatan.getId());
        updatedKegiatan.setKd_kegiatan(UPDATED_KD_KEGIATAN);
        updatedKegiatan.setKegiatan(UPDATED_KEGIATAN);
        updatedKegiatan.setBiaya_kegiatan(UPDATED_BIAYA_KEGIATAN);
        updatedKegiatan.setKegiatan_update(UPDATED_KEGIATAN_UPDATE);

        restKegiatanMockMvc.perform(put("/api/kegiatans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKegiatan)))
                .andExpect(status().isOk());

        // Validate the Kegiatan in the database
        List<Kegiatan> kegiatans = kegiatanRepository.findAll();
        assertThat(kegiatans).hasSize(databaseSizeBeforeUpdate);
        Kegiatan testKegiatan = kegiatans.get(kegiatans.size() - 1);
        assertThat(testKegiatan.getKd_kegiatan()).isEqualTo(UPDATED_KD_KEGIATAN);
        assertThat(testKegiatan.getKegiatan()).isEqualTo(UPDATED_KEGIATAN);
        assertThat(testKegiatan.getBiaya_kegiatan()).isEqualTo(UPDATED_BIAYA_KEGIATAN);
        assertThat(testKegiatan.getKegiatan_update()).isEqualTo(UPDATED_KEGIATAN_UPDATE);
    }

    @Test
    @Transactional
    public void deleteKegiatan() throws Exception {
        // Initialize the database
        kegiatanRepository.saveAndFlush(kegiatan);
        int databaseSizeBeforeDelete = kegiatanRepository.findAll().size();

        // Get the kegiatan
        restKegiatanMockMvc.perform(delete("/api/kegiatans/{id}", kegiatan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Kegiatan> kegiatans = kegiatanRepository.findAll();
        assertThat(kegiatans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
