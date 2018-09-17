package mesaric.marko.fer.hr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class represents the implementation of a main activity
 * which offers the division functionality and ability to navigate
 * to layout used for composing emails.
 *
 * @author Marko Mesarić
 */
public class LifecycleActivity extends AppCompatActivity {

    public static final String EXTRA_RESULT = "rezultat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        EditText inputFirst =  findViewById(R.id.input_first);
        EditText inputSecond = findViewById(R.id.input_second);
        TextView labelResult =  findViewById(R.id.input_result);
        Button btnCalculate =  findViewById(R.id.btn_calculate);

        btnCalculate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String first = inputFirst.getText().toString();
                String second = inputSecond.getText().toString();

                int firstNumber = 0;
                int secondNumber = 0;

                try {
                    firstNumber = Integer.parseInt(first);
                } catch (NumberFormatException e) {}

                try {
                    secondNumber = Integer.parseInt(second);
                } catch (NumberFormatException e) {}

                if (secondNumber!=0) {
                    labelResult.setText(String.valueOf(firstNumber/secondNumber));
                }
                else {
                    labelResult.setText("Nedozvoljena operacija");
                }
            }
        });

        Button btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifecycleActivity.this, ShowActivity.class);

                Bundle extras = new Bundle();
                extras.putString(EXTRA_RESULT, labelResult.getText().toString());

                intent.putExtras(extras);
                startActivityForResult(intent, 140);
            }
        });

        Button composeEmailButton = findViewById(R.id.btn_compose);
        composeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifecycleActivity.this, ComposeMailActivity.class);
                startActivity(intent);
            }
        });


        Log.d("Lifecycle", "Pozvao oncreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "Pozvao onpause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "Pozvao onresume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 140 && resultCode == RESULT_OK && data!=null) {
            Toast.makeText(this, data.getExtras().getString(EXTRA_RESULT), Toast.LENGTH_SHORT).show();
        }
    }
}
