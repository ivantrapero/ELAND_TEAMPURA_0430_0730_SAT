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
import com.trapero.cchoice.models.ChatMessage;
import com.trapero.cchoice.viewmodels.MessageViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView; // Import BottomNavigationView
//import com.trapero.cchoice.databinding.ActivityMessageBinding; //Removed this import

import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private MessageViewModel viewModel;
    private BottomNavigationView bottomNavigationView; // Declare BottomNavigationView

    //private ActivityMessageBinding binding; //Removed binding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // binding = ActivityMessageBinding.inflate(getLayoutInflater()); //Removed binding
        // setContentView(binding.getRoot()); //Removed binding

        recyclerView = findViewById(R.id.recyclerMessages);
        if (recyclerView == null) {
            Log.e(TAG, "RecyclerView is null. Check your activity_message.xml layout.");
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        viewModel.getMessages().observe(this, messages -> {
            if (messages != null) {
                adapter.setMessages(messages);
            } else {
                Log.w(TAG, "Messages LiveData is null or empty.");
            }
        });

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView); //Initialize findViewById
        if(bottomNavigationView != null) { //Added null check
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    Intent profileIntent = new Intent(this, DashboardActivity.class);
                    startActivity(profileIntent);
                    return true;
                } else if (itemId == R.id.navigation_basket) {
                    Intent productListIntent = new Intent(this, ProductListActivity.class);
                    startActivity(productListIntent);
                    return true;
                } else if (itemId == R.id.navigation_chat) {
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    Intent profileIntent = new Intent(this, ProfileActivity.class);
                    startActivity(profileIntent);
                    return true;
                }
                return false;
            });
        } else {
            Log.e(TAG, "BottomNavigationView is null. Check your activity_message.xml layout.");
        }
    }

    private static class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
        private List<ChatMessage> messageList;

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
            holder.senderTextView.setText(message.getSender());
            holder.messageTextView.setText(message.getText());
            holder.timestampTextView.setText(message.getTimestamp());
            holder.iconImageView.setImageResource(message.getIcon());

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), MessageDetailActivity.class);
                intent.putExtra("sender", message.getSender());
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
