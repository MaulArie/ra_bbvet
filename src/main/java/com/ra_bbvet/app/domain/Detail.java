package com.ra_bbvet.app.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Detail.
 */
@Entity
@Table(name = "detail")
public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "detail")
    private String detail;

    @Column(name = "volume_detail")
    private Integer volume_detail;

    @Column(name = "satuan_detail")
    private String satuan_detail;

    @Column(name = "biaya_detail")
    private Long biaya_detail;

    @ManyToOne
    private Program kddetailProgram;

    @ManyToOne
    private Kegiatan kddetailKegiatan;

    @ManyToOne
    private Output kddetailOutput;

    @ManyToOne
    private Suboutput kddetailSuboutput;

    @ManyToOne
    private Komponen kddetailKomponen;

    @ManyToOne
    private Subkomponen kddetailSubkomponen;

    @ManyToOne
    private Akun kddetailAkun;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getVolume_detail() {
        return volume_detail;
    }

    public void setVolume_detail(Integer volume_detail) {
        this.volume_detail = volume_detail;
    }

    public String getSatuan_detail() {
        return satuan_detail;
    }

    public void setSatuan_detail(String satuan_detail) {
        this.satuan_detail = satuan_detail;
    }

    public Long getBiaya_detail() {
        return biaya_detail;
    }

    public void setBiaya_detail(Long biaya_detail) {
        this.biaya_detail = biaya_detail;
    }

    public Program getKddetailProgram() {
        return kddetailProgram;
    }

    public void setKddetailProgram(Program program) {
        this.kddetailProgram = program;
    }

    public Kegiatan getKddetailKegiatan() {
        return kddetailKegiatan;
    }

    public void setKddetailKegiatan(Kegiatan kegiatan) {
        this.kddetailKegiatan = kegiatan;
    }

    public Output getKddetailOutput() {
        return kddetailOutput;
    }

    public void setKddetailOutput(Output output) {
        this.kddetailOutput = output;
    }

    public Suboutput getKddetailSuboutput() {
        return kddetailSuboutput;
    }

    public void setKddetailSuboutput(Suboutput suboutput) {
        this.kddetailSuboutput = suboutput;
    }

    public Komponen getKddetailKomponen() {
        return kddetailKomponen;
    }

    public void setKddetailKomponen(Komponen komponen) {
        this.kddetailKomponen = komponen;
    }

    public Subkomponen getKddetailSubkomponen() {
        return kddetailSubkomponen;
    }

    public void setKddetailSubkomponen(Subkomponen subkomponen) {
        this.kddetailSubkomponen = subkomponen;
    }

    public Akun getKddetailAkun() {
        return kddetailAkun;
    }

    public void setKddetailAkun(Akun akun) {
        this.kddetailAkun = akun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Detail detail = (Detail) o;
        if(detail.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, detail.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Detail{" +
            "id=" + id +
            ", detail='" + detail + "'" +
            ", volume_detail='" + volume_detail + "'" +
            ", satuan_detail='" + satuan_detail + "'" +
            ", biaya_detail='" + biaya_detail + "'" +
            '}';
    }
}
