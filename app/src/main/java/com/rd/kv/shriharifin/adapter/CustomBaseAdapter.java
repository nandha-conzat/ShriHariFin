package com.rd.kv.shriharifin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rd.kv.shriharifin.R;
import com.rd.kv.shriharifin.activity.InvestFullList;
import com.rd.kv.shriharifin.bean.SegmentList;

import java.util.ArrayList;

/**
 * Created by Nandha on 03-06-2016.
 */
public class CustomBaseAdapter extends BaseAdapter {

    ArrayList<SegmentList> myList = new ArrayList<SegmentList>();
    LayoutInflater inflater;
    Context context;

    public CustomBaseAdapter(Context context, ArrayList<SegmentList> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public SegmentList getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (myList != null) {
            return myList.get(position).id;
        }
        return 0;
    }

    private class MyViewHolder {

        TextView txtName, txtAmount, txtDate;
        //ImageView img;

        public MyViewHolder(View item) {
            txtName = (TextView) item.findViewById(R.id.textName);
            txtAmount = (TextView) item.findViewById(R.id.textAmount);
            txtDate = (TextView) item.findViewById(R.id.textDate);
            //img=(ImageView) item.findViewById(R.id.imageView1);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.program_list, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        SegmentList currentListData = getItem(position);

        holder.txtName.setText(currentListData.getName());
        holder.txtAmount.setText(currentListData.getAmount());
        holder.txtDate.setText(currentListData.getDate());

//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
//            }
//        });
        return convertView;
    }
}
