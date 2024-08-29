package com.nutricon.smartcare.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutricon.smartcare.R;
import com.nutricon.smartcare.activities.SingleInfoActivity;
import com.nutricon.smartcare.ads.InterstitialManager;
import com.nutricon.smartcare.data.Info;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private final Context context;
    private final Activity activity;
    private final List<Info> infoList;
    InterstitialManager interstitialManager = new InterstitialManager();

    public InfoAdapter(Activity activity, Context context, List<Info> infoList) {
        this.activity = activity;
        this.context = context;
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(infoList.get(position));
        interstitialManager.loadInterstitial(activity);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SingleInfoActivity.class);
            intent.putExtra("title", infoList.get(position).getTitle());
            intent.putExtra("image", infoList.get(position).getImage());
            intent.putExtra("description", infoList.get(position).getDescription());
            intent.putExtra("id", infoList.get(position).getId());
            activity.startActivity(intent);
            interstitialManager.showInterstitial(activity);

        });
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView status;
        TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);
        }

        void setData(Info info){
            Picasso.get().load(info.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(image);
            title.setText(info.getTitle());
        }
    }
}
