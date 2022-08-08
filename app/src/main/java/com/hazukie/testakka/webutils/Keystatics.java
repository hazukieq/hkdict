package com.hazukie.testakka.webutils;

public class Keystatics {
    public static String[] keys=new String[]{
            "searchMode","isPagerScroll","night_mode"
    };

    public static String[] directions=new String[]{"横向","纵向"};

    public static String[] typographys=new String[]{
            "大","标准","小"
    };

    public static String[] bgcolors=new String[]{
            "白色","纸黄色","灰色",
    };

    public static String[] hk_visibles=new String[]{
            //"简明客语拼音","简明客拼+IPA"
            "通用客语拼音","国际音标","通用客拼+IPA"
    };

    public static String[] cmn_visibles=new String[]{
            "汉语拼音","国际音标"
    };

    public static String[] tones=new String[]{
            "数字型","调值型"
    };

    public static String[] special_tones=new String[]{
            "数字型","调值型","标调型"
    };

    public static String[] item_listkeys=new String[]{
            "drt","ft","lt","lh","bg"
    };

    public static String[] hkcmn=new String[]{
            "_visibles","tone"
    };
    public static String[] getStatics(String key){
        switch (key){
            case "typographys":
                return typographys;
            case "directions":
                return directions;
            case "bgcolors":
                return  bgcolors;
            case "hk_visibles":
                return hk_visibles;
            case "cmn_visibles":
                return cmn_visibles;
            case "tones":
                return tones;
            case "special_tones":
                return special_tones;
            default:
                return  tones;
        }
    }


}
