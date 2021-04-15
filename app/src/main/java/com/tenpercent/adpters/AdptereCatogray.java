package com.tenpercent.adpters;

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
import com.bumptech.glide.Glide;
import com.tenpercent.activites.PrductesActivity;
import com.tenpercent.pojo.Categories;

import java.util.ArrayList;

public class AdptereCatogray extends RecyclerView.Adapter<AdptereCatogray.ViewHolderVidio> {

    private ArrayList<Categories> list;
    private Context mcontext;
int layout;

    public AdptereCatogray(ArrayList<Categories> list, Context mcontext) {

        this.list = list;
        this.mcontext = mcontext;
    }

    public AdptereCatogray(ArrayList<Categories> list, Context mcontext, int layout) {
        this.list = list;
        this.mcontext = mcontext;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolderVidio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolderVidio chatViewHolder = new ViewHolderVidio(view);
        return chatViewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolderVidio holder, final int position) {
        holder.name.setText(list.get(position).getName());



        Glide.with(mcontext)
                .load(list.get(position).getImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(view -> {

                intentMothed(PrductesActivity.class ,  list.get(position).getId().toString());
        });

    }


    private void intentMothed(Class a, String value) {

        Intent intent = new Intent(mcontext, a);
        intent.putExtra("id", value);

        mcontext.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderVidio extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;


        public ViewHolderVidio(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_ctogray);
            name = itemView.findViewById(R.id.tv_name_cato);


        }


    }



}
