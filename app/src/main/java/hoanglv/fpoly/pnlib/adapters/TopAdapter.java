package hoanglv.fpoly.pnlib.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.fragments.Top10Fragment;
import hoanglv.fpoly.pnlib.models.Top;

public class TopAdapter extends ArrayAdapter<Top> {
    private Context context;
    Top10Fragment fragment;
    private ArrayList<Top> list;
    TextView tvSach, tvSoLuong;

    public TopAdapter(@NonNull Context context, Top10Fragment fragment, @NonNull ArrayList<Top> objects) {
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
            view = View.inflate(context, R.layout.top10_item_listview, null);
        }
        tvSach = view.findViewById(R.id.tvSach);
        tvSoLuong = view.findViewById(R.id.tvSoLuong);
        final Top top = list.get(position);
        if (top != null) {
            tvSach.setText("Sách: " + top.getTenSach());
            tvSoLuong.setText("Số lượng: " + top.getSoLuong());
        }
        return view;
    }
}
