package com.example.namtn.punchclock.ModelView;

import android.support.v4.app.Fragment;

import java.io.Serializable;

public class FragmentInformation implements Serializable {

    private int idFragment;
    private String titleFragment;
    private Fragment layoutFragment;

    public FragmentInformation(int idFragment, String titleFragment, Fragment layoutFragment) {
        this.idFragment = idFragment;
        this.titleFragment = titleFragment;
        this.layoutFragment = layoutFragment;
    }

    public int getIdFragment() {
        return idFragment;
    }

    public void setIdFragment(int idFragment) {
        this.idFragment = idFragment;
    }

    public String getTitleFragment() {
        return titleFragment;
    }

    public void setTitleFragment(String titleFragment) {
        this.titleFragment = titleFragment;
    }

    public Fragment getLayoutFragment() {
        return layoutFragment;
    }

    public void setLayoutFragment(Fragment layoutFragment) {
        this.layoutFragment = layoutFragment;
    }
}
