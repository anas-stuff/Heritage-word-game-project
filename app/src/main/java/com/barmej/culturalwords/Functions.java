package com.barmej.culturalwords;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

public class Functions {
    public static void getAndSetLang(Context context){
        String appLang = context.getSharedPreferences(Constants.APP_PREF, MODE_PRIVATE)
                .getString(Constants.APP_LANG_PREF, "");
        if(appLang != null && !appLang.equals(""))
            LocaleHelper.setLocale(context, appLang);
    }
}
