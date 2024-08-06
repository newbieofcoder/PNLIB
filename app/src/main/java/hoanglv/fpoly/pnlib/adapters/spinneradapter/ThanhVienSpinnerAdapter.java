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
import hoanglv.fpoly.pnlib.models.ThanhVien;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    private ArrayList<ThanhVien> list;
    TextView tvMaTV, tvTenTV;

    public ThanhVienSpinnerAdapter(@NonNull Context context, ArrayList<ThanhVien> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.thanh_vien_item_spinner, null);
        }
        tvMaTV = view.findViewById(R.id.tvMaTVSp);
        tvTenTV = view.findViewById(R.id.tvTenTVSp);
        final ThanhVien thanhVien = list.get(position);
        if (thanhVien != null) {
            tvMaTV.setText(thanhVien.getMaTV() + ".");
            tvTenTV.setText(thanhVien.getHoTen());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.thanh_vien_item_spinner, null);
        }
        final ThanhVien thanhVien = list.get(position);
        if (thanhVien != null) {
            tvMaTV = view.findViewById(R.id.tvMaTVSp);
            tvTenTV = view.findViewById(R.id.tvTenTVSp);
            tvMaTV.setText(thanhVien.getMaTV() + ".");
            tvTenTV.setText(thanhVien.getHoTen());
        }
        return view;
    }
}
