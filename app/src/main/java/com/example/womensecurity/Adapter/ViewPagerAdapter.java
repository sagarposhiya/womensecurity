package com.example.womensecurity.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.womensecurity.fragments.MediaFragment;
import com.example.womensecurity.fragments.TipsAndTricksFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
           TipsAndTricksFragment tipsAndTricksFragment = new TipsAndTricksFragment();
            return tipsAndTricksFragment;
        }
        else if (position == 1)
        {
           MediaFragment mediaFragment = new MediaFragment();
           return mediaFragment;
        }
        return new MediaFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Tips And Tricks";
        }
        else if (position == 1)
        {
            title = "Solutions";
        }
//        else if (position == 2)
//        {
//            title = "Tab-3";
//        }
        return title;
    }
}
