package id.deyak.modul1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    TextView showNama, showNo, showEmail, showAlamat, showJK, showUmur, showValidasi;
    String nama, no, email, alamat, jeniskelamin, umur, validasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        showNama = (TextView) findViewById(R.id.showNama);
        showNo = (TextView) findViewById(R.id.showNo);
        showEmail = (TextView) findViewById(R.id.showEmail);
        showAlamat = (TextView) findViewById(R.id.showAlamat);
        showJK = (TextView) findViewById(R.id.showJK);
        showUmur = (TextView) findViewById(R.id.showUmur);
        showValidasi = (TextView) findViewById(R.id.showValidasi);

        //validasi, menyimpan nilai variabel dari intent, set text pada textview untuk menampilkan nilai
        if (getIntent().getStringExtra("nama")!=""){
            nama = getIntent().getStringExtra("nama");
            showNama.setText(nama);
        }
        if (getIntent().getStringExtra("no")!="") {
            no = getIntent().getStringExtra("no");
            showNo.setText(no);
        }
        if (getIntent().getStringExtra("email")!="") {
            email = getIntent().getStringExtra("email");
            showEmail.setText(email);
        }
        if (getIntent().getStringExtra("alamat")!="") {
            alamat = getIntent().getStringExtra("alamat");
            showAlamat.setText(alamat);
        }
        if (getIntent().getStringExtra("jeniskelamin")!="") {
            jeniskelamin = getIntent().getStringExtra("jeniskelamin");
            showJK.setText(jeniskelamin);
        }
        if (getIntent().getStringExtra("umur")!="") {
            umur = getIntent().getStringExtra("umur");
            showUmur.setText(umur);
        }
        if (getIntent().getStringExtra("validasi")!="") {
            validasi = getIntent().getStringExtra("kategori");
            showValidasi.setText(validasi);
        }

        //mengambil widget button pada activity2
        Button b_kembali =(Button)findViewById(R.id.btn_exit);
        Button b_list = (Button)findViewById(R.id.btn_view);

        b_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true); //method keluar dari app
                Toast.makeText(getApplicationContext(), "Sampai Bertemu Kembali Di Griya Laundry", Toast.LENGTH_SHORT).show();
            }
        });

        b_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity2.this, listMember.class));
            }
        });




    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Menampilkan Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"Menjeda Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(this," Memulai Activity Kembali", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Menghancurkan activity", Toast.LENGTH_SHORT).show();
    }

}