package cn.wsgwz.baselibrary.permission

enum class Permission(var permission:Array<String>?,var description:String) {
    CALENDAR(PermissionConst.CALENDAR,"读写日历"),
    CAMERA(PermissionConst.CAMERA,"相机"),
    CONTACTS(PermissionConst.CONTACTS,"读写联系人"),
    LOCATION(PermissionConst.LOCATION,"读位置信息"),
    MICROPHONE(PermissionConst.MICROPHONE,"使用麦克风"),
    PHONE(PermissionConst.PHONE,"读电话状态、打电话、读写电话记录"),
    SENSORS(PermissionConst.SENSORS,"传感器"),
    SMS(PermissionConst.SMS,"读写短信、收发短信"),
    STORAGE(PermissionConst.STORAGE,"读写存储卡"),
    INSTALL(PermissionConst.INSTALL,"安装应用")
}