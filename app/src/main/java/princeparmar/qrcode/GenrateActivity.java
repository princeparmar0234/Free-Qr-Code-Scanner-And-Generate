package princeparmar.qrcode;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;

public class GenrateActivity extends AppCompatActivity {


    ImageView image;
    Button download;
    TextView genrateTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genrate);

        getSupportActionBar().hide();
        Intent intent=getIntent();
        String genrateData=intent.getStringExtra("genrate");

        image=findViewById(R.id.genrateImage);
        download=findViewById(R.id.download);

        ActivityCompat.requestPermissions(GenrateActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(GenrateActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        genrateTextview=findViewById(R.id.genrateText);
        if (genrateData!=null){
            MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
            BitMatrix bitMatrix= null;
            try {
                bitMatrix = multiFormatWriter.encode(genrateData, BarcodeFormat.QR_CODE,200,200);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
        }else
        {
            genrateTextview.setText("Please Try Again....");
        }


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               saveGallery();
            }
        });
    }

    private void permission(){
        Dexter.withContext(GenrateActivity.this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                saveGallery();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(GenrateActivity.this, "Please Accept Permission..", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    private void saveGallery(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        FileOutputStream outputStream = null;
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File dir = new File(path.getAbsolutePath() + "/QRCode");
        dir.mkdirs();

        String filename = String.format("%d.png",System.currentTimeMillis());
        File outFile = new File(dir,filename);
        try{
            outputStream = new FileOutputStream(outFile);
        }catch (Exception e){
           // Toast.makeText(GenrateActivity.this, "AB.."+e, Toast.LENGTH_SHORT).show();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        try{
            outputStream.flush();
        }catch (Exception e){
            //Toast.makeText(GenrateActivity.this, "BC.."+e, Toast.LENGTH_SHORT).show();
        }
        try{
            outputStream.close();
            Toast.makeText(GenrateActivity.this, "Save Image :-"+outFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            //Toast.makeText(GenrateActivity.this, "CD.."+e, Toast.LENGTH_SHORT).show();
        }
    }

}