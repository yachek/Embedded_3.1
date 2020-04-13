package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.Math;

import java.math.BigInteger;
import java.util.ArrayList;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button calc;
    EditText num;
    TextView result;
    ArrayList<BigInteger> res = new ArrayList<>();

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
                BigInteger number = new BigInteger(tmp);
                if (number.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введіть непарне число!", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (number.isProbablePrime(1) || number.equals(BigInteger.ONE)) {
                    String temp = number.longValue() + " просте!";
                    result.setText(temp);
                } else {
                    ferma(number);
                    out();
                }
            }
        });
    }

    private void ferma(BigInteger num) {
        BigInteger s = sqrtInt(num);
        BigInteger y;
        double radical;
        BigInteger k = BigInteger.ZERO;
        for (;; k = k.add(BigInteger.ONE)) {
            y = s.add(k).pow(2).subtract(num);
            radical = Math.sqrt(y.doubleValue());
            if (radical % 1 == 0.0) {
                BigInteger mul1 = s.add(k).add(BigInteger.valueOf((long)radical));
                BigInteger mul2 = s.add(k).subtract(BigInteger.valueOf((long)radical));
                if (mul1.isProbablePrime(1)) {
                    res.add(mul1);
                } else {
                    ferma(mul1);
                }
                if (mul2.isProbablePrime(1)) {
                    res.add(mul2);
                } else {
                    ferma(mul2);
                }
                break;
            }
            if (k.compareTo(num) > -1) break;
        }
    }

    private void out() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < res.size()-1; i++) {
            String tmp = res.get(i).toString() + " * ";
            temp.append(tmp);
        }
        temp.append(res.get(res.size()-1).toString());
        temp.append(" = ");
        temp.append(num.getText().toString());
        result.setText(temp);
        res.clear();
    }

    public static BigInteger sqrtInt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = n.shiftRight(1).add(new BigInteger("2"));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1); // (a+b) >> 1
            if (mid.multiply(mid).compareTo(n) > 0)
                b = mid.subtract(BigInteger.ONE);
            else
                a = mid.add(BigInteger.ONE);
        }
        return a.subtract(BigInteger.ONE);
    }

}
