package hoanglv.fpoly.pnlib.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.DAO.LoaiSachDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.fragments.SachFragment;
import hoanglv.fpoly.pnlib.models.LoaiSach;
import hoanglv.fpoly.pnlib.models.Sach;

public class SachAdapter extends ArrayAdapter<Sach> {

    private Context context;
    private SachFragment fragment;
    private ArrayList<Sach> list;

    public SachAdapter(@NonNull Context context, SachFragment fragment, @NonNull ArrayList<Sach> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.sach_item_listview, null);
        }
        TextView tvMaSach = view.findViewById(R.id.tvMaSach);
        TextView tvTenSach = view.findViewById(R.id.tvSach);
        TextView tvGiaThue = view.findViewById(R.id.tvGiaThue);
        TextView tvLoaiSach = view.findViewById(R.id.tvLoaiSach);
        ImageView imgDeleteSach = view.findViewById(R.id.imgDeleteSach);

        final Sach sach = list.get(position);
        if (sach != null) {
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
            LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(sach.getMaLoai()));

            Log.d("sach", "getView: " + sach.getMaLoai());
            tvMaSach.setText("Mã sách: " + sach.getMaSach());
            tvTenSach.setText("Tên sách: " + sach.getTenSach());
            tvGiaThue.setText("Giá thuê: " + sach.getGiaThue());
            tvLoaiSach.setText("Loại sách: " + loaiSach.getTenLoai());
        }

        imgDeleteSach.setOnClickListener(v -> {
            fragment.delete(String.valueOf(sach.getMaSach()));
        });
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.sach_item_listview, null);
        }
        TextView tvMaSach = view.findViewById(R.id.tvMaSach);
        TextView tvTenSach = view.findViewById(R.id.tvSach);
        TextView tvGiaThue = view.findViewById(R.id.tvGiaThue);
        TextView tvLoaiSach = view.findViewById(R.id.tvLoaiSach);
        ImageView imgDeleteSach = view.findViewById(R.id.imgDeleteSach);

        final Sach sach = list.get(position);
        if (sach != null) {
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
            LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(sach.getMaLoai()));

            tvMaSach.setText("Mã sách: " + sach.getMaSach());
            tvTenSach.setText("Tên sách: " + sach.getTenSach());
            tvGiaThue.setText("Giá thuê: " + sach.getGiaThue());
            tvLoaiSach.setText("Loại sách: " + loaiSach.getTenLoai());
        }

        imgDeleteSach.setOnClickListener(v -> {
            fragment.delete(String.valueOf(sach.getMaSach()));
        });
        return view;
    }
}
