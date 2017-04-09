/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.support.v4.app.p
 *  android.support.v7.app.b
 *  android.text.Editable
 *  android.util.SparseBooleanArray
 *  android.view.ActionMode
 *  android.view.ContextMenu
 *  android.view.ContextMenu$ContextMenuInfo
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.AbsListView
 *  android.widget.AbsListView$MultiChoiceModeListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$AdapterContextMenuInfo
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.EditText
 *  android.widget.ListAdapter
 *  android.widget.Toast
 *  java.io.File
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Thread
 *  java.util.ArrayList
 *  java.util.Collections
 *  java.util.Iterator
 *  java.util.List
 */
package com.cygery.repetitouch.pro;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.p;
import android.text.Editable;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;
import com.cygery.repetitouch.Event;
import com.cygery.repetitouch.MergeItem;
import com.cygery.repetitouch.b;
import com.cygery.repetitouch.c;
import com.cygery.repetitouch.e;
import com.cygery.repetitouch.pro.EditMergeItemActivity;
import com.cygery.repetitouch.pro.EventManagerServicePro;
import com.cygery.repetitouch.pro.a;
import com.cygery.utilities.DraggableListView;
import com.cygery.utilities.e;
import com.cygery.utilities.f;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MergeActivity
extends android.support.v7.app.b
implements DraggableListView.a,
e.b {
    private static final String m = MergeActivity.class.getName();
    private Context n;
    private SharedPreferences o;
    private String p;
    private ArrayList<MergeItem> q;
    private a r;
    private c s;
    private int t = 1;

    int a(SparseBooleanArray sparseBooleanArray) {
        if (sparseBooleanArray == null) {
            return 0;
        }
        int n2 = 0;
        for (int i2 = 0; i2 < sparseBooleanArray.size(); ++i2) {
            if (!sparseBooleanArray.valueAt(i2)) continue;
            ++n2;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(int n2, int n3) {
        if (this.q == null || this.r == null || n2 == n3) {
            return;
        }
        if (n2 > n3) {
            MergeItem mergeItem = (MergeItem)this.q.remove(n2);
            this.q.add(n3, (Object)mergeItem);
            this.r.notifyDataSetChanged();
            return;
        }
        MergeItem mergeItem = (MergeItem)this.q.remove(n2);
        int n4 = n3 - 1;
        this.q.add(n4, (Object)mergeItem);
        this.r.notifyDataSetChanged();
    }

    void a(String string, final Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setTitle(2131165404);
        builder.setMessage(2131165348);
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                runnable.run();
            }
        });
        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                dialogInterface.cancel();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialogInterface) {
                Toast.makeText((Context)MergeActivity.this.n, (int)2131165234, (int)0).show();
            }
        });
        builder.create().show();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean a(File file, int n2) {
        Runnable runnable;
        if (n2 == 1) {
            if (file == null) return true;
            {
                if (b.c(file.getAbsolutePath()) < 0) {
                    Toast.makeText((Context)this, (int)2131165239, (int)0).show();
                    return true;
                }
                this.q.add((Object)new MergeItem(this.n, file.getAbsolutePath()));
                this.r.notifyDataSetChanged();
            }
            return true;
        }
        if (n2 != 2) {
            return false;
        }
        if (file == null) return true;
        final b b2 = b.a();
        final String string = file.getAbsolutePath();
        if (b2 == null) return true;
        {
            runnable = new Runnable(){

                public void run() {
                    new Thread(){

                        /*
                         * Enabled aggressive block sorting
                         */
                        public void run() {
                            final boolean bl = b2.l() ? b2.a(string, MergeActivity.this.s.a(), MergeActivity.this.s.b()) : false;
                            MergeActivity.this.runOnUiThread(new Runnable(){

                                public void run() {
                                    if (bl) {
                                        Toast.makeText((Context)MergeActivity.this.n, (int)2131165247, (int)0).show();
                                        return;
                                    }
                                    Toast.makeText((Context)MergeActivity.this.n, (int)2131165246, (int)0).show();
                                }
                            });
                        }

                    }.start();
                }

            };
            if (f.a(string)) {
                this.a(string, runnable);
                return true;
            }
        }
        runnable.run();
        return true;
    }

    List<Integer> b(SparseBooleanArray sparseBooleanArray) {
        ArrayList arrayList = new ArrayList();
        if (sparseBooleanArray == null) {
            return arrayList;
        }
        for (int i2 = 0; i2 < sparseBooleanArray.size(); ++i2) {
            int n2;
            if (!sparseBooleanArray.valueAt(i2) || arrayList.contains((Object)(n2 = sparseBooleanArray.keyAt(i2)))) continue;
            arrayList.add((Object)n2);
        }
        Collections.sort((List)arrayList);
        return arrayList;
    }

    void b(int n2) {
        if (n2 >= 0 && n2 < this.q.size()) {
            this.q.remove(n2);
        }
    }

    void c(int n2) {
        MergeItem mergeItem = new MergeItem((MergeItem)this.q.get(n2));
        this.q.add(n2 + 1, (Object)mergeItem);
    }

    void c(SparseBooleanArray sparseBooleanArray) {
        List<Integer> list = this.b(sparseBooleanArray);
        if (list != null) {
            Collections.reverse(list);
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                int n2 = (Integer)iterator.next();
                this.q.remove(n2);
            }
        }
    }

    void d(int n2) {
        MergeItem mergeItem = (MergeItem)this.q.get(n2);
        this.s.c();
        this.s.a(mergeItem);
        Bundle bundle = new Bundle();
        bundle.putBoolean("savedialog", true);
        bundle.putString("path", this.p);
        bundle.putInt("requestcode", 2);
        com.cygery.utilities.e e2 = new com.cygery.utilities.e();
        e2.g(bundle);
        e2.a(this.e(), null);
    }

    void d(SparseBooleanArray sparseBooleanArray) {
        List<Integer> list = this.b(sparseBooleanArray);
        if (!list.isEmpty()) {
            int n2 = (Integer)list.get(0);
            c c2 = new c();
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                int n3 = (Integer)iterator.next();
                c2.a((MergeItem)this.q.get(n3));
            }
            Collections.reverse(list);
            Iterator iterator2 = list.iterator();
            while (iterator2.hasNext()) {
                int n4 = (Integer)iterator2.next();
                this.q.remove(n4);
            }
            ArrayList<MergeItem> arrayList = this.q;
            Context context = this.n;
            ArrayList<Event> arrayList2 = c2.b();
            long l2 = c2.a();
            StringBuilder stringBuilder = new StringBuilder().append(this.n.getString(2131165284)).append(" ");
            int n5 = this.t;
            this.t = n5 + 1;
            arrayList.add(n2, (Object)new MergeItem(context, arrayList2, l2, null, null, stringBuilder.append(n5).toString()));
        }
    }

    public void onActivityResult(int n2, int n3, Intent intent) {
        Bundle bundle;
        if (n2 == 1 && n3 == -1 && (bundle = intent.getExtras()) != null) {
            MergeItem mergeItem = (MergeItem)this.q.get(bundle.getInt("position"));
            mergeItem.a(bundle);
            if (mergeItem.a() == MergeItem.Type.TYPE_FILE) {
                mergeItem.j();
            }
            this.r.notifyDataSetChanged();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onContextItemSelected(MenuItem var1) {
        var2_2 = (AdapterView.AdapterContextMenuInfo)var1.getMenuInfo();
        if (var2_2 == null) {
            return false;
        }
        var3_3 = var2_2.position;
        switch (var1.getItemId()) {
            case 1: {
                this.b(var3_3);
                this.r.notifyDataSetChanged();
                ** break;
            }
            case 2: {
                this.c(var3_3);
                this.r.notifyDataSetChanged();
            }
lbl13: // 3 sources:
            default: {
                return true;
            }
            case 3: 
        }
        this.d(var3_3);
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @SuppressLint(value={"NewApi"})
    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.n = this;
        com.cygery.utilities.a.a((Context)this);
        this.setContentView(2130903071);
        this.o = this.getSharedPreferences("repetitouch_prefs", 0);
        this.p = this.o.getString("startDir", Environment.getExternalStorageDirectory().getPath());
        try {
            this.p = new File(this.p).getCanonicalPath();
        }
        catch (IOException var2_3) {
            ** continue;
        }
lbl11: // 2 sources:
        do {
            this.s = new c();
            var3_2 = (DraggableListView)this.findViewById(2131558554);
            this.q = EventManagerServicePro.L();
            if (this.q == null) {
                com.cygery.utilities.a.c(MergeActivity.m, "merge item list is null");
                return;
            }
            this.r = new a((Context)this, 2130903076, (List<MergeItem>)this.q);
            var3_2.setAdapter((ListAdapter)this.r);
            var3_2.setDragHandleResId(2131558589);
            var3_2.setDropListener((DraggableListView.a)this);
            var3_2.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> adapterView, View view, int n2, long l2) {
                    Bundle bundle = ((MergeItem)MergeActivity.this.q.get(n2)).n();
                    MergeActivity.this.startActivityForResult(new Intent(MergeActivity.this.n, (Class)EditMergeItemActivity.class).putExtras(bundle).putExtra("position", n2), 1);
                }
            });
            if (Build.VERSION.SDK_INT >= 11) {
                var3_2.setChoiceMode(3);
                var3_2.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener(){
                    Menu a;

                    /*
                     * Enabled aggressive block sorting
                     */
                    private void a() {
                        if (this.a == null) return;
                        MenuItem menuItem = this.a.findItem(2131558667);
                        MenuItem menuItem2 = this.a.findItem(2131558660);
                        if (var3_2.getCheckedItemCount() == 1) {
                            if (menuItem != null) {
                                menuItem.setVisible(true);
                            }
                            if (menuItem2 == null) return;
                            {
                                menuItem2.setVisible(true);
                                return;
                            }
                        }
                        if (menuItem != null) {
                            menuItem.setVisible(false);
                        }
                        if (menuItem2 == null) {
                            return;
                        }
                        menuItem2.setVisible(false);
                    }

                    private void a(ActionMode actionMode) {
                        actionMode.setTitle((CharSequence)("" + MergeActivity.this.a(var3_2.getCheckedItemPositions()) + "/" + MergeActivity.this.q.size()));
                    }

                    /*
                     * Enabled force condition propagation
                     * Lifted jumps to return sites
                     */
                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                        int n2 = menuItem.getItemId();
                        int n3 = 0;
                        if (n2 == 2131558665) {
                            while (n3 < MergeActivity.this.r.getCount()) {
                                var3_2.setItemChecked(n3, true);
                                ++n3;
                            }
                            return true;
                        }
                        if (n2 == 2131558666) {
                            MergeActivity.this.c(var3_2.getCheckedItemPositions());
                            MergeActivity.this.r.notifyDataSetChanged();
                            actionMode.finish();
                            return true;
                        }
                        if (n2 == 2131558667) {
                            SparseBooleanArray sparseBooleanArray = var3_2.getCheckedItemPositions();
                            if (sparseBooleanArray != null && sparseBooleanArray.size() == 1) {
                                MergeActivity.this.c(sparseBooleanArray.keyAt(0));
                            }
                            MergeActivity.this.r.notifyDataSetChanged();
                            return true;
                        }
                        if (n2 == 2131558664) {
                            MergeActivity.this.d(var3_2.getCheckedItemPositions());
                            MergeActivity.this.r.notifyDataSetChanged();
                            actionMode.finish();
                            return true;
                        }
                        boolean bl = false;
                        if (n2 != 2131558660) return bl;
                        SparseBooleanArray sparseBooleanArray = var3_2.getCheckedItemPositions();
                        if (sparseBooleanArray != null && sparseBooleanArray.size() == 1) {
                            MergeActivity.this.d(sparseBooleanArray.keyAt(0));
                        }
                        actionMode.finish();
                        return true;
                    }

                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        MenuInflater menuInflater = actionMode.getMenuInflater();
                        if (menuInflater == null) {
                            return false;
                        }
                        menuInflater.inflate(2131689476, menu);
                        var3_2.setDraggingEnabled(false);
                        this.a = menu;
                        super.a(actionMode);
                        return true;
                    }

                    public void onDestroyActionMode(ActionMode actionMode) {
                        var3_2.setDraggingEnabled(true);
                    }

                    public void onItemCheckedStateChanged(ActionMode actionMode, int n2, long l2, boolean bl) {
                        super.a();
                        super.a(actionMode);
                    }

                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }
                });
                return;
            }
            this.registerForContextMenu((View)var3_2);
            return;
            break;
        } while (true);
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        if (view.getId() == 2131558554) {
            contextMenu.add(0, 1, 0, 2131165313);
            contextMenu.add(0, 2, 0, 2131165314);
            contextMenu.add(0, 3, 0, 2131165316);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(2131689475, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int n2 = menuItem.getItemId();
        if (n2 == 2131558662) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("savedialog", false);
            bundle.putString("path", this.p);
            bundle.putInt("requestcode", 1);
            com.cygery.utilities.e e2 = new com.cygery.utilities.e();
            e2.g(bundle);
            e2.a(this.e(), null);
            return true;
        }
        if (n2 == 2131558663) {
            final EditText editText = new EditText(this.n);
            editText.setHint((CharSequence)this.n.getString(2131165225));
            AlertDialog.Builder builder = new AlertDialog.Builder(this.n);
            builder.setTitle(2131165311);
            builder.setView((View)editText);
            builder.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n2) {
                    long l2 = MergeItem.e(String.valueOf((Object)editText.getText()));
                    if (l2 >= 0) {
                        MergeActivity.this.q.add((Object)new MergeItem(MergeActivity.this.n, l2));
                        MergeActivity.this.r.notifyDataSetChanged();
                    }
                }
            });
            builder.setNegativeButton(17039360, null);
            builder.show();
            return true;
        }
        if (n2 == 2131558664) {
            Context context = this.n;
            StringBuilder stringBuilder = new StringBuilder().append(this.n.getString(2131165284)).append(" ");
            int n3 = this.t;
            this.t = n3 + 1;
            EventManagerServicePro.a(context, stringBuilder.append(n3).toString());
            this.r.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}

