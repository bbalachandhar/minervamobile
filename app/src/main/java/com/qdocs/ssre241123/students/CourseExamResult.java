package com.qdocs.ssre241123.students;

import static android.widget.Toast.makeText;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qdocs.ssre241123.R;
import com.qdocs.ssre241123.adapters.CourseExamResultAdapter;
import com.qdocs.ssre241123.adapters.StudentOnlineExamResultAdapter;
import com.qdocs.ssre241123.utils.Constants;
import com.qdocs.ssre241123.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CourseExamResult extends AppCompatActivity {
    public ImageView backBtn;
    public String defaultDatetimeFormat, currency;
    RecyclerView recyclerView;
    LinearLayout nodata_layout;
    CourseExamResultAdapter adapter;
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, String> headers = new HashMap<String, String>();
    SwipeRefreshLayout pullToRefresh;
    public TextView titleTV;
    TextView exam,fromdate,attempt,todate,duration,rank,description,percent,total_quest,correct,wrong,notattempt,score,exam_marks,scored_marks,neg_marks,total_descriptive;
    LinearLayout neg_marks_layout;
    protected FrameLayout mDrawerLayout, actionBar;
    ArrayList<String> select_optionlist = new ArrayList<String>();
    ArrayList<String> correctlist = new ArrayList<String>();
    ArrayList<String> option_a = new ArrayList<String>();
    ArrayList<String> option_b = new ArrayList<String>();
    ArrayList<String> idlist = new ArrayList<String>();
    ArrayList<String> questionlist = new ArrayList<String>();
    ArrayList<String> subject_namelist = new ArrayList<String>();
    ArrayList<String> is_rank_generated = new ArrayList<String>();
    String is_neg_marking_marks;
    ArrayList<String> option_c = new ArrayList<String>();
    ArrayList<String> option_d = new ArrayList<String>();
    ArrayList<String> option_e = new ArrayList<String>();
    ArrayList<String> neg_marks_list = new ArrayList<String>();
    ArrayList<String> remark_list = new ArrayList<String>();
    ArrayList<String> question_typelist = new ArrayList<String>();
    ArrayList<String> marklist = new ArrayList<String>();
    ArrayList<String> getmarklist = new ArrayList<String>();
    String OnlineExam_student_Id,exam_id;
   CardView card_view_outer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.setLocale(getApplicationContext(), Utility.getSharedPreferences(getApplicationContext(), Constants.langCode));
        setContentView(R.layout.activity_course_exam_result);
        backBtn = findViewById(R.id.actionBar_backBtn);
        mDrawerLayout = findViewById(R.id.container);
        actionBar = findViewById(R.id.actionBarSecondary);
        titleTV = findViewById(R.id.actionBar_title);
        defaultDatetimeFormat = Utility.getSharedPreferences(getApplicationContext(), "datetimeFormat");
        currency = Utility.getSharedPreferences(getApplicationContext(), Constants.currency);
        exam_id = getIntent().getExtras().getString("courseexamId");
        decorate();
        Utility.setLocale(getApplicationContext(), Utility.getSharedPreferences(getApplicationContext(), Constants.langCode));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        titleTV.setText(getApplicationContext().getString(R.string.courseexamresult));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        nodata_layout = (LinearLayout) findViewById(R.id.nodata_layout);
        loaddata();


    }

    private void decorate() {
        actionBar.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        }
    }

    public  void  loaddata(){
        if (Utility.isConnectingToInternet(getApplicationContext())) {
            params.put("student_id", Utility.getSharedPreferences(getApplicationContext(), Constants.studentId));
            params.put("exam_id", exam_id);
            params.put("user_type", Utility.getSharedPreferences(getApplicationContext(), Constants.loginType));
            JSONObject examobjt=new JSONObject(params);
            Log.e("params ", examobjt.toString());
            examDetailsApi(examobjt.toString());
        } else {
            makeText(getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        loaddata();
    }

    private void examDetailsApi (String bodyParams) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;
        String url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl")+Constants.getCourseExamDetailsUrl;
        Log.e("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {

                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Result", result);
                        System.out.println("Result===="+result);
                        JSONObject object = new JSONObject(result);

                                JSONArray resultArray = object.getJSONArray("question_result");
                                String defaultDatetimeFormat = Utility.getSharedPreferences(getApplicationContext(), "datetimeFormat");
                                questionlist.clear();
                                question_typelist.clear();
                                idlist.clear();
                                subject_namelist.clear();
                                select_optionlist.clear();
                                correctlist.clear();
                                marklist.clear();
                                option_a.clear();
                                option_b.clear();
                                option_c.clear();
                                option_d.clear();
                                option_e.clear();
                                neg_marks_list.clear();
                                remark_list.clear();
                                if(resultArray.length() != 0) {
                                    for (int i = 0; i < resultArray.length(); i++) {
                                        idlist.add(resultArray.getJSONObject(i).getString("id"));
                                        questionlist.add(resultArray.getJSONObject(i).getString("question"));
                                        // subject_namelist.add(resultArray.getJSONObject(i).getString("subject_name")+" ("+resultArray.getJSONObject(i).getString("subjects_code")+")");
                                        select_optionlist.add(resultArray.getJSONObject(i).getString("select_option"));
                                        question_typelist.add(resultArray.getJSONObject(i).getString("question_type"));
                                        getmarklist.add(resultArray.getJSONObject(i).getString("score_marks") + "/" + resultArray.getJSONObject(i).getString("marks"));
                                        marklist.add(resultArray.getJSONObject(i).getString("marks"));
                                        correctlist.add(resultArray.getJSONObject(i).getString("correct"));
                                        option_a.add(resultArray.getJSONObject(i).getString("opt_a"));
                                        option_b.add(resultArray.getJSONObject(i).getString("opt_b"));
                                        option_c.add(resultArray.getJSONObject(i).getString("opt_c"));
                                        option_d.add(resultArray.getJSONObject(i).getString("opt_d"));
                                        option_e.add(resultArray.getJSONObject(i).getString("opt_e"));
                                        neg_marks_list.add(resultArray.getJSONObject(i).getString("neg_marks"));
                                        remark_list.add(resultArray.getJSONObject(i).getString("remark"));

                                    }

                                    adapter = new CourseExamResultAdapter(CourseExamResult.this, questionlist,
                                            select_optionlist, correctlist, idlist, option_a, option_b, option_c, option_d, option_e, question_typelist, marklist, getmarklist, neg_marks_list, remark_list);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            nodata_layout.setVisibility(View.VISIBLE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(CourseExamResult.this, R.string.apiErrorMsg, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(CourseExamResult.this); //Creating a Request Queue
        requestQueue.add(stringRequest);  //Adding request to the queue
    }
}
