package com.ra_bbvet.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Output.
 */
@Entity
@Table(name = "output")
public class Output implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kd_output")
    private String kd_output;

    @Column(name = "output")
    private String output;

    @Column(name = "volume_output")
    private Integer volume_output;

    @Column(name = "satuan_output")
    private String satuan_output;

    @Column(name = "biaya_output")
    private Long biaya_output;

    @Column(name = "output_update")
    private ZonedDateTime output_update;

    @ManyToOne
    private Kegiatan kd_kegiatanOutput;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKd_output() {
        return kd_output;
    }

    public void setKd_output(String kd_output) {
        this.kd_output = kd_output;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Integer getVolume_output() {
        return volume_output;
    }

    public void setVolume_output(Integer volume_output) {
        this.volume_output = volume_output;
    }

    public String getSatuan_output() {
        return satuan_output;
    }

    public void setSatuan_output(String satuan_output) {
        this.satuan_output = satuan_output;
    }

    public Long getBiaya_output() {
        return biaya_output;
    }

    public void setBiaya_output(Long biaya_output) {
        this.biaya_output = biaya_output;
    }

    public ZonedDateTime getOutput_update() {
        return output_update;
    }

    public void setOutput_update(ZonedDateTime output_update) {
        this.output_update = output_update;
    }

    public Kegiatan getKd_kegiatanOutput() {
        return kd_kegiatanOutput;
    }

    public void setKd_kegiatanOutput(Kegiatan kegiatan) {
        this.kd_kegiatanOutput = kegiatan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Output output = (Output) o;
        if(output.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, output.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Output{" +
            "id=" + id +
            ", kd_output='" + kd_output + "'" +
            ", output='" + output + "'" +
            ", volume_output='" + volume_output + "'" +
            ", satuan_output='" + satuan_output + "'" +
            ", biaya_output='" + biaya_output + "'" +
            ", output_update='" + output_update + "'" +
            '}';
    }
}
