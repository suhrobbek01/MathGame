package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mathgame.databinding.ActivityPracticeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Practice extends AppCompatActivity {
    private ActivityPracticeBinding binding;
    private int result;
    private int number1;
    private int number2;
    private int i;
    private int counter = 1;
    private CountDownTimer countDownTimer;
    private int a = 0;
    private Map<String, List<String>> map;

    @SuppressLint({"SetTextI18n", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPracticeBinding.inflate(getLayoutInflater());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());
        loadMap();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String practice = bundle.getString("practice");

        binding.back.setOnClickListener(view -> {
            finish();
            countDownTimer.cancel();
        });
        loadMaths(practice);
        binding.checkingTv.setText("");
        binding.countCoin.setText("0");
        binding.countQuestions.setText(counter + "/20");
        timer(practice);

        binding.next.setOnClickListener(view -> {
            checking(practice);
        });
    }

    private void randomize() {
        Random random = new Random();
        number1 = random.nextInt(100);
        number2 = random.nextInt(100);
        i = random.nextInt(4);
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    private void loadMaths(String practice) {
        Set<String> strings = map.keySet();

        a = 0;
        randomize();
        binding.answer.setText("");
        if (practice.equals("plus")) {
            binding.number11.setText("" + number1);
            binding.number12.setText("" + number2);
            binding.practice.setText("+");
            result = number1 + number2;
        } else if (practice.equals("minus")) {
            if (number1 > number2) {
                binding.number11.setText(number1 + "");
                binding.number12.setText(number2 + "");
                result = number1 - number2;
            } else {
                binding.number11.setText(number2 + "");
                binding.number12.setText(number1 + "");
                result = number2 - number1;
            }
            binding.practice.setText("-");
        } else if (practice.equals("multiplication")) {
            binding.number11.setText(number1 + "");
            binding.number12.setText(number2 + "");
            binding.practice.setText("*");
            result = number1 * number2;
        } else if (practice.equals("division")) {
            try {
                if (number2 != 0 && ((number1 / number2) == ((double) number1 / number2))) {
                    binding.number11.setText(number1 + "");
                    binding.number12.setText(number2 + "");
                    result = number1 / number2;
                } else if (number1 != 0 && ((int) (number2 / number1) == ((double) number2 / number1))) {
                    binding.number12.setText(number1 + "");
                    binding.number11.setText(number2 + "");
                    result = number2 / number1;
                } else {
                    loadMaths(practice);
                }
                binding.practice.setText("/");
            } catch (Exception e) {
                binding.number11.setText(20 + "");
                binding.number12.setText(2 + "");
                result = number1 / number2;
            }
        } else if (practice.equals("random")) {
            if (i == 0) {
                binding.number11.setText(number1 + "");
                binding.number12.setText(number2 + "");
                binding.practice.setText("+");
                result = number1 + number2;
            } else if (i == 1) {
                if (number1 > number2) {
                    binding.number11.setText(number1 + "");
                    binding.number12.setText(number2 + "");
                    result = number1 - number2;
                } else {
                    binding.number11.setText(number2 + "");
                    binding.number12.setText(number1 + "");
                    result = number2 - number1;
                }
                binding.practice.setText("-");
            } else if (i == 2) {
                binding.number11.setText(number1 + "");
                binding.number12.setText(number2 + "");
                binding.practice.setText("*");
                result = number1 * number2;
            } else if (i == 3) {
                checkingDivision_and_loading(number1, number2, practice);
            }
        }
    }

    private void timer(String practice) {
        countDownTimer = new CountDownTimer(15000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                String ms = (TimeUnit.MILLISECONDS.toMinutes(l) + ":" + (TimeUnit.MILLISECONDS.toSeconds(l)));
                binding.clockTv.setText(ms + "");
            }

            @SuppressLint({"SetTextI18n", "WrongConstant"})
            @Override
            public void onFinish() {
                String s = binding.answer.getText().toString();
                if (s.equals("")) {
                    Toast.makeText(Practice.this, "Vaqtingiz tugadi", Toast.LENGTH_SHORT).show();
                    if (counter >= 20) {
                        binding.checkingTv.setText("Misollar tugadi");
                    } else {
                        loadMaths(practice);
                        countDownTimer.cancel();
                        timer(practice);
                        counter++;
                        binding.countQuestions.setText(counter + "/20");
                    }
                } else {
                    checking(practice);
                }
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    private Boolean checking(String practice) {
        boolean b = false;
        String answer = binding.answer.getText().toString().trim();

        String s = String.valueOf(result);
        if (!answer.equals("")) {
            if (isNumeric(answer)) {
                a = 1;
                if (answer.equals(s)) {
                    String s1 = binding.countCoin.getText().toString();
                    int i = Integer.parseInt(s1);
                    binding.countCoin.setText(String.valueOf(i + 5));
                    Toast.makeText(Practice.this, "To`g`ri topdingiz", Toast.LENGTH_SHORT).show();
                    b = true;
                } else {
                    Toast.makeText(Practice.this, "Noto`g`ri topdingiz", Toast.LENGTH_SHORT).show();
                    b = false;
                }
                if (counter >= 20) {
                    binding.checkingTv.setText("Misollar tugadi");
                } else {
                    loadMaths(practice);
                    countDownTimer.cancel();
                    timer(practice);
                    counter++;
                    binding.countQuestions.setText(counter + "/20");
                }
            } else {
                Toast.makeText(Practice.this, "Javobni to`g`ri shaklda kiriting", Toast.LENGTH_SHORT).show();
                b = false;
            }
        } else {
            a = 0;
            Toast.makeText(Practice.this, "Javobingizni kiriting", Toast.LENGTH_SHORT).show();
        }

        return b;
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean checkingDivision_and_loading(int number1, int number2, String practice) {
        if (number2 != 0 && (number1 / number2 == (double) number1 / number2)) {
            binding.number11.setText(number1 + "");
            binding.number12.setText(number2 + "");
            result = number1 / number2;
            binding.practice.setText("/");
            return true;
        } else if (number1 != 0 && (number2 / number1 == (double) number2 / number1)) {
            binding.number12.setText(number1 + "");
            binding.number11.setText(number2 + "");
            result = number2 / number1;
            binding.practice.setText("/");
            return true;
        } else {
            loadMaths(practice);
            return false;
        }
    }

    private void loadMap() {
        map = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        list1.add("Haq yo‘linda kim senga bir harf o‘qitmish ranj ila, Aylamak bo‘lmas ado, oning haqin yuz ganch ila.");
        list1.add("Ilmni kim vositayi joh etar, O`zini-yu xalqni gumroh etar.");
        list1.add("Ey Navoiy, o’yin kulgi naqadar diltortar bo’lmasin, odob va hayo yanada yaxshi, xushdir.");
        map.put("Alisher Navoiy", list1);
        List<String> list2 = new ArrayList<>();
        list2.add("Ilm o`rganish har bir musulmon uchun farzdir.");
        list2.add("Beshikdan qabrgacha ilm izla.");
        list2.add("Avvalo olim bo`l! Loaqal ta`lim oluvchi yoki tinglovchi, ilm-u ulamolarni sevuvchi bo`lgin.");
        map.put("Hadisdan", list1);
        List<String> list3 = new ArrayList<>();
        list3.add("Ilm olishning erta-kechi yo'q.");
        list3.add("Ilmi yo'qning ko'zi yumuq.");
        list3.add("Ilmsiz bir yashar, \n" +
                "Ilmli ming yashar.");
        map.put("Maqol", list1);

    }
}