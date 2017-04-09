package com.ebay.kleinanzeigen.imagepicker.image_editing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePicker;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.ebay.kleinanzeigen.imagepicker.R$layout;
import com.ebay.kleinanzeigen.imagepicker.R$string;
import com.ebay.kleinanzeigen.imagepicker.image_library.Image;
import com.ebay.kleinanzeigen.imagepicker.image_library.ImageData;
import com.ebay.kleinanzeigen.imagepicker.platform.DepthPageTransformer;
import com.ebay.kleinanzeigen.imagepicker.platform.DoneActivity;
import com.ebay.kleinanzeigen.imagepicker.platform.ImageLoaderFactory;
import com.ebay.kleinanzeigen.imagepicker.platform.ImageUtils;
import com.ebay.kleinanzeigen.imagepicker.storage.ImageStorage;
import com.ebay.kleinanzeigen.imagepicker.storage.ImageStorageImpl;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.soundcloud.android.crop.Crop;
import java.util.ArrayList;
import java.util.List;
import uk.co.senab.photoview.IPhotoView;

public class ImageGalleryActivity extends DoneActivity {
    private static final String BITMAP = "BITMAP";
    private static final String BITMAP_INDEX = "BITMAP_INDEX";
    private static final String COUNTER_TEXT = "COUNTER_TEXT";
    private static final String DEGREES_TO_ROTATE = "DEGREES_TO_ROTATE";
    private static final String SETTLED = "SETTLED";
    private ImageButton cropButton;
    private int degreesToRotate;
    private ImageButton deleteButton;
    private boolean finishing = false;
    private int galleryPosition;
    private ArrayList<Image> imageList;
    private int imageLongestDimension;
    private ImageStorage imageStorage;
    private Bitmap lastRotatedBitmap;
    private int lastRotatedBitmapIndex;
    private ViewPager pager;
    private ImageGalleryPagerAdapter pagerAdapter;
    private AnimationListener rotateAnimationListener = new AnimationListener() {
        public void onAnimationStart(Animation animation) {
            ImageGalleryActivity.this.setToolbarItemsEnabled(false);
        }

        public void onAnimationEnd(Animation animation) {
            ImageGalleryActivity.this.setToolbarItemsEnabled(true);
        }

        public void onAnimationRepeat(Animation animation) {
        }
    };
    private ImageButton rotateButton;
    private Button setAsGalleryButton;
    private boolean settled = true;

    class ImageGalleryPageListener implements OnPageChangeListener {
        private float lastOffset;

        private ImageGalleryPageListener() {
            this.lastOffset = 0.0f;
        }

        private int getIndex(float f, float f2, int i) {
            return f < f2 ? i : i + 1;
        }

        public void onPageScrolled(int i, float f, int i2) {
            if (!(this.lastOffset == 0.0f || ImageGalleryActivity.this.degreesToRotate == 0)) {
                ImageGalleryActivity.this.saveRotations(getIndex(this.lastOffset, f, i));
            }
            this.lastOffset = f;
        }

        public void onPageSelected(int i) {
        }

        public void onPageScrollStateChanged(int i) {
            switch (i) {
                case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                    ImageGalleryActivity.this.setItemCounterText();
                    ImageGalleryActivity.this.settled = true;
                    ImageGalleryActivity.this.setToolbarItemsEnabled(true);
                    ImageGalleryActivity.this.showCurrentImageIfInvisible();
                    return;
                case HighlightView.GROW_NONE /*1*/:
                    if (ImageGalleryActivity.this.settled) {
                        ImageGalleryActivity.this.setToolbarItemsEnabled(false);
                    }
                    ImageGalleryActivity.this.settled = false;
                    return;
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    ImageGalleryActivity.this.setItemCounterText();
                    ImageGalleryActivity.this.showCurrentImageIfInvisible();
                    return;
                default:
                    return;
            }
        }
    }

    public class ImageSizeCalculator {
        private ImageView imageView;
        private ImageView imageViewRotated;
        private float normalRealHeight;
        private float rotatedRealHeight;

        public ImageSizeCalculator(ImageView imageView, ImageView imageView2) {
            this.imageView = imageView;
            this.imageViewRotated = imageView2;
        }

        public float getNormalRealHeight() {
            return this.normalRealHeight;
        }

        public float getRotatedRealHeight() {
            return this.rotatedRealHeight;
        }

        public ImageSizeCalculator invoke() {
            float[] fArr = new float[9];
            this.imageView.getImageMatrix().getValues(fArr);
            float[] fArr2 = new float[9];
            this.imageViewRotated.getImageMatrix().getValues(fArr2);
            this.normalRealHeight = fArr[4] * ((float) this.imageView.getHeight());
            this.rotatedRealHeight = fArr2[4] * ((float) this.imageViewRotated.getHeight());
            return this;
        }
    }

    class SaveRotationsTask extends AsyncTask<Void, Void, Void> {
        private int degrees;
        private int index;

        SaveRotationsTask(int i, int i2) {
            this.index = i;
            this.degrees = i2;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            ImageGalleryActivity.this.showLoadingIndicator(this.index, true);
            ((Image) ImageGalleryActivity.this.imageList.get(this.index)).getData().setSaving(true);
        }

        protected Void doInBackground(Void... voidArr) {
            ImageGalleryActivity.this.saveImage(this.index, this.degrees);
            return null;
        }

        protected void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            ((Image) ImageGalleryActivity.this.imageList.get(this.index)).getData().setSaving(false);
            ImageGalleryActivity.this.showLoadingIndicator(this.index, false);
        }
    }

    class ToolbarButtonClickListener implements OnClickListener {
        private ToolbarButtonClickListener() {
        }

        public void onClick(View view) {
            int id = view.getId();
            if (ImageGalleryActivity.this.settled && !ImageGalleryActivity.this.finishing) {
                if (id == R$id.action_button_rotate || id == R$id.action_button_crop) {
                    if (id == R$id.action_button_rotate) {
                        ImageGalleryActivity.this.handleRotate();
                    } else {
                        ImageGalleryActivity.this.handleCrop();
                    }
                } else if (id == R$id.action_button_set_as_gallery) {
                    ImageGalleryActivity.this.handleSetAsGallery();
                } else if (id == R$id.action_button_delete) {
                    ImageGalleryActivity.this.handleDelete();
                }
            }
        }
    }

    public static Intent createIntent(Context context, int i, List<Image> list, String str, boolean z, int i2) {
        Intent intent = new Intent(context, ImageGalleryActivity.class);
        intent.putExtra("STARTING_POSITION", i);
        intent.putExtra("EXTRA_IMAGE_LIST", new ArrayList(list));
        intent.putExtra("STORAGE_DIR", str);
        intent.putExtra("GALLERY_ENABLED", z);
        intent.putExtra("LONGEST_DIMENSION", i2);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.activity_image_gallery);
        getSupportActionBar().setTitle(BuildConfig.FLAVOR);
        initToolbar();
        this.imageStorage = new ImageStorageImpl(getIntent().getStringExtra("STORAGE_DIR"));
        this.imageLongestDimension = getIntent().getIntExtra("LONGEST_DIMENSION", EbkImagePicker.DEFAULT_LONGEST_IMAGE_DIMENSION);
        getItemCounterView().setVisibility(0);
        if (getIntent().getSerializableExtra("EXTRA_IMAGE_LIST") != null) {
            this.imageList = (ArrayList) getIntent().getSerializableExtra("EXTRA_IMAGE_LIST");
        }
        initViewPager();
        setItemCounterText();
        if (bundle != null) {
            getSavedData(bundle);
        }
    }

    private void initToolbar() {
        this.rotateButton = (ImageButton) findViewById(R$id.action_button_rotate);
        this.cropButton = (ImageButton) findViewById(R$id.action_button_crop);
        this.deleteButton = (ImageButton) findViewById(R$id.action_button_delete);
        this.setAsGalleryButton = (Button) findViewById(R$id.action_button_set_as_gallery);
        this.setAsGalleryButton.setVisibility(getIntent().getBooleanExtra("GALLERY_ENABLED", true) ? 0 : 4);
        this.rotateButton.setOnClickListener(new ToolbarButtonClickListener());
        this.cropButton.setOnClickListener(new ToolbarButtonClickListener());
        this.deleteButton.setOnClickListener(new ToolbarButtonClickListener());
        this.setAsGalleryButton.setOnClickListener(new ToolbarButtonClickListener());
    }

    private void getSavedData(Bundle bundle) {
        this.galleryPosition = bundle.getInt("STARTING_POSITION");
        this.lastRotatedBitmap = (Bitmap) bundle.getParcelable(BITMAP);
        this.lastRotatedBitmapIndex = bundle.getInt(BITMAP_INDEX);
        this.degreesToRotate = bundle.getInt(DEGREES_TO_ROTATE);
        this.settled = bundle.getBoolean(SETTLED);
        getItemCounterView().setText(bundle.getString(COUNTER_TEXT));
        setItemAsGalleryImage(this.galleryPosition);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        saveImageIfRotated();
        bundle.putInt("STARTING_POSITION", this.galleryPosition);
        bundle.putParcelable(BITMAP, this.lastRotatedBitmap);
        bundle.putInt(BITMAP_INDEX, this.lastRotatedBitmapIndex);
        bundle.putInt(DEGREES_TO_ROTATE, this.degreesToRotate);
        bundle.putBoolean(SETTLED, this.settled);
        bundle.putString(COUNTER_TEXT, getCounterText());
    }

    private TextView getItemCounterView() {
        return (TextView) findViewById(R$id.done_bar_text_view);
    }

    private void setItemCounterText() {
        getItemCounterView().setText((this.pager.getCurrentItem() + 1) + " " + getString(R$string.ebk_image_picker_of) + " " + this.imageList.size());
    }

    private String getCounterText() {
        return getItemCounterView().getText().toString();
    }

    private void initViewPager() {
        if (this.imageList != null && !this.imageList.isEmpty()) {
            setItemAsGalleryImage(0);
            this.pager = (ViewPager) findViewById(R$id.image_pager);
            this.pager.setAdapter(createPagerAdapter());
            this.pager.setPageTransformer(true, new DepthPageTransformer());
            this.pager.setCurrentItem(getIntent().getIntExtra("STARTING_POSITION", 0));
            this.pager.setOnPageChangeListener(new ImageGalleryPageListener());
        }
    }

    private void handleDelete() {
        setToolbarItemsEnabled(false);
        int currentItem = this.pager.getCurrentItem();
        if (!this.imageList.isEmpty()) {
            if (((Image) this.imageList.get(currentItem)).isGalleryImage()) {
                r0 = this.galleryPosition > 0 ? currentItem - 1 : this.imageList.size() > 1 ? currentItem + 1 : currentItem;
                setItemAsGalleryImage(r0);
            } else if (this.galleryPosition > currentItem) {
                setItemAsGalleryImage(this.galleryPosition - 1);
            }
            this.imageStorage.deleteImage(((Image) this.imageList.get(currentItem)).getModifiedFilePath());
            this.imageList.remove(currentItem);
            this.pager.setAdapter(createPagerAdapter());
            if (this.imageList.isEmpty()) {
                setToolbarItemsEnabled(true);
                setResultAndFinish();
                return;
            }
            this.pager.setCurrentItem(currentItem - 1);
            setItemCounterText();
        }
        setToolbarItemsEnabled(true);
    }

    private void handleSetAsGallery() {
        int currentItem = this.pager.getCurrentItem();
        if (!((Image) this.imageList.get(currentItem)).isGalleryImage()) {
            if (this.degreesToRotate != 0) {
                saveImage(this.pager.getCurrentItem(), this.degreesToRotate);
            }
            setItemAsGalleryImage(currentItem);
            this.pager.setAdapter(createPagerAdapter());
            this.pager.setCurrentItem(currentItem);
        }
    }

    private void handleRotate() {
        setToolbarItemsEnabled(false);
        rotateView();
    }

    private void handleCrop() {
        createModifiedPathIfNecessary(this.pager.getCurrentItem());
        ImageLoaderFactory.clearCaches();
        saveImageIfRotated();
        Crop.of(Uri.parse("file://" + ((Image) this.imageList.get(this.pager.getCurrentItem())).getModifiedFilePath()), Uri.parse("file://" + ((Image) this.imageList.get(this.pager.getCurrentItem())).getModifiedFilePath())).start(this);
    }

    private void showCurrentImageIfInvisible() {
        int currentItem = this.pager.getCurrentItem();
        if (this.pager.findViewWithTag(Integer.valueOf(currentItem)).findViewById(R$id.item_image).getVisibility() == 4) {
            this.pager.setAdapter(createPagerAdapter());
            this.pager.setCurrentItem(currentItem);
        }
    }

    private void setItemAsGalleryImage(int i) {
        for (int i2 = 0; i2 < this.imageList.size(); i2++) {
            boolean z;
            Image image = (Image) this.imageList.get(i2);
            if (i == i2) {
                z = true;
            } else {
                z = false;
            }
            image.setGalleryImage(z);
        }
        this.galleryPosition = i;
    }

    private ImageGalleryPagerAdapter createPagerAdapter() {
        this.pagerAdapter = new ImageGalleryPagerAdapter(this.imageList, this, ImageLoaderFactory.getImageLoader(this), getIntent().getBooleanExtra("GALLERY_ENABLED", true), getIntent().getIntExtra("LONGEST_DIMENSION", EbkImagePicker.DEFAULT_LONGEST_IMAGE_DIMENSION));
        return this.pagerAdapter;
    }

    protected void onDone() {
        ImageLoaderFactory.clearCaches();
        handleDone();
    }

    private void handleDone() {
        saveImageIfRotated();
        setResultAndFinish();
    }

    public void onBackPressed() {
        handleDone();
    }

    private void saveImageIfRotated() {
        if (imageShouldBeSaved(this.pager.getCurrentItem())) {
            saveImage(this.pager.getCurrentItem(), this.degreesToRotate);
        }
    }

    private void saveRotations(int i) {
        if (imageShouldBeSaved(i)) {
            new SaveRotationsTask(i, this.degreesToRotate).execute(new Void[0]);
            this.degreesToRotate = 0;
        }
    }

    private boolean imageShouldBeSaved(int i) {
        boolean z;
        boolean z2 = false;
        if (this.degreesToRotate == 0 || this.imageList.size() <= i || ((Image) this.imageList.get(i)).getData().isSaving()) {
            z = false;
        } else {
            z = true;
        }
        Image image = (Image) this.imageList.get(i);
        if (((Image) this.imageList.get(i)).isImageModifiedByUser() || z) {
            z2 = true;
        }
        image.setImageModifiedByUser(z2);
        return z;
    }

    private void saveImage(int i, int i2) {
        createModifiedPathIfNecessary(i);
        loadBitmapForRotation(i);
        this.lastRotatedBitmap = ImageUtils.rotateBitmap(this.lastRotatedBitmap, i2);
        this.degreesToRotate = 0;
        if (this.imageList.size() > i && ((Image) this.imageList.get(i)).equals(this.imageList.get(i))) {
            ((Image) this.imageList.get(i)).setModifiedFilePath(this.imageStorage.storeImage(this.lastRotatedBitmap, Uri.parse("file://" + ((Image) this.imageList.get(i)).getModifiedFilePath()).getLastPathSegment(), true, false));
        }
        ImageLoaderFactory.clearCaches();
    }

    private void showLoadingIndicator(int i, boolean z) {
        if (this.pager.findViewWithTag(Integer.valueOf(i)) != null && this.pager.findViewWithTag(Integer.valueOf(i)).findViewById(R$id.loading_view) != null) {
            this.pager.findViewWithTag(Integer.valueOf(i)).findViewById(R$id.loading_view).setVisibility(z ? 0 : 8);
        }
    }

    private void createModifiedPathIfNecessary(int i) {
        if (this.imageList.size() > i && TextUtils.isEmpty(((Image) this.imageList.get(i)).getModifiedFilePath())) {
            ((Image) this.imageList.get(i)).setModifiedFilePath(this.imageStorage.makeCopyForModification(this.pagerAdapter.getItem(i).getOriginalFilePath()));
        }
    }

    private void setResultAndFinish() {
        this.finishing = true;
        Intent intent = new Intent();
        setGalleryImageAsFirstItem();
        if (this.lastRotatedBitmap != null) {
            this.lastRotatedBitmap.recycle();
            this.lastRotatedBitmap = null;
        }
        intent.putExtra("EXTRA_IMAGE_LIST", this.imageList);
        setResult(-1, intent);
        finish();
    }

    private void setGalleryImageAsFirstItem() {
        if (!this.imageList.isEmpty() && this.galleryPosition >= 0 && this.galleryPosition < this.imageList.size() && !((Image) this.imageList.get(0)).isGalleryImage()) {
            this.imageList.add(0, this.imageList.remove(this.galleryPosition));
        }
    }

    private void setToolbarItemsEnabled(boolean z) {
        this.settled = z;
        this.rotateButton.setEnabled(z);
        this.cropButton.setEnabled(z);
        this.deleteButton.setEnabled(z);
        this.setAsGalleryButton.setEnabled(z);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == Crop.REQUEST_CROP && i2 == -1) {
            ImageLoaderFactory.clearCaches();
            int currentItem = this.pager.getCurrentItem();
            this.pager.setAdapter(createPagerAdapter());
            this.pager.setCurrentItem(currentItem);
            ((Image) this.imageList.get(currentItem)).setImageModifiedByUser(true);
        }
        super.onActivityResult(i, i2, intent);
    }

    private ScaleAnimation getScaleAnimation(ImageView imageView) {
        float f;
        float f2 = IPhotoView.DEFAULT_MIN_SCALE;
        ImageSizeCalculator invoke = new ImageSizeCalculator(imageView, (ImageView) this.pager.findViewWithTag(Integer.valueOf(this.pager.getCurrentItem())).findViewById(R$id.item_image_rotated)).invoke();
        ImageData data = ((Image) this.imageList.get(this.pager.getCurrentItem())).getData();
        if (data.getNormalHeight() == 0.0f && data.getRotatedHeight() == 0.0f) {
            data.setRotatedHeight(invoke.getRotatedRealHeight());
            data.setNormalHeight(invoke.getNormalRealHeight());
        }
        if (data.getLastDegreestoRotate() == -1) {
            data.setLastDegreestoRotate(this.degreesToRotate);
        }
        if (data.getLastDegreestoRotate() == 90 || data.getLastDegreestoRotate() == 270) {
            if (data.getRotatedHeight() != data.getNormalHeight()) {
                float rotatedHeight = data.getRotatedHeight() / data.getNormalHeight();
                f = IPhotoView.DEFAULT_MIN_SCALE;
                f2 = rotatedHeight;
            }
            f = IPhotoView.DEFAULT_MIN_SCALE;
        } else {
            if (data.getRotatedHeight() != data.getNormalHeight()) {
                f = data.getRotatedHeight() / data.getNormalHeight();
            }
            f = IPhotoView.DEFAULT_MIN_SCALE;
        }
        data.setLastDegreestoRotate(data.getLastDegreestoRotate() == 270 ? 0 : data.getLastDegreestoRotate() + 90);
        return new ScaleAnimation(f2, f, f2, f, 1, 0.5f, 1, 0.5f);
    }

    private void rotateView() {
        ImageView imageView = (ImageView) this.pager.findViewWithTag(Integer.valueOf(this.pager.getCurrentItem())).findViewById(R$id.item_image);
        float rotation = imageView.getRotation() + 90.0f;
        Animation animationSet = new AnimationSet(true);
        Animation scaleAnimation = getScaleAnimation(imageView);
        Animation rotateAnimation = new RotateAnimation(-90.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setAnimationListener(this.rotateAnimationListener);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setFillAfter(true);
        animationSet.setDuration(150);
        animationSet.setInterpolator(this, 17432587);
        imageView.startAnimation(animationSet);
        imageView.setRotation(rotation);
        this.degreesToRotate = this.degreesToRotate == 270 ? 0 : this.degreesToRotate + 90;
    }

    private void loadBitmapForRotation(int i) {
        if (this.lastRotatedBitmap == null || this.lastRotatedBitmapIndex != i) {
            this.lastRotatedBitmap = ImageLoaderFactory.getImageLoader(this).loadImageSync("file://" + ((Image) this.imageList.get(i)).getModifiedFilePath(), new ImageSize(this.imageLongestDimension, this.imageLongestDimension));
            this.lastRotatedBitmapIndex = i;
        }
    }
}
