package id.deyak.modul1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class listMember extends AppCompatActivity {
    private RecyclerView memberz;
    private memberAdapter Memberadapter;
    private DatabaseHelper database;
    Button btnLogout;
    private LinearLayout edit_member, delete_member;
    private Button btn_batal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_member);

        database = new DatabaseHelper(this);

        memberz = findViewById(R.id.memberz);

        memberz.setLayoutManager(new LinearLayoutManager(this));
        Memberadapter = new memberAdapter(this,database.getAllMember());
        memberz.setAdapter(Memberadapter);

        //rg = findViewById(R.id.rg);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent _intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(_intent);
                finish();
            }
        });

        Memberadapter.setOnItemClickListener(new memberAdapter.OnClickListenerMember(){
            @Override
            public void onItemClickMember(long id) {
                LayoutInflater inflater = LayoutInflater.from(listMember.this);
                View view = inflater.inflate(R.layout.popup_member,null);
                edit_member = view.findViewById(R.id.edit_member);
                delete_member = view.findViewById(R.id.delete_member);
                btn_batal = view.findViewById(R.id.btn_batal);


                Dialog popup = new Dialog(listMember.this);
                popup.setContentView(view);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popup.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        edit_member.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent editMember = new Intent(listMember.this, editMember.class);
                                editMember.putExtra(database.id_member,id);
                                startActivity(editMember);
                                popup.dismiss();
                            }
                        });

                        delete_member.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(listMember.this);
                                builder.setTitle("Konfirmasi Penghapusan");
                                builder.setMessage("Apakah anda yakin untuk menghapus data ini?");
                                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        database.deleteMember(id);
                                        popup.dismiss();
                                        Memberadapter.swapCursor(database.getAllMember());
                                    }
                                });
                                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        popup.dismiss();
                                    }
                                });
                                AlertDialog popupKonfirm = builder.create();
                                popupKonfirm.show();

                            }
                        });

                        btn_batal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
                popup.show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Memberadapter.swapCursor(database.getAllMember());
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        Memberadapter.swapCursor(database.getAllMember());
    }*/
}