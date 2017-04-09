package com.gumtree.android.dagger;

import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import java.util.HashMap;
import java.util.Map;

public final class ComponentsManager {
    private static final String KEY_ACTIVITY = "activity";
    private static final String KEY_APP = "app";
    private static final String KEY_COMMON = "commmon";
    private final Map<String, ? super BaseComponent> baseComponents;

    class LazyHolder {
        private static final ComponentsManager INSTANCE = new ComponentsManager();

        private LazyHolder() {
        }
    }

    private ComponentsManager() {
        this.baseComponents = new HashMap();
    }

    public static ComponentsManager get() {
        return LazyHolder.INSTANCE;
    }

    public <C extends BaseComponent> void removeBaseComponent(String str) {
        this.baseComponents.remove(str);
    }

    public <C extends BaseComponent> C getBaseComponent(String str) {
        return (BaseComponent) this.baseComponents.get(str);
    }

    public <C extends BaseComponent> void putBaseComponent(String str, C c) {
        this.baseComponents.put(str, c);
    }

    public void removeBaseComponents() {
        this.baseComponents.clear();
    }

    public ApplicationComponent getAppComponent() {
        return (ApplicationComponent) getBaseComponent(KEY_APP);
    }

    public void setAppComponent(BaseComponent baseComponent) {
        this.baseComponents.put(KEY_APP, baseComponent);
    }

    public synchronized void clear() {
        this.baseComponents.clear();
    }
}
