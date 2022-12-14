package com.orderfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orderfood.DTO.NhanVienDTO;
import com.orderfood.R;

import java.time.temporal.Temporal;
import java.util.List;

public class HienThiNhanVienAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<NhanVienDTO> nhanVienDTOList;
    private ViewHolderNhanVien viewHolderNhanVien;
    public HienThiNhanVienAdapter(Context context, int layout, List<NhanVienDTO> nhanVienDTOList) {
        this.context = context;
        this.layout = layout;
        this.nhanVienDTOList = nhanVienDTOList;
    }

    @Override
    public int getCount() {
        return nhanVienDTOList.size();
    }

    @Override
    public Object getItem(int i) {
        return nhanVienDTOList.get(i);
    }
    public class ViewHolderNhanVien{
        ImageView imgHinhNhanVien;
        TextView txtTenNhanVien,txtCMND;
    }
    @Override
    public long getItemId(int i) {
        return nhanVienDTOList.get(i).getMANV();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            viewHolderNhanVien = new ViewHolderNhanVien();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,viewGroup,false);
            viewHolderNhanVien.imgHinhNhanVien=view.findViewById(R.id.imgNhanVien);
            viewHolderNhanVien.txtTenNhanVien=view.findViewById(R.id.txtTenNhanVien);
            viewHolderNhanVien.txtCMND=view.findViewById(R.id.txtCMND);
            view.setTag(viewHolderNhanVien);
        }else{
            viewHolderNhanVien= (ViewHolderNhanVien) view.getTag();
        }
        NhanVienDTO nhanVienDTO = nhanVienDTOList.get(i);
        viewHolderNhanVien.txtTenNhanVien.setText(nhanVienDTO.getTENDN());
        viewHolderNhanVien.txtCMND.setText(String.valueOf(nhanVienDTO.getCMND()));
        return view;
    }
}
