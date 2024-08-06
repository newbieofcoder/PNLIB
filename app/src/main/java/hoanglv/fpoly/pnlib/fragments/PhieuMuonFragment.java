package hoanglv.fpoly.pnlib.fragments;

import static android.content.Intent.getIntent;
import static java.time.MonthDay.now;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import hoanglv.fpoly.pnlib.DAO.PhieuMuonDao;
import hoanglv.fpoly.pnlib.DAO.SachDao;
import hoanglv.fpoly.pnlib.DAO.ThanhVienDAO;
import hoanglv.fpoly.pnlib.DAO.ThuThuDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.adapters.PhieuMuonAdapter;
import hoanglv.fpoly.pnlib.adapters.spinneradapter.SachSpinnerAdapter;
import hoanglv.fpoly.pnlib.adapters.spinneradapter.ThanhVienSpinnerAdapter;
import hoanglv.fpoly.pnlib.adapters.spinneradapter.ThuThuSpinnerAdapter;
import hoanglv.fpoly.pnlib.models.PhieuMuon;
import hoanglv.fpoly.pnlib.models.Sach;
import hoanglv.fpoly.pnlib.models.ThanhVien;
import hoanglv.fpoly.pnlib.models.ThuThu;
import hoanglv.fpoly.pnlib.services.MyApplication;

public class PhieuMuonFragment extends Fragment {
    private ListView lvPhieuMuon;
    private PhieuMuonAdapter phieuMuonAdapter;
    private ArrayList<PhieuMuon> phieuMuonList;
    private ArrayList<ThanhVien> thanhVienList;
    private ArrayList<Sach> sachList;
    private ArrayList<ThuThu> thuThuList;
    private static PhieuMuonDao phieuMuonDAO;
    private static ThanhVienDAO thanhVienDAO;
    private static SachDao sachDao;
    private static ThuThuDAO thuThuDAO;
    FloatingActionButton fab;
    SachSpinnerAdapter sachSpinnerAdapter;
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    ThuThuSpinnerAdapter thuThuSpinnerAdapter;
    LinearLayout lnSavePhieuMuon, lnCancel;
    EditText edtMaPM, edtTienThue, edtNgay;
    Spinner spnTV, spnSach, spnTT;
    CheckBox chkTraSach;
    int maSach, positionTV, positionSach, maTV, trangThai, positionTT, position;
    PhieuMuon phieuMuon;
    Dialog dialog;
    String maThuThu;
    Boolean admin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        Intent intent = requireActivity().getIntent();
        Bundle bundle = intent.getExtras();
        trangThai = bundle.getInt("trangThai");
        position = bundle.getInt("position");
        admin = bundle.getBoolean("admin");
        lvPhieuMuon = view.findViewById(R.id.lvPhieuMuon);
        fab = view.findViewById(R.id.btn_Add);
        phieuMuonDAO = new PhieuMuonDao(getActivity());
        capNhatLV();
        fab.setOnClickListener(v -> {
            if (trangThai == 1) {
                openDialog(getActivity(), 0);
            } else {
                Toast.makeText(getActivity(), "Bạn không có quyền thêm", Toast.LENGTH_SHORT).show();
            }

        });
        lvPhieuMuon.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (trangThai == 1) {
                phieuMuon = phieuMuonList.get(position);
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
        View view1 = getLayoutInflater().inflate(R.layout.dialog_add_phieu_muon, null);
        dialog.setContentView(view1);

        spnTV = view1.findViewById(R.id.spTV);
        spnSach = view1.findViewById(R.id.spSach);
        spnTT = view1.findViewById(R.id.spTT);
        edtNgay = view1.findViewById(R.id.edtNgay);
        edtTienThue = view1.findViewById(R.id.edtTienThue);
        chkTraSach = view1.findViewById(R.id.chkDaTraSach);
        edtMaPM = view1.findViewById(R.id.edtPhieuMuon);
        lnSavePhieuMuon = view1.findViewById(R.id.lnSavePhieuMuon);
        lnCancel = view1.findViewById(R.id.lnCancel);

        thanhVienDAO = new ThanhVienDAO(context);
        thanhVienList = new ArrayList<>();
        thanhVienList = thanhVienDAO.getData("SELECT * FROM THANHVIEN");
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, thanhVienList);
        spnTV.setAdapter(thanhVienSpinnerAdapter);
        spnTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = thanhVienList.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sachDao = new SachDao(context);
        sachList = new ArrayList<>();
        sachList = sachDao.getData("SELECT * FROM SACH");
        sachSpinnerAdapter = new SachSpinnerAdapter(context, sachList);
        spnSach.setAdapter(sachSpinnerAdapter);
        spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = sachList.get(position).getMaSach();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        thuThuDAO = new ThuThuDAO(context);
        thuThuList = new ArrayList<>();
        thuThuList = thuThuDAO.getData();
        thuThuSpinnerAdapter = new ThuThuSpinnerAdapter(context, thuThuList);
        spnTT.setAdapter(thuThuSpinnerAdapter);
        spnTT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThuThu = thuThuList.get(position).getMaTT();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtMaPM.setEnabled(false);
        if (!admin) {
            spnTT.setSelection(position);
            spnTT.setEnabled(false);
        }
        if (type == 1) {
            edtMaPM.setText(String.valueOf(phieuMuon.getMaPM()));
            for (int i = 0; i < thanhVienList.size(); i++) {
                if (thanhVienList.get(i).getMaTV() == phieuMuon.getMaTV()) {
                    positionTV = i;
                }
            }
            spnTV.setSelection(positionTV);
            for (int i = 0; i < sachList.size(); i++) {
                if (sachList.get(i).getMaSach() == phieuMuon.getMaSach()) {
                    positionSach = i;
                }
            }
            spnSach.setSelection(positionSach);
            for (int i = 0; i < thuThuList.size(); i++) {
                if (thuThuList.get(i).getMaTT().equals(phieuMuon.getMaTT())) {
                    positionTT = i;
                }
            }
            spnTT.setSelection(positionTT);
            edtNgay.setText(phieuMuon.getNgay());
            edtTienThue.setText(String.valueOf(phieuMuon.getTienThue()));
            chkTraSach.setChecked(phieuMuon.getTraSach() == 1);
        }
        lnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        lnSavePhieuMuon.setOnClickListener(v -> {
            phieuMuon = new PhieuMuon();
            if (validate() > 0) {
                String ngay = edtNgay.getText().toString();
                String tienThue = edtTienThue.getText().toString();
                phieuMuon.setMaTV(maTV);
                phieuMuon.setMaSach(maSach);
                phieuMuon.setMaTT(maThuThu);
                phieuMuon.setTienThue(Integer.parseInt(tienThue));
                phieuMuon.setNgay(ngay);
                if (chkTraSach.isChecked()) {
                    phieuMuon.setTraSach(1);
                } else {
                    phieuMuon.setTraSach(0);
                }
                if (type == 0) {
                    if (phieuMuonDAO.insert(phieuMuon) > 0) {
                        sendNotification(context, "Đã thêm " + phieuMuon.getNgay());
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    phieuMuon.setMaPM(Integer.parseInt(edtMaPM.getText().toString()));
                    if (phieuMuonDAO.update(phieuMuon) > 0) {
                        sendNotification(context, "Đã sửa " + phieuMuon.getMaPM());
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLV();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void delete(String id) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
        builder1.setTitle("Xoá");
        builder1.setIcon(R.drawable.baseline_warning_24);
        builder1.setMessage("Bạn có muốn xoá?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Có", (dialog, which) -> {
            phieuMuonDAO.delete(id);
            capNhatLV();
            dialog.cancel();
            sendNotification(getActivity(), "Đã xoá " + id);
        });
        builder1.setNegativeButton("Không", (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
        capNhatLV();
    }

    private void capNhatLV() {
        phieuMuonList = new ArrayList<>();
        phieuMuonList = phieuMuonDAO.getData("SELECT * FROM PHIEUMUON");
        phieuMuonAdapter = new PhieuMuonAdapter(requireActivity(), this, phieuMuonList);
        lvPhieuMuon.setAdapter(phieuMuonAdapter);
    }

    public int validate() {
        int check = 1;
        if (edtNgay.getText().toString().isEmpty() || edtTienThue.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!isValidDate(edtNgay.getText().toString())) {
            Toast.makeText(getActivity(), "Định dạng ngày không đúng (dd/MM/yyyy)", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!isNumeric(edtTienThue.getText().toString()) || Integer.parseInt(edtTienThue.getText().toString()) < 0) {
            Toast.makeText(getActivity(), "Phải là số > 0", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDate(String inDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(inDate, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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