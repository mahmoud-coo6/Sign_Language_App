package com.example.signlanguageapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.signlanguageapp.models.Classification;
import com.example.signlanguageapp.models.Classifier;
import com.example.signlanguageapp.models.TensorFlowClassifier;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ImageFragment extends Fragment {

    private ImageView buttonImageView;
    private ImageView viewImage;
    private TextView textView;
    private View myImageFragment;
    private static final int PERMISSION_REQUEST_CODE = 200;
//    WebView webView;
    private List<Classifier> mClassifiers = new ArrayList<>();
    private static final int PIXEL_WIDTH = 28;
    private Bitmap image;


    private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul:0";
    private static final String OUTPUT_NAME = "final_result:0";

    private static final String MODEL_FILE = "file:///android_asset/retrained_graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/retrained_labels.txt";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myImageFragment = inflater.inflate(R.layout.image_input, container, false);



//        webView = myImageFragment.findViewById(R.id.webview);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.loadUrl("file:///android_asset/www/index.html");
//        webView.loadUrl("www/index.html");

//        webView.loadUrl("../app/src/assets/www/index.html");

        buttonImageView = myImageFragment.findViewById(R.id.image_input);
        viewImage = myImageFragment.findViewById(R.id.current_letter_image);
        textView = myImageFragment.findViewById(R.id.current_letter_text);

        buttonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        loadModel();


        return myImageFragment;
    }

    private void loadModel() {
        //The Runnable interface is another way in which you can implement multi-threading other than extending the
        // //Thread class due to the fact that Java allows you to extend only one class. Runnable is just an interface,
        // //which provides the method run.
        // //Threads are implementations and use Runnable to call the method run().
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //add 2 classifiers to our classifier arraylist
                    //the tensorflow classifier and the keras classifier
//                    mClassifiers.add(
//                            TensorFlowClassifier.create(getActivity().getAssets(), "TensorFlow",
//                                    "file:///android_asset/saved_model.pb", "file:///android_asset/labels.txt", PIXEL_WIDTH,
//                                    "input", "output", true));

                    mClassifiers.add(
                            TensorFlowClassifier.create(getActivity().getAssets(), "TensorFlow",
                                    "saved_model.pb", "labels.txt", PIXEL_WIDTH,
                                    "input", "output", true));

                } catch (final Exception e) {
                    //if they aren't found, throw an error!
                    throw new RuntimeException("Error initializing classifiers!", e);
                }
            }
        }).start();
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (checkPermission()) {
                    //main logic or main code
                    if (options[item].equals("Take Photo")) {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                        processImage();

                    } else if (options[item].equals("Choose from Gallery")) {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                        processImage();
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                } else {
                    requestPermission();
                }
            }
        });
        builder.show();
    }


    private void processImage() {
        //if the user clicks the classify button
        //get the pixel data and store it in an array
//        float pixels[] = image.getPixelData();
        float pixels[] = getPixelData();

        //init an empty string to fill with the classification output
        String text = "";
        //for each classifier in our array
        for (Classifier classifier : mClassifiers) {
            //perform classification on the image
            final Classification res = classifier.recognize(pixels);
            //if it can't classify, output a question mark
            if (res.getLabel() == null) {
                text += classifier.name() + ": ?\n";
            } else {
                //else output its name
                text += String.format("%s: %s, %f\n", classifier.name(), res.getLabel(),
                        res.getConf());
            }
        }
        textView.setText(text);
    }

    private float[] getPixelData() {
        if (image == null) {
            return null;
        }

        int width = image.getWidth();
        int height = image.getHeight();

        // Get 28x28 pixel data from bitmap
        int[] pixels = new int[width * height];
        image.getPixels(pixels, 0, width, 0, 0, width, height);

        float[] retPixels = new float[pixels.length];
        for (int i = 0; i < pixels.length; ++i) {
            // Set 0 for white and 255 for black pixel
            int pix = pixels[i];
            int b = pix & 0xff;
            retPixels[i] = (float)((0xff - b)/255.0);
        }
        return retPixels;
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    selectImage();
                    // main logic
                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmapOptions.inSampleSize = 3;
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    image= bitmap;
                    viewImage.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
//                    Picasso.get()
//                            .load(path)
//                            .resize(50, 50)// resizing images
//                            .centerCrop()
//                            .into(viewImage);

                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        viewImage.setImageBitmap(bitmap);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmapOptions.inSampleSize = 3;
                thumbnail = BitmapFactory.decodeFile(picturePath,
                        bitmapOptions);
                Log.w("path of image from gallery..............", picturePath + "");
//                thumbnail.inSampleSize = 3;
                image= thumbnail;
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        myImageFragment = null;
//         buttonImageView= null;
//         viewImage= null;
//    }

}