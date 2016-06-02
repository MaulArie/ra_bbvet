package com.ra_bbvet.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Program.
 */
@Entity
@Table(name = "program")
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kd_program")
    private String kd_program;

    @Column(name = "program")
    private String program;

    @Column(name = "biaya_program")
    private Long biaya_program;

    @Column(name = "program_update")
    private ZonedDateTime program_update;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKd_program() {
        return kd_program;
    }

    public void setKd_program(String kd_program) {
        this.kd_program = kd_program;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Long getBiaya_program() {
        return biaya_program;
    }

    public void setBiaya_program(Long biaya_program) {
        this.biaya_program = biaya_program;
    }

    public ZonedDateTime getProgram_update() {
        return program_update;
    }

    public void setProgram_update(ZonedDateTime program_update) {
        this.program_update = program_update;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Program program = (Program) o;
        if(program.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, program.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Program{" +
            "id=" + id +
            ", kd_program='" + kd_program + "'" +
            ", program='" + program + "'" +
            ", biaya_program='" + biaya_program + "'" +
            ", program_update='" + program_update + "'" +
            '}';
    }
}
