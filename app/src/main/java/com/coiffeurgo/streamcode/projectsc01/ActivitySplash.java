package com.coiffeurgo.streamcode.projectsc01;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.coiffeurgo.streamcode.projectsc01.utils.StatusVerification;

public class ActivitySplash extends AppCompatActivity {
    private boolean _hasNet = false;
    private StatusVerification _statusCheck = new StatusVerification();

    private TextView _vwStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final AlertDialog alertDialog = new AlertDialog.Builder(ActivitySplash.this).create();
        alertDialog.setTitle("Erro de Conexão!");
        alertDialog.setMessage("Por Favor, ative sua conexão com a internet.");

        _vwStatus = (TextView) findViewById(R.id.txtStatus);

        _vwStatus.setText("Verificando conexão...");
        _hasNet = _statusCheck.isOnline(ActivitySplash.this);
        if(!_hasNet){alertDialog.show();}
        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                _vwStatus.setText("Iniciando...");
                new Handler().postDelayed(new Runnable() {
                    /*
                     * Exibindo splash com um timer.
                     */
                    @Override
                    public void run() {
                        // Esse método será executado sempre que o timer acabar
                        // E inicia a activity principal
                        alertDialog.dismiss();
                        Intent i = new Intent(ActivitySplash.this, MainActivity.class);
                        startActivity(i);

                        // Fecha esta activity
                        finish();
                    }
                }, 2000);
            }
        }, 1000);
    }
}
