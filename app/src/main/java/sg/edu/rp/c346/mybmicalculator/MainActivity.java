package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvEnhance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvEnhance = findViewById(R.id.textViewEnhancement);

        final Calendar now = Calendar.getInstance();
        final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float fltWeight = Float.parseFloat(etWeight.getText().toString());
                float fltHeight = Float.parseFloat(etHeight.getText().toString());
                float calcBMI = fltWeight/(fltHeight*fltHeight);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putFloat("weight", fltWeight);
                prefEdit.putFloat("height", fltHeight);
                prefEdit.putString("date", datetime);
                prefEdit.putFloat("bmi", calcBMI);
                prefEdit.commit();

                etWeight.setText(null);
                etHeight.setText(null);
                tvDate.setText(getString(R.string.calculateDate) + datetime);
                tvBMI.setText(getString(R.string.calculateBMI) + calcBMI);

                if (calcBMI >= 25 && calcBMI <= 29.9) {
                    tvEnhance.setText("You are overweight");
                } else if (calcBMI >= 18.5 && calcBMI <= 24.9) {
                    tvEnhance.setText("Your BMI is normal");
                } else if (calcBMI < 18.5) {
                    tvEnhance.setText("You are underweight");
                } else {
                    tvEnhance.setText("You are obese");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etWeight.setText(null);
                etHeight.setText(null);
                tvDate.setText(getString(R.string.calculateDate));
                tvBMI.setText(getString(R.string.calculateBMI));
                tvEnhance.setText(null);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String displayWeight = String.valueOf(prefs.getFloat("weight", 0));
        String displayHeight = String.valueOf(prefs.getFloat("height", 0));
        etWeight.setText(null);
        etHeight.setText(null);

    }
}
