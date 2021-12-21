package id.deyak.modul1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Name;
    EditText Phone;
    EditText Email;
    EditText Add;
    RadioGroup rg;
    RadioButton rb;
    SeekBar sb;
    TextView usia;
    CheckBox cb1;
    Button buttonSubmit;
    private DatabaseHelper database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.Name);
        Phone= (EditText) findViewById(R.id.Phone);
        Email = (EditText) findViewById(R.id.Email);
        Add = (EditText) findViewById(R.id.Add);
        buttonSubmit= findViewById(R.id.submit_btn);
        //jenis kelamin
        rg = findViewById(R.id.rg);

        //database
        database = new DatabaseHelper(this);


        //umur
        sb = findViewById(R.id.sb);
        usia = findViewById(R.id.usia);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                usia.setText(String.valueOf(progress + "Tahun"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //checkbox
        cb1 = (CheckBox) findViewById(R.id.cb);

    }



    public void submit(View view) {
        int radio = rg.getCheckedRadioButtonId();
        rb = findViewById(radio);


        String nama = Name.getText().toString();
        String no = Phone.getText().toString();
        String email = Email.getText().toString();
        String alamat = Add.getText().toString();
        String jeniskelamin = rb.getText().toString();
        String umur = usia.getText().toString();
        String validasi = "";

        //checkbox
        if (cb1.isChecked()) {
            validasi += "Valid";
        }


        //validasi dan alert
        if(TextUtils.isEmpty(nama)){
            Name.setError("Nama Tidak Boleh Kosong!");
        }
        else if(TextUtils.isEmpty(email)){
            Email.setError("Email Tidak Boleh Kosong!");
        }
        else if(TextUtils.isEmpty(no)){
            Phone.setError("Nomor Telepon Tidak Boleh Kosong!");
        }
        else if(TextUtils.isEmpty(alamat)){
            Add.setError("Alamat Tidak Boleh Kosong!");
        }
        /*else if(radio == -1){
         Toast.makeText(this,"Harap Pilih Jenis Kelamin Anda!",Toast.LENGTH_SHORT).show();
        }*/
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Registrasi Member Berhasil !!!");
            builder.setMessage("Nama : " + String.valueOf(nama) + "\n" + "No Telp :" + String.valueOf(no)
                    + "\n" + "Email :" + String.valueOf(email)
                    + "\n" + "Alamat :" + String.valueOf(alamat) + "\n" + "Jenis Kelamin :" + String.valueOf(jeniskelamin)
                    + "\n" + "Usia :" + String.valueOf(umur) + "\n" + "Validasi Data :" + String.valueOf(validasi)).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prosesSimpan();
                            openActivity2();

                        }

                    });

            AlertDialog dialoghasil = builder.create();
            dialoghasil.show();}
    }

    private void prosesSimpan() {

        String nama = Name.getText().toString().trim();
        String no = Phone.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String alamat = Add.getText().toString().trim();
        String jeniskelamin = rb.getText().toString().trim();
        String umur = usia.getText().toString().trim();
        String validasi = "";

        //checkbox
        if (cb1.isChecked()) {
            validasi += "Valid";
        }

        //validasi dan alert
        if(TextUtils.isEmpty(nama)){
            Name.setError("Nama Tidak Boleh Kosong!");
        }
        else if(TextUtils.isEmpty(email)){
            Email.setError("Email Tidak Boleh Kosong!");
        }
        else if(TextUtils.isEmpty(no)){
            Phone.setError("Nomor Telepon Tidak Boleh Kosong!");
        }
        else if(TextUtils.isEmpty(alamat)){
            Add.setError("Alamat Tidak Boleh Kosong!");
        }
        /*else if(radio == -1){
         Toast.makeText(this,"Harap Pilih Jenis Kelamin Anda!",Toast.LENGTH_SHORT).show();
        }*/
        else {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.nama, nama);
            values.put(DatabaseHelper.phone, no);
            values.put(DatabaseHelper.email,email);
            values.put(DatabaseHelper.alamat, alamat);
            values.put(DatabaseHelper.gender, jeniskelamin);
            values.put(DatabaseHelper.usia, umur);
            values.put(DatabaseHelper.validasi, validasi);

            database.InsertMember(values);
            finish();
        }
    }


    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);

        int radio = rg.getCheckedRadioButtonId();
        rb = findViewById(radio);


        String nama = Name.getText().toString();
        String no = Phone.getText().toString();
        String email = Email.getText().toString();
        String alamat = Add.getText().toString();
        String jeniskelamin = rb.getText().toString();
        String umur = usia.getText().toString();
        String validasi = "";

        //checkbox
        if (cb1.isChecked()) {
            validasi += "Valid ";
        }

        intent.putExtra("nama", nama);
        intent.putExtra("no", no);
        intent.putExtra("email", email);
        intent.putExtra("alamat", alamat);
        intent.putExtra("jeniskelamin", jeniskelamin);
        intent.putExtra("umur", umur);
        intent.putExtra("kategori", validasi);

        startActivity(intent);

    }

}
