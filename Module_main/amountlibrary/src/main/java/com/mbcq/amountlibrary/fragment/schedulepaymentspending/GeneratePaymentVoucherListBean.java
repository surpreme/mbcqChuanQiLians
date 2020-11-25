package com.mbcq.amountlibrary.fragment.schedulepaymentspending;

import java.io.Serializable;
import java.util.List;

public class GeneratePaymentVoucherListBean implements Serializable {
    private List<SchedulePaymentsPendingBean> list;

    public List<SchedulePaymentsPendingBean> getList() {
        return list;
    }

    public void setList(List<SchedulePaymentsPendingBean> list) {
        this.list = list;
    }
}
