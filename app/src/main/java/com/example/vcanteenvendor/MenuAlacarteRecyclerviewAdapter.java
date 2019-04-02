package com.example.vcanteenvendor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MenuAlacarteRecyclerviewAdapter extends RecyclerView.Adapter<MenuAlacarteRecyclerviewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Menu> mData ;

    RequestOptions option;


    public MenuAlacarteRecyclerviewAdapter(Context mContext, List<Menu> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        //option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        option = new RequestOptions().centerCrop();
    }

    @Override
    public MenuAlacarteRecyclerviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.menu_card,parent,false);
        final MenuAlacarteRecyclerviewAdapter.MyViewHolder viewHolder = new MenuAlacarteRecyclerviewAdapter.MyViewHolder(view);


        viewHolder.cardViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext,AddEditMenuActivity.class);
                // passing data to the book activity
                i.putExtra("foodId",mData.get(viewHolder.getAdapterPosition()).getFoodId());
                i.putExtra("foodName",mData.get(viewHolder.getAdapterPosition()).getFoodName());
                i.putExtra("price",mData.get(viewHolder.getAdapterPosition()).getFoodPrice());
                i.putExtra("foodImageUrl",mData.get(viewHolder.getAdapterPosition()).getFoodImg());
                i.putExtra("foodStatus",mData.get(viewHolder.getAdapterPosition()).getFoodStatus());
                i.putExtra("foodType",mData.get(viewHolder.getAdapterPosition()).getFoodType());
                // start the activity
                mContext.startActivity(i);

            }
        });


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.card_food_name.setText(mData.get(position).getFoodName());
        //holder.card_food_price.setText(mData.get(position).getFoodPrice());
        holder.card_food_price.setText(String.format ("%d", mData.get(position).getFoodPrice()));
        Glide.with(mContext).load(mData.get(position).getFoodImg()).apply(option).into(holder.menuImg); //Set image via url using Glide

        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,AddEditMenuActivity.class);
                // passing data to the book activity
                intent.putExtra("foodId",mData.get(position).getFoodId());
                intent.putExtra("foodName",mData.get(position).getFoodName());
                intent.putExtra("price",mData.get(position).getFoodPrice());
                intent.putExtra("foodImageUrl",mData.get(position).getFoodImg());
                intent.putExtra("foodStatus",mData.get(position).getFoodStatus());
                // start the activity
                mContext.startActivity(intent);

            }
        });*/

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView card_food_name;
        TextView card_food_price;
        ImageView menuImg;
        RelativeLayout cardViewContainer;
        CardView cardView ;


        public MyViewHolder(View itemView) {
            super(itemView);

            card_food_name = (TextView) itemView.findViewById(R.id.card_food_name) ;
            card_food_price = (TextView) itemView.findViewById(R.id.card_food_price) ;
            menuImg = (ImageView) itemView.findViewById(R.id.menuImg);
            cardViewContainer = itemView.findViewById(R.id.cardViewContainer);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}
