package com.qdocs.ssre241123.students;

import static android.widget.Toast.makeText;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.qdocs.ssre241123.OpenPdf;
import com.qdocs.ssre241123.R;
import com.qdocs.ssre241123.utils.Constants;
import com.qdocs.ssre241123.utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AddCourseAssignment extends AppCompatActivity {
    public ImageView backBtn;
    public String defaultDateFormat, currency,startweek;
    Context mContext = this;
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, String> headers = new HashMap<String, String>();
    protected FrameLayout mDrawerLayout, actionBar;
    String applydate = "";
    String formdate = "";
    String filePath;
    ProgressDialog progress;
    String todate = "";
    private boolean isfromDateSelected = false;
    private boolean istoDateSelected = false;
    Bitmap bitmap;
    Button submit;
    private static final int CAMERA_REQUEST = 1888;
    String url;
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    String file_path="";
    File f;
    RequestBody file_body;
    ArrayList<String> subjectlist=new ArrayList<String>();
    ArrayList<String>subjectidlist=new ArrayList<String>();
    EditText reason;
    Uri uri;
    ImageView imageView;
    EditText title;
    TextView textView;
    public TextView titleTV,buttonSelectImage,messageET;
    Button buttonUploadImage;
    private static final String TAG = "StudentAddLeave";
    TextInputEditText titleET, dateET, descriptionET;
    public static Boolean camera = false;
    public static Boolean gallery = false;
    boolean isKitKat = false;
    Spinner subjectlist_spinner;
    String[] mimeTypes =
            {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                    "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                    "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                    "text/plain",
                    "application/pdf",
                    "application/zip","image/*"};
    CardView card_view_outer;
    String subjectid;
    String extension="",name="";
    Bitmap selectedImageString = null;
    TextView assignmentdate,submissionDate,evaluationDate,createdBy,evaluatedBy,status,doc_name,teacher_remark,homedoc_name;
    long downloadID;
    LinearLayout teacher_remark_layout,document_layout,homedocument_layout;
    ImageView document,homedocument;
    String course_assignment_id,assignment_title,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_assignment);
        backBtn = findViewById(R.id.actionBar_backBtn);
       // mDrawerLayout = findViewById(R.id.container);
        actionBar = findViewById(R.id.actionBarSecondary);
        titleTV = findViewById(R.id.actionBar_title);
        assignmentdate = findViewById(R.id.assignmentdate);
        submissionDate = findViewById(R.id.submissionDate);
        evaluationDate = findViewById(R.id.evaluationDate);
        createdBy = findViewById(R.id.createdBy);
        evaluatedBy = findViewById(R.id.evaluatedBy);
        status = findViewById(R.id.status);
        doc_name = findViewById(R.id.doc_name);
        homedoc_name = findViewById(R.id.homedoc_name);
        teacher_remark = findViewById(R.id.teacher_remark);
        document = findViewById(R.id.document);
        homedocument = findViewById(R.id.homedocument);
        document_layout = findViewById(R.id.document_layout);
        teacher_remark_layout = findViewById(R.id.teacher_remark_layout);
        homedocument_layout = findViewById(R.id.homedocument_layout);


        defaultDateFormat = Utility.getSharedPreferences(getApplicationContext(), "dateFormat");
        currency = Utility.getSharedPreferences(getApplicationContext(), Constants.currency);
        startweek = Utility.getSharedPreferences(getApplicationContext(), "startWeek");

        decorate();
        Utility.setLocale(getApplicationContext(), Utility.getSharedPreferences(getApplicationContext(), Constants.langCode));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        card_view_outer = findViewById(R.id.card_view_outer);
        card_view_outer.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        titleTV.setText(getApplicationContext().getString(R.string.course_assignment));
        titleET = findViewById(R.id.titleET);
        descriptionET = findViewById(R.id.descriptionET);
        messageET = findViewById(R.id.messageET);
        Bundle bundle = getIntent().getExtras();
        course_assignment_id = bundle.getString("course_assignment_id");
        System.out.println("course_assignment_id="+course_assignment_id);
        assignment_title = bundle.getString("assignment_title");
        description = bundle.getString("description");
        titleET.setText(assignment_title);
        titleTV.setText(assignment_title);
        descriptionET.setText(assignment_title);
        imageView =  findViewById(R.id.imageView);
        textView =  findViewById(R.id.textview);
      //  title =  findViewById(R.id.title);
       // buttonUploadImage =  findViewById(R.id.buttonUploadImage);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        submit = findViewById(R.id.addLeave_dialog_submitBtn);

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Else", "Else");
                ActivityCompat.requestPermissions(AddCourseAssignment.this, permissions(), 1);
                showFileChooser();

            }
        });

        submit.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                     if(titleET.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.titlefield), Toast.LENGTH_LONG).show();
                    }else if(descriptionET.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.descriptionfield), Toast.LENGTH_LONG).show();
                    }else if(messageET.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.messagefield), Toast.LENGTH_LONG).show();
                    } else {
                        if(Utility.isConnectingToInternet(getApplicationContext())){
                            uploadBitmap();
                        }else{
                            makeText(getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        if(Utility.isConnectingToInternet(getApplicationContext())){
            params.put("assignment_id", course_assignment_id);
            params.put("student_id", Utility.getSharedPreferences(getApplicationContext(), Constants.studentId));
            JSONObject objt=new JSONObject(params);
            System.out.println("params== "+ objt.toString());
            Log.e("params ", objt.toString());
            getDataFromApi(objt.toString());
        }else{
            makeText(getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.
                    READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }


    }

    private void decorate() {
        actionBar.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        }
    }

    private void showFileChooser() {
        final Dialog dialog = new Dialog(AddCourseAssignment.this);
        dialog.setContentView(R.layout.choose_file);
        dialog.setCanceledOnTouchOutside(false);
        RelativeLayout headerLay = (RelativeLayout) dialog.findViewById(R.id.addTask_dialog_header);
        final LinearLayout takephoto = (LinearLayout) dialog.findViewById(R.id.takephoto);
        final LinearLayout choosegallery = (LinearLayout) dialog.findViewById(R.id.gallery);
        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.addTask_dialog_crossIcon);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camerapic();
                camera = true;
                gallery = false;
                dialog.dismiss();
            }
        });
        choosegallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opengallery();
                gallery = true;
                camera = false;
                dialog.dismiss();
            }
        });

        headerLay.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        dialog.show();
    }

    void camerapic() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void opengallery() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            } else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
            }
            isKitKat = true;
            startActivityForResult(Intent.createChooser(intent, "Select file"), PICK_IMAGE_REQUEST);
        } else {
            isKitKat = false;

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            } else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
            }
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        if (inImage == null) {
            Log.e("getImageUri", "Bitmap is null");
            return null;
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(
                inContext.getContentResolver(), inImage, "CapturedImage", null);

        if (path == null) {
            Log.e("getImageUri", "Failed to insert image");
            return null;
        }

        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    public String getgalleryRealPathFromURI(Context context, Uri contentUri) {
        OutputStream out;
        File file = new File(getFilename(context));

        try {
            if (file.createNewFile()) {
                InputStream iStream = context != null ? context.getContentResolver().openInputStream(contentUri) : context.getContentResolver().openInputStream(contentUri);
                byte[] inputData = getBytes(iStream);
                out = new FileOutputStream(file);
                out.write(inputData);
                out.close();
                return file.getAbsolutePath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private String getFilename(Context context) {
        File mediaStorageDir = new File(context.getExternalFilesDir(""), "Soers_Images");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        String mImageName=name+"."+extension;
        System.out.println("mImageName=="+mImageName);
        System.out.println("Image=="+mediaStorageDir.getAbsolutePath() + "/" + mImageName);
        return mediaStorageDir.getAbsolutePath() + "/" + mImageName;
    }

    public static String[] storage_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storage_permissions;
        }
        return p;
    }



    @TargetApi(19)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            System.out.println("uri=="+uri);

            String path = new File(uri.getPath()).getAbsolutePath();
            System.out.println("path=="+path);

            if(path != null){
                uri = data.getData();

                String filenames;
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);

                if(cursor == null) filenames=uri.getPath();
                else{
                    cursor.moveToFirst();
                    int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
                    filenames = cursor.getString(idx);
                    cursor.close();
                }

                name = filenames.substring(0,filenames.lastIndexOf("."));
                System.out.println("name=="+name);
                extension = filenames.substring(filenames.lastIndexOf(".")+1);
                System.out.println("extension=="+extension);
            }else{
                makeText(this, "Please select file", Toast.LENGTH_SHORT).show();
            }

            try {
                selectedImageString = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            textView.setText(getApplicationContext().getString(R.string.fileselected));

            filePath = getgalleryRealPathFromURI(AddCourseAssignment.this, uri);
            if(extension.equals("jpg")||extension.equals("png")||extension.equals("jpeg")){
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(selectedImageString);
            }else if(extension.equals("PDF")||extension.equals("pdf")||extension.equals("doc")||extension.equals("docx")||extension.equals("txt")){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_file));
            }
            f = new File(filePath);
            System.out.println("file=="+filePath);
            String mimeType = URLConnection.guessContentTypeFromName(f.getName());
            file_body = RequestBody.create(MediaType.parse(mimeType), f);
            System.out.println("file_bodypathasd" + file_body);
            System.out.println("bitmap image==" + selectedImageString);
        }else if (requestCode == CAMERA_REQUEST  && resultCode == RESULT_OK ) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (bitmap != null) {
                progress = new ProgressDialog(AddCourseAssignment.this);
                progress.setTitle("uploading");
                progress.setMessage("Please Wait....");
                progress.show();
                textView.setText(getApplicationContext().getString(R.string.fileselected));
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                filePath = getRealPathFromURI(tempUri);
                System.out.println("pathasd" + filePath);
                 f = new File(filePath);
                String mimeType = URLConnection.guessContentTypeFromName(f.getName());
                file_body = RequestBody.create(MediaType.parse(mimeType), f);
                System.out.println("file_bodypathasd" + file_body);
                progress.dismiss();
            }
        }
    }
    private void uploadBitmap() throws IOException{
        url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl") + Constants.saveCourseAssignmentUrl;
        OkHttpClient client=new OkHttpClient();
        Log.i("url=", url);

        if(filePath==null || file_body==null){

            RequestBody requestBody=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("assignmentid",course_assignment_id)
                    .addFormDataPart("message",messageET.getText().toString())
                    .addFormDataPart("file","")
                    .addFormDataPart("student_id", Utility.getSharedPreferences(getApplicationContext(), Constants.studentId))
                    .build();

            Request request=new Request.Builder()
                    .url(url)
                    .header("Client-Service", Constants.clientService)
                    .header("Auth-Key", Constants.authKey)
                    .header("User-ID",Utility.getSharedPreferences(getApplicationContext(), "userId"))
                    .header("Authorization",Utility.getSharedPreferences(getApplicationContext(), "accessToken"))
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        makeText(mContext, R.string.apiErrorMsg, Toast.LENGTH_SHORT).show();
                    }
                });}

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody body = response.body();
                    if(body != null) {
                        try {
                            String jsonData = response.body().string();
                            try {
                                final JSONObject Jobject = new JSONObject(jsonData);
                                String Jarray = Jobject.getString("status");
                                if(Jarray.equals("1")){
                                    runOnUiThread(new Runnable(){
                                        public void run() {
                                            Toast.makeText(mContext, getApplicationContext().getString(R.string.submit_success), Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });
                                }else{
                                    runOnUiThread(new Runnable(){
                                        public void run() {
                                            try {
                                                JSONObject error = Jobject.getJSONObject("error");
                                                Toast.makeText(mContext, error.getString("reason"), Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });

        }else{
            String file_name=filePath.substring(filePath.lastIndexOf("/")+1);
            System.out.println("file_name== "+file_name);
            System.out.println("file_body== "+file_body);

            RequestBody requestBody=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("assignmentid",course_assignment_id)
                    .addFormDataPart("message",messageET.getText().toString())
                    .addFormDataPart("file",file_name,file_body)
                    .addFormDataPart("student_id", Utility.getSharedPreferences(getApplicationContext(), Constants.studentId))
                    .build();

            Request request=new Request.Builder()
                    .url(url)
                    .header("Client-Service", Constants.clientService)
                    .header("Auth-Key", Constants.authKey)
                    .header("User-ID",Utility.getSharedPreferences(getApplicationContext(), "userId"))
                    .header("Authorization",Utility.getSharedPreferences(getApplicationContext(), "accessToken"))
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            makeText(mContext, R.string.apiErrorMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody body = response.body();
                    if(body != null) {
                        try {
                            String jsonData = response.body().string();
                            try {
                                final JSONObject Jobject = new JSONObject(jsonData);
                                String Jarray = Jobject.getString("status");


                                if(Jarray.equals("1")){
                                    runOnUiThread(new Runnable(){
                                        public void run() {
                                            Toast.makeText(mContext, getApplicationContext().getString(R.string.submit_success), Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    });

                                }else{
                                    runOnUiThread(new Runnable(){
                                        public void run() {
                                            try {
                                                String error = Jobject.getString("msg");
                                                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });
        }

    }
    private String getMimeType(String path) {
        String extension= MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }


    private void getDataFromApi (String bodyParams) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;

        String url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl")+Constants.getSubmitedAssignmentDetailsUrl;
        Log.e("URL", url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Result", result);
                        JSONObject object = new JSONObject(result);
                        JSONObject resultArray = object.getJSONObject("result");
                        JSONObject result_statusArray = object.getJSONObject("result_status");
                        createdBy.setText(resultArray.getString("assignemnt_created_by"));
                        assignmentdate.setText(Utility.parseDate("yyyy-MM-dd", defaultDateFormat,resultArray.getString("assignment_date")));
                        submissionDate.setText(Utility.parseDate("yyyy-MM-dd", defaultDateFormat,resultArray.getString("submit_date")));
                        if(resultArray.getString("document").equals("")){
                            homedocument_layout.setVisibility(View.GONE);
                        }else{
                            homedocument_layout.setVisibility(View.VISIBLE);
                            homedoc_name.setText(resultArray.getString("document"));
                            homedocument.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String urlStr = Utility.getSharedPreferences(getApplicationContext(), Constants.imagesUrl);
                                    try {
                                        urlStr += "/uploads/course_content/online_course_assignment/"+resultArray.getString("document");
                                        downloadID = Utility.beginDownload(getApplicationContext(), resultArray.getString("document"), urlStr);
                                        Intent intent=new Intent(getApplicationContext().getApplicationContext(), OpenPdf.class);
                                        intent.putExtra("imageUrl",urlStr);
                                        System.out.println("imageUrl=="+urlStr);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });
                        }

                       if(result_statusArray.getString("status_lable").equals("pending")) {
                           status.setText(getApplicationContext().getString(R.string.pending));
                           status.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_border));
                           evaluationDate.setText(result_statusArray.getString(""));
                           evaluatedBy.setText(resultArray.getString(""));
                           messageET.setText(result_statusArray.getString(""));
                           teacher_remark_layout.setVisibility(View.GONE);
                           document_layout.setVisibility(View.GONE);
                       }else if(result_statusArray.getString("status_lable").equals("submitted")){
                           status.setText(getApplicationContext().getString(R.string.submitted));
                           status.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_border));
                           messageET.setText(result_statusArray.getString("message"));
                           if(result_statusArray.getString("docs").equals("")){
                               document_layout.setVisibility(View.GONE);
                               teacher_remark_layout.setVisibility(View.GONE);
                           }else{
                               document_layout.setVisibility(View.VISIBLE);
                               doc_name.setText(result_statusArray.getString("docs"));
                               teacher_remark_layout.setVisibility(View.GONE);
                           }
                       }else if(result_statusArray.getString("status_lable").equals("evaluated")){
                           status.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_border));
                           status.setText(getApplicationContext().getString(R.string.evaluated));
                           evaluationDate.setText(Utility.parseDate("yyyy-MM-dd", defaultDateFormat,result_statusArray.getString("evaluated_date")));
                           evaluatedBy.setText(resultArray.getString("assignemnt_evaluated_by"));
                           if(result_statusArray.getString("docs").equals("")){
                               document_layout.setVisibility(View.GONE);
                           }else{
                               document_layout.setVisibility(View.VISIBLE);
                               doc_name.setText(result_statusArray.getString("docs"));
                           }
                           if(result_statusArray.getString("evaluated_note").equals("")){
                               teacher_remark_layout.setVisibility(View.GONE);
                           }else{
                               teacher_remark_layout.setVisibility(View.VISIBLE);
                               teacher_remark.setText(result_statusArray.getString("evaluated_note"));
                           }
                       }

                        document.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String urlStr = Utility.getSharedPreferences(getApplicationContext(), Constants.imagesUrl);
                                try {
                                    urlStr += "/uploads/course_content/online_course_assignment/"+result_statusArray.getString("docs");
                                    downloadID = Utility.beginDownload(getApplicationContext(), result_statusArray.getString("docs"), urlStr);

                                    Intent intent=new Intent(getApplicationContext().getApplicationContext(), OpenPdf.class);
                                    intent.putExtra("imageUrl",urlStr);
                                    System.out.println("imageUrl=="+urlStr);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        });
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            registerReceiver(onDownloadComplete,
                                    new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                                    Context.RECEIVER_EXPORTED);
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            registerReceiver(onDownloadComplete,
                                    new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                                    Context.RECEIVER_NOT_EXPORTED);
                        } else {
                            registerReceiver(onDownloadComplete,
                                    new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_NOT_EXPORTED);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    pd.dismiss();


                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(AddCourseAssignment.this, R.string.apiErrorMsg, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                headers.put("Client-Service", Constants.clientService);
                headers.put("Auth-Key", Constants.authKey);
                headers.put("Content-Type", Constants.contentType);
                headers.put("User-ID", Utility.getSharedPreferences(getApplicationContext(), "userId"));
                headers.put("Authorization", Utility.getSharedPreferences(getApplicationContext(), "accessToken"));
                Log.e("Headers", headers.toString());
                return headers;
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddCourseAssignment.this); //Creating a Request Queue
        requestQueue.add(stringRequest); //Adding request to the queue
    }
    public BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.notification_logo)
                                .setContentTitle(context.getApplicationContext().getString(R.string.app_name))
                                .setContentText("All Download completed");


                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());

            }
        }
    };

}



