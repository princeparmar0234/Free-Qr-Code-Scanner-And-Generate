package princeparmar.qrcode;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<RetriveModel> retriveModels;
    RetriveAdapter adapter;
    DBHealper dbHealper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.recyclerView);


        dbHealper=new DBHealper(this);
        retriveModels=new ArrayList<>(dbHealper.getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new RetriveAdapter(getApplicationContext(),retriveModels);
        recyclerView.setAdapter(adapter);

    }
}