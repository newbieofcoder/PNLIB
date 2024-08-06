package hoanglv.fpoly.pnlib.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import hoanglv.fpoly.pnlib.DAO.PhieuMuonDao;
import hoanglv.fpoly.pnlib.DAO.ThanhVienDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.adapters.ThanhVienAdapter;
import hoanglv.fpoly.pnlib.models.PhieuMuon;
import hoanglv.fpoly.pnlib.models.ThanhVien;
import hoanglv.fpoly.pnlib.services.MyApplication;

public class ThanhVienFragment extends Fragment {
    ListView lvThanhVien;
    ThanhVienAdapter thanhVienAdapter;
    ArrayList<ThanhVien> thanhVienList;
    ArrayList<PhieuMuon> phieuMuonList;
    FloatingActionButton fab;
    LinearLayout lnSaveThanhVien, lnCancel;
    Dialog dialog;
    ThanhVien thanhVien;
    static ThanhVienDAO thanhVienDAO;
    static PhieuMuonDao phieuMuonDao;
    EditText edtMaTV, edtTenTV, edtNamSinh;
    private static final String REGEX_NAME = "^[a-zA-Z\\s]+$";
    String maTT;
    int trangThai;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        lvThanhVien = view.findViewById(R.id.lvThanhVien);
        Intent intent = requireActivity().getIntent();
        trangThai = intent.getIntExtra("trangThai", 0);
        maTT = intent.getStringExtra("maTT");
        fab = view.findViewById(R.id.btn_Add_ThanhVien);
        thanhVienDAO = new ThanhVienDAO(getActivity());
        capNhatLV();
        fab.setOnClickListener(v -> {
            if (trangThai == 1) {
                openDialog(getActivity(), 0);
            } else {
                Toast.makeText(getActivity(), "Bạn không có quyền thêm", Toast.LENGTH_SHORT).show();
            }
        });
        lvThanhVien.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (trangThai == 1) {
                thanhVien = thanhVienList.get(position);
                openDialog(getActivity(), 1);
            } else {
                Toast.makeText(getActivity(), "Bạn không có quyền sửa", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
        return view;
    }

    private void openDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        View view1 = getLayoutInflater().inflate(R.layout.dialog_add_thanh_vien, null);
        dialog.setContentView(view1);
        edtMaTV = view1.findViewById(R.id.edtNewMaThanhVien);
        edtTenTV = view1.findViewById(R.id.edtNewTenThanhVien);
        edtNamSinh = view1.findViewById(R.id.edtNewNamSinh);
        lnSaveThanhVien = view1.findViewById(R.id.lnSaveThanhVien);
        lnCancel = view1.findViewById(R.id.lnCancel);
        edtMaTV.setEnabled(false);
        if (type == 1) {
            edtMaTV.setText(String.valueOf(thanhVien.getMaTV()));
            edtTenTV.setText(thanhVien.getHoTen());
            edtNamSinh.setText(thanhVien.getNamSinh());
        }
        lnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        lnSaveThanhVien.setOnClickListener(v -> {
            thanhVien = new ThanhVien();
            thanhVien.setHoTen(edtTenTV.getText().toString());
            thanhVien.setNamSinh(edtNamSinh.getText().toString());
            if (validate() > 0) {
                if (type == 0) {
                    if (thanhVienDAO.insert(thanhVien) > 0) {
                        sendNotification(context, "Đã thêm " + thanhVien.getHoTen());
                    } else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    thanhVien.setMaTV(Integer.parseInt(edtMaTV.getText().toString()));
                    if (thanhVienDAO.update(thanhVien) > 0) {
                        sendNotification(context, "Đã sửa " + thanhVien.getMaTV());
                    } else {
                        Toast.makeText(getActivity(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLV();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void capNhatLV() {
        thanhVienList = thanhVienDAO.getAll();
        thanhVienAdapter = new ThanhVienAdapter(requireActivity(), this, thanhVienList);
        lvThanhVien.setAdapter(thanhVienAdapter);
    }

    public void delete(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có muốn xóa thành viên này? (Đồng thời xoá toàn bộ phiếu mượn của thành viên này)");
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setCancelable(true);
        builder.setPositiveButton("Có", (dialog, which) -> {
            phieuMuonDao = new PhieuMuonDao(requireContext());
            phieuMuonList = phieuMuonDao.getData("SELECT * FROM PHIEUMUON");
            for (PhieuMuon phieuMuon : phieuMuonList) {
                if (phieuMuon.getMaTV() == Integer.parseInt(id)) {
                    phieuMuonDao.delete(String.valueOf(phieuMuon.getMaPM()));
                }
            }
            thanhVienDAO.delete(id);
            capNhatLV();
            dialog.dismiss();
            sendNotification(getActivity(), "Đã xoá " + id);
        });
        builder.setNegativeButton("Không", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public int validate() {
        int check = 1;
        if (edtTenTV.getText().toString().isEmpty() || edtNamSinh.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!edtTenTV.getText().toString().matches(REGEX_NAME)) {
            Toast.makeText(getActivity(), "Tên không hợp lệ", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!isValidYear(edtNamSinh.getText().toString())) {
            Toast.makeText(getActivity(), "Năm sinh không hợp lệ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public static boolean isValidYear(String yearStr) {
        String regex = "^\\d{4}$";
        if (!yearStr.matches(regex)) {
            return false;
        }
        int year = Integer.parseInt(yearStr);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return year >= 1900 && year <= currentYear;
    }

    private void sendNotification(Context context, String title) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MyApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.appicon)
                .setContentTitle("PN Library")
                .setContentText(title)
                .setColor(getResources().getColor(R.color.teal_200))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(getNotificationId(), builder.build());
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}