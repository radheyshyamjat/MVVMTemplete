package com.task.demo.ui.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.task.demo.data.model.MasterWrapper
import com.task.demo.data.repositries.AuthenticateRepository
import org.json.JSONObject

class AuthenticateViewModel(@NonNull application: Application) :
    AndroidViewModel(application) {

    private var authenticateRepository: AuthenticateRepository =
        AuthenticateRepository.getInstance()

//    fun signUp(userDetail: UserDto): LiveData<MasterWrapper> {
//        return authenticateRepository.signUp(userDetail)
//    }

    fun checkEmail(email: String): LiveData<JSONObject> {
        return authenticateRepository.checkEmail(email)
    }
}