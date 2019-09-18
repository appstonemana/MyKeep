package com.mb.mykeep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mb.mykeep.Note;
import com.mb.mykeep.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVh> {
    List<Note> list;
    OnItemClickListener listener;
    private Context context;

    public NoteAdapter(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoteVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_layout, parent, false);
        return (new NoteVh((view)));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVh holder, int position) {
        final Note note = list.get(position);
        holder.mTvtitle.setText(note.title);
        holder.mTvtext.setText(note.content);

        holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(note.id);

            }
        });

        holder.mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUpdate(note.id, note.title, note.content);
            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setListener(OnItemClickListener listener) {

        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onUpdate(String id, String title, String text);

        void onDelete(String id);
    }

    public class NoteVh extends RecyclerView.ViewHolder {

        TextView mTvtitle, mTvtext;
        Button mBtnUpdate, mBtnDelete;

        public NoteVh(@NonNull View itemView) {
            super(itemView);
            mTvtitle = itemView.findViewById(R.id.tv_tit);
            mTvtext = itemView.findViewById(R.id.tv_txt);

            mBtnDelete = itemView.findViewById(R.id.btn_del);
            mBtnUpdate = itemView.findViewById(R.id.btn_upd);
        }
    }

}
