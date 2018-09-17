package mesaric.marko.fer.hr;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;

/**
 * This class represents the implementation of an activity used for
 * composing emails. Solution consists of validating user input and
 * in case of error, informs the user of wrong input. If input is
 * valid, user is asked which application to use when sending this
 * email.
 *
 * @author Marko Mesarić
 */
public class ComposeMailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_mail);

        EditText recipient = findViewById(R.id.email_recipient);
        EditText title = findViewById(R.id.email_title);
        EditText message = findViewById(R.id.message);
        Button sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipientsString = recipient.getText().toString().trim();
                String titleString = title.getText().toString().trim();
                String messageString = message.getText().toString();

                if (recipient == null || title == null || message == null || recipientsString.isEmpty() ||
                        titleString.isEmpty() || messageString.isEmpty()) {
                    Toast.makeText(ComposeMailActivity.this, getString(R.string.invalid_input), Toast.LENGTH_SHORT).show();
                } else {

                    Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(recipientsString);
                    if (!matcher.matches()) {
                        Toast.makeText(ComposeMailActivity.this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
                    } else {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" +
                                recipientsString + "?cc=ana@baotic.org;marcupic@gmail.com"));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, titleString);
                        emailIntent.putExtra(Intent.EXTRA_TEXT, messageString);




                        startActivity(Intent.createChooser(emailIntent, "Send email"));

                        finish();
                    }
                }
            }
        });
    }
}
