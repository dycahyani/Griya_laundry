package id.deyak.modul1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String database_name = "progmobdeyak";
    public static final int database_version = 1;

    public static final String tabel_member = "tb_member";
    public static final String id_member = "id_member";
    public static final String nama = "nama";
    public static final String phone = "no_hp";
    public static final String email = "email";
    public static final String alamat = "alamat";
    public static final String gender = "jenis_kelamin";
    public static final String usia = "usia";
    public static final String validasi = "validasi";

    private SQLiteDatabase db;

    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, database_name, null, database_version);
        db = getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tabel_member + " ("
                + id_member + " integer primary key autoincrement,"
                + nama + " text not null,"
                + phone + " textdeyak not null,"
                + email + " text not null,"
                + alamat + " text not null,"
                + gender + " text not null,"
                + usia + " int not null,"
                + validasi + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + tabel_member);
    }


    public void InsertMember(ContentValues values){
        db.insert(tabel_member, null, values);
    }

    public void UpdateMember(ContentValues values, long id){
        db.update(tabel_member, values, id_member + "=" + id, null);
    }

    public void deleteMember(long id){
        db.delete(tabel_member, id_member + "=" + id, null);
    }

    public Cursor getAllMember(){
        return db.query(tabel_member, null, null, null, null, null, nama + " ASC");
    }

    public Cursor getMember(long id){
        return db.rawQuery("select * from " + tabel_member + " where " + id_member + "=" + id, null);
    }
}