package com.hazukie.testakka.adapters;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.qmuiteam.qmui.widget.QMUIPagerAdapter;

public abstract class PagerAdapter extends QMUIPagerAdapter {

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurrentTransaction;
    private Fragment mCurrentPrimaryItem = null;

    public PagerAdapter(@NonNull FragmentManager fm) {
        mFragmentManager = fm;
    }

    public abstract Fragment createFragment(int position);

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((Fragment) object).getView();
    }

    @SuppressLint("CommitTransaction")
    @Override
    @NonNull
    protected Object hydrate(@NonNull ViewGroup container, int position) {
        String name = makeFragmentName(container.getId(), position);
       // Log.i( "qpagetad_hydrate: ",name);
        if (mCurrentTransaction == null) {
            mCurrentTransaction = mFragmentManager.beginTransaction();
        }
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            return fragment;
        }
        return createFragment(position);
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void populate(@NonNull ViewGroup container, @NonNull Object item, int position) {
        String name = makeFragmentName(container.getId(), position);
        if (mCurrentTransaction == null) {
            mCurrentTransaction = mFragmentManager.beginTransaction();
        }
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            mCurrentTransaction.attach(fragment);
            if (fragment.getView() != null && fragment.getView().getWidth() == 0) {
                fragment.getView().requestLayout();
            }
        } else {
            fragment = (Fragment) item;
            mCurrentTransaction.add(container.getId(), fragment, name);
        }
        if (fragment != mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            mCurrentTransaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
        }
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void destroy(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object;
        if (mCurrentTransaction == null) {
            mCurrentTransaction = mFragmentManager.beginTransaction();
        }
        mCurrentTransaction.detach(fragment);
        if (fragment == mCurrentPrimaryItem) {
            mCurrentPrimaryItem = null;
        }
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        if (container.getId() == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (mCurrentTransaction != null) {
            mCurrentTransaction.commitNowAllowingStateLoss();
            mCurrentTransaction = null;
        }
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                if (mCurrentTransaction == null) {
                    mCurrentTransaction = mFragmentManager.beginTransaction();
                }
                mCurrentTransaction.setMaxLifecycle(mCurrentPrimaryItem, Lifecycle.State.STARTED);
            }
            fragment.setMenuVisibility(true);
            if (mCurrentTransaction == null) {
                mCurrentTransaction = mFragmentManager.beginTransaction();
            }
            mCurrentTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
            mCurrentPrimaryItem = fragment;
        }
    }

    private String makeFragmentName(int viewId, long id) {
        return "QMUIFragmentPagerAdapter:" + viewId + ":" + id;
    }
}