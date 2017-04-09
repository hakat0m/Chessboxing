package com.mikepenz.fastadapter.adapters;

import com.mikepenz.fastadapter.IGenericItem;
import com.mikepenz.fastadapter.utils.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GenericItemAdapter<Model, Item extends IGenericItem<? extends Model, ?, ?>> extends ItemAdapter<Item> {
    private final Function<Model, Item> mItemFactory;

    public GenericItemAdapter(Class<? extends Item> cls, Class<? extends Model> cls2) {
        this(new ReflectionBasedItemFactory(cls2, cls));
    }

    public GenericItemAdapter(Function<Model, Item> function) {
        this.mItemFactory = function;
    }

    public List<Model> getModels() {
        int size = getAdapterItems().size();
        List<Model> arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(((IGenericItem) getAdapterItems().get(i)).getModel());
        }
        return arrayList;
    }

    public GenericItemAdapter<Model, Item> setModel(List<Model> list) {
        super.set(toItems(list));
        return this;
    }

    public GenericItemAdapter<Model, Item> setNewModel(List<Model> list) {
        super.setNewList(toItems(list));
        return this;
    }

    @SafeVarargs
    public final GenericItemAdapter<Model, Item> addModel(Model... modelArr) {
        addModel(Arrays.asList(modelArr));
        return this;
    }

    public GenericItemAdapter<Model, Item> addModel(List<Model> list) {
        super.add(toItems(list));
        return this;
    }

    @SafeVarargs
    public final GenericItemAdapter<Model, Item> addModel(int i, Model... modelArr) {
        addModel(i, Arrays.asList(modelArr));
        return this;
    }

    public GenericItemAdapter<Model, Item> addModel(int i, List<Model> list) {
        super.add(i, toItems(list));
        return this;
    }

    public GenericItemAdapter<Model, Item> setModel(int i, Model model) {
        super.set(i, toItem(model));
        return this;
    }

    public GenericItemAdapter<Model, Item> clearModel() {
        super.clear();
        return this;
    }

    public GenericItemAdapter<Model, Item> moveModel(int i, int i2) {
        super.move(i, i2);
        return this;
    }

    public GenericItemAdapter<Model, Item> removeModelRange(int i, int i2) {
        super.removeRange(i, i2);
        return this;
    }

    public GenericItemAdapter<Model, Item> removeModel(int i) {
        super.remove(i);
        return this;
    }

    protected List<Item> toItems(List<Model> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        int size = list.size();
        List<Item> arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(toItem(list.get(i)));
        }
        return arrayList;
    }

    protected Item toItem(Model model) {
        return (IGenericItem) this.mItemFactory.apply(model);
    }
}
