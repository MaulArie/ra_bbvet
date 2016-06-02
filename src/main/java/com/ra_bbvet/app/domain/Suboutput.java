package com.ra_bbvet.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Suboutput.
 */
@Entity
@Table(name = "suboutput")
public class Suboutput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kd_suboutput")
    private String kd_suboutput;

    @Column(name = "suboutput")
    private String suboutput;

    @Column(name = "biaya_suboutput")
    private Long biaya_suboutput;

    @Column(name = "suboutput_update")
    private ZonedDateTime suboutput_update;

    @ManyToOne
    private Output kdsubOutput;

    @ManyToOne
    private Kegiatan kdsubOuputKegiatan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKd_suboutput() {
        return kd_suboutput;
    }

    public void setKd_suboutput(String kd_suboutput) {
        this.kd_suboutput = kd_suboutput;
    }

    public String getSuboutput() {
        return suboutput;
    }

    public void setSuboutput(String suboutput) {
        this.suboutput = suboutput;
    }

    public Long getBiaya_suboutput() {
        return biaya_suboutput;
    }

    public void setBiaya_suboutput(Long biaya_suboutput) {
        this.biaya_suboutput = biaya_suboutput;
    }

    public ZonedDateTime getSuboutput_update() {
        return suboutput_update;
    }

    public void setSuboutput_update(ZonedDateTime suboutput_update) {
        this.suboutput_update = suboutput_update;
    }

    public Output getKdsubOutput() {
        return kdsubOutput;
    }

    public void setKdsubOutput(Output output) {
        this.kdsubOutput = output;
    }

    public Kegiatan getKdsubOuputKegiatan() {
        return kdsubOuputKegiatan;
    }

    public void setKdsubOuputKegiatan(Kegiatan kegiatan) {
        this.kdsubOuputKegiatan = kegiatan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Suboutput suboutput = (Suboutput) o;
        if(suboutput.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, suboutput.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Suboutput{" +
            "id=" + id +
            ", kd_suboutput='" + kd_suboutput + "'" +
            ", suboutput='" + suboutput + "'" +
            ", biaya_suboutput='" + biaya_suboutput + "'" +
            ", suboutput_update='" + suboutput_update + "'" +
            '}';
    }
}
