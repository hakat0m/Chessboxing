/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.os.Vibrator
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.util.SparseBooleanArray
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.ListView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Random
 */
package com.hendricks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.hendricks.R;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity
extends Activity {
    private boolean bool_clear_1;
    private boolean bool_clear_2;
    private boolean bool_clear_3;
    private boolean bool_clear_4;
    private boolean bool_clear_all;
    private boolean bool_clear_all_cnt;
    private Button btn_clear_all_cnt;
    private Button btn_clear_all_nm_cnt;
    private Button button_clear_1;
    private Button button_clear_2;
    private Button button_clear_3;
    private Button button_clear_4;
    private Button button_counter_1;
    private Button button_counter_2;
    private Button button_counter_3;
    private Button button_counter_4;
    private Button button_minus_1;
    private Button button_minus_2;
    private Button button_minus_3;
    private Button button_minus_4;
    private SharedPreferences cnt_name_1;
    private SharedPreferences cnt_name_2;
    private SharedPreferences cnt_name_3;
    private SharedPreferences cnt_name_4;
    private SharedPreferences cnt_value_1;
    private SharedPreferences cnt_value_2;
    private SharedPreferences cnt_value_3;
    private SharedPreferences cnt_value_4;
    private EditText edittext_name_1;
    private EditText edittext_name_2;
    private EditText edittext_name_3;
    private EditText edittext_name_4;
    private int int_counter_1 = 0;
    private int int_counter_2 = 0;
    private int int_counter_3 = 0;
    private int int_counter_4 = 0;
    private LinearLayout linear10;
    private LinearLayout linear7;
    private LinearLayout linear8;
    private LinearLayout linear9;
    private LinearLayout linear_clear_all_btn;
    private LinearLayout linear_counter_btm;
    private LinearLayout linear_counter_top;
    private LinearLayout linear_ctr_1;
    private LinearLayout linear_ctr_2;
    private LinearLayout linear_ctr_3;
    private LinearLayout linear_ctr_4;
    private LinearLayout linear_whole;
    private Vibrator vib_on_count;

    private void _block_clear_1() {
        this.int_counter_1 = 0;
        this.button_counter_1.setText((CharSequence)String.valueOf((int)this.int_counter_1));
        this.edittext_name_1.setText((CharSequence)"One");
        this.cnt_name_1.edit().putString("cnt_name_1", this.edittext_name_1.getText().toString()).commit();
        this.cnt_value_1.edit().putString("cnt_value_1", "0").commit();
    }

    private void _block_clear_2() {
        this.int_counter_2 = 0;
        this.button_counter_2.setText((CharSequence)String.valueOf((int)this.int_counter_2));
        this.edittext_name_2.setText((CharSequence)"Two");
        this.cnt_name_2.edit().putString("cnt_name_2", this.edittext_name_2.getText().toString()).commit();
        this.cnt_value_2.edit().putString("cnt_value_2", "0").commit();
    }

    private void _block_clear_3() {
        this.int_counter_3 = 0;
        this.button_counter_3.setText((CharSequence)String.valueOf((int)this.int_counter_3));
        this.edittext_name_3.setText((CharSequence)"Three");
        this.cnt_name_3.edit().putString("cnt_name_3", this.edittext_name_3.getText().toString()).commit();
        this.cnt_value_3.edit().putString("cnt_value_3", "0").commit();
    }

    private void _block_clear_4() {
        this.int_counter_4 = 0;
        this.button_counter_4.setText((CharSequence)String.valueOf((int)this.int_counter_4));
        this.edittext_name_4.setText((CharSequence)"Four");
        this.cnt_name_4.edit().putString("cnt_name_4", this.edittext_name_4.getText().toString()).commit();
        this.cnt_value_4.edit().putString("cnt_value_4", "0").commit();
    }

    private void _block_clear_only_cnt() {
        this.int_counter_1 = 0;
        this.button_counter_1.setText((CharSequence)String.valueOf((int)this.int_counter_1));
        this.cnt_value_1.edit().putString("cnt_value_1", "0").commit();
        this.int_counter_2 = 0;
        this.button_counter_2.setText((CharSequence)String.valueOf((int)this.int_counter_2));
        this.cnt_value_2.edit().putString("cnt_value_2", "0").commit();
        this.int_counter_3 = 0;
        this.button_counter_3.setText((CharSequence)String.valueOf((int)this.int_counter_3));
        this.cnt_value_3.edit().putString("cnt_value_3", "0").commit();
        this.int_counter_4 = 0;
        this.button_counter_4.setText((CharSequence)String.valueOf((int)this.int_counter_4));
        this.cnt_value_4.edit().putString("cnt_value_4", "0").commit();
    }

    private void _block_clear_tap() {
        this.bool_clear_1 = false;
        this.bool_clear_2 = false;
        this.bool_clear_3 = false;
        this.bool_clear_4 = false;
        this.bool_clear_all = false;
        this.bool_clear_all_cnt = false;
    }

    static /* synthetic */ void access$1(MainActivity mainActivity, int n) {
        mainActivity.int_counter_2 = n;
    }

    static /* synthetic */ void access$11(MainActivity mainActivity, int n) {
        mainActivity.int_counter_3 = n;
    }

    static /* synthetic */ void access$15(MainActivity mainActivity, int n) {
        mainActivity.int_counter_4 = n;
    }

    static /* synthetic */ void access$20(MainActivity mainActivity, boolean bl) {
        mainActivity.bool_clear_1 = bl;
    }

    static /* synthetic */ void access$21(MainActivity mainActivity, boolean bl) {
        mainActivity.bool_clear_2 = bl;
    }

    static /* synthetic */ void access$22(MainActivity mainActivity, boolean bl) {
        mainActivity.bool_clear_3 = bl;
    }

    static /* synthetic */ void access$23(MainActivity mainActivity, boolean bl) {
        mainActivity.bool_clear_4 = bl;
    }

    static /* synthetic */ void access$24(MainActivity mainActivity, boolean bl) {
        mainActivity.bool_clear_all = bl;
    }

    static /* synthetic */ void access$25(MainActivity mainActivity, boolean bl) {
        mainActivity.bool_clear_all_cnt = bl;
    }

    static /* synthetic */ void access$7(MainActivity mainActivity, int n) {
        mainActivity.int_counter_1 = n;
    }

    private int getRandom(int n, int n2) {
        return n + new Random().nextInt(1 + (n2 - n));
    }

    private void initialize() {
        this.linear_whole = (LinearLayout)this.findViewById(R.id.linear_whole);
        this.linear_counter_top = (LinearLayout)this.findViewById(R.id.linear_counter_top);
        this.linear_counter_btm = (LinearLayout)this.findViewById(R.id.linear_counter_btm);
        this.linear_clear_all_btn = (LinearLayout)this.findViewById(R.id.linear_clear_all_btn);
        this.linear_ctr_1 = (LinearLayout)this.findViewById(R.id.linear_ctr_1);
        this.linear_ctr_2 = (LinearLayout)this.findViewById(R.id.linear_ctr_2);
        this.linear7 = (LinearLayout)this.findViewById(R.id.linear7);
        this.button_counter_1 = (Button)this.findViewById(R.id.button_counter_1);
        this.button_minus_1 = (Button)this.findViewById(R.id.button_minus_1);
        this.edittext_name_1 = (EditText)this.findViewById(R.id.edittext_name_1);
        this.button_clear_1 = (Button)this.findViewById(R.id.button_clear_1);
        this.linear8 = (LinearLayout)this.findViewById(R.id.linear8);
        this.button_counter_2 = (Button)this.findViewById(R.id.button_counter_2);
        this.button_minus_2 = (Button)this.findViewById(R.id.button_minus_2);
        this.edittext_name_2 = (EditText)this.findViewById(R.id.edittext_name_2);
        this.button_clear_2 = (Button)this.findViewById(R.id.button_clear_2);
        this.linear_ctr_3 = (LinearLayout)this.findViewById(R.id.linear_ctr_3);
        this.linear_ctr_4 = (LinearLayout)this.findViewById(R.id.linear_ctr_4);
        this.linear9 = (LinearLayout)this.findViewById(R.id.linear9);
        this.button_counter_3 = (Button)this.findViewById(R.id.button_counter_3);
        this.button_minus_3 = (Button)this.findViewById(R.id.button_minus_3);
        this.edittext_name_3 = (EditText)this.findViewById(R.id.edittext_name_3);
        this.button_clear_3 = (Button)this.findViewById(R.id.button_clear_3);
        this.linear10 = (LinearLayout)this.findViewById(R.id.linear10);
        this.button_counter_4 = (Button)this.findViewById(R.id.button_counter_4);
        this.button_minus_4 = (Button)this.findViewById(R.id.button_minus_4);
        this.edittext_name_4 = (EditText)this.findViewById(R.id.edittext_name_4);
        this.button_clear_4 = (Button)this.findViewById(R.id.button_clear_4);
        this.btn_clear_all_nm_cnt = (Button)this.findViewById(R.id.btn_clear_all_nm_cnt);
        this.btn_clear_all_cnt = (Button)this.findViewById(R.id.btn_clear_all_cnt);
        this.vib_on_count = (Vibrator)this.getSystemService("vibrator");
        this.cnt_name_1 = this.getSharedPreferences("counter_settings.txt", 0);
        this.cnt_name_2 = this.getSharedPreferences("counter_settings.txt", 0);
        this.cnt_name_3 = this.getSharedPreferences("counter_settings.txt", 0);
        this.cnt_name_4 = this.getSharedPreferences("counter_settings.txt", 0);
        this.cnt_value_1 = this.getSharedPreferences("counter_settings.txt", 0);
        this.cnt_value_2 = this.getSharedPreferences("counter_settings.txt", 0);
        this.cnt_value_3 = this.getSharedPreferences("counter_settings.txt", 0);
        this.cnt_value_4 = this.getSharedPreferences("counter_settings.txt", 0);
        this.button_counter_2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity mainActivity = MainActivity.this;
                MainActivity.access$1(mainActivity, 1 + mainActivity.int_counter_2);
                MainActivity.this.button_counter_2.setText((CharSequence)String.valueOf((int)MainActivity.this.int_counter_2));
                MainActivity.this._block_clear_tap();
                MainActivity.this.vib_on_count.vibrate(30);
                MainActivity.this.cnt_value_2.edit().putString("cnt_value_2", String.valueOf((int)MainActivity.this.int_counter_2)).commit();
            }
        });
        this.button_counter_1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity mainActivity = MainActivity.this;
                MainActivity.access$7(mainActivity, 1 + mainActivity.int_counter_1);
                MainActivity.this.button_counter_1.setText((CharSequence)String.valueOf((int)MainActivity.this.int_counter_1));
                MainActivity.this._block_clear_tap();
                MainActivity.this.vib_on_count.vibrate(30);
                MainActivity.this.cnt_value_1.edit().putString("cnt_value_1", String.valueOf((int)MainActivity.this.int_counter_1)).commit();
            }
        });
        this.button_minus_1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.int_counter_1 > 0) {
                    MainActivity mainActivity = MainActivity.this;
                    MainActivity.access$7(mainActivity, -1 + mainActivity.int_counter_1);
                    MainActivity.this.button_counter_1.setText((CharSequence)String.valueOf((int)MainActivity.this.int_counter_1));
                }
                MainActivity.this._block_clear_tap();
                MainActivity.this.vib_on_count.vibrate(15);
                MainActivity.this.cnt_value_1.edit().putString("cnt_value_1", String.valueOf((int)MainActivity.this.int_counter_1)).commit();
            }
        });
        this.button_minus_2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.int_counter_2 > 0) {
                    MainActivity mainActivity = MainActivity.this;
                    MainActivity.access$1(mainActivity, -1 + mainActivity.int_counter_2);
                    MainActivity.this.button_counter_2.setText((CharSequence)String.valueOf((int)MainActivity.this.int_counter_2));
                }
                MainActivity.this._block_clear_tap();
                MainActivity.this.vib_on_count.vibrate(15);
                MainActivity.this.cnt_value_2.edit().putString("cnt_value_2", String.valueOf((int)MainActivity.this.int_counter_2)).commit();
            }
        });
        this.button_minus_3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.int_counter_3 > 0) {
                    MainActivity mainActivity = MainActivity.this;
                    MainActivity.access$11(mainActivity, -1 + mainActivity.int_counter_3);
                    MainActivity.this.button_counter_3.setText((CharSequence)String.valueOf((int)MainActivity.this.int_counter_3));
                }
                MainActivity.this._block_clear_tap();
                MainActivity.this.vib_on_count.vibrate(15);
                MainActivity.this.cnt_value_3.edit().putString("cnt_value_3", String.valueOf((int)MainActivity.this.int_counter_3)).commit();
            }
        });
        this.button_counter_3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity mainActivity = MainActivity.this;
                MainActivity.access$11(mainActivity, 1 + mainActivity.int_counter_3);
                MainActivity.this.button_counter_3.setText((CharSequence)String.valueOf((int)MainActivity.this.int_counter_3));
                MainActivity.this._block_clear_tap();
                MainActivity.this.vib_on_count.vibrate(30);
                MainActivity.this.cnt_value_3.edit().putString("cnt_value_3", String.valueOf((int)MainActivity.this.int_counter_3)).commit();
            }
        });
        this.button_counter_4.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity mainActivity = MainActivity.this;
                MainActivity.access$15(mainActivity, 1 + mainActivity.int_counter_4);
                MainActivity.this.button_counter_4.setText((CharSequence)String.valueOf((int)MainActivity.this.int_counter_4));
                MainActivity.this._block_clear_tap();
                MainActivity.this.vib_on_count.vibrate(30);
                MainActivity.this.cnt_value_4.edit().putString("cnt_value_4", String.valueOf((int)MainActivity.this.int_counter_4)).commit();
            }
        });
        this.button_minus_4.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.int_counter_4 > 0) {
                    MainActivity mainActivity = MainActivity.this;
                    MainActivity.access$15(mainActivity, -1 + mainActivity.int_counter_4);
                    MainActivity.this.button_counter_4.setText((CharSequence)String.valueOf((int)MainActivity.this.int_counter_4));
                }
                MainActivity.this._block_clear_tap();
                MainActivity.this.vib_on_count.vibrate(15);
                MainActivity.this.cnt_value_4.edit().putString("cnt_value_4", String.valueOf((int)MainActivity.this.int_counter_4)).commit();
            }
        });
        this.button_clear_1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.bool_clear_1) {
                    MainActivity.this._block_clear_1();
                    MainActivity.this._block_clear_tap();
                    return;
                }
                MainActivity.access$20(MainActivity.this, true);
                MainActivity.access$21(MainActivity.this, false);
                MainActivity.access$22(MainActivity.this, false);
                MainActivity.access$23(MainActivity.this, false);
                MainActivity.access$24(MainActivity.this, false);
                MainActivity.access$25(MainActivity.this, false);
                MainActivity.this.showMessage("Press again to clear the name and counter");
            }
        });
        this.button_clear_2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.bool_clear_2) {
                    MainActivity.this._block_clear_2();
                    MainActivity.this._block_clear_tap();
                    return;
                }
                MainActivity.access$20(MainActivity.this, false);
                MainActivity.access$21(MainActivity.this, true);
                MainActivity.access$22(MainActivity.this, false);
                MainActivity.access$23(MainActivity.this, false);
                MainActivity.access$24(MainActivity.this, false);
                MainActivity.access$25(MainActivity.this, false);
                MainActivity.this.showMessage("Press again to clear the name and counter");
            }
        });
        this.button_clear_3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.bool_clear_3) {
                    MainActivity.this._block_clear_3();
                    MainActivity.this._block_clear_tap();
                    return;
                }
                MainActivity.access$20(MainActivity.this, false);
                MainActivity.access$21(MainActivity.this, false);
                MainActivity.access$22(MainActivity.this, true);
                MainActivity.access$23(MainActivity.this, false);
                MainActivity.access$24(MainActivity.this, false);
                MainActivity.access$25(MainActivity.this, false);
                MainActivity.this.showMessage("Press again to clear the name and counter");
            }
        });
        this.button_clear_4.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.bool_clear_4) {
                    MainActivity.this._block_clear_4();
                    MainActivity.this._block_clear_tap();
                    return;
                }
                MainActivity.access$20(MainActivity.this, false);
                MainActivity.access$21(MainActivity.this, false);
                MainActivity.access$22(MainActivity.this, false);
                MainActivity.access$23(MainActivity.this, true);
                MainActivity.access$24(MainActivity.this, false);
                MainActivity.access$25(MainActivity.this, false);
                MainActivity.this.showMessage("Press again to clear the name and counter");
            }
        });
        this.edittext_name_1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity.this._block_clear_tap();
            }
        });
        this.edittext_name_2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity.this._block_clear_tap();
            }
        });
        this.edittext_name_3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity.this._block_clear_tap();
            }
        });
        this.edittext_name_4.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MainActivity.this._block_clear_tap();
            }
        });
        this.btn_clear_all_nm_cnt.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.bool_clear_all) {
                    MainActivity.this._block_clear_1();
                    MainActivity.this._block_clear_2();
                    MainActivity.this._block_clear_3();
                    MainActivity.this._block_clear_4();
                    MainActivity.this._block_clear_tap();
                    return;
                }
                MainActivity.access$20(MainActivity.this, false);
                MainActivity.access$21(MainActivity.this, false);
                MainActivity.access$22(MainActivity.this, false);
                MainActivity.access$23(MainActivity.this, false);
                MainActivity.access$24(MainActivity.this, true);
                MainActivity.access$25(MainActivity.this, false);
                MainActivity.this.showMessage("Press again to clear ALL the names and counters");
            }
        });
        this.edittext_name_1.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                MainActivity.this.cnt_name_1.edit().putString("cnt_name_1", charSequence.toString()).commit();
            }
        });
        this.edittext_name_2.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                MainActivity.this.cnt_name_2.edit().putString("cnt_name_2", charSequence.toString()).commit();
            }
        });
        this.edittext_name_3.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                MainActivity.this.cnt_name_3.edit().putString("cnt_name_3", charSequence.toString()).commit();
            }
        });
        this.edittext_name_4.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                MainActivity.this.cnt_name_4.edit().putString("cnt_name_4", charSequence.toString()).commit();
            }
        });
        this.btn_clear_all_cnt.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (MainActivity.this.bool_clear_all_cnt) {
                    MainActivity.this._block_clear_only_cnt();
                    return;
                }
                MainActivity.access$20(MainActivity.this, false);
                MainActivity.access$21(MainActivity.this, false);
                MainActivity.access$22(MainActivity.this, false);
                MainActivity.access$23(MainActivity.this, false);
                MainActivity.access$24(MainActivity.this, false);
                MainActivity.access$25(MainActivity.this, true);
                MainActivity.this.showMessage("Press again to clear ALL the counters");
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initializeLogic() {
        if (this.cnt_value_1.getString("cnt_value_1", "").length() == 0) {
            this.showMessage("Initializing ... It should only take a moment");
            this._block_clear_1();
            this._block_clear_2();
            this._block_clear_3();
            this._block_clear_4();
        }
        this._block_clear_tap();
        this.edittext_name_1.setText((CharSequence)this.cnt_name_1.getString("cnt_name_1", ""));
        this.button_counter_1.setText((CharSequence)this.cnt_value_1.getString("cnt_value_1", ""));
        this.edittext_name_2.setText((CharSequence)this.cnt_name_2.getString("cnt_name_2", ""));
        this.button_counter_2.setText((CharSequence)this.cnt_value_2.getString("cnt_value_2", ""));
        this.edittext_name_3.setText((CharSequence)this.cnt_name_3.getString("cnt_name_3", ""));
        this.button_counter_3.setText((CharSequence)this.cnt_value_3.getString("cnt_value_3", ""));
        this.edittext_name_4.setText((CharSequence)this.cnt_name_4.getString("cnt_name_4", ""));
        this.button_counter_4.setText((CharSequence)this.cnt_value_4.getString("cnt_value_4", ""));
        this.int_counter_1 = this.cnt_value_1.getString("cnt_value_1", "").length() == 0 ? 0 : Integer.valueOf((String)this.cnt_value_1.getString("cnt_value_1", ""));
        this.int_counter_2 = this.cnt_value_2.getString("cnt_value_2", "").length() == 0 ? 0 : Integer.valueOf((String)this.cnt_value_2.getString("cnt_value_2", ""));
        this.int_counter_3 = this.cnt_value_3.getString("cnt_value_3", "").length() == 0 ? 0 : Integer.valueOf((String)this.cnt_value_3.getString("cnt_value_3", ""));
        if (this.cnt_value_4.getString("cnt_value_4", "").length() == 0) {
            this.int_counter_4 = 0;
            return;
        }
        this.int_counter_4 = Integer.valueOf((String)this.cnt_value_4.getString("cnt_value_4", ""));
    }

    private void showMessage(String string) {
        Toast.makeText((Context)this.getApplicationContext(), (CharSequence)string, (int)0).show();
    }

    public ArrayList<Integer> getCheckedItemPositionsToArray(ListView listView) {
        ArrayList arrayList = new ArrayList();
        SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
        int n = 0;
        while (n < sparseBooleanArray.size()) {
            if (sparseBooleanArray.valueAt(n)) {
                arrayList.add((Object)sparseBooleanArray.keyAt(n));
            }
            ++n;
        }
        return arrayList;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.main);
        super.initialize();
        super.initializeLogic();
    }

}

