package com.hazukie.testakka.infoutils;

import android.content.Context;
import android.view.View;

import com.hazukie.testakka.webutils.Keystatics;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

public class Dialogsheet {

    private Dialogsheet instance;
    private Context context;
    public Dialogsheet(Context getContext){
        this.context=getContext;
    }


    public Dialogsheet getInstance(Context context) {
        if(instance==null){
            instance=new Dialogsheet(context);
        }
        return instance;
    }

    public void showBottom(int saveKey, String title, String[] items,boolean isNeedMark,boolean isAddCancelBtn,String appendTag){
        QMUIBottomSheet.BottomListSheetBuilder bottomListSheetBuilder=new QMUIBottomSheet.BottomListSheetBuilder(context);
        bottomListSheetBuilder
                .setTitle(title)
                .setAddCancelBtn(isAddCancelBtn)
                .setAllowDrag(true)
                .setNeedRightMark(isNeedMark)
                .setGravityCenter(true)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        controlClick(position,appendTag,items[position]);
                        dialog.dismiss();
                    }
                });

        for(int i=0;i<items.length;i++){
            bottomListSheetBuilder.addItem(items[i]);
        }
        bottomListSheetBuilder.setCheckedIndex(saveKey);
        bottomListSheetBuilder.build().show();

    }

    public void controlClick(int position,String tag,String selectedValue){

    }
}
