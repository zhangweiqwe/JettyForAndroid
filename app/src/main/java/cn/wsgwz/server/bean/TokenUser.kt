package cn.wsgwz.server.bean

class TokenUser(val userId:String?, val validTime:Long) {
    override fun toString(): String {
        return "TokenUser(userId=$userId, validTime=$validTime)"
    }
}