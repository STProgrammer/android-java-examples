package com.wfamedia.viewpager2utstyr0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private static final int NUM_PAGES = 5;
    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Oppretter (/finner) ViewModel-objektet:
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        // Legger data i ViewModel-objektet:
        myViewModel.setLabUtstyr(new UtstyrsListe().getUtstyr());
        // Abonnerer på endringer:
        myViewModel.getLabUtstyr().observe(this, labUtstyr -> {
            Toast.makeText(this, "Utstyr endret ...", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return UtstyrSlidePageFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}