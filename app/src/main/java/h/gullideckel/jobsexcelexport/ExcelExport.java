package h.gullideckel.jobsexcelexport;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import h.gullideckel.jobsexcelexport.AsyncTasks.CsvToDoc.TaskCsvToDoc;
import h.gullideckel.jobsexcelexport.AsyncTasks.DocumentErroring.TaskCompareCsvDb;
import h.gullideckel.jobsexcelexport.AsyncTasks.ReadCsv.TaskReadCsv;
import h.gullideckel.jobsexcelexport.Interfaces.ICompanyDocuments;
import h.gullideckel.jobsexcelexport.Interfaces.ICsvData;
import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.Objects.DocsDbCsv;
import h.gullideckel.jobsexcelexport.Statics.StaticMessages;
import h.gullideckel.jobsexcelexport.ViewPagerFragments.FragDirectory;
import h.gullideckel.jobsexcelexport.ViewPagerFragments.EntrieErrors.FragEntrieErrors;
import h.gullideckel.jobsexcelexport.ViewPagerFragments.FragExcelTable;
import h.gullideckel.jobsexcelexport.ViewPagerFragments.NewEntries.FragNewEntries;

public class ExcelExport extends FragmentActivity implements ICompanyDocuments, ICsvData
{
    private static final String TAG = "ExcelExport";

    private String filePath;

    private ViewPager viewPager;
    private TabLayout tabs;

    private ViewPagerAdapter adapter;

    private FragDirectory fragDirectory;
    private FragEntrieErrors fragEntrieErrors;
    private FragExcelTable fragExcelTable;
    private FragNewEntries fragNewEntries;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private TaskReadCsv taskReadCsv;
    private TaskCompareCsvDb taskCompare;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel_export);

        taskCompare = new TaskCompareCsvDb(this);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        LoginInStandartUser("c.gullideckel@gmail.com","12345678");

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        filePath = sharedPref.getString(getString(R.string.saved_directory_key), "");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabs = (TabLayout) findViewById(R.id.tabsErrors);

        RunCsvReader();

        AddTabs(viewPager);
        tabs.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
    }

    private void RunCsvReader()
    {
        if(!filePath.isEmpty() && filePath.toLowerCase().endsWith(".csv"))
        {
            File file = new File(filePath);
            taskReadCsv = new TaskReadCsv(this, this);
            if(taskReadCsv.getStatus() == AsyncTask.Status.RUNNING)
                taskReadCsv.cancel(true);
            taskReadCsv.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR ,file);
        }
    }

    private void LoginInStandartUser(String email, String password)
    {
        if(user == null)
        {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        Log.d(TAG, "signInWithEmail:success");
                        if(auth.getCurrentUser().isEmailVerified())
                        {
                            LoadJobs();
                            user = auth.getCurrentUser();
                            Log.d(TAG, "createUserWithEmail:success");
                            StaticMessages.Toast("User log in successfull", ExcelExport.this);
                        }
                        else
                            StaticMessages.Toast("Not email verified", ExcelExport.this);
                    }
                    else
                    {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        StaticMessages.Toast("Authentication failed.", ExcelExport.this);
                    }
                }
            });
        }
        else
            LoadJobs();
    }

    private void LoadJobs()
    {
        final List<CompanyDocument> docs = new ArrayList<>();

        db.collection("companies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                for(DocumentSnapshot document : task.getResult())
                {
                    CompanyDocument doc = document.toObject(CompanyDocument.class);
                    doc.setId(document.getId());

                    docs.add(doc);
                }
                taskCompare.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, docs);
                StaticMessages.Toast("Data from Server loaded", ExcelExport.this);
            }

        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                StaticMessages.Toast("Couldn't load data from Database", ExcelExport.this);
                Log.e(TAG, "onFailure: ", e);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        user = auth.getCurrentUser();
    }

    private void AddTabs(ViewPager viewPager)
    {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        fragNewEntries = FragNewEntries.newInstance();
        fragEntrieErrors = FragEntrieErrors.newInstance();
        fragExcelTable = FragExcelTable.newInstance();
        fragDirectory = FragDirectory.newInstance(this, taskReadCsv);
        adapter.addFrag(fragDirectory, "SD card");
        adapter.addFrag(fragNewEntries, "New entries");
        adapter.addFrag(fragEntrieErrors, "Entry errors");
        adapter.addFrag(fragExcelTable, "Full table");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onRecieveDocuments(List<DocsDbCsv> docs)
    {
        if(docs == null)
            StaticMessages.Toast("Creating Documents are canceled. Open csv file again!", this);
        else if(docs.size() > 0)
        {
            StaticMessages.Toast("Entries have been read", this);
            adapter.getItem(1);
            List<DocsDbCsv> docsCorrect = new ArrayList<>();
            List<DocsDbCsv> docsIncorrect = new ArrayList<>();
            for(DocsDbCsv doc : docs)
            {
                if(!doc.getDocCsv().isCorrect() || !doc.getDocCsv().isMayCorrect())
                    docsIncorrect.add(doc);
                else
                    docsCorrect.add(doc);
            }
            fragNewEntries.SetDocumentPages(docsCorrect, this);
            fragEntrieErrors.SetDocumentPages(docsIncorrect, this);
        }
        else
        {
            Log.d(TAG, "onRecieveDocuments: Couldn't read any entries in Csv file");
            StaticMessages.Toast("Couldn't read any entries in Csv file", this);
        }

    }

    @Override
    public void onRecieveData(List<String[][]> data)
    {
        if(data == null)
            StaticMessages.Toast("Creating Documents are canceled. Open csv file again!", this);
        else if(data.size() > 0)
        {
            TaskCsvToDoc csvToDoc = new TaskCsvToDoc(this, taskCompare);
            csvToDoc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
            fragExcelTable.RunTaskCreateTable(data, this);
        }
        else
        {
            StaticMessages.Toast("Couldn't read any entries in file", this);
            Log.d(TAG, "onRecieveData: No entries");
        }
    }
}
