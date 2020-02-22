package com.e.relojledbluetooth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class SlidePageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;

    public SlidePageAdapter(FragmentManager fm , List<Fragment> fragmentList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        CharSequence titulo = "";

        switch (position) {
            case 0:
                titulo = "Ajustes";
                break;
            case 1:
                titulo = "Modo";
                break;
            case 2:
                titulo = "Alarma";
                break;
            case 3:
                titulo = "Anim.";
                break;
        }

        return titulo;
    }

}
