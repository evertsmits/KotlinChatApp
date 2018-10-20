package com.egsdevelopment.smack.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.egsdevelopment.smack.Model.Message
import com.egsdevelopment.smack.R
import com.egsdevelopment.smack.Services.UserDataService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(val context: Context, val messages: ArrayList<Message> ) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindMessage(context, messages[position])
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val userimage = itemView?.findViewById(R.id.messageUserImage) as ImageView
        val timeStamp = itemView?.findViewById(R.id.timestampLabel) as TextView
        val userName = itemView?.findViewById(R.id.messageUserNameLabel) as TextView
        val messageBody = itemView?.findViewById(R.id.messageBodyLabel) as TextView

        fun bindMessage(context: Context, message: Message) {
            val resourceId = context.resources.getIdentifier(message.userAvatar, "drawable", context.packageName)
            userimage?.setImageResource(resourceId)
            userimage?.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor))
            userName?.text = message.userName
            timeStamp?.text =  returnDateString(message.timeStamp)
            messageBody?.text = message.message
        }


        fun returnDateString(isoString: String) : String {

            val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            isoFormatter.timeZone = TimeZone.getTimeZone("UTC")

            var convertedDate = Date()
            try {
                convertedDate = isoFormatter.parse(isoString)

            } catch(e: ParseException) {
                Log.d("PARSE", "Could not parse date" + e.localizedMessage)
            }

            val outDateString = SimpleDateFormat("E, h:mm a", Locale.getDefault())

            return outDateString.format(convertedDate)

        }

    }

}