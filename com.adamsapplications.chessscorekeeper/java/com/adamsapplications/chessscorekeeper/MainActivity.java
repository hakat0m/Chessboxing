/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.view.View
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.String
 */
package com.adamsapplications.chessscorekeeper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity
extends Activity {
    int bscore = 0;
    int wscore = 0;

    public void bbm(View view) {
        this.bscore = -3 + this.bscore;
        this.save();
    }

    public void bbp(View view) {
        this.bscore = 3 + this.bscore;
        this.save();
    }

    public void bkm(View view) {
        this.bscore = -3 + this.bscore;
        this.save();
    }

    public void bkp(View view) {
        this.bscore = 3 + this.bscore;
        this.save();
    }

    public void bpm(View view) {
        this.bscore = -1 + this.bscore;
        this.save();
    }

    public void bpp(View view) {
        this.bscore = 1 + this.bscore;
        this.save();
    }

    public void bqm(View view) {
        this.bscore = -9 + this.bscore;
        this.save();
    }

    public void bqp(View view) {
        this.bscore = 9 + this.bscore;
        this.save();
    }

    public void brm(View view) {
        this.bscore = -5 + this.bscore;
        this.save();
    }

    public void brp(View view) {
        this.bscore = 5 + this.bscore;
        this.save();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130903040);
        SharedPreferences sharedPreferences = this.getSharedPreferences("PREFS_NAME", 0);
        this.wscore = sharedPreferences.getInt("wkey", 0);
        this.bscore = sharedPreferences.getInt("bkey", 0);
        ((TextView)this.findViewById(2131230720)).setText((CharSequence)("Score (W/B) = " + this.wscore + "/" + this.bscore));
    }

    public void rst(View view) {
        this.wscore = 0;
        this.bscore = 0;
        this.save();
    }

    public void save() {
        SharedPreferences.Editor editor = this.getSharedPreferences("PREFS_NAME", 0).edit();
        editor.putInt("wkey", this.wscore);
        editor.putInt("bkey", this.bscore);
        editor.commit();
        ((TextView)this.findViewById(2131230720)).setText((CharSequence)("Score (W/B) = " + this.wscore + "/" + this.bscore));
    }

    public void wbm(View view) {
        this.wscore = -3 + this.wscore;
        this.save();
    }

    public void wbp(View view) {
        this.wscore = 3 + this.wscore;
        this.save();
    }

    public void wkm(View view) {
        this.wscore = -3 + this.wscore;
        this.save();
    }

    public void wkp(View view) {
        this.wscore = 3 + this.wscore;
        this.save();
    }

    public void wpm(View view) {
        this.wscore = -1 + this.wscore;
        this.save();
    }

    public void wpp(View view) {
        this.wscore = 1 + this.wscore;
        this.save();
    }

    public void wqm(View view) {
        this.wscore = -9 + this.wscore;
        this.save();
    }

    public void wqp(View view) {
        this.wscore = 9 + this.wscore;
        this.save();
    }

    public void wrm(View view) {
        this.wscore = -5 + this.wscore;
        this.save();
    }

    public void wrp(View view) {
        this.wscore = 5 + this.wscore;
        this.save();
    }
}

