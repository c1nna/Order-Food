package com.orderfood.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.orderfood.Adapter.HienThiLoaiMonAnThucDonAdapter;
import com.orderfood.DAO.LoaiMonAnDAO;
import com.orderfood.DTO.LoaiMonAnDTO;
import com.orderfood.R;
import com.orderfood.ThemThucDonActivity;

import java.util.List;

public class HienThiThucDonFragment extends Fragment {
    private GridView gridView;
    private List<LoaiMonAnDTO> loaiMonAnDTOList;
    private LoaiMonAnDAO loaiMonAnDAO;
    private FragmentManager manager;
    private int maban;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container,false);
        setHasOptionsMenu(true);
        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        loaiMonAnDTOList = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        gridView = view.findViewById(R.id.gridHienThiThucDon);
        HienThiLoaiMonAnThucDonAdapter adapter = new HienThiLoaiMonAnThucDonAdapter(getActivity(),R.layout.custom_layout_hienthiloaimonan,loaiMonAnDTOList);
        gridView.setAdapter(adapter);
        manager = getActivity().getSupportFragmentManager();
        adapter.notifyDataSetChanged();
        Bundle bDLThucDon = getArguments();
        if(bDLThucDon!=null){
            maban = bDLThucDon.getInt("maban");
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                int maloai = loaiMonAnDTOList.get(i).getMaLoai();
                HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putInt("maban",maban);
                hienThiDanhSachMonAnFragment.setArguments(bundle);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame,hienThiDanhSachMonAnFragment).addToBackStack("hienthiloai");
                transaction.commit();
            }
        });
        return view;
    }
    //tao layout
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itThemBanAn = menu.add(1,R.id.itThemThucDon,1,R.string.themthucdon);
        itThemBanAn.setIcon(R.drawable.logodangnhap);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
    //chuyen sang them thuc don
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemThucDon:
                Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                startActivity(iThemThucDon);
                getActivity().overridePendingTransition(R.anim.hieuung_activity_vao,R.anim.hieuung_activity_ra);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
