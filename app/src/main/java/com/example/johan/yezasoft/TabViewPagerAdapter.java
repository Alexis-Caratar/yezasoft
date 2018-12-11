package com.example.johan.yezasoft;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class TabViewPagerAdapter  extends FragmentStatePagerAdapter {
    TabLayout tabLayout;
    public TabViewPagerAdapter(FragmentManager supportFragmentManager) {
    super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment=null;
        switch (position){
            case 0:fragment=new MesasDisponibles(); break;
            case 1:fragment=new MesasOcupadas(); break;
         default: fragment=new MesasDisponibles(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
