package com.muv.tracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragments {

    private DashboardFragment myDashboardFragment;

    public DashboardFragment getMyDashboardFragment() {

        return myDashboardFragment;
    }

    public void setMyDashboardFragment(DashboardFragment myDashboardFragment) {
        this.myDashboardFragment = myDashboardFragment;
    }

    public static class DashboardFragment extends Fragment{

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

}
