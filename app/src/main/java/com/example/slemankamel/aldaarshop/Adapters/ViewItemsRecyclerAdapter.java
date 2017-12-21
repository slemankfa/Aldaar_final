package com.example.slemankamel.aldaarshop.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.slemankamel.aldaarshop.Model.ViewItems;
import com.example.slemankamel.aldaarshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.id.list;

/**
 * Created by SlemanKamel on 11/30/2017.
 */

public class ViewItemsRecyclerAdapter  extends RecyclerView.Adapter<ViewItemsRecyclerAdapter.ViewItemsViewHolder>{



    private List<ViewItems>  items ;

    Context context ;


    public ViewItemsRecyclerAdapter(Context context, List<ViewItems> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View root  = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item , parent , false);


        ViewItemsViewHolder viewHolder  =  new ViewItemsViewHolder(root);

        return viewHolder ;
    }

    @Override
    public void onBindViewHolder(ViewItemsViewHolder holder, int position) {


        Picasso.with(context).load(items.get(position).getProductImage()).into(holder.mItemImage);

        holder.mItemPrice.setText(items.get(position).getProductPrice()+ "$");
        holder.mItemName.setText(items.get(position).getProductName() );

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public  static  class  ViewItemsViewHolder  extends RecyclerView.ViewHolder
    {


        private ImageView  mItemImage ;
        private TextView mItemPrice  , mItemName ;

        public ViewItemsViewHolder(View itemView) {
            super(itemView);


            this.mItemImage = (ImageView) itemView.findViewById(R.id.product_itemimage_imgview);
            this.mItemPrice = (TextView)  itemView.findViewById(R.id.product_itemprice_txt);
            this.mItemName = (TextView)  itemView.findViewById(R.id.product_itemname_txt);
        }
    }
}
