package com.carouseldemo.main;

import com.carouseldemo.controls.Carousel;
import com.carouseldemo.controls.CarouselAdapter;
import com.carouseldemo.controls.CarouselAdapter.OnItemClickListener;
import com.carouseldemo.controls.CarouselAdapter.OnItemSelectedListener;
import com.carouseldemo.controls.CarouselItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        Carousel carousel = (Carousel)findViewById(R.id.carousel);
        carousel.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(CarouselAdapter<?> parent, View view,
					int position, long id) {	
				
				Toast.makeText(MainActivity.this, 
						String.format("%s has been clicked", 
						((CarouselItem)parent.getChildAt(position)).getName()), 
						Toast.LENGTH_SHORT).show();				
			}
        	
        });

        carousel.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(CarouselAdapter<?> parent, View view,
					int position, long id) {
				
			}

			public void onNothingSelected(CarouselAdapter<?> parent) {
			}
        	
        }
        );
        
    }
    
}

