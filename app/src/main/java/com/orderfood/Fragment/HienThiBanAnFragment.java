package com.orderfood.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.orderfood.Adapter.HienThiBanAnAdapter;
import com.orderfood.DAO.BanAnDAO;
import com.orderfood.DTO.BanAnDTO;
import com.orderfood.R;
import com.orderfood.SuaBanAnActivity;
import com.orderfood.ThemBanAnActivity;

import java.util.List;

public class HienThiBanAnFragment extends Fragment {
    public static int REQ_CODE_THEM = 111,REQ_CODE_SUA = 222;
    private GridView gridViewHienThiBanAn;
    private List<BanAnDTO> banAnDTOList;
    private BanAnDAO banAnDAO;
    private HienThiBanAnAdapter hienThiBanAnAdapter;
//    private int maquyen = 0;
//    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthibanan,container,false);
        setHasOptionsMenu(true);
        gridViewHienThiBanAn=view.findViewById(R.id.gridHienThiBanAn);
//        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
//        maquyen = sharedPreferences.getInt("maquyen",0);
        banAnDAO=new BanAnDAO(getActivity());
        banAnDTOList = banAnDAO.LayTatCaBanAn();
        HienThiDanhSachBanAn();
        registerForContextMenu(gridViewHienThiBanAn);
        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }
    //tao pop up sua hoac xoa ban an
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maban = banAnDTOList.get(vitri).getMaBan();
        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaBanAnActivity.class);
                intent.putExtra("maban",maban);
                startActivityForResult(intent,REQ_CODE_SUA);
                break;
            case R.id.itXoa:
                boolean kiemtra = banAnDAO.XoaBanAnTheoMa(maban);
                if(kiemtra){
                    HienThiDanhSachBanAn();
                    Toast.makeText(getActivity(),R.string.thanhcong,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),R.string.thatbai,Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
    //tao layout cho fragment
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itThemBanAn = menu.add(1,R.id.itThemBanAn,1,R.string.thembanan);
        itThemBanAn.setIcon(R.drawable.thembanan);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }
    //tao menu them ban an
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn,REQ_CODE_THEM);
                    break;
        }
        return true;
    }
    private void HienThiDanhSachBanAn(){
        banAnDTOList = banAnDAO.LayTatCaBanAn();
        hienThiBanAnAdapter = new HienThiBanAnAdapter(getActivity(),R.layout.custom_layout_hienthibanan,banAnDTOList);
        gridViewHienThiBanAn.setAdapter(hienThiBanAnAdapter);
        hienThiBanAnAdapter.notifyDataSetChanged();
    }
    //tao pop up them hoac sua ban an
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE_THEM){
            if(resultCode==Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("ketquathem",false);
                if(kiemtra){
                    HienThiDanhSachBanAn();
                    hienThiBanAnAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(),R.string.thanhcong,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),R.string.thatbai,Toast.LENGTH_SHORT).show();
                }
            }
        }else if(requestCode==REQ_CODE_SUA){
            if(resultCode==Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("kiemtra",false);
                HienThiDanhSachBanAn();
                if(kiemtra){
                    Toast.makeText(getActivity(),R.string.thanhcong,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),R.string.thatbai,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
