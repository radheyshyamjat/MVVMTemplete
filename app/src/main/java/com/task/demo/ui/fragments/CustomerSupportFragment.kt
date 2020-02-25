package com.task.demo.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.demo.R
import com.task.demo.data.model.MessagesDto
import com.task.demo.ui.adapter.ChatMessagesAdapter
import com.task.demo.utils.debug
import kotlinx.android.synthetic.main.fragment_customer_support.*

class CustomerSupportFragment : Fragment(R.layout.fragment_customer_support) {

    //    private lateinit var customerSupportViewModel: CustomerSupportViewModel
    private lateinit var adapter: ChatMessagesAdapter
    private var messages: MutableList<MessagesDto> = ArrayList()
    private var userID: Int = 0
    private var senderId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debug("Customer support fragment")
//        senderId = App.getPreferences().getUserProfile().id!!
//        customerSupportViewModel = ViewModelProvider(this).get(CustomerSupportViewModel::class.java)

        adapter = ChatMessagesAdapter(1, messages)
        val manager = LinearLayoutManager(requireContext())
        rv_chat_messages.layoutManager = manager
        rv_chat_messages.adapter = adapter
//        initializeDummyMessage()
        getOldMessages()

    }

    private fun sendMessage(message: String) {
//        messages.add(
//            MessagesDto(
//                userID, senderId, message,
//                DateUtils.getFormattedDate(),
//                DateUtils.getFormattedDate()
//            )
//        )
//        adapter.notifyDataSetChanged()
//        rv_chat_messages.scrollToPosition(messages.size - 1)
//
//        customerSupportViewModel.sendMessage(userID, message)
//            .observe(viewLifecycleOwner, Observer<WrapperMessages> { info ->
//                Dialog.closeProgressMessage()
//                if (info.code == BaseAPIHelper.RESULT_SUCCESS_CREATED) {
//                    toastShort(info.message)
//                } else toastShort(info.message)
//            })
    }

    fun getOldMessages() {
//        customerSupportViewModel.getOldMessages()
//            .observe(viewLifecycleOwner, Observer<WrapperMessages> { info ->
//                Dialog.closeProgressMessage()
//                if (info.code == BaseAPIHelper.RESULT_SUCCESS && info.data != null) {
//                    userID = info.data!!.user
//                    if (info.data!!.messages != null) {
//                        messages.clear()
//                        messages.addAll(info.data!!.messages!!)
//                        if (messages.isNotEmpty()) {
//                            adapter.notifyDataSetChanged()
//                            rv_chat_messages.scrollToPosition(messages.size - 1)
//                        }
//                    }
//                } else toastShort(info.message)
//            })

    }
}
