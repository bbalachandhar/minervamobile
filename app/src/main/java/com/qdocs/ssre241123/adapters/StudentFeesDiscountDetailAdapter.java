package com.qdocs.ssre241123.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qdocs.ssre241123.R;
import com.qdocs.ssre241123.utils.Constants;
import com.qdocs.ssre241123.utils.Utility;

import java.util.ArrayList;

public class StudentFeesDiscountDetailAdapter extends RecyclerView.Adapter<StudentFeesDiscountDetailAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> paymentIdList;
    private ArrayList<String> paymentDateList;
    private ArrayList<String> feesDiscountList;
    private ArrayList<String> valueList;
    private ArrayList<String> feestypeList;
    private ArrayList<String> feespercentageList;


    public StudentFeesDiscountDetailAdapter(Context studentsFees, ArrayList<String> paymentDateList,
                                            ArrayList<String> paymentIdList, ArrayList<String> feesDiscountList,
                                            ArrayList<String> valueList,ArrayList<String> feestypeList,ArrayList<String> feespercentageList) {
        this.context = studentsFees;
        this.paymentIdList = paymentIdList;
        this.paymentDateList = paymentDateList;
        this.feesDiscountList = feesDiscountList;
        this.valueList = valueList;
        this.feestypeList = feestypeList;
        this.feespercentageList = feespercentageList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView paymentId, paymentDate, feesdiscount,value;
        LinearLayout viewContainer;

        public MyViewHolder(View view) {
            super(view);

            paymentDate = (TextView) view.findViewById(R.id.paymentDate);
            paymentId = (TextView) view.findViewById(R.id.paymentId);
            feesdiscount = (TextView) view.findViewById(R.id.feesdiscount);
            value = (TextView) view.findViewById(R.id.value);
            viewContainer = (LinearLayout) view.findViewById(R.id.adapter_student_feesDetail_viewContainer);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_discount_details_fees, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        String currency = Utility.getSharedPreferences(context.getApplicationContext(), Constants.currency);
        String  currency_price =  Utility.getSharedPreferences(context.getApplicationContext(), Constants.currency_price);

        holder.paymentId.setText(paymentIdList.get(position));
        holder.paymentDate.setText(paymentDateList.get(position));
        holder.feesdiscount.setText(feesDiscountList.get(position));
        if(feestypeList.get(position).equals("fix")) {
            holder.value.setText(currency + Utility.changeAmount(valueList.get(position), currency, currency_price));
        }else if(feestypeList.get(position).equals("percentage")) {
            holder.value.setText(feespercentageList.get(position)+"%");
        }

    }


    @Override
    public int getItemCount() {
        return feesDiscountList.size();
    }

}

