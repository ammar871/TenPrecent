package com.tenpercent.adpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ammarten.tenpercent.R;
import com.bumptech.glide.Glide;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.pojo.ProductCart;
import com.tenpercent.pojo.Products;
import com.tenpercent.roomdatabase.AppDatabase;
import com.tenpercent.roomdatabase.RoomAdpter;

import java.util.ArrayList;
import java.util.List;

public class AdapterFavorate extends RecyclerView.Adapter<AdapterFavorate.ViewHolderVidio> {

    private List<Products> list;
    private Context mcontext;
    private int limit = 6;

    int layout;
    AppDatabase database;
    ShardEditor shardEditor;
    private OnClickListenerFav mListener;
    private Callback mCallback;
    public interface OnClickListenerFav { // create an interface
        void onClickItemViewFav(Products position); // create callback function
    }

    public Callback getmCallback() {
        return mCallback;
    }

    public void setmCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }


    public AdapterFavorate(List<Products> list, Context mcontext, OnClickListenerFav mListener, Callback mCallback) {
        this.list = list;
        this.mcontext = mcontext;
        this.mListener = mListener;
        this.mCallback = mCallback;
        database = AppDatabase.getDatabaseInstance(mcontext);
        shardEditor = new ShardEditor(mcontext);
    }



    @NonNull
    @Override
    public ViewHolderVidio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav, parent, false);
       ViewHolderVidio chatViewHolder = new ViewHolderVidio(view);
        return chatViewHolder;

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderVidio holder, final int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_desc.setText(list.get(position).getDesc());
        holder.tv_price.setText(list.get(position).getPrice()+"");


        Glide.with(mcontext)
                .load(list.get(position).getImage())
                .into(holder.imageView);




//        isFavoraite(holder, position);
//        isAddedCart(holder, productCart);

//        holder.tv_add_cart.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("UseCompatLoadingForDrawables")
//            @Override
//            public void onClick(View v) {
//                if (shardEditor.loadDataEnter().get(ShardEditor.IS_LOGIN)) {
//
//                    if (database.cartDao().findByName(productCart.getId())) {
//                        database.cartDao().delete(productCart);
//                        holder.tv_add_cart.setBackground(mcontext.getResources().getDrawable(R.drawable.drawble_text));
//                        Toast.makeText(mcontext, "Deleted", Toast.LENGTH_SHORT).show();
//                        holder.tv_add_cart.setTextColor(mcontext.getResources().getColor(R.color.purple_500));
//                        holder.tv_add_cart.setText("أضف  للسلة ");
//
//                    } else {
//                        database.cartDao().insertUser(productCart);
//                        holder.tv_add_cart.setBackground(mcontext.getResources().getDrawable(R.drawable.draw_added_cardt));
//                        Toast.makeText(mcontext, "added", Toast.LENGTH_SHORT).show();
//                        holder.tv_add_cart.setTextColor(mcontext.getResources().getColor(R.color.white));
//                        holder.tv_add_cart.setText("تمت اضافتها للسلة ");
//
//                    }
//
//                } else {
//                    Toast.makeText(mcontext, "من فضلك سجل الدخول اولا", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });

//        holder.img_fav.setOnClickListener(v -> {
//            if (shardEditor.loadDataEnter().get(ShardEditor.IS_LOGIN)) {
//
//                if (database.userDao().findByName(list.get(position).getId())) {
//                    database.userDao().delete(list.get(position));
//                    holder.img_fav.setImageResource(R.drawable.ic_unfavorite);
//                    Toast.makeText(mcontext, "Deleted From your wishList", Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
//                    notifyItemChanged(position);
//
//                } else {
//                    database.userDao().insertUser(list.get(position));
//                    holder.img_fav.setImageResource(R.drawable.ic_favorite_24);
//
//                    Toast.makeText(mcontext, "Added on your wishList", Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
//                    notifyItemChanged(position);
//
//                }
//            } else {
//                Toast.makeText(mcontext, "من فضلك سجل الدخول اولا", Toast.LENGTH_SHORT).show();
//            }
//
//        });



        holder.itemView.setOnClickListener(view -> mListener.onClickItemViewFav(list.get(position)));

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void isAddedCart(AdapterProductAll.ViewHolderVidio holder, ProductCart position) {
        if (database.cartDao().findByName(position.getId())) {
            holder.tv_add_cart.setBackground(mcontext.getResources().getDrawable(R.drawable.draw_added_cardt));

            holder.tv_add_cart.setTextColor(mcontext.getResources().getColor(R.color.white));
            holder.tv_add_cart.setText("تمت اضافتها للسلة ");

        } else {


            holder.tv_add_cart.setBackground(mcontext.getResources().getDrawable(R.drawable.drawble_text));
            holder.tv_add_cart.setTextColor(mcontext.getResources().getColor(R.color.purple_500));
            holder.tv_add_cart.setText("أضف للسلة ");

        }

    }

    private void isFavoraite(@NonNull AdapterProductAll.ViewHolderVidio holder, int position) {
        if (database.userDao().findByName(list.get(position).getId())) {
            holder.img_fav.setImageResource(R.drawable.ic_favorite_24);


        } else {
            holder.img_fav.setImageResource(R.drawable.ic_unfavorite);

        }
    }


    private void intentMothed(Class a, Products value) {

        Intent intent = new Intent(mcontext, a);
        intent.putExtra("pro_cato", (Parcelable) value);

        mcontext.startActivity(intent);
    }

    public interface Callback {
        void onDeleteClick(Products mUser);
    }

    public void addItems(List<Products> userList) {
        list = (ArrayList<Products>) userList;
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
//        if (list.size() > limit)
//        return limit;
//        else
        return list.size();
    }

    public static class ViewHolderVidio extends RecyclerView.ViewHolder {


        ImageView imageView, img_fav;
        TextView tv_name, tv_desc,tv_price;

        public RelativeLayout viewBackground;
        public ConstraintLayout viewForeground;
        public ViewHolderVidio(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_product_fav);
            tv_name = itemView.findViewById(R.id.tv_name_fav);
            tv_desc = itemView.findViewById(R.id.tv_desc_fav);
            tv_price = itemView.findViewById(R.id.tv_pric_fav);
            viewForeground = itemView.findViewById(R.id.view_for_background);
            viewBackground = itemView.findViewById(R.id.view_background);

        }


    }}

