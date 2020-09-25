package com.mbcq.vehicleslibrary.activity.allterminalagent.fixedterminalagentbycar;


import com.mbcq.vehicleslibrary.activity.allterminalagent.TerminalAgentByCarHouseBean;
import com.mbcq.vehicleslibrary.fragment.terminalagentbycar.TerminalAgentByCarBean;

import java.util.List;

public class AddOrderFixedTerminalAgentByCarBean extends TerminalAgentByCarBean {
    private List<TerminalAgentByCarHouseBean> waybillAgentDetLst;
    private String commonStr;

    public List<TerminalAgentByCarHouseBean> getWaybillAgentDetLst() {
        return waybillAgentDetLst;
    }

    public void setWaybillAgentDetLst(List<TerminalAgentByCarHouseBean> waybillAgentDetLst) {
        this.waybillAgentDetLst = waybillAgentDetLst;
    }

    public String getCommonStr() {
        return commonStr;
    }

    public void setCommonStr(String commonStr) {
        this.commonStr = commonStr;
    }
}
