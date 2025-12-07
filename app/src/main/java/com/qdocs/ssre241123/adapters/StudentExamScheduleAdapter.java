package com.qdocs.ssre241123.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qdocs.ssre241123.R;
import com.qdocs.ssre241123.students.StudentExamSchedule;
import com.qdocs.ssre241123.utils.Constants;
import com.qdocs.ssre241123.utils.Utility;
import java.util.ArrayList;

public class StudentExamScheduleAdapter extends RecyclerView.Adapter<StudentExamScheduleAdapter.MyViewHolder> {

    private StudentExamSchedule context;
    private ArrayList<String> subjectList;
    private ArrayList<String> dateList;
    private ArrayList<String> timeList;
    private ArrayList<String> roomList;
    private ArrayList<String> durationList;
    private ArrayList<String> max_marksList;
    private ArrayList<String> min_marksList;
    private ArrayList<String> credit_hoursList;

    public StudentExamScheduleAdapter(StudentExamSchedule studentExamSchedule,ArrayList<String> subjectList,
                                      ArrayList<String> dateList,ArrayList<String> timeList,ArrayList<String> roomList,
                                      ArrayList<String> durationList,ArrayList<String> max_marksList,ArrayList<String> min_marksList,ArrayList<String> credit_hoursList){
        this.context = studentExamSchedule;
        this.subjectList = subjectList;
        this.dateList = dateList;
        this.timeList = timeList;
        this.roomList = roomList;
        this.durationList = durationList;
        this.max_marksList = max_marksList;
        this.min_marksList = min_marksList;
        this.credit_hoursList = credit_hoursList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectNameTV, dateTV, timeTV,duration,roomNoTV,max,min,CreditHours;
        private RelativeLayout subjectNameHeader;
        LinearLayout viewBtn;

        public MyViewHolder(View view) {
            super(view);

            subjectNameHeader = view.findViewById(R.id.adapter_student_libraryBook_nameView);
            subjectNameTV = (TextView) view.findViewById(R.id.adapter_student_examSchedule_subjectTV);
            dateTV = (TextView) view.findViewById(R.id.adapter_student_examSchedule_dateTV);
            timeTV = (TextView) view.findViewById(R.id.adapter_student_examSchedule_timeTV);
            duration = (TextView) view.findViewById(R.id.adapter_student_examSchedule_duration);
            roomNoTV = (TextView) view.findViewById(R.id.adapter_student_examSchedule_roomTV);
            max = (TextView) view.findViewById(R.id.adapter_student_examSchedule_max);
            min = (TextView) view.findViewById(R.id.adapter_student_examSchedule_min);
            viewBtn = (LinearLayout) view.findViewById(R.id.viewBtn);
            CreditHours = (TextView) view.findViewById(R.id.adapter_student_examSchedule_CreditHours);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_student_exam_schedule, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.subjectNameHeader.setBackgroundColor(Color.parseColor(Utility.getSharedPreferences(context.getApplicationContext(), Constants.secondaryColour)));
        holder.subjectNameTV.setText(subjectList.get(position));
        holder.dateTV.setText(dateList.get(position));
        holder.timeTV.setText(timeList.get(position));
        holder.roomNoTV.setText(roomList.get(position));
        holder.duration.setText(durationList.get(position));
        holder.max.setText(max_marksList.get(position));
        holder.min.setText(min_marksList.get(position));
        holder.CreditHours.setText(credit_hoursList.get(position));
        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webView = new WebView(context);
                webView.loadDataWithBaseURL(null, "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Admit Card</title>\n" +
                        "    <style>\n" +
                        "        body {\n" +
                        "            font-family: Arial, sans-serif;\n" +
                        "            margin: 0;\n" +
                        "            padding: 0;\n" +
                        "            background-color: #f8f9fa;\n" +
                        "            color: #333;\n" +
                        "        }\n" +
                        "        .admit-card {\n" +
                        "            width: 800px;\n" +
                        "            margin: 30px auto;\n" +
                        "            background: rgba(255, 255, 255, 0.9); /* Semi-transparent background */\n" +
                        "            border: 2px solid #007bff;\n" +
                        "            border-radius: 10px;\n" +
                        "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                        "            overflow: hidden;\n" +
                        "            position: relative;\n" +
                        "        }\n" +
                        "        body::before {\n" +
                        "            content: '';\n" +
                        "            position: fixed;\n" +
                        "            top: 0;\n" +
                        "            left: 0;\n" +
                        "            width: 100%;\n" +
                        "            height: 100%;\n" +
                        "            background: url('https://via.placeholder.com/1000x700') no-repeat center center/cover;\n" +
                        "            z-index: -1;\n" +
                        "            opacity: 0.2; /* Adjust opacity for a subtle effect */\n" +
                        "        }\n" +
                        "        .header {\n" +
                        "            background: #007bff;\n" +
                        "            color: #fff;\n" +
                        "            text-align: center;\n" +
                        "            padding: 20px 10px;\n" +
                        "        }\n" +
                        "        .header h1 {\n" +
                        "            margin: 0;\n" +
                        "            font-size: 24px;\n" +
                        "            text-transform: uppercase;\n" +
                        "        }\n" +
                        "        .header p {\n" +
                        "            margin: 5px 0;\n" +
                        "            font-size: 16px;\n" +
                        "        }\n" +
                        "        .content {\n" +
                        "            padding: 20px;\n" +
                        "        }\n" +
                        "        .details-table {\n" +
                        "            width: 100%;\n" +
                        "            border-collapse: collapse;\n" +
                        "            margin-bottom: 20px;\n" +
                        "        }\n" +
                        "        .details-table th, .details-table td {\n" +
                        "            border: 1px solid #ccc;\n" +
                        "            padding: 10px;\n" +
                        "            text-align: left;\n" +
                        "        }\n" +
                        "        .details-table th {\n" +
                        "            background: #f1f1f1;\n" +
                        "        }\n" +
                        "        .photo-section {\n" +
                        "            text-align: center;\n" +
                        "            margin: 20px 0;\n" +
                        "        }\n" +
                        "        .photo-box {\n" +
                        "            display: inline-block;\n" +
                        "            width: 120px;\n" +
                        "            height: 150px;\n" +
                        "            border: 2px dashed #ccc;\n" +
                        "            line-height: 150px;\n" +
                        "            color: #999;\n" +
                        "            font-size: 14px;\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        .instructions {\n" +
                        "            background: #f9f9f9;\n" +
                        "            border: 1px solid #ccc;\n" +
                        "            padding: 15px;\n" +
                        "            border-radius: 5px;\n" +
                        "        }\n" +
                        "        .instructions ul {\n" +
                        "            margin: 0;\n" +
                        "            padding-left: 20px;\n" +
                        "        }\n" +
                        "        .footer {\n" +
                        "            display: flex;\n" +
                        "            justify-content: space-between;\n" +
                        "            margin-top: 20px;\n" +
                        "        }\n" +
                        "        .footer .signature-box {\n" +
                        "            text-align: center;\n" +
                        "        }\n" +
                        "        .footer .signature-box p {\n" +
                        "            margin: 10px 0 0;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div class=\"admit-card\">\n" +
                        "        <!-- Header Section -->\n" +
                        "        <div class=\"header\">\n" +
                        "            <h1>XYZ School/University</h1>\n" +
                        "            <p>Admit Card for Annual Examination 2025</p>\n" +
                        "        </div>\n" +
                        "\n" +
                        "        <!-- Content Section -->\n" +
                        "        <div class=\"content\">\n" +
                        "            <!-- Student Details -->\n" +
                        "            <table class=\"details-table\">\n" +
                        "                <tr>\n" +
                        "                    <th>Student Name</th>\n" +
                        "                    <td>John Doe</td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <th>Roll Number</th>\n" +
                        "                    <td>123456</td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <th>Class/Grade</th>\n" +
                        "                    <td>10th Grade</td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <th>Examination Center</th>\n" +
                        "                    <td>ABC High School, City</td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                    <th>Date of Birth</th>\n" +
                        "                    <td>01 January 2008</td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "\n" +
                        "            <!-- Photo Section -->\n" +
                        "            <div class=\"photo-section\">\n" +
                        "                <div class=\"photo-box\">\n" +
                        "                    <p>Paste Photo</p>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "\n" +
                        "            <!-- Instructions -->\n" +
                        "            <div class=\"instructions\">\n" +
                        "                <h3>Instructions:</h3>\n" +
                        "                <ul>\n" +
                        "                    <li>Carry this admit card along with a valid photo ID to the examination center.</li>\n" +
                        "                    <li>Reach the examination center at least 30 minutes before the scheduled time.</li>\n" +
                        "                    <li>Electronic gadgets like mobile phones and calculators are strictly prohibited.</li>\n" +
                        "                    <li>Follow all the instructions provided by the invigilator.</li>\n" +
                        "                </ul>\n" +
                        "            </div>\n" +
                        "\n" +
                        "            <!-- Footer Section -->\n" +
                        "            <div class=\"footer\">\n" +
                        "                <div class=\"signature-box\">\n" +
                        "                    <p>_________________________</p>\n" +
                        "                    <p>Student's Signature</p>\n" +
                        "                </div>\n" +
                        "                <div class=\"signature-box\">\n" +
                        "                    <p>_________________________</p>\n" +
                        "                    <p>Principal's Signature</p>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>", "text/HTML", "UTF-8", null);

                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter("WebViewDocument");
                        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
                        printManager.print("PrintJob", printAdapter, new PrintAttributes.Builder().build());
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
