package com.mbcq.commonlibrary

import com.mbcq.commonlibrary.db.WebAreaDbInfo

interface WebsDbInterface {
    fun isNull()
    fun isSuccess(list: MutableList<WebAreaDbInfo>)

}