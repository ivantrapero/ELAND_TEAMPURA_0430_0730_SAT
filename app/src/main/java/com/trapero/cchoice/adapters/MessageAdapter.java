package com.trapero.cchoice.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trapero.cchoice.R;
import com.trapero.cchoice.models.ChatMessage; // Import ChatMessage
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<ChatMessage> messageList; // Use ChatMessage

    public void setMessageList(List<ChatMessage> messageList) { // Use ChatMessage
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_layout, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (messageList == null || messageList.isEmpty()) {
            return;
        }
        ChatMessage message = messageList.get(position);
        holder.senderTextView.setText(message.getSender());
        holder.messageTextView.setText(message.getText());
        holder.timestampTextView.setText(message.getTimestamp());
        holder.iconImageView.setImageResource(message.getIcon());
    }

    @Override
    public int getItemCount() {
        return messageList != null ? messageList.size() : 0;
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView senderTextView;
        TextView messageTextView;
        TextView timestampTextView;
        ImageView iconImageView;

        MessageViewHolder(View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.textSender);
            messageTextView = itemView.findViewById(R.id.textMessage);
            timestampTextView = itemView.findViewById(R.id.textTimestamp);
            iconImageView = itemView.findViewById(R.id.imageIcon);
        }
    }
}

