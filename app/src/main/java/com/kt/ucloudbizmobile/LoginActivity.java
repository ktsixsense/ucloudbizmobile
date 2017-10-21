package com.kt.ucloudbizmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    int index = 0;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progressLogin);

        final TextView txtApikey = (TextView) findViewById(R.id.txt_login_apikey);
        final TextView txtSecretkey = (TextView) findViewById(R.id.txt_login_secretkey);
        final ApiParser parser = new ApiParser();
        final AQuery aq = new AQuery(this);

        final Button btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Key 인증
                if(txtApikey.getText().toString().equals("")) Toast.makeText(getApplicationContext(), "API Key를 입력해주세요.", Toast.LENGTH_LONG).show();
                else if(txtSecretkey.getText().toString().equals("")) Toast.makeText(getApplicationContext(), "Secret Key를 입력해주세요.", Toast.LENGTH_LONG).show();
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.INVISIBLE);

                    final String cloudstack1 = ApiGenerator.apiGenerator(txtApikey.getText().toString(), txtSecretkey.getText().toString(), "listAccounts", false, "all");

                    aq.ajax(cloudstack1, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String json, AjaxStatus status) {
                            if(status.getCode() == 401) {
                                Toast.makeText(getApplicationContext(), "Key가 잘못되었습니다. 확인해주세요.", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                btnLogin.setVisibility(View.VISIBLE);
                            }
                            if (json != null) {
                                //successful ajax call, show status code and json content
                                Document doc = parser.getDocument(json);
                                index = parser.getNumberOfResponse("account", doc);
                                if(index > 0) {
                                    // Main으로 Intent 넘김
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Key가 잘못되었습니다. 확인해주세요.", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    btnLogin.setVisibility(View.VISIBLE);
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }



            }
        });
    }
}
