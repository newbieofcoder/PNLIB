package hoanglv.fpoly.pnlib.fragments;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import hoanglv.fpoly.pnlib.DAO.ThuThuDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.adapters.ThuThuAdapter;
import hoanglv.fpoly.pnlib.models.ThuThu;
import hoanglv.fpoly.pnlib.services.MyApplication;

public class ThuThuFragment extends Fragment {
    ListView lvThuThu;
    ThuThu thuThu;
    ThuThuAdapter thuThuAdapter;
    static ThuThuDAO thuThuDAO;
    static ArrayList<ThuThu> thuThuList;
    Dialog dialog;
    EditText edtMaThuThu, edtTenThuThu, edtMatKhau;
    CheckBox chkTrangThai;
    LinearLayout lnSaveThuThu, lnCancel;
    private TextView tvValidateLength, tvValidateLetter, tvValidateNumber, tvValidateSpecialCharacter;
    private static final String PASSWORD_REGEX_LONG = "^.{6,}$";
    private static final String PASSWORD_REGEX_LETTER = "^(?=.*[a-z])(?=.*[A-Z]).{1,}$";
    private static final String PASSWORD_REGEX_NUMBER = "^(?=.*\\d).{1,}$";
    private static final String PASSWORD_REGEX_SPECIAL_CHARACTER = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).{1,}$";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu_thu, container, false);
        lvThuThu = view.findViewById(R.id.lvThuThu);
        thuThuDAO = new ThuThuDAO(getActivity());
        capNhatLV();
        lvThuThu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                thuThu = thuThuList.get(position);
                dialog = new Dialog(requireActivity());
                dialog.setContentView(R.layout.dialog_thu_thu);
                edtMaThuThu = dialog.findViewById(R.id.edtMaThuThu);
                edtTenThuThu = dialog.findViewById(R.id.edtTenThuThu);
                edtMatKhau = dialog.findViewById(R.id.edtMatKhau);
                chkTrangThai = dialog.findViewById(R.id.chkTrangThai);
                lnSaveThuThu = dialog.findViewById(R.id.lnSaveThuThu);
                lnCancel = dialog.findViewById(R.id.lnCancel);
                tvValidateLength = dialog.findViewById(R.id.tvValidateLength);
                tvValidateLetter = dialog.findViewById(R.id.tvValidateLetter);
                tvValidateNumber = dialog.findViewById(R.id.tvValidateNumber);
                tvValidateSpecialCharacter = dialog.findViewById(R.id.tvValidateSpecialCharacter);
                edtMatKhau.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        validatePassword(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edtMaThuThu.setEnabled(false);
                edtMaThuThu.setText(thuThu.getMaTT());
                edtTenThuThu.setText(thuThu.getTenTT());
                edtMatKhau.setText(thuThu.getMatKhau());
                chkTrangThai.setChecked(thuThu.getTrangThai() == 1);

                lnCancel.setOnClickListener(v -> {
                    dialog.dismiss();
                });

                lnSaveThuThu.setOnClickListener(v -> {
                    String ma = edtMaThuThu.getText().toString();
                    String ten = edtTenThuThu.getText().toString();
                    String matKhau = edtMatKhau.getText().toString();
                    int trangThai = chkTrangThai.isChecked() ? 1 : 0;
                    if (validate() > 0) {
                        thuThu = new ThuThu();
                        thuThu.setMaTT(ma);
                        thuThu.setTenTT(ten);
                        thuThu.setMatKhau(matKhau);
                        thuThu.setTrangThai(trangThai);
                        if (thuThuDAO.update(thuThu) > 0) {
                            sendNotification(getActivity(), "Đã sửa " + thuThu.getMaTT());
                        }
                        capNhatLV();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return false;
            }
        });
        return view;
    }

    private void capNhatLV() {
        thuThuList = thuThuDAO.getData();
        thuThuAdapter = new ThuThuAdapter(requireActivity(), this, thuThuList);
        lvThuThu.setAdapter(thuThuAdapter);
    }

    private int validate() {
        int check = 1;
        if (edtTenThuThu.getText().toString().isEmpty() || edtMatKhau.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
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

    private boolean validatePassword(String password) {
        tvValidateLength.setVisibility(View.VISIBLE);
        tvValidateLetter.setVisibility(View.VISIBLE);
        tvValidateNumber.setVisibility(View.VISIBLE);
        tvValidateSpecialCharacter.setVisibility(View.VISIBLE);
        if (password.matches(PASSWORD_REGEX_LONG)) {
            tvValidateLength.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvValidateLength.setTextColor(getResources().getColor(R.color.red));
        }
        if (password.matches(PASSWORD_REGEX_LETTER)) {
            tvValidateLetter.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvValidateLetter.setTextColor(getResources().getColor(R.color.red));
        }
        if (password.matches(PASSWORD_REGEX_NUMBER)) {
            tvValidateNumber.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvValidateNumber.setTextColor(getResources().getColor(R.color.red));
        }
        if (password.matches(PASSWORD_REGEX_SPECIAL_CHARACTER)) {
            tvValidateSpecialCharacter.setTextColor(getResources().getColor(R.color.green));
        } else {
            tvValidateSpecialCharacter.setTextColor(getResources().getColor(R.color.red));
        }
        return password.matches(PASSWORD_REGEX_LONG) && password.matches(PASSWORD_REGEX_LETTER)
                && password.matches(PASSWORD_REGEX_NUMBER) && password.matches(PASSWORD_REGEX_SPECIAL_CHARACTER);
    }
}