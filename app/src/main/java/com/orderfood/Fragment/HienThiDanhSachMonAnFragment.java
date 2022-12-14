package com.orderfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.orderfood.Adapter.HienThiDanhSachMonAnAdapter;
import com.orderfood.DAO.MonAnDAO;
import com.orderfood.DTO.MonAnDTO;
import com.orderfood.R;
import com.orderfood.SoLuongActivity;

import java.util.List;

public class HienThiDanhSachMonAnFragment extends Fragment {
    private GridView gridView;
    private MonAnDAO monAnDAO;
    private List<MonAnDTO> monAnDTOList;
    private HienThiDanhSachMonAnAdapter hienThiDanhSachMonAnAdapter;
    private int maban;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container,false);
        gridView = view.findViewById(R.id.gridHienThiThucDon);
        monAnDAO = new MonAnDAO(getActivity());
        Bundle bundle = getArguments();
        if(bundle!=null) {
            int maloai = bundle.getInt("maloai");
            maban = bundle.getInt("maban");

            monAnDTOList = monAnDAO.LayDSMonAnTheoLoai(maloai);
            hienThiDanhSachMonAnAdapter = new HienThiDanhSachMonAnAdapter(getActivity(),R.layout.custom_layout_hienthidanhsachmonan,monAnDTOList);
            gridView.setAdapter(hienThiDanhSachMonAnAdapter);
            hienThiDanhSachMonAnAdapter.notifyDataSetChanged();
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(maban!=0){
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maban",maban);
                        iSoLuong.putExtra("mamonan",monAnDTOList.get(i).getMaMonAn());
                        startActivity(iSoLuong);
                    }
                }
            });

        }
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });
        return view;
    }
}
