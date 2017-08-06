package com.example.h.learn.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.h.learn.GreenDaoHelper;
import com.example.h.learn.R;
import com.example.h.learn.bean.Note;
import com.example.h.learn.bean.NoteDao;

import java.util.List;

public class GreenDaoActivity extends AppCompatActivity {

    NoteDao noteDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        //初始化
        GreenDaoHelper.initDatabase();
        // 获取Dao对象
        noteDao = GreenDaoHelper.getDaoSession().getNoteDao();


        add();
        search();

    }

    private void search() {
        if (noteDao!=null){
            List<Note> notes = noteDao.loadAll();

            for (Note n:notes
                 ) {
                Log.d("flag--","search(GreenDaoActivity.java:37)-->>"+n.getId()+n.getName()+n.getNoteContent());
            }
        }
    }

    private void add() {
        if (noteDao!=null){

            Note mNote = new Note(null, "新笔记", "123455");
            noteDao.insert(mNote);
        }
    }


}
