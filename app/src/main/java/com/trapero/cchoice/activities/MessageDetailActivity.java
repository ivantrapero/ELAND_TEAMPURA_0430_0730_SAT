package com.trapero.cchoice.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trapero.cchoice.R;
import com.trapero.cchoice.models.ChatMessage;
import com.trapero.cchoice.viewmodels.MessageDetailViewModel;

import java.util.List;

public class MessageDetailActivity extends AppCompatActivity {

    private static final String TAG = "MessageDetailActivity";
    private MessageDetailViewModel viewModel;
    private MessageAdapter adapter; // Using the inner adapter
    private EditText editMessage;
    private ImageButton btnSend;
    private RecyclerView recyclerView;
    private String currentUserId; // Identifier for the current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        recyclerView = findViewById(R.id.recyclerChat);
        editMessage = findViewById(R.id.editMessage);
        btnSend = findViewById(R.id.btnSend);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(); // Initialize the inner adapter
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MessageDetailViewModel.class);

        currentUserId = getIntent().getStringExtra("sender"); // Assuming the sender's identifier is passed with key "sender"
        adapter.setCurrentUserId(currentUserId); // Set the currentUserId in the inner adapter
        Toast.makeText(this, "Sender: " + currentUserId, Toast.LENGTH_SHORT).show();

        viewModel.getMessages().observe(this, messages -> {
            adapter.submitList(messages);
            recyclerView.scrollToPosition(messages.size() - 1);
        });

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                viewModel.clearErrorMessage();
            }
        });

        btnSend.setOnClickListener(v -> {
            String text = editMessage.getText().toString().trim();
            if (!text.isEmpty()) {
                viewModel.sendMessage(text);
                editMessage.setText("");
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    // --- Inner Adapter for Displaying Messages ---
    private static class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
        private List<ChatMessage> messageList;
        private String currentUserId;

        public void submitList(List<ChatMessage> newList) {
            messageList = newList;
            notifyDataSetChanged();
        }

        public void setCurrentUserId(String userId) {
            currentUserId = userId;
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView;
            if (viewType == 1) { // Current user's message
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_right, parent, false);
            } else { // Other user's message
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_left, parent, false);
            }
            return new MessageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            ChatMessage message = messageList.get(position);
            holder.bind(message, currentUserId);
        }

        @Override
        public int getItemCount() {
            return messageList == null ? 0 : messageList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return messageList.get(position).isUser() ? 1 : 0;
        }

        static class MessageViewHolder extends RecyclerView.ViewHolder {
            TextView textMessage;

            MessageViewHolder(@NonNull View itemView) {
                super(itemView);
                textMessage = itemView.findViewById(R.id.textMessage);
            }

            void bind(ChatMessage message, String currentUserId) {
                textMessage.setText(message.getText());
            }
        }
    }
}