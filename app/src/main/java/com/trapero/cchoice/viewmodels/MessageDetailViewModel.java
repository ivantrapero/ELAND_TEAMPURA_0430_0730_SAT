package com.trapero.cchoice.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trapero.cchoice.models.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.trapero.cchoice.R; // Import for the icon

public class MessageDetailViewModel extends ViewModel {

    private final MutableLiveData<List<ChatMessage>> messages = new MutableLiveData<>(new ArrayList<>());
    private String chatPartnerName;
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private boolean initialMessageSent = false;

    public void setChatPartnerName(String chatPartnerName) {
        this.chatPartnerName = chatPartnerName;
        if (!initialMessageSent) {
            List<ChatMessage> initial = new ArrayList<>();
            // Use a constant sender, or get it from somewhere.
            String initialSender = "System";
            String initialTimestamp = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());
            initial.add(new ChatMessage(initialSender, "Hi! This is " + chatPartnerName, initialTimestamp, R.drawable.ic_launcher_background, false));
            messages.setValue(initial);
            initialMessageSent = true;
        }
    }

    public LiveData<List<ChatMessage>> getMessages() {
        return messages;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void clearErrorMessage() {
        errorMessage.setValue(null);
    }

    public void sendMessage(String text) {
        if (chatPartnerName == null || chatPartnerName.isEmpty()) {
            errorMessage.setValue("Error: Chat partner name not set.");
            return;
        }

        // Use a constant sender, or get it from the current user's data.
        String userSender = "You";
        String userTimestamp = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());

        ChatMessage userMessage = new ChatMessage(userSender, text, userTimestamp, R.drawable.ic_launcher_background, true);
        List<ChatMessage> current = new ArrayList<>(messages.getValue());
        current.add(userMessage);
        messages.setValue(current);

        // Simulate receiving a reply after a delay
        new android.os.Handler().postDelayed(() -> {
            String replySender = chatPartnerName; // Use the chat partner's name
            String replyTimestamp = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());
            String reply = "Reply from " + chatPartnerName;
            ChatMessage receivedMessage = new ChatMessage(replySender, reply, replyTimestamp, R.drawable.ic_launcher_background, false);
            List<ChatMessage> updated = new ArrayList<>(messages.getValue());
            updated.add(receivedMessage);
            messages.setValue(updated);
        }, 1500);
    }
}
