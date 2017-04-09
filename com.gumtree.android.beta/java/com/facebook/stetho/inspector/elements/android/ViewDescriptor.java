package com.facebook.stetho.inspector.elements.android;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewDebug.FlagToString;
import android.view.ViewDebug.IntToString;
import com.facebook.stetho.common.ExceptionUtil;
import com.facebook.stetho.common.LogUtil;
import com.facebook.stetho.common.StringUtil;
import com.facebook.stetho.common.android.ResourcesUtil;
import com.facebook.stetho.inspector.elements.AbstractChainedDescriptor;
import com.facebook.stetho.inspector.elements.AttributeAccumulator;
import com.facebook.stetho.inspector.elements.StyleAccumulator;
import com.facebook.stetho.inspector.helper.IntegerFormatter;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

final class ViewDescriptor extends AbstractChainedDescriptor<View> implements HighlightableDescriptor {
    private static final String ID_NAME = "id";
    private static final String NONE_MAPPING = "<no mapping>";
    private static final String NONE_VALUE = "(none)";
    private final MethodInvoker mMethodInvoker;
    @GuardedBy("this")
    @Nullable
    private volatile List<ViewCSSProperty> mViewProperties;
    @Nullable
    private Pattern mWordBoundaryPattern;

    abstract class ViewCSSProperty {
        private final ExportedProperty mAnnotation;
        private final String mCSSName;

        public abstract Object getValue(View view) throws InvocationTargetException, IllegalAccessException;

        public ViewCSSProperty(String str, ExportedProperty exportedProperty) {
            this.mCSSName = str;
            this.mAnnotation = exportedProperty;
        }

        public final String getCSSName() {
            return this.mCSSName;
        }

        @Nullable
        public final ExportedProperty getAnnotation() {
            return this.mAnnotation;
        }
    }

    private Pattern getWordBoundaryPattern() {
        if (this.mWordBoundaryPattern == null) {
            this.mWordBoundaryPattern = Pattern.compile("(?<=\\p{Lower})(?=\\p{Upper})");
        }
        return this.mWordBoundaryPattern;
    }

    private List<ViewCSSProperty> getViewProperties() {
        int i = 0;
        if (this.mViewProperties == null) {
            synchronized (this) {
                if (this.mViewProperties == null) {
                    ExportedProperty exportedProperty;
                    List arrayList = new ArrayList();
                    for (Method method : View.class.getDeclaredMethods()) {
                        exportedProperty = (ExportedProperty) method.getAnnotation(ExportedProperty.class);
                        if (exportedProperty != null) {
                            arrayList.add(new MethodBackedCSSProperty(this, method, convertViewPropertyNameToCSSName(method.getName()), exportedProperty));
                        }
                    }
                    Field[] declaredFields = View.class.getDeclaredFields();
                    int length = declaredFields.length;
                    while (i < length) {
                        Field field = declaredFields[i];
                        exportedProperty = (ExportedProperty) field.getAnnotation(ExportedProperty.class);
                        if (exportedProperty != null) {
                            arrayList.add(new FieldBackedCSSProperty(this, field, convertViewPropertyNameToCSSName(field.getName()), exportedProperty));
                        }
                        i++;
                    }
                    Collections.sort(arrayList, new 1(this));
                    this.mViewProperties = Collections.unmodifiableList(arrayList);
                }
            }
        }
        return this.mViewProperties;
    }

    public ViewDescriptor() {
        this(new MethodInvoker());
    }

    public ViewDescriptor(MethodInvoker methodInvoker) {
        this.mMethodInvoker = methodInvoker;
    }

    protected String onGetNodeName(View view) {
        String name = view.getClass().getName();
        return StringUtil.removePrefix(name, "android.view.", StringUtil.removePrefix(name, "android.widget."));
    }

    protected void onGetAttributes(View view, AttributeAccumulator attributeAccumulator) {
        String idAttribute = getIdAttribute(view);
        if (idAttribute != null) {
            attributeAccumulator.store(ID_NAME, idAttribute);
        }
    }

    protected void onSetAttributesAsText(View view, String str) {
        for (Entry entry : parseSetAttributesAsTextArg(str).entrySet()) {
            this.mMethodInvoker.invoke(view, "set" + capitalize((String) entry.getKey()), (String) entry.getValue());
        }
    }

    @Nullable
    private static String getIdAttribute(View view) {
        int id = view.getId();
        if (id == -1) {
            return null;
        }
        return ResourcesUtil.getIdStringQuietly(view, view.getResources(), id);
    }

    public View getViewForHighlighting(Object obj) {
        return (View) obj;
    }

    protected void onGetStyles(View view, StyleAccumulator styleAccumulator) {
        List viewProperties = getViewProperties();
        int size = viewProperties.size();
        for (int i = 0; i < size; i++) {
            ViewCSSProperty viewCSSProperty = (ViewCSSProperty) viewProperties.get(i);
            try {
                getStyleFromValue(view, viewCSSProperty.getCSSName(), viewCSSProperty.getValue(view), viewCSSProperty.getAnnotation(), styleAccumulator);
            } catch (Throwable e) {
                if ((e instanceof IllegalAccessException) || (e instanceof InvocationTargetException)) {
                    LogUtil.e(e, "failed to get style property " + viewCSSProperty.getCSSName() + " of element= " + view.toString());
                } else {
                    throw ExceptionUtil.propagate(e);
                }
            }
        }
    }

    protected void onGetAccessibilityStyles(View view, StyleAccumulator styleAccumulator) {
        AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain();
        ViewCompat.onInitializeAccessibilityNodeInfo(view, obtain);
        boolean ignored = AccessibilityNodeInfoWrapper.getIgnored(obtain, view);
        getStyleFromValue(view, "ignored", Boolean.valueOf(ignored), null, styleAccumulator);
        if (ignored) {
            getStyleFromValue(view, "ignored-reasons", AccessibilityNodeInfoWrapper.getIgnoredReasons(obtain, view), null, styleAccumulator);
        }
        getStyleFromValue(view, "focusable", Boolean.valueOf(!ignored), null, styleAccumulator);
        if (!ignored) {
            getStyleFromValue(view, "focusable-reasons", AccessibilityNodeInfoWrapper.getFocusableReasons(obtain, view), null, styleAccumulator);
            getStyleFromValue(view, "focused", Boolean.valueOf(obtain.isAccessibilityFocused()), null, styleAccumulator);
            getStyleFromValue(view, "description", AccessibilityNodeInfoWrapper.getDescription(obtain, view), null, styleAccumulator);
            getStyleFromValue(view, "actions", AccessibilityNodeInfoWrapper.getActions(obtain), null, styleAccumulator);
        }
        obtain.recycle();
    }

    private static boolean canIntBeMappedToString(@Nullable ExportedProperty exportedProperty) {
        return (exportedProperty == null || exportedProperty.mapping() == null || exportedProperty.mapping().length <= 0) ? false : true;
    }

    private static String mapIntToStringUsingAnnotation(int i, @Nullable ExportedProperty exportedProperty) {
        if (canIntBeMappedToString(exportedProperty)) {
            for (IntToString intToString : exportedProperty.mapping()) {
                if (intToString.from() == i) {
                    return intToString.to();
                }
            }
            return NONE_MAPPING;
        }
        throw new IllegalStateException("Cannot map using this annotation");
    }

    private static boolean canFlagsBeMappedToString(@Nullable ExportedProperty exportedProperty) {
        return (exportedProperty == null || exportedProperty.flagMapping() == null || exportedProperty.flagMapping().length <= 0) ? false : true;
    }

    private static String mapFlagsToStringUsingAnnotation(int i, @Nullable ExportedProperty exportedProperty) {
        if (canFlagsBeMappedToString(exportedProperty)) {
            StringBuilder stringBuilder = null;
            Object obj = null;
            for (FlagToString flagToString : exportedProperty.flagMapping()) {
                boolean z;
                boolean outputIf = flagToString.outputIf();
                if ((flagToString.mask() & i) == flagToString.equals()) {
                    z = true;
                } else {
                    z = false;
                }
                if (outputIf == z) {
                    if (stringBuilder == null) {
                        stringBuilder = new StringBuilder();
                    }
                    if (obj != null) {
                        stringBuilder.append(" | ");
                    }
                    stringBuilder.append(flagToString.name());
                    obj = 1;
                }
            }
            if (obj != null) {
                return stringBuilder.toString();
            }
            return NONE_MAPPING;
        }
        throw new IllegalStateException("Cannot map using this annotation");
    }

    private String convertViewPropertyNameToCSSName(String str) {
        String[] split = getWordBoundaryPattern().split(str);
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (i < split.length) {
            if (!(split[i].equals("get") || split[i].equals("m"))) {
                stringBuilder.append(split[i].toLowerCase());
                if (i < split.length - 1) {
                    stringBuilder.append('-');
                }
            }
            i++;
        }
        return stringBuilder.toString();
    }

    private void getStyleFromValue(View view, String str, Object obj, @Nullable ExportedProperty exportedProperty, StyleAccumulator styleAccumulator) {
        boolean z = true;
        if (str.equals(ID_NAME)) {
            getIdStyle(view, styleAccumulator);
        } else if (obj instanceof Integer) {
            getStyleFromInteger(str, (Integer) obj, exportedProperty, styleAccumulator);
        } else if (obj instanceof Float) {
            r2 = String.valueOf(obj);
            if (((Float) obj).floatValue() != 0.0f) {
                z = false;
            }
            styleAccumulator.store(str, r2, z);
        } else if (obj instanceof Boolean) {
            styleAccumulator.store(str, String.valueOf(obj), false);
        } else if (obj instanceof Short) {
            r2 = String.valueOf(obj);
            if (((Short) obj).shortValue() != (short) 0) {
                z = false;
            }
            styleAccumulator.store(str, r2, z);
        } else if (obj instanceof Long) {
            r2 = String.valueOf(obj);
            if (((Long) obj).longValue() != 0) {
                z = false;
            }
            styleAccumulator.store(str, r2, z);
        } else if (obj instanceof Double) {
            r2 = String.valueOf(obj);
            if (((Double) obj).doubleValue() != 0.0d) {
                z = false;
            }
            styleAccumulator.store(str, r2, z);
        } else if (obj instanceof Byte) {
            r2 = String.valueOf(obj);
            if (((Byte) obj).byteValue() != (byte) 0) {
                z = false;
            }
            styleAccumulator.store(str, r2, z);
        } else if (obj instanceof Character) {
            r2 = String.valueOf(obj);
            if (((Character) obj).charValue() != '\u0000') {
                z = false;
            }
            styleAccumulator.store(str, r2, z);
        } else if (obj instanceof CharSequence) {
            r2 = String.valueOf(obj);
            if (((CharSequence) obj).length() != 0) {
                z = false;
            }
            styleAccumulator.store(str, r2, z);
        } else {
            getStylesFromObject(view, str, obj, exportedProperty, styleAccumulator);
        }
    }

    private void getIdStyle(View view, StyleAccumulator styleAccumulator) {
        String idAttribute = getIdAttribute(view);
        if (idAttribute == null) {
            styleAccumulator.store(ID_NAME, NONE_VALUE, false);
        } else {
            styleAccumulator.store(ID_NAME, idAttribute, false);
        }
    }

    private void getStyleFromInteger(String str, Integer num, @Nullable ExportedProperty exportedProperty, StyleAccumulator styleAccumulator) {
        String format = IntegerFormatter.getInstance().format(num, exportedProperty);
        if (canIntBeMappedToString(exportedProperty)) {
            styleAccumulator.store(str, format + " (" + mapIntToStringUsingAnnotation(num.intValue(), exportedProperty) + ")", false);
        } else if (canFlagsBeMappedToString(exportedProperty)) {
            styleAccumulator.store(str, format + " (" + mapFlagsToStringUsingAnnotation(num.intValue(), exportedProperty) + ")", false);
        } else {
            Boolean valueOf = Boolean.valueOf(true);
            if (num.intValue() != 0 || canFlagsBeMappedToString(exportedProperty) || canIntBeMappedToString(exportedProperty)) {
                valueOf = Boolean.valueOf(false);
            }
            styleAccumulator.store(str, format, valueOf.booleanValue());
        }
    }

    private void getStylesFromObject(View view, String str, Object obj, @Nullable ExportedProperty exportedProperty, StyleAccumulator styleAccumulator) {
        if (exportedProperty != null && exportedProperty.deepExport() && obj != null) {
            for (Field field : obj.getClass().getFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    try {
                        String str2;
                        field.setAccessible(true);
                        Object obj2 = field.get(obj);
                        String name = field.getName();
                        Object obj3 = -1;
                        switch (name.hashCode()) {
                            case -599904534:
                                if (name.equals("rightMargin")) {
                                    obj3 = 3;
                                    break;
                                }
                                break;
                            case -414179485:
                                if (name.equals("topMargin")) {
                                    obj3 = 1;
                                    break;
                                }
                                break;
                            case 1928835221:
                                if (name.equals("leftMargin")) {
                                    obj3 = 2;
                                    break;
                                }
                                break;
                            case 2064613305:
                                if (name.equals("bottomMargin")) {
                                    obj3 = null;
                                    break;
                                }
                                break;
                        }
                        switch (obj3) {
                            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                                str2 = "margin-bottom";
                                break;
                            case HighlightView.GROW_NONE /*1*/:
                                str2 = "margin-top";
                                break;
                            case HighlightView.GROW_LEFT_EDGE /*2*/:
                                str2 = "margin-left";
                                break;
                            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                                str2 = "margin-right";
                                break;
                            default:
                                String prefix = exportedProperty.prefix();
                                if (prefix != null) {
                                    name = prefix + name;
                                }
                                str2 = convertViewPropertyNameToCSSName(name);
                                break;
                        }
                        getStyleFromValue(view, str2, obj2, (ExportedProperty) field.getAnnotation(ExportedProperty.class), styleAccumulator);
                    } catch (Throwable e) {
                        LogUtil.e(e, "failed to get property of name: \"" + str + "\" of object: " + String.valueOf(obj));
                        return;
                    }
                }
            }
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.length() == 0 || Character.isTitleCase(str.charAt(0))) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.setCharAt(0, Character.toTitleCase(stringBuilder.charAt(0)));
        return stringBuilder.toString();
    }
}
