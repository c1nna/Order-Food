package com.orderfood.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orderfood.DAO.LoaiMonAnDAO;
import com.orderfood.DTO.LoaiMonAnDTO;
import com.orderfood.R;
import java.util.List;
//class hien thi loai thuc don voi ten loai va hinh anh loai thuc don lay hinh anh mon an moi nhat de set cho hinh an
public class HienThiLoaiMonAnThucDonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<LoaiMonAnDTO> loaiMonAnDTOList;
    private ViewHolderHienThiLoaiThucDon viewHolderHienThiLoaiThucDon;
    private LoaiMonAnDAO loaiMonAnDAO;
    public HienThiLoaiMonAnThucDonAdapter(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
        this.context=context;
        this.layout=layout;
        this.loaiMonAnDTOList=loaiMonAnDTOList;
        loaiMonAnDAO = new LoaiMonAnDAO(context);
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
    public class ViewHolderHienThiLoaiThucDon{
        private ImageView imgHinhLoaiThucDon;
        private TextView txtTenLoaiThucDon;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            viewHolderHienThiLoaiThucDon = new ViewHolderHienThiLoaiThucDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,viewGroup,false);
            viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon = view.findViewById(R.id.imgHienThiMonAn);
            viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon = view.findViewById(R.id.txtTenLoaiThucDon);
            view.setTag(viewHolderHienThiLoaiThucDon);
        }else{
            viewHolderHienThiLoaiThucDon = (ViewHolderHienThiLoaiThucDon) view.getTag();
        }
        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(i);
        int maloai = loaiMonAnDTO.getMaLoai();
        String hinhanh = loaiMonAnDAO.LayHinhLoaiMonAn(maloai);
        Uri uri = Uri.parse(hinhanh);
        viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon.setText(loaiMonAnDTO.getTenLoai());
        viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon.setImageURI(uri);
        return view;
    }
}
