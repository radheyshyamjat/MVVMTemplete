package com.task.demo.data.model


class WrapperMessages(var data: WrapperData?, var message: String, var code: Int) {
}

class WrapperData(
    var id: Int, var user: Int, var admin: Int,
    var created_at: String, var updated_at: String, var messages: List<MessagesDto>?
)

class MessagesDto(
    var id: Int,
    var sender: Int,
    var text: String,
    var created_at: String,
    var updated_at: String
) {
}
