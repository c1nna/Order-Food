package com.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.orderfood.Fragment.HienThiBanAnFragment;
import com.orderfood.Fragment.HienThiNhanVienFragment;
import com.orderfood.Fragment.HienThiThucDonFragment;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView naviView;
    private Toolbar toolbar;
    private TextView txtTenNhanVien_Navi;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);
        drawerLayout=findViewById(R.id.drawerlayout);
        naviView=findViewById(R.id.navi_trangchu);
        toolbar=findViewById(R.id.toolbar);
        //set ten va anh o naviView
        View view = naviView.inflateHeaderView(R.layout.layout_header_trangchu);
        txtTenNhanVien_Navi=view.findViewById(R.id.txtTenNV_navi);
        //tao actionbar menu o trang chu
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.mo,R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //end
        naviView.setItemIconTintList(null);
        naviView.setNavigationItemSelectedListener(this);
        Intent intent = getIntent();
        String tendn = intent.getStringExtra("tendn");
        txtTenNhanVien_Navi.setText(tendn);
        FragmentTransaction transactionHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAn = new HienThiBanAnFragment();
        transactionHienThiBanAn.replace(R.id.frame,hienThiBanAn);
        transactionHienThiBanAn.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itTrangChu:
                FragmentTransaction transactionHienThiBanAn = fragmentManager.beginTransaction();
                HienThiBanAnFragment hienThiBanAn = new HienThiBanAnFragment();
                transactionHienThiBanAn.replace(R.id.frame,hienThiBanAn);
                transactionHienThiBanAn.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.itThemThucDon:
                FragmentTransaction transactionHienThiThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDon = new HienThiThucDonFragment();
                transactionHienThiThucDon.setCustomAnimations(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                transactionHienThiThucDon.replace(R.id.frame,hienThiThucDon);
                transactionHienThiThucDon.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.itNhanVien:
                FragmentTransaction transactionNhanVien = fragmentManager.beginTransaction();
                HienThiNhanVienFragment hienThiNhanVien = new HienThiNhanVienFragment();
                transactionNhanVien.replace(R.id.frame,hienThiNhanVien);
                transactionNhanVien.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.itDangxuat:
                Intent intent = new Intent(TrangChuActivity.this,DangNhapActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return false;
    }
}