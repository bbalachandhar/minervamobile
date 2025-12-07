package com.qdocs.ssre241123.adapters;

import static android.widget.Toast.makeText;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.qdocs.ssre241123.R;
import com.qdocs.ssre241123.model.MyModel;
import com.qdocs.ssre241123.students.MultiplePayment;
import com.qdocs.ssre241123.students.StudentFees;
import com.qdocs.ssre241123.students.StudentOfflinePayment;
import com.qdocs.ssre241123.utils.Constants;
import com.qdocs.ssre241123.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StudentFeesAdapter extends RecyclerView.Adapter<StudentFeesAdapter.MyViewHolder>  {
    private StudentFees context;

    TextView dateTV,amountTV,fineTV,discountTV,discountamountTV,cancelBtn,feespayBtn,negativeamountTV;
    Double amountvalue=0.00,discount=0.00, disval=0.00;
    String  currency,currency_price,feescat;
    LinearLayout discountLayout;
    RecyclerView recyclerview;
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    public Map<String, String> params = new Hashtable<String, String>();
    public Map<String, Object> disparams = new Hashtable<String, Object>();
    public Map<String, String>  headers = new HashMap<String, String>();
    private ArrayList<String> feesIdList;
    FeesDiscountGroupAdapter feesDiscountGroupAdapter;
    private List<MyModel> itemList = new ArrayList<>();

    ArrayList <String> idList = new ArrayList<String>();
    ArrayList <String> codeList = new ArrayList<String>();
    ArrayList <String> nameList = new ArrayList<String>();
    ArrayList <String> typeList = new ArrayList<String>();
    ArrayList <String> percentageList = new ArrayList<String>();
    String  balanceAmount;
    ArrayList <String> countList = new ArrayList<String>();
    ArrayList <String> amountList = new ArrayList<String>();
    private ArrayList<String> feesCodeList;
    private ArrayList<String> dueDateList;
    private ArrayList<String> amtList;
    private ArrayList<String> balanceAmtList;
    private ArrayList<String> paidAmtList;
    private ArrayList<String> depositId;
    private ArrayList<String> feesSessionIdList;
    private ArrayList<String> statusList;
    private ArrayList<String> feesDetails;
    private ArrayList<String> feesTypeId;
    private ArrayList<String> feesCatList;
    private ArrayList<String> discountNameList;
    private ArrayList<String> discountStatusList;
    private ArrayList<String> discountAmtList;
    private ArrayList<String> discountpayment_idList;
    private ArrayList<String> discAmtList;
    private ArrayList<String> feesnameList;
    private ArrayList<String> fineAmtList;
    private ArrayList<String> amtfineList;
    private ArrayList<String> transportdueDateList;
    private ArrayList<String> transportamtfineList;
    private ArrayList<String> transportpaidAmtList;
    private ArrayList<String> transportdiscAmtList;
    private ArrayList<String> transportbalanceAmtList;
    private ArrayList<String> transportfeesDepositIdList;
    private ArrayList<String> transportstatusList;
    private ArrayList<String> transportamtList;
    private ArrayList<String> feesessiongroupidIdList;
    private ArrayList<String> transportfineAmtList;
    private ArrayList<Boolean> checkBoxItems;
    private JSONArray disjsonArray = new JSONArray();
    String fees_discount,is_offline_fee_payment;
    Double discountamountvalue=0.0;
     Dialog disdialog;

    public StudentFeesAdapter(StudentFees activity, ArrayList<String> feesIdList, ArrayList<String> feesnameList, ArrayList<String> feesCodeList,
                              ArrayList<String> dueDateList, ArrayList<String> amtList, ArrayList<String> paidAmtList,
                              ArrayList<String> balanceAmtList, ArrayList<String> feesDepositIdList, ArrayList<String> feesSessionIdList, ArrayList<String> statusList,
                              ArrayList<String> feesDetails, ArrayList<String> feesTypeId, ArrayList<String> feesCatList,
                              ArrayList<String> discountNameList, ArrayList<String> discountAmtList,
                              ArrayList<String> discountStatusList, ArrayList<String> discountpayment_idList, ArrayList<String> discAmtList,
                              ArrayList<String> fineAmtList, ArrayList<String> amtfineList, ArrayList<String> transportdueDateList, ArrayList<String> transportamtfineList,
                              ArrayList<String> transportpaidAmtList, ArrayList<String> transportdiscAmtList, ArrayList<String> transportbalanceAmtList,
                              ArrayList<String> transportfeesDepositIdList, ArrayList<String> transportamtList, ArrayList<String> transportfineAmtList, ArrayList<String> transportstatusList,ArrayList<Boolean> checkBoxItems, ArrayList<String> feesessiongroupidIdList) {

        this.context = activity;
        this.feesIdList = feesIdList;
        this.feesnameList = feesnameList;
        this.feesCodeList = feesCodeList;
        this.dueDateList = dueDateList;
        this.amtList = amtList;
        this.balanceAmtList = balanceAmtList;
        this.paidAmtList = paidAmtList;
        this.depositId = feesDepositIdList;
        this.feesSessionIdList = feesSessionIdList;
        this.statusList = statusList;
        this.feesDetails = feesDetails;
        this.feesTypeId = feesTypeId;
        this.feesCatList = feesCatList;
        this.transportstatusList = transportstatusList;
        this.discountNameList = discountNameList;
        this.discountStatusList = discountStatusList;
        this.discountAmtList = discountAmtList;
        this.discountpayment_idList = discountpayment_idList;
        this.fineAmtList = fineAmtList;
        this.discAmtList = discAmtList;
        this.amtfineList = amtfineList;
        this.transportdueDateList = transportdueDateList;
        this.transportamtfineList = transportamtfineList;
        this.transportpaidAmtList = transportpaidAmtList;
        this.transportdiscAmtList = transportdiscAmtList;
        this.transportbalanceAmtList = transportbalanceAmtList;
        this.transportfeesDepositIdList = transportfeesDepositIdList;
        this.transportfineAmtList = transportfineAmtList;
        this.transportamtList = transportamtList;
        this.feesessiongroupidIdList = feesessiongroupidIdList;
        this.checkBoxItems = checkBoxItems;

    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView feecodeTV, feesDueDateTV, feesAmtTV, feesPaidAmtTV, feesDueAmtTV, feesStatusTV, payBtn,discountpayment_id,
                discountTV,fineTV,feefineTV,transportfeeDueDateTV,transportfeeAmtTV,transportfeefineTV,transportfineTV,transportdiscountTV,transportfeePaidAmtTV,transportfeeDueAmtTV,transportfeesAdapter_payBtn;
        private LinearLayout viewContainer, viewBtn, feesLay, discountLay,feescode_layout,transportfeesLay;
        private RelativeLayout header;
        CheckBox checkBox;
        private TextView discountNameTV, discountAmtTV,feesnameTV;

        public MyViewHolder(View rowView) {
            super(rowView);

            viewContainer = rowView.findViewById(R.id.studentFeesAdapter);
            feescode_layout = rowView.findViewById(R.id.feescode_layout);
            feesnameTV = rowView.findViewById(R.id.studentFeesAdapter_feenameTV);
            feecodeTV = rowView.findViewById(R.id.studentFeesAdapter_feecodeTV);
            feesDueDateTV = rowView.findViewById(R.id.studentFeesAdapter_feeDueDateTV);
            feesAmtTV = rowView.findViewById(R.id.studentFeesAdapter_feeAmtTV);
            feesPaidAmtTV = rowView.findViewById(R.id.studentFeesAdapter_feePaidAmtTV);
            discountTV = rowView.findViewById(R.id.studentFeesAdapter_discountTV);
            fineTV = rowView.findViewById(R.id.studentFeesAdapter_fineTV);
            feesDueAmtTV = rowView.findViewById(R.id.studentFeesAdapter_feeDueAmtTV);
            feesStatusTV = rowView.findViewById(R.id.feesAdapter_statusTV);
            header = rowView.findViewById(R.id.feesAdapter_nameHeader);
            viewBtn = rowView.findViewById(R.id.studentFeesAdapter_viewBtn);
            payBtn = rowView.findViewById(R.id.feesAdapter_payBtn);
            discountpayment_id = rowView.findViewById(R.id.studentFeesAdapter_discountpayment_idTV);
            feesLay = rowView.findViewById(R.id.studentFeesAdapter_feesLay);
            transportfeesLay = rowView.findViewById(R.id.studentFeesAdapter_transportfeesLay);
            discountLay = rowView.findViewById(R.id.studentFeesAdapter_discountLay);
            discountAmtTV = rowView.findViewById(R.id.studentFeesAdapter_discountAmtTV);
            feefineTV = rowView.findViewById(R.id.studentFeesAdapter_feefineTV);
            transportfeeDueDateTV = rowView.findViewById(R.id.transportfeeDueDateTV);
            transportfeeAmtTV = rowView.findViewById(R.id.transportfeeAmtTV);
            transportfeefineTV = rowView.findViewById(R.id.transportfeefineTV);
            transportfineTV = rowView.findViewById(R.id.transportfineTV);
            transportdiscountTV = rowView.findViewById(R.id.transportdiscountTV);
            transportfeePaidAmtTV = rowView.findViewById(R.id.transportfeePaidAmtTV);
            transportfeeDueAmtTV = rowView.findViewById(R.id.transportfeeDueAmtTV);
            transportfeesAdapter_payBtn = rowView.findViewById(R.id.transportfeesAdapter_payBtn);
            checkBox = rowView.findViewById(R.id.checkBox);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_student_fees, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {

       /* viewHolder.checkBox.setOnCheckedChangeListener(null); // reset listener for recycling issue
        viewHolder.checkBox.setChecked(checkBoxItems.get(position));

        viewHolder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkBoxItems.set(position, isChecked);  // update checked state in the list
            if (isChecked) {
                // get the item at that position and add to JSONArray
                JSONObject jsonObject = new JSONObject();
                try {
                    if( feesCatList.get(position).equals("fees")) {
                        jsonObject.put("fee_category", feesCatList.get(position));
                        jsonObject.put("trans_fee_id", "0");
                        jsonObject.put("fee_session_group_id", feesessiongroupidIdList.get(position));
                        jsonObject.put("fee_master_id", feesIdList.get(position));
                        jsonObject.put("fee_groups_feetype_id", feesTypeId.get(position));
                    }else if(feesCatList.get(position).equals("transport")){
                        jsonObject.put("fee_category", feesCatList.get(position));
                        jsonObject.put("trans_fee_id", feesIdList.get(position));
                        jsonObject.put("fee_session_group_id", feesessiongroupidIdList.get(position));
                        jsonObject.put("fee_master_id", "0");
                        jsonObject.put("fee_groups_feetype_id","0");
                    }
                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                    removeFromJsonArrayByFeeMasterId(feesTypeId.get(position));

                // Optional: remove from JSONArray if unchecked
            }
            System.out.println("JSONArray == " + jsonArray.toString());
        });
*/
        feescat=feesCatList.get(position);
        System.out.println("feescat=="+feescat);
        //FEES VIEW
        if(feesCatList.get(position).equals("fees")) {
            viewHolder.feesLay.setVisibility(View.VISIBLE);
            viewHolder.feescode_layout.setVisibility(View.VISIBLE);
            viewHolder.transportfeesLay.setVisibility(View.GONE);
            viewHolder.discountLay.setVisibility(View.GONE);
            showFeesCard(viewHolder, position);
        } else if(feesCatList.get(position).equals("transport")) {
            viewHolder.feesLay.setVisibility(View.GONE);
            viewHolder.feescode_layout.setVisibility(View.VISIBLE);
            viewHolder.transportfeesLay.setVisibility(View.VISIBLE);
            viewHolder.discountLay.setVisibility(View.GONE);
            showTransportFeesCard(viewHolder, position);
        }
        Log.e("payBtn", Utility.getSharedPreferencesBoolean(context, Constants.showPaymentBtn)+"..");
        viewHolder.viewContainer.setOnClickListener(null);
    }

    private void showDiscountCard(MyViewHolder viewHolder, final int position) {
        viewHolder.feesnameTV.setText(context.getString(R.string.paymentDiscount)+"-"+discountNameList.get(position));
        String discountMsg = context.getString(R.string.discountMsg) +" ";
        discountMsg += discountAmtList.get(position) + " " + discountStatusList.get(position) /*+" - " + discountpayment_idList.get(position)*/;
        viewHolder.discountAmtTV.setText(discountMsg);
    }

    private void showFeesCard(MyViewHolder viewHolder, final int position) {
        viewHolder.feesnameTV.setText(feesnameList.get(position));
        viewHolder.feecodeTV.setText(feesCodeList.get(position));
        String defaultDateFormat = Utility.getSharedPreferences(context.getApplicationContext(), "dateFormat");
        if(dueDateList.get(position).equals("0000-00-00")){
            viewHolder.feesDueDateTV.setText("No Due Date");
        }else{
            viewHolder.feesDueDateTV.setText(Utility.parseDate("yyyy-MM-dd", defaultDateFormat, dueDateList.get(position)));
        }
        viewHolder.feesAmtTV.setText(amtList.get(position));
        viewHolder.feefineTV.setText(amtfineList.get(position));

        viewHolder.fineTV.setText(fineAmtList.get(position));
        viewHolder.discountTV.setText(discAmtList.get(position));
        viewHolder.feesPaidAmtTV.setText(paidAmtList.get(position));
        viewHolder.feesDueAmtTV.setText(balanceAmtList.get(position));
    //    viewHolder.feesStatusTV.setText(statusList.get(position));

        if(statusList.get(position).equals("Paid")) {
            viewHolder.feesStatusTV.setText(context.getApplicationContext().getString(R.string.paid));
            viewHolder.feesStatusTV.setBackgroundResource(R.drawable.green_border);
            viewHolder.feesDueDateTV.setBackgroundResource(R.color.transparent);
//            viewHolder.payBtn.setVisibility(View.GONE);
            viewHolder.viewBtn.setVisibility(View.VISIBLE);
        } if (statusList.get(position).equals("Unpaid")) {
            viewHolder.feesStatusTV.setText(context.getApplicationContext().getString(R.string.unpaid));
            viewHolder.feesStatusTV.setBackgroundResource(R.drawable.red_border); viewHolder.feesDueDateTV.setBackgroundResource(R.color.transparent);

//            viewHolder.payBtn.setVisibility(View.VISIBLE);
            viewHolder.viewBtn.setVisibility(View.GONE);
            if(dueDateList.get(position).equals("0000-00-00")){
                viewHolder.feesDueDateTV.setText("No Due Date");
                viewHolder.feesDueDateTV.setBackgroundResource(R.color.transparent);
            }else{
                viewHolder.feesDueDateTV.setText(Utility.parseDate("yyyy-MM-dd", defaultDateFormat, dueDateList.get(position)));
                if(checkDueDate(dueDateList.get(position))) {
                    viewHolder.feesDueDateTV.setBackgroundResource(R.drawable.red_border);

                    float scale = context.getResources().getDisplayMetrics().density;
                    int dpAsPixels = (int) (5*scale + 0.5f);

                    viewHolder.feesDueDateTV.setPadding(dpAsPixels, 0, dpAsPixels, 0);
                    viewHolder.feesDueDateTV.setTextColor(Color.WHITE);

                } else {
                    viewHolder.feesDueDateTV.setBackgroundResource(R.color.transparent);
                }
            }

        } if (statusList.get(position).equals("Partial")) {
            viewHolder.feesStatusTV.setText(context.getApplicationContext().getString(R.string.partial));
            viewHolder.feesStatusTV.setBackgroundResource(R.drawable.yellow_border);
//          viewHolder.payBtn.setVisibility(View.VISIBLE);
            viewHolder.viewBtn.setVisibility(View.VISIBLE);
            if(checkDueDate(dueDateList.get(position))) {
                if(dueDateList.get(position).equals("0000-00-00")){
                    viewHolder.feesDueDateTV.setText(context.getApplicationContext().getString(R.string.noduedate));
                    viewHolder.feesDueDateTV.setBackgroundResource(R.color.transparent);
                }else{
                    viewHolder.feesDueDateTV.setBackgroundResource(R.drawable.red_border);
                }

                float scale = context.getResources().getDisplayMetrics().density;
                int dpAsPixels = (int) (5*scale + 0.5f);

                viewHolder.feesDueDateTV.setPadding(dpAsPixels, 0, dpAsPixels, 0);
                viewHolder.feesDueDateTV.setTextColor(Color.WHITE);

            } else {
                viewHolder.feesDueDateTV.setBackgroundResource(R.color.transparent);
            }
        }

        if(Utility.getSharedPreferencesBoolean(context, Constants.showPaymentBtn))  {
            viewHolder.payBtn.setVisibility(View.VISIBLE);

            if(statusList.get(position).equals("Paid")) {
                viewHolder.payBtn.setVisibility(View.GONE);
            } if (statusList.get(position).equals("Unpaid")) {
                viewHolder.payBtn.setVisibility(View.VISIBLE);
            } if (statusList.get(position).equals("Partial")) {
                viewHolder.payBtn.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.payBtn.setVisibility(View.GONE);
        }

        //DECORATE
        viewHolder.header.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));
        viewHolder.payBtn.setText(Utility.getSharedPreferences(context.getApplicationContext(), Constants.currency) + " " + context.getApplicationContext().getString(R.string.pay));
        //DECORATE

        viewHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickview) {

                ArrayList<String> paymentId = new ArrayList<>();
                ArrayList<String> paymentDate = new ArrayList<>();
                ArrayList<String> paymentDiscount = new ArrayList<>();
                ArrayList<String> paymentFine = new ArrayList<>();
                ArrayList<String> paymentPaid = new ArrayList<>();
                ArrayList<String> paymentNote = new ArrayList<>();


                JSONObject feesDetailsJson = new JSONObject();

                try {
                    feesDetailsJson = new JSONObject(feesDetails.get(position));

                    Iterator<String> iter = feesDetailsJson.keys();
                    while(iter.hasNext()){
                        String key = iter.next();

                        JSONObject object1 = feesDetailsJson.getJSONObject(key);
                        String paymentmode=object1.getString("payment_mode");
                        if(paymentmode.equals("upi")){
                            paymentmode=context.getApplicationContext().getString(R.string.upi);
                        }else if(paymentmode.equals("bank_transfer")){
                            paymentmode=context.getApplicationContext().getString(R.string.banktransfer);
                        }else if(paymentmode.equals("bank_payment")){
                            paymentmode=context.getApplicationContext().getString(R.string.bankpayment);
                        }else if(paymentmode.equals("Cash")){
                            paymentmode=context.getApplicationContext().getString(R.string.cash);
                        }else{
                            paymentmode=object1.getString("payment_mode");
                        }

                        paymentId.add(depositId.get(position) + "/" + object1.getString("inv_no") + "(" + paymentmode + ")" );
                        paymentDate.add(Utility.parseDate("yyyy-MM-dd", Utility.getSharedPreferences(context.getApplicationContext(), "dateFormat"), object1.getString("date")));
                        paymentDiscount.add(object1.getString("amount_discount"));
                        paymentFine.add(object1.getString("amount_fine"));
                        paymentPaid.add(object1.getString("amount"));
                        paymentNote.add(object1.getString("description"));
                    }
                } catch (JSONException je) {
                    Log.e("Error Parseing Data", je.toString());
                }

                View view = context.getLayoutInflater().inflate(R.layout.fragment_fees_bottom_sheet, null);
                view.setMinimumHeight(500);

                ImageView crossBtn = view.findViewById(R.id.fees_bottomSheet_crossBtn);
                TextView header = view.findViewById(R.id.fees_bottomSheet_header);
                RecyclerView hostelListView = view.findViewById(R.id.fees_bottomSheet_listview);

                header.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));

                StudentFeesDetailAdapter adapter = new StudentFeesDetailAdapter(context, paymentId, paymentDate, paymentDiscount, paymentFine, paymentPaid, paymentNote,"fees",depositId.get(position));
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
                hostelListView.setLayoutManager(mLayoutManager);
                hostelListView.setItemAnimator(new DefaultItemAnimator());
                hostelListView.setAdapter(adapter);
                final BottomSheetDialog dialog = new BottomSheetDialog(context);

                dialog.setContentView(view);
                dialog.show();

                crossBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        is_offline_fee_payment=Utility.getSharedPreferences(context.getApplicationContext(), "is_offline_fee_payment");
        if(is_offline_fee_payment.equals("1")){
            viewHolder.payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPaymentChooser(feesIdList.get(position),feesTypeId.get(position),feesSessionIdList.get(position),"fees","");
                }
            });
        }else{

            viewHolder.payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBalanceDialog(context,feesIdList.get(position),feesTypeId.get(position),"fees","",feesSessionIdList.get(position));
                }
            });
        }
    }

    private void showTransportFeesCard(MyViewHolder viewHolder, final int position) {
        viewHolder.feesnameTV.setText("Transport Fees");
        viewHolder.feecodeTV.setText(feesCodeList.get(position));
        String defaultDateFormat = Utility.getSharedPreferences(context.getApplicationContext(), "dateFormat");
        if(transportdueDateList.get(position).equals("0000-00-00")){
            viewHolder.transportfeeDueDateTV.setText("No Due Date");
        }else{
            viewHolder.transportfeeDueDateTV.setText(Utility.parseDate("yyyy-MM-dd", defaultDateFormat, transportdueDateList.get(position)));
        }
        viewHolder.transportfeeAmtTV.setText(transportamtList.get(position));
        if(transportamtfineList.get(position).equals("+.00")){
            viewHolder.transportfeefineTV.setText("");
        }else{
            viewHolder.transportfeefineTV.setText(transportamtfineList.get(position));
        }
        viewHolder.transportfineTV.setText(transportfineAmtList.get(position));
        viewHolder.transportdiscountTV.setText(transportdiscAmtList.get(position));
        viewHolder.transportfeePaidAmtTV.setText(transportpaidAmtList.get(position));
        viewHolder.transportfeeDueAmtTV.setText(transportbalanceAmtList.get(position));
        viewHolder.feesStatusTV.setText(transportstatusList.get(position));

        if(transportstatusList.get(position).equals("Paid")) {
            viewHolder.feesStatusTV.setText(context.getApplicationContext().getString(R.string.paid));
            viewHolder.feesStatusTV.setBackgroundResource(R.drawable.green_border);
            viewHolder.transportfeeDueDateTV.setBackgroundResource(R.color.transparent);
            viewHolder.payBtn.setVisibility(View.GONE);
            viewHolder.viewBtn.setVisibility(View.VISIBLE);
        } if (transportstatusList.get(position).equals("Unpaid")) {
            viewHolder.feesStatusTV.setText(context.getApplicationContext().getString(R.string.unpaid));
            viewHolder.feesStatusTV.setBackgroundResource(R.drawable.red_border);
            viewHolder.transportfeeDueDateTV.setBackgroundResource(R.color.transparent);
            viewHolder.payBtn.setVisibility(View.VISIBLE);
            viewHolder.viewBtn.setVisibility(View.GONE);
            if(transportdueDateList.get(position).equals("0000-00-00")){
                viewHolder.transportfeeDueDateTV.setText("No Due Date");
                viewHolder.transportfeeDueDateTV.setBackgroundResource(R.color.transparent);
            }else{
                viewHolder.transportfeeDueDateTV.setText(Utility.parseDate("yyyy-MM-dd", defaultDateFormat, transportdueDateList.get(position)));
                if(checkDueDate(transportdueDateList.get(position))) {
                    viewHolder.transportfeeDueDateTV.setBackgroundResource(R.drawable.red_border);
                    float scale = context.getResources().getDisplayMetrics().density;
                    int dpAsPixels = (int) (5*scale + 0.5f);
                    viewHolder.transportfeeDueDateTV.setPadding(dpAsPixels, 0, dpAsPixels, 0);
                    viewHolder.transportfeeDueDateTV.setTextColor(Color.WHITE);
                } else {
                    viewHolder.transportfeeDueDateTV.setBackgroundResource(R.color.transparent);
                }
            }
        } if (transportstatusList.get(position).equals("Partial")) {
            viewHolder.feesStatusTV.setText(context.getApplicationContext().getString(R.string.partial));
            viewHolder.feesStatusTV.setBackgroundResource(R.drawable.yellow_border);
            viewHolder.viewBtn.setVisibility(View.VISIBLE);
            if(checkDueDate(transportdueDateList.get(position))) {
                if(transportdueDateList.get(position).equals("0000-00-00")){
                    viewHolder.transportfeeDueDateTV.setText(context.getApplicationContext().getString(R.string.noduedate));
                    viewHolder.transportfeeDueDateTV.setBackgroundResource(R.color.transparent);
                }else{
                    viewHolder.transportfeeDueDateTV.setBackgroundResource(R.drawable.red_border);
                }
                float scale = context.getResources().getDisplayMetrics().density;
                int dpAsPixels = (int) (5*scale + 0.5f);

                viewHolder.transportfeeDueDateTV.setPadding(dpAsPixels, 0, dpAsPixels, 0);
                viewHolder.transportfeeDueDateTV.setTextColor(Color.WHITE);
            } else {
                viewHolder.transportfeeDueDateTV.setBackgroundResource(R.color.transparent);
            }
        }

        if(Utility.getSharedPreferencesBoolean(context, Constants.showPaymentBtn))  {
            Log.e("testing", "testing 1");
            viewHolder.transportfeesAdapter_payBtn.setVisibility(View.VISIBLE);

            if(transportstatusList.get(position).equals("Paid")) {
                viewHolder.transportfeesAdapter_payBtn.setVisibility(View.GONE);
            } if (transportstatusList.get(position).equals("Unpaid")) {
                viewHolder.transportfeesAdapter_payBtn.setVisibility(View.VISIBLE);
            } if (transportstatusList.get(position).equals("Partial")) {
                viewHolder.transportfeesAdapter_payBtn.setVisibility(View.VISIBLE);
            }
        } else {
            Log.e("testing", "testing 2");
            viewHolder.transportfeesAdapter_payBtn.setVisibility(View.GONE);
        }

        //DECORATE
        viewHolder.header.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));
        viewHolder.transportfeesAdapter_payBtn.setText(Utility.getSharedPreferences(context.getApplicationContext(), Constants.currency) + " " + context.getApplicationContext().getString(R.string.pay));
        //DECORATE
        is_offline_fee_payment=Utility.getSharedPreferences(context.getApplicationContext(), "is_offline_fee_payment");
        if(is_offline_fee_payment.equals("1")){
            viewHolder.transportfeesAdapter_payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPaymentChooser("","",feesSessionIdList.get(position),"transport_fees",feesIdList.get(position));
                }
            });
        }else{
            viewHolder.transportfeesAdapter_payBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBalanceDialog(context,"0","0","transport",feesIdList.get(position),feesSessionIdList.get(position));

                }
            });
        }

        viewHolder.viewBtn.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View clickview) {

                ArrayList<String> paymentId = new ArrayList<>();
                ArrayList<String> paymentDate = new ArrayList<>();
                ArrayList<String> paymentDiscount = new ArrayList<>();
                ArrayList<String> paymentFine = new ArrayList<>();
                ArrayList<String> paymentPaid = new ArrayList<>();
                ArrayList<String> paymentNote = new ArrayList<>();

                JSONObject feesDetailsJson = new JSONObject();

                try {
                    feesDetailsJson = new JSONObject(feesDetails.get(position));

                    feesDetailsJson.length();

                    for (int k = 1; k<=feesDetailsJson.length(); k++) {

                        JSONObject fee = feesDetailsJson.getJSONObject(k+"");
                        String paymentmode=fee.getString("payment_mode");
                        if(paymentmode.equals("upi")){
                            paymentmode=context.getApplicationContext().getString(R.string.upi);
                        }else if(paymentmode.equals("bank_transfer")){
                            paymentmode=context.getApplicationContext().getString(R.string.banktransfer);
                        }else if(paymentmode.equals("bank_payment")){
                            paymentmode=context.getApplicationContext().getString(R.string.bankpayment);
                        }else if(paymentmode.equals("Cash")){
                            paymentmode=context.getApplicationContext().getString(R.string.cash);
                        }else{
                            paymentmode=fee.getString("payment_mode");
                        }

                        paymentId.add(transportfeesDepositIdList.get(position) + "/" + fee.getString("inv_no") + "(" + paymentmode + ")" );
                        paymentDate.add(Utility.parseDate("yyyy-MM-dd", Utility.getSharedPreferences(context.getApplicationContext(), "dateFormat"), fee.getString("date")));
                        paymentDiscount.add(fee.getString("amount_discount"));
                        paymentFine.add(fee.getString("amount_fine"));
                        paymentPaid.add(fee.getString("amount"));
                        paymentNote.add(fee.getString("description"));
                    }
                } catch (JSONException je) {
                    Log.e("Error Parseing Data", je.toString());
                }

                View view = context.getLayoutInflater().inflate(R.layout.fragment_fees_bottom_sheet, null);
                view.setMinimumHeight(500);

                ImageView crossBtn = view.findViewById(R.id.fees_bottomSheet_crossBtn);
                TextView header = view.findViewById(R.id.fees_bottomSheet_header);
                RecyclerView hostelListView = view.findViewById(R.id.fees_bottomSheet_listview);

                header.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));

                StudentFeesDetailAdapter adapter = new StudentFeesDetailAdapter(context, paymentId, paymentDate, paymentDiscount, paymentFine, paymentPaid, paymentNote,"transport_fees",transportfeesDepositIdList.get(position));
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
                hostelListView.setLayoutManager(mLayoutManager);
                hostelListView.setItemAnimator(new DefaultItemAnimator());
                hostelListView.setAdapter(adapter);
                final BottomSheetDialog dialog = new BottomSheetDialog(context);

                dialog.setContentView(view);
                dialog.show();

                crossBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    private void showPaymentChooser(final String feesIdList, final String feesTypeId, final String feesSessionId, final String paymenttype, final String transfeesIdList) {
        final Dialog dialog = new Dialog(context);
        Utility.setLocale(context.getApplicationContext(), Utility.getSharedPreferences(context.getApplicationContext(), Constants.langCode));
        dialog.setContentView(R.layout.choose_payment_mode);
        dialog.setCanceledOnTouchOutside(false);
        RelativeLayout headerLay = (RelativeLayout) dialog.findViewById(R.id.addTask_dialog_header);
        final LinearLayout onlinePayment = (LinearLayout) dialog.findViewById(R.id.onlinePayment);
        final LinearLayout offlinePayment = (LinearLayout) dialog.findViewById(R.id.offlinePayment);
        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.addTask_dialog_crossIcon);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        onlinePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(paymenttype.equals("transport_fees")){
                    showBalanceDialog(context, "0", "0", "transport", transfeesIdList, feesSessionId);
                }else {
                    showBalanceDialog(context, feesIdList, feesTypeId, paymenttype, transfeesIdList, feesSessionId);
                }

            }
        });
        offlinePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("feesId= "+feesIdList+"feesTypeId= "+feesTypeId+"feesSessionId= "+feesSessionId+"paymenttype= "+paymenttype+"transfeesId="+transfeesIdList);
                Intent asd = new Intent(context, StudentOfflinePayment.class);
                asd.putExtra("feesId", feesIdList);
                asd.putExtra("feesTypeId",feesTypeId);
                asd.putExtra("feesSessionId",feesSessionId);
                asd.putExtra("paymenttype",paymenttype);
                asd.putExtra("transfeesIdList",transfeesIdList);
                context.startActivity(asd);
                dialog.dismiss();
            }
        });

        headerLay.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.primaryColour)));
        dialog.show();
    }
    private void showBalanceDialog(Context context,String feesId,String feesTypeId,String paymenttype,String transfeesId,String feesSessionId ) {

         disdialog = new Dialog(context);
        disdialog.setContentView(R.layout.fees_balance_dialoug);

        RelativeLayout headerLay = (RelativeLayout) disdialog.findViewById(R.id.add_dialog_header);
         discountLayout = (LinearLayout) disdialog.findViewById(R.id.discountLayout);
        ImageView closeBtn = (ImageView) disdialog.findViewById(R.id.add_dialog_crossIcon);
        dateTV= disdialog.findViewById(R.id.dateTV);
        String defaultDateFormat = Utility.getSharedPreferences(context.getApplicationContext(), "dateFormat");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentDateandTime = sdf.format(new Date());
        dateTV.setText(Utility.parseDate("MM/dd/yyyy", defaultDateFormat,currentDateandTime));
        amountTV= disdialog.findViewById(R.id.amountTV);
        fineTV= disdialog.findViewById(R.id.fineTV);
        discountTV= disdialog.findViewById(R.id.discountTV);
        negativeamountTV= disdialog.findViewById(R.id.negativeamountTV);
        discountamountTV= disdialog.findViewById(R.id.discountamountTV);
        cancelBtn= disdialog.findViewById(R.id.cancelBtn);
        feespayBtn= disdialog.findViewById(R.id.feespayBtn);
        recyclerview= disdialog.findViewById(R.id.recyclerview);
        // Add sample data
        for (int i = 0; i < 10; i++) {
            itemList.add(new MyModel("Item " + i, false));
        }

        if(Utility.isConnectingToInternet(context)){
            params.put("fee_groups_feetype_id",feesTypeId);
            params.put("student_fees_master_id",feesId);
            params.put("student_session_id",feesSessionId);
            params.put("fee_category",paymenttype);
            params.put("trans_fee_id",transfeesId);
            JSONObject obj=new JSONObject(params);
            Log.e("params ", obj.toString());
            System.out.println("params ==="+ obj.toString());
            getDataFromApi(obj.toString(),feesTypeId,feesId,transfeesId);
        }else{
            makeText(context, R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
        }
        for (MyModel item : itemList) {
            item.setChecked(false); // or true, based on your default
        }
        feesDiscountGroupAdapter = new FeesDiscountGroupAdapter(context,idList,codeList,amountList,nameList,typeList,percentageList,balanceAmount,countList,itemList, (position, isChecked,amount,type,percentage,updatedArray) -> {
            // Handle checkbox change
            Log.d("CheckboxState", "Item at " + position + " is " + (isChecked ? "Checked" : "Unchecked"));
            System.out.println("Item at " + position + " is " + (isChecked ? "Checked" : "Unchecked")+"-"+amount+" of type "+type+" percentage= "+percentage);
            System.out.println("Item at jsonarray==" + updatedArray);

                feespayBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        disdialog.dismiss();
                        if(Utility.isConnectingToInternet(context)){
                            disparams.put("student_fees_master_id",feesId);
                            disparams.put("fee_groups_feetype_id",feesTypeId);
                            disparams.put("student_id", Utility.getSharedPreferences(context.getApplicationContext(), "studentId"));
                            disparams.put("student_transport_fee_id",transfeesId);
                            disparams.put("fee_discount_group",updatedArray);
                            JSONObject obj=new JSONObject(disparams);
                            Log.e("params ", obj.toString());
                            System.out.println("payment params ==="+ obj.toString());
                            getPaymentDataFromApi(obj.toString());
                        }else{
                            makeText(context, R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            //discountamountvalue=Double.parseDouble(amount);
            DecimalFormat dft = new DecimalFormat("0.00");
            if((isChecked ? "Checked" : "Unchecked").equals("Checked")){
                if(type.equals("percentage")){

                    Double perc_amt=((Double.parseDouble(Utility.changeAmount(balanceAmount, currency, currency_price))*Double.parseDouble(percentage))/100);
                    discount = discount +perc_amt;
                   // makeText(context, discount.toString(), Toast.LENGTH_SHORT).show();
                    discountTV.setText(currency + dft.format(Float.parseFloat(String.valueOf(discount))));
                    amountvalue=Double.parseDouble(discountamountTV.getText().toString().replaceAll("[^\\d.-]", ""));
                    disval = amountvalue - perc_amt;

                    if (disval <= 0.00) {
                        feespayBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                negativeamountTV.setVisibility(View.VISIBLE);
                                System.out.println("Value is negative"+disval);
                                negativeamountTV.setText("Deposit Amount Can Not Be Less Than Zero");
                            }
                        });
                    }else{
                        negativeamountTV.setVisibility(View.GONE);
                    }
                    // System.out.println("Check Pay Amount ==== "+String.valueOf(disval));
                    discountamountTV.setText(currency + dft.format(Float.parseFloat(disval.toString())));
                    //Math.round(disval*100)/100;
                   // System.out.println("perc_amt value= "+Double.parseDouble(balanceAmount)+" * "+Double.parseDouble(percentage)+" = "+perc_amt);
                }else if(type.equals("fix")){
                   /* if(discount.equals("")) {
                        discount = discount + Double.parseDouble(amount.replaceAll("[^\\d.-]", ""));
                    }else{
                        discount = Double.parseDouble(amount.replaceAll("[^\\d.-]", ""));
                    }*/
                    discount = discount + Double.parseDouble(amount.replaceAll("[^\\d.-]", ""));
                    discountTV.setText(currency + dft.format(Float.parseFloat(discount.toString())));
                    amountvalue=Double.parseDouble(discountamountTV.getText().toString().replaceAll("[^\\d.-]", ""));
                    System.out.println("amountvalue ==== "+String.valueOf(amountvalue));
                     System.out.println("discountamountvalue ==== "+amount);
                    disval = amountvalue - Double.parseDouble(amount.replaceAll("[^\\d.-]", ""));
                    if (disval <= 0.00) {
                        feespayBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                System.out.println("Value is negative"+disval);
                                negativeamountTV.setVisibility(View.VISIBLE);
                                negativeamountTV.setText("Deposit Amount Can Not Be Less Than Zero");
                            }
                        });
                    }else{
                        negativeamountTV.setVisibility(View.GONE);
                    }
                    // System.out.println("Check Pay Amount ==== "+String.valueOf(disval));
                    discountamountTV.setText(currency + String.valueOf(disval));
                    //Math.round(disval*100)/100;
                }
            }else if((isChecked ? "Checked" : "Unchecked").equals("Unchecked")){
                if(type.equals("percentage")){

                    Double perc_amt=((Double.parseDouble(Utility.changeAmount(balanceAmount, currency, currency_price))*Double.parseDouble(percentage))/100);
                    discount = discount -perc_amt;
                    discountTV.setText(currency + dft.format(Float.parseFloat(String.valueOf(discount))));
                    disval = disval + perc_amt;
                    if (disval <= 0.00) {
                        feespayBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                negativeamountTV.setVisibility(View.VISIBLE);
                                System.out.println("Value is negative"+disval);
                                negativeamountTV.setText("Deposit Amount Can Not Be Less Than Zero");
                            }
                        });
                    }else{
                        negativeamountTV.setVisibility(View.GONE);
                    }
                    // System.out.println("Check Pay Amount ==== "+String.valueOf(disval));
                    discountamountTV.setText(currency + dft.format(Float.parseFloat(String.valueOf(disval))));
                    //Math.round(disval*100)/100;
                    // System.out.println("perc_amt value= "+Double.parseDouble(balanceAmount)+" * "+Double.parseDouble(percentage)+" = "+perc_amt);
                }else if(type.equals("fix")) {
                    // System.out.println("Uncheck amount="+amount);
                    // System.out.println("Uncheck discount="+discount);
                    discount = discount - Double.parseDouble(amount.replaceAll("[^\\d.-]", ""));
                    discountTV.setText(currency + dft.format(Float.parseFloat(String.valueOf(discount))));

                    //System.out.println("amountvalue ==== "+String.valueOf(amountvalue));
                    //System.out.println("discountamountvalue ==== "+discount);
                    disval = disval + Double.parseDouble(amount.replaceAll("[^\\d.-]", ""));
                    if (disval < 0.00) {
                        feespayBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                negativeamountTV.setVisibility(View.VISIBLE);
                                System.out.println("Value is negative"+disval);
                                negativeamountTV.setText("Deposit Amount Can Not Be Less Than Zero");
                            }
                        });
                    }else{
                        negativeamountTV.setVisibility(View.GONE);
                    }
                    //System.out.println("Uncheck Pay Amount ==== "+String.valueOf(disval));
                    discountamountTV.setText(currency + dft.format(Float.parseFloat(String.valueOf(disval))));

                }
            }

            // You can also update your list or do any other logic
            itemList.get(position).setChecked(isChecked);
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(feesDiscountGroupAdapter);
        if(Utility.isConnectingToInternet(context)){
            DiscountGroupStatus();
        }else{
            makeText(context, R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
        }


         closeBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 disdialog.dismiss();
             }
         });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 disdialog.dismiss();
             }
         });

        //DECORATE
        headerLay.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.primaryColour)));
        //DECORATE
        disdialog.show();
    }


    private boolean checkDueDate(String dueDateStr) {
        try {
            Date todayDate = new Date();
            Date dueDate =new SimpleDateFormat("yyyy-MM-dd").parse(dueDateStr);

            if(todayDate.after(dueDate)) {
                return true;
            } else {
                return  false;
            }

        } catch (ParseException e) {
            Log.e("Parse Exp", e.toString());
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return feesIdList.size();
    }

    private void getDataFromApi (String bodyParams,String feesTypeId,String feesId,String transfeesId) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;

        String url = Utility.getSharedPreferences(context, "apiUrl")+Constants.getBalanceFeeUrl;
        Log.e("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null) {
                    pd.dismiss();
                    try {
                        amountvalue=0.00;
                        discount=0.00;
                        disval=0.00;
                        DecimalFormat f = new DecimalFormat("##.00");
                        Log.e("Result", result);
                        System.out.println("Result==="+ result);
                        JSONObject object = new JSONObject(result);
                          currency = Utility.getSharedPreferences(context, Constants.currency);
                          currency_price =  Utility.getSharedPreferences(context, Constants.currency_price);
                        JSONObject dataArray = object.getJSONObject("result_array");
                        DecimalFormat df = new DecimalFormat("0.00");
                        amountTV.setText(currency + df.format(Float.parseFloat(Utility.changeAmount(dataArray.getString("balance"),currency,currency_price))));
                        fineTV.setText(currency + df.format(Float.parseFloat(Utility.changeAmount(dataArray.getString("remain_amount_fine"),currency,currency_price))));
                        amountvalue=Double.parseDouble(dataArray.getString("balance"))+Double.parseDouble(dataArray.getString("remain_amount_fine"));
                        discountamountTV.setText(currency + df.format(Float.parseFloat(Utility.changeAmount(String.valueOf(amountvalue),currency,currency_price))));
                        discountTV.setText(currency +"0.00");
                        balanceAmount=dataArray.getString("balance");
                        //makeText(context, discountamountTV.getText().toString(), Toast.LENGTH_SHORT).show();

                        codeList.clear();
                        idList.clear();
                        nameList.clear();
                        typeList.clear();
                        percentageList.clear();
                        countList.clear();
                        amountList.clear();
                        JSONArray discount_feearray = dataArray.getJSONArray("discount_not_applied");
                        if(discount_feearray.length() == 0) {
                            feespayBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    disdialog.dismiss();
                                    if(Utility.isConnectingToInternet(context)){
                                        disparams.put("student_fees_master_id",feesId);
                                        disparams.put("fee_groups_feetype_id",feesTypeId);
                                        disparams.put("student_id", Utility.getSharedPreferences(context.getApplicationContext(), "studentId"));
                                        disparams.put("student_transport_fee_id",transfeesId);
                                        disparams.put("fee_discount_group","");
                                        JSONObject obj=new JSONObject(disparams);
                                        Log.e("params ", obj.toString());
                                        System.out.println("payment params ==="+ obj.toString());
                                        getPaymentDataFromApi(obj.toString());
                                    }else{
                                        makeText(context, R.string.noInternetMsg, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            for (int i = 0; i < discount_feearray.length(); i++) {
                                idList.add(discount_feearray.getJSONObject(i).getString("id"));
                                codeList.add(discount_feearray.getJSONObject(i).getString("code"));
                                nameList.add(discount_feearray.getJSONObject(i).getString("name"));
                                typeList.add(discount_feearray.getJSONObject(i).getString("type"));

                                percentageList.add(discount_feearray.getJSONObject(i).getString("percentage"));
                                amountList.add(discount_feearray.getJSONObject(i).getString("amount"));
                                countList.add(discount_feearray.getJSONObject(i).getString("remaining_discount_limit"));
                            }
                            feesDiscountGroupAdapter.notifyDataSetChanged();
                        }
                        //Double discountvalue= Double.parseDouble(dataArray.getString("student_fees"))-Double.parseDouble(fineamount);
                        //discountamountTV.setText(String.valueOf(discountvalue));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    pd.dismiss();
                   // pullToRefresh.setVisibility(View.GONE);

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

    private void getPaymentDataFromApi (String bodyParams) {

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        final String requestBody = bodyParams;

        String url = Utility.getSharedPreferences(context, "apiUrl")+Constants.paymentrequestUrl;
        Log.e("URL", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                if (result != null) {
                    pd.dismiss();
                    try {
                        JSONObject jsonObject1=new JSONObject(result);
                        System.out.println("redirect_url=="+jsonObject1.getString("redirect_url"));
                        Intent asd = new Intent(context.getApplicationContext(), MultiplePayment.class);
                        asd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        asd.putExtra("redirect_url",  jsonObject1.getString("redirect_url"));
                        context.startActivity(asd);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    pd.dismiss();
                    // pullToRefresh.setVisibility(View.GONE);

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

    private void DiscountGroupStatus() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();

        String url = Utility.getSharedPreferences(context.getApplicationContext(), "apiUrl")+ Constants.getfeesdiscountstatusStatusUrl;
        System.out.println("url=="+url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                pd.dismiss();
                try {
                    JSONObject object = new JSONObject(result);
                    fees_discount = object.getString("fees_discount");

                    if(fees_discount.equals("1")){
                        discountLayout.setVisibility(View.VISIBLE);
                    }else{
                        discountLayout.setVisibility(View.GONE);
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

    // Select All / Unselect All Logic
    public void selectAll(boolean isChecked) {
        jsonArray = new JSONArray(); // clear previous data

        for (int i = 0; i < checkBoxItems.size(); i++) {
            checkBoxItems.set(i, isChecked);

            if (isChecked) {
                JSONObject jsonObject = new JSONObject(); //  create new object every time
                try {

                    if( feesCatList.get(i).equals("fees")) {
                        jsonObject.put("fee_category", feesCatList.get(i));
                        jsonObject.put("trans_fee_id", "0");
                        jsonObject.put("fee_session_group_id", feesessiongroupidIdList.get(i));
                        jsonObject.put("fee_master_id", feesIdList.get(i));
                        jsonObject.put("fee_groups_feetype_id", feesTypeId.get(i));
                    }else if(feesCatList.get(i).equals("transport")){
                        jsonObject.put("fee_category", feesCatList.get(i));
                        jsonObject.put("trans_fee_id", feesIdList.get(i));
                        jsonObject.put("fee_session_group_id", feesessiongroupidIdList.get(i));
                        jsonObject.put("fee_master_id", "0");
                        jsonObject.put("fee_groups_feetype_id", "0");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject); // now each one is unique
            }
        }

        if (isChecked) {
            System.out.println("All checked");
            System.out.println("JSONArray == " + jsonArray.toString());
        } else {
            System.out.println("All unchecked");
        }

        notifyDataSetChanged();
    }
    private void removeFromJsonArrayByFeeMasterId(String feeMasterId) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("fee_groups_feetype_id").equals(feeMasterId)) {
                    jsonArray.remove(i); // ✅ requires API 19+
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
