package com.task.demo.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.demo.ui.viewmodel.AuthenticateViewModel
import com.task.demo.R

class SlaveUserFragment : Fragment(R.layout.fragment_slave_user) {

    private lateinit var authenticateViewModel: AuthenticateViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authenticateViewModel = ViewModelProvider(this).get(AuthenticateViewModel::class.java)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
//        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        getUsers()
    }

    private fun getUsers() {
//        authenticateViewModel.getSlaveUser()
//            .observe(viewLifecycleOwner, Observer<SlaveUserWrapper> { response ->
//                if (response.code == BaseAPIHelper.RESULT_SUCCESS && response.list != null) {
//                    if (response.list!!.isEmpty()) {
//                        text_no_user.visibility = View.VISIBLE
//                        image_no_user.visibility = View.VISIBLE
//                        recyclerView.visibility = View.GONE
//                    } else {
//                        text_no_user.visibility = View.GONE
//                        image_no_user.visibility = View.GONE
//                        recyclerView.visibility = View.VISIBLE
//                    }
//                    userList.clear()
//                    userList.addAll(response.list!!)
//                    adapter.notifyDataSetChanged()
//                } else toastShort(response.message)
//
//            })
    }

//    private fun loadFragment(fragment: Fragment) {
//        if (activity is HomePageActivity) {
//            (activity as HomePageActivity).loadFragment(fragment)
//        }
//    }
}
