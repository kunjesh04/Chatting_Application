package com.example.chatapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.ItemContainerRecentConvosBinding;
import com.example.chatapp.listeners.ConversationListener;
import com.example.chatapp.models.ChatMessage;
import com.example.chatapp.models.User;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversationViewHolder> {

    private final List<ChatMessage> chatMessages;
    private final ConversationListener conversationListener;
    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversationListener conversationListener) {
        this.chatMessages = chatMessages;
        this.conversationListener = conversationListener;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(
                ItemContainerRecentConvosBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder{

        ItemContainerRecentConvosBinding binding;

        ConversationViewHolder(ItemContainerRecentConvosBinding itemContainerRecentConvosBinding){
            super(itemContainerRecentConvosBinding.getRoot());
            binding = itemContainerRecentConvosBinding;
        }

        void setData(ChatMessage chatMessage){
            binding.imageProfile.setImageBitmap(getConversationImage(chatMessage.convoImage));
            binding.textName.setText(chatMessage.convoname);
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v -> {
                User user = new User();
                user.id = chatMessage.convoId;
                user.name = chatMessage.convoname;
                user.image = chatMessage.convoImage;
                conversationListener.onConversationClicked(user);
            });
        }

    }



    private Bitmap getConversationImage(String encodedImage){
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
