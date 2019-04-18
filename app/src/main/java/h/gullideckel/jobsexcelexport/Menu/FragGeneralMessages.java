package h.gullideckel.jobsexcelexport.Menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import h.gullideckel.jobsexcelexport.Objects.GeneralMessage.GeneralMessage;
import h.gullideckel.jobsexcelexport.Objects.UserMessage.Message;
import h.gullideckel.jobsexcelexport.Objects.UserMessage.UserInfo;
import h.gullideckel.jobsexcelexport.R;

public class FragGeneralMessages extends Fragment
{
    private static final String TAG = "FragUserMessages";

    private EditText edtTitle;
    private EditText edtMessage;
    private Spinner spinPosition;
    private Button btnSendAll;

    private List<GeneralMessage> messages;

    private FirebaseFirestore db;

    public FragGeneralMessages()
    {
        messages = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
    }

    public static FragGeneralMessages newInstance()
    {
        FragGeneralMessages fragment = new FragGeneralMessages();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.frag_general_messages, container, false);

        v.setBackgroundColor(Color.WHITE);
        v.bringToFront();

        edtTitle = (EditText) v.findViewById(R.id.edtMesTitle);
        edtMessage = (EditText) v.findViewById(R.id.edtMesMessage);
        spinPosition = (Spinner) v.findViewById(R.id.spinMesPosition);
        btnSendAll = (Button) v.findViewById(R.id.btnMesSendAll);


        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(getContext(), R.array.message_position, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPosition.setAdapter(adapterMonth);

        LoadAllMessages();

        btnSendAll.setOnClickListener(SendAllUser);

        return v;
    }

    private View.OnClickListener SendAllUser = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if (edtTitle.getText().toString().isEmpty() && edtMessage.getText().toString().isEmpty() && spinPosition.getSelectedItemPosition() > -1)
                Toast.makeText(getContext(), "Please fill Title, Message and select Position", Toast.LENGTH_LONG);
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(edtTitle.getText());
                builder.setMessage(edtMessage.getText());
                builder.setCancelable(true);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SendMessage();
                    }
                });
                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        }
    };


    private void SendMessage()
    {
        db.collection(getContext().getString(R.string.db_messages)).add(GetMessage()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task)
            {
                if(task.isSuccessful())
                    Log.i(TAG, "onComplete: Message successfull uploaded");
                else
                    Log.e(TAG, "onComplete: ", task.getException());
            }
        });
    }


    private GeneralMessage GetMessage()
    {
        GeneralMessage message = new GeneralMessage();

        message.setTitle(edtTitle.getText().toString());
        message.setMessage(edtMessage.getText().toString());
        message.setPosition(spinPosition.getSelectedItemPosition());

        return message;
    }


    private void LoadAllMessages()
    {
        db.collection(getContext().getString(R.string.db_messages)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    List<GeneralMessage> generalMessages = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult())
                    {
                        try
                        {
                            GeneralMessage message = document.toObject(GeneralMessage.class);
                            generalMessages.add(message);
                        }catch (Exception e)
                        {
                            Log.wtf(TAG, "onComplete: Could not convert to UserInfo", e);
                        }
                    }
                    messages = generalMessages;
                    btnSendAll.setEnabled(true);
                }
                else
                {
                    Log.e(TAG, "onComplete: ", task.getException());
                }

            }
        });
    }



}
