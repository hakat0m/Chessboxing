package com.gumtree.android.postad.promote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.postad.DraftFeature.Type;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import org.apache.commons.lang3.Validate;

public class PromotionInfoActivity extends BaseActivity {
    private static final String FEATURE_TYPE = "FEATURE_TYPE";
    @Bind({2131624059})
    ImageView image;
    @Bind({16908298})
    RecyclerView list;
    @Bind({2131624063})
    TextView title;

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$gumtree$android$postad$DraftFeature$Type = new int[Type.values().length];

        static {
            try {
                $SwitchMap$com$gumtree$android$postad$DraftFeature$Type[Type.BUMPED_UP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$gumtree$android$postad$DraftFeature$Type[Type.IN_SPOTLIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$gumtree$android$postad$DraftFeature$Type[Type.URGENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$gumtree$android$postad$DraftFeature$Type[Type.FEATURED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    class PromotionInfoAdapter extends Adapter<PromotionInfoViewHolder> {
        private final String[] data;

        public PromotionInfoAdapter(@NonNull String[] strArr) {
            this.data = (String[]) Validate.notNull(strArr);
        }

        public PromotionInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new PromotionInfoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(2130903313, viewGroup, false));
        }

        public void onBindViewHolder(PromotionInfoViewHolder promotionInfoViewHolder, int i) {
            promotionInfoViewHolder.fillWith(this.data[i]);
        }

        public int getItemCount() {
            return this.data.length;
        }
    }

    public static Intent createIntent(@NonNull Context context, @NonNull Type type) {
        return new Intent(context, PromotionInfoActivity.class).putExtra(FEATURE_TYPE, type);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Type type = (Type) getIntent().getSerializableExtra(FEATURE_TYPE);
        setContentView(2130903103);
        ButterKnife.bind(this);
        this.title.setText(calculateTitle(type));
        this.image.setImageResource(calculateImage(type));
        this.list.setAdapter(new PromotionInfoAdapter(getResources().getStringArray(calculateLines(type))));
        this.list.setLayoutManager(new LinearLayoutManager(this));
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @StringRes
    public int calculateTitle(@NonNull Type type) {
        switch (AnonymousClass1.$SwitchMap$com$gumtree$android$postad$DraftFeature$Type[type.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return 2131165823;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return 2131165898;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return 2131165912;
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return 2131165858;
            default:
                return 2131165742;
        }
    }

    @DrawableRes
    public int calculateImage(@NonNull Type type) {
        switch (AnonymousClass1.$SwitchMap$com$gumtree$android$postad$DraftFeature$Type[type.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return 2130837951;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return 2130837953;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return 2130837954;
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return 2130837952;
            default:
                return 2130837627;
        }
    }

    @ArrayRes
    public int calculateLines(@NonNull Type type) {
        switch (AnonymousClass1.$SwitchMap$com$gumtree$android$postad$DraftFeature$Type[type.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return 2131558400;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return 2131558402;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return 2131558404;
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return 2131558401;
            default:
                return 2131558403;
        }
    }
}
