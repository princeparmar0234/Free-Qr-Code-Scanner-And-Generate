package princeparmar.qrcode;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class MainActivity extends AppCompatActivity {

    Button scan,genreta,history,share;
    ImageView facebook,instagram,twitter;
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        scan=findViewById(R.id.scan);
        genreta=findViewById(R.id.genrate);
        history=findViewById(R.id.history);
        share=findViewById(R.id.share);

        facebook=findViewById(R.id.facebook);
        instagram=findViewById(R.id.instagram);
        twitter=findViewById(R.id.twitter);


        mAppUpdateManager= AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {

                if (result.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.FLEXIBLE,MainActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

      //  mAppUpdateManager.registerListener(installStateUpdatedListener);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "#", Toast.LENGTH_SHORT).show();
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "UserName:-Princeparmar0234", Toast.LENGTH_SHORT).show();
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "UserName:-PrinceParmar096", Toast.LENGTH_SHORT).show();
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        genreta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genrateDialoge();
            }
        });

    }

    public void genrateDialoge(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Enter Text.");

        final EditText input = new EditText(MainActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setCancelable(false);

        alertDialog.setView(input);

        alertDialog.setPositiveButton("Generate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String genrate=input.getText().toString();
                Intent intent=new Intent(MainActivity.this, GenrateActivity.class);
                intent.putExtra("genrate",genrate);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }


    public void permission(){
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                       scan();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "Please Accept Permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public void scan()
    {
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setOrientationLocked(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

    }

    @Override
    protected void onResume() {
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {

                if (result.updateAvailability()== UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.FLEXIBLE,MainActivity.this,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        super.onResume();
    }

    @Override
    protected void onStop() {
       // if (mAppUpdateManager!=null) mAppUpdateManager.registerListener(installStateUpdatedListener);
        super.onStop();
    }

    private InstallStateUpdatedListener installStateUpdatedListener=new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState state) {


            if (state.installStatus()== InstallStatus.DOWNLOADED){
                showCompleteListner();
            }
        }
    };

    private void showCompleteListner() {
        Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"New App Is Ready!",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppUpdateManager.completeUpdate();

            }
        });
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null && result.getContents()!=null){

            Intent intent=new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("type",result.getFormatName().toString());
            intent.putExtra("mainData",result.getContents().toString());
            intent.putExtra("image",result.getBarcodeImagePath().toString());
            startActivity(intent);

        }


        if (resultCode==RC_APP_UPDATE && resultCode!=RESULT_OK){
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {




        super.onBackPressed();

    }
}