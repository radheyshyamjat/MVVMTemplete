package com.task.demo.data.model


class ErrorModelDTO(var code:Int?,var message:String?) {

    override fun toString(): String {
        return "ErrorModelDTO{" +
                "code=" + code +
                ", message='" + message + '\''.toString() +
                '}'.toString()
    }
}
