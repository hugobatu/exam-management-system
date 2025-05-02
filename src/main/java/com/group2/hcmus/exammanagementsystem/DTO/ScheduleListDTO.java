package com.group2.hcmus.exammanagementsystem.DTO;

import javafx.beans.property.*;
import java.sql.Timestamp;

public class ScheduleListDTO {
    private final IntegerProperty maLichThi;
    private final IntegerProperty maPhongThi;
    private final IntegerProperty maGiamThi;
    private final IntegerProperty maChungChi;
    private final ObjectProperty<Timestamp> ngayGio;
    private final StringProperty diaDiemThi;
    private final IntegerProperty slDangKy;
    // select box
    private final BooleanProperty selected = new SimpleBooleanProperty(false);

    public BooleanProperty selectedProperty() {
        return selected;
    }
    public boolean isSelected() {
        return selected.get();
    }
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public ScheduleListDTO() {
        this(0, 0, 0, 0, new Timestamp(System.currentTimeMillis()), "", 0);
    }

    public ScheduleListDTO(int maLichThi, int maPhongThi,
                           int maGiamThi, int maChungChi,
                           Timestamp ngayGio, String diaDiemThi,
                           int slDangKy) {
        this.maLichThi = new SimpleIntegerProperty(maLichThi);
        this.maPhongThi = new SimpleIntegerProperty(maPhongThi);
        this.maGiamThi = new SimpleIntegerProperty(maGiamThi);
        this.maChungChi = new SimpleIntegerProperty(maChungChi);
        this.ngayGio = new SimpleObjectProperty<>(ngayGio);
        this.diaDiemThi = new SimpleStringProperty(diaDiemThi);
        this.slDangKy = new SimpleIntegerProperty(slDangKy);
    }

    public IntegerProperty maLichThiProperty() { return maLichThi; }
    public IntegerProperty maPhongThiProperty() { return maPhongThi; }
    public IntegerProperty maGiamThiProperty() { return maGiamThi; }
    public IntegerProperty maChungChiProperty() { return maChungChi; }
    public ObjectProperty<Timestamp> ngayGioProperty() { return ngayGio; }
    public StringProperty diaDiemThiProperty() { return diaDiemThi; }
    public IntegerProperty slDangKyProperty() { return slDangKy; }

    public int getMaLichThi() { return maLichThi.get(); }
    public int getMaPhongThi() { return maPhongThi.get(); }
    public int getMaGiamThi() { return maGiamThi.get(); }
    public int getMaChungChi() { return maChungChi.get(); }
    public Timestamp getNgayGio() { return ngayGio.get(); }
    public String getDiaDiemThi() { return diaDiemThi.get(); }
    public int getSlDangKy() { return slDangKy.get(); }

    public void setMaLichThi(int value) { maLichThi.set(value); }
    public void setMaPhongThi(int value) { maPhongThi.set(value); }
    public void setMaGiamThi(int value) { maGiamThi.set(value); }
    public void setMaChungChi(int value) { maChungChi.set(value); }
    public void setNgayGio(Timestamp value) { ngayGio.set(value); }
    public void setDiaDiemThi(String value) { diaDiemThi.set(value); }
    public void setSlDangKy(int value) { slDangKy.set(value); }
}
