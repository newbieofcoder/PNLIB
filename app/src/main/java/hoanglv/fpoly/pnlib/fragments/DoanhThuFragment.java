package hoanglv.fpoly.pnlib.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import hoanglv.fpoly.pnlib.DAO.ThongKeDAO;
import hoanglv.fpoly.pnlib.R;

public class DoanhThuFragment extends Fragment {
    TextView tvDoanhThu, tvDoanhThuValue, tvFromDate, tvToDate;
    EditText edtFromDate, edtToDate;
    int mYear, mMonth, mDay;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatePickerDialog.OnDateSetListener mDateTuNgay = (view, year, month, dayOfMonth) -> {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar calendar = new GregorianCalendar(mYear, mMonth, mDay);
            edtFromDate.setText(simpleDateFormat.format(calendar.getTime()));
        };

        DatePickerDialog.OnDateSetListener mDateDenNgay = (view, year, month, dayOfMonth) -> {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar calendar = new GregorianCalendar(mYear, mMonth, mDay);
            edtToDate.setText(simpleDateFormat.format(calendar.getTime()));
        };
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        tvDoanhThuValue = view.findViewById(R.id.tvDoanhThuValue);
        tvFromDate = view.findViewById(R.id.tvFromDate);
        tvToDate = view.findViewById(R.id.tvToDate);
        edtFromDate = view.findViewById(R.id.edtFromDate);
        edtToDate = view.findViewById(R.id.edtToDate);

        tvFromDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DATE);
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(), mDateTuNgay, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        tvToDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DATE);
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(), mDateDenNgay, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        tvDoanhThu.setOnClickListener(v -> {
            String tuNgay = edtFromDate.getText().toString();
            String denNgay = edtToDate.getText().toString();
            ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
            tvDoanhThuValue.setText("Doanh thu: " + thongKeDAO.getDoanhThu(tuNgay, denNgay) + " VNĐ");
        });
        return view;
    }
}