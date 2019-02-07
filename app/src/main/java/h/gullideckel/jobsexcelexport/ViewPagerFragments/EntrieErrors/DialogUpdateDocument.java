package h.gullideckel.jobsexcelexport.ViewPagerFragments.EntrieErrors;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ScheduledExecutorService;

import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.Statics.StaticMessages;

public class DialogUpdateDocument implements DialogInterface.OnClickListener
{
    private static final String TAG = "DialogUpdateDocument";
    private String id;
    private FirebaseFirestore db;
    private Context context;
    private CompanyDocument doc;

    public DialogUpdateDocument(String id, FirebaseFirestore db, Context context, CompanyDocument doc)
    {
        this.id = id;
        this.db = db;
        this.context = context;
        this.doc = doc;
    }

    public DialogUpdateDocument(){}

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                StaticMessages.Toast("Upload Started", context);
                db.collection("companies").document(id).set(doc).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        StaticMessages.Toast("Document updated", context);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(TAG, "Error writing document", e);
                        StaticMessages.Toast("Error writing document", context);

                    }
                });
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
        }
    }
}
