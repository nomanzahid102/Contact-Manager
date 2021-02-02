package com.developersrefresh.read;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class userdetial extends AppCompatActivity {
    TextView name,number;
    String nam,numbe;
    LinearLayout phonecall,phonewhats;
    //String mimeString = "vnd.android.cursor.item/vnd.com.whatsapp.video.call";
    String mimeString = "vnd.android.cursor.item/vnd.com.whatsapp.voip.call";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetial);
        name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        phonecall=findViewById(R.id.phonecall);
        phonewhats=findViewById(R.id.phonewhats);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            //Toast.makeText(this, "not null", Toast.LENGTH_SHORT).show();
            nam = extras.getString("name");
            numbe = extras.getString("phone");
            name.setText(nam);
            number.setText(numbe);
            //Toast.makeText(this, ""+type, Toast.LENGTH_SHORT).show();



        }
        phonecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+numbe));
                startActivity(intent);
            }
        });
        phonewhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   String contactNumber = numbe; // to change with real value

                Cursor cursor = userdetial.this.getContentResolver ()
                        .query (
                                ContactsContract.Data.CONTENT_URI,
                                new String [] { ContactsContract.Data._ID },
                                ContactsContract.RawContacts.ACCOUNT_TYPE + " = 'com.whatsapp' " +
                                        "AND " + ContactsContract.Data.MIMETYPE + " = 'vnd.android.cursor.item/vnd.com.whatsapp.voip.call' " +
                                        "AND " + ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE '%" + contactNumber + "%'",
                                null,
                                ContactsContract.Contacts.DISPLAY_NAME
                        );

                if (cursor == null) {
                    // throw an exception
                }

                long id = -1;
                while (cursor.moveToNext()) {
                    id = cursor.getLong (cursor.getColumnIndex (ContactsContract.Data._ID));
                }

                if (!cursor.isClosed ()) {
                    cursor.close ();
                }




                Intent intent = new Intent ();
                intent.setAction (Intent.ACTION_VIEW);

                intent.setDataAndType (Uri.parse ("content://com.android.contacts/data/" + id), "vnd.android.cursor.item/vnd.com.whatsapp.voip.call");
                intent.setPackage ("com.whatsapp");

                startActivity (intent);
*/

                boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+numbe+  "&text="+""));
                    startActivity(intent);
                }else {
                    Toast.makeText(userdetial.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                }






            }
        });
    }
    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }
}