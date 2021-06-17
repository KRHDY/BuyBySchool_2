package com.example.registerloginexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//3. BaseAdapter를 상속받아 Adapter구현
public class ListViewAdapter extends BaseAdapter implements Filterable {

    private ImageView iconImageView;
    private TextView titleTextView;
    private TextView contentTextView;
    private TextView priceTextView;

    //    데이터를 넣은 리스트 변수(원본데이터)
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    //    필터링 결과를 저장하기 위한 ArrayList.
    private ArrayList<ListViewItem> filteredItemList = listViewItemList;

    Filter listFilter;

    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return filteredItemList.size();
    }


//    지정한 위치에 있는 데이터와 관계된 아이템의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {

//        Log.i("TAG",listViewItemList.get(position));
        return filteredItemList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int post = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }
        titleTextView = (TextView) convertView.findViewById(R.id.title);
        iconImageView = (ImageView) convertView.findViewById(R.id.icon);
        contentTextView = (TextView) convertView.findViewById(R.id.text);
        priceTextView = (TextView) convertView.findViewById(R.id.price);

//        ListViewItem mlistViewItem = listViewItemList.get(position);
        ListViewItem mlistViewItem = filteredItemList.get(position);

        Log.i("뭐냐", "왜이래");
        iconImageView.setImageResource(mlistViewItem.getIcon());
        titleTextView.setText(mlistViewItem.getTitle());
        contentTextView.setText(mlistViewItem.getContent());

        Log.i("뭐냐", mlistViewItem.getTitle());
        Log.i("뭐냐", mlistViewItem.getPrice() + "");
        Log.i("뭐냐", mlistViewItem.getContent());


        priceTextView.setText(mlistViewItem.getPrice() + "");

        return convertView;
    }

    public void addItem(String title, int icon, int price, String content) {
        ListViewItem item = new ListViewItem();

        Log.i("check", price + content);

        Log.i("addItem", title);
        Log.i("addItem", price + "");
        Log.i("addItem", content);

        item.setTitle(title);
        item.setContent(content); // price
        item.setIcon(icon);
        item.setPrice(price); // writer
        System.out.println("-------gettitle" + item.getTitle() + "------getprice " + item.getPrice() + "-----getcontent " + item.getContent() );

        listViewItemList.add(item);
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null){
            listFilter = new ListFilter();
        }

        return listFilter;
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;

            if (constraint == null || constraint.length() == 0) {
                results.values = listViewItemList ;
                results.count = listViewItemList.size() ;
            } else {
                ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>() ;

                for (ListViewItem item : listViewItemList) {
                    if (item.getTitle().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                            item.getContent().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item) ;
                    }
                }

                results.values = itemList ;
                results.count = itemList.size() ;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            // update listview by filtered data list.
            filteredItemList = (ArrayList<ListViewItem>) results.values ;

            // notify
            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }
    }

}
