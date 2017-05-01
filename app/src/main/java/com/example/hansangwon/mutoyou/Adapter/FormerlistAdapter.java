package com.example.hansangwon.mutoyou.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.hansangwon.mutoyou.ListViewItem;
import com.example.hansangwon.mutoyou.R;

import java.util.ArrayList;

/**
 * Created by hansangwon on 2016-12-08.
 */

public class FormerlistAdapter extends ArrayAdapter {
    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }

    // 생성자로부터 전달된 resource id 값을 저장.
    int resourceId ;
    // 생성자로부터 전달된 ListBtnClickListener  저장.


    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    public FormerlistAdapter(Context context, int resource, ArrayList<ListViewItem> list) {
        super(context, resource, list) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource ;
    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position ;
        final Context context = parent.getContext();

        // 생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.listview_btn_item*/, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        final TextView text1 = (TextView) convertView.findViewById(R.id.kyokwa_tv_listview_former_list);
        final TextView text2 = (TextView) convertView.findViewById(R.id.credit_tv_listview_former_list);
        final TextView text3 = (TextView) convertView.findViewById(R.id.credit_tv_listview_major_list);
        final TextView text4 = (TextView) convertView.findViewById(R.id.name_tv_listview_former_list);
        final TextView text5 = (TextView) convertView.findViewById(R.id.limit_tv_listview_former_list);
        final TextView text6 = (TextView) convertView.findViewById(R.id.number_tv_listview_former_list);
        final TextView text7 = (TextView) convertView.findViewById(R.id.atrate_tv_listview_former_list);
        final TextView text8 = (TextView) convertView.findViewById(R.id.professor_tv_listview_former_list);
        final TextView text9 = (TextView) convertView.findViewById(R.id.sitetime_tv_listview_former_list);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewItem listViewItem = (ListViewItem) getItem(position);

        // 아이템 내 각 위젯에 데이터 반영
        text1.setText(listViewItem.getText1());
        text2.setText(listViewItem.getText2());
        text3.setText(listViewItem.getText3());
        text4.setText(listViewItem.getText4());
        text5.setText(listViewItem.getText5());
        text6.setText(listViewItem.getText6());
        text7.setText(listViewItem.getText7());
        text8.setText(listViewItem.getText8());
        text9.setText(listViewItem.getText9());

        return convertView;
    }
}
