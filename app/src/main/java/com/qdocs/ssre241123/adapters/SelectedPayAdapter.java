package com.qdocs.ssre241123.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.qdocs.ssre241123.R;
import java.util.ArrayList;

public class SelectedPayAdapter extends RecyclerView.Adapter<SelectedPayAdapter.MyViewHolder> {
    private FragmentActivity context;
    private ArrayList<String> feenametypeList;
    private ArrayList<String> feeamount;
    private ArrayList<String> feecode;
    private ArrayList<String> feefineamount;
    private ArrayList<String> feecategory;
    RecyclerView listView;

    public SelectedPayAdapter(FragmentActivity studentTasks, RecyclerView listView, ArrayList<String> feenametypeList,
                              ArrayList<String> feeamount, ArrayList<String> feecode, ArrayList<String> feefineamount, ArrayList<String> feecategory) {

        this.context = studentTasks;
        this.feenametypeList = feenametypeList;
        this.feeamount = feeamount;
        this.feecode = feecode;
        this.feefineamount = feefineamount;
        this.feecategory = feecategory;
        this.listView = listView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_payadapter_layout, parent, false);

        return new SelectedPayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.feesnametype.setText(feenametypeList.get(position));
        holder.amount.setText(feeamount.get(position));
        holder.feescode.setText(feecode.get(position));
        if(feefineamount.get(position).equals("$0.0")){
            holder.finelayout.setVisibility(View.GONE);
        }else {
            holder.finelayout.setVisibility(View.VISIBLE);
            holder.feefineamount.setText(feefineamount.get(position));
        }


    }

    @Override
    public int getItemCount() {
        return feenametypeList.size();
    }


    public static class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView feesnametype,amount,feescode,feefineamount;
        LinearLayout finelayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            feesnametype=itemView.findViewById(R.id.feesnametype);
            amount=itemView.findViewById(R.id.amount);
            feescode=itemView.findViewById(R.id.feescode);
            feefineamount=itemView.findViewById(R.id.feefineamount);
            finelayout=itemView.findViewById(R.id.finelayout);


        }
    }
}
