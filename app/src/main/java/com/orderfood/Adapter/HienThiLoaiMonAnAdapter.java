package com.orderfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.orderfood.DTO.LoaiMonAnDTO;
import com.orderfood.R;

import java.util.List;
//class hien thi loai mon an vao spinner them thuc don mon an
public class HienThiLoaiMonAnAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<LoaiMonAnDTO> loaiMonAnDTOList;
    private ViewHolderLoaiMonAn viewHolderLoaiMonAn;
    public HienThiLoaiMonAnAdapter(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
        this.context=context;
        this.layout=layout;
        this.loaiMonAnDTOList=loaiMonAnDTOList;
    }
    @Override
    public int getCount() {
        return loaiMonAnDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiMonAnDTOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return loaiMonAnDTOList.get(i).getMaLoai();
    }
    public class ViewHolderLoaiMonAn{
        TextView txtTenLoai;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinloaithucdon,viewGroup,false);
            viewHolderLoaiMonAn.txtTenLoai=view.findViewById(R.id.txtTenLoai);
            view.setTag(viewHolderLoaiMonAn);
        }else{
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }
        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(i);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());
        return view;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinloaithucdon,viewGroup,false);
            viewHolderLoaiMonAn.txtTenLoai=view.findViewById(R.id.txtTenLoai);
            view.setTag(viewHolderLoaiMonAn);
        }else{
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }
        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(i);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());
        return view;
    }
}
