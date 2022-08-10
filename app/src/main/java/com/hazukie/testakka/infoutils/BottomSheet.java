package com.hazukie.testakka.infoutils;

import android.content.Context;
import android.view.View;

import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

public class BottomSheet {
        //菜单控制类
        private Context context;
        private Clicks clicks;
        public BottomSheet(Context context,Clicks clicks){
            this.context=context;
            this.clicks=clicks;
        }


        //横向排列
        public BottomSheet setHorizonalList(String[] menus,int[] imgRes,int[] tags) {
            QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(context);
            for(int i=0;i<menus.length;i++){
                builder.addItem(imgRes[i], menus[i], tags[i], QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            }

            builder.setAddCancelBtn(true)
                    .setSkinManager(QMUISkinManager.defaultInstance(context))
                    .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                        @Override
                        public void onClick(QMUIBottomSheet dialog, View itemView) {
                            dialog.dismiss();
                            int tag = (int) itemView.getTag();
                            clicks.controlHorizontalList(tag);
                        }
                    }).build().show();

            return this;
        }

        //竖向排列
        public BottomSheet setVerticalList(String[] menus) {
            QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(context);
            for(int i=0;i<menus.length;i++){
                builder.addItem(menus[i]);
            }
            builder.setAddCancelBtn(true)
                    .setGravityCenter(true)
                    .setAllowDrag(true)
                    .setFitNav(true)
                    .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                        @Override
                        public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                            clicks.controlVerticalList(position);
                            dialog.dismiss();
                        }
                    }).build().show();
            return this;
        }

    public interface Clicks{
            void controlVerticalList(int postion);
            void controlHorizontalList(int tag);
    }
}



