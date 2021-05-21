package com.gaf.project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gaf.project.R;
import com.gaf.project.constant.SystemConstant;
import com.gaf.project.dialog.FailDialog;
import com.gaf.project.dialog.SuccessDialog;
import com.gaf.project.model.Class;
import com.gaf.project.model.Question;
import com.gaf.project.model.Topic;
import com.gaf.project.response.TrainerReponse;
import com.gaf.project.service.QuestionService;
import com.gaf.project.utils.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddQuestionFragment extends Fragment {

    private View view;
    private String mission;
    private QuestionService questionService;
    private TextView title, topicName;
    private EditText questionContent;
    private Spinner sprTopic;
    private RelativeLayout topicLayoutBox;
    private Button btnSave, btnBack;

    public AddQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionService = ApiUtils.getQuestionService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_question, container, false);

        Question question = null;

        Bundle bundle = new Bundle();
        mission = getArguments().getString("mission");
        question= (Question) getArguments().getSerializable("item");

        title = view.findViewById(R.id.txt_title);
        sprTopic = view.findViewById(R.id.spinner_topic_name);
        questionContent = view.findViewById(R.id.edt_question_content);
        topicLayoutBox= view.findViewById(R.id.spinner_topic_box);
        topicName=view.findViewById(R.id.txt_topic_name);
        btnBack = view.findViewById(R.id.btn_back);
        btnSave = view.findViewById(R.id.btn_save);

        String content= questionContent.getText().toString().trim();
        Topic topic = question.getTopic();

        if(mission.equals(SystemConstant.ADD)){
            title.setText("Add Question");
            topicName.setVisibility(View.GONE);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSuccessDialog("Add Success!");
                }
            });
        }

        if(mission.equals(SystemConstant.UPDATE)){
            title.setText("Edit Question");
            topicLayoutBox.setVisibility(View.GONE);
            topicName.setText(String.valueOf(question.getTopic().getTopicID()));
            questionContent.setText(question.getQuestionContent());

//            Call<TopicReponse> callTrainer =  trainerService.loadListTrainer();
//            callTrainer.enqueue(new Callback<TrainerReponse>() {
//                @Override
//                public void onResponse(Call<TrainerReponse> call, Response<TrainerReponse> response) {
//                    new Thread(()-> {
//                        if (response.isSuccessful()&& response.body()!=null){
//                            topicList = response.body().getTrainers();
//                            adapterTopic =
//                                    new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, topicList);
//                            sprTopic.setAdapter(adapterTopic);
//                            int spnPosition = -1;
//                            spnPosition = adapterTopic.getPosition(topic);
//                            sprTopic.setSelection(spnPosition);
//                        }}).run();
//                }
//                @Override
//                public void onFailure(Call<TrainerReponse> call, Throwable t) {
//                    Log.e("Error",t.getLocalizedMessage());
//                    showToast("Error");
//                }
//            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Question questionUpdated = new Question(1,topic,content,false);
                    Call<Question> call =  questionService.update( questionUpdated );
                    call.enqueue(new Callback<Question>() {
                        @Override
                        public void onResponse(Call<Question> call, Response<Question> response) {
                            if (response.isSuccessful()&&response.body()!=null) {
                                showSuccessDialog("Edit Success!");
                                Log.e("Success","Update Class success");
                            }
                        }

                        @Override
                        public void onFailure(Call<Question> call, Throwable t) {
                            Log.e("Error",t.getLocalizedMessage());
                            showFailDialog("Error");
                        }
                    });
                    Log.e("Success","Send Class success");
                }
            });
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    public void showSuccessDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        SuccessDialog newFragment = new SuccessDialog(message);
        newFragment.show(ft, "dialog success");
    }

    public void showFailDialog(String message){
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        FailDialog newFragment = new FailDialog(message);
        newFragment.show(ft, "dialog fail");
    }
}