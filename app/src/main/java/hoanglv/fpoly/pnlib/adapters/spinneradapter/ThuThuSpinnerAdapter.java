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
import hoanglv.fpoly.pnlib.models.ThuThu;

public class ThuThuSpinnerAdapter extends ArrayAdapter<ThuThu> {
    private final Context context;
    private ArrayList<ThuThu> list;
    TextView tvTenTT;

    public ThuThuSpinnerAdapter(@NonNull Context context, @NonNull ArrayList<ThuThu> objects) {
        super(context, 0, objects);
        this.context = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.thu_thu_item_spinner, null);
        }
        tvTenTT = view.findViewById(R.id.tvTenTTSp);
        final ThuThu thuThu = list.get(position);
        if (thuThu != null) {
            tvTenTT.setText(thuThu.getTenTT());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.thu_thu_item_spinner, null);
        }
        tvTenTT = view.findViewById(R.id.tvTenTTSp);
        final ThuThu thuThu = list.get(position);
        if (thuThu != null) {
            tvTenTT.setText(thuThu.getTenTT());
        }
        return view;
    }
}
