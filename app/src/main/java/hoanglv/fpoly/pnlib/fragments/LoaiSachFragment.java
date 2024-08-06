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
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

import hoanglv.fpoly.pnlib.DAO.LoaiSachDAO;
import hoanglv.fpoly.pnlib.DAO.PhieuMuonDao;
import hoanglv.fpoly.pnlib.DAO.SachDao;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.adapters.LoaiSachAdapter;
import hoanglv.fpoly.pnlib.models.LoaiSach;
import hoanglv.fpoly.pnlib.models.PhieuMuon;
import hoanglv.fpoly.pnlib.models.Sach;
import hoanglv.fpoly.pnlib.services.MyApplication;

public class LoaiSachFragment extends Fragment {
    ListView lvLoaiSach;
    LoaiSachAdapter loaiSachAdapter;
    ArrayList<LoaiSach> loaiSachList;
    ArrayList<Sach> sachList;
    ArrayList<PhieuMuon> phieuMuonList;
    static LoaiSachDAO loaiSachDAO;
    static SachDao sachDao;
    static PhieuMuonDao phieuMuonDao;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edtMaLoaiSach, edtTenLoaiSach;
    LinearLayout lnSaveLoaiSach, lnCacel;
    LoaiSach loaiSach;
    String maTT;
    int trangThai;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        lvLoaiSach = view.findViewById(R.id.lvLoaiSach);
        Intent intent = requireActivity().getIntent();
        trangThai = intent.getIntExtra("trangThai", 0);
        maTT = intent.getStringExtra("maTT");
        fab = view.findViewById(R.id.btn_Add_Loai_Sach);
        loaiSachDAO = new LoaiSachDAO(getActivity());
        capNhatLV();
        fab.setOnClickListener(v -> {
            if (trangThai == 1) {
                openDialog(getActivity(), 0);
            } else {
                Toast.makeText(getActivity(), "Bạn không có quyền thêm", Toast.LENGTH_SHORT).show();
            }
        });
        lvLoaiSach.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (trangThai == 1) {
                loaiSach = loaiSachList.get(position);
                openDialog(getActivity(), 1);
            } else {
                Toast.makeText(getActivity(), "Bạn không có quyền sửa", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
        return view;
    }

    private void capNhatLV() {
        loaiSachList = loaiSachDAO.getData("SELECT * FROM LOAISACH");
        loaiSachAdapter = new LoaiSachAdapter(requireActivity(), this, loaiSachList);
        lvLoaiSach.setAdapter(loaiSachAdapter);
    }

    private void openDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_loai_sach);
        edtMaLoaiSach = dialog.findViewById(R.id.edtNewMaLoaiSach);
        edtTenLoaiSach = dialog.findViewById(R.id.edtNewTenLoaiSach);
        lnSaveLoaiSach = dialog.findViewById(R.id.lnSaveLoaiSach);
        lnCacel = dialog.findViewById(R.id.lnCancel);

        edtMaLoaiSach.setEnabled(false);
        if (type == 1) {
            edtMaLoaiSach.setText(String.valueOf(loaiSach.getMaLoai()));
            edtTenLoaiSach.setText(loaiSach.getTenLoai());
        }

        lnCacel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        lnSaveLoaiSach.setOnClickListener(v -> {
            loaiSach = new LoaiSach();
            loaiSach.setTenLoai(edtTenLoaiSach.getText().toString());
            if (validate() > 0) {
                if (type == 0) {
                    if (loaiSachDAO.insert(loaiSach) > 0) {
                        sendNotification(context, "Đã thêm " + loaiSach.getTenLoai());
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loaiSach.setMaLoai(Integer.parseInt(edtMaLoaiSach.getText().toString()));
                    if (loaiSachDAO.update(loaiSach) > 0) {
                        sendNotification(context, "Đã sửa " + loaiSach.getMaLoai());
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

    private int validate() {
        int check = 1;
        if (edtTenLoaiSach.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public void delete(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xoá");
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setMessage("Bạn có muốn xoá? (Đồng thời xoá toàn bộ phiếu mượn và sách thuộc loại sách này)");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", (dialog, which) -> {
            sachDao = new SachDao(requireContext());
            sachList = sachDao.getAll();
            phieuMuonDao = new PhieuMuonDao(requireContext());
            phieuMuonList = phieuMuonDao.getData("SELECT * FROM PHIEUMUON");
            for (Sach sach : sachList) {
                if (sach.getMaLoai() == Integer.parseInt(id)) {
                    for (PhieuMuon phieuMuon : phieuMuonList) {
                        if (phieuMuon.getMaSach() == sach.getMaSach()) {
                            phieuMuonDao.delete(String.valueOf(phieuMuon.getMaPM()));
                        }
                    }
                    sachDao.deleteSach(String.valueOf(sach.getMaSach()));
                }
            }
            loaiSachDAO.delete(id);
            capNhatLV();
            dialog.cancel();
            sendNotification(getActivity(), "Đã xoá " + id);
        });
        builder.setNegativeButton("Không", (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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