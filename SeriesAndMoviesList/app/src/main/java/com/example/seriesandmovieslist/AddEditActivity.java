package com.example.seriesandmovieslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddEditActivity extends AppCompatActivity {

    String sname;
    String syear;
    String splatform;
    String sID;
    String cID;
    String editID;
    int fID;
    int counter=-1;

    public final ArrayList<String> dbIDList = new ArrayList<>();

    EditText name;
    EditText year;
    EditText platform;
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
    }

    public void addEditToList(View view){

        DatabaseReference reff;
        reff = FirebaseDatabase.getInstance().getReference().child("Series");

        String dbID = reff.push().getKey();
        int ID;
        String sID;

        name = (EditText) findViewById(R.id.editName);
        platform = (EditText) findViewById(R.id.editPlatform);
        year = (EditText) findViewById(R.id.editYear);
        id = (EditText) findViewById(R.id.editID2);

        sname = name.getText().toString();
        splatform = platform.getText().toString();
        syear = year.getText().toString();
        editID = id.getText().toString();
        ID = 1;
        //-------------------------

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                counter = 0;
                dbIDList.clear();
                for(DataSnapshot seriesSnapshot: snapshot.getChildren()){
                    try {
                        String dbIDcheck = seriesSnapshot.getKey();
                        cID = dbIDcheck.substring(16,20);
                        dbIDList.add(dbIDcheck);
                        if(cID.equals(editID)) {
                            fID = counter;
                            String sfID = Integer.toString(fID);
                            //Toast.makeText(AddEditActivity.this, sfID, Toast.LENGTH_LONG).show();
                            break;
                        }
                        counter = counter + 1;
                    }
                    catch(DatabaseException e){
                        snapshot.getKey();
                        Toast.makeText(AddEditActivity.this, "error", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        //------------------------
        if(dbIDList.isEmpty() && (name.length() == 0 || platform.length() == 0 || year.length() == 0)) {
            Toast.makeText(AddEditActivity.this, "You forgot some informations", Toast.LENGTH_SHORT).show();
        }
        else if(dbIDList.isEmpty() && id.length() > 0) Toast.makeText(AddEditActivity.this, "No items to edit or click once again", Toast.LENGTH_SHORT).show();
        else if(editID.length() == 0)
        {
            Toast.makeText(this, "Item added/edited", Toast.LENGTH_LONG).show();

            if(name.length() > 0 && platform.length() > 0 && year.length() > 0 &&
            (splatform.equals("netflix") || splatform.equals("Netflix") || splatform.equals("NETFLIX"))) {
                Series newOne = new Series(sname, splatform, syear, dbID, ID, "drawable://" + R.drawable.netflix);
                reff.child(dbID).setValue(newOne);
                Toast.makeText(this, "Item added/edited", Toast.LENGTH_LONG).show();
                name.getText().clear();
                platform.getText().clear();
                year.getText().clear();
                id.getText().clear();
            }
            else if(name.length() > 0 && platform.length() > 0 && year.length() > 0 &&
                    (platform.equals("hbo") || platform.equals("HBO") || platform.equals("Hbo"))) {
                Series newOne = new Series(sname, splatform, syear, dbID, ID, "drawable://" + R.drawable.hbo);
                reff.child(dbID).setValue(newOne);
                Toast.makeText(this, "Item added/edited", Toast.LENGTH_LONG).show();
                name.getText().clear();
                platform.getText().clear();
                year.getText().clear();
                id.getText().clear();
            }
            else if(name.length() > 0 && platform.length() > 0 && year.length() > 0) {
                Series newOne = new Series(sname, splatform, syear, dbID, ID, "drawable://" + R.drawable.netflix);
                reff.child(dbID).setValue(newOne);
                Toast.makeText(this, "Item added/edited", Toast.LENGTH_LONG).show();
                name.getText().clear();
                platform.getText().clear();
                year.getText().clear();
                id.getText().clear();
            }
            else Toast.makeText(this, "You forgot some informations", Toast.LENGTH_LONG).show();


        }
        else if(editID.length() == 4 && counter > -1 && dbIDList.get(fID).substring(16,20).equals(editID)){
            String sfID = Integer.toString(fID);
            String short_deleteID = dbIDList.get(fID).substring(16,20);
            String deleteID = dbIDList.get(fID);
            Toast.makeText(this, deleteID, Toast.LENGTH_LONG).show();

            Toast.makeText(this, deleteID, Toast.LENGTH_LONG).show();
            reff = FirebaseDatabase.getInstance().getReference().child("Series").child(deleteID);


            Toast.makeText(this, "Item added/edited", Toast.LENGTH_LONG).show();

            if(name.length() > 0 && platform.length() > 0 && year.length() > 0 &&
                    (splatform.equals("netflix") || splatform.equals("Netflix") || splatform.equals("NETFLIX"))) {
                Series newOne = new Series(sname, splatform, syear, dbID, ID, "drawable://" + R.drawable.netflix);
                reff.removeValue();
                reff = FirebaseDatabase.getInstance().getReference().child("Series");
                reff.child(dbID).setValue(newOne);
                Toast.makeText(this, "Item added/edited", Toast.LENGTH_LONG).show();

                fID = -1;
                //Toast.makeText(this, dbID, Toast.LENGTH_LONG).show();
                name.getText().clear();
                platform.getText().clear();
                year.getText().clear();
                id.getText().clear();
            }
            else if(name.length() > 0 && platform.length() > 0 && year.length() > 0 &&
                    (splatform.equals("hbo") || splatform.equals("Hbo") || splatform.equals("HBO"))) {
                Series newOne = new Series(sname, splatform, syear, dbID, ID, "drawable://" + R.drawable.hbo);
                reff.removeValue();
                reff = FirebaseDatabase.getInstance().getReference().child("Series");
                reff.child(dbID).setValue(newOne);
                Toast.makeText(this, "Item added/edited", Toast.LENGTH_LONG).show();

                fID = -1;
                //Toast.makeText(this, dbID, Toast.LENGTH_LONG).show();
                name.getText().clear();
                platform.getText().clear();
                year.getText().clear();
                id.getText().clear();
            }
            else if(name.length() > 0 && platform.length() > 0 && year.length() > 0) {
                Series newOne = new Series(sname, splatform, syear, dbID, ID, "drawable://" + R.drawable.unknown);
                reff.removeValue();
                reff = FirebaseDatabase.getInstance().getReference().child("Series");
                reff.child(dbID).setValue(newOne);
                Toast.makeText(this, "Item added/edited", Toast.LENGTH_LONG).show();

                fID = -1;
                //Toast.makeText(this, dbID, Toast.LENGTH_LONG).show();
                name.getText().clear();
                platform.getText().clear();
                year.getText().clear();
                id.getText().clear();
            }
            else Toast.makeText(this, "You forgot some informations", Toast.LENGTH_LONG).show();



        }
        else if(counter < 0) Toast.makeText(this, "Click again", Toast.LENGTH_LONG).show();
        else Toast.makeText(this, "Wrong ID or click once again", Toast.LENGTH_LONG).show();


    }

    public void deleteByID(View view){

        DatabaseReference reff;

        id = (EditText) findViewById(R.id.editID);
        sID = id.getText().toString();

        reff = FirebaseDatabase.getInstance().getReference().child("Series");



        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                counter = 0;
                dbIDList.clear();
                for(DataSnapshot seriesSnapshot: snapshot.getChildren()){
                    try {
                        String dbID = seriesSnapshot.getKey();
                        cID = dbID.substring(16,20);
                        dbIDList.add(dbID);
                        if(cID.equals(sID)) {
                            fID = counter;
                            String sfID = Integer.toString(fID);
                            //Toast.makeText(AddEditActivity.this, sfID, Toast.LENGTH_LONG).show();
                            break;
                            }
                        counter = counter + 1;
                    }
                    catch(DatabaseException e){
                        snapshot.getKey();
                        Toast.makeText(AddEditActivity.this, "error", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        if(dbIDList.size() > 0 && (dbIDList.get(fID).substring(16,20)).equals(sID)) {
            String deleteID = dbIDList.get(fID);
            reff = FirebaseDatabase.getInstance().getReference().child("Series").child(deleteID);
            reff.removeValue();
            fID = -1;
        }
        else if(dbIDList.size() > 0) Toast.makeText(AddEditActivity.this, "Wrong ID or click once again", Toast.LENGTH_SHORT).show();
        else Toast.makeText(AddEditActivity.this, "No items to delete", Toast.LENGTH_SHORT).show();
    }

    public void deleteAll(View view){
        Toast.makeText(AddEditActivity.this, "All deleted", Toast.LENGTH_SHORT).show();

        DatabaseReference reff;
        reff = FirebaseDatabase.getInstance().getReference().child("Series");
        reff.removeValue();
    }
}