package princeparmar.qrcode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RetriveAdapter extends RecyclerView.Adapter<RetriveAdapter.viewHolder>{
    Context context;
    List<RetriveModel> retriveModels;

    DBHealper dbHealper;
    public RetriveAdapter(Context context, List<RetriveModel> retriveModels) {
        this.context = context;
        this.retriveModels = retriveModels;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.sample_layout,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        RetriveModel model=retriveModels.get(position);
        holder.type.setText(model.getType());
        holder.mainData.setText(model.getMainData());
        String data=model.mainData.toString();
        holder.mainImage.setImageURI(Uri.parse(model.getImagePath()));
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip=ClipData.newPlainText("Demo",data);
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(context, "Copied..", Toast.LENGTH_SHORT).show();
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, data);
                sendIntent.setType("text/plain");
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sendIntent);
            }
        });
        dbHealper=new DBHealper(context);
    }

    @Override
    public int getItemCount() {
        return retriveModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView mainImage;
        TextView mainData,type;
        AppCompatButton copy,share;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            mainImage=itemView.findViewById(R.id.mainImage);
            mainData=itemView.findViewById(R.id.mainData);
            type=itemView.findViewById(R.id.type);
            copy=itemView.findViewById(R.id.open);
            share=itemView.findViewById(R.id.share);
        }
    }


}
