package com.ra_bbvet.app.web.rest;

import com.ra_bbvet.app.RaBbvetApp;
import com.ra_bbvet.app.domain.Output;
import com.ra_bbvet.app.repository.OutputRepository;

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
 * Test class for the OutputResource REST controller.
 *
 * @see OutputResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RaBbvetApp.class)
@WebAppConfiguration
@IntegrationTest
public class OutputResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_KD_OUTPUT = "AAAAA";
    private static final String UPDATED_KD_OUTPUT = "BBBBB";
    private static final String DEFAULT_OUTPUT = "AAAAA";
    private static final String UPDATED_OUTPUT = "BBBBB";

    private static final Integer DEFAULT_VOLUME_OUTPUT = 1;
    private static final Integer UPDATED_VOLUME_OUTPUT = 2;
    private static final String DEFAULT_SATUAN_OUTPUT = "AAAAA";
    private static final String UPDATED_SATUAN_OUTPUT = "BBBBB";

    private static final Long DEFAULT_BIAYA_OUTPUT = 1L;
    private static final Long UPDATED_BIAYA_OUTPUT = 2L;

    private static final ZonedDateTime DEFAULT_OUTPUT_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_OUTPUT_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_OUTPUT_UPDATE_STR = dateTimeFormatter.format(DEFAULT_OUTPUT_UPDATE);

    @Inject
    private OutputRepository outputRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOutputMockMvc;

    private Output output;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OutputResource outputResource = new OutputResource();
        ReflectionTestUtils.setField(outputResource, "outputRepository", outputRepository);
        this.restOutputMockMvc = MockMvcBuilders.standaloneSetup(outputResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        output = new Output();
        output.setKd_output(DEFAULT_KD_OUTPUT);
        output.setOutput(DEFAULT_OUTPUT);
        output.setVolume_output(DEFAULT_VOLUME_OUTPUT);
        output.setSatuan_output(DEFAULT_SATUAN_OUTPUT);
        output.setBiaya_output(DEFAULT_BIAYA_OUTPUT);
        output.setOutput_update(DEFAULT_OUTPUT_UPDATE);
    }

    @Test
    @Transactional
    public void createOutput() throws Exception {
        int databaseSizeBeforeCreate = outputRepository.findAll().size();

        // Create the Output

        restOutputMockMvc.perform(post("/api/outputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(output)))
                .andExpect(status().isCreated());

        // Validate the Output in the database
        List<Output> outputs = outputRepository.findAll();
        assertThat(outputs).hasSize(databaseSizeBeforeCreate + 1);
        Output testOutput = outputs.get(outputs.size() - 1);
        assertThat(testOutput.getKd_output()).isEqualTo(DEFAULT_KD_OUTPUT);
        assertThat(testOutput.getOutput()).isEqualTo(DEFAULT_OUTPUT);
        assertThat(testOutput.getVolume_output()).isEqualTo(DEFAULT_VOLUME_OUTPUT);
        assertThat(testOutput.getSatuan_output()).isEqualTo(DEFAULT_SATUAN_OUTPUT);
        assertThat(testOutput.getBiaya_output()).isEqualTo(DEFAULT_BIAYA_OUTPUT);
        assertThat(testOutput.getOutput_update()).isEqualTo(DEFAULT_OUTPUT_UPDATE);
    }

    @Test
    @Transactional
    public void getAllOutputs() throws Exception {
        // Initialize the database
        outputRepository.saveAndFlush(output);

        // Get all the outputs
        restOutputMockMvc.perform(get("/api/outputs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(output.getId().intValue())))
                .andExpect(jsonPath("$.[*].kd_output").value(hasItem(DEFAULT_KD_OUTPUT.toString())))
                .andExpect(jsonPath("$.[*].output").value(hasItem(DEFAULT_OUTPUT.toString())))
                .andExpect(jsonPath("$.[*].volume_output").value(hasItem(DEFAULT_VOLUME_OUTPUT)))
                .andExpect(jsonPath("$.[*].satuan_output").value(hasItem(DEFAULT_SATUAN_OUTPUT.toString())))
                .andExpect(jsonPath("$.[*].biaya_output").value(hasItem(DEFAULT_BIAYA_OUTPUT.intValue())))
                .andExpect(jsonPath("$.[*].output_update").value(hasItem(DEFAULT_OUTPUT_UPDATE_STR)));
    }

    @Test
    @Transactional
    public void getOutput() throws Exception {
        // Initialize the database
        outputRepository.saveAndFlush(output);

        // Get the output
        restOutputMockMvc.perform(get("/api/outputs/{id}", output.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(output.getId().intValue()))
            .andExpect(jsonPath("$.kd_output").value(DEFAULT_KD_OUTPUT.toString()))
            .andExpect(jsonPath("$.output").value(DEFAULT_OUTPUT.toString()))
            .andExpect(jsonPath("$.volume_output").value(DEFAULT_VOLUME_OUTPUT))
            .andExpect(jsonPath("$.satuan_output").value(DEFAULT_SATUAN_OUTPUT.toString()))
            .andExpect(jsonPath("$.biaya_output").value(DEFAULT_BIAYA_OUTPUT.intValue()))
            .andExpect(jsonPath("$.output_update").value(DEFAULT_OUTPUT_UPDATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingOutput() throws Exception {
        // Get the output
        restOutputMockMvc.perform(get("/api/outputs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutput() throws Exception {
        // Initialize the database
        outputRepository.saveAndFlush(output);
        int databaseSizeBeforeUpdate = outputRepository.findAll().size();

        // Update the output
        Output updatedOutput = new Output();
        updatedOutput.setId(output.getId());
        updatedOutput.setKd_output(UPDATED_KD_OUTPUT);
        updatedOutput.setOutput(UPDATED_OUTPUT);
        updatedOutput.setVolume_output(UPDATED_VOLUME_OUTPUT);
        updatedOutput.setSatuan_output(UPDATED_SATUAN_OUTPUT);
        updatedOutput.setBiaya_output(UPDATED_BIAYA_OUTPUT);
        updatedOutput.setOutput_update(UPDATED_OUTPUT_UPDATE);

        restOutputMockMvc.perform(put("/api/outputs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedOutput)))
                .andExpect(status().isOk());

        // Validate the Output in the database
        List<Output> outputs = outputRepository.findAll();
        assertThat(outputs).hasSize(databaseSizeBeforeUpdate);
        Output testOutput = outputs.get(outputs.size() - 1);
        assertThat(testOutput.getKd_output()).isEqualTo(UPDATED_KD_OUTPUT);
        assertThat(testOutput.getOutput()).isEqualTo(UPDATED_OUTPUT);
        assertThat(testOutput.getVolume_output()).isEqualTo(UPDATED_VOLUME_OUTPUT);
        assertThat(testOutput.getSatuan_output()).isEqualTo(UPDATED_SATUAN_OUTPUT);
        assertThat(testOutput.getBiaya_output()).isEqualTo(UPDATED_BIAYA_OUTPUT);
        assertThat(testOutput.getOutput_update()).isEqualTo(UPDATED_OUTPUT_UPDATE);
    }

    @Test
    @Transactional
    public void deleteOutput() throws Exception {
        // Initialize the database
        outputRepository.saveAndFlush(output);
        int databaseSizeBeforeDelete = outputRepository.findAll().size();

        // Get the output
        restOutputMockMvc.perform(delete("/api/outputs/{id}", output.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Output> outputs = outputRepository.findAll();
        assertThat(outputs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
