package com.ra_bbvet.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Kegiatan.
 */
@Entity
@Table(name = "kegiatan")
public class Kegiatan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kd_kegiatan")
    private String kd_kegiatan;

    @Column(name = "kegiatan")
    private String kegiatan;

    @Column(name = "biaya_kegiatan")
    private Long biaya_kegiatan;

    @Column(name = "kegiatan_update")
    private ZonedDateTime kegiatan_update;

    @ManyToOne
    private Program kd_programKegiatan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKd_kegiatan() {
        return kd_kegiatan;
    }

    public void setKd_kegiatan(String kd_kegiatan) {
        this.kd_kegiatan = kd_kegiatan;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public Long getBiaya_kegiatan() {
        return biaya_kegiatan;
    }

    public void setBiaya_kegiatan(Long biaya_kegiatan) {
        this.biaya_kegiatan = biaya_kegiatan;
    }

    public ZonedDateTime getKegiatan_update() {
        return kegiatan_update;
    }

    public void setKegiatan_update(ZonedDateTime kegiatan_update) {
        this.kegiatan_update = kegiatan_update;
    }

    public Program getKd_programKegiatan() {
        return kd_programKegiatan;
    }

    public void setKd_programKegiatan(Program program) {
        this.kd_programKegiatan = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kegiatan kegiatan = (Kegiatan) o;
        if(kegiatan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, kegiatan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Kegiatan{" +
            "id=" + id +
            ", kd_kegiatan='" + kd_kegiatan + "'" +
            ", kegiatan='" + kegiatan + "'" +
            ", biaya_kegiatan='" + biaya_kegiatan + "'" +
            ", kegiatan_update='" + kegiatan_update + "'" +
            '}';
    }
}
