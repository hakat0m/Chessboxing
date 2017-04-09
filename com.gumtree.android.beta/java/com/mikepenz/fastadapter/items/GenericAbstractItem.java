package com.mikepenz.fastadapter.items;

import android.support.v7.widget.RecyclerView.ViewHolder;
import com.mikepenz.fastadapter.IGenericItem;
import java.lang.reflect.ParameterizedType;

public abstract class GenericAbstractItem<Model, Item extends GenericAbstractItem<?, ?, ?>, VH extends ViewHolder> extends AbstractItem<Item, VH> implements IGenericItem<Model, Item, VH> {
    private Model mModel;

    public GenericAbstractItem(Model model) {
        this.mModel = model;
    }

    public Model getModel() {
        return this.mModel;
    }

    @Deprecated
    public GenericAbstractItem<?, ?, ?> setModel(Model model) {
        return withModel(model);
    }

    public GenericAbstractItem<?, ?, ?> withModel(Model model) {
        this.mModel = model;
        return this;
    }

    protected Class<? extends VH> viewHolderType() {
        return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }
}
