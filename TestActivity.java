package com.entrolabs.firebaseauth.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.entrolabs.firebaseauth.R;

public class TestActivity extends AppCompatActivity {
    private GridLayout numberGrid;
    private Spinner ruleSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        numberGrid = findViewById(R.id.number_grid);
        ruleSpinner = findViewById(R.id.rule_spinner);

        // Set up the spinner with rules
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                TestActivity.this,
                R.array.rules_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ruleSpinner.setAdapter(adapter);

        // Initialize grid
        initializeGrid();

        // Set up spinner listener
        ruleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyRule(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

    }
    private void initializeGrid() {
        numberGrid.removeAllViews();
        numberGrid.setRowCount(10);
        numberGrid.setColumnCount(10);

        for (int i = 1; i <= 100; i++) {
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(i));
            textView.setTextSize(16);
            textView.setPadding(8, 8, 8, 8);
            textView.setGravity(Gravity.CENTER);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec((i - 1) / 10);
            params.columnSpec = GridLayout.spec((i - 1) % 10);
            params.setMargins(4, 4, 4, 4);
            textView.setLayoutParams(params);
            numberGrid.addView(textView);
        }
    }

    private void applyRule(int rulePosition) {
        for (int i = 0; i < numberGrid.getChildCount(); i++) {
            TextView textView = (TextView) numberGrid.getChildAt(i);
            int number = Integer.parseInt(textView.getText().toString());

            switch (rulePosition) {
                case 0:
                    // Default
                    textView.setBackgroundColor(Color.TRANSPARENT);
                    break;
                case 1:
                    // Highlight multiples of 3
                    if (number % 3 == 0) {
                        textView.setBackgroundColor(Color.YELLOW);
                    } else {
                        textView.setBackgroundColor(Color.TRANSPARENT);
                    }
                    break;
                case 2:
                    // Highlight prime numbers
                    if (isPrime(number)) {
                        textView.setBackgroundColor(Color.GREEN);
                    } else {
                        textView.setBackgroundColor(Color.TRANSPARENT);
                    }
                    break;
            }
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}