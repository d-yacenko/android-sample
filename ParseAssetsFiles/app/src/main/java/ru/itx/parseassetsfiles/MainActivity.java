package ru.itx.parseassetsfiles;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String FASSET="test3.xml";
    TextView tvRaw,tvXml,tvAssets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvRaw=(TextView)findViewById(R.id.tvRaw);
        tvXml=(TextView)findViewById(R.id.tvXml);
        tvAssets=(TextView)findViewById(R.id.tvAssets);
        try {
            tvRaw.setText(loadFromRaw());
            tvXml.setText(loadFromXml());
            tvAssets.setText(loadFromAssets());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String loadFromXml() throws IOException, XmlPullParserException {
        XmlPullParser parser=getResources().getXml(R.xml.test1);
        return loadXml(parser);

    }
    private String loadFromRaw() throws XmlPullParserException, IOException {
        InputStream is=getResources().openRawResource(R.raw.test2);
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(is,null);
        return loadXml(parser);
    }
    private String loadFromAssets() throws XmlPullParserException, IOException {
        InputStream is=getResources().getAssets().open(FASSET);
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(is,null);
        return loadXml(parser);
    }

    private String loadXml(XmlPullParser parser) throws XmlPullParserException, IOException {
        String tmp="";
        while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
            switch (parser.getEventType()) {
                // начало документа
                case XmlPullParser.START_DOCUMENT:
                    Log.d(LOG_TAG, "START_DOCUMENT");
                    break;
                // начало тэга
                case XmlPullParser.START_TAG:
                    Log.d(LOG_TAG, "START_TAG: name = " + parser.getName()
                            + ", depth = " + parser.getDepth() + ", attrCount = "
                            + parser.getAttributeCount());
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        tmp = tmp + parser.getAttributeName(i) + " = "
                                + parser.getAttributeValue(i) + ", ";
                    }
                    if (!TextUtils.isEmpty(tmp))
                        Log.d(LOG_TAG, "Attributes: " + tmp);
                    break;
                // конец тэга
                case XmlPullParser.END_TAG:
                    Log.d(LOG_TAG, "END_TAG: name = " + parser.getName());
                    break;
                // содержимое тэга
                case XmlPullParser.TEXT:
                    Log.d(LOG_TAG, "text = " + parser.getText());
                    tmp+=parser.getText();
                    if(parser.getName()!=null && parser.getName().equals("name")) tmp+="\n";
                    break;
                default:
                    break;
            }
            // следующий элемент
            parser.next();
        }
        Log.d(LOG_TAG, "END_DOCUMENT");
        return tmp;
    }



}
