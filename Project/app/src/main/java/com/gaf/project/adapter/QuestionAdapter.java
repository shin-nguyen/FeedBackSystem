package com.gaf.project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaf.project.R;
import com.gaf.project.model.Assignment;
import com.gaf.project.model.Question;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> mListQuestion;
    private Context mContext;

    public void setData(List<Question> list, Context context){
        this.mListQuestion = list;
        this.mContext= context;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question,parent,false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull QuestionViewHolder holder, int position) {

        Question question = mListQuestion.get(position);

        if(question==null){
            return;
        }

        holder.topicId.setText(String.valueOf(question.getTopic().getTopicID()));
        holder.topicName.setText(String.valueOf(question.getTopic().getTopicName()));
        holder.questionId.setText(String.valueOf(question.getQuestionID()));
        holder.questionContent.setText(String.valueOf(question.getQuestionContent()));

    }

    @Override
    public int getItemCount() {
        if (mListQuestion != null){
            return mListQuestion.size();
        }
        return 0;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{

        private TextView topicId, topicName, questionId, questionContent;
        private Button editQuestion, deleteQuestion;

        public QuestionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            topicId=itemView.findViewById(R.id.question_topic_id);
            topicName=itemView.findViewById(R.id.question_topic_name);
            questionId=itemView.findViewById(R.id.question_question_id);
            questionContent=itemView.findViewById(R.id.question_question_content);

            editQuestion=itemView.findViewById(R.id.btn_edit_question);
            deleteQuestion=itemView.findViewById(R.id.btn_delete_question);

            editQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            deleteQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    View view=LayoutInflater.from(mContext).inflate(R.layout.warning_dialog,null);

                    builder.setView(view);

                    final AlertDialog warningDialog=builder.create();
                    warningDialog.show();

                    TextView warningContent = view.findViewById(R.id.txt_warning_content);
                    warningContent.setText("Do you want to delete this Question?");

                    Button btnCancel = view.findViewById(R.id.btn_cancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            warningDialog.dismiss();
                        }
                    });

                    Button btnYes = view.findViewById(R.id.btn_yes);
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            warningDialog.dismiss();

                            AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                            View view=LayoutInflater.from(mContext).inflate(R.layout.success_dialog,null);

                            builder.setView(view);

                            final AlertDialog successDialog=builder.create();
                            successDialog.show();

                            TextView warningContent = view.findViewById(R.id.txt_success_content);
                            warningContent.setText("Delete Success!");

                            Button btnCancel = view.findViewById(R.id.btn_ok);
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    successDialog.dismiss();
                                }
                            });

                        }
                    });
                }
            });

        }
    }
}
