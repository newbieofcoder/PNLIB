package hoanglv.fpoly.pnlib.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hoanglv.fpoly.pnlib.DAO.ThuThuDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.models.ThuThu;

public class AddThuThuFragment extends Fragment {
    private EditText edtNewMaThuThu, edtNewNameThuThu, edtNewPasswordThuThu;
    private LinearLayout lnSaveThuThu;
    private TextView tvValidateLength, tvValidateLetter, tvValidateNumber, tvValidateSpecialCharacter;
    private static final String NAME_REGEX = "^[a-zA-Z\\s]+$";
    private static final String PASSWORD_REGEX_LONG = "^.{6,}$";
    private static final String PASSWORD_REGEX_LETTER = "^(?=.*[a-z])(?=.*[A-Z]).{1,}$";
    private static final String PASSWORD_REGEX_NUMBER = "^(?=.*\\d).{1,}$";
    private static final String PASSWORD_REGEX_SPECIAL_CHARACTER = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).{1,}$";
    private ThuThuDAO thuThuDAO;
    private ArrayList<ThuThu> thuThuList;
    private CheckBox chkTrangThai;
    Boolean existed = false;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_thu_thu, container, false);
        thuThuDAO = new ThuThuDAO(getActivity());
        thuThuList = thuThuDAO.getData();
        mappingComponent(view);
        setEvent();
        tvValidateLength.setVisibility(View.INVISIBLE);
        tvValidateLetter.setVisibility(View.INVISIBLE);
        tvValidateNumber.setVisibility(View.INVISIBLE);
        tvValidateSpecialCharacter.setVisibility(View.INVISIBLE);
        return view;
    }

    private void setEvent() {
        lnSaveThuThu.setOnClickListener(v -> {
            String maThuThu = edtNewMaThuThu.getText().toString();
            String tenThuThu = edtNewNameThuThu.getText().toString();
            String matKhau = edtNewPasswordThuThu.getText().toString();
            int trangThai = chkTrangThai.isChecked() ? 1 : 0;
            if (maThuThu.isEmpty() || tenThuThu.isEmpty() || matKhau.isEmpty()) {
                Toast.makeText(getContext(), "Bạn chưa nhập đủ thông tin",
                        Toast.LENGTH_SHORT).show();
            } else if (ifExist(maThuThu)) {
                Toast.makeText(getContext(), "Mã thủ thư đã tồn tại",
                        Toast.LENGTH_SHORT).show();
            } else if (!validateTenThuThu(tenThuThu)) {
                Toast.makeText(getContext(), "Tên thủ thư phải là chữ cái",
                        Toast.LENGTH_SHORT).show();
            } else if (!validateMaThuThu(maThuThu)) {
                Toast.makeText(getContext(), "Mã thủ thư phải ít nhất 6 ký tự bao gồm chữ cái và số, không chứa ký tự đặc biệt",
                        Toast.LENGTH_SHORT).show();
            } else if (validatePassword(matKhau)) {
                for (ThuThu thuThu: thuThuList) {
                    if (thuThu.getMaTT().equals(maThuThu)) {
                        existed = true;
                        break;
                    }
                }
                if (existed) {
                    Toast.makeText(getContext(), "Mã thủ thư đã tồn tại",
                            Toast.LENGTH_SHORT).show();
                } else {
                    ThuThu thuThu = new ThuThu(maThuThu, tenThuThu, trangThai, matKhau);
                    thuThuDAO.insert(thuThu);
                    edtNewMaThuThu.setText("");
                    edtNewNameThuThu.setText("");
                    edtNewPasswordThuThu.setText("");
                    Toast.makeText(getContext(), "Thêm thành công",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        edtNewPasswordThuThu.addTextChangedListener(new TextWatcher() {
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
    }

    private void mappingComponent(View view) {
        edtNewMaThuThu = view.findViewById(R.id.edtNewMaThuThu);
        edtNewNameThuThu = view.findViewById(R.id.edtNewNameThuThu);
        edtNewPasswordThuThu = view.findViewById(R.id.edtNewPasswordThuThu);
        tvValidateLength = view.findViewById(R.id.tvValidateLength);
        tvValidateLetter = view.findViewById(R.id.tvValidateLetter);
        tvValidateNumber = view.findViewById(R.id.tvValidateNumber);
        tvValidateSpecialCharacter = view.findViewById(R.id.tvValidateSpecialCharacter);
        lnSaveThuThu = view.findViewById(R.id.lnSaveThuThu);
        chkTrangThai = view.findViewById(R.id.chkTrangThai);
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

    private boolean validateMaThuThu(String ma){
        return ma.matches(PASSWORD_REGEX_LONG)
                && ma.matches("^(?=.*[a-zA-Z]).{1,}$")
                && !ma.matches(PASSWORD_REGEX_SPECIAL_CHARACTER);
    }

    private boolean validateTenThuThu(String ten){
        return ten.matches(NAME_REGEX);
    }

    private boolean ifExist(String ma) {
        for(ThuThu thuThu : thuThuList) {
            if(thuThu.getMaTT().equals(ma)) {
                return true;
            }
        }
        return false;
    }
}