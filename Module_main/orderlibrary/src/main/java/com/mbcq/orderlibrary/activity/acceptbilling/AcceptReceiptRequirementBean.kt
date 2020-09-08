package com.mbcq.orderlibrary.activity.acceptbilling

/**
 * kotlin bean
 */
data class AcceptReceiptRequirementBean(
        var id: String = "",
        var type: String = "",
        var typestr: String = "",
        var companyid: String = "",
        var typecode: String = "",
        var tdescribe: String = "",
        var recorddate: String = "",
        var typedes: String = ""
) {
    /**
     * id : 97
     * type : 14
     * typestr : 回单要求
     * companyid : 2001
     * typecode : 5
     * tdescribe : 2份回单
     * recorddate : 2018-12-06T17:06:42
     * typedes : 对运单表(tyd)backqty
     */

}