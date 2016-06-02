package com.ra_bbvet.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Komponen.
 */
@Entity
@Table(name = "komponen")
public class Komponen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kd_komponen")
    private String kd_komponen;

    @Column(name = "komponen")
    private String komponen;

    @Column(name = "biaya_komponen")
    private Long biaya_komponen;

    @Column(name = "komponen_update")
    private ZonedDateTime komponen_update;

    @ManyToOne
    private Output kdkomponenOutput;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKd_komponen() {
        return kd_komponen;
    }

    public void setKd_komponen(String kd_komponen) {
        this.kd_komponen = kd_komponen;
    }

    public String getKomponen() {
        return komponen;
    }

    public void setKomponen(String komponen) {
        this.komponen = komponen;
    }

    public Long getBiaya_komponen() {
        return biaya_komponen;
    }

    public void setBiaya_komponen(Long biaya_komponen) {
        this.biaya_komponen = biaya_komponen;
    }

    public ZonedDateTime getKomponen_update() {
        return komponen_update;
    }

    public void setKomponen_update(ZonedDateTime komponen_update) {
        this.komponen_update = komponen_update;
    }

    public Output getKdkomponenOutput() {
        return kdkomponenOutput;
    }

    public void setKdkomponenOutput(Output output) {
        this.kdkomponenOutput = output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Komponen komponen = (Komponen) o;
        if(komponen.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, komponen.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Komponen{" +
            "id=" + id +
            ", kd_komponen='" + kd_komponen + "'" +
            ", komponen='" + komponen + "'" +
            ", biaya_komponen='" + biaya_komponen + "'" +
            ", komponen_update='" + komponen_update + "'" +
            '}';
    }
}
