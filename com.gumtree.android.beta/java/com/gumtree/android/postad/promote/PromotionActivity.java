package com.gumtree.android.postad.promote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.DraftFeature.Type;
import com.gumtree.android.postad.di.DaggerPromotionComponent;
import com.gumtree.android.postad.di.PromotionComponent;
import com.gumtree.android.postad.promote.PromotionPresenter.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.lang3.math.NumberUtils;

public class PromotionActivity extends BaseActivity {
    private List<Type> activeFeatures;
    @Nullable
    private String adId;
    private PromotionAdapter adapter;
    private String categoryId;
    private int imageCount;
    @Bind({2131624217})
    RecyclerView list;
    private long locationId;
    @Inject
    PromotionPresenter presenter;
    private List<PromotionFeature> selectedFeatures;
    private String sellerType;
    private final View viewDelegate = new ViewDelegate();

    class ViewDelegate implements View {
        private final List<Type> DISPLAY_ORDER;

        private ViewDelegate() {
            this.DISPLAY_ORDER = Arrays.asList(new Type[]{Type.BUMPED_UP, Type.FEATURED, Type.URGENT, Type.IN_SPOTLIGHT});
        }

        public void showFeatures(List<PromotionFeature> list) {
            List arrayList = new ArrayList(list);
            Collections.sort(arrayList, PromotionActivity$ViewDelegate$$Lambda$1.lambdaFactory$(this));
            PromotionActivity.this.adapter.setData(arrayList);
        }

        /* synthetic */ int lambda$showFeatures$0(PromotionFeature promotionFeature, PromotionFeature promotionFeature2) {
            return NumberUtils.compare(extractType(promotionFeature), extractType(promotionFeature2));
        }

        private int extractType(PromotionFeature promotionFeature) {
            return this.DISPLAY_ORDER.indexOf(promotionFeature.getFeature().getType());
        }

        public void cancelOnError(String str) {
            Toast.makeText(PromotionActivity.this, str, 0).show();
            PromotionActivity.this.setResult(0);
            PromotionActivity.this.finish();
        }

        public void updateSelection(@NonNull List<PromotionFeature> list) {
            PromotionActivity.this.setResult(-1, new IntentBuilderAnalyser().createResult(list));
        }

        public void promptInfo(Type type) {
            PromotionActivity.this.startActivity(PromotionInfoActivity.createIntent(PromotionActivity.this, type));
        }
    }

    public static Intent createIntent(Context context, DraftAd draftAd) {
        return IntentBuilderAnalyser.createIntent(context, draftAd);
    }

    private void injectParameters() {
        Intent intent = getIntent();
        IntentBuilderAnalyser intentBuilderAnalyser = new IntentBuilderAnalyser();
        this.adId = intentBuilderAnalyser.extractAdId(intent);
        this.categoryId = intentBuilderAnalyser.extractCategoryId(intent);
        this.locationId = intentBuilderAnalyser.extractLocationId(intent);
        this.activeFeatures = intentBuilderAnalyser.extractActiveFeatures(intent);
        this.selectedFeatures = intentBuilderAnalyser.extractSelectedFeatures(intent);
        this.imageCount = intentBuilderAnalyser.extractImageCount(intent);
        this.sellerType = intentBuilderAnalyser.extractSellerType(intent);
    }

    @NonNull
    public static List<PromotionFeature> analyse(@NonNull Intent intent) {
        return IntentBuilderAnalyser.analyse(intent);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        injectParameters();
        getComponent().inject(this);
        setContentView(2130903102);
        ButterKnife.bind(this);
        this.adapter = new PromotionAdapter(this.presenter);
        this.list.setAdapter(this.adapter);
        this.list.setLayoutManager(new LinearLayoutManager(this));
        if (bundle == null) {
            this.presenter.onLoadFeatures(this.adId, this.categoryId, this.locationId, this.selectedFeatures, this.activeFeatures, this.imageCount, this.sellerType);
        }
        this.presenter.onRefreshView();
    }

    protected void onDestroy() {
        if (isFinishing()) {
            this.presenter.destroy();
            ComponentsManager.get().removeBaseComponent(PromotionComponent.KEY);
        }
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this.viewDelegate);
    }

    protected void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    private PromotionComponent getComponent() {
        PromotionComponent promotionComponent = (PromotionComponent) ComponentsManager.get().getBaseComponent(PromotionComponent.KEY);
        if (promotionComponent != null) {
            return promotionComponent;
        }
        Object build = DaggerPromotionComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).build();
        ComponentsManager.get().putBaseComponent(PromotionComponent.KEY, build);
        return build;
    }

    public void finish() {
        this.presenter.onViewCloses();
        super.finish();
    }

    protected boolean isHomeAsUp() {
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755014, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624936:
                finish();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
