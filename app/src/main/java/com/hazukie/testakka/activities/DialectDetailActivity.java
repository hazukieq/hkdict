package com.hazukie.testakka.activities;

import com.hazukie.testakka.base.ActcomWeb;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class DialectDetailActivity extends ActcomWeb {

    public DialectDetailActivity(){

    }

    @Override
    public void geParams() {
        super.geParams();
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        hiddenST(true);
    }
}
