package hoanglv.fpoly.pnlib.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import hoanglv.fpoly.pnlib.DAO.ThuThuDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.models.ThuThu;
import hoanglv.fpoly.pnlib.ui.Login;

public class DoiMatKhauFragment extends Fragment {
    private ThuThuDAO thuThuDAO;
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
        // Inflate the layout for this fragment
        thuThuDAO = new ThuThuDAO(getContext());
        Intent intent = requireActivity().getIntent();
        Bundle bundle = intent.getExtras();
        String password = bundle.getString("password");
        String username = bundle.getString("username");
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);

        EditText edtOldPassword = view.findViewById(R.id.edtOldPassword);
        EditText edtNewPassword = view.findViewById(R.id.edtNewPassword);
        EditText edtConfirmNewPassword = view.findViewById(R.id.edtConfirmNewPassword);
        tvValidateLength = view.findViewById(R.id.tvValidateLength);
        tvValidateLetter = view.findViewById(R.id.tvValidateLetter);
        tvValidateNumber = view.findViewById(R.id.tvValidateNumber);
        tvValidateSpecialCharacter = view.findViewById(R.id.tvValidateSpecialCharacter);

        tvValidateLength.setVisibility(View.INVISIBLE);
        tvValidateLetter.setVisibility(View.INVISIBLE);
        tvValidateNumber.setVisibility(View.INVISIBLE);
        tvValidateSpecialCharacter.setVisibility(View.INVISIBLE);

        TextView tvOK = view.findViewById(R.id.tvOK);
        tvOK.setOnClickListener(v -> {
            String oldPassword = edtOldPassword.getText().toString();
            String newPassword = edtNewPassword.getText().toString();
            String confirmNewPassword  = edtConfirmNewPassword.getText().toString();
            if (!Objects.equals(password, oldPassword)) {
                Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
            } else if (!newPassword.equals(confirmNewPassword)) {
                Toast.makeText(getContext(), "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
            } else if (validatePassword(newPassword)) {
                thuThuDAO.updatePassword(newPassword, username);
                Toast.makeText(getContext(), "Đổi mật khẩu thành công, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getContext(), Login.class);
                startActivity(intent1);
                requireActivity().finish();
            }
        });

        edtNewPassword.addTextChangedListener(new TextWatcher() {
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
        return view;
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