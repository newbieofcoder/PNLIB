package hoanglv.fpoly.pnlib.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import hoanglv.fpoly.pnlib.R;
import hoanglv.fpoly.pnlib.fragments.DoanhThuFragment;
import hoanglv.fpoly.pnlib.fragments.DoiMatKhauFragment;
import hoanglv.fpoly.pnlib.fragments.LoaiSachFragment;
import hoanglv.fpoly.pnlib.fragments.PhieuMuonFragment;
import hoanglv.fpoly.pnlib.fragments.SachFragment;
import hoanglv.fpoly.pnlib.fragments.ThanhVienFragment;
import hoanglv.fpoly.pnlib.fragments.AddThuThuFragment;
import hoanglv.fpoly.pnlib.fragments.ThuThuFragment;
import hoanglv.fpoly.pnlib.fragments.Top10Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean admin_login_state;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextView profile_name;
    private FloatingActionButton btn_Add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        admin_login_state = intent.getBooleanExtra("admin", false);
        String fullName = intent.getStringExtra("fullName");
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        profile_name = navigationView.getHeaderView(0).findViewById(R.id.profile_name);
        if (admin_login_state) {
            profile_name.setText("Welcome administrator");
            navigationView.getMenu().findItem(R.id.nav_them_nguoi_dung).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_thu_thu).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_doi_mat_khau).setVisible(false);
        } else {
            profile_name.setText("Welcome " + fullName);
            navigationView.getMenu().findItem(R.id.nav_them_nguoi_dung).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_thu_thu).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_doi_mat_khau).setVisible(true);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Quản lý phiếu mượn");
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            replaceFragment(new PhieuMuonFragment());
            navigationView.setCheckedItem(R.id.nav_phieu_muon);
        }
        mappingComponent();
        setEvent();
    }

    private void setEvent() {
    }

    private void mappingComponent() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_phieu_muon) {
            replaceFragment(new PhieuMuonFragment());
            toolbar.setTitle("Quản lý phiếu mượn");
        } else if (id == R.id.nav_loai_sach) {
            replaceFragment(new LoaiSachFragment());
            toolbar.setTitle("Quản lý loại sách");
        } else if (id == R.id.nav_sach) {
            replaceFragment(new SachFragment());
            toolbar.setTitle("Quản lý sách");
        } else if (id == R.id.nav_thanh_vien) {
            replaceFragment(new ThanhVienFragment());
            toolbar.setTitle("Quản lý thành viên");
        } else if (id == R.id.nav_top_10_book) {
            replaceFragment(new Top10Fragment());
            toolbar.setTitle("Top 10 sách mượn nhiều nhất");
        } else if (id == R.id.nav_doanh_thu) {
            replaceFragment(new DoanhThuFragment());
            toolbar.setTitle("Doanh thu");
        } else if (id == R.id.nav_doi_mat_khau) {
            replaceFragment(new DoiMatKhauFragment());
            toolbar.setTitle("Đổi mật khẩu");
        } else if (id == R.id.nav_them_nguoi_dung) {
            replaceFragment(new AddThuThuFragment());
            toolbar.setTitle("Thêm người dùng");
        } else if (id == R.id.nav_thu_thu) {
            replaceFragment(new ThuThuFragment());
            toolbar.setTitle("Quản lý thủ thư");
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment).commit();
    }
}