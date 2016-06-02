package com.ra_bbvet.app.web.rest;

import com.ra_bbvet.app.RaBbvetApp;
import com.ra_bbvet.app.domain.Program;
import com.ra_bbvet.app.repository.ProgramRepository;

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
 * Test class for the ProgramResource REST controller.
 *
 * @see ProgramResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RaBbvetApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProgramResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_KD_PROGRAM = "AAAAA";
    private static final String UPDATED_KD_PROGRAM = "BBBBB";
    private static final String DEFAULT_PROGRAM = "AAAAA";
    private static final String UPDATED_PROGRAM = "BBBBB";

    private static final Long DEFAULT_BIAYA_PROGRAM = 1L;
    private static final Long UPDATED_BIAYA_PROGRAM = 2L;

    private static final ZonedDateTime DEFAULT_PROGRAM_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_PROGRAM_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_PROGRAM_UPDATE_STR = dateTimeFormatter.format(DEFAULT_PROGRAM_UPDATE);

    @Inject
    private ProgramRepository programRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProgramMockMvc;

    private Program program;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProgramResource programResource = new ProgramResource();
        ReflectionTestUtils.setField(programResource, "programRepository", programRepository);
        this.restProgramMockMvc = MockMvcBuilders.standaloneSetup(programResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        program = new Program();
        program.setKd_program(DEFAULT_KD_PROGRAM);
        program.setProgram(DEFAULT_PROGRAM);
        program.setBiaya_program(DEFAULT_BIAYA_PROGRAM);
        program.setProgram_update(DEFAULT_PROGRAM_UPDATE);
    }

    @Test
    @Transactional
    public void createProgram() throws Exception {
        int databaseSizeBeforeCreate = programRepository.findAll().size();

        // Create the Program

        restProgramMockMvc.perform(post("/api/programs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(program)))
                .andExpect(status().isCreated());

        // Validate the Program in the database
        List<Program> programs = programRepository.findAll();
        assertThat(programs).hasSize(databaseSizeBeforeCreate + 1);
        Program testProgram = programs.get(programs.size() - 1);
        assertThat(testProgram.getKd_program()).isEqualTo(DEFAULT_KD_PROGRAM);
        assertThat(testProgram.getProgram()).isEqualTo(DEFAULT_PROGRAM);
        assertThat(testProgram.getBiaya_program()).isEqualTo(DEFAULT_BIAYA_PROGRAM);
        assertThat(testProgram.getProgram_update()).isEqualTo(DEFAULT_PROGRAM_UPDATE);
    }

    @Test
    @Transactional
    public void getAllPrograms() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get all the programs
        restProgramMockMvc.perform(get("/api/programs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(program.getId().intValue())))
                .andExpect(jsonPath("$.[*].kd_program").value(hasItem(DEFAULT_KD_PROGRAM.toString())))
                .andExpect(jsonPath("$.[*].program").value(hasItem(DEFAULT_PROGRAM.toString())))
                .andExpect(jsonPath("$.[*].biaya_program").value(hasItem(DEFAULT_BIAYA_PROGRAM.intValue())))
                .andExpect(jsonPath("$.[*].program_update").value(hasItem(DEFAULT_PROGRAM_UPDATE_STR)));
    }

    @Test
    @Transactional
    public void getProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);

        // Get the program
        restProgramMockMvc.perform(get("/api/programs/{id}", program.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(program.getId().intValue()))
            .andExpect(jsonPath("$.kd_program").value(DEFAULT_KD_PROGRAM.toString()))
            .andExpect(jsonPath("$.program").value(DEFAULT_PROGRAM.toString()))
            .andExpect(jsonPath("$.biaya_program").value(DEFAULT_BIAYA_PROGRAM.intValue()))
            .andExpect(jsonPath("$.program_update").value(DEFAULT_PROGRAM_UPDATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingProgram() throws Exception {
        // Get the program
        restProgramMockMvc.perform(get("/api/programs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);
        int databaseSizeBeforeUpdate = programRepository.findAll().size();

        // Update the program
        Program updatedProgram = new Program();
        updatedProgram.setId(program.getId());
        updatedProgram.setKd_program(UPDATED_KD_PROGRAM);
        updatedProgram.setProgram(UPDATED_PROGRAM);
        updatedProgram.setBiaya_program(UPDATED_BIAYA_PROGRAM);
        updatedProgram.setProgram_update(UPDATED_PROGRAM_UPDATE);

        restProgramMockMvc.perform(put("/api/programs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProgram)))
                .andExpect(status().isOk());

        // Validate the Program in the database
        List<Program> programs = programRepository.findAll();
        assertThat(programs).hasSize(databaseSizeBeforeUpdate);
        Program testProgram = programs.get(programs.size() - 1);
        assertThat(testProgram.getKd_program()).isEqualTo(UPDATED_KD_PROGRAM);
        assertThat(testProgram.getProgram()).isEqualTo(UPDATED_PROGRAM);
        assertThat(testProgram.getBiaya_program()).isEqualTo(UPDATED_BIAYA_PROGRAM);
        assertThat(testProgram.getProgram_update()).isEqualTo(UPDATED_PROGRAM_UPDATE);
    }

    @Test
    @Transactional
    public void deleteProgram() throws Exception {
        // Initialize the database
        programRepository.saveAndFlush(program);
        int databaseSizeBeforeDelete = programRepository.findAll().size();

        // Get the program
        restProgramMockMvc.perform(delete("/api/programs/{id}", program.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Program> programs = programRepository.findAll();
        assertThat(programs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
