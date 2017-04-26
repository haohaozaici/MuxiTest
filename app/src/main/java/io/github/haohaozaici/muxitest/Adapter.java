package io.github.haohaozaici.muxitest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.List;

/**
 * Created by hao on 2017/4/25.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {

    private List<Note> mNotes;
    private MainActivity mActivity;


    public Adapter(MainActivity activity) {
        this.mActivity = activity;
    }


    @Override public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.recycler_view_item, parent, false);

        return new NoteViewHolder(view);
    }


    @Override public void onBindViewHolder(final NoteViewHolder holder, int position) {

        holder.mCheckBox.setChecked(mNotes.get(position).getIsChecked());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Note note = mNotes.get(holder.getAdapterPosition());
                note.setIsChecked(b);
                mActivity.updateNote(note);
            }
        });

        if (holder.mCheckBox.isChecked()){
            holder.mEditText.setText(mNotes.get(position).getContent());
            holder.mEditText.setTextColor(mActivity.getResources().getColor(R.color.gery));
        }else {
            holder.mEditText.setText(mNotes.get(position).getContent());
        }
        holder.mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Note note = mNotes.get(holder.getAdapterPosition());
                note.setContent(charSequence.toString());
                mActivity.updateNote(note);
            }


            @Override public void afterTextChanged(Editable editable) {

            }
        });

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                new AlertDialog.Builder(mActivity)
                    .setMessage("真的不要这条记录了吗~？")
                    .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int i) {
                            Note note = mNotes.get(holder.getAdapterPosition());
                            mActivity.deleteNote(note);
                            mActivity.updateNotes();
                        }
                    })
                    .setNegativeButton("留下来吧~", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
            }
        });

    }


    @Override public int getItemCount() {
        return mNotes.size();
    }


    public void setItem(List<Note> notes) {
        this.mNotes = notes;
        notifyDataSetChanged();
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckBox;
        private EditText mEditText;
        private ImageView mImageView;


        public NoteViewHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            mEditText = (EditText) itemView.findViewById(R.id.content);
            mImageView = (ImageView) itemView.findViewById(R.id.delete_button);

        }
    }
}
