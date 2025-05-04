package com.trapero.cchoice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trapero.cchoice.R;
import com.trapero.cchoice.models.ChatMessage; // Import ChatMessage
import com.trapero.cchoice.viewmodels.MessageViewModel;

import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private MessageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.recyclerMessages);
        if (recyclerView == null) {
            Log.e(TAG, "RecyclerView is null. Check your activity_message.xml layout.");
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        // Observe the messages, which are now of type List<ChatMessage>
        viewModel.getMessages().observe(this, messages -> {
            if (messages != null) {
                adapter.setMessages(messages);
            } else {
                Log.w(TAG, "Messages LiveData is null or empty.");
            }

        });
    }

    private static class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
        // Use List<ChatMessage>
        private List<ChatMessage> messageList;

        // Use List<ChatMessage>
        public void setMessages(List<ChatMessage> messages) {
            this.messageList = messages;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_layout, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            if (messageList == null || messageList.isEmpty()) {
                Log.w(TAG, "messageList is null or empty in onBindViewHolder.");
                return;
            }
            ChatMessage message = messageList.get(position);
            holder.senderTextView.setText(message.getSender()); // Use getSender() from ChatMessage
            holder.messageTextView.setText(message.getText());     // Use getText() from ChatMessage
            holder.timestampTextView.setText(message.getTimestamp()); // Use getTimestamp()
            holder.iconImageView.setImageResource(message.getIcon());    // Use getIcon()

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), MessageDetailActivity.class);
                intent.putExtra("sender", message.getSender()); // Pass sender
                v.getContext().startActivity(intent);
            });
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

            MessageViewHolder(@NonNull View itemView) {
                super(itemView);
                senderTextView = itemView.findViewById(R.id.textSender);
                messageTextView = itemView.findViewById(R.id.textMessage);
                timestampTextView = itemView.findViewById(R.id.textTimestamp);
                iconImageView = itemView.findViewById(R.id.imageIcon);

                if (senderTextView == null || messageTextView == null || timestampTextView == null || iconImageView == null) {
                    Log.e(TAG, "One or more TextViews or ImageView is null in MessageViewHolder. Check item_message_layout.xml");
                }
            }
        }
    }
}