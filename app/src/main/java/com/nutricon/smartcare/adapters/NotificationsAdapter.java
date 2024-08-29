package com.nutricon.smartcare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutricon.smartcare.R;
import com.nutricon.smartcare.data.Notification;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private final List<Notification> notificationList;

    public NotificationsAdapter(Context context, List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()
                ).inflate(R.layout.item_notification, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Notification notification = this.notificationList.get(position);
        holder.textTitle.setText(notification.getTitle());
        holder.textStatus.setText(setRead(notification.getStatus()));
        holder.textDuration.setText(notification.getDate());

        Picasso.get().load(notification.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imagePreview);
    }

    public int getItemCount() {
        return this.notificationList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePreview;
        TextView textTitle;
        TextView textStatus;
        TextView textDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePreview = itemView.findViewById(R.id.image);
            textTitle = itemView.findViewById(R.id.title);
            textStatus = itemView.findViewById(R.id.status);
            textDuration = itemView.findViewById(R.id.date);
        }
    }

    private String  setRead(boolean bool) {
        if(bool) {
            return "Unread";
        } else {
            return "";
        }
    }
}
