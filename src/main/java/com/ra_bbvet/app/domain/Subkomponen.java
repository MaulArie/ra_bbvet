package com.ra_bbvet.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Subkomponen.
 */
@Entity
@Table(name = "subkomponen")
public class Subkomponen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kd_subkomponen")
    private String kd_subkomponen;

    @Column(name = "subkomponen")
    private String subkomponen;

    @Column(name = "biaya_subkomponen")
    private Long biaya_subkomponen;

    @Column(name = "subkomponen_update")
    private ZonedDateTime subkomponen_update;

    @ManyToOne
    private Komponen kdsubKomponen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKd_subkomponen() {
        return kd_subkomponen;
    }

    public void setKd_subkomponen(String kd_subkomponen) {
        this.kd_subkomponen = kd_subkomponen;
    }

    public String getSubkomponen() {
        return subkomponen;
    }

    public void setSubkomponen(String subkomponen) {
        this.subkomponen = subkomponen;
    }

    public Long getBiaya_subkomponen() {
        return biaya_subkomponen;
    }

    public void setBiaya_subkomponen(Long biaya_subkomponen) {
        this.biaya_subkomponen = biaya_subkomponen;
    }

    public ZonedDateTime getSubkomponen_update() {
        return subkomponen_update;
    }

    public void setSubkomponen_update(ZonedDateTime subkomponen_update) {
        this.subkomponen_update = subkomponen_update;
    }

    public Komponen getKdsubKomponen() {
        return kdsubKomponen;
    }

    public void setKdsubKomponen(Komponen komponen) {
        this.kdsubKomponen = komponen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subkomponen subkomponen = (Subkomponen) o;
        if(subkomponen.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subkomponen.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subkomponen{" +
            "id=" + id +
            ", kd_subkomponen='" + kd_subkomponen + "'" +
            ", subkomponen='" + subkomponen + "'" +
            ", biaya_subkomponen='" + biaya_subkomponen + "'" +
            ", subkomponen_update='" + subkomponen_update + "'" +
            '}';
    }
}
