package com.example.seriesandmovieslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public int listIndex;

    private Button addbutton;
    private Button deletebutton;

    public DatabaseReference reff;

    public final ArrayList<Series> seriesList = new ArrayList<>();
    public final ArrayList<String> dbIDList = new ArrayList<>();
    public int ID = 0;
    public String sID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addbutton = (Button) findViewById(R.id.AddButton);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddItemActivity();
            }
    });

        Toast.makeText(MainActivity.this, "Firebase connection success", Toast.LENGTH_LONG).show();

        Log.d(TAG, "onCreate: Started.");

        ListView mListView = (ListView) findViewById(R.id.listView);
        //Series adapter
        final SeriesListAdapter adapter = new SeriesListAdapter(this, R.layout.adapter_view_layout, seriesList);
        mListView.setAdapter(adapter);

        reff = FirebaseDatabase.getInstance().getReference().child("Series");



        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                seriesList.clear();
                dbIDList.clear();
                for(DataSnapshot seriesSnapshot: snapshot.getChildren()){
                    try {
                        Series series = seriesSnapshot.getValue(Series.class);

                        //Toast.makeText(MainActivity.this, "it works", Toast.LENGTH_LONG).show();

                        String name = series.getName();
                        String platform = series.getPlatform();
                        String year = series.getYear();


                        DatabaseReference reff;
                        reff = FirebaseDatabase.getInstance().getReference().child("Series");

                        String dbID = seriesSnapshot.getKey();
                        sID = Integer.toString(ID);

                        if(platform.equals("netflix") || platform.equals("Netflix") || platform.equals("NETFLIX"))
                        {
                            Series newOne = new Series(name, platform, year, dbID, ID, "drawable://" + R.drawable.netflix);
                            seriesList.add(newOne);
                        }
                        else if(platform.equals("hbo") || platform.equals("HBO") || platform.equals("Hbo"))
                        {
                            Series newOne = new Series(name, platform, year, dbID, ID, "drawable://" + R.drawable.hbo);
                            seriesList.add(newOne);
                        }
                        else
                        {
                            Series newOne = new Series(name, platform, year, dbID, ID, "drawable://" + R.drawable.unknown);
                            seriesList.add(newOne);
                        }
                        ID = ID + 1;


                        dbIDList.add(dbID);

                        //Toast.makeText(MainActivity.this, dbID, Toast.LENGTH_LONG).show();
                    }
                    catch(DatabaseException e){
                        snapshot.getKey();
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void openAddItemActivity(){
        Intent intent = new Intent(this, AddEditActivity.class);
        startActivity(intent);
    }

    public void openAddItemActivity(View view) {
        Intent intent = new Intent(this, AddEditActivity.class);
        startActivity(intent);
    }

}