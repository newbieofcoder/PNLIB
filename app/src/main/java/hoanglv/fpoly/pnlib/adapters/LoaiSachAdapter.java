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
import hoanglv.fpoly.pnlib.fragments.LoaiSachFragment;
import hoanglv.fpoly.pnlib.models.LoaiSach;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    private ArrayList<LoaiSach> list;
    private LoaiSachFragment fragment;

    public LoaiSachAdapter(@NonNull Context context, LoaiSachFragment fragment, @NonNull ArrayList<LoaiSach> objects) {
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
            view = View.inflate(context, R.layout.loai_sach_item_listview, null);
        }
        TextView tvMaLoaiSach = view.findViewById(R.id.tvMaLoaiSach);
        TextView tvTenLoaiSach = view.findViewById(R.id.tvTenLoaiSach);
        ImageView imgDeleteLoaiSach = view.findViewById(R.id.imgDeleteLoaiSach);

        final LoaiSach loaiSach = list.get(position);
        if (loaiSach != null) {
            tvMaLoaiSach.setText("Mã loại: " + loaiSach.getMaLoai());
            tvTenLoaiSach.setText("Tên loại: " + loaiSach.getTenLoai());
        }
        imgDeleteLoaiSach.setOnClickListener(v -> {
            fragment.delete(String.valueOf(loaiSach.getMaLoai()));
        });
        return view;
    }
}
