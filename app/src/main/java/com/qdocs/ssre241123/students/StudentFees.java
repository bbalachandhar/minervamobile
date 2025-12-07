package com.qdocs.ssre241123.students;

import static android.widget.Toast.makeText;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.qdocs.ssre241123.BaseActivity;
import com.qdocs.ssre241123.Login;
import com.qdocs.ssre241123.R;
import com.qdocs.ssre241123.TakeUrl;
import com.qdocs.ssre241123.adapters.SelectedPayAdapter;
import com.qdocs.ssre241123.adapters.StudentFeesAdapter;
import com.qdocs.ssre241123.utils.Constants;
import com.qdocs.ssre241123.utils.DatabaseHelper;
import com.qdocs.ssre241123.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class StudentFees extends BaseActivity {

    RecyclerView feesList;
    StudentFeesAdapter adapter;
    SelectedPayAdapter selectedPayAdapter;
     TextView total_fees;
    TextView payBtn;
    TextView gtAmtTV, gtDiscountTV, gtFineTV, gtPaidTV, gtBalanceTV,gtamtfineTV;
    CardView grandTotalLay;
    ArrayList <String> feesIdList = new ArrayList<String>();
    ArrayList <String> feesCodeList = new ArrayList<String>();
    ArrayList <String> feesnameList = new ArrayList<String>();
    ArrayList <String> dueDateList = new ArrayList<String>();
    ArrayList <String> amtList = new ArrayList<String>();
    ArrayList <String> amtfineList = new ArrayList<String>();
    ArrayList <String> paidAmtList = new ArrayList<String>();
    ArrayList <String> discAmtList = new ArrayList<String>();
    ArrayList <String> fineAmtList = new ArrayList<String>();
    ArrayList <String> balanceAmtList = new ArrayList<String>();
    ArrayList <String> statusList = new ArrayList<String>();
    ArrayList <String> feesDepositIdList = new ArrayList<String>();
    ArrayList <String> feesSessionIdList = new ArrayList<String>();
    ArrayList <String> feesessiongroupidIdList = new ArrayList<String>();
    ArrayList <String> feesDetails = new ArrayList<String>();
    ArrayList <String> feesTypeId = new ArrayList<String>();
    ArrayList <String> feesCat = new ArrayList<String>();
    ArrayList <String> discountNameList = new ArrayList<String>();
    ArrayList <String> discountAmtList = new ArrayList<String>();
    ArrayList <String> discountpayment_idList = new ArrayList<String>();
    ArrayList <String> discountStatusList = new ArrayList<String>();
    ArrayList <String> transportfeesnameList = new ArrayList<String>();
    ArrayList <String> transportdueDateList = new ArrayList<String>();
    ArrayList <String> transportamtList = new ArrayList<String>();
    ArrayList <String> transportamtfineList = new ArrayList<String>();
    ArrayList <String> transportpaidAmtList = new ArrayList<String>();
    ArrayList <String> transportdiscAmtList = new ArrayList<String>();
    ArrayList <String> transportfineAmtList = new ArrayList<String>();
    ArrayList <String> transportbalanceAmtList = new ArrayList<String>();
    ArrayList <String> transportfeesDepositIdList = new ArrayList<String>();
    ArrayList <String> transportstatusList = new ArrayList<String>();
    ArrayList <String> feenametypeList = new ArrayList<String>();
    ArrayList <String> feecode = new ArrayList<String>();
    ArrayList <String> feecategory = new ArrayList<String>();
    ArrayList <String> feeamount = new ArrayList<String>();
    ArrayList <String> feefineamount = new ArrayList<String>();
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, Object> payparams = new Hashtable<String, Object>();
    public Map<String, String>  headers = new HashMap<String, String>();
    TextView headerTV,offlinePayment,processingfees,fees,payselected;
    SwipeRefreshLayout pullToRefresh;
    CardView card_view_outer;
    String device_token;
    public Map<String, String> logoutparams = new Hashtable<String, String>();
    String is_offline_fee_payment;
    CheckBox allCheckBox;
    ArrayList<Boolean> isCheckedList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.students_fees_activity, null, false);
        mDrawerLayout.addView(contentView, 0);

        titleTV.setText(getApplicationContext().getString(R.string.fees));
        device_token = FirebaseInstanceId.getInstance().getToken()+"";
        Log.e(" logout DEVICE TOKEN", device_token);
       // makeText(this, Utility.getSharedPreferences(getApplicationContext(),Constants.currency), Toast.LENGTH_SHORT).show();
        feesList = (RecyclerView) findViewById(R.id.studentFees_listview);
        card_view_outer = findViewById(R.id.card_view_outer);
        card_view_outer.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
        gtAmtTV = findViewById(R.id.fees_amtTV);
        gtamtfineTV = findViewById(R.id.fees_amtfineTV);
        gtDiscountTV = findViewById(R.id.fees_discountTV);
        gtFineTV = findViewById(R.id.fees_fineTV);
        gtPaidTV = findViewById(R.id.fees_paidTV);
        gtBalanceTV = findViewById(R.id.fees_balance);
        //allCheckBox = findViewById(R.id.allCheckBox);
        grandTotalLay = findViewById(R.id.feesAdapter_containerCV);
        fees = findViewById(R.id.fees);
        processingfees = findViewById(R.id.processingfees);
        //payselected = findViewById(R.id.payselected);
        offlinePayment = findViewById(R.id.offlinePayment);
        offlinePayment.setVisibility(View.VISIBLE);
        if(Utility.getSharedPreferencesBoolean(getApplicationContext(), Constants.isLock)) {
            logout.setVisibility(View.VISIBLE);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(StudentFees.this);
                    builder.setCancelable(false);
                    builder.setMessage(R.string.logoutMsg);
                    builder.setTitle("");
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            if (Utility.isConnectingToInternet(getApplicationContext())) {
                                logoutparams.put("deviceToken", device_token);
                                JSONObject obj=new JSONObject(logoutparams);
                                Log.e("params ", obj.toString());
                                System.out.println("Logout Details=="+obj.toString());
                                loginOutApi(obj.toString());
                            } else {
                                makeText(getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    android.app.AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }else{
            logout.setVisibility(View.GONE);
        }

        if(Utility.isConnectingToInternet(StudentFees.this)){
            StudentOfflineStatus();
        }else{
            makeText(getApplicationContext(), R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
        }

        headerTV = findViewById(R.id.fees_headTV);

        adapter = new StudentFeesAdapter(StudentFees.this, feesIdList, feesnameList,feesCodeList, dueDateList, amtList,
                paidAmtList, balanceAmtList, feesDepositIdList,feesSessionIdList, statusList, feesDetails, feesTypeId, feesCat,
                discountNameList, discountAmtList, discountStatusList,discountpayment_idList,discAmtList,fineAmtList,amtfineList,
        transportdueDateList, transportamtfineList, transportpaidAmtList, transportdiscAmtList, transportbalanceAmtList, transportfeesDepositIdList,transportamtList,transportfineAmtList,transportstatusList,isCheckedList,feesessiongroupidIdList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        feesList.setLayoutManager(mLayoutManager);
        feesList.setItemAnimator(new DefaultItemAnimator());
        feesList.setAdapter(adapter);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh.setRefreshing(true);
                loaddata();
            }
        });
        offlinePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentFees.this,StudentOfflinePaymentList.class);
                startActivity(intent);
            }
        });
    /*    allCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            adapter.selectAll(isChecked);
        });
*/
        processingfees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StudentFees.this,StudentProcessingFees.class);
                startActivity(intent);
            }
        });
        /*payselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray arrayFromAdapter = adapter.getJsonArray();
                System.out.println("Activity JSONArray: " + arrayFromAdapter.toString());

                final Dialog dialog = new Dialog(StudentFees.this);
                Utility.setLocale(getApplicationContext(), Utility.getSharedPreferences(getApplicationContext(), Constants.langCode));
                dialog.setContentView(R.layout.pay_selected_layout);
                dialog.setCanceledOnTouchOutside(false);
                RelativeLayout headerLay = (RelativeLayout) dialog.findViewById(R.id.addTask_dialog_header);
                final RecyclerView recyclerview = (RecyclerView) dialog.findViewById(R.id.recyclerview);
                selectedPayAdapter = new SelectedPayAdapter(StudentFees.this,recyclerview, feenametypeList, feeamount, feecode, feefineamount,feecategory);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerview.setLayoutManager(mLayoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(selectedPayAdapter);
                total_fees = (TextView) dialog.findViewById(R.id.total_fees);
                payBtn = (TextView) dialog.findViewById(R.id.payBtn);
                ImageView closeBtn = (ImageView) dialog.findViewById(R.id.addTask_dialog_crossIcon);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                payBtn.setText(Utility.getSharedPreferences(getApplicationContext(), Constants.currency) + " " + getApplicationContext().getString(R.string.pay));


                if(Utility.isConnectingToInternet(getApplicationContext())){
                    payparams.put("fees_data",arrayFromAdapter);
                    payparams.put("student_id", Utility.getSharedPreferences(getApplicationContext(), "studentId"));
                    JSONObject obj=new JSONObject(payparams);
                    Log.e("params ", obj.toString());
                    System.out.println("params== "+ obj.toString());
                    getSelectedDataFromApi(obj.toString());
                }else{
                    makeText(getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                }

                headerLay.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.primaryColour)));
                dialog.show();
            }
        });*/

        loaddata();


        headerTV.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(getApplicationContext(), Constants.secondaryColour)));
    }
    @Override
    public void onRestart() {
        super.onRestart();
        loaddata();
        Utility.setLocale(getApplicationContext(), Utility.getSharedPreferences(getApplicationContext(), Constants.langCode));
        // do some stuff here
    }

    public  void  loaddata(){
        if(Utility.isConnectingToInternet(getApplicationContext())){
            params.put("student_id", Utility.getSharedPreferences(getApplicationContext(), "studentId"));
            JSONObject obj=new JSONObject(params);
            Log.e("params ", obj.toString());
            getDataFromApi(obj.toString());
        }else{
            makeText(getApplicationContext(),R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
        }

    }

    private void getSelectedDataFromApi(String bodyParams) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;
        String url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl")+Constants.getSelectedFeesPayUrl;
        Log.e("Forgot Password Url", url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null) {
                    pd.dismiss();
                    try {
                        float sum = 0;
                        Log.e("Result", result);
                        JSONObject object = new JSONObject(result);
                        JSONArray resultarray=object.getJSONArray("student_fees_master_array");
                        //JSONObject resultarray=object.getString("redirect_url");

                        payBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                Intent asd = new Intent(getApplicationContext(), MultiplePayment.class);
                                asd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                asd.putExtra("redirect_url", object.getString("redirect_url"));
                                startActivity(asd);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

                        if(resultarray.length() != 0) {
                            feenametypeList.clear();
                            feecode.clear();
                            feeamount.clear();
                            feefineamount.clear();
                            feecategory.clear();
                            for(int i = 0; i < resultarray.length(); i++) {
                               if(resultarray.getJSONObject(i).getString("fee_category").equals("fees")) {
                                   feenametypeList.add(resultarray.getJSONObject(i).getString("fee_group_name") /*+ " (" + resultarray.getJSONObject(i).getString("type") + ")"*/);
                               }else{
                                   feenametypeList.add(resultarray.getJSONObject(i).getString("fee_group_name"));
                               }
                                feecode.add(resultarray.getJSONObject(i).getString("fee_type_code"));
                                feecategory.add(resultarray.getJSONObject(i).getString("fee_category"));
                                feeamount.add(currency + Utility.changeAmount(resultarray.getJSONObject(i).getString("amount_balance"), currency, currency_price));
                                feefineamount.add(currency + Utility.changeAmount(resultarray.getJSONObject(i).getString("fine_balance"), currency, currency_price));
                                sum += Float.parseFloat(resultarray.getJSONObject(i).getString("amount_balance"));
                                sum += Float.parseFloat(resultarray.getJSONObject(i).getString("fine_balance"));
                                total_fees.setText(currency+  Utility.changeAmount(String.format("%.2f", sum),currency,currency_price));
                            }
                            selectedPayAdapter.notifyDataSetChanged();
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
                Log.e("Volley Error 1", volleyError.toString());
                Toast.makeText(StudentFees.this, R.string.invalidUsername, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                headers.put("Client-Service", Constants.clientService);
                headers.put("Auth-Key", Constants.authKey);
                headers.put("Content-Type", Constants.contentType);
                headers.put("User-ID", Utility.getSharedPreferences(getApplicationContext(), "userId"));
                headers.put("Authorization", Utility.getSharedPreferences(getApplicationContext(), "accessToken"));
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
        //SETTING RETRY Policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(StudentFees.this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getDataFromApi (String bodyParams) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;

        String url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl")+ Constants.getFeesUrl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                pullToRefresh.setRefreshing(false);
                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Result", result);
                        JSONObject object = new JSONObject(result);

                        feesIdList.clear();
                        feesCodeList.clear();
                        dueDateList.clear();
                        amtList.clear();
                        paidAmtList.clear();
                        discAmtList.clear();
                        fineAmtList.clear();
                        balanceAmtList.clear();
                        feesDepositIdList.clear();
                        feesSessionIdList.clear();
                        feesessiongroupidIdList.clear();
                        feesTypeId.clear();
                        feesCat.clear();
                        statusList.clear();
                        feesDetails.clear();
                        amtfineList.clear();
                        transportdueDateList.clear();
                        transportamtList.clear();
                        transportamtfineList.clear();
                        transportpaidAmtList.clear();
                        transportdiscAmtList.clear();
                        transportfineAmtList.clear();
                        transportbalanceAmtList.clear();
                        transportfeesDepositIdList.clear();
                        transportstatusList.clear();

                        String success = "1";
                        if (success.equals("1")) {
                            if(object.getString("pay_method").equals("0")) {
                                Log.e("test", "testing");
                                Utility.setSharedPreferenceBoolean(getApplicationContext(), Constants.showPaymentBtn, false);
                            } else {
                                Utility.setSharedPreferenceBoolean(getApplicationContext(), Constants.showPaymentBtn, true);
                            }
                            String  currency = Utility.getSharedPreferences(getApplicationContext(), Constants.currency);
                            String  currency_price =  Utility.getSharedPreferences(getApplicationContext(), Constants.currency_price);
                            JSONObject grandTotalDetails = object.getJSONObject("grand_fee");

                            /*String amount= Utility.changeAmount(grandTotalDetails.getString("amount"), Utility.getSharedPreferences(getApplicationContext(), Constants.currency));
                            System.out.println("Amount=="+amount);*/
                            gtAmtTV.setText(currency + Utility.changeAmount(grandTotalDetails.getString("amount"),currency,currency_price));
                            gtamtfineTV.setText("+ " + Utility.changeAmount(grandTotalDetails.getString("fee_fine"),currency,currency_price));
                            gtDiscountTV.setText(currency + Utility.changeAmount(grandTotalDetails.getString("amount_discount"),currency,currency_price));
                            gtFineTV.setText(currency + Utility.changeAmount(grandTotalDetails.getString("amount_fine"),currency,currency_price));
                            gtPaidTV.setText(currency + Utility.changeAmount(grandTotalDetails.getString("amount_paid"),currency,currency_price));
                            gtBalanceTV.setText(currency + Utility.changeAmount(grandTotalDetails.getString("amount_remaining"),currency,currency_price));

                           // Double amount=Double.parseDouble(Utility.changeAmount(grandTotalDetails.getString("fee_fine"),currency,currency_price));

                            JSONArray dataArray = object.getJSONArray("student_due_fee");

                            if(dataArray.length() != 0) {
                                grandTotalLay.setVisibility(View.VISIBLE);

                                for(int i = 0; i < dataArray.length(); i++) {

                                    JSONArray feesArray = dataArray.getJSONObject(i).getJSONArray("fees");

                                    for(int j = 0; j<feesArray.length(); j++) {
                                        feesIdList.add(feesArray.getJSONObject(j).getString("id"));
                                        feesnameList.add(feesArray.getJSONObject(j).getString("name") + "-" + feesArray.getJSONObject(j).getString("type"));
                                        feesCodeList.add(feesArray.getJSONObject(j).getString("code"));

                                        dueDateList.add(feesArray.getJSONObject(j).getString("due_date"));
                                        amtList.add( currency + Utility.changeAmount(feesArray.getJSONObject(j).getString("amount"),currency,currency_price));
                                        if(feesArray.getJSONObject(j).getString("fees_fine_amount").equals("0")){
                                            amtfineList.add("");
                                        }else {
                                            amtfineList.add("+" + Utility.changeAmount(feesArray.getJSONObject(j).getString("fees_fine_amount"), currency, currency_price));
                                        }
                                        paidAmtList.add(currency + Utility.changeAmount(feesArray.getJSONObject(j).getString("total_amount_paid"),currency,currency_price));
                                        discAmtList.add(currency + Utility.changeAmount(feesArray.getJSONObject(j).getString("total_amount_discount"),currency,currency_price));
                                        fineAmtList.add(currency + Utility.changeAmount(feesArray.getJSONObject(j).getString("total_amount_fine"),currency,currency_price));
                                        balanceAmtList.add(currency + Utility.changeAmount(feesArray.getJSONObject(j).getString("total_amount_remaining"),currency,currency_price));
                                        feesDepositIdList.add(feesArray.getJSONObject(j).getString("student_fees_deposite_id"));
                                        feesSessionIdList.add(feesArray.getJSONObject(j).getString("student_session_id"));
                                        feesessiongroupidIdList.add(feesArray.getJSONObject(j).getString("fee_session_group_id"));
                                        feesTypeId.add(feesArray.getJSONObject(j).getString("fee_groups_feetype_id"));
                                        feesCat.add("fees");
                                        isCheckedList.add(false);
                                        discountNameList.add("");
                                        discountAmtList.add("");
                                        discountStatusList.add("");
                                        transportdueDateList.add("");
                                        transportamtList.add("");
                                        transportamtfineList.add("");
                                        transportpaidAmtList.add("");
                                        transportdiscAmtList.add("");
                                        transportfineAmtList.add("");
                                        transportbalanceAmtList.add("");
                                        transportfeesDepositIdList.add("");
                                        transportstatusList.add("");


                                        statusList.add(feesArray.getJSONObject(j).getString("status").substring(0, 1).toUpperCase() + feesArray.getJSONObject(j).getString("status").substring(1));
                                        System.out.println("statusList="+statusList.size());
                                        JSONObject feesDetailsJson;
                                        try {
                                            feesDetailsJson = new JSONObject(feesArray.getJSONObject(j).getString("amount_detail"));
                                        } catch (JSONException e) {
                                            feesDetailsJson = new JSONObject();
                                        }
                                        feesDetails.add(feesDetailsJson.toString());
                                    }
                                }
                            }

                            JSONArray transportfeesArray = object.getJSONArray("transport_fees");

                                for (int l = 0; l < transportfeesArray.length(); l++) {
                                    feesIdList.add(transportfeesArray.getJSONObject(l).getString("id"));
                                    feesCodeList.add(transportfeesArray.getJSONObject(l).getString("month"));
                                    feesSessionIdList.add(transportfeesArray.getJSONObject(l).getString("student_session_id"));
                                    transportdueDateList.add(transportfeesArray.getJSONObject(l).getString("due_date"));
                                    transportamtList.add(currency + Utility.changeAmount(transportfeesArray.getJSONObject(l).getString("fees"), currency, currency_price));
                                    transportamtfineList.add("+" + Utility.changeAmount(transportfeesArray.getJSONObject(l).getString("fees_fine_amount"), currency, currency_price));
                                    transportpaidAmtList.add(currency + Utility.changeAmount(transportfeesArray.getJSONObject(l).getString("total_amount_paid"), currency, currency_price));
                                    transportdiscAmtList.add(currency + Utility.changeAmount(transportfeesArray.getJSONObject(l).getString("total_amount_discount"), currency, currency_price));
                                    transportfineAmtList.add(currency + Utility.changeAmount(transportfeesArray.getJSONObject(l).getString("total_amount_fine"), currency, currency_price));
                                    transportbalanceAmtList.add(currency + Utility.changeAmount(transportfeesArray.getJSONObject(l).getString("total_amount_remaining"), currency, currency_price));
                                    transportfeesDepositIdList.add(transportfeesArray.getJSONObject(l).getString("student_fees_deposite_id"));
                                    transportstatusList.add(transportfeesArray.getJSONObject(l).getString("status").substring(0, 1).toUpperCase() + transportfeesArray.getJSONObject(l).getString("status").substring(1));
                                    feesCat.add("transport");
                                    isCheckedList.add(false);
                                    discountNameList.add("");
                                    discountAmtList.add("");
                                    discountStatusList.add("");
                                    feesessiongroupidIdList.add("0");
                                    statusList.add("");
                                    feesTypeId.add("");
                                    JSONObject feesDetailsJson;
                                    try {
                                        feesDetailsJson = new JSONObject(transportfeesArray.getJSONObject(l).getString("amount_detail"));
                                    } catch (JSONException e) {
                                        feesDetailsJson = new JSONObject();
                                    }
                                    feesDetails.add(feesDetailsJson.toString());
                                    System.out.println("transportstatusList=" + transportstatusList.size());
                                }

                            // Select All Logic
                          /*  allCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                adapter.selectAll(isChecked);
                            });*/
                            adapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(getApplicationContext(), object.getString("errorMsg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    pd.dismiss();
                    pullToRefresh.setVisibility(View.GONE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(StudentFees.this, R.string.apiErrorMsg, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(StudentFees.this);//Creating a Request Queue
        requestQueue.add(stringRequest); //Adding request to the queue
    }
    private void loginOutApi (String bodyParams) {
        DatabaseHelper dataBaseHelpers = new DatabaseHelper(StudentFees.this);
        dataBaseHelpers.deleteAll() ;

        final ProgressDialog pd = new ProgressDialog(StudentFees.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;
        String url = Utility.getSharedPreferences(StudentFees.this, "apiUrl")+ Constants.logoutUrl;
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
                            Utility.setSharedPreferenceBoolean(getApplicationContext(), "isLoggegIn", false);
                            Intent logout = new Intent(StudentFees.this, Login.class);
                            logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            logout.putExtra("EXIT", true);
                            startActivity(logout);
                            finish();
                        } else {
                            Intent intent=new Intent(StudentFees.this, TakeUrl.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(StudentFees.this, R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                // Toast.makeText(StudentDashboard.this, R.string.apiErrorMsg, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(StudentFees.this,TakeUrl.class);
                startActivity(intent);
                finish();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                headers.put("Client-Service", Constants.clientService);
                headers.put("Auth-Key", Constants.authKey);
                headers.put("Content-Type", Constants.contentType);
                headers.put("User-ID", Utility.getSharedPreferences(StudentFees.this, "userId"));
                headers.put("Authorization", Utility.getSharedPreferences(StudentFees.this, "accessToken"));
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
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(StudentFees.this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    private void StudentOfflineStatus() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        String url = Utility.getSharedPreferences(getApplicationContext(), "apiUrl")+ Constants.getOfflineBankPaymentStatusUrl;
        System.out.println("url=="+url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                pd.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    is_offline_fee_payment = object.getString("is_offline_fee_payment");
                    Utility.setSharedPreference(getApplicationContext(),"is_offline_fee_payment",is_offline_fee_payment);
                    System.out.println("student_timeline="+is_offline_fee_payment);
                    if(is_offline_fee_payment.equals("1")){
                        offlinePayment.setVisibility(View.VISIBLE);
                    }else{
                        offlinePayment.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                Log.e("Volley Error", volleyError.toString());
                Toast.makeText(StudentFees.this, R.string.apiErrorMsg, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(StudentFees.this);//Creating a Request Queue
        requestQueue.add(stringRequest); //Adding request to the queue
    }
}
