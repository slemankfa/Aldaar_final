package com.example.slemankamel.aldaarshop.ViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.slemankamel.aldaarshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SlemanKamel on 10/8/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {


    Context mContext ;
    LayoutInflater inflater ;

    List<String> imagePath ;

    View root ;

    public ViewPagerAdapter(Context mContext, List<String> imagePath) {
        this.mContext = mContext;
        this.imagePath = imagePath;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return this.imagePath.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return  view  == ( (RelativeLayout)object);


    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        root = inflater.inflate(R.layout.header_product_item , container , false);


        ImageView productImage = (ImageView) root.findViewById(R.id.img_new_product_header) ;



        Picasso.with(mContext).load(imagePath.get(position)).into(productImage);



        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "postion " + imagePath.get(position).toString() , Toast.LENGTH_SHORT).show();

            }
        });
        container.addView(root);
        return root;



    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout) object);

    }

}
