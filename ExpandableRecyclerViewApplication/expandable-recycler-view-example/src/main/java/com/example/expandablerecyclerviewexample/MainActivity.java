package com.example.expandablerecyclerviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.expandable_recycler_view.expandable.ExpandableList;
import com.example.expandable_recycler_view.expandable.ExpandableSublist;
import com.example.expandablerecyclerviewexample.adapter.LetterWordAdapter;
import com.example.expandablerecyclerviewexample.items.Letter;
import com.example.expandablerecyclerviewexample.items.Word;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ArrayList list = new ArrayList();

        list.add(new Letter(Arrays.asList(new Word[]{new Word("Apple"), new Word("Application")}), "A"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Banana")}), "B"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Apple"), new Word("Application")}), "A"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Banana")}), "B"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Apple"), new Word("Application")}), "A"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Banana")}), "B"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Apple"), new Word("Application")}), "A"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Banana")}), "B"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Apple"), new Word("Application")}), "A"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Banana")}), "B"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Apple"), new Word("Application")}), "A"));
        list.add(new Letter(Arrays.asList(new Word[]{new Word("Banana")}), "B"));

        ExpandableList<Letter> letterList = new ExpandableList<>(list);

        letterList.setExpandOrCollapse(0);

        Log.d(MainActivity.class.getSimpleName(), letterList.toString());

        LetterWordAdapter adapter = new LetterWordAdapter(this, letterList);

        recyclerView.setAdapter(adapter);
    }

    private void logExpandableList(ExpandableList expandableList) {

    }
}
