package com.ra_bbvet.app.web.rest;

import com.ra_bbvet.app.RaBbvetApp;
import com.ra_bbvet.app.domain.Detail;
import com.ra_bbvet.app.repository.DetailRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DetailResource REST controller.
 *
 * @see DetailResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RaBbvetApp.class)
@WebAppConfiguration
@IntegrationTest
public class DetailResourceIntTest {

    private static final String DEFAULT_DETAIL = "AAAAA";
    private static final String UPDATED_DETAIL = "BBBBB";

    private static final Integer DEFAULT_VOLUME_DETAIL = 1;
    private static final Integer UPDATED_VOLUME_DETAIL = 2;
    private static final String DEFAULT_SATUAN_DETAIL = "AAAAA";
    private static final String UPDATED_SATUAN_DETAIL = "BBBBB";

    private static final Long DEFAULT_BIAYA_DETAIL = 1L;
    private static final Long UPDATED_BIAYA_DETAIL = 2L;

    @Inject
    private DetailRepository detailRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDetailMockMvc;

    private Detail detail;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DetailResource detailResource = new DetailResource();
        ReflectionTestUtils.setField(detailResource, "detailRepository", detailRepository);
        this.restDetailMockMvc = MockMvcBuilders.standaloneSetup(detailResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        detail = new Detail();
        detail.setDetail(DEFAULT_DETAIL);
        detail.setVolume_detail(DEFAULT_VOLUME_DETAIL);
        detail.setSatuan_detail(DEFAULT_SATUAN_DETAIL);
        detail.setBiaya_detail(DEFAULT_BIAYA_DETAIL);
    }

    @Test
    @Transactional
    public void createDetail() throws Exception {
        int databaseSizeBeforeCreate = detailRepository.findAll().size();

        // Create the Detail

        restDetailMockMvc.perform(post("/api/details")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(detail)))
                .andExpect(status().isCreated());

        // Validate the Detail in the database
        List<Detail> details = detailRepository.findAll();
        assertThat(details).hasSize(databaseSizeBeforeCreate + 1);
        Detail testDetail = details.get(details.size() - 1);
        assertThat(testDetail.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testDetail.getVolume_detail()).isEqualTo(DEFAULT_VOLUME_DETAIL);
        assertThat(testDetail.getSatuan_detail()).isEqualTo(DEFAULT_SATUAN_DETAIL);
        assertThat(testDetail.getBiaya_detail()).isEqualTo(DEFAULT_BIAYA_DETAIL);
    }

    @Test
    @Transactional
    public void getAllDetails() throws Exception {
        // Initialize the database
        detailRepository.saveAndFlush(detail);

        // Get all the details
        restDetailMockMvc.perform(get("/api/details?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(detail.getId().intValue())))
                .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].volume_detail").value(hasItem(DEFAULT_VOLUME_DETAIL)))
                .andExpect(jsonPath("$.[*].satuan_detail").value(hasItem(DEFAULT_SATUAN_DETAIL.toString())))
                .andExpect(jsonPath("$.[*].biaya_detail").value(hasItem(DEFAULT_BIAYA_DETAIL.intValue())));
    }

    @Test
    @Transactional
    public void getDetail() throws Exception {
        // Initialize the database
        detailRepository.saveAndFlush(detail);

        // Get the detail
        restDetailMockMvc.perform(get("/api/details/{id}", detail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(detail.getId().intValue()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.volume_detail").value(DEFAULT_VOLUME_DETAIL))
            .andExpect(jsonPath("$.satuan_detail").value(DEFAULT_SATUAN_DETAIL.toString()))
            .andExpect(jsonPath("$.biaya_detail").value(DEFAULT_BIAYA_DETAIL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetail() throws Exception {
        // Get the detail
        restDetailMockMvc.perform(get("/api/details/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetail() throws Exception {
        // Initialize the database
        detailRepository.saveAndFlush(detail);
        int databaseSizeBeforeUpdate = detailRepository.findAll().size();

        // Update the detail
        Detail updatedDetail = new Detail();
        updatedDetail.setId(detail.getId());
        updatedDetail.setDetail(UPDATED_DETAIL);
        updatedDetail.setVolume_detail(UPDATED_VOLUME_DETAIL);
        updatedDetail.setSatuan_detail(UPDATED_SATUAN_DETAIL);
        updatedDetail.setBiaya_detail(UPDATED_BIAYA_DETAIL);

        restDetailMockMvc.perform(put("/api/details")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDetail)))
                .andExpect(status().isOk());

        // Validate the Detail in the database
        List<Detail> details = detailRepository.findAll();
        assertThat(details).hasSize(databaseSizeBeforeUpdate);
        Detail testDetail = details.get(details.size() - 1);
        assertThat(testDetail.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testDetail.getVolume_detail()).isEqualTo(UPDATED_VOLUME_DETAIL);
        assertThat(testDetail.getSatuan_detail()).isEqualTo(UPDATED_SATUAN_DETAIL);
        assertThat(testDetail.getBiaya_detail()).isEqualTo(UPDATED_BIAYA_DETAIL);
    }

    @Test
    @Transactional
    public void deleteDetail() throws Exception {
        // Initialize the database
        detailRepository.saveAndFlush(detail);
        int databaseSizeBeforeDelete = detailRepository.findAll().size();

        // Get the detail
        restDetailMockMvc.perform(delete("/api/details/{id}", detail.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Detail> details = detailRepository.findAll();
        assertThat(details).hasSize(databaseSizeBeforeDelete - 1);
    }
}
