package com.mikepenz.fastadapter.adapters;

import android.widget.Filter;
import android.widget.Filter.FilterResults;
import com.gumtree.android.UrlMatcher;
import com.mikepenz.fastadapter.AbstractAdapter;
import com.mikepenz.fastadapter.IExpandable;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.IItemAdapter.Predicate;
import com.mikepenz.fastadapter.ISubItem;
import com.mikepenz.fastadapter.utils.IdDistributor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemAdapter<Item extends IItem> extends AbstractAdapter<Item> implements IItemAdapter<Item> {
    protected Comparator<Item> mComparator;
    private Predicate<Item> mFilterPredicate;
    private Filter mItemFilter = new ItemFilter();
    protected ItemFilterListener mItemFilterListener;
    private List<Item> mItems = new ArrayList();
    private boolean mUseIdDistributor = true;

    public class ItemFilter extends Filter {
        private CharSequence mConstraint;
        private List<Item> mOriginalItems;

        protected FilterResults performFiltering(CharSequence charSequence) {
            if (ItemAdapter.this.getFastAdapter().isPositionBasedStateManagement()) {
                ItemAdapter.this.getFastAdapter().deselect();
            }
            ItemAdapter.this.getFastAdapter().collapse(false);
            this.mConstraint = charSequence;
            if (this.mOriginalItems == null) {
                this.mOriginalItems = new ArrayList(ItemAdapter.this.mItems);
            }
            FilterResults filterResults = new FilterResults();
            if (charSequence == null || charSequence.length() == 0) {
                filterResults.values = this.mOriginalItems;
                filterResults.count = this.mOriginalItems.size();
                this.mOriginalItems = null;
            } else {
                List list;
                List arrayList = new ArrayList();
                if (ItemAdapter.this.mFilterPredicate != null) {
                    for (IItem iItem : this.mOriginalItems) {
                        if (!ItemAdapter.this.mFilterPredicate.filter(iItem, charSequence)) {
                            arrayList.add(iItem);
                        }
                    }
                    list = arrayList;
                } else {
                    list = ItemAdapter.this.mItems;
                }
                filterResults.values = list;
                filterResults.count = list.size();
            }
            return filterResults;
        }

        public CharSequence getConstraint() {
            return this.mConstraint;
        }

        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.values != null) {
                ItemAdapter.this.set((List) filterResults.values);
            }
            if (ItemAdapter.this.mItemFilterListener != null) {
                ItemAdapter.this.mItemFilterListener.itemsFiltered();
            }
        }

        public Set<Integer> getSelections() {
            if (this.mOriginalItems == null) {
                return ItemAdapter.this.getFastAdapter().getSelections();
            }
            Set<Integer> hashSet = new HashSet();
            int preItemCountByOrder = ItemAdapter.this.getFastAdapter().getPreItemCountByOrder(ItemAdapter.this.getOrder());
            int size = this.mOriginalItems.size();
            for (int i = 0; i < size; i++) {
                if (((IItem) this.mOriginalItems.get(i)).isSelected()) {
                    hashSet.add(Integer.valueOf(i + preItemCountByOrder));
                }
            }
            return hashSet;
        }

        public Set<Item> getSelectedItems() {
            if (this.mOriginalItems == null) {
                return ItemAdapter.this.getFastAdapter().getSelectedItems();
            }
            Set<Item> hashSet = new HashSet();
            int size = this.mOriginalItems.size();
            for (int i = 0; i < size; i++) {
                IItem iItem = (IItem) this.mOriginalItems.get(i);
                if (iItem.isSelected()) {
                    hashSet.add(iItem);
                }
            }
            return hashSet;
        }

        @SafeVarargs
        public final ItemAdapter<Item> add(Item... itemArr) {
            return add(Arrays.asList(itemArr));
        }

        public ItemAdapter<Item> add(List<Item> list) {
            if (this.mOriginalItems == null || list.size() <= 0) {
                return ItemAdapter.this.add((List) list);
            }
            if (ItemAdapter.this.mUseIdDistributor) {
                IdDistributor.checkIds(list);
            }
            this.mOriginalItems.addAll(list);
            performFiltering(this.mConstraint);
            return ItemAdapter.this;
        }

        @SafeVarargs
        public final ItemAdapter<Item> add(int i, Item... itemArr) {
            return add(i, Arrays.asList(itemArr));
        }

        public ItemAdapter<Item> add(int i, List<Item> list) {
            if (this.mOriginalItems == null || list.size() <= 0) {
                return ItemAdapter.this.add(i, (List) list);
            }
            if (ItemAdapter.this.mUseIdDistributor) {
                IdDistributor.checkIds(list);
            }
            this.mOriginalItems.addAll(i - ItemAdapter.this.getFastAdapter().getPreItemCountByOrder(ItemAdapter.this.getOrder()), list);
            performFiltering(this.mConstraint);
            return ItemAdapter.this;
        }

        public ItemAdapter<Item> set(int i, Item item) {
            if (this.mOriginalItems == null) {
                return ItemAdapter.this.set(i, (IItem) item);
            }
            if (ItemAdapter.this.mUseIdDistributor) {
                IdDistributor.checkId(item);
            }
            this.mOriginalItems.set(i - ItemAdapter.this.getFastAdapter().getPreItemCount(i), item);
            performFiltering(this.mConstraint);
            return ItemAdapter.this;
        }

        public ItemAdapter<Item> move(int i, int i2) {
            if (this.mOriginalItems == null) {
                return ItemAdapter.this.move(i, i2);
            }
            int preItemCount = ItemAdapter.this.getFastAdapter().getPreItemCount(i);
            IItem iItem = (IItem) this.mOriginalItems.get(i - preItemCount);
            this.mOriginalItems.remove(i - preItemCount);
            this.mOriginalItems.add(i2 - preItemCount, iItem);
            performFiltering(this.mConstraint);
            return ItemAdapter.this;
        }

        public ItemAdapter<Item> remove(int i) {
            if (this.mOriginalItems == null) {
                return ItemAdapter.this.remove(i);
            }
            ItemAdapter.this.mItems.remove(i - ItemAdapter.this.getFastAdapter().getPreItemCount(i));
            performFiltering(this.mConstraint);
            return ItemAdapter.this;
        }

        public ItemAdapter<Item> removeRange(int i, int i2) {
            if (this.mOriginalItems == null) {
                return ItemAdapter.this.removeRange(i, i2);
            }
            int size = this.mOriginalItems.size();
            int preItemCount = ItemAdapter.this.getFastAdapter().getPreItemCount(i);
            int min = Math.min(i2, (size - i) + preItemCount);
            for (size = 0; size < min; size++) {
                this.mOriginalItems.remove(i - preItemCount);
            }
            performFiltering(this.mConstraint);
            return ItemAdapter.this;
        }

        public ItemAdapter<Item> clear() {
            if (this.mOriginalItems == null) {
                return ItemAdapter.this.clear();
            }
            this.mOriginalItems.size();
            this.mOriginalItems.clear();
            performFiltering(this.mConstraint);
            return ItemAdapter.this;
        }
    }

    public ItemAdapter withUseIdDistributor(boolean z) {
        this.mUseIdDistributor = z;
        return this;
    }

    public boolean isUseIdDistributor() {
        return this.mUseIdDistributor;
    }

    public ItemAdapter<Item> withItemFilter(Filter filter) {
        this.mItemFilter = filter;
        return this;
    }

    public Filter getItemFilter() {
        return this.mItemFilter;
    }

    public ItemAdapter<Item> withFilterPredicate(Predicate<Item> predicate) {
        this.mFilterPredicate = predicate;
        return this;
    }

    public void filter(CharSequence charSequence) {
        this.mItemFilter.filter(charSequence);
    }

    public ItemAdapter<Item> withItemFilterListener(ItemFilterListener itemFilterListener) {
        this.mItemFilterListener = itemFilterListener;
        return this;
    }

    public ItemAdapter<Item> withComparator(Comparator<Item> comparator) {
        return withComparator(comparator, true);
    }

    public ItemAdapter<Item> withComparator(Comparator<Item> comparator, boolean z) {
        this.mComparator = comparator;
        if (!(this.mItems == null || this.mComparator == null || !z)) {
            Collections.sort(this.mItems, this.mComparator);
            getFastAdapter().notifyAdapterDataSetChanged();
        }
        return this;
    }

    public Comparator<Item> getComparator() {
        return this.mComparator;
    }

    public int getOrder() {
        return UrlMatcher.URI_CODE_SAVED_SEARCHES;
    }

    public int getAdapterItemCount() {
        return this.mItems.size();
    }

    public List<Item> getAdapterItems() {
        return this.mItems;
    }

    public int getAdapterPosition(Item item) {
        return getAdapterPosition(item.getIdentifier());
    }

    public int getAdapterPosition(long j) {
        int size = this.mItems.size();
        for (int i = 0; i < size; i++) {
            if (((IItem) this.mItems.get(i)).getIdentifier() == j) {
                return i;
            }
        }
        return -1;
    }

    public int getGlobalPosition(int i) {
        return getFastAdapter().getPreItemCountByOrder(getOrder()) + i;
    }

    public Item getAdapterItem(int i) {
        return (IItem) this.mItems.get(i);
    }

    public <T extends IItem & IExpandable<T, S>, S extends IItem & ISubItem<Item, T>> T setSubItems(T t, List<S> list) {
        if (this.mUseIdDistributor) {
            IdDistributor.checkIds(list);
        }
        return (IItem) ((IExpandable) t).withSubItems(list);
    }

    public ItemAdapter<Item> set(List<Item> list) {
        if (this.mUseIdDistributor) {
            IdDistributor.checkIds(list);
        }
        getFastAdapter().collapse(false);
        int size = list.size();
        int size2 = this.mItems.size();
        int preItemCountByOrder = getFastAdapter().getPreItemCountByOrder(getOrder());
        if (list != this.mItems) {
            if (!this.mItems.isEmpty()) {
                this.mItems.clear();
            }
            this.mItems.addAll(list);
        }
        mapPossibleTypes(list);
        if (this.mComparator != null) {
            Collections.sort(this.mItems, this.mComparator);
        }
        if (size > size2) {
            if (size2 > 0) {
                getFastAdapter().notifyAdapterItemRangeChanged(preItemCountByOrder, size2);
            }
            getFastAdapter().notifyAdapterItemRangeInserted(preItemCountByOrder + size2, size - size2);
        } else if (size > 0 && size < size2) {
            getFastAdapter().notifyAdapterItemRangeChanged(preItemCountByOrder, size);
            getFastAdapter().notifyAdapterItemRangeRemoved(preItemCountByOrder + size, size2 - size);
        } else if (size == 0) {
            getFastAdapter().notifyAdapterItemRangeRemoved(preItemCountByOrder, size2);
        } else {
            getFastAdapter().notifyAdapterDataSetChanged();
        }
        return this;
    }

    public ItemAdapter<Item> setNewList(List<Item> list) {
        if (this.mUseIdDistributor) {
            IdDistributor.checkIds(list);
        }
        this.mItems = new ArrayList(list);
        mapPossibleTypes(this.mItems);
        if (this.mComparator != null) {
            Collections.sort(this.mItems, this.mComparator);
        }
        getFastAdapter().notifyAdapterDataSetChanged();
        return this;
    }

    public void remapMappedTypes() {
        clearMappedTypes();
        mapPossibleTypes(this.mItems);
    }

    @SafeVarargs
    public final ItemAdapter<Item> add(Item... itemArr) {
        return add(Arrays.asList(itemArr));
    }

    public ItemAdapter<Item> add(List<Item> list) {
        if (this.mUseIdDistributor) {
            IdDistributor.checkIds(list);
        }
        int size = this.mItems.size();
        this.mItems.addAll(list);
        mapPossibleTypes(list);
        if (this.mComparator == null) {
            getFastAdapter().notifyAdapterItemRangeInserted(size + getFastAdapter().getPreItemCountByOrder(getOrder()), list.size());
        } else {
            Collections.sort(this.mItems, this.mComparator);
            getFastAdapter().notifyAdapterDataSetChanged();
        }
        return this;
    }

    @SafeVarargs
    public final ItemAdapter<Item> add(int i, Item... itemArr) {
        return add(i, Arrays.asList(itemArr));
    }

    public ItemAdapter<Item> add(int i, List<Item> list) {
        if (this.mUseIdDistributor) {
            IdDistributor.checkIds(list);
        }
        if (list != null && list.size() > 0) {
            this.mItems.addAll(i - getFastAdapter().getPreItemCountByOrder(getOrder()), list);
            mapPossibleTypes(list);
            getFastAdapter().notifyAdapterItemRangeInserted(i, list.size());
        }
        return this;
    }

    public ItemAdapter<Item> set(int i, Item item) {
        if (this.mUseIdDistributor) {
            IdDistributor.checkId(item);
        }
        this.mItems.set(i - getFastAdapter().getPreItemCount(i), item);
        mapPossibleType(item);
        getFastAdapter().notifyAdapterItemChanged(i);
        return this;
    }

    public ItemAdapter<Item> move(int i, int i2) {
        int preItemCount = getFastAdapter().getPreItemCount(i);
        IItem iItem = (IItem) this.mItems.get(i - preItemCount);
        this.mItems.remove(i - preItemCount);
        this.mItems.add(i2 - preItemCount, iItem);
        getFastAdapter().notifyAdapterItemMoved(i, i2);
        return this;
    }

    public ItemAdapter<Item> remove(int i) {
        this.mItems.remove(i - getFastAdapter().getPreItemCount(i));
        getFastAdapter().notifyAdapterItemRemoved(i);
        return this;
    }

    public ItemAdapter<Item> removeRange(int i, int i2) {
        int size = this.mItems.size();
        int preItemCount = getFastAdapter().getPreItemCount(i);
        int min = Math.min(i2, (size - i) + preItemCount);
        for (size = 0; size < min; size++) {
            this.mItems.remove(i - preItemCount);
        }
        getFastAdapter().notifyAdapterItemRangeRemoved(i, min);
        return this;
    }

    public ItemAdapter<Item> clear() {
        int size = this.mItems.size();
        this.mItems.clear();
        getFastAdapter().notifyAdapterItemRangeRemoved(getFastAdapter().getPreItemCountByOrder(getOrder()), size);
        return this;
    }
}
