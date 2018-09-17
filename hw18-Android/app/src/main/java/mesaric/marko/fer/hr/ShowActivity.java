package mesaric.marko.fer.hr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        String result = getIntent().getExtras().getString(LifecycleActivity.EXTRA_RESULT);

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        final TextView inputResult = findViewById(R.id.text_receive);
        inputResult.setText(result);

        Button btnSendBack = findViewById(R.id.btn_send);
        btnSendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle extras = new Bundle();
                extras.putString(LifecycleActivity.EXTRA_RESULT, inputResult.getText().toString());
                intent.putExtras(extras);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
