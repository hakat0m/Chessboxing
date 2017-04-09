package com.ebay.kleinanzeigen.imagepicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.kleinanzeigen.imagepicker.drag_and_drop.SelectedImagesFragment;
import com.ebay.kleinanzeigen.imagepicker.drag_and_drop.SelectedImagesFragment.ImageSelector;
import com.ebay.kleinanzeigen.imagepicker.image_library.Image;
import com.ebay.kleinanzeigen.imagepicker.image_library.ImageLibraryFoldersActivity;
import com.ebay.kleinanzeigen.imagepicker.permissions.PermissionResponseReceiver;
import com.ebay.kleinanzeigen.imagepicker.permissions.PermissionsDialogFragment;
import com.ebay.kleinanzeigen.imagepicker.platform.ImageLoaderFactory;
import com.ebay.kleinanzeigen.imagepicker.platform.ImageUtils;
import com.ebay.kleinanzeigen.imagepicker.platform.LOG;
import com.ebay.kleinanzeigen.imagepicker.storage.ImageStorage;
import com.ebay.kleinanzeigen.imagepicker.storage.ImageStorageImpl;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class EbkImagePickerActivity extends AppCompatActivity implements ImageSelector {
    private static final String CURRENT_FLASH_MODE = "CURRENT_FLASH_MODE";
    private static final String IMAGES_ARRAY = "IMAGES_ARRAY";
    public static final int REQUEST_CODE_IMAGE_GALLERY = 101;
    public static final int REQUEST_CODE_IMAGE_LIBRARY = 100;
    private AccelerometerListener accelerometerListener;
    private Camera camera;
    private int cameraRotation;
    private ImageButton captureButton;
    private ImageButton changeFlashModeButton;
    private int currentCameraFacing = 0;
    private String currentFlashMode = "auto";
    private float degrees = 0.0f;
    private FrameLayout doneButton;
    private ImageStorage imageStorage;
    private ScreenOrientation orientation = ScreenOrientation.portrait;
    private PermissionResponseReceiver permissionResponseReceiver;
    private int permissionText;
    private ImageButton photoLibraryButton;
    private ArrayList<Image> selectedImages;
    private SensorManager sensorManager;
    private boolean shouldFinishAfterSaving = false;
    private boolean shouldShowPermissionDialog = false;
    private SurfaceView surfaceView;
    private ImageButton switchCameraButton;
    private View takePicEffectView;
    private boolean takingPicture = false;
    private int titleText;

    class AccelerometerListener implements SensorEventListener {
        private boolean viewsRotatedMinusNinety;
        private boolean viewsRotatedNinety;
        private boolean viewsRotatedZero;

        private AccelerometerListener() {
            this.viewsRotatedNinety = false;
            this.viewsRotatedMinusNinety = false;
            this.viewsRotatedZero = false;
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == 1) {
                float f = sensorEvent.values[0];
                if (f > 8.0f) {
                    if (!this.viewsRotatedNinety) {
                        EbkImagePickerActivity.this.rotateViews(90.0f);
                        this.viewsRotatedNinety = true;
                        this.viewsRotatedMinusNinety = false;
                        this.viewsRotatedZero = false;
                        EbkImagePickerActivity.this.orientation = ScreenOrientation.landscapePlus;
                    }
                } else if (f < -8.0f) {
                    if (!this.viewsRotatedMinusNinety) {
                        EbkImagePickerActivity.this.rotateViews(-90.0f);
                        this.viewsRotatedNinety = false;
                        this.viewsRotatedMinusNinety = true;
                        this.viewsRotatedZero = false;
                        EbkImagePickerActivity.this.orientation = ScreenOrientation.landscapeMinus;
                    }
                } else if (f < 7.0f && f > -7.0f && !this.viewsRotatedZero) {
                    EbkImagePickerActivity.this.rotateViews(0.0f);
                    this.viewsRotatedNinety = false;
                    this.viewsRotatedMinusNinety = false;
                    this.viewsRotatedZero = true;
                    EbkImagePickerActivity.this.orientation = ScreenOrientation.portrait;
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }

    class ButtonClickListener implements OnClickListener {
        private ButtonClickListener() {
        }

        public void onClick(View view) {
            if (view.getId() == EbkImagePickerActivity.this.photoLibraryButton.getId()) {
                EbkImagePickerActivity.this.startActivityForResult(ImageLibraryFoldersActivity.createIntent(EbkImagePickerActivity.this, EbkImagePickerActivity.this.getIntent().getIntExtra("MAX_SELECTION", 1), EbkImagePickerActivity.this.getSelectedImages(true), EbkImagePickerActivity.this.getIntent().getStringExtra("STORAGE_DIR"), EbkImagePickerActivity.this.getIntent().getIntExtra("LONGEST_DIMENSION", EbkImagePicker.DEFAULT_LONGEST_IMAGE_DIMENSION)), EbkImagePickerActivity.REQUEST_CODE_IMAGE_LIBRARY);
            } else if (view.getId() == EbkImagePickerActivity.this.captureButton.getId()) {
                EbkImagePickerActivity.this.takePicture();
            } else if (view.getId() == EbkImagePickerActivity.this.doneButton.getId()) {
                EbkImagePickerActivity.this.setResultAndFinish();
            } else if (!EbkImagePickerActivity.this.takingPicture) {
                if (view.getId() == EbkImagePickerActivity.this.switchCameraButton.getId()) {
                    EbkImagePickerActivity.this.switchCamera();
                } else if (view.getId() == EbkImagePickerActivity.this.changeFlashModeButton.getId()) {
                    EbkImagePickerActivity.this.changeFlashMode();
                }
            }
        }
    }

    class CameraCallback implements PictureCallback {
        private CameraCallback() {
        }

        public void onPictureTaken(byte[] bArr, Camera camera) {
            EbkImagePickerActivity.this.takingPicture = false;
            EbkImagePickerActivity.this.takePicAnimation();
            camera.startPreview();
            EbkImagePickerActivity.this.selectedImages.add(new Image(null, true));
            EbkImagePickerActivity.this.getSelectedImagesFragment().recreateAdapter();
            EbkImagePickerActivity.this.getSelectedImagesFragment().smoothScrollRight();
            new SaveImageTask(EbkImagePickerActivity.this, (Image) EbkImagePickerActivity.this.selectedImages.get(EbkImagePickerActivity.this.selectedImages.size() - 1)).execute(new byte[][]{bArr});
        }
    }

    class EbkImagePickerPermissionReceiver implements PermissionResponseReceiver {
        private EbkImagePickerPermissionReceiver() {
        }

        public void onPermissionGranted() {
            int i;
            int i2 = 0;
            if (!EbkImagePickerActivity.this.safelyOpenCamera(EbkImagePickerActivity.this.currentCameraFacing)) {
                LOG.error("Failed to open Camera");
            }
            ImageButton access$600 = EbkImagePickerActivity.this.switchCameraButton;
            if (EbkImagePickerActivity.this.hasFrontCamera()) {
                i = 0;
            } else {
                i = 8;
            }
            access$600.setVisibility(i);
            ImageButton access$800 = EbkImagePickerActivity.this.changeFlashModeButton;
            if (!EbkImagePickerActivity.this.canHaveFlashSetting()) {
                i2 = 8;
            }
            access$800.setVisibility(i2);
        }

        public void onPermissionDenied() {
        }
    }

    enum ScreenOrientation {
        portrait,
        landscapePlus,
        landscapeMinus
    }

    class SurfaceCallback implements Callback {
        private SurfaceCallback() {
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            EbkImagePickerActivity.this.setCameraFocusMode();
            EbkImagePickerActivity.this.camera.startPreview();
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                EbkImagePickerActivity.this.camera.setPreviewDisplay(surfaceHolder);
            } catch (Throwable e) {
                LOG.error(e);
            }
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (EbkImagePickerActivity.this.camera != null) {
                EbkImagePickerActivity.this.camera.stopPreview();
            }
        }
    }

    class SurfaceClickListener implements OnClickListener {
        private SurfaceClickListener() {
        }

        public void onClick(View view) {
            try {
                EbkImagePickerActivity.this.camera.cancelAutoFocus();
                EbkImagePickerActivity.this.setCameraFocusMode();
            } catch (Exception e) {
                LOG.error("Focus assigned failed");
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.activity_image_picker);
        ImageLoaderFactory.clearCaches();
        this.imageStorage = new ImageStorageImpl(getIntent().getStringExtra("STORAGE_DIR"));
        initViews();
        getSelectedImagesFromIntent();
        persistValues(bundle);
    }

    private void initViews() {
        this.photoLibraryButton = (ImageButton) findViewById(R$id.select_from_photo_library);
        this.captureButton = (ImageButton) findViewById(R$id.capture_button);
        this.switchCameraButton = (ImageButton) findViewById(R$id.switch_camera_button);
        this.changeFlashModeButton = (ImageButton) findViewById(R$id.change_flash_mode_button);
        this.doneButton = (FrameLayout) findViewById(R$id.actionbar_done);
        this.surfaceView = (SurfaceView) findViewById(R$id.camera_preview);
        this.takePicEffectView = findViewById(R$id.white_flash);
        this.photoLibraryButton.setOnClickListener(new ButtonClickListener());
        this.captureButton.setOnClickListener(new ButtonClickListener());
        this.switchCameraButton.setOnClickListener(new ButtonClickListener());
        this.changeFlashModeButton.setOnClickListener(new ButtonClickListener());
        this.doneButton.setOnClickListener(new ButtonClickListener());
        this.surfaceView.setOnClickListener(new SurfaceClickListener());
    }

    private void getSelectedImagesFromIntent() {
        if (getIntent().getSerializableExtra("EXTRA_IMAGE_LIST") != null) {
            this.selectedImages = (ArrayList) getIntent().getSerializableExtra("EXTRA_IMAGE_LIST");
        } else {
            this.selectedImages = new ArrayList();
        }
    }

    private void persistValues(Bundle bundle) {
        if (bundle != null) {
            this.selectedImages = bundle.getParcelableArrayList(IMAGES_ARRAY);
            this.currentFlashMode = bundle.getString(CURRENT_FLASH_MODE);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(IMAGES_ARRAY, this.selectedImages);
        bundle.putString(CURRENT_FLASH_MODE, this.currentFlashMode);
    }

    protected void onResume() {
        super.onResume();
        registerAccelerometer();
        if (this.shouldShowPermissionDialog) {
            this.shouldShowPermissionDialog = false;
            showPermissionsCustomDialog();
            return;
        }
        accessPermissionRestrictedResources(R$string.ebk_image_picker_permission_camera_and_folder_access_title, R$string.ebk_image_picker_permission_camera_and_folder_access_description, new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, new EbkImagePickerPermissionReceiver());
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i != 0) {
            return;
        }
        if (allPermissionsGranted(iArr)) {
            this.permissionResponseReceiver.onPermissionGranted();
            return;
        }
        this.shouldShowPermissionDialog = true;
        this.permissionResponseReceiver.onPermissionDenied();
    }

    private boolean allPermissionsGranted(int[] iArr) {
        for (int i : iArr) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    private void showPermissionsCustomDialog() {
        PermissionsDialogFragment.newInstance(this, this.titleText, this.permissionText).show(getSupportFragmentManager(), "permissionsDialog");
    }

    public void askForAndroidPermissions(String[] strArr, PermissionResponseReceiver permissionResponseReceiver) {
        List ungrantedPermissions = getUngrantedPermissions(strArr);
        if (ungrantedPermissions.isEmpty()) {
            permissionResponseReceiver.onPermissionGranted();
            return;
        }
        this.permissionResponseReceiver = permissionResponseReceiver;
        ActivityCompat.requestPermissions(this, (String[]) ungrantedPermissions.toArray(new String[ungrantedPermissions.size()]), 0);
    }

    @NonNull
    private List<String> getUngrantedPermissions(String[] strArr) {
        List<String> arrayList = new ArrayList();
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public void accessPermissionRestrictedResources(int i, int i2, String[] strArr, PermissionResponseReceiver permissionResponseReceiver) {
        this.titleText = i;
        this.permissionText = i2;
        if (getUngrantedPermissions(strArr).isEmpty()) {
            permissionResponseReceiver.onPermissionGranted();
        } else {
            askForAndroidPermissions(strArr, permissionResponseReceiver);
        }
    }

    private void initPreview() {
        this.surfaceView = (SurfaceView) findViewById(R$id.camera_preview);
        this.surfaceView.setVisibility(0);
        this.surfaceView.getHolder().addCallback(new SurfaceCallback());
    }

    private boolean hasFrontCamera() {
        return getPackageManager().hasSystemFeature("android.hardware.camera.front");
    }

    private boolean canHaveFlashSetting() {
        return getPackageManager().hasSystemFeature("android.hardware.camera.flash") && this.currentCameraFacing == 0;
    }

    private boolean safelyOpenCamera(int i) {
        releaseCameraAndPreview();
        this.camera = getCameraInstance(i);
        boolean z = this.camera != null;
        if (z) {
            setCameraDisplayOrientation(i, this.camera);
            initPreview();
            setFlashMode(this.currentFlashMode);
        }
        return z;
    }

    @Nullable
    private Camera getCameraInstance(int i) {
        Camera open;
        Throwable e;
        try {
            open = Camera.open(i);
            try {
                this.currentCameraFacing = i;
            } catch (Exception e2) {
                e = e2;
                Toast.makeText(this, R$string.ebk_image_picker_camera_error, 0).show();
                LOG.error(e);
                return open;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            open = null;
            e = th;
            Toast.makeText(this, R$string.ebk_image_picker_camera_error, 0).show();
            LOG.error(e);
            return open;
        }
        return open;
    }

    protected void onPause() {
        releaseCameraAndPreview();
        unregisterAccelerometer();
        this.takingPicture = false;
        super.onPause();
    }

    private void registerAccelerometer() {
        this.accelerometerListener = new AccelerometerListener();
        this.sensorManager = (SensorManager) getSystemService("sensor");
        this.sensorManager.registerListener(this.accelerometerListener, this.sensorManager.getDefaultSensor(1), 3);
    }

    private void unregisterAccelerometer() {
        this.sensorManager.unregisterListener(this.accelerometerListener);
    }

    protected void onDestroy() {
        releaseCameraAndPreview();
        super.onDestroy();
    }

    private void releaseCameraAndPreview() {
        if (this.camera != null) {
            this.camera.stopPreview();
            this.camera.release();
            this.camera = null;
            this.surfaceView.destroyDrawingCache();
            this.surfaceView.setVisibility(8);
        }
    }

    private void rotateViews(float f) {
        float f2 = f == 0.0f ? this.degrees : -f;
        if (f2 != f) {
            RotateAnimation rotateAnimation = new RotateAnimation(f2, 0.0f, 1, 0.5f, 1, 0.5f);
            rotateAnimation.setDuration(250);
            rotateAnimation.setInterpolator(this, 17432587);
            rotateViewWithAnimation(this.captureButton, rotateAnimation, f);
            rotateViewWithAnimation(this.photoLibraryButton, rotateAnimation, f);
            rotateViewWithAnimation(this.switchCameraButton, rotateAnimation, f);
            rotateViewWithAnimation(this.changeFlashModeButton, rotateAnimation, f);
            getSelectedImagesFragment().rotateViews(f2, f);
            this.degrees = f;
        }
    }

    private void rotateViewWithAnimation(View view, RotateAnimation rotateAnimation, float f) {
        if (view.getVisibility() == 0) {
            view.startAnimation(rotateAnimation);
        }
        view.setRotation(f);
    }

    private Bitmap adjustImage(Bitmap bitmap) {
        if (this.currentCameraFacing == 1) {
            bitmap = ImageUtils.mirrorBitmap(bitmap);
        }
        if (this.orientation.equals(ScreenOrientation.portrait)) {
            return ImageUtils.rotateBitmap(bitmap, this.cameraRotation);
        }
        if (this.orientation.equals(ScreenOrientation.landscapeMinus)) {
            return ImageUtils.rotateBitmap(bitmap, this.cameraRotation + 90);
        }
        if (this.orientation.equals(ScreenOrientation.landscapePlus)) {
            return ImageUtils.rotateBitmap(bitmap, this.cameraRotation - 90);
        }
        return bitmap;
    }

    private void takePicture() {
        PictureCallback cameraCallback = new CameraCallback();
        if (this.selectedImages.size() >= getIntent().getIntExtra("MAX_SELECTION", 1)) {
            Toast.makeText(this, getResources().getText(R$string.ebk_image_picker_message_max_images_count_reached), 0).show();
        } else if (!this.takingPicture) {
            this.takingPicture = true;
            try {
                this.camera.takePicture(null, null, cameraCallback);
            } catch (RuntimeException e) {
                LOG.error("Exception when taking photo");
                this.takingPicture = false;
            }
        }
    }

    private void switchCamera() {
        int i = 1;
        int i2 = 0;
        if (this.currentCameraFacing == 1) {
            i = 0;
        }
        safelyOpenCamera(i);
        ImageButton imageButton = this.changeFlashModeButton;
        if (!canHaveFlashSetting()) {
            i2 = 8;
        }
        imageButton.setVisibility(i2);
    }

    private void changeFlashMode() {
        String nextFlashMode = setNextFlashMode();
        ImageButton imageButton = this.changeFlashModeButton;
        int i = nextFlashMode.equals("on") ? R$drawable.ebk_image_picker_ic_flash_on : nextFlashMode.equals("off") ? R$drawable.ebk_image_picker_ic_flash_off : R$drawable.ebk_image_picker_ic_flash_auto;
        imageButton.setImageResource(i);
    }

    public String setNextFlashMode() {
        String str = BuildConfig.FLAVOR;
        if (this.currentFlashMode.equals("auto")) {
            str = "on";
        } else if (this.currentFlashMode.equals("on")) {
            str = "off";
        } else if (this.currentFlashMode.equals("off")) {
            str = "auto";
        }
        return setFlashMode(str);
    }

    private String setFlashMode(String str) {
        if (this.camera.getParameters().getSupportedFlashModes() != null && this.camera.getParameters().getSupportedFlashModes().contains(str)) {
            Parameters parameters = this.camera.getParameters();
            parameters.setFlashMode(str);
            this.camera.setParameters(parameters);
            this.currentFlashMode = str;
        }
        return this.currentFlashMode;
    }

    public List<Image> getSelectedImages(boolean z) {
        if (z) {
            this.selectedImages = cleanImages(this.selectedImages);
        }
        return this.selectedImages;
    }

    public static ArrayList<Image> cleanImages(ArrayList<Image> arrayList) {
        if (arrayList != null) {
            ArrayList arrayList2 = new ArrayList();
            int i = 0;
            while (i < arrayList.size()) {
                if (((((Image) arrayList.get(i)).getModifiedFilePath() == null || ((Image) arrayList.get(i)).getModifiedFilePath().isEmpty()) && (((Image) arrayList.get(i)).getOriginalFilePath() == null || ((Image) arrayList.get(i)).getOriginalFilePath().isEmpty())) || ((Image) arrayList.get(i)).getData().isSaving()) {
                    arrayList2.add(arrayList.get(i));
                    break;
                }
                i++;
            }
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                arrayList.remove((Image) it.next());
            }
        }
        return arrayList;
    }

    private void addPhotoToGallery(String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        sendBroadcast(intent);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == REQUEST_CODE_IMAGE_LIBRARY || i == REQUEST_CODE_IMAGE_GALLERY) {
            getSelectedImagesFragment().destroyTransparentView();
            if (i2 == -1) {
                this.selectedImages = (ArrayList) intent.getSerializableExtra("EXTRA_IMAGE_LIST");
                this.selectedImages = cleanImages(this.selectedImages);
            }
        } else {
            super.onActivityResult(i, i2, intent);
        }
        getSelectedImagesFragment().recreateAdapter();
        if (i == REQUEST_CODE_IMAGE_LIBRARY) {
            getSelectedImagesFragment().smoothScrollRight();
        }
    }

    public void onBackPressed() {
        setResultAndFinish();
    }

    private void setResultAndFinish() {
        if (savedAllImages()) {
            Intent intent = new Intent();
            intent.putExtra("EXTRA_IMAGE_LIST", this.selectedImages);
            setResult(-1, intent);
            finish();
            return;
        }
        this.shouldFinishAfterSaving = true;
    }

    private boolean savedAllImages() {
        Iterator it = this.selectedImages.iterator();
        boolean z = true;
        while (it.hasNext()) {
            boolean z2;
            if (((Image) it.next()).getData().isSaving()) {
                z2 = false;
            } else {
                z2 = z;
            }
            z = z2;
        }
        return z;
    }

    private void takePicAnimation() {
        Animation loadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), 17432576);
        loadAnimation.setDuration(100);
        this.takePicEffectView.setAnimation(loadAnimation);
        this.takePicEffectView.setVisibility(0);
        this.takePicEffectView.post(new Runnable() {
            public void run() {
                Animation loadAnimation = AnimationUtils.loadAnimation(EbkImagePickerActivity.this.getApplicationContext(), 17432577);
                loadAnimation.setDuration(100);
                EbkImagePickerActivity.this.takePicEffectView.setAnimation(loadAnimation);
                EbkImagePickerActivity.this.takePicEffectView.setVisibility(8);
            }
        });
    }

    private SelectedImagesFragment getSelectedImagesFragment() {
        return (SelectedImagesFragment) getSupportFragmentManager().findFragmentById(R$id.fragment_selected_images);
    }

    private void setCameraFocusMode() {
        try {
            Parameters parameters = this.camera.getParameters();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            Size optimalPreviewSize = getOptimalPreviewSize(getPreviewSizesSortedByWidth(parameters), displayMetrics.widthPixels, displayMetrics.heightPixels);
            parameters.setPreviewSize(optimalPreviewSize.width, optimalPreviewSize.height);
            optimalPreviewSize = getOptimalPreviewSize(getPictureSizesSortedByWidth(parameters), displayMetrics.widthPixels, displayMetrics.heightPixels);
            parameters.setPictureSize(optimalPreviewSize.width, optimalPreviewSize.height);
            if (parameters.getSupportedFocusModes().contains("continuous-picture")) {
                parameters.setFocusMode("continuous-picture");
            } else if (parameters.getSupportedFocusModes().contains("auto")) {
                parameters.setFocusMode("auto");
            } else if (parameters.getSupportedFocusModes().contains("macro")) {
                parameters.setFocusMode("macro");
            }
            this.camera.setParameters(parameters);
            this.camera.startPreview();
        } catch (Throwable e) {
            LOG.error(e);
        }
    }

    private Size getOptimalPreviewSize(List<Size> list, int i, int i2) {
        double d = ((double) i) / ((double) i2);
        if (list == null) {
            return null;
        }
        Size size = null;
        double d2 = Double.MAX_VALUE;
        for (Size size2 : list) {
            double d3;
            Size size3;
            double abs;
            double d4 = getResources().getConfiguration().orientation != 2 ? (double) size2.height : (double) size2.width;
            if (getResources().getConfiguration().orientation != 2) {
                d3 = (double) size2.width;
            } else {
                d3 = (double) size2.height;
            }
            if (Math.abs((d4 / d3) - d) <= 0.05d) {
                if (Math.abs(d3 - ((double) i2)) < d2) {
                    size3 = size2;
                    abs = Math.abs(d3 - ((double) i2));
                } else {
                    double d5 = d2;
                    size3 = size;
                    abs = d5;
                }
                size = size3;
                d2 = abs;
            }
        }
        if (size != null) {
            return size;
        }
        d2 = Double.MAX_VALUE;
        for (Size size22 : list) {
            d4 = getResources().getConfiguration().orientation != 2 ? (double) size22.width : (double) size22.height;
            if (Math.abs(d4 - ((double) i2)) < d2) {
                size3 = size22;
                abs = Math.abs(d4 - ((double) i2));
            } else {
                d5 = d2;
                size3 = size;
                abs = d5;
            }
            size = size3;
            d2 = abs;
        }
        return size;
    }

    private List<Size> getPreviewSizesSortedByWidth(Parameters parameters) {
        return sortSizes(parameters.getSupportedPreviewSizes());
    }

    private List<Size> getPictureSizesSortedByWidth(Parameters parameters) {
        return sortSizes(parameters.getSupportedPictureSizes());
    }

    private List<Size> sortSizes(List<Size> list) {
        Collections.sort(list, new Comparator<Size>() {
            public int compare(Size size, Size size2) {
                if (size.width < size2.width) {
                    return -1;
                }
                return size.width > size2.width ? 1 : 0;
            }
        });
        return list;
    }

    public void setCameraDisplayOrientation(int i, Camera camera) {
        int i2;
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                i2 = 0;
                break;
            case HighlightView.GROW_NONE /*1*/:
                i2 = 90;
                break;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                i2 = 180;
                break;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                i2 = 270;
                break;
            default:
                i2 = 0;
                break;
        }
        this.cameraRotation = 0;
        if (cameraInfo.facing == 1) {
            this.cameraRotation = (i2 + cameraInfo.orientation) % 360;
            this.cameraRotation = (360 - this.cameraRotation) % 360;
        } else {
            this.cameraRotation = ((cameraInfo.orientation - i2) + 360) % 360;
        }
        camera.setDisplayOrientation(this.cameraRotation);
    }
}
