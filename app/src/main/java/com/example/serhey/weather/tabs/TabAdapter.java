package com.example.serhey.weather.tabs;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Serhey on 04.09.2016.
 */
public class TabAdapter extends FragmentPagerAdapter {




    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:return new TodayForecastFragment();
            case 1:return new TomorrowForecastAdapter();
            case 2:return new WeekForecastAdapter();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:return "Today" ;
            case 1:return "Tomorrow";
            case 2:return "Week";
        }
        return null;
    }

}
