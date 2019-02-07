package h.gullideckel.jobsexcelexport.ViewPagerFragments.EntrieErrors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import h.gullideckel.jobsexcelexport.AsyncTasks.BuiltViewsDocuments.TaskBuildDocs;
import h.gullideckel.jobsexcelexport.DynamicViewPager.PageHandler;
import h.gullideckel.jobsexcelexport.DynamicViewPager.ViewPagerDynamicAdapter;
import h.gullideckel.jobsexcelexport.Interfaces.IBuildDocs;
import h.gullideckel.jobsexcelexport.Objects.DocsDbCsv;
import h.gullideckel.jobsexcelexport.Objects.ViewsDocs;
import h.gullideckel.jobsexcelexport.R;
import h.gullideckel.jobsexcelexport.Statics.StaticMessages;

public class FragEntrieErrors extends Fragment implements IBuildDocs
{

    private ViewPager viewPager;
    private ViewPagerDynamicAdapter adapter;
    private ToggleButton tglBtnDbCsv;
    private PageHandler pageHandle;
    private Button btnUpload;
    private boolean isView = false;
    private TaskBuildDocs task;
    private List<ViewsDocs> views;

    private boolean isLogedIn;
    private FirebaseFirestore db;

    public static FragEntrieErrors newInstance()
    {
        FragEntrieErrors fragment = new FragEntrieErrors();
        return fragment;
    }

    public void SetLogedIn(boolean isLogedIn) { this.isLogedIn = isLogedIn; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.frag_entrie_errors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.viewPagerErrors);
        tglBtnDbCsv = (ToggleButton) view.findViewById(R.id.tglBtnDbCsv);
        btnUpload = (Button) view.findViewById(R.id.btnUpload);

        pageHandle = new PageHandler(viewPager);
        adapter = new ViewPagerDynamicAdapter();
        viewPager.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        isView = true;
        if(task != null)
            task.SetView(isView);

        tglBtnDbCsv.setOnCheckedChangeListener(CsvDb);

        btnUpload.setOnClickListener(clickUpload);
    }

    private View.OnClickListener clickUpload = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {

            if (views != null && views.size() > 0)
            {
                View view = pageHandle.getCurrentPage(adapter);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (((TextView) view.findViewById(R.id.txtErrorMessage)).getText().toString().contains("This document is wrong and can not be uploaded"))
                    builder.setMessage("This document is wrong and can not be uploaded").setNegativeButton("Ok", new DialogUpdateDocument());

                for (ViewsDocs vDocs : views)
                {
                    if (vDocs.getViewCsv().equals(view))
                    {
                        if (vDocs.getViewDb() != null)
                        {
                            builder.setMessage("Do you want to update this document in the Database?")
                                    .setPositiveButton("Yes", new DialogUpdateDocument(vDocs.getDocs().getDocDb().getId(), db, getContext(), vDocs.getDocs().getDocCsv()))
                                    .setNegativeButton("No", new DialogUpdateDocument()).show();
                        }
                    }
                    if (vDocs.getViewDb().equals(view))
                    {
                        builder.setMessage("Do you want to update this document in the Database?")
                                .setPositiveButton("Yes", new DialogUpdateDocument(vDocs.getDocs().getDocDb().getId(), db, getContext(), vDocs.getDocs().getDocCsv()))
                                .setNegativeButton("No", new DialogUpdateDocument()).show();
                    }
                }
            }

        }
    };




    private CompoundButton.OnCheckedChangeListener CsvDb = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            View v = pageHandle.getCurrentPage(adapter);
            if(views != null)
                if(isChecked)
                {
                    for(ViewsDocs view : views)
                    {
                        if(view.getViewCsv().equals(v))
                        {
                            if(view.getViewDb() != null)
                            {
                                pageHandle.changeCurrentView(adapter, view.getViewDb());
                            }
                            else
                                tglBtnDbCsv.setChecked(true);
                        }
                    }
                }

            else
                for(ViewsDocs view : views)
                {
                    if(view.getViewDb().equals(v))
                    {
                        pageHandle.changeCurrentView(adapter, view.getViewCsv());
                    }
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
