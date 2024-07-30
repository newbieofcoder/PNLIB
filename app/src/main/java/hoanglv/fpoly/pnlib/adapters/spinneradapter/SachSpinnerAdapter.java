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
import hoanglv.fpoly.pnlib.models.Sach;

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {
    private Context context;
    private ArrayList<Sach> list;
    TextView tvMaSach, tvTenSach;

    public SachSpinnerAdapter(@NonNull Context context, @NonNull ArrayList<Sach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.sach_item_spinner, null);
        }
        final Sach sach = list.get(position);
        if (sach != null) {
            tvMaSach = view.findViewById(R.id.tvMaSachSp);
            tvTenSach = view.findViewById(R.id.tvTenSachSp);
            tvMaSach.setText(sach.getMaSach() + ".");
            tvTenSach.setText(sach.getTenSach());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.sach_item_spinner, null);
        }
        final Sach sach = list.get(position);
        if (sach != null) {
            tvMaSach = view.findViewById(R.id.tvMaSachSp);
            tvTenSach = view.findViewById(R.id.tvTenSachSp);
            tvMaSach.setText(sach.getMaSach() + ".");
            tvTenSach.setText(sach.getTenSach());
        }
        return view;
    }
}
