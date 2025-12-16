package com.sopt.dive.data.type

sealed class CommonError : Exception() {
    class Timeout : CommonError()
    class Unknown : CommonError()
    class Undefined(val serverMessage: String) : CommonError()
}

sealed class AuthError : Exception() {
    class TokenExpired : AuthError()
    class IdDuplicated : AuthError()
    class InvalidCredentials : AuthError()
}
