package com.tenpercent.roomdatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ammarten.tenpercent.R;
import com.bumptech.glide.Glide;
import com.tenpercent.pojo.ProductCart;
import com.tenpercent.pojo.Products;


import java.util.ArrayList;
import java.util.List;


public class RoomAdpter extends RecyclerView.Adapter<RoomAdpter.MyViewHolder> {

    ArrayList<ProductCart> list;
    Context mcontext;
    private Callback mCallback;

    private ListAdapterListeneradd mListener;

    public interface ListAdapterListeneradd { // create an interface
        void onClickAtOKButtonadd(ProductCart position); // create callback function
    }


    private ListAdapterListenerminus mListenermins;

    public interface ListAdapterListenerminus { // create an interface
        void onClickAtOKButtonmins(ProductCart position); // create callback function
    }

    public RoomAdpter(ArrayList<ProductCart> list, Context mcontext, Callback mCallback,
                      ListAdapterListeneradd mListener, ListAdapterListenerminus mListenermins) {
        this.list = list;
        this.mcontext = mcontext;
        this.mCallback = mCallback;
        this.mListener = mListener;
        this.mListenermins = mListenermins;
    }

    public ArrayList<ProductCart> getList() {
        return list;
    }

    public void setList(ArrayList<ProductCart> list) {
        this.list = list;
    }

    public Callback getmCallback() {
        return mCallback;
    }

    public void setmCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    AppDatabase database = AppDatabase.getDatabaseInstance(mcontext);


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        MyViewHolder chatViewHolder = new MyViewHolder(view);
        return chatViewHolder;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tv_name.setText(list.get(position).getName());

        holder.tv_price.setText(" ج.م" + list.get(position).getPrice()+"");

        holder.tv_quntity.setText(list.get(position).getOrderCount() + "");

        ProductCart productCart=list.get(position);
        Glide.with(mcontext)
                .load(list.get(position).getImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        holder.img_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickAtOKButtonadd(list.get(position));
            }
        });
        holder.img_Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListenermins.onClickAtOKButtonmins(list.get(position));
            }
        });


        holder.img_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickAtOKButtonadd(list.get(position));
            }
        });


        holder.img_deleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mCallback.onDeleteClick(productCart);
                holder.onBind(position);
            }
        });
    }


    public interface Callback {
        void onDeleteClick(ProductCart mUser);
    }

    public void addItems(List<ProductCart> userList) {
        list = (ArrayList<ProductCart>) userList;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {

            return list.size();
        } else {
            return 0;
        }
    }

    public void updateData(ArrayList<ProductCart> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends BaseViewHolder {


        ImageView imageView, img_Add, img_Minus, img_deleted;
        TextView tv_quntity, tv_name, tv_desc, tv_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_cart);
            img_Add = itemView.findViewById(R.id.img_add_cart);
            img_deleted = itemView.findViewById(R.id.img_delete_cart);
            img_Minus = itemView.findViewById(R.id.img_mins);
            tv_name = itemView.findViewById(R.id.tv_name_cart);
            tv_price = itemView.findViewById(R.id.tv_price_cart);
            tv_quntity = itemView.findViewById(R.id.tv_quntity);
        }

        @Override
        protected void clear() {


        }


    }
}

