package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button calc;
    EditText num;
    TextView result;
    ArrayList<Integer> res = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calc = findViewById(R.id.calculate_button);
        num = findViewById(R.id.number_edit_text);
        result = findViewById(R.id.result_text);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = num.getText().toString();
                int number = Integer.parseInt(tmp);
                if (number % 2 == 0 || tmp == "") {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введіть непарне число!", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (isPrime(number)) {
                    String temp = number + " просте!";
                    result.setText(temp);
                } else {
                    ferma(number);
                    out();
                }
            }
        });
    }

    private void ferma(int num) {
        int s = (int)Math.sqrt(num);
        int y;
        double radical;
        for (int k = 0; k < num; k++) {
            y = (int)Math.pow(s + k, 2) - num;
            radical = Math.sqrt(y);
            if (radical % 1 == 0.0) {
                Integer mul1 = (int)(s + k + radical);
                Integer mul2 = (int)(s + k - radical);
                if (isPrime(mul1)) {
                    res.add(mul1);
                } else {
                    ferma(mul1);
                }
                if (isPrime(mul2)) {
                    res.add(mul2);
                } else {
                    ferma(mul2);
                }
                break;
            }
        }
    }

    private void out() {
         StringBuilder temp = new StringBuilder();
         for (int i = 0; i < res.size()-1; i++) {
             String tmp = res.get(i) + " * ";
             temp.append(tmp);
         }
         temp.append(res.get(res.size()-1));
         temp.append(" = ");
         temp.append(num.getText().toString());
         result.setText(temp);
         res.clear();
    }

    private boolean isPrime(int n) {
        if (n == 2) return true;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

}
