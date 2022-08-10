package com.hazukie.testakka.activities;

import com.hazukie.testakka.base.ActcomWeb;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class DialectDetailActivity extends ActcomWeb {

    @Override
    public void customStatus(QMUITopBarLayout topBarLayout) {
        setLightOrDarkStatusBar(1);
        hiddenStatusBar(true);
    }
}
