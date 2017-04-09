package com.gumtree.android.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NonNls;

public final class Timber {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    private static final List<Tree> FOREST = new ArrayList();
    public static final int INFO = 4;
    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    private static final Tree TREE_OF_SOULS = new 1();
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;

    public abstract class Tree {
        final ThreadLocal<String> explicitTag = new ThreadLocal();

        protected abstract void log(int i, String str, String str2, Throwable th);

        protected String getTag() {
            String str = (String) this.explicitTag.get();
            if (str != null) {
                this.explicitTag.remove();
            }
            return str;
        }

        public void v(String str, Object... objArr) {
            prepareLog(Timber.VERBOSE, null, str, objArr);
        }

        public void v(Throwable th, String str, Object... objArr) {
            prepareLog(Timber.VERBOSE, th, str, objArr);
        }

        public void v(Throwable th) {
            prepareLog(Timber.VERBOSE, th, null, new Object[0]);
        }

        public void d(String str, Object... objArr) {
            prepareLog(Timber.DEBUG, null, str, objArr);
        }

        public void d(Throwable th, String str, Object... objArr) {
            prepareLog(Timber.DEBUG, th, str, objArr);
        }

        public void d(Throwable th) {
            prepareLog(Timber.DEBUG, th, null, new Object[0]);
        }

        public void i(String str, Object... objArr) {
            prepareLog(Timber.INFO, null, str, objArr);
        }

        public void i(Throwable th, String str, Object... objArr) {
            prepareLog(Timber.INFO, th, str, objArr);
        }

        public void i(Throwable th) {
            prepareLog(Timber.INFO, th, null, new Object[0]);
        }

        public void w(String str, Object... objArr) {
            prepareLog(Timber.WARN, null, str, objArr);
        }

        public void w(Throwable th, String str, Object... objArr) {
            prepareLog(Timber.WARN, th, str, objArr);
        }

        public void w(Throwable th) {
            prepareLog(Timber.WARN, th, null, new Object[0]);
        }

        public void e(String str, Object... objArr) {
            prepareLog(Timber.ERROR, null, str, objArr);
        }

        public void e(Throwable th, String str, Object... objArr) {
            prepareLog(Timber.ERROR, th, str, objArr);
        }

        public void e(Throwable th) {
            prepareLog(Timber.ERROR, th, null, new Object[0]);
        }

        public void wtf(String str, Object... objArr) {
            prepareLog(Timber.ASSERT, null, str, objArr);
        }

        public void wtf(Throwable th, String str, Object... objArr) {
            prepareLog(Timber.ASSERT, th, str, objArr);
        }

        public void wtf(Throwable th) {
            prepareLog(Timber.ASSERT, th, null, new Object[0]);
        }

        public void log(int i, String str, Object... objArr) {
            prepareLog(i, null, str, objArr);
        }

        public void log(int i, Throwable th, String str, Object... objArr) {
            prepareLog(i, th, str, objArr);
        }

        public void log(int i, Throwable th) {
            prepareLog(i, th, null, new Object[0]);
        }

        @Deprecated
        protected boolean isLoggable(int i) {
            return true;
        }

        protected boolean isLoggable(String str, int i) {
            return isLoggable(i);
        }

        private void prepareLog(int i, Throwable th, String str, Object... objArr) {
            String tag = getTag();
            if (isLoggable(tag, i)) {
                String str2;
                if (str == null || str.length() != 0) {
                    str2 = str;
                } else {
                    str2 = null;
                }
                if (str2 != null) {
                    if (objArr.length > 0) {
                        str2 = String.format(str2, objArr);
                    }
                    if (th != null) {
                        str2 = str2 + "\n" + getStackTraceString(th);
                    }
                } else if (th != null) {
                    str2 = getStackTraceString(th);
                } else {
                    return;
                }
                log(i, tag, str2, th);
            }
        }

        private String getStackTraceString(Throwable th) {
            Writer stringWriter = new StringWriter(256);
            PrintWriter printWriter = new PrintWriter(stringWriter, false);
            th.printStackTrace(printWriter);
            printWriter.flush();
            return stringWriter.toString();
        }
    }

    public static void v(@NonNls String str, Object... objArr) {
        TREE_OF_SOULS.v(str, objArr);
    }

    public static void v(Throwable th, @NonNls String str, Object... objArr) {
        TREE_OF_SOULS.v(th, str, objArr);
    }

    public static void v(Throwable th) {
        TREE_OF_SOULS.v(th);
    }

    public static void d(@NonNls String str, Object... objArr) {
        TREE_OF_SOULS.d(str, objArr);
    }

    public static void d(Throwable th, @NonNls String str, Object... objArr) {
        TREE_OF_SOULS.d(th, str, objArr);
    }

    public static void d(Throwable th) {
        TREE_OF_SOULS.d(th);
    }

    public static void i(@NonNls String str, Object... objArr) {
        TREE_OF_SOULS.i(str, objArr);
    }

    public static void i(Throwable th, @NonNls String str, Object... objArr) {
        TREE_OF_SOULS.i(th, str, objArr);
    }

    public static void i(Throwable th) {
        TREE_OF_SOULS.i(th);
    }

    public static void w(@NonNls String str, Object... objArr) {
        TREE_OF_SOULS.w(str, objArr);
    }

    public static void w(Throwable th, @NonNls String str, Object... objArr) {
        TREE_OF_SOULS.w(th, str, objArr);
    }

    public static void w(Throwable th) {
        TREE_OF_SOULS.w(th);
    }

    public static void e(@NonNls String str, Object... objArr) {
        TREE_OF_SOULS.e(str, objArr);
    }

    public static void e(Throwable th, @NonNls String str, Object... objArr) {
        TREE_OF_SOULS.e(th, str, objArr);
    }

    public static void e(Throwable th) {
        TREE_OF_SOULS.e(th);
    }

    public static void wtf(@NonNls String str, Object... objArr) {
        TREE_OF_SOULS.wtf(str, objArr);
    }

    public static void wtf(Throwable th, @NonNls String str, Object... objArr) {
        TREE_OF_SOULS.wtf(th, str, objArr);
    }

    public static void wtf(Throwable th) {
        TREE_OF_SOULS.wtf(th);
    }

    public static void log(int i, @NonNls String str, Object... objArr) {
        TREE_OF_SOULS.log(i, str, objArr);
    }

    public static void log(int i, Throwable th, @NonNls String str, Object... objArr) {
        TREE_OF_SOULS.log(i, th, str, objArr);
    }

    public static void log(int i, Throwable th) {
        TREE_OF_SOULS.log(i, th);
    }

    public static Tree asTree() {
        return TREE_OF_SOULS;
    }

    public static Tree tag(String str) {
        for (Tree tree : forestAsArray) {
            tree.explicitTag.set(str);
        }
        return TREE_OF_SOULS;
    }

    public static void plant(Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        } else if (tree == TREE_OF_SOULS) {
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
        } else {
            synchronized (FOREST) {
                FOREST.add(tree);
                forestAsArray = (Tree[]) FOREST.toArray(new Tree[FOREST.size()]);
            }
        }
    }

    public static void plant(Tree... treeArr) {
        if (treeArr == null) {
            throw new NullPointerException("trees == null");
        }
        int length = treeArr.length;
        int i = 0;
        while (i < length) {
            Tree tree = treeArr[i];
            if (tree == null) {
                throw new NullPointerException("trees contains null");
            } else if (tree == TREE_OF_SOULS) {
                throw new IllegalArgumentException("Cannot plant Timber into itself.");
            } else {
                i++;
            }
        }
        synchronized (FOREST) {
            Collections.addAll(FOREST, treeArr);
            forestAsArray = (Tree[]) FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    public static void uproot(Tree tree) {
        synchronized (FOREST) {
            if (FOREST.remove(tree)) {
                forestAsArray = (Tree[]) FOREST.toArray(new Tree[FOREST.size()]);
            } else {
                throw new IllegalArgumentException("Cannot uproot tree which is not planted: " + tree);
            }
        }
    }

    public static void uprootAll() {
        synchronized (FOREST) {
            FOREST.clear();
            forestAsArray = TREE_ARRAY_EMPTY;
        }
    }

    public static List<Tree> forest() {
        List<Tree> unmodifiableList;
        synchronized (FOREST) {
            unmodifiableList = Collections.unmodifiableList(new ArrayList(FOREST));
        }
        return unmodifiableList;
    }

    public static int treeCount() {
        int size;
        synchronized (FOREST) {
            size = FOREST.size();
        }
        return size;
    }

    private Timber() {
        throw new AssertionError("No instances.");
    }
}
