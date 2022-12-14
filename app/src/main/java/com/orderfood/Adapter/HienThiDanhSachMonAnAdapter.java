package com.orderfood.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orderfood.DTO.MonAnDTO;
import com.orderfood.R;

import java.util.List;

public class HienThiDanhSachMonAnAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<MonAnDTO> monAnDTOList;
    private ViewHolderHienThiDanhSachMonAn viewHolderHienThiDanhSachMonAn;
    public HienThiDanhSachMonAnAdapter(Context context, int layout, List<MonAnDTO> monAnDTOList){
        this.context=context;
        this.layout=layout;
        this.monAnDTOList=monAnDTOList;
    }
    @Override
    public int getCount() {
        return monAnDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return monAnDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return monAnDTOList.get(i).getMaMonAn();
    }
    public class ViewHolderHienThiDanhSachMonAn{
        ImageView imgHinhMonAn;
        TextView txtTenMonAn,txtGiaTien;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderHienThiDanhSachMonAn = new ViewHolderHienThiDanhSachMonAn();
            view = inflater.inflate(layout,viewGroup,false);
            viewHolderHienThiDanhSachMonAn.imgHinhMonAn=view.findViewById(R.id.imgHienThiDSMonAn);
            viewHolderHienThiDanhSachMonAn.txtTenMonAn=view.findViewById(R.id.txtTenDSMonAn);
            viewHolderHienThiDanhSachMonAn.txtGiaTien=view.findViewById(R.id.txtGiaTienDSMonAn);
            view.setTag(viewHolderHienThiDanhSachMonAn);
        }else{
            viewHolderHienThiDanhSachMonAn = (ViewHolderHienThiDanhSachMonAn) view.getTag();
        }
        MonAnDTO monAnDTO = monAnDTOList.get(i);
        String hinhanh = monAnDTO.getHinhAnh().toString();
        if(hinhanh==null||hinhanh.equals("")){
            viewHolderHienThiDanhSachMonAn.imgHinhMonAn.setImageResource(R.drawable.backgroundheader1);
        }else{
            Uri uri = Uri.parse(hinhanh);
            viewHolderHienThiDanhSachMonAn.imgHinhMonAn.setImageURI(uri);
        }
        viewHolderHienThiDanhSachMonAn.txtTenMonAn.setText(monAnDTO.getTenMonAn());
        viewHolderHienThiDanhSachMonAn.txtGiaTien.setText(monAnDTO.getGiaTien()+" đồng");

        return view;
    }
}
