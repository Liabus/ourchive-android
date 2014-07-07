package com.liabus.ourchive;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liabus.ourchive.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;

    LinearLayout imageList;

    private boolean canTake = false;

    public void doneTaking(View v){
        Bundle conData = new Bundle();
        conData.putString("param_result", "Thanks Thanks");
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageList = (LinearLayout) getWindow().findViewById(R.id.image_list);

        imageList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doneTaking(view);
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Ensure we have camera hardware:
        if(!checkCameraHardware(this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Camera Not Found")
                .setMessage("The device's camera could not be found. Please try closing the app and trying again.")
                .setPositiveButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //GO back
                    }
                });
            // Create the AlertDialog object and return it
            builder.show();
            return;
        }

        cameraSetup();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void shutterSound() {
        mCamera.enableShutterSound(false);
    }

    private void cameraSetup(){
        // Create an instance of Camera
        mCamera = getCameraInstance();
        if(mCamera != null) {
            mCamera.setDisplayOrientation(90);

            //Set ORIENTATION
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0: degrees = 0; break; //Natural orientation
                case Surface.ROTATION_90: degrees = 90; break; //Landscape left
                case Surface.ROTATION_180: degrees = 180; break;//Upside down
                case Surface.ROTATION_270: degrees = 270; break;//Landscape right
            }
            int rotate = (info.orientation - degrees + 360) % 360;
            Camera.Parameters params = mCamera.getParameters();
            params.setRotation(rotate);
            mCamera.setParameters(params);



            shutterSound();

            // Create our Preview view and set it as the content of our activity.
            mPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.removeAllViews();
            preview.addView(mPreview);

            canTake = true;
        }
    }

    MediaPlayer _shootMP = null;
    public void shootSound(){
        AudioManager meng = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        int volume = meng.getStreamVolume( AudioManager.STREAM_NOTIFICATION);

        if (volume != 0)
        {
            if (_shootMP == null)
                _shootMP = MediaPlayer.create(this, Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
            if (_shootMP != null)
                _shootMP.start();
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    int flashMode = 0;

    public void openGallery(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 100:
                if(resultCode == RESULT_OK){
                    //Uri selectedImage = imageReturnedIntent.getData();
                    //InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                    //Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                }
        }
    }

    public void takePicture(View v){
        if(canTake) {
            canTake = false;
            mCamera.takePicture(new Camera.ShutterCallback() {
                @Override
                public void onShutter() {
                    shootSound();
                }
            }, null, mPicture);
        }
    }

    public void flashClicked(View v){

        Button btn = (Button)v;

        Camera.Parameters params = mCamera.getParameters();

        flashMode++;
        if(flashMode > 2) flashMode = 0;

        if(flashMode == 0){
            btn.setText("Off");
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(params);
        }
        if(flashMode == 1){
            btn.setText("On");
            params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            mCamera.setParameters(params);
        }
        if(flashMode == 2){
            btn.setText("Auto");
            params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            mCamera.setParameters(params);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCamera == null) {
            cameraSetup();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    private void releaseCamera(){
        if (mCamera != null){
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.release();
            mCamera = null;
        }
    }



    /** Create a File for saving an image or video */
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                System.out.println("Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();

                //Tell the file system to rescan the new file:
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + pictureFile.getAbsolutePath())));
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error accessing file: " + e.getMessage());
            }

            //Restart preview:
            mCamera.startPreview();
            canTake = true;

            //Insert image to list:
            ImageView img = new ImageView(getBaseContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            params.setMargins(10, 10, 10, 10);
            img.setLayoutParams(params);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Bitmap myBitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());
            myBitmap = scaleBitmap(myBitmap, 100);

            img.setImageBitmap(myBitmap);

            imageList.addView(img);
        }
    };

    private Bitmap scaleBitmap(Bitmap bm, int max) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        if (width > height) {
            width = (int)(((double)width / (double)height) * max);
            height = max;
        } else if (height > width) {
            height = (int)(((double)height / (double)width) * max);
            width = max;
        } else {
            // square
            height = max;
            width = max;
        }


        bm = Bitmap.createScaledBitmap(bm, width, height, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return bm;
    }

    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Ourchive");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                //Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

}
