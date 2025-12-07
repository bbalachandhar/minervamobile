package com.qdocs.ssre241123.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.qdocs.ssre241123.R;
import com.qdocs.ssre241123.model.MyModel;
import com.qdocs.ssre241123.utils.Constants;
import com.qdocs.ssre241123.utils.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FeesDiscountGroupAdapter extends RecyclerView.Adapter<FeesDiscountGroupAdapter.MyViewHolder> {
    private List<MyModel> itemList;
    String  currency,currency_price;
    JSONArray jsonArray = new JSONArray();
    private OnCheckedChangeListener onCheckedChangeListener;
    private Context context;
    private ArrayList<String> codeList;
    private ArrayList<String> idList;
    private ArrayList<String> amountList;
    private ArrayList<String> nameList;
    private ArrayList<String> typeList;
    private ArrayList<String> percentageList;
    private ArrayList<String> countList;
    String balanceAmount;

    public FeesDiscountGroupAdapter(Context studentsFees, ArrayList<String> idList, ArrayList<String> codeList,
                                    ArrayList<String> amountList, ArrayList<String> nameList,ArrayList<String> typeList,ArrayList<String> percentageList,String balanceAmount,
                                    ArrayList<String> countList, List<MyModel> itemList, OnCheckedChangeListener listener) {
        this.context = studentsFees;
        this.codeList = codeList;
        this.idList = idList;
        this.amountList = amountList;
        this.nameList = nameList;
        this.typeList = typeList;
        this.percentageList = percentageList;
        this.balanceAmount = balanceAmount;
        this.countList = countList;
        this.itemList = itemList;
        this.onCheckedChangeListener = listener;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView feesdiscount, availablecount, value;
        CheckBox checkBox;

        public MyViewHolder(View view) {
            super(view);

            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            feesdiscount = (TextView) view.findViewById(R.id.feesdiscount);
            availablecount = (TextView) view.findViewById(R.id.availablecount);
            value = (TextView) view.findViewById(R.id.value);
        }
        public void bind(MyModel item, int position,String amount,String type,String percentage) {
            checkBox.setChecked(item.isChecked());

            if (!checkBox.isPressed()) {
                onCheckedChangeListener.onCheckedChange(position, false,"0","notClicked","0", jsonArray); // Manually call with false if not clicked
            }
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setChecked(isChecked);
                if (onCheckedChangeListener != null) {
                    if(isChecked){
                        JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put(String.valueOf(position), idList.get(position));
                            jsonArray.put(jsonObject);
                            System.out.println("JSONArray == " + jsonArray.toString());
                            System.out.println("JSONObject == " + jsonObject.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("JSONArray == " + jsonArray.toString());
                        removeDiscount(jsonArray,idList.get(position));
                        // Optional: remove from JSONArray if unchecked
                    }
                    //System.out.println("JSONArray == " + jsonArray.toString());
                    onCheckedChangeListener.onCheckedChange(position, isChecked,amount,type,percentage, jsonArray);
                }
            });
        }
    }
    public void removeDiscount(JSONArray jsonArray, String discountId) {
        List<JSONObject> toKeep = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.optJSONObject(i);
            if (obj != null) {
                String key = obj.keys().next();
                String value = obj.optString(key);
                if (!discountId.equals(value)) {
                    toKeep.add(obj);
                }
            }
        }
        // Clear original array (no direct clear, so remove one by one)
        for (int i = jsonArray.length() - 1; i >= 0; i--) {
            jsonArray.remove(i);
        }
        // Add back filtered items
        for (JSONObject obj : toKeep) {
            jsonArray.put(obj);
        }
        System.out.println("Updated JSONArray == " + jsonArray.toString());
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(int position, boolean isChecked,String amount,String type,String percentage,JSONArray jsonString );
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discountgrouplist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("0.00");
        currency = Utility.getSharedPreferences(context, Constants.currency);
        currency_price = Utility.getSharedPreferences(context, Constants.currency_price);
        holder.feesdiscount.setText(nameList.get(position) + "(" + codeList.get(position) + ")");
        if (typeList.get(position).equals("fix")){
            holder.value.setText(currency + df.format(Float.parseFloat(Utility.changeAmount(amountList.get(position), currency, currency_price))));
        }else{
            holder.value.setText(percentageList.get(position)+"%");
           // Double perc_amt=((Double.parseDouble(balanceAmount)*Double.parseDouble(percentageList.get(position)))/100);
            //System.out.println("perc_amt="+balanceAmount);
            //System.out.println("perc_amt value="+Double.parseDouble(percentageList.get(position)));
           // System.out.println("perc_amt value= "+Double.parseDouble(balanceAmount)+" * "+Double.parseDouble(percentageList.get(position))+" = "+perc_amt);
        }
        holder.availablecount.setText(countList.get(position));
        holder.bind(itemList.get(position), position,Utility.changeAmount(amountList.get(position), currency, currency_price),typeList.get(position),percentageList.get(position));

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

}

