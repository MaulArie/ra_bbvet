package com.ra_bbvet.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Akun.
 */
@Entity
@Table(name = "akun")
public class Akun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "kd_akun")
    private String kd_akun;

    @Column(name = "akun")
    private String akun;

    @Column(name = "biaya_akun")
    private Long biaya_akun;

    @Column(name = "akun_update")
    private ZonedDateTime akun_update;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKd_akun() {
        return kd_akun;
    }

    public void setKd_akun(String kd_akun) {
        this.kd_akun = kd_akun;
    }

    public String getAkun() {
        return akun;
    }

    public void setAkun(String akun) {
        this.akun = akun;
    }

    public Long getBiaya_akun() {
        return biaya_akun;
    }

    public void setBiaya_akun(Long biaya_akun) {
        this.biaya_akun = biaya_akun;
    }

    public ZonedDateTime getAkun_update() {
        return akun_update;
    }

    public void setAkun_update(ZonedDateTime akun_update) {
        this.akun_update = akun_update;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Akun akun = (Akun) o;
        if(akun.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, akun.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Akun{" +
            "id=" + id +
            ", kd_akun='" + kd_akun + "'" +
            ", akun='" + akun + "'" +
            ", biaya_akun='" + biaya_akun + "'" +
            ", akun_update='" + akun_update + "'" +
            '}';
    }
}
