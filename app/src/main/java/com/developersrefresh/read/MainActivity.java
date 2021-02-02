package com.developersrefresh.read;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    Button newfile;
    String type;
    private ItemArrayAdapter itemArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            type = extras.getString("filename");
        }
        newfile=findViewById(R.id.newfile);
        newfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, ""+itemArrayAdapter., Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout,MainActivity.this);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);
        InputStream inputStream= null;
        try {
            inputStream = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"contact_manager"+"/"+type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //InputStream inputStream = getResources().openRawResource(R.raw.stats);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> scoreList = csvFile.read();

        for(String[] scoreData:scoreList ) {
            itemArrayAdapter.add(scoreData);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//        MenuItem menuItem=menu.findItem(R.id.search_bar);
//        SearchView searchView=(SearchView)menuItem.getActionView();
//        searchView.setQueryHint("Search");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                Toast.makeText(MainActivity.this, ""+s, Toast.LENGTH_SHORT).show();
//                if(itemArrayAdapter!=null) {
//                    itemArrayAdapter.getFilter().filter(s);
//
//                }
//
//
//
//                return false;
//            }
//        });
//        return  super.onCreateOptionsMenu(menu);
//    }
}
