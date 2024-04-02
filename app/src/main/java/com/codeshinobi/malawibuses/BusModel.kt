package com.codeshinobi.malawibuses

@kotlinx.serialization.Serializable
data class Bus(
    val id:Int = 0,
    val created_at:String = "",
    val company_name:String = "",
    val company_id:String = ""
)