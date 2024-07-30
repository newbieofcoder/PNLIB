package hoanglv.fpoly.pnlib.models;

public class LoaiSach {
    private int maLoai; //auto increment
    private String tenLoai;

    public LoaiSach() {
    }

    public LoaiSach(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
