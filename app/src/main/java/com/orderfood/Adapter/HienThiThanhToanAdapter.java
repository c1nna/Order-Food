package com.orderfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.orderfood.R;
import com.orderfood.DTO.ThanhToanDTO;

import java.util.List;

public class HienThiThanhToanAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ThanhToanDTO> thanhToanDTOs;
    private ViewHolderThanhToan viewHolderThanhToan;

    public HienThiThanhToanAdapter(Context context, int layout, List<ThanhToanDTO> thanhToanDTOs) {
        this.context = context;
        this.layout = layout;
        this.thanhToanDTOs = thanhToanDTOs;
    }

    @Override
    public int getCount() {
        return thanhToanDTOs.size();
    }

    @Override
    public Object getItem(int i) {
        return thanhToanDTOs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolderThanhToan{
        TextView txtTenMonAn,txtSoLuong,txtGiaTien;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,viewGroup,false);
            viewHolderThanhToan.txtTenMonAn=view.findViewById(R.id.txtTenMonAnThanhToan);
            viewHolderThanhToan.txtSoLuong=view.findViewById(R.id.txtSoLuongThanhToan);
            viewHolderThanhToan.txtGiaTien=view.findViewById(R.id.txtGiaTienThanhToan);
            view.setTag(viewHolderThanhToan);
        }else{
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }
        ThanhToanDTO thanhToanDTO = thanhToanDTOs.get(i);
        viewHolderThanhToan.txtTenMonAn.setText(thanhToanDTO.getTenMonAn());
        viewHolderThanhToan.txtSoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolderThanhToan.txtGiaTien.setText(String.valueOf(thanhToanDTO.getGiaTien()));
        return view;
    }
}
