package io.github.haohaozaici.muxitest;

import java.util.Date;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hao on 2017/4/25.
 */

@Entity
public class Note {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String content;
    private boolean isChecked;
    private Date mDate;
    @Generated(hash = 255961430)
    public Note(Long id, @NotNull String content, boolean isChecked, Date mDate) {
        this.id = id;
        this.content = content;
        this.isChecked = isChecked;
        this.mDate = mDate;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public boolean getIsChecked() {
        return this.isChecked;
    }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    public Date getMDate() {
        return this.mDate;
    }
    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }



}
