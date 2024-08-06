package hoanglv.fpoly.pnlib.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.DAO.ThuThuDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.fragments.ThuThuFragment;
import hoanglv.fpoly.pnlib.models.ThuThu;

public class ThuThuAdapter extends ArrayAdapter<ThuThu> {
    private ThuThuFragment fragment;
    private ArrayList<ThuThu> list;
    private Context context;
    private ThuThuDAO thuThuDAO;

    public ThuThuAdapter(@NonNull Context context, ThuThuFragment fragment, @NonNull ArrayList<ThuThu> objects) {
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
            view = View.inflate(context, R.layout.thu_thu_item_listview, null);
        }
        TextView tvMaThuThu = view.findViewById(R.id.tvMaThuThu);
        TextView tvTenThuThu = view.findViewById(R.id.tvTenThuThu);
        CheckBox chkTrangThai = view.findViewById(R.id.chkTrangThai);

        final ThuThu thuThu = list.get(position);
        if (thuThu != null) {
            tvMaThuThu.setText("Mã thủ thư: " + thuThu.getMaTT());
            tvTenThuThu.setText("Tên thủ thư: " + thuThu.getTenTT());
            chkTrangThai.setChecked(thuThu.getTrangThai() == 1);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = View.inflate(context, R.layout.thu_thu_item_listview, null);
        }
        TextView tvMaThuThu = view.findViewById(R.id.tvMaThuThu);
        TextView tvTenThuThu = view.findViewById(R.id.tvTenThuThu);

        final ThuThu thuThu = list.get(position);
        if (thuThu != null) {
            tvMaThuThu.setText("Mã thủ thư: " + thuThu.getMaTT());
            tvTenThuThu.setText("Tên thủ thư: " + thuThu.getTenTT());
        }
        return view;
    }
}
