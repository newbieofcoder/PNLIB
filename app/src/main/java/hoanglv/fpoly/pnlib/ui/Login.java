package hoanglv.fpoly.pnlib.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import hoanglv.fpoly.pnlib.DAO.ThuThuDAO;
import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.models.ThuThu;

public class Login extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private CheckBox chkRememberPassword;
    private TextView txtLogin, txtCancel;
    private ThuThuDAO thuThuDAO;
    private static final String ADMIN = "admin";
    private List<ThuThu> thuThuList;
    private Boolean existed = false;
    private String fullName;
    private int trangThai;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        thuThuDAO = new ThuThuDAO(this);
        thuThuList = thuThuDAO.getData();
        mappingComponent();
        setEvent();
        checkRemember();
    }

    private void setEvent() {
        txtCancel.setOnClickListener(v -> finish());
        txtLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Bạn chưa nhập đủ thông tin",
                        Toast.LENGTH_SHORT).show();
            } else if(username.equals(ADMIN) && password.equals(ADMIN)) {
                Toast.makeText(Login.this, "Đăng nhập dưới quyền quản trị viên",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("admin", true);
                intent.putExtra("password", password);
                intent.putExtra("trangThai", 1);
                startActivity(intent);
                finish();
            } else {
                for (int i = 0; i < thuThuList.size(); i++) {
                    if (username.equals(thuThuList.get(i).getMaTT())
                            && password.equals(thuThuList.get(i).getMatKhau())) {
                        fullName = thuThuList.get(i).getTenTT();
                        trangThai = thuThuList.get(i).getTrangThai();
                        position = i;
                        existed = true;
                        break;
                    }
                }
                if (!existed) {
                    Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không đúng",
                            Toast.LENGTH_SHORT).show();
                } else {
                    rememberMe(username, password, chkRememberPassword.isChecked());
                    Toast.makeText(Login.this, "Đăng nhập dưới quyền thủ thư",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("admin", false);
                    bundle.putString("username", username);
                    bundle.putString("fullName", fullName);
                    bundle.putString("password", password);
                    bundle.putInt("trangThai", trangThai);
                    bundle.putInt("position", position);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void mappingComponent() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        chkRememberPassword = findViewById(R.id.chkRememberPassword);
        txtLogin = findViewById(R.id.txtLogin);
        txtCancel = findViewById(R.id.txtCancel);
    }

    private void checkRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences("remembered", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        boolean remember = sharedPreferences.getBoolean("remember", false);
        chkRememberPassword.setChecked(remember);
        if (chkRememberPassword.isChecked()) {
            edtUsername.setText(username);
            edtPassword.setText(password);
        }
    }

    private void rememberMe(String username, String password, boolean remember) {
        SharedPreferences sharedPreferences = getSharedPreferences("remembered", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("remember", remember);
        editor.apply();
    }
}