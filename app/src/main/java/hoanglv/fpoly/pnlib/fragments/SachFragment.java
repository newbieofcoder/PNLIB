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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

import hoanglv.fpoly.pnlib.DAO.LoaiSachDAO;
import hoanglv.fpoly.pnlib.DAO.PhieuMuonDao;
import hoanglv.fpoly.pnlib.DAO.SachDao;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.adapters.spinneradapter.LoaiSachSpinnerAdapter;
import hoanglv.fpoly.pnlib.adapters.SachAdapter;
import hoanglv.fpoly.pnlib.models.LoaiSach;
import hoanglv.fpoly.pnlib.models.PhieuMuon;
import hoanglv.fpoly.pnlib.models.Sach;
import hoanglv.fpoly.pnlib.services.MyApplication;

public class SachFragment extends Fragment {
    ListView lvSach;
    ArrayList<Sach> list;
    ArrayList<PhieuMuon> phieuMuonList;
    ArrayList<LoaiSach> listLoaiSach;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edtNewMaSach, edtNewTenSach, edtNewGiaThue;
    Sach sach;
    Spinner spinner;
    LinearLayout lnSaveSach, lnCancel;
    static SachDao sachDao;
    static PhieuMuonDao phieuMuonDao;
    static LoaiSachDAO loaiSachDAO;
    SachAdapter sachAdapter;
    LoaiSachSpinnerAdapter loaiSachSpinnerAdapter;
    int maLoai, position, trangThai;
    String maTT;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        lvSach = view.findViewById(R.id.lvSach);
        fab = view.findViewById(R.id.btn_Add_sach);
        Intent intent = requireActivity().getIntent();
        trangThai = intent.getIntExtra("trangThai", 0);
        maTT = intent.getStringExtra("maTT");
        sachDao = new SachDao(getActivity());
        capNhatLv();
        fab.setOnClickListener(v -> {
            if (trangThai == 1) {
                openDialog(getActivity(), 0);
            } else {
                Toast.makeText(getActivity(), "Bạn không có quyền thêm", Toast.LENGTH_SHORT).show();
            }
        });
        lvSach.setOnItemLongClickListener((parent, view1, position, id) -> {
            if (trangThai == 1) {
                sach = list.get(position);
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
        dialog.setContentView(R.layout.dialog_add_sach);

        edtNewMaSach = dialog.findViewById(R.id.edtNewMaSach);
        edtNewTenSach = dialog.findViewById(R.id.edtNewTenSach);
        edtNewGiaThue = dialog.findViewById(R.id.edtNewGiaThue);
        spinner = dialog.findViewById(R.id.spnLoaiSach);
        lnSaveSach = dialog.findViewById(R.id.lnSaveSach);
        lnCancel = dialog.findViewById(R.id.lnCancel);

        listLoaiSach = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = loaiSachDAO.getData("SELECT * FROM LOAISACH");
        loaiSachSpinnerAdapter = new LoaiSachSpinnerAdapter(context, listLoaiSach);
        spinner.setAdapter(loaiSachSpinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoai = listLoaiSach.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtNewMaSach.setEnabled(false);
        if (type == 1) {
            edtNewMaSach.setText(String.valueOf(sach.getMaSach()));
            edtNewTenSach.setText(sach.getTenSach());
            edtNewGiaThue.setText(String.valueOf(sach.getGiaThue()));
            for (int i = 0; i < listLoaiSach.size(); i++) {
                if (listLoaiSach.get(i).getMaLoai() == sach.getMaLoai()) {
                    position = i;
                    break;
                }
            }
            spinner.setSelection(position);
        }
        lnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        lnSaveSach.setOnClickListener(v -> {
            if (validate() > 0) {
                sach = new Sach();
                sach.setTenSach(edtNewTenSach.getText().toString());
                String giaThue = edtNewGiaThue.getText().toString();
                sach.setGiaThue(Integer.parseInt(giaThue));
                sach.setMaLoai(maLoai);
                if (type == 0) {
                    if (sachDao.insert(sach) > 1) {
                        sendNotification(context, "Đã thêm " + sach.getTenSach());
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    sach.setMaSach(Integer.parseInt(edtNewMaSach.getText().toString()));
                    if (sachDao.update(sach) > 0) {
                        sendNotification(context, "Đã sửa " + sach.getMaSach());
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
                capNhatLv();
            }
        });
        dialog.show();
    }

    protected void capNhatLv() {
        list = sachDao.getAll();
        Log.d("TAG", "capNhatLv: " + list.size());
        sachAdapter = new SachAdapter(requireActivity(), this, list);
        lvSach.setAdapter(sachAdapter);
    }

    public void delete(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn xóa không? (Đồng thời xoá toàn bộ phiếu mượn sách này)");
        builder.setIcon(R.drawable.baseline_warning_24);
        builder.setCancelable(true);
        builder.setPositiveButton("Có", (dialog, which) -> {
            phieuMuonDao = new PhieuMuonDao(requireContext());
            phieuMuonList = phieuMuonDao.getData("SELECT * FROM PHIEUMUON");
            for (PhieuMuon phieuMuon : phieuMuonList) {
                if (phieuMuon.getMaSach() == Integer.parseInt(id)) {
                    phieuMuonDao.delete(String.valueOf(phieuMuon.getMaPM()));
                }
            }
            sachDao.deleteSach(id);
            capNhatLv();
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
        if (edtNewTenSach.getText().toString().isEmpty() || edtNewGiaThue.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (!isNumeric(edtNewGiaThue.getText().toString()) || Integer.parseInt(edtNewGiaThue.getText().toString()) < 0) {
            Toast.makeText(getActivity(), "Phải là số > 0", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
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