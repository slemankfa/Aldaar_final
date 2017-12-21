package com.example.slemankamel.aldaarshop.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.FB.FBMaker;
import com.example.slemankamel.aldaarshop.Model.ViewItems;
import com.example.slemankamel.aldaarshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SlemanKamel on 12/7/2017.
 */

public class ViewFavoriteItemsAdapter  extends RecyclerView.Adapter<ViewFavoriteItemsAdapter.ViewFavListItemHolder>{

    private List<ViewItems> items ;

    Context context ;

    FBMaker fbMaker = new FBMaker();

    FirebaseDatabase mDatabase  = fbMaker.getmDatabase() ;
    FirebaseAuth mAuth = fbMaker.getmAuth();



    public ViewFavoriteItemsAdapter(List<ViewItems> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @Override
    public ViewFavListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_product_item,parent,false);

        ViewFavListItemHolder  viewFavListItemHolder = new ViewFavListItemHolder(root);

        return  viewFavListItemHolder;
    }

    @Override
    public void onBindViewHolder(final ViewFavListItemHolder holder, final int position) {

        final DatabaseReference FavList   = mDatabase.getReference().child("Favorites");


        holder.mProductName.setText(items.get(position).getProductName());
        holder.mProductPrice.setText(items.get(position).getProductPrice());

        Picasso.with(context).load(items.get(position).getProductImage()).into(holder.mProductImage);


        holder.mFav.setImageResource(R.drawable.ic_fav_addes);



        // remove from Fav List
        holder.mFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Toast.makeText(context, items.get(position).getProductKey() , Toast.LENGTH_SHORT).show();
              FavList.child(mAuth.getCurrentUser().getUid()).child(items.get(position).getProductKey()).removeValue();


                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,items.size());

                notifyDataSetChanged();


            }
        });


//        FavList.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if(dataSnapshot.child(mAuth.getCurrentUser().getUid()).hasChild(productKey))
//                {
//                    mFav.setImageResource(R.drawable.ic_fav_addes);
//                }else
//                {
//                    mFav.setImageResource(R.drawable.ic_favourite);
//                }
//
//            }





        }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public  static class  ViewFavListItemHolder extends RecyclerView.ViewHolder {
        ImageView mProductImage ;
        TextView mProductName  , mProductPrice ;
        ImageButton mFav ;

        public ViewFavListItemHolder(View itemView) {
            super(itemView);

            this.mProductImage  = (ImageView) itemView.findViewById(R.id.customer_product_itemimage_imgview);
            this.mProductName = (TextView) itemView.findViewById(R.id.customer_product_itemname_txt);
            this.mProductPrice = (TextView) itemView.findViewById(R.id.customer_product_itemprice_txt);
            this.mFav = (ImageButton) itemView.findViewById(R.id.customer_product_fav_imgbtn);
        }
    }

}
