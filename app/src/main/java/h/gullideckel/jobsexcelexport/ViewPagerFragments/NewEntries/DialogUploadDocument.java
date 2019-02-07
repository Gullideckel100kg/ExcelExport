package h.gullideckel.jobsexcelexport.ViewPagerFragments.NewEntries;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import h.gullideckel.jobsexcelexport.DynamicViewPager.PageHandler;
import h.gullideckel.jobsexcelexport.DynamicViewPager.ViewPagerDynamicAdapter;
import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.Statics.StaticMessages;

public class DialogUploadDocument implements DialogInterface.OnClickListener
{
    private static final String TAG = "DialogUpdateDocument";
    private FirebaseFirestore db;
    private Context context;
    private CompanyDocument doc;
    private List<CompanyDocument> docs;
    private ViewPagerDynamicAdapter adapter;
    private PageHandler handler;

    public DialogUploadDocument(FirebaseFirestore db, Context context, List<CompanyDocument> docs, ViewPagerDynamicAdapter adapter, PageHandler handler)
    {
        this.db = db;
        this.context = context;
        this.docs = docs;
        this.adapter = adapter;
        this.handler = handler;
    }

    public DialogUploadDocument(){}

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                StaticMessages.Toast("Upload Started", context);
                for(CompanyDocument doc : docs)
                {
                    db.collection("companies").add(doc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference)
                        {
                            handler.removeView(handler.getCurrentPage(adapter), adapter);
                            StaticMessages.Toast("List uploaded", context);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            StaticMessages.Toast("Could not upload this document", context);
                        }
                    });
                }

                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;
        }
    }
}
