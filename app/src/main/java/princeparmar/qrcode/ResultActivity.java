package princeparmar.qrcode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {

    TextView type, date, data;
    ImageView mainImage;
    Button search, rate, share;

    DBHealper dbHealper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        type = findViewById(R.id.type);
        date = findViewById(R.id.date);
        data = findViewById(R.id.mainData);
        mainImage = findViewById(R.id.mainImage);

        search = findViewById(R.id.open);
        share = findViewById(R.id.share);
        rate = findViewById(R.id.rate);


        dbHealper=new DBHealper(ResultActivity.this);


        Intent intent = getIntent();
        String typeIntent = intent.getStringExtra("type");
        if (typeIntent.toString().equals("")){
            type.setText("type");
        }else{
            type.setText(typeIntent);
        }


        String mainDataIntent = intent.getStringExtra("mainData");
        if (mainDataIntent.equals("")){
            data.setText("Please Try Again..");
        }else {
            data.setText(mainDataIntent);
        }

        String imageIntent = intent.getStringExtra("image");
        if (imageIntent.equals("")){
            mainImage.setImageResource(R.drawable.ic_baseline_error_24);
        }else {
            mainImage.setImageURI(Uri.parse(imageIntent));
        }

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        date.setText(mydate);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip=ClipData.newPlainText("Demo",data.getText().toString());
                clipboardManager.setPrimaryClip(clip);

                Toast.makeText(ResultActivity.this, "Copied..", Toast.LENGTH_SHORT).show();

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, data.getText().toString());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


        boolean add=dbHealper.addDeatil(typeIntent,date.toString(),imageIntent,mainDataIntent);
        if (add){
            //Toast.makeText(this, "Add Pass", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
