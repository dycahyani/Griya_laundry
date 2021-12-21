package id.deyak.modul1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class editMember extends AppCompatActivity {
    EditText Name;
    EditText Phone;
    EditText Email;
    EditText Add;
    RadioButton rb1;
    RadioGroup rg;
    RadioButton rb;
    RadioButton rb2;
    SeekBar sb;
    TextView usia;
    CheckBox cb2;
    Button buttonUpdate;
    private DatabaseHelper database;
    private long id;
    private Intent intent;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_edit);

        intent = getIntent();
        id = intent.getLongExtra(database.id_member,0);

        Name = (EditText) findViewById(R.id.Name2);
        Phone= (EditText) findViewById(R.id.Phone2);
        Email = (EditText) findViewById(R.id.Email2);
        Add = (EditText) findViewById(R.id.Add2);
        buttonUpdate= findViewById(R.id.update_btn);
        //jenis kelamin
        rg = (RadioGroup) findViewById(R.id.rg2);

        //database
        database = new DatabaseHelper(this);


        //umur
        sb = (SeekBar) findViewById(R.id.sb2);
        usia = findViewById(R.id.usia2);
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
        cb2 = (CheckBox) findViewById(R.id.cb2);

        getMember();
    }

    private void getMember(){
        rb1 = (RadioButton) findViewById(R.id.rb12);
        rb2 = (RadioButton) findViewById(R.id.rb22);


        Cursor cursor = database.getMember(id);
        if(cursor.moveToFirst()){
            String nama = cursor.getString(cursor.getColumnIndex(database.nama));
            String no = cursor.getString(cursor.getColumnIndex(database.phone));
            String email = cursor.getString(cursor.getColumnIndex(database.email));
            String alamat = cursor.getString(cursor.getColumnIndex(database.alamat));
            String gender = cursor.getString(cursor.getColumnIndex(database.gender));
            String umur = cursor.getString(cursor.getColumnIndex(database.usia));
            String validasi = cursor.getString(cursor.getColumnIndex(database.validasi));

            Name.setText(nama);
            Phone.setText(no);
            Email.setText(email);
            Add.setText(alamat);
            usia.setText(umur);
            //ngambil id dari db terus dihubungin ke radio button yang ada (jenis kelamin)
            if (gender == "Wanita"){
                rb1.setChecked(true);
            }
            if (gender == "Pria"){
                rb2.setChecked(true);
            }

            //validasi
            if (validasi == "Valid"){
                cb2.setChecked(true);
            }
        }
    }



    public void update(View view) {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(editMember.this);
                builder.setTitle("UPDATE MEMBER");
                builder.setMessage("Apakah anda akan mengupdate data ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Data Berhasil diupdate!", Toast.LENGTH_SHORT).show();
                        Intent list = new Intent(getApplicationContext(), listMember.class);
                        prosesUpdate();
                        startActivity(list);

                    }
                });

                builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Update Data dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialogUpdate = builder.create();
                dialogUpdate.show();
            }

        });
    }

    private void prosesUpdate() {
        int radio = rg.getCheckedRadioButtonId();
        rb = findViewById(radio);

        String nama = Name.getText().toString().trim();
        String no = Phone.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String alamat = Add.getText().toString().trim();
        String jeniskelamin = rb.getText().toString().trim();
        String umur = usia.getText().toString().trim();
        String validasi = "";
        //checkbox
        if (cb2.isChecked()) {
            validasi += "Valid";

            //validasi dan alert
            if (TextUtils.isEmpty(nama)) {
                Name.setError("Nama Tidak Boleh Kosong!");
            } else if (TextUtils.isEmpty(email)) {
                Email.setError("Email Tidak Boleh Kosong!");
            } else if (TextUtils.isEmpty(no)) {
                Phone.setError("Nomor Telepon Tidak Boleh Kosong!");
            } else if (TextUtils.isEmpty(alamat)) {
                Add.setError("Alamat Tidak Boleh Kosong!");
            } else {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.nama, nama);
                values.put(DatabaseHelper.phone, no);
                values.put(DatabaseHelper.email, email);
                values.put(DatabaseHelper.alamat, alamat);
                values.put(DatabaseHelper.gender, jeniskelamin);
                values.put(DatabaseHelper.usia, umur);
                values.put(DatabaseHelper.validasi, validasi);
                if (intent.hasExtra(database.id_member)) {
                    database.UpdateMember(values,id);
                } else {
                    database.InsertMember(values);
                }
                finish();

            }
        }
    }
}