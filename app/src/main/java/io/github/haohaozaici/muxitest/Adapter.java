package io.github.haohaozaici.muxitest;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Date;
import java.util.List;

/**
 * Created by hao on 2017/4/25.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {

    private List<Note> mNotes;
    private MainActivity mActivity;

    private boolean onBind;


    public Adapter(MainActivity activity) {
        this.mActivity = activity;
    }


    @Override public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.recycler_view_item, parent, false);

        return new NoteViewHolder(view);
    }


    @Override public void onBindViewHolder(final NoteViewHolder holder, int position) {

        onBind = true;
        holder.mCheckBox.setChecked(mNotes.get(position).getIsChecked());
        onBind = false;

        holder.mEditText.setText(mNotes.get(position).getContent());
        if (holder.mCheckBox.isChecked()) {
            holder.mEditText.setTextColor(mActivity.getResources().getColor(R.color.gery));
        } else {
            holder.mEditText.setTextColor(mActivity.getResources().getColor(R.color.black));
        }

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
        private RelativeLayout mRelativeLayout;


        public NoteViewHolder(View itemView) {
            super(itemView);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_relative_layout);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            mEditText = (EditText) itemView.findViewById(R.id.content);
            mImageView = (ImageView) itemView.findViewById(R.id.delete_button);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Note note = mNotes.get(getAdapterPosition());
                    note.setIsChecked(b);
                    note.setMDate(new Date());
                    mActivity.updateNote(note);
                    if (!onBind) {
                        mActivity.updateNotes();
                    }
                }
            });

            mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE) {
                        mRelativeLayout.requestFocus();
                        mRelativeLayout.requestFocusFromTouch();
                        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
                        Snackbar.make(mEditText, "已保存", Snackbar.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            });
            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }


                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Note note = mNotes.get(getAdapterPosition());
                    note.setContent(charSequence.toString());
                    mActivity.updateNote(note);
                }


                @Override public void afterTextChanged(Editable editable) {

                }
            });

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    new AlertDialog.Builder(mActivity)
                        .setMessage("真的不要这条记录了吗~？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) {
                                Note note = mNotes.get(getAdapterPosition());
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
    }
}
