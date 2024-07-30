package hoanglv.fpoly.pnlib.models;

public class ThuThu {
    private String maTT;
    private String tenTT;
    private String matKhau;

    public ThuThu() {
    }

    public ThuThu(String maTT, String tenTT, String matKhau) {
        this.maTT = maTT;
        this.tenTT = tenTT;
        this.matKhau = matKhau;
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
