package io.github.krtkush.lineartimeproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutThree = (TextView) findViewById(R.id.para_three);

        aboutThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGithub = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/krtkush/LinearTimer"));
                startActivity(goToGithub);
            }
        });
    }
}
