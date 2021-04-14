package com.gbcish.models;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gbcish.Fragments.ContactAbtHelpFragment;
import com.gbcish.Fragments.CurrencyHelpFragment;
import com.gbcish.Fragments.ExploreHelpFragment;
import com.gbcish.Fragments.HelpDashboardFragment;
import com.gbcish.Fragments.HelpFragment;
import com.gbcish.Fragments.PostHelpFragment;

public class HelpFragmentCollectionAdapter extends FragmentStatePagerAdapter {
    public HelpFragmentCollectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                return new HelpDashboardFragment();

            case 1:
                return new ExploreHelpFragment();

            case 2:
                return new PostHelpFragment();

            case 3:
                return new CurrencyHelpFragment();

            case 4:
                return new ContactAbtHelpFragment();
        }
        return null;
        }




    @Override
    public int getCount() {
        return 5 ;
    }
}
