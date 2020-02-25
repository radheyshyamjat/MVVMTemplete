package com.task.demo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.demo.data.model.MessagesDto
import com.task.demo.ui.adapter.ChatMessagesAdapter.ChatMessagesViewHolder
import com.task.demo.R

class ChatMessagesAdapter(private val userId: Int, private val messages: List<MessagesDto>) :
    RecyclerView.Adapter<ChatMessagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessagesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatMessagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatMessagesViewHolder, position: Int) {
        val message = messages[position]
        val previous: MessagesDto? = if (position != 0) messages[position - 1] else null
        holder.bind(message, previous)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class ChatMessagesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var tvMessageRecv: TextView = itemView.findViewById(R.id.tv_chat_message_receive)
        private var tvMessageSend: TextView = itemView.findViewById(R.id.tv_chat_message_send)
        private var tvSendTime: TextView = itemView.findViewById(R.id.tv_chat_message_send_time)
        private var tvReceiveTime: TextView =
            itemView.findViewById(R.id.tv_chat_message_receive_time)
        private var tvTimestamp: TextView = itemView.findViewById(R.id.tv_chat_timestamp)

        fun bind(message: MessagesDto, previous: MessagesDto?) {
//            if (previous != null) {
//                val previousTime = DateUtils.getDayFromDateForChat(previous.created_at)
//                val currentTime = DateUtils.getDayFromDateForChat(message.created_at)
//                if (previousTime == currentTime) tvTimestamp.visibility = View.GONE else {
//                    tvTimestamp.visibility = View.VISIBLE
//                    tvTimestamp.text = currentTime
//                }
//            } else {
//                tvTimestamp.visibility = View.VISIBLE
//                tvTimestamp.text = DateUtils.getDayFromDateForChat(message.created_at)
//            }
//            if (message.sender != userId) {
//                tvMessageRecv.visibility = View.VISIBLE
//                tvReceiveTime.visibility = View.VISIBLE
//                tvMessageSend.visibility = View.GONE
//                tvSendTime.visibility = View.GONE
//                tvMessageRecv.text = message.text
//                tvReceiveTime.text = DateUtils.getTimeFromDateForChat(message.created_at)
//            } else if (message.sender == userId) {
//                tvMessageSend.visibility = View.VISIBLE
//                tvSendTime.visibility = View.VISIBLE
//                tvMessageRecv.visibility = View.GONE
//                tvReceiveTime.visibility = View.GONE
//                tvSendTime.text = DateUtils.getTimeFromDateForChat(message.created_at)
//                tvMessageSend.text = message.text
//            }
        }
    }
}