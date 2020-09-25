package com.mbcq.vehicleslibrary.activity.alllocalagent.fixlocalgentshortfeederhouse;

import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean;
import com.mbcq.vehicleslibrary.fragment.localagentshortfeeder.LocalGentByCarBean;

import java.util.List;

public class AddOrderFixedLocalGentByCarHouseBean extends LocalGentByCarBean {
    private List<LocalGentShortFeederHouseBean > waybillAgentDetLst;
    private String commonStr;

    public List<LocalGentShortFeederHouseBean> getWaybillAgentDetLst() {
        return waybillAgentDetLst;
    }

    public void setWaybillAgentDetLst(List<LocalGentShortFeederHouseBean> waybillAgentDetLst) {
        this.waybillAgentDetLst = waybillAgentDetLst;
    }

    public String getCommonStr() {
        return commonStr;
    }

    public void setCommonStr(String commonStr) {
        this.commonStr = commonStr;
    }
}
