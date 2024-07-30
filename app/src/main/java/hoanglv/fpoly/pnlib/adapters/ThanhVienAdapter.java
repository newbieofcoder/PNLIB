package hoanglv.fpoly.pnlib.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.fragments.ThanhVienFragment;
import hoanglv.fpoly.pnlib.models.ThanhVien;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    private ThanhVienFragment fragment;
    private ArrayList<ThanhVien> list;

    public ThanhVienAdapter(@NonNull Context context, ThanhVienFragment fragment, @NonNull ArrayList<ThanhVien> objects) {
        super(context, 0, objects);
        this.context = context;
        this.list = objects;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.thanh_vien_item_listview, null);
        }
        TextView tvMaTV = view.findViewById(R.id.tvMaThanhVien);
        TextView tvTenTV = view.findViewById(R.id.tvTenThanhVien);
        TextView tvNamSinh = view.findViewById(R.id.tvNamSinh);
        ImageView imgDeleteTV = view.findViewById(R.id.imgDeleteThanhVien);

        final ThanhVien thanhVien = list.get(position);
        if (thanhVien != null) {
            tvMaTV.setText("Mã thành viên: " + thanhVien.getMaTV());
            tvTenTV.setText("Tên thành viên: " + thanhVien.getHoTen());
            tvNamSinh.setText("Năm sinh: " + thanhVien.getNamSinh());
        }
        imgDeleteTV.setOnClickListener(v -> {
            fragment.delete(String.valueOf(thanhVien.getMaTV()));
        });
        return view;
    }
}
