package com.gumtree.android.srp.treebay;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.apptentive.android.sdk.BuildConfig;
import com.bumptech.glide.Glide;
import com.gumtree.android.ad.treebay.TreebayAdvertItem;
import com.gumtree.android.ad.treebay.services.TrackingTreebayService;
import com.gumtree.android.api.treebay.ItemLocation;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.vip_treebay.VIPActivityTreebay;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

abstract class AbstractTreebayAdItemView extends FrameLayout implements TreebayAdvertItem {
    private static final String GB = "GB";
    private static final Pattern LONG_OUTCODE_PATTERN = Pattern.compile("([A-Z]{1,2}[0-9R][0-9A-Z]?).*");
    private static final Pattern NORMALISE_PATTERN = Pattern.compile("([A-Z]{1,2}[0-9R][0-9A-Z]?)([0-9][A-Z-[CIKMOV]]{2})");
    private static final Pattern OUTCODE_PATTERN = Pattern.compile("[A-Z]{1,2}[0-9R][0-9A-Z]?");
    private static final Pattern POSTCODE_PATTERN = Pattern.compile("[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][A-Z-[CIKMOV]]{2}");
    private static final String SEPARATOR = ", ";
    private static final String UNITED_KINGDOM = "United Kingdom";
    private static final String ZERO_PENCE = ".00";
    @Bind({2131624862})
    TextView description;
    @Bind({2131624859})
    View dummy;
    @Bind({2131624861})
    View dummy2;
    @Bind({2131624649})
    View ebayLogo;
    private boolean hasContent;
    private String id;
    @Bind({2131624856})
    ImageView image;
    @Bind({2131624858})
    TextView location;
    private int position;
    @Bind({2131624860})
    TextView price;
    private int relativePosition;
    @Bind({2131624855})
    View root;
    private Object search;
    @Bind({2131624857})
    TextView title;
    @Inject
    TrackingTreebayService trackingService;
    private String treebayItemUrl;

    protected abstract String getImageUrl(String str);

    @LayoutRes
    protected abstract int getLayout();

    public AbstractTreebayAdItemView(Context context) {
        super(context);
        init(context);
    }

    public AbstractTreebayAdItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public AbstractTreebayAdItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    protected void init(Context context) {
        LayoutInflater.from(context).inflate(getLayout(), this);
        ButterKnife.bind(this);
        this.root.setOnClickListener(AbstractTreebayAdItemView$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$init$0(View view) {
        if (this.hasContent) {
            this.trackingService.eventTreebayResultsAdClick(this.id, this.relativePosition);
            getContext().startActivity(VIPActivityTreebay.createIntent(getContext(), this.id, false, true, (Search) this.search, 0, 0, this.treebayItemUrl));
            this.trackingService.screenViewPVip(this.id);
        }
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setTitle(String str) {
        this.title.setText(str.trim());
    }

    public void setPrice(String str) {
        if (TextUtils.isEmpty(str)) {
            this.price.setVisibility(8);
            return;
        }
        this.price.setText(formatPrice(str));
        this.price.setVisibility(0);
    }

    public void setLocation(@Nullable ItemLocation itemLocation) {
        if (this.location != null) {
            int i = itemLocation != null ? 1 : 0;
            String str = null;
            if (!(i == 0 || itemLocation.getPostalCode() == null)) {
                str = normalisePostcode(itemLocation.getPostalCode());
            }
            if (str != null) {
                str = parseLongOutcode(str);
            }
            CharSequence countryName = getCountryName(i != 0 ? itemLocation.getCountry() : BuildConfig.FLAVOR);
            CharSequence city = i != 0 ? itemLocation.getCity() : BuildConfig.FLAVOR;
            if (StringUtils.isEmpty(city)) {
                city = str;
            }
            TextView textView = this.location;
            if (!StringUtils.isEmpty(countryName)) {
                city = String.format("%s%s%s", new Object[]{city, SEPARATOR, countryName});
            }
            textView.setText(city);
        }
    }

    public void setDescription(String str) {
        this.description.setText(str);
    }

    public void loadImage(String str) {
        if (TextUtils.isEmpty(str)) {
            this.image.setImageResource(2130837949);
        } else {
            Glide.with(getContext()).load(getImageUrl(str)).asBitmap().centerCrop().placeholder(2130837633).error(2130837638).into(this.image);
        }
    }

    public void setVisible(boolean z) {
        this.root.setVisibility(z ? 0 : 8);
    }

    public void hideDescription() {
    }

    public void showContent() {
        this.hasContent = true;
        this.root.setVisibility(0);
        this.ebayLogo.setVisibility(0);
        this.price.setVisibility(0);
        this.dummy.setVisibility(8);
        this.dummy2.setVisibility(8);
        this.title.setBackgroundDrawable(null);
        this.price.setBackgroundDrawable(null);
        setContentDescription(String.format("Treebay_ad_%d", new Object[]{Integer.valueOf(this.position)}));
    }

    public void hideAd() {
        this.hasContent = false;
        this.root.setVisibility(8);
        setContentDescription(String.format("Treebay_placeholder_%d", new Object[]{Integer.valueOf(this.position)}));
    }

    public void vipPayload(Object obj) {
        this.search = obj;
    }

    public void setTreebayItemUrl(String str) {
        this.treebayItemUrl = str;
    }

    public void setAbsolutePosition(int i) {
        this.position = i;
    }

    public void setRelativePosition(int i) {
        this.relativePosition = i;
    }

    private String formatPrice(@NonNull String str) {
        try {
            String format = NumberFormat.getCurrencyInstance(Locale.UK).format(Double.valueOf(str));
            if (!format.endsWith(ZERO_PENCE)) {
                return format;
            }
            int lastIndexOf = format.lastIndexOf(ZERO_PENCE);
            if (lastIndexOf != -1) {
                return format.substring(0, lastIndexOf);
            }
            return format;
        } catch (Throwable e) {
            Timber.d(e, "Error formatting Treebay price value %s", str);
            return null;
        }
    }

    private static String parseLongOutcode(@NonNull String str) {
        Matcher matcher = LONG_OUTCODE_PATTERN.matcher(str);
        return matcher.matches() ? matcher.group(1) : BuildConfig.FLAVOR;
    }

    private static boolean isPostcode(@NonNull String str) {
        return POSTCODE_PATTERN.matcher(str).matches() || OUTCODE_PATTERN.matcher(str).matches();
    }

    private static String normalisePostcode(@NonNull String str) {
        if (isPostcode(str)) {
            return str;
        }
        Matcher matcher = NORMALISE_PATTERN.matcher(str);
        return matcher.matches() ? matcher.group(1) + ' ' + matcher.group(2) : null;
    }

    private String getCountryName(String str) {
        return GB.equals(str) ? UNITED_KINGDOM : BuildConfig.FLAVOR;
    }
}
