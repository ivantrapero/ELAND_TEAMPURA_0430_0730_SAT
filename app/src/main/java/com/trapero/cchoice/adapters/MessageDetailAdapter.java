package com.trapero.cchoice.adapters; // Changed package

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.trapero.cchoice.R; // Changed R import
import com.trapero.cchoice.models.ChatMessage; // Changed import

import java.util.ArrayList;
import java.util.List;

public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.ChatViewHolder> {

    private final List<ChatMessage> chatList = new ArrayList<>();

    public void submitList(List<ChatMessage> list) {
        chatList.clear();
        chatList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = (viewType == 0) ? R.layout.item_chat_left : R.layout.item_chat_right;
        return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.textMessage.setText(chatList.get(position).getText());
    }

    @Override
    public int getItemViewType(int position) {
        return chatList.get(position).isUser() ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
        }
    }
}