package com.qdocs.ssre241123.adapters;

import static android.widget.Toast.makeText;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.qdocs.ssre241123.OpenPdf;
import com.qdocs.ssre241123.R;
import com.qdocs.ssre241123.students.AddCourseAssignment;
import com.qdocs.ssre241123.students.CourseExamResult;
import com.qdocs.ssre241123.students.OnlineCourseExam;
import com.qdocs.ssre241123.students.StudentOnlineCourseQuiz;
import com.qdocs.ssre241123.students.StudentOnlineQuizResult;
import com.qdocs.ssre241123.students.StudentStartLessonActivity;
import com.qdocs.ssre241123.utils.Constants;
import com.qdocs.ssre241123.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StartLessonAdapter extends BaseAdapter {
    CourseExamResultAdapter adapter;
    private StudentStartLessonActivity context;
     String lesson_type,course_exam_name,course_exam_from,course_exam_to,assignment_date,submit_date;
    private ArrayList<String> section_titleList;
    private ArrayList<String> section_idList;
    private ArrayList<String> lesson_countList;
    TableRow tablerow;
    Dialog courseexamdetailsdialog;

    ArrayList newobj=new ArrayList<String>();
    ArrayList newobj1=new ArrayList<String>();
    ArrayList<String> lessonArray;
    public String defaultDateFormat;
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, String> headers = new HashMap<String, String>();
    long downloadID;
    String status;
    RelativeLayout webview_layout;
    WebView webView;
    int countnum=0;
    String url;
    LinearLayout submitdialoug,afterresult_layout;
    RecyclerView recyclerview;
    ArrayList<String> idlist = new ArrayList<String>();
    ArrayList<String> questionlist = new ArrayList<String>();
    ArrayList<String> subject_namelist = new ArrayList<String>();
    ArrayList<String> select_optionlist = new ArrayList<String>();
    ArrayList<String> option_c = new ArrayList<String>();
    ArrayList<String> option_d = new ArrayList<String>();
    ArrayList<String> option_e = new ArrayList<String>();
    ArrayList<String> neg_marks_list = new ArrayList<String>();
    ArrayList<String> remark_list = new ArrayList<String>();
    ArrayList<String> question_typelist = new ArrayList<String>();
    ArrayList<String> marklist = new ArrayList<String>();
    ArrayList<String> getmarklist = new ArrayList<String>();
    ArrayList<String> correctlist = new ArrayList<String>();
    ArrayList<String> option_a = new ArrayList<String>();
    ArrayList<String> option_b = new ArrayList<String>();
    TextView title,totalattempt,totalquestion,examfrom,examto,durationET,answerwordcount,passing_per,descriptionET,startBtn,resultBtn,descriptive_ques,total_exam_marks,total_negative_marks,total_scored_marks,score_per;

    public StartLessonAdapter(StudentStartLessonActivity studentTransportRoutes,
                              ArrayList<String> section_titleList,ArrayList<String> section_idList, ArrayList<String> lessonArray, ArrayList<String> lesson_countList) {
        this.context = studentTransportRoutes;
        this.section_titleList = section_titleList;
        this.section_idList = section_idList;
        this.lessonArray = lessonArray;
        this.lesson_countList = lesson_countList;
    }

    @Override
    public int getCount() {
        return section_titleList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        defaultDateFormat = Utility.getSharedPreferences(context.getApplicationContext(), "dateFormat");

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.adapter_start_section, viewGroup, false);

        LinearLayout nameHeader = view.findViewById(R.id.course_sectionnameHeader);
        TableLayout vehicleTable = view.findViewById(R.id.course_sectionTable);
        TextView routeNameTV = (TextView) view.findViewById(R.id.course_sectionNameTV);
        TextView statusTV = (TextView) view.findViewById(R.id.course_section);
        routeNameTV.setTag(position);

        if(Utility.isConnectingToInternet(context)){
            getOnlineCourseSettingsStatus();
        }else{
            makeText(context, R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
        }

        routeNameTV.setText(section_titleList.get(position));
        statusTV.setText(context.getApplicationContext().getString(R.string.section)+" "+(position+1)+": ");
        webview_layout = context.findViewById(R.id.webview_layout);
        webview_layout = context.findViewById(R.id.webview_layout);
        webView=context.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new ChromeClient());
        nameHeader.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));

        Log.e("DATA==", lessonArray.get(position));

        try {
            final JSONArray dataArray = new JSONArray(lessonArray.get(position));
           /* // get the last object
            JSONObject tot_obj = dataArray.getJSONObject(dataArray.length()-1);

            // get String from last object
            String tot_str = tot_obj.optString("course_exam_name");
            System.out.println("tot_str=="+tot_str);*/
            Log.e("DDDATA==", String.valueOf(dataArray.length()));
            if (String.valueOf(dataArray.length()).equals("0")) {
                vehicleTable.setVisibility(View.GONE);
            } else {
                vehicleTable.setVisibility(View.VISIBLE);

                for (int i = 0; i < dataArray.length(); i++) {
                    final TableRow tr = (TableRow) context.getLayoutInflater().inflate(R.layout.adapter_start_lesson, null);

                    final TextView lessonTV, duration;
                    LinearLayout viewBtn;

                    CheckBox CheckBox;
                    final ImageView play_icon,summary;

                    viewBtn = tr.findViewById(R.id.course_lesson_detailsBtn);
                   lessonTV = (TextView) tr.findViewById(R.id.course_lessonTV);
                    tablerow = (TableRow) tr.findViewById(R.id.tablerow);

                    duration = (TextView) tr.findViewById(R.id.duration);
                    summary = (ImageView) tr.findViewById(R.id.summary);
                    CheckBox = (CheckBox) tr.findViewById(R.id.CheckBox);
                    play_icon = tr.findViewById(R.id.play_icon);
                    final String type = dataArray.getJSONObject(i).getString("type");
                    lesson_type = dataArray.getJSONObject(i).getString("lesson_type");
                    final String quiz_name = dataArray.getJSONObject(i).getString("quiz_title");
                    final String quiz_id = dataArray.getJSONObject(i).getString("quiz_id");
                    final String lesson_id = dataArray.getJSONObject(i).getString("lesson_id");
                    final String quizstatus = dataArray.getJSONObject(i).getString("quiz_status");
                    final String course_assignment_id = dataArray.getJSONObject(i).getString("course_assignment_id");
                    course_exam_name = dataArray.getJSONObject(i).getString("course_exam_name");
                    final String course_exam_id = dataArray.getJSONObject(i).getString("course_exam_id");
                    course_exam_from = dataArray.getJSONObject(i).getString("exam_from");
                    course_exam_to = dataArray.getJSONObject(i).getString("exam_to");
                    final String assignment_title = dataArray.getJSONObject(i).getString("assignment_title");
                    assignment_date = dataArray.getJSONObject(i).getString("assignment_date");
                    submit_date = dataArray.getJSONObject(i).getString("submit_date");
                    final String description = dataArray.getJSONObject(i).getString("description");
                    final String video = dataArray.getJSONObject(i).getString("video_url");
                    final String videoid = dataArray.getJSONObject(i).getString("video_id");
                    final String video_provider = dataArray.getJSONObject(i).getString("video_provider");
                    final String section_id =dataArray.getJSONObject(i).getString("course_section_id");
                    final String progress = dataArray.getJSONObject(i).getString("progress");
                    final String attachment = dataArray.getJSONObject(i).getString("attachment");
                    final String lessonsummary = dataArray.getJSONObject(i).getString("summary");
                    if(progress.equals("1")){
                        CheckBox.setChecked(true);
                    }else if(progress.equals("0")){
                        CheckBox.setChecked(false);
                    }else{
                        CheckBox.setChecked(false);
                    }

                    if(type.equals("lesson")){
                        lessonTV.setText(dataArray.getJSONObject(i).getString("lesson_title"));
                        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean checkedStatus) {
                                if (Utility.isConnectingToInternet(context.getApplicationContext())) {
                                    params.put("student_id", Utility.getSharedPreferences(context.getApplicationContext(), Constants.studentId));
                                    params.put("lesson_quiz_id", lesson_id);
                                    params.put("section_id", section_idList.get(position));
                                    params.put("lesson_quiz_type","1");
                                    JSONObject objt=new JSONObject(params);
                                    Log.e("mark complete params =", objt.toString());
                                    System.out.println("mark complete params ="+objt.toString());
                                    changeStatusApi(objt.toString());
                                } else {
                                    makeText(context.getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        if(lesson_type.equals("video")){
                            duration.setVisibility(View.VISIBLE);
                            summary.setVisibility(View.GONE);
                            duration.setText(dataArray.getJSONObject(i).getString("duration"));
                            play_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play_icon));
                            lessonTV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (video_provider.equals("html5")) {
                                        webview_layout.setVisibility(View.VISIBLE);
                                        webView.setWebViewClient(new WebViewClient() {
                                            @SuppressWarnings("deprecation")
                                            @Override
                                            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) { }

                                            @TargetApi(Build.VERSION_CODES.M)
                                            @Override
                                            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) { }

                                            @Override
                                            public void onPageStarted(WebView view, String url, Bitmap favicon) { }

                                            @Override
                                            public void onPageFinished(WebView view, String url) { }
                                        });
                                        Log.e("Video URL", "URL " + video);
                                        webView.loadUrl(video);
                                    } else if(video_provider.equals("youtube")){
                                        if (Utility.isConnectingToInternet(context)) {
                                            url = "http://www.youtube.com/embed/" + videoid + "?autoplay=1&vq=small";
                                            Log.e("URL===", url);
                                            System.out.println("url=="+url);
                                        } else {
                                            makeText(context,R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                                        }
                                        webview_layout.setVisibility(View.VISIBLE);
                                        webView.setWebViewClient(new WebViewClient() {
                                            @SuppressWarnings("deprecation")
                                            @Override
                                            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) { }

                                            @TargetApi(Build.VERSION_CODES.M)
                                            @Override
                                            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) { }

                                            @Override
                                            public void onPageStarted(WebView view, String url, Bitmap favicon) { }

                                            @Override
                                            public void onPageFinished(WebView view, String url) { }
                                        });
                                        Log.e("Video URL", "URL " + video);
                                        webView.loadUrl(url);
                                    } if (video_provider.equals("s3_bucket")) {

                                        String text = "<html><body>" + "<video width=\"1000\" height=\"500\" controls>" + "<source src=" +"\"" + video
                                                    +"\"" + ">" + "</video>" + "</body></html>";
                                        System.out.println("s3 bucket url="+text);
                                        webview_layout.setVisibility(View.VISIBLE);
                                        webView.loadDataWithBaseURL(null,text,"text/html; charset=utf-8", "utf-8", null);

                                    }else if (video_provider.equals("vimeo")) {
                                        if (Utility.isConnectingToInternet(context)) {
                                            url = "https://player.vimeo.com/video/"+videoid;
                                            Log.e("URL", url);
                                        } else {
                                            makeText(context,R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                                        }
                                        webview_layout.setVisibility(View.VISIBLE);
                                        webView.setWebViewClient(new WebViewClient() {
                                            @SuppressWarnings("deprecation")
                                            @Override
                                            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) { }

                                            @TargetApi(Build.VERSION_CODES.M)
                                            @Override
                                            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) { }

                                            @Override
                                            public void onPageStarted(WebView view, String url, Bitmap favicon) { }

                                            @Override
                                            public void onPageFinished(WebView view, String url) { }
                                        });
                                        Log.e("Video URL", "URL " + video);
                                        webView.loadUrl(url);
                                    }
                                }
                            });
                        }else if(lesson_type.equals("pdf")){
                            duration.setVisibility(View.GONE);
                            if(lessonsummary.equals("")){
                                summary.setVisibility(View.GONE);
                            }else{
                                summary.setVisibility(View.VISIBLE);
                                summary.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final Dialog dialog = new Dialog(context);
                                        dialog.setContentView(R.layout.lesson_description);
                                        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
                                        final ProgressDialog progressDialog = new ProgressDialog(context.getApplicationContext());
                                        progressDialog.setMessage("Loading Data...");
                                        progressDialog.setCancelable(false);
                                        TextView headerTV = dialog.findViewById(R.id.homework_bottomSheet_headerTV);
                                        ImageView closeBtn = dialog.findViewById(R.id.homework_bottomSheet_crossBtn);
                                        TextView details= dialog.findViewById(R.id.bottomSheet_TV);
                                        details.setText(lessonsummary);
                                        headerTV.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));
                                        headerTV.setText((context.getApplicationContext().getString(R.string.description)));
                                        closeBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    }
                                });
                            }
                            play_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_attach_file_black));
                            lessonTV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String urlStr = Utility.getSharedPreferences(context.getApplicationContext(), Constants.imagesUrl);
                                    urlStr += "uploads/course_content/"+section_id+"/"+lesson_id+"/"+attachment;
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlStr));
                                    context.startActivity(browserIntent);
                                }
                            });
                        }else if(lesson_type.equals("text")){
                            duration.setVisibility(View.GONE);
                            if(lessonsummary.equals("")){
                                summary.setVisibility(View.GONE);
                            }else{
                                summary.setVisibility(View.VISIBLE);
                                summary.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final Dialog dialog = new Dialog(context);
                                        dialog.setContentView(R.layout.lesson_description);
                                        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
                                        final ProgressDialog progressDialog = new ProgressDialog(context.getApplicationContext());
                                        progressDialog.setMessage("Loading Data...");
                                        progressDialog.setCancelable(false);
                                        TextView headerTV = dialog.findViewById(R.id.homework_bottomSheet_headerTV);
                                        ImageView closeBtn = dialog.findViewById(R.id.homework_bottomSheet_crossBtn);
                                        TextView details= dialog.findViewById(R.id.bottomSheet_TV);
                                        details.setText(lessonsummary);

                                        headerTV.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));
                                        headerTV.setText((context.getApplicationContext().getString(R.string.description)));
                                        closeBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    }
                                });
                            }
                            play_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_attach_file_black));
                            lessonTV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String urlStr = Utility.getSharedPreferences(context.getApplicationContext(), Constants.imagesUrl);
                                    urlStr += "uploads/course_content/"+section_id+"/"+lesson_id+"/"+attachment;
                                    Intent intent=new Intent(context.getApplicationContext(), OpenPdf.class);
                                    intent.putExtra("imageUrl",urlStr);
                                    context.startActivity(intent);
                                    System.out.println("imageUrl"+urlStr);
                                }
                            });
                        }else if(lesson_type.equals("document")){
                            duration.setVisibility(View.GONE);
                            if(lessonsummary.equals("")){
                                summary.setVisibility(View.GONE);
                            }else{
                                summary.setVisibility(View.VISIBLE);
                                summary.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final Dialog dialog = new Dialog(context);
                                        dialog.setContentView(R.layout.lesson_description);
                                        dialog.getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
                                        final ProgressDialog progressDialog = new ProgressDialog(context.getApplicationContext());
                                        progressDialog.setMessage("Loading Data...");
                                        progressDialog.setCancelable(false);
                                        TextView headerTV = dialog.findViewById(R.id.homework_bottomSheet_headerTV);
                                        ImageView closeBtn = dialog.findViewById(R.id.homework_bottomSheet_crossBtn);
                                        TextView details= dialog.findViewById(R.id.bottomSheet_TV);
                                        details.setText(lessonsummary);
                                        headerTV.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));
                                        headerTV.setText((context.getApplicationContext().getString(R.string.description)));
                                        closeBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    }
                                });
                            }
                            play_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_attach_file_black));
                            lessonTV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String urlStr = Utility.getSharedPreferences(context.getApplicationContext(), Constants.imagesUrl);
                                    urlStr += "uploads/course_content/"+section_id+"/"+lesson_id+"/"+attachment;
                                    downloadID = Utility.beginDownload(context, attachment, urlStr);
                                    System.out.println("imageUrl"+urlStr);
                                }
                            });
                        }

                    }else if(type.equals("quiz")){

                        duration.setVisibility(View.GONE);
                        play_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_quiz));
                        lessonTV.setText(dataArray.getJSONObject(i).getString("quiz_title"));
                        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean checkedStatus) {
                                if (Utility.isConnectingToInternet(context.getApplicationContext())) {
                                    params.put("student_id", Utility.getSharedPreferences(context.getApplicationContext(), Constants.studentId));
                                    params.put("lesson_quiz_id", quiz_id);
                                    params.put("section_id", section_idList.get(position));
                                    params.put("lesson_quiz_type","2");
                                    JSONObject objt=new JSONObject(params);
                                    Log.e("params ", objt.toString());
                                    System.out.println("mark complete params ="+objt.toString());
                                    changeStatusApi(objt.toString());

                                } else {
                                    makeText(context.getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        lessonTV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                webview_layout.setVisibility(View.GONE);

                                if(quizstatus.equals("0")){
                                    Intent intent=new Intent(context.getApplicationContext(), StudentOnlineCourseQuiz.class);
                                    intent.putExtra("quiz_id", quiz_id);
                                    intent.putExtra("quiz_name", quiz_name);
                                    context.startActivity(intent);
                                    context.overridePendingTransition(R.anim.slide_leftright,  R.anim.no_animation);

                                }else {

                                    Intent intent=new Intent(context.getApplicationContext(), StudentOnlineQuizResult.class);
                                    intent.putExtra("quiz_id", quiz_id);
                                    intent.putExtra("quiz_name", quiz_name);
                                    context.startActivity(intent);
                                    context.overridePendingTransition(R.anim.slide_leftright,  R.anim.no_animation);

                                }

                            }
                        });
                        /* if(newobj1.contains("online_course_quiz")) {
                            System.out.println("online_course_quiz is there");
                            tablerow.setVisibility(View.VISIBLE);
                        }
                        else{
                            System.out.println("online_course_quiz is not there" );
                            tablerow.setVisibility(View.GONE);
                        }*/
                    }else if(type.equals("assignment")){
                        Calendar c = Calendar.getInstance();
                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        final String getCurrentDateTime = sdf.format(c.getTime());
                        final String getstartTime=assignment_date;
                        final String getendTime=submit_date;

                        if (getCurrentDateTime.compareTo(getstartTime) < 0||getCurrentDateTime.compareTo(getendTime)>0) {
                            CheckBox.setEnabled(false);
                            System.out.println("helloo current date");
                        }else{
                            System.out.println("hiii current date");
                            if (Utility.getSharedPreferences(context.getApplicationContext(), Constants.loginType).equals("parent")) {
                                CheckBox.setEnabled(false);
                            }else{
                                CheckBox.setEnabled(true);
                            }
                        }
                       /* if(newobj.contains("online_course_assignment")) {
                            System.out.println("online_course_assignment is there");
                            tablerow.setVisibility(View.VISIBLE);
                        }
                        else{
                            System.out.println("online_course_assignment is not there" );
                            tablerow.setVisibility(View.GONE);
                        }*/
                        duration.setVisibility(View.GONE);
                        play_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_assignment));
                        lessonTV.setText(dataArray.getJSONObject(i).getString("assignment_title"));
                        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean checkedStatus) {
                                if (Utility.isConnectingToInternet(context.getApplicationContext())) {
                                    params.put("student_id", Utility.getSharedPreferences(context.getApplicationContext(), Constants.studentId));
                                    params.put("lesson_quiz_id", course_assignment_id);
                                    params.put("section_id", section_idList.get(position));
                                    params.put("lesson_quiz_type","3");
                                    JSONObject objt=new JSONObject(params);
                                    Log.e("params ", objt.toString());
                                    System.out.println("mark complete params ="+objt.toString());
                                    changeStatusApi(objt.toString());

                                } else {
                                    makeText(context.getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        lessonTV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(context.getApplicationContext(), AddCourseAssignment.class);
                                intent.putExtra("course_assignment_id", course_assignment_id);
                                intent.putExtra("assignment_title", assignment_title);
                                intent.putExtra("description", description);
                                context.startActivity(intent);
                                context.overridePendingTransition(R.anim.slide_leftright,  R.anim.no_animation);
                            }
                        });
                    }else if(type.equals("exam")){
                        Calendar c = Calendar.getInstance();
                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final String getCurrentDateTime = sdf.format(c.getTime());
                        final String getstartTime=course_exam_from;
                        final String getendTime=course_exam_to;

                        if (getCurrentDateTime.compareTo(getstartTime) < 0||getCurrentDateTime.compareTo(getendTime)>0) {
                            CheckBox.setEnabled(false);
                            System.out.println("helloo current date");
                        }else{
                            System.out.println("hiii current date");
                            if (Utility.getSharedPreferences(context.getApplicationContext(), Constants.loginType).equals("parent")) {
                                CheckBox.setEnabled(false);
                            }else{
                                CheckBox.setEnabled(true);
                            }
                        }
                       /* if(newobj.contains("online_course_exam")) {
                            System.out.println("online_course_exam is there");
                            tablerow.setVisibility(View.VISIBLE);
                        }
                        else{
                            System.out.println("online_course_exam is not there" );
                            tablerow.setVisibility(View.GONE);
                        }*/
                        duration.setVisibility(View.GONE);
                        play_icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_online_exams));
                        lessonTV.setText(course_exam_name);
                        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean checkedStatus) {
                                if (Utility.isConnectingToInternet(context.getApplicationContext())) {
                                    params.put("student_id", Utility.getSharedPreferences(context.getApplicationContext(), Constants.studentId));
                                    params.put("lesson_quiz_id", course_exam_id);
                                    params.put("section_id", section_idList.get(position));
                                    params.put("lesson_quiz_type","4");
                                    JSONObject objt=new JSONObject(params);
                                    Log.e("params ", objt.toString());
                                    System.out.println("mark complete params ="+objt.toString());
                                    changeStatusApi(objt.toString());
                                } else {
                                    makeText(context.getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        lessonTV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                courseexamdetailsdialog = new Dialog(context);
                                courseexamdetailsdialog.setContentView(R.layout.course_exam_details);
                                courseexamdetailsdialog.setCanceledOnTouchOutside(false);
                                RelativeLayout headerLay = (RelativeLayout) courseexamdetailsdialog.findViewById(R.id.addTask_dialog_header);
                                title = (TextView) courseexamdetailsdialog.findViewById(R.id.title);

                                totalattempt = (TextView) courseexamdetailsdialog.findViewById(R.id.totalattempt);
                                totalquestion = (TextView) courseexamdetailsdialog.findViewById(R.id.totalquestion);
                                examfrom = (TextView) courseexamdetailsdialog.findViewById(R.id.examfrom);
                                examto = (TextView) courseexamdetailsdialog.findViewById(R.id.examto);
                                durationET = (TextView) courseexamdetailsdialog.findViewById(R.id.duration);
                                answerwordcount = (TextView) courseexamdetailsdialog.findViewById(R.id.answerwordcount);
                                passing_per = (TextView) courseexamdetailsdialog.findViewById(R.id.passing_per);
                                descriptionET = (TextView) courseexamdetailsdialog.findViewById(R.id.descriptionET);
                                descriptive_ques = (TextView) courseexamdetailsdialog.findViewById(R.id.descriptive_ques);
                                startBtn = (TextView) courseexamdetailsdialog.findViewById(R.id.startBtn);
                                resultBtn = (TextView) courseexamdetailsdialog.findViewById(R.id.resultBtn);
                                total_exam_marks = (TextView) courseexamdetailsdialog.findViewById(R.id.total_exam_marks);
                                total_negative_marks = (TextView) courseexamdetailsdialog.findViewById(R.id.total_negative_marks);
                                total_scored_marks = (TextView) courseexamdetailsdialog.findViewById(R.id.total_scored_marks);
                                score_per = (TextView) courseexamdetailsdialog.findViewById(R.id.score_per);
                                submitdialoug = (LinearLayout) courseexamdetailsdialog.findViewById(R.id.submitdialoug);
                                afterresult_layout = (LinearLayout) courseexamdetailsdialog.findViewById(R.id.afterresult_layout);
                                recyclerview=(RecyclerView)courseexamdetailsdialog.findViewById(R.id.recyclerview);
                                title.setText(quiz_name);
                                if (Utility.isConnectingToInternet(context.getApplicationContext())) {
                                    params.put("student_id", Utility.getSharedPreferences(context.getApplicationContext(), Constants.studentId));
                                    params.put("exam_id", course_exam_id);
                                    params.put("user_type", Utility.getSharedPreferences(context.getApplicationContext(), Constants.loginType));
                                    JSONObject examobjt=new JSONObject(params);
                                    Log.e("params ", examobjt.toString());
                                    examDetailsApi(examobjt.toString());
                                } else {
                                    makeText(context.getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                                }
                                headerLay.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.primaryColour)));
                                courseexamdetailsdialog.show();

                            }
                        });
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        context.registerReceiver(onDownloadComplete,
                                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                                Context.RECEIVER_EXPORTED);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.registerReceiver(onDownloadComplete,
                                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                                Context.RECEIVER_NOT_EXPORTED);
                    } else {
                        context.registerReceiver(onDownloadComplete,
                                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), Context.RECEIVER_NOT_EXPORTED);
                    }
                    vehicleTable.addView(tr);
                    context.registerForContextMenu(tr);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  view.setTag(viewHolder);
        return view;
    }

    private void changeStatusApi (String bodyParams) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;

        String url = Utility.getSharedPreferences(context.getApplicationContext(), "apiUrl") + Constants.markAsCompleteUrl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Result", result);
                        JSONObject object = new JSONObject(result);
                        String success = object.getString("status");

                        if (success.equals("1")) {
                            //context.finish();
                           // context.startActivity(context.getIntent());
                         Toast.makeText(context.getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    pd.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(context, R.string.apiErrorMsg, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                headers.put("Client-Service", Constants.clientService);
                headers.put("Auth-Key", Constants.authKey);
                headers.put("Content-Type", Constants.contentType);
                headers.put("User-ID", Utility.getSharedPreferences(context.getApplicationContext(), "userId"));
                headers.put("Authorization", Utility.getSharedPreferences(context.getApplicationContext(), "accessToken"));
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
        RequestQueue requestQueue = Volley.newRequestQueue(context); //Creating a Request Queue
        requestQueue.add(stringRequest);//Adding request to the queue
    }
    private void examDetailsApi (String bodyParams) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;

        String url = Utility.getSharedPreferences(context.getApplicationContext(), "apiUrl") + Constants.getCourseExamDetailsUrl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Result", result);
                        String defaultDatetimeFormat = Utility.getSharedPreferences(context.getApplicationContext(), "datetimeFormat");
                        JSONObject object = new JSONObject(result);
                        JSONObject question_typeobj = object.getJSONObject("question_type");
                        JSONObject examobj = object.getJSONObject("exam");

                      String examId=examobj.getString("id");
                      String duration=examobj.getString("duration");
                      title.setText(examobj.getString("exam"));
                      String attempt=examobj.getString("attempt");
                      String counter=object.getString("counter");
                      totalattempt.setText(examobj.getString("attempt"));
                      totalquestion.setText(question_typeobj.getString("total_quetions"));
                      examfrom.setText(Utility.parseDate("yyyy-MM-dd HH:mm:ss", defaultDatetimeFormat,examobj.getString("exam_from")));
                      examto.setText(Utility.parseDate("yyyy-MM-dd HH:mm:ss", defaultDatetimeFormat,examobj.getString("exam_to")));
                      descriptionET.setText(Html.fromHtml(examobj.getString("description"), Html.FROM_HTML_MODE_COMPACT));
                      passing_per.setText(examobj.getString("passing_percentage"));
                      durationET.setText(duration);
                      descriptive_ques.setText(question_typeobj.getString("total_descriptive"));
                      answerwordcount.setText(examobj.getString("answer_word_count"));

                        Calendar c = Calendar.getInstance();
                        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final String getCurrentDateTime = sdf.format(c.getTime());
                        final String getstartTime=examobj.getString("exam_from");
                        final String getendTime=examobj.getString("exam_to");
                        Log.d("getCurrentDateTime",getCurrentDateTime);
                        System.out.println("getCurrentDateTime="+getCurrentDateTime);
                        //holder.startexam.setVisibility(View.VISIBLE);

                        total_exam_marks.setText(object.getString("total_marks"));
                        total_negative_marks.setText(object.getString("total_negative"));
                        total_scored_marks.setText(object.getString("total_score"));
                        score_per.setText(object.getString("score_percentage"));

                        if(object.getString("submitstatus").equals("1")){
                            if(examobj.getString("publish_result").equals("1")){
                                startBtn.setVisibility(View.GONE);
                                submitdialoug.setVisibility(View.GONE);
                                afterresult_layout.setVisibility(View.VISIBLE);
                                total_exam_marks.setText(object.getString("total_marks"));
                                total_negative_marks.setText(object.getString("total_negative"));
                                total_scored_marks.setText(object.getString("total_score"));
                                score_per.setText(object.getString("score_percentage"));
                                resultBtn.setVisibility(View.VISIBLE);
                                resultBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(context.getApplicationContext(), CourseExamResult.class);
                                        intent.putExtra("courseexamId",examId);
                                        context.startActivity(intent);
                                    }
                                });
                            }else {
                                startBtn.setVisibility(View.GONE);
                                submitdialoug.setVisibility(View.VISIBLE);
                                resultBtn.setVisibility(View.GONE);
                                afterresult_layout.setVisibility(View.GONE);
                            }
                        }else{
                            submitdialoug.setVisibility(View.GONE);
                            if (getCurrentDateTime.compareTo(getstartTime) < 0||getCurrentDateTime.compareTo(getendTime)>0) {
                                startBtn.setVisibility(View.GONE);
                            }else{
                                if (Utility.getSharedPreferences(context.getApplicationContext(), Constants.loginType).equals("parent")) {
                                    startBtn.setVisibility(View.GONE);
                                }else{
                                    startBtn.setVisibility(View.VISIBLE);
                                    startBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if(attempt.equals(counter)){
                                                Snackbar snackbar = Snackbar.make(startBtn, "You have reached total limits",Snackbar.LENGTH_SHORT);
                                                snackbar.show();
                                            }else{
                                                courseexamdetailsdialog.dismiss();
                                                if (getCurrentDateTime.compareTo(getstartTime) > 0||getCurrentDateTime.compareTo(getstartTime) == 0) {
                                                    Date d1 = null;
                                                    Date d2 = null;
                                                    try {
                                                        d1 = sdf.parse(getCurrentDateTime);
                                                        d2 = sdf.parse(getendTime);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    long diff = d2.getTime() - d1.getTime();

                                                    String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(diff),
                                                            TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)),
                                                            TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)));

                                                    String duration= null;
                                                    try {
                                                        duration = examobj.getString("duration");
                                                    } catch (JSONException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    String time_duration= hms;
                                                    System.out.println("time_duration= "+time_duration+" duration= "+duration);

                                                    String gettimedurationTime=time_duration;
                                                    String getdurationTime=duration;

                                                    if (gettimedurationTime.compareTo(getdurationTime) < 0) {
                                                        Log.d("Return","gettimedurationTime");
                                                         Intent intent=new Intent(context.getApplicationContext(), OnlineCourseExam.class);
                                                            intent.putExtra("course_exam_name", course_exam_name);
                                                            intent.putExtra("course_exam_id", examId);
                                                            intent.putExtra("duration", gettimedurationTime);
                                                            context.startActivity(intent);
                                                            context.overridePendingTransition(R.anim.slide_leftright,  R.anim.no_animation);

                                                    } else {
                                                        Log.d("Return","getdurationTime ");
                                                        Intent intent=new Intent(context.getApplicationContext(), OnlineCourseExam.class);
                                                        intent.putExtra("course_exam_name",course_exam_name);
                                                        intent.putExtra("duration",duration);
                                                        intent.putExtra("course_exam_id",examId);
                                                        context.startActivity(intent);
                                                    }
                                                } else if (getCurrentDateTime.compareTo(getendTime) < 0||getCurrentDateTime.compareTo(getendTime) == 0) {
                                                    Date d1 = null;
                                                    Date d2 = null;
                                                    try {
                                                        d1 = sdf.parse(getCurrentDateTime);
                                                        d2 = sdf.parse(getendTime);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    long diff = d2.getTime() - d1.getTime();
                                                    String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(diff),
                                                            TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diff)),
                                                            TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)));


                                                    String time_duration= hms;
                                                    String duration= null;
                                                    try {
                                                        duration = examobj.getString("duration");
                                                    } catch (JSONException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    System.out.println("time_duration= "+time_duration+" duration= "+duration);

                                                    String gettimedurationTime=sdf.format(time_duration);
                                                    String getdurationTime=sdf.format(duration);

                                                    if (gettimedurationTime.compareTo(getdurationTime) < 0) {

                                                            Intent intent=new Intent(context.getApplicationContext(), OnlineCourseExam.class);
                                                            intent.putExtra("course_exam_name", course_exam_name);
                                                            intent.putExtra("course_exam_id", examId);
                                                            intent.putExtra("duration", gettimedurationTime);
                                                            intent.putExtra("student_id", Utility.getSharedPreferences(context.getApplicationContext(), Constants.studentId));
                                                            context.startActivity(intent);
                                                            context.overridePendingTransition(R.anim.slide_leftright,  R.anim.no_animation);
                                                            Log.d("Return","gettimedurationTime");
                                                    } else {
                                                            Intent intent=new Intent(context.getApplicationContext(), OnlineCourseExam.class);
                                                            intent.putExtra("course_exam_name",course_exam_name);
                                                            intent.putExtra("duration",duration);
                                                            intent.putExtra("course_exam_id",examId);
                                                            context.startActivity(intent);
                                                            Log.d("Return","getdurationTime ");
                                                    }
                                                } else {
                                                    Snackbar snackbar = Snackbar.make(startBtn, "You have reached total attemps or exam date passed, Please contact to administrator.",Snackbar.LENGTH_SHORT);
                                                    snackbar.show();
                                                    Log.d("Return","getMyTime older than getCurrentDateTime");
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    pd.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(context, R.string.apiErrorMsg, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                headers.put("Client-Service", Constants.clientService);
                headers.put("Auth-Key", Constants.authKey);
                headers.put("Content-Type", Constants.contentType);
                headers.put("User-ID", Utility.getSharedPreferences(context.getApplicationContext(), "userId"));
                headers.put("Authorization", Utility.getSharedPreferences(context.getApplicationContext(), "accessToken"));
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
        RequestQueue requestQueue = Volley.newRequestQueue(context); //Creating a Request Queue
        requestQueue.add(stringRequest);//Adding request to the queue
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

    public class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(context.getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout)context.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            context.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            // setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = context.getWindow().getDecorView().getSystemUiVisibility();
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            //this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)context.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            context.getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        }
    }

    private void getOnlineCourseSettingsStatus() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        String url = Utility.getSharedPreferences(context.getApplicationContext(), "apiUrl")+ Constants.getOnlineCourseSettingsUrl;
        System.out.println("url=="+url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                pd.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject resultobj = object.getJSONObject("result");
                   System.out.println("course_curriculum_settings=="+resultobj.getString("course_curriculum_settings"));
                    JSONArray jsonArr = new JSONArray(resultobj.getString("course_curriculum_settings"));

                    for (int i = 0; i < jsonArr.length(); i++) {
                        System.out.println("courseobj=="+ jsonArr );
                        newobj.add(jsonArr.getString( i ));
                    }
                    String newobjstring=newobj.toString().replace("[","");
                    System.out.println("newobjstring=="+newobjstring);
                    String newobjstring1=newobjstring.toString().replace("]","");
                    System.out.println("newobjstring1=="+newobjstring1);

                    newobj1.add(newobjstring1);
                    System.out.println("newobj1=="+ newobj1);
                   /* if(newobj1.contains("online_course_quiz")) {
                        System.out.println("online_course_quiz is there");
                        tablerow.setVisibility(View.VISIBLE);
                    }
                    else{
                        System.out.println("online_course_quiz is not there" );
                        tablerow.setVisibility(View.GONE);
                    }*/

                        /*if(lesson_type.equals("quiz")){
                            if(newobj.contains("online_course_quiz")) {
                                System.out.println("online_course_quiz is there");
                                tablerow.setVisibility(View.VISIBLE);
                            }
                            else{
                                System.out.println("online_course_quiz is not there" );
                                tablerow.setVisibility(View.GONE);
                            }
                        }
                        if(lesson_type.equals("assignment")){
                          if(newobj.contains("online_course_assignment")) {
                            System.out.println("online_course_assignment is there");
                            tablerow.setVisibility(View.VISIBLE);
                         }
                         else{
                            System.out.println("online_course_assignment is not there" );
                            tablerow.setVisibility(View.GONE);
                         }
                        }
                    if(lesson_type.equals("exam")){
                        if(newobj.contains("online_course_exam")) {
                            System.out.println("online_course_exam is there");
                            tablerow.setVisibility(View.VISIBLE);
                        }
                        else{
                            System.out.println("online_course_exam is not there" );
                            tablerow.setVisibility(View.GONE);
                        }
                    }*/



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(context, R.string.apiErrorMsg, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                headers.put("Client-Service", Constants.clientService);
                headers.put("Auth-Key", Constants.authKey);
                headers.put("Content-Type", Constants.contentType);
                Log.e("Headers", headers.toString());
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);//Creating a Request Queue
        requestQueue.add(stringRequest); //Adding request to the queue
    }

}
