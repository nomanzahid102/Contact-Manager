package com.developersrefresh.read;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
Button opencsv;
    private int PICK_PDF_REQUEST = 1;
    private Uri filePath;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String path;
    InputStream inStream = null;
    OutputStream outStream = null;
    List<String> results = new ArrayList<String>();
    ListView listView;
    TextView noitem;
    public static  List<ModelClass> dataModels;
    fileadapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.listview);
        noitem = findViewById(R.id.noitem);
        dataModels = new ArrayList<>();
        if(checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }
        if (ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            CreateFolder();

        } else {

            ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        }


        File file = new File(Environment.getExternalStorageDirectory(), "contact_manager");
        if (file.exists()) {
            //Toast.makeText(this, "directory exist already", Toast.LENGTH_SHORT).show();




        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "contact_manager";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files.length == 0) {
            listView.setVisibility(View.GONE);
            noitem.setVisibility(View.VISIBLE);

        }

        else {
            Log.d("Files", "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                //Toast.makeText(this, ""+files[i].getName(), Toast.LENGTH_SHORT).show();
                dataModels.add(new ModelClass(files[i].getName()));
                Log.d("Files", "FileName:" + files[i].getName());
            }
        }
        }
        adapter = new fileadapter(dataModels, MainActivity2.this);
        listView.setAdapter(adapter);
        //File[] files = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"contact_manager").listFiles();

        opencsv=findViewById(R.id.opencsv);
        opencsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateFolder();
                Intent i=new Intent();
//                i.setType("text/csv");
                i.setType("text/*");
//                i.setType("*/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select CSV File"), PICK_PDF_REQUEST);


//
//                Uri uri= Uri.parse(Environment.getExternalStorageDirectory()+"/"+"contact_manager"+"/");
//                startActivity(new Intent(Intent.ACTION_GET_CONTENT).setDataAndType(uri,"*/*"));
//                //startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(MainActivity2.this, MainActivity.class);
                intent.putExtra("filename",dataModels.get(i).getName());
                //Toast.makeText(Employdashboard.this, ""+sessionManager.get_id(), Toast.LENGTH_SHORT).show();
                startActivity(intent);



                //Toast.makeText(MainActivity2.this, "hello"+dataModels.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && (grantResults.length>0) && (grantResults[0]==PackageManager.PERMISSION_GRANTED)){

            CreateFolder();

        }
        else{
            //Toast.makeText(this, "Set permission first", Toast.LENGTH_SHORT).show();

        }
    }

    private void CreateFolder() {

        File file=new File(Environment.getExternalStorageDirectory(),"contact_manager");
        if(file.exists()){
            //Toast.makeText(this, "directory exist already", Toast.LENGTH_SHORT).show();

        }
        else{

            file.mkdir();
            if(file.isDirectory()){

                Toast.makeText(this, "Diretory created Successfully", Toast.LENGTH_SHORT).show();

            }
            else
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity2.this);
                String smessage="Message: failed to create directory"+"\npath:"+Environment.getExternalStorageDirectory()+"\nmkdirs:"+file.mkdir();
                builder.setMessage(smessage);
                builder.show();

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem=menu.findItem(R.id.search_bar);
        SearchView searchView=(SearchView)menuItem.getActionView();
       searchView.setQueryHint("Search");
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null) {
                    adapter.getFilter().filter(newText);

                }
                return false;
            }
        });












        return  super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            File filee= new File(filePath.getPath());
            path=FilePath.getPath(MainActivity2.this,filePath);
            Toast.makeText(this, ""+path, Toast.LENGTH_SHORT).show();
            File from = new File(FilePath.getPath(MainActivity2.this,filePath));
            File to = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"contact_manager"+"/"+path.substring(path.lastIndexOf("/")+1));
            from.renameTo(to);

        }
        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
        finish();
    }




    private  boolean checkAndRequestPermissions() {
        int callphone = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);
        int writeexternal = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readcontact = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);
        int readfile = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (callphone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (readcontact != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (writeexternal != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (readfile != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    private void copyFile(String inputPath, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }


}