package com.samsung.myitschool.testsimplexml;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText name,number,date;
    TextView out;
    Phonebook phonebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.name);
        number=(EditText)findViewById(R.id.number);
        date=(EditText)findViewById(R.id.date);
        out=(TextView)findViewById(R.id.out);
        phonebook=new Phonebook();
    }
    public void add(View V){
        String in_name=name.getText().toString();
        Date in_date= null;
        try {
            in_date = DateFormat.getDateInstance(DateFormat.SHORT).parse(date.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(this,"неверный формат даты",Toast.LENGTH_LONG).show();
        }

        long in_number=Long.parseLong(number.getText().toString());

        Contact contact=new Contact(in_name,in_date,in_number);
        phonebook.contacts.add(contact);
        out.setText(phonebook.toString());
    }

    public void save(View V){
        String myDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(myDir,"SimpleXmlSample.xml");
        if (file.exists()) file.delete();
        Serializer serializer = new Persister();
        try {
            serializer.write(phonebook, file);
        } catch (Exception e) {
            Toast.makeText(this, "Не удалось сохранить", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Сохранено", Toast.LENGTH_LONG).show();
    }

    public void load(View V){
        String myDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(myDir,"SimpleXmlSample.xml");
        if (!file.exists()){
            Toast.makeText(this,"Нет файла для загрузки",Toast.LENGTH_LONG).show();
            return;
        }
        Serializer serializer = new Persister();
        try {
            phonebook=serializer.read(Phonebook.class,file);
        } catch (Exception e) {
            Toast.makeText(this, "Не удалось загрузить", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(this,"Загружено",Toast.LENGTH_LONG);
        out.setText(phonebook.toString());
    }

    public void clear(View V){
        phonebook=new Phonebook();
        out.setText(phonebook.toString());
    }
}
