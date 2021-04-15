package com.tenpercent.adpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ammarten.tenpercent.R;
import com.tenpercent.pojo.ModelsOrders;
import com.tenpercent.pojo.Orders;

import java.util.ArrayList;

public class AdpterOrders extends RecyclerView.Adapter<AdpterOrders.ViewHolderVidio> {

    private ArrayList<ModelsOrders> list;
    private Context mcontext;


    public AdpterOrders(ArrayList<ModelsOrders> list, Context mcontext) {

        this.list = list;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolderVidio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordres, parent, false);
        ViewHolderVidio chatViewHolder = new ViewHolderVidio(view);
        return chatViewHolder;

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderVidio holder, final int position) {
        holder.numb_orders.setText("رقم الطلب : " + list.get(position).getId()+"");
        holder.tv_total.setText(list.get(position).getTotalCost()+"");


//        holder.tv_date.setText(list.get(position).getDateOrder());


    }


//    private void intentMothed(Class a, Catogray value) {
//
//        Intent intent = new Intent(mcontext, a);
//        intent.putExtra("catogery", value);
//
//        mcontext.startActivity(intent);
//    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderVidio extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView numb_orders,  tv_date, tv_status, tv_total;


        public ViewHolderVidio(@NonNull View itemView) {
            super(itemView);


            numb_orders = itemView.findViewById(R.id.tv_num_order);
//            tv_date = itemView.findViewById(R.id.tv_date_order);

            tv_status = itemView.findViewById(R.id.tv_status_order);
            tv_total = itemView.findViewById(R.id.tv_totalV_order);


        }


    }


}
