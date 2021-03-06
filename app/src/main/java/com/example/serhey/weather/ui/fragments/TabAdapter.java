package com.example.serhey.weather.ui.fragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.serhey.weather.R;
import com.example.serhey.weather.core.App;

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
            case 1:return new TomorrowForecastFragment();
            case 2:return new WeekForecastFragment();

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
            case 0:
                return App.getContext().getString(R.string.today);
            case 1:
                return App.getContext().getString(R.string.tomorrow);
            case 2:
                return App.getContext().getString(R.string.week);
        }
        return null;
    }
}
