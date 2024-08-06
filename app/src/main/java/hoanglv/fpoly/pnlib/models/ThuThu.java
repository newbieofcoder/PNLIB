package hoanglv.fpoly.pnlib.models;

public class ThuThu {
    private String maTT;
    private String tenTT;
    private int trangThai;
    private String matKhau;

    public ThuThu() {
    }

    public ThuThu(String maTT, String tenTT, int trangThai, String matKhau) {
        this.maTT = maTT;
        this.tenTT = tenTT;
        this.trangThai = trangThai;
        this.matKhau = matKhau;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
