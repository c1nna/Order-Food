package com.orderfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.orderfood.Adapter.HienThiNhanVienAdapter;
import com.orderfood.DAO.NhanVienDAO;
import com.orderfood.DTO.NhanVienDTO;
import com.orderfood.DangKyActivity;
import com.orderfood.R;

import java.util.List;

public class HienThiNhanVienFragment extends Fragment {
    private ListView listView;
    private NhanVienDAO nhanVienDAO;
    private List<NhanVienDTO> nhanVienDTOList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthinhanvien,container,false);
        setHasOptionsMenu(true);
        listView = view.findViewById(R.id.lvNhanVien);
        nhanVienDAO = new NhanVienDAO(getActivity());
        HienThiDSNhanVien();
        registerForContextMenu(listView);
        return view;
    }
    private void HienThiDSNhanVien(){
        nhanVienDTOList = nhanVienDAO.LayDSNhanVien();
        HienThiNhanVienAdapter adapter = new HienThiNhanVienAdapter(getActivity(),R.layout.custom_layout_hienthinhanvien,nhanVienDTOList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itThemNhanVien = menu.add(1,R.id.itThemNhanVien,1,R.string.themnhanvien);
        itThemNhanVien.setIcon(R.drawable.nhanvien);
        itThemNhanVien.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemNhanVien:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                startActivity(iDangKy);
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manhanvien = nhanVienDTOList.get(vitri).getMANV();
        switch (id){
            case R.id.itSua:
                Intent iDangKy = new Intent(getActivity(),DangKyActivity.class);
                iDangKy.putExtra("manhanvien",manhanvien);
                startActivity(iDangKy);
                break;
            case R.id.itXoa:
                boolean kiemtra = nhanVienDAO.XoaNhanVien(manhanvien);
                if(kiemtra){
                    HienThiDSNhanVien();
                    Toast.makeText(getActivity(),R.string.thanhcong,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),R.string.thatbai,Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
