package hoanglv.fpoly.pnlib.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import hoanglv.fpoly.pnlib.DAO.SachDao;
import hoanglv.fpoly.pnlib.DAO.ThanhVienDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.fragments.PhieuMuonFragment;
import hoanglv.fpoly.pnlib.models.PhieuMuon;
import hoanglv.fpoly.pnlib.models.Sach;
import hoanglv.fpoly.pnlib.models.ThanhVien;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {
    private Context context;
    PhieuMuonFragment phieuMuonFragment;
    private ArrayList<PhieuMuon> list;
    TextView tvMaPhieu, tvThanhVien, tvSach, tvTienThue, tvNgayMuon, tvTrangThai;
    ImageView imgDelete;
    SachDao sachDao;
    ThanhVienDAO thanhVienDAO;

    public PhieuMuonAdapter(@NonNull Context context, PhieuMuonFragment fragment, @NonNull ArrayList<PhieuMuon> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.phieuMuonFragment = fragment;
        sachDao = new SachDao(context);
        thanhVienDAO = new ThanhVienDAO(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.phieu_muon_item_listview, null);
        }
        final PhieuMuon phieuMuon = list.get(position);
        if (phieuMuon != null){
            sachDao = new SachDao(context);
            thanhVienDAO = new ThanhVienDAO(context);

            tvMaPhieu = view.findViewById(R.id.tvMaPhieu);
            tvThanhVien = view.findViewById(R.id.tvThanhVien);
            tvSach = view.findViewById(R.id.tvSach);
            tvTienThue = view.findViewById(R.id.tvTienThue);
            tvNgayMuon = view.findViewById(R.id.tvNgayMuon);
            tvTrangThai = view.findViewById(R.id.tvTrangThai);
            imgDelete = view.findViewById(R.id.imgDeletePhieuMuon);

            Sach sach = sachDao.getID(String.valueOf(phieuMuon.getMaSach()));
            ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(phieuMuon.getMaTV()));

            tvMaPhieu.setText("Mã phiếu: " + phieuMuon.getMaPM());
            tvThanhVien.setText("Thành viên: " + thanhVien.getHoTen());
            tvSach.setText("Tên sách: " + sach.getTenSach());
            tvTienThue.setText("Tiền thuê: " + phieuMuon.getTienThue());
            tvNgayMuon.setText("Ngày mượn: " + phieuMuon.getNgay());

            if (phieuMuon.getTraSach() == 1) {
                tvTrangThai.setText("Đã trả sách");
                tvTrangThai.setTextColor(Color.BLUE);
            } else {
                tvTrangThai.setText("Chưa trả sách");
                tvTrangThai.setTextColor(Color.RED);
            }
        }
        imgDelete.setOnClickListener(v -> {
            phieuMuonFragment.delete(String.valueOf(phieuMuon.getMaPM()));
        });
        return view;
    }
}
