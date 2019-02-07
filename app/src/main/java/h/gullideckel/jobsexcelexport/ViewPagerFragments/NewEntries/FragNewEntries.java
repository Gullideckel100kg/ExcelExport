package h.gullideckel.jobsexcelexport.ViewPagerFragments.NewEntries;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import h.gullideckel.jobsexcelexport.AsyncTasks.BuiltViewsDocuments.TaskBuildDocs;
import h.gullideckel.jobsexcelexport.DynamicViewPager.PageHandler;
import h.gullideckel.jobsexcelexport.DynamicViewPager.ViewPagerDynamicAdapter;
import h.gullideckel.jobsexcelexport.Interfaces.IBuildDocs;
import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.Objects.DocsDbCsv;
import h.gullideckel.jobsexcelexport.Objects.ViewsDocs;
import h.gullideckel.jobsexcelexport.R;
import h.gullideckel.jobsexcelexport.ViewPagerFragments.EntrieErrors.DialogUpdateDocument;


public class FragNewEntries extends Fragment implements IBuildDocs
{
    private ViewPager vwPgerDocuments;
    private ViewPagerDynamicAdapter adapter;

    private Button btnUploadAll, btnUploadOne;

    private volatile boolean isView = false;

    private TaskBuildDocs task;
    private List<ViewsDocs> views;
    private FirebaseFirestore db;

    private PageHandler handler;
    private AlertDialog.Builder builder;

    public static FragNewEntries newInstance()
    {
        FragNewEntries fragment = new FragNewEntries();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_new_entries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        vwPgerDocuments = (ViewPager) view.findViewById(R.id.vwPgerDocuments);
        adapter = new ViewPagerDynamicAdapter();
        vwPgerDocuments.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        btnUploadAll = (Button) view.findViewById(R.id.btnUploadAll);
        btnUploadOne = (Button) view.findViewById(R.id.btnUploadOne);

        isView = true;
        if(task != null)
            task.SetView(isView);

        handler = new PageHandler(vwPgerDocuments);
        builder = new AlertDialog.Builder(getContext());

        btnUploadAll.setOnClickListener(UploadAll);
        btnUploadOne.setOnClickListener(UploadOne);
    }

    private View.OnClickListener UploadAll = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if (views != null && views.size() > 0)
            {
                List<CompanyDocument> docs = new ArrayList<>();
                for (ViewsDocs view : views)
                {
                    docs.add(view.getDocs().getDocCsv());
                }

                builder.setMessage("Do you want to upload all Documents to the Database?")
                        .setPositiveButton("Yes", new DialogUploadDocument(db, getContext(), docs, adapter, handler))
                        .setNegativeButton("No", new DialogUploadDocument()).show();
            }
        }
    };

    private View.OnClickListener UploadOne = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if (views != null && views.size() > 0)
            {

                List<CompanyDocument> docs = new ArrayList<>();

                View view = handler.getCurrentPage(adapter);

                for (ViewsDocs vDoc : views)
                {
                    if (vDoc.getViewCsv().equals(view))
                        docs.add(vDoc.getDocs().getDocCsv());
                }

                builder.setMessage("Do you want to upload this Document to the Database?")
                        .setPositiveButton("Yes", new DialogUploadDocument(db, getContext(), docs, adapter, handler))
                        .setNegativeButton("No", new DialogUploadDocument()).show();
            }
        }
    };

    public void SetDocumentPages(List<DocsDbCsv> docs, Context context)
    {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        task = new TaskBuildDocs(inflater, context, this);
        task.SetView(isView);
        if(task.getStatus() == AsyncTask.Status.RUNNING)
            task.cancel(true);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR ,docs);
    }

    @Override
    public void onRecieveViews(List<ViewsDocs> views)
    {
        this.views = views;
        for(ViewsDocs v : views)
            adapter.addView(v.getViewCsv());

        adapter.notifyDataSetChanged();
    }
}
