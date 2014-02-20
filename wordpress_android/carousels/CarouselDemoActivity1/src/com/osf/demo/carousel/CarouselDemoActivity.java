package com.osf.demo.carousel;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarouselDemoActivity extends Activity {

    /**
     * Define the number of items visible when the carousel is first shown.
     */
    private static final float INITIAL_ITEMS_COUNT = 3.5F;

    /**
     * Carousel container layout
     */
    private LinearLayout mCarouselContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set title
        setTitle("");
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Set content layout
        setContentView(R.layout.activity_carousel_demo);

        // Get reference to carousel container
        mCarouselContainer = (LinearLayout) findViewById(R.id.carousel);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Compute the width of a carousel item based on the screen width and number of initial items.
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int imageWidth = (int) (displayMetrics.widthPixels / INITIAL_ITEMS_COUNT);

        // Get the array of puppy resources
        final TypedArray imagesArray = getResources().obtainTypedArray(R.array.images_array);
        final TypedArray namesArray = getResources().obtainTypedArray(R.array.names_array);

        // Populate the carousel with items
        for (int i = 0 ; i < imagesArray.length() ; ++i) {
            // Create new ImageView
        	View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, null);

        	ImageView imageItem = (ImageView) view.findViewById(R.id.item_image);
            TextView text = (TextView) view.findViewById(R.id.item_text);

            // Set the shadow background
//            imageItem.setBackgroundResource(R.drawable.shadow);

            // Set the image view resource
            imageItem.setImageResource(imagesArray.getResourceId(i, -1));

            // Set the size of the image view to the previously computed value
            imageItem.setLayoutParams(new LinearLayout.LayoutParams(imageWidth, imageWidth));
            
            text.setText(namesArray.getString(i));

            /// Add image view to the carousel container
            mCarouselContainer.addView(view);
        }
    }

}
