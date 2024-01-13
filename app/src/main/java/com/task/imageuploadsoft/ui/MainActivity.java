package com.task.imageuploadsoft.ui;

import static com.task.imageuploadsoft.util.Constants.SOFTGROUP;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.task.imageuploadsoft.R;
import com.task.imageuploadsoft.databinding.ActivityMainBinding;
import com.task.imageuploadsoft.model.CameraOptions;
import com.task.imageuploadsoft.util.AppPermissionsRunTime;
import com.task.imageuploadsoft.util.ConnectionUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final int GALLERY = 100;
    private final int IMAGE_CAPTURE = 1000;
    private AppPermissionsRunTime permissionsRunTime;
    private ArrayList<AppPermissionsRunTime.Permission> permissionList;
    public String mCurrentPhotoPath;
    private String recentPhotoPath;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        permissionsRunTime = AppPermissionsRunTime.getInstance();
        permissionList = new ArrayList<>();
        handler = new Handler(getMainLooper());
        binding.progressBarCyclic.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionList.add(AppPermissionsRunTime.Permission.READ_MEDIA_IMAGE);
            permissionList.add(AppPermissionsRunTime.Permission.READ_MEDIA_VIDEO);
            permissionList.add(AppPermissionsRunTime.Permission.READ_MEDIA_AUDIO);
        } else {
            permissionList.add(AppPermissionsRunTime.Permission.CAMERA);
            permissionList.add(AppPermissionsRunTime.Permission.READ_EXTERNAL_STORAGE);
            permissionList.add(AppPermissionsRunTime.Permission.WRITE_EXTERNAL_STORAGE);
        }
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUploadOptions();
            }
        });
        binding.previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the preview of the selected image in a dialog
                if (recentPhotoPath != null) {
                    showImagePreviewDialog(recentPhotoPath);
                } else {
                    Toast.makeText(getApplicationContext(), "Please choose image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.fileTypeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUploadOptions();
            }
        });
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImages();
            }
        });
    }

    private void uploadImages() {
        if (recentPhotoPath != null) {
            if (!ConnectionUtil.isNetworkAvailable(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                uploadFile(recentPhotoPath, "upload");
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please choose image", Toast.LENGTH_SHORT).show();
        }
    }

    private void showImagePreviewDialog(String imageUri) {
        Dialog imagePreviewDialog = new Dialog(this);
        imagePreviewDialog.setContentView(R.layout.dialogue_preview);
        imagePreviewDialog.setCancelable(true);
        ImageView dialogImageView = imagePreviewDialog.findViewById(R.id.dialogImageView);
        dialogImageView.setImageBitmap(BitmapFactory.decodeFile(imageUri));
        imagePreviewDialog.show();
    }

    private void uploadFile(String filestr, String desc) {


        File file = new File(filestr);

        // Show the progress bar
        binding.progressBarCyclic.setVisibility(View.VISIBLE);

        // Schedule a task to hide the progress bar after 2 seconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Hide the progress bar
                binding.progressBarCyclic.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Image uploaded successfuly", Toast.LENGTH_SHORT).show();
                recentPhotoPath = null;
                mCurrentPhotoPath = null;
                binding.fileTypeTextView.setText(R.string.pick_image);
                binding.imageView.setImageBitmap(null);
            }
        }, 2000); // 2000 milliseconds = 2 seconds
//        //creating request body for file
//        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(Uri.fromFile(file)))), file);
//        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);
//
//        //The gson builder
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//
//        //creating retrofit object
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(RetrofitInterface.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//        //creating our api
//        RetrofitInterface api = retrofit.create(RetrofitInterface.class);
//
//        //creating a call and calling the upload image method
//        Call<MyResponse> call = api.uploadOpPhoto(requestFile, descBody);
//
//        //finally performing the call
//        call.enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                if (!(response.code() == 200)) {
//                    Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void showUploadOptions() {
        CameraOptions[] items;

        items = new CameraOptions[]{
                new CameraOptions(getString(R.string.txt_camera_option), R.drawable.image_placeholder),
                new CameraOptions(getString(R.string.txt_gallery_option), R.drawable.image_placeholder)
        };

        final CameraOptions[] finalItems = items;
        ListAdapter adapter = new ArrayAdapter<CameraOptions>(
                MainActivity.this,
                android.R.layout.select_dialog_item,
                android.R.id.text1,
                finalItems) {
            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView tv = v.findViewById(android.R.id.text1);
                tv.setTextSize(18);
                tv.setCompoundDrawablesWithIntrinsicBounds(finalItems[position].icon, 0, 0, 0);
                tv.setCompoundDrawablePadding(0);
                return v;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle(getString(R.string.txt_select));
        builder.setAdapter(adapter, (dialog, item) -> {
            if (item == 0) {
                openCamera();
            } else if (item == 1) {
                openGallery();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), (dialog, id) -> {
        });
        builder.show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);
    }

    private void openCamera() {
        if (permissionsRunTime.getPermission(permissionList, MainActivity.this)) {
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                Method m = StrictMode.class.getMethod(getString(R.string.file_uri_exposure));
                m.invoke(null);
            }
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile;
                photoFile = createImageFileDp();
                Uri photoURI = FileProvider.getUriForFile(this, SOFTGROUP, photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File createImageFileDp() {
        File storageDir = new File(this.getFilesDir(), "SOFT-GROUP");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        String fName = "SoftGroup" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(storageDir, fName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_CAPTURE:
                    if (mCurrentPhotoPath != null) {
                        ImageView imageView = findViewById(R.id.imageView);
                        imageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
                        recentPhotoPath = mCurrentPhotoPath;
                        File f = new File(mCurrentPhotoPath);
                        String imageName = f.getName();
                        binding.fileTypeTextView.setText("" + imageName);
                    }
                    break;
                case GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(Objects.requireNonNull(selectedImage), filePathColumn, null, null, null);

                        if (cursor != null) {
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();
                            ImageView imageView = findViewById(R.id.imageView);
                            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                            mCurrentPhotoPath = picturePath;
                            recentPhotoPath = picturePath;
                            File f = new File(mCurrentPhotoPath);
                            String imageName = f.getName();
                            binding.fileTypeTextView.setText("" + imageName);
                        }
                    }
                    break;
            }
        }
    }
}