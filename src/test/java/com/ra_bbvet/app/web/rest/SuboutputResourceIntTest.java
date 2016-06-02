package com.ra_bbvet.app.web.rest;

import com.ra_bbvet.app.RaBbvetApp;
import com.ra_bbvet.app.domain.Suboutput;
import com.ra_bbvet.app.repository.SuboutputRepository;

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
 * Test class for the SuboutputResource REST controller.
 *
 * @see SuboutputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RaBbvetApp.class)
@WebAppConfiguration
@IntegrationTest
public class SuboutputResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_KD_SUBOUTPUT = "AAAAA";
    private static final String UPDATED_KD_SUBOUTPUT = "BBBBB";
    private static final String DEFAULT_SUBOUTPUT = "AAAAA";
    private static final String UPDATED_SUBOUTPUT = "BBBBB";

    private static final Long DEFAULT_BIAYA_SUBOUTPUT = 1L;
    private static final Long UPDATED_BIAYA_SUBOUTPUT = 2L;

    private static final ZonedDateTime DEFAULT_SUBOUTPUT_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SUBOUTPUT_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SUBOUTPUT_UPDATE_STR = dateTimeFormatter.format(DEFAULT_SUBOUTPUT_UPDATE);

    @Inject
    private SuboutputRepository suboutputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSuboutputMockMvc;

    private Suboutput suboutput;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SuboutputResource suboutputResource = new SuboutputResource();
        ReflectionTestUtils.setField(suboutputResource, "suboutputRepository", suboutputRepository);
        this.restSuboutputMockMvc = MockMvcBuilders.standaloneSetup(suboutputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        suboutput = new Suboutput();
        suboutput.setKd_suboutput(DEFAULT_KD_SUBOUTPUT);
        suboutput.setSuboutput(DEFAULT_SUBOUTPUT);
        suboutput.setBiaya_suboutput(DEFAULT_BIAYA_SUBOUTPUT);
        suboutput.setSuboutput_update(DEFAULT_SUBOUTPUT_UPDATE);
    }

    @Test
    @Transactional
    public void createSuboutput() throws Exception {
        int databaseSizeBeforeCreate = suboutputRepository.findAll().size();

        // Create the Suboutput

        restSuboutputMockMvc.perform(post("/api/suboutputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(suboutput)))
                .andExpect(status().isCreated());

        // Validate the Suboutput in the database
        List<Suboutput> suboutputs = suboutputRepository.findAll();
        assertThat(suboutputs).hasSize(databaseSizeBeforeCreate + 1);
        Suboutput testSuboutput = suboutputs.get(suboutputs.size() - 1);
        assertThat(testSuboutput.getKd_suboutput()).isEqualTo(DEFAULT_KD_SUBOUTPUT);
        assertThat(testSuboutput.getSuboutput()).isEqualTo(DEFAULT_SUBOUTPUT);
        assertThat(testSuboutput.getBiaya_suboutput()).isEqualTo(DEFAULT_BIAYA_SUBOUTPUT);
        assertThat(testSuboutput.getSuboutput_update()).isEqualTo(DEFAULT_SUBOUTPUT_UPDATE);
    }

    @Test
    @Transactional
    public void getAllSuboutputs() throws Exception {
        // Initialize the database
        suboutputRepository.saveAndFlush(suboutput);

        // Get all the suboutputs
        restSuboutputMockMvc.perform(get("/api/suboutputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(suboutput.getId().intValue())))
                .andExpect(jsonPath("$.[*].kd_suboutput").value(hasItem(DEFAULT_KD_SUBOUTPUT.toString())))
                .andExpect(jsonPath("$.[*].suboutput").value(hasItem(DEFAULT_SUBOUTPUT.toString())))
                .andExpect(jsonPath("$.[*].biaya_suboutput").value(hasItem(DEFAULT_BIAYA_SUBOUTPUT.intValue())))
                .andExpect(jsonPath("$.[*].suboutput_update").value(hasItem(DEFAULT_SUBOUTPUT_UPDATE_STR)));
    }

    @Test
    @Transactional
    public void getSuboutput() throws Exception {
        // Initialize the database
        suboutputRepository.saveAndFlush(suboutput);

        // Get the suboutput
        restSuboutputMockMvc.perform(get("/api/suboutputs/{id}", suboutput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(suboutput.getId().intValue()))
            .andExpect(jsonPath("$.kd_suboutput").value(DEFAULT_KD_SUBOUTPUT.toString()))
            .andExpect(jsonPath("$.suboutput").value(DEFAULT_SUBOUTPUT.toString()))
            .andExpect(jsonPath("$.biaya_suboutput").value(DEFAULT_BIAYA_SUBOUTPUT.intValue()))
            .andExpect(jsonPath("$.suboutput_update").value(DEFAULT_SUBOUTPUT_UPDATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSuboutput() throws Exception {
        // Get the suboutput
        restSuboutputMockMvc.perform(get("/api/suboutputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuboutput() throws Exception {
        // Initialize the database
        suboutputRepository.saveAndFlush(suboutput);
        int databaseSizeBeforeUpdate = suboutputRepository.findAll().size();

        // Update the suboutput
        Suboutput updatedSuboutput = new Suboutput();
        updatedSuboutput.setId(suboutput.getId());
        updatedSuboutput.setKd_suboutput(UPDATED_KD_SUBOUTPUT);
        updatedSuboutput.setSuboutput(UPDATED_SUBOUTPUT);
        updatedSuboutput.setBiaya_suboutput(UPDATED_BIAYA_SUBOUTPUT);
        updatedSuboutput.setSuboutput_update(UPDATED_SUBOUTPUT_UPDATE);

        restSuboutputMockMvc.perform(put("/api/suboutputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSuboutput)))
                .andExpect(status().isOk());

        // Validate the Suboutput in the database
        List<Suboutput> suboutputs = suboutputRepository.findAll();
        assertThat(suboutputs).hasSize(databaseSizeBeforeUpdate);
        Suboutput testSuboutput = suboutputs.get(suboutputs.size() - 1);
        assertThat(testSuboutput.getKd_suboutput()).isEqualTo(UPDATED_KD_SUBOUTPUT);
        assertThat(testSuboutput.getSuboutput()).isEqualTo(UPDATED_SUBOUTPUT);
        assertThat(testSuboutput.getBiaya_suboutput()).isEqualTo(UPDATED_BIAYA_SUBOUTPUT);
        assertThat(testSuboutput.getSuboutput_update()).isEqualTo(UPDATED_SUBOUTPUT_UPDATE);
    }

    @Test
    @Transactional
    public void deleteSuboutput() throws Exception {
        // Initialize the database
        suboutputRepository.saveAndFlush(suboutput);
        int databaseSizeBeforeDelete = suboutputRepository.findAll().size();

        // Get the suboutput
        restSuboutputMockMvc.perform(delete("/api/suboutputs/{id}", suboutput.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Suboutput> suboutputs = suboutputRepository.findAll();
        assertThat(suboutputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
