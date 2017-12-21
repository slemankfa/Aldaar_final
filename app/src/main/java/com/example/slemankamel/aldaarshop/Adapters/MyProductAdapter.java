package com.example.slemankamel.aldaarshop.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slemankamel.aldaarshop.Model.MyProductItem;
import com.example.slemankamel.aldaarshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SlemanKamel on 10/24/2017.
 */

public class MyProductAdapter   extends  RecyclerView.Adapter<MyProductAdapter.MyProductHolder> {


    private List<MyProductItem> item ;

    Context context ;
    public MyProductAdapter(List<MyProductItem> item  , Context context  )
    {
        this.item = item ;
        this.context  = context ;
    }

    @Override
    public MyProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View root  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_products_item , parent , false);

        MyProductHolder productHolder = new MyProductHolder(root);


        return productHolder;
    }

    @Override
    public void onBindViewHolder(MyProductHolder holder, int position) {


        holder.productTypeTitle.setText(item.get(position).getTitle());

//        Picasso.with(context).load(image).into(post_Image);
         Picasso.with(context).load(item.get(position).getImage()).into(holder.ImagePath);


    }


    @Override
    public int getItemCount() {
        return item.size() ;
    }


    // view holder

    public  static  class MyProductHolder extends  RecyclerView.ViewHolder {



         public ImageView ImagePath ;
        public TextView  productTypeTitle ;

        View root  ;

        public MyProductHolder(View itemView) {
            super(itemView);
            root =  itemView;

            this.ImagePath = (ImageView) root.findViewById(R.id.my_product_image_item);
            this.productTypeTitle = (TextView) root.findViewById(R.id.my_product_name_item);
        }
    }


}



