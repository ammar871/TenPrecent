package com.tenpercent.adpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ammarten.tenpercent.R;
import com.bumptech.glide.Glide;
import com.tenpercent.activites.interFace.CallFave;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.pojo.ProductCart;
import com.tenpercent.pojo.Products;
import com.tenpercent.roomdatabase.AppDatabase;
import java.util.List;

public class AdapterOffers extends RecyclerView.Adapter<AdapterOffers.ViewHolderVidio> {

    private List<Products> list;
    private Context mcontext;
    private  int limit = 6;
    AppDatabase database;
    ShardEditor shardEditor;

    CallFave callFave;

    public interface CallbackInterface {
        void onCall();
    }


    private OnClickListenerGrid mListener;
    public interface OnClickListenerGrid { // create an interface
        void onClickItemViewGrid(Products position); // create callback function
        void getCounterCart(int count,String name);
    }

    public AdapterOffers(OnClickListenerGrid mListener , Context mcontext) {
        this.mcontext = mcontext;
        this.mListener = mListener;
        database=AppDatabase.getDatabaseInstance(mcontext);
        shardEditor=new ShardEditor(mcontext);

    }

    public AdapterOffers(List<Products> list, Context mcontext, OnClickListenerGrid mListener,CallFave callFave) {
        this.list = list;
        this.mcontext = mcontext;
        this.mListener = mListener;
        database=AppDatabase.getDatabaseInstance(mcontext);
        shardEditor=new ShardEditor(mcontext);
        this.callFave=callFave;
    }



    @NonNull
    @Override
    public ViewHolderVidio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produt, parent, false);
        ViewHolderVidio chatViewHolder = new ViewHolderVidio(view);
        return chatViewHolder;

    }


    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderVidio holder, final int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_desc.setText(list.get(position).getDesc());
        holder.tv_price.setText(list.get(position).getPrice()+"");
        isFavoraite(holder,position);

        Glide.with(mcontext)
                .load(list.get(position).getImage())
                .into(holder.imageView);

        ProductCart productCart = new ProductCart(list.get(position).getId(),
                list.get(position).getName(),
                list.get(position).getDesc(),
                list.get(position).getImage(),
                list.get(position).getCategoryId(),
                list.get(position).getSellerId(),
                1,
                list.get(position).getRate(),
                list.get(position).getPrice(),
                list.get(position).getDiscount()

        );

        isAddedCart(holder, productCart);

        holder.tv_add_cart.setOnClickListener(v -> {

            if (shardEditor.loadDataEnter().get(ShardEditor.IS_LOGIN)){

                if (database.cartDao().findByName(productCart.getId())) {
                    database.cartDao().delete(productCart);
                    holder.tv_add_cart.setBackground(mcontext.getResources().getDrawable(R.drawable.drawble_text));

                    holder.tv_add_cart.setTextColor(mcontext.getResources().getColor(R.color.purple_500));
                    holder.tv_add_cart.setText("أضف  للسلة ");
                    int size=database.cartDao().getAll().size();
                   // Toast.makeText(mcontext, ""+size, Toast.LENGTH_SHORT).show();
                    mListener.getCounterCart(size,"تم حذف "+ " "+ list.get(position).getName());

                } else {
                    database.cartDao().insertUser(productCart);
                    holder.tv_add_cart.setBackground(mcontext.getResources().getDrawable(R.drawable.draw_added_cardt));

                    holder.tv_add_cart.setTextColor(mcontext.getResources().getColor(R.color.white));
                    holder.tv_add_cart.setText("تمت اضافتها للسلة ");
                    int size=database.cartDao().getAll().size();
                      mListener.getCounterCart(size,"تم اضافتها الى السلة  ");
                }

            }else {
                Toast.makeText(mcontext, "من فضلك سجل الدخول اولا", Toast.LENGTH_SHORT).show();
            }


        });

        holder.img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (database.userDao().findByName(list.get(position).getId())){
                    database.userDao().delete(list.get(position));
                    holder.img_fav.setImageResource(R.drawable.ic_unfavorite);

                    notifyDataSetChanged();
                    notifyItemChanged(position);
                    int size=database.userDao().getAll().size();
                    callFave.getCounterFaveOffers(size,"تم حذفها من المفضلــة");

                }else {
                    database.userDao().insertUser(list.get(position));
                    holder.img_fav.setImageResource(R.drawable.ic_favorite_24);

                    notifyDataSetChanged();
                    notifyItemChanged(position);
                    int size=database.userDao().getAll().size();
                    callFave.getCounterFaveOffers(size,"تم اضافتهــا للمفضلة ");
                }
            }
        });



        holder.itemView.setOnClickListener(view -> mListener.onClickItemViewGrid(list.get(position)));

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void isAddedCart(ViewHolderVidio holder, ProductCart position) {
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
    private void isFavoraite(@NonNull ViewHolderVidio holder, int position) {
        if (database.userDao().findByName(list.get(position).getId())){
            holder.img_fav.setImageResource(R.drawable.ic_favorite_24);



        }else {
            holder.img_fav.setImageResource(R.drawable.ic_unfavorite);

        }
    }

    private void intentMothed(Class a, Products value) {

        Intent intent = new Intent(mcontext, a);
        intent.putExtra("pro_cato", (Parcelable) value);

        mcontext.startActivity(intent);
    }


    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class ViewHolderVidio extends RecyclerView.ViewHolder {

        ImageView imageView, img_fav;
        TextView tv_name, tv_desc,tv_add_cart,tv_price;


        public ViewHolderVidio(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_product_horz);
            img_fav = itemView.findViewById(R.id.img_fav_product);
            tv_name = itemView.findViewById(R.id.tv_producte_name_horz);
            tv_desc = itemView.findViewById(R.id.tv_desc_product_horz);
            tv_price = itemView.findViewById(R.id.tv_pric_hrz);
            tv_add_cart = itemView.findViewById(R.id.tv_add_cart);

        }

    }

}

