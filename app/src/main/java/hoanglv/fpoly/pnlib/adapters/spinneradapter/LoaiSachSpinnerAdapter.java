package hoanglv.fpoly.pnlib.adapters.spinneradapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.models.LoaiSach;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    private ArrayList<LoaiSach> list;
    TextView tvMaLoaiSach, tvTenLoaiSach;

    public LoaiSachSpinnerAdapter(@NonNull Context context, @NonNull ArrayList<LoaiSach> objects) {
        super(context, 0, objects);
        this.context = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.loai_sach_item_spinner, null);
        }
        final LoaiSach loaiSach = list.get(position);
        if (loaiSach != null) {
            tvMaLoaiSach = view.findViewById(R.id.tvMaLoaiSachSp);
            tvTenLoaiSach = view.findViewById(R.id.tvTenLoaiSachSp);
            tvMaLoaiSach.setText(loaiSach.getMaLoai() + ".");
            tvTenLoaiSach.setText(loaiSach.getTenLoai());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.loai_sach_item_spinner, null);
        }
        final LoaiSach loaiSach = list.get(position);
        if (loaiSach != null) {
            tvMaLoaiSach = view.findViewById(R.id.tvMaLoaiSachSp);
            tvTenLoaiSach = view.findViewById(R.id.tvTenLoaiSachSp);
            tvMaLoaiSach.setText(loaiSach.getMaLoai() + ".");
            tvTenLoaiSach.setText(loaiSach.getTenLoai());
        }
        return view;
    }
}
