package id.deyak.modul1;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class memberAdapter extends RecyclerView.Adapter<memberAdapter.memberViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private OnClickListenerMember mlistener;

    public memberAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public memberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_list_member,parent, false);
        return new memberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull memberViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String nama = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.nama));
        String phone = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.phone));
        String email = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.email));
        String alamat = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.alamat));
        String gender = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.gender));
        String umur = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.usia));
        String kategori = mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.validasi));
        long id = mCursor.getLong(mCursor.getColumnIndex(DatabaseHelper.id_member));

        holder.itemView.setTag(id);
        holder.mnama.setText(nama);
        holder.mphone.setText(phone);
        holder.malamat.setText(alamat);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class memberViewHolder extends  RecyclerView.ViewHolder {

        public TextView mnama;
        public TextView mphone;
        public TextView malamat;

        public memberViewHolder(@NonNull View itemview) {
            super(itemview);
            mnama = itemView.findViewById(R.id.nama_member);
            mphone = itemView.findViewById(R.id.no);
            malamat = itemView.findViewById(R.id.alamat);

           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long position = (long) itemView.getTag();
                    mlistener.onItemClickMember(position);
                    //Toast.makeText(itemView.getContext(),"id click from adapter : " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public interface OnClickListenerMember{
        void onItemClickMember(long id);
    }

    public void setOnItemClickListener(OnClickListenerMember listener){
        mlistener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor !=null){
            mCursor.close();
        }
        mCursor = newCursor;

        if (newCursor !=null){
            this.notifyDataSetChanged();
        }

    }
}