package com.qdocs.ssre241123.adapters;

import static android.widget.Toast.makeText;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.qdocs.ssre241123.R;
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

public class StudentFeesDetailAdapter extends RecyclerView.Adapter<StudentFeesDetailAdapter.MyViewHolder> {
    StudentFeesDiscountDetailAdapter studentFeesDiscountDetailAdapter;
    private FragmentActivity context;
    TextView nodata_layout;
    RecyclerView recyclerView;
    private ArrayList<String> paymentIdList;
    private ArrayList<String> paymentDateList;
    private ArrayList<String> paymentDiscountList;
    private ArrayList<String> paymentFineList;
    private ArrayList<String> paymentPaidList;
    private ArrayList<String> paymentNoteList;
    private ArrayList<String> feesDateList= new ArrayList<String>();
    private ArrayList<String> feestypeList= new ArrayList<String>();
    private ArrayList<String> feespercentageList= new ArrayList<String>();
    private ArrayList<String> feesDiscountList= new ArrayList<String>();
    private ArrayList<String> feesvalueList= new ArrayList<String>();
    private ArrayList<String> paymentfeesIdList= new ArrayList<String>();
    String depositeId;
    String feesType;
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, String>  headers = new HashMap<String, String>();


    public StudentFeesDetailAdapter(FragmentActivity studentsFees, ArrayList<String> paymentId,
                                    ArrayList<String> paymentDate, ArrayList<String> paymentDiscount,
                                    ArrayList<String> paymentFine, ArrayList<String> paymentPaid, ArrayList<String> paymentNote,String feesType, String depositeId) {
        this.context = studentsFees;
        this.paymentIdList = paymentId;
        this.paymentDateList = paymentDate;
        this.paymentDiscountList = paymentDiscount;
        this.paymentFineList = paymentFine;
        this.paymentPaidList = paymentPaid;
        this.paymentNoteList = paymentNote;
        this.feesType = feesType;
        this.depositeId = depositeId;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView paymentId, paymentDate, totalfees,paymentDiscount, paymentFine, paymentPaid, paymentNote,collected_by;
        LinearLayout viewContainer;

        public MyViewHolder(View view) {
            super(view);

            paymentId = (TextView) view.findViewById(R.id.adapter_student_feesDetail_paymentId);
            paymentDate = (TextView) view.findViewById(R.id.adapter_student_feesDetail_paymentDate);
            paymentDiscount = (TextView) view.findViewById(R.id.adapter_student_feesDetail_paymentDiscount);
            paymentFine = (TextView) view.findViewById(R.id.adapter_student_feesDetail_paymentFine);
            paymentPaid = (TextView) view.findViewById(R.id.adapter_student_feesDetail_paymentPaid);
            paymentNote = (TextView) view.findViewById(R.id.adapter_student_feesDetail_paymentNoteTV);
            viewContainer = (LinearLayout) view.findViewById(R.id.adapter_student_feesDetail_viewContainer);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_student_fees_detail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        String currency = Utility.getSharedPreferences(context.getApplicationContext(), Constants.currency);
        String  currency_price =  Utility.getSharedPreferences(context.getApplicationContext(), Constants.currency_price);

        holder.paymentId.setText(paymentIdList.get(position));
        holder.paymentDate.setText(paymentDateList.get(position));
        holder.paymentDiscount.setText(currency +   Utility.changeAmount(paymentDiscountList.get(position),currency,currency_price));
        holder.paymentFine.setText(currency +   Utility.changeAmount(paymentFineList.get(position),currency,currency_price) );
        holder.paymentPaid.setText(currency +  Utility.changeAmount(paymentPaidList.get(position),currency,currency_price));


        if(paymentNoteList.get(position).equals("")){
            holder.paymentNote.setVisibility(View.GONE);
        } else {
            holder.paymentNote.setVisibility(View.VISIBLE);
            holder.paymentNote.setText(paymentNoteList.get(position));
        }


        holder.viewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog(context);
            }
        });
    }

    private void showAddDialog(Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.fees_dicount_list);

        RelativeLayout headerLay = (RelativeLayout) dialog.findViewById(R.id.add_dialog_header);
        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.add_dialog_crossIcon);
         recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerview);
         nodata_layout = (TextView) dialog.findViewById(R.id.nodata_layout);
        studentFeesDiscountDetailAdapter = new StudentFeesDiscountDetailAdapter(context,feesDateList,paymentfeesIdList,feesDiscountList,feesvalueList,feestypeList,feespercentageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(studentFeesDiscountDetailAdapter);

        if(Utility.isConnectingToInternet(context)){
            params.put("student_fees_deposite",depositeId);
            JSONObject obj=new JSONObject(params);
            Log.e("params ", obj.toString());
            System.out.println("params ==="+ obj.toString());
            getDiscountListApi(obj.toString());
        }else{
            makeText(context, R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //DECORATE
        headerLay.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.primaryColour)));
        //DECORATE
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return paymentIdList.size();
    }


    private void getDiscountListApi (String bodyParams) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;

        String url = Utility.getSharedPreferences(context, "apiUrl")+Constants.getAppliedDiscountsUrl;
        Log.e("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null) {
                    pd.dismiss();
                    try {
                        Log.e("Result", result);
                        JSONObject object = new JSONObject(result);
                        String defaultDateFormat = Utility.getSharedPreferences(context.getApplicationContext(), "dateFormat");
                        JSONArray discount_feearray = object.getJSONArray("result");
                        paymentfeesIdList.clear();
                        feesvalueList.clear();
                        feesDiscountList.clear();
                        feesDateList.clear();
                        feestypeList.clear();
                        feespercentageList.clear();
                        if (discount_feearray.length() != 0) {
                            nodata_layout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            for(int i = 0; i < discount_feearray.length(); i++) {
                                paymentfeesIdList.add(discount_feearray.getJSONObject(i).getString("invoice_id")+"/"+discount_feearray.getJSONObject(i).getString("sub_invoice_id"));
                                feesvalueList.add(discount_feearray.getJSONObject(i).getString("amount"));
                                feestypeList.add(discount_feearray.getJSONObject(i).getString("type"));
                                feespercentageList.add(discount_feearray.getJSONObject(i).getString("percentage"));
                                feesDiscountList.add(discount_feearray.getJSONObject(i).getString("name"));
                                feesDateList.add( Utility.parseDate("yyyy-MM-dd", defaultDateFormat, discount_feearray.getJSONObject(i).getString("date")));
                            }
                            studentFeesDiscountDetailAdapter.notifyDataSetChanged();

                        }else{
                            nodata_layout.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
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
                headers.put("User-ID", Utility.getSharedPreferences(context, "userId"));
                headers.put("Authorization", Utility.getSharedPreferences(context, "accessToken"));
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
        RequestQueue requestQueue = Volley.newRequestQueue(context); //Creating a Request Queue
        requestQueue.add(stringRequest); //Adding request to the queue
    }
}

