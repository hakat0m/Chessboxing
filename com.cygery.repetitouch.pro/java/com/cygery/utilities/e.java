/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Bundle
 *  android.support.v4.app.k
 *  android.support.v4.app.l
 *  android.text.Editable
 *  android.util.Pair
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 *  android.widget.Toast
 *  java.io.File
 *  java.io.FileFilter
 *  java.io.IOException
 *  java.lang.AssertionError
 *  java.lang.CharSequence
 *  java.lang.ClassCastException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.Comparator
 *  java.util.HashMap
 *  java.util.List
 */
package com.cygery.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.k;
import android.support.v4.app.l;
import android.text.Editable;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cygery.utilities.h;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class e
extends k {
    static final /* synthetic */ boolean aa;
    private static final String ab;
    private Context ac;
    private b ad;
    private File ae;
    private String af;
    private ArrayList<File> ag;
    private a ah;
    private ListView ai;
    private LayoutInflater aj;
    private EditText ak;
    private TextView al;
    private boolean am;
    private boolean an;
    private int ao;
    private HashMap<String, Pair<Integer, Integer>> ap;
    private AdapterView.OnItemClickListener aq;
    private FileFilter ar;
    private FileFilter as;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !e.class.desiredAssertionStatus();
        aa = bl;
        ab = e.class.getName();
    }

    public e() {
        this.aq = new AdapterView.OnItemClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
                File file = (File)e.this.ag.get(n2);
                File file2 = new File(e.this.ae.getAbsolutePath() + File.separator + file.getName());
                if (!file2.canRead()) {
                    e.this.b(file2);
                    return;
                }
                if (file.getPath().equals((Object)"..")) {
                    if (e.this.ae.getParent() != null) {
                        e.this.d(e.this.ae);
                        e.this.ae = e.this.ae.getParentFile();
                        e.this.ak.setText(null);
                        e.this.af = null;
                        e.this.V();
                        e.this.W();
                    }
                    e.this.ah.notifyDataSetChanged();
                    e.this.e(e.this.ae);
                    return;
                }
                if (file2.isDirectory()) {
                    e.this.d(e.this.ae);
                    e.this.ae = file2;
                    e.this.ak.setText(null);
                    e.this.af = null;
                    e.this.V();
                    e.this.W();
                    e.this.ah.notifyDataSetChanged();
                    e.this.e(e.this.ae);
                    return;
                }
                if (!file2.isFile()) return;
                {
                    if (e.this.am && !file2.getPath().equals((Object)e.this.af)) {
                        e.this.ak.setText((CharSequence)file2.getName());
                        e.this.ak.setSelection(file2.getName().length());
                        e.this.af = file2.getPath();
                        return;
                    }
                }
                e.this.ad.a(file2, e.this.ao);
                e.this.a().dismiss();
            }
        };
        this.ar = new FileFilter(){

            public boolean accept(File file) {
                return file.isFile();
            }
        };
        this.as = new FileFilter(){

            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    private Dialog U() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.ac);
        builder.setTitle(h.d.label_select_file);
        RelativeLayout relativeLayout = (RelativeLayout)this.aj.inflate(h.c.filepicker, null);
        if (!aa && relativeLayout == null) {
            throw new AssertionError();
        }
        this.ai = (ListView)relativeLayout.findViewById(h.b.listView);
        this.ai.setAdapter((ListAdapter)this.ah);
        this.ai.setOnItemClickListener(this.aq);
        this.ak = (EditText)relativeLayout.findViewById(h.b.editTextNewFileName);
        this.al = (TextView)relativeLayout.findViewById(h.b.textViewCurDir);
        if (!this.am) {
            relativeLayout.findViewById(h.b.linearLayoutNewFile).setVisibility(8);
        }
        final EditText editText = this.ak;
        final Button button = (Button)relativeLayout.findViewById(h.b.buttonSaveNewFile);
        Button button2 = (Button)relativeLayout.findViewById(h.b.buttonNewDir);
        editText.setSingleLine();
        editText.setInputType(177);
        editText.setImeOptions(6);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            public boolean onEditorAction(TextView textView, int n2, KeyEvent keyEvent) {
                if (n2 == 6) {
                    button.performClick();
                    return true;
                }
                return false;
            }
        });
        button.setOnClickListener(new View.OnClickListener(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public void onClick(View view) {
                String string;
                File file;
                if (editText.getText() == null || (string = editText.getText().toString()).equals((Object)"")) return;
                File file2 = e.this.ae;
                if (!e.this.ae.isDirectory()) {
                    file2 = e.this.ae.getParentFile();
                }
                if ((file = new File(file2.getAbsolutePath() + File.separator + string)).exists()) {
                    if (file.canRead()) {
                        if (!e.this.am || file.canWrite()) {
                            e.this.ad.a(file, e.this.ao);
                            Dialog dialog = e.this.a();
                            if (dialog == null) return;
                            dialog.dismiss();
                            return;
                        }
                        e.this.a(file);
                        return;
                    }
                    e.this.b(file);
                    return;
                }
                e.this.ad.a(file, e.this.ao);
                e.this.a().dismiss();
            }
        });
        int n2 = this.an ? 0 : 8;
        button2.setVisibility(n2);
        button2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(e.this.ac);
                final EditText editText = new EditText(e.this.ac);
                editText.setHint(h.d.hint_new_directory);
                builder.setTitle((CharSequence)e.this.ac.getString(h.d.title_new_directory));
                builder.setView((View)editText);
                builder.setNegativeButton(17039360, null);
                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n2) {
                        if (editText.getText() != null) {
                            e.this.b(editText.getText().toString());
                        }
                    }
                });
                builder.show();
            }

        });
        builder.setView((View)relativeLayout);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setSoftInputMode(19);
        return alertDialog;
    }

    private void V() {
        this.ag.clear();
        this.ag.add((Object)new File(".."));
        if (this.ae.exists()) {
            ArrayList arrayList = new ArrayList();
            Object[] arrobject = this.ae.listFiles(this.as);
            if (arrobject != null) {
                arrayList.addAll((Collection)Arrays.asList((Object[])arrobject));
                Collections.sort((List)arrayList, (Comparator)new c(this, null));
                this.ag.addAll((Collection)arrayList);
            }
            arrayList.clear();
            Object[] arrobject2 = this.ae.listFiles(this.ar);
            if (arrobject2 != null) {
                arrayList.addAll((Collection)Arrays.asList((Object[])arrobject2));
                Collections.sort((List)arrayList, (Comparator)new c(this, null));
                this.ag.addAll((Collection)arrayList);
            }
        }
    }

    private void W() {
        try {
            this.al.setText((CharSequence)this.ae.getCanonicalFile().getAbsolutePath());
            return;
        }
        catch (IOException var1_1) {
            return;
        }
    }

    private boolean b(String string) {
        File file = new File(this.ae.getAbsolutePath() + File.separator + string);
        boolean bl = file.mkdir();
        if (bl || file.isDirectory()) {
            super.V();
            super.W();
            this.ah.notifyDataSetChanged();
            int n2 = super.c(file);
            if (n2 != -1) {
                this.ai.setSelection(n2);
            }
        }
        return bl;
    }

    private int c(File file) {
        for (int i2 = 0; i2 < this.ag.size(); ++i2) {
            try {
                boolean bl = ((File)this.ag.get(i2)).getCanonicalPath().equals((Object)file.getCanonicalPath());
                if (!bl) continue;
                return i2;
            }
            catch (IOException var3_4) {
                // empty catch block
            }
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void d(File file) {
        try {
            int n2 = this.ai.getFirstVisiblePosition();
            View view = this.ai.getChildAt(0);
            int n3 = 0;
            if (view != null) {
                int n4;
                n3 = n4 = view.getTop();
            }
            this.ap.put((Object)file.getCanonicalPath(), (Object)new Pair((Object)n2, (Object)n3));
            return;
        }
        catch (IOException var2_6) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void e(File file) {
        try {
            Pair pair = (Pair)this.ap.get((Object)file.getCanonicalPath());
            if (pair != null) {
                this.ai.setSelectionFromTop(((Integer)pair.first).intValue(), ((Integer)pair.second).intValue());
                return;
            }
            this.ai.setSelectionFromTop(0, 0);
            return;
        }
        catch (IOException var2_3) {
            this.ai.setSelectionFromTop(0, 0);
            return;
        }
    }

    public void a(Activity activity) {
        super.a(activity);
        try {
            this.ad = (b)activity;
            return;
        }
        catch (ClassCastException var2_2) {
            throw new ClassCastException(activity.toString() + " doesn't implement OnFileSelectedListener");
        }
    }

    protected void a(File file) {
        this.a(file, h.d.error_unable_to_write_to_this);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void a(File file, int n2) {
        String string = file.isDirectory() ? this.i().getString(h.d.text_directory) : (file.isFile() ? this.i().getString(h.d.text_file) : this.i().getString(h.d.text_dotdotdot_thing));
        Toast.makeText((Context)this.ac, (CharSequence)(this.a(n2, new Object[]{string}) + "."), (int)0).show();
    }

    protected void b(File file) {
        this.a(file, h.d.error_unable_to_read_this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Dialog c(Bundle bundle) {
        boolean bl = true;
        this.ac = this.i();
        this.aj = (LayoutInflater)this.ac.getSystemService("layout_inflater");
        this.ap = new HashMap();
        Bundle bundle2 = this.h();
        if (bundle2 != null) {
            String string = bundle2.getString("path");
            if (string == null) {
                string = "/";
            }
            this.ae = new File(string);
            this.am = bundle2.getBoolean("savedialog", false);
            this.ao = bundle2.getInt("requestcode", -1);
            boolean bl2 = this.am ? bl : false;
            if (!bundle2.getBoolean("cancreatenewdirectories", bl2) || !this.am) {
                bl = false;
            }
            this.an = bl;
        } else {
            this.ae = new File("/");
            this.am = false;
            this.ao = -1;
            this.an = false;
        }
        if (!this.ae.isDirectory() || !this.ae.canRead()) {
            this.ae = this.ae.getParentFile();
            if (this.ae == null || !this.ae.isDirectory() || !this.ae.canRead()) {
                this.ae = new File("/");
            }
        }
        this.ag = new ArrayList();
        this.ah = (e)this.new a(this.ac, h.c.filepicker_list_row, (List<File>)this.ag);
        super.V();
        Dialog dialog = super.U();
        super.W();
        return dialog;
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.ad.a(null, this.ao);
    }

    private class a
    extends ArrayAdapter<File> {
        static final /* synthetic */ boolean a;
        private int c;
        private List<File> d;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !e.class.desiredAssertionStatus();
            a = bl;
        }

        public a(Context context, int n2, List<File> list) {
            super(context, n2, list);
            this.c = n2;
            this.d = list;
        }

        /*
         * Enabled aggressive block sorting
         */
        public View getView(int n2, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = e.this.aj.inflate(this.c, null);
            }
            if (!a && view == null) {
                throw new AssertionError();
            }
            TextView textView = (TextView)view.findViewById(h.b.textView_list_row);
            ImageView imageView = (ImageView)view.findViewById(h.b.imageView_list_row);
            File file = (File)this.d.get(n2);
            if (file == null) return view;
            {
                textView.setText((CharSequence)file.getName());
                textView.setTextColor(-1);
                if (file.isDirectory()) {
                    view.setBackgroundResource(h.a.bg_list_selector_dir);
                    if (!"..".equals((Object)file.getPath())) {
                        imageView.setImageResource(h.a.ic_collections_small_collection);
                        return view;
                    }
                    imageView.setImageResource(h.a.ic_navigation_collapse);
                    return view;
                } else {
                    if (!file.isFile()) return view;
                    {
                        view.setBackgroundResource(h.a.bg_list_selector_file);
                        imageView.setImageResource(h.a.ic_collections_file);
                        return view;
                    }
                }
            }
        }
    }

    public static interface b {
        public boolean a(File var1, int var2);
    }

    private class c
    implements Comparator<File> {
        final /* synthetic */ e a;

        private c(e e2) {
            this.a = e2;
        }

        /* synthetic */ c(e e2,  var2_2) {
            super(e2);
        }

        public int a(File file, File file2) {
            int n2 = file.getName().compareToIgnoreCase(file2.getName());
            if (n2 != 0) {
                return n2;
            }
            return file.getName().compareTo(file2.getName());
        }

        public /* synthetic */ int compare(Object object, Object object2) {
            return this.a((File)object, (File)object2);
        }
    }

}

