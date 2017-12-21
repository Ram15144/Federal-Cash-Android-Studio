package course.examples.Services.KeyService;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import static course.examples.Services.KeyService.KeyGeneratorImpl.updateStatus;

public class Status extends Activity {
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.status);
        txt = (TextView) findViewById(R.id.textView);

    }

    @Override
    protected void onResume() {

        super.onResume();
        txt.setText(updateStatus);

    }

    @Override
    protected void onDestroy() {
        txt.setText("destroyed");
        super.onDestroy();
    }

}
