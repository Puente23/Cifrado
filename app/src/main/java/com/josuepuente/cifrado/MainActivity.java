package com.josuepuente.cifrado;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et1=findViewById(R.id.file_name_edit_text);
        et2=findViewById(R.id.edittext1);
        key=findViewById(R.id.editTextTextPersonName);

    }
    public void grabar(View v) {
        String nomarchivo = et1.getText().toString();
        String contenido = et2.getText().toString();
        try {
            File file =new  File(getExternalFilesDir(null), nomarchivo);
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(file));
            osw.write(contenido);
            osw.flush();
            osw.close();
            Toast.makeText(this, "Los datos fueron grabados correctamente",
                    Toast.LENGTH_SHORT).show();
            et1.setText("");
            et2.setText("");
        } catch (IOException ioe) {
            Toast.makeText(this, "No se pudo grabar",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void recuperar(View v) {
        File file =new  File(getExternalFilesDir(null), et1.getText().toString());
        try {
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader archivo = new InputStreamReader(fIn);
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String todo = "";
            while (linea != null) {
                todo = todo + linea + " ";
                linea = br.readLine();
            }
            br.close();
            archivo.close();
            et2.setText(todo);
        } catch (IOException e) {
            Toast.makeText(this, "No se pudo leer",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void cifradoAES(View v) throws Exception {
        String keyString = key.getText().toString();
        String mensaje = et2.getText().toString();
        String cifrado= "AES";
        filechooser encripter= new filechooser();
        String encryptedMsg = encripter.encryptSMS(keyString,
                mensaje,cifrado);
       et2.setText(encryptedMsg);
    }
    public void desaes(View v)throws Exception{
        String keyString = key.getText().toString();
        byte[] mensaje = Base64.decode(et2.getText().toString().trim(),Base64.DEFAULT);
        String cifrado= "AES";
        filechooser dencripter= new filechooser();
        byte[] result = dencripter.decryptSMS(keyString, mensaje,cifrado);
        et2.setText(new String(result));
    }
    public void cifradoDES(View v) throws Exception {
        String keyString = key.getText().toString();
        String mensaje = et2.getText().toString();
        String cifrado= "DES";
        filechooser encripter= new filechooser();
        String encryptedMsg = encripter.encryptSMS(keyString,
                mensaje,cifrado);
        et2.setText(encryptedMsg);
    }
    public void desDes(View v) throws Exception {
        String keyString = key.getText().toString();

        byte[] mensaje = Base64.decode(et2.getText().toString().trim(),Base64.DEFAULT);
        String cifrado= "DES";
        filechooser dencripter= new filechooser();
        byte[] encryptedMsg = dencripter.decryptSMS(keyString,
                mensaje,cifrado);
        et2.setText(new String(encryptedMsg));

    }
}