package com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating;

import java.util.List;

public class ArrivalTrunkDepartureScanOperatingByIdBean {

    /**
     * code : 0
     * msg :
     * data : [{"data":[{"id":172,"sendDate":"2021-01-05 11:11:29","vehicleState":2,"vehicleStateStr":"到货","contractNo":"GX1001-20210105-001","inoneVehicleFlag":"GX1001-20210105-001","vehicleInterval":"义乌后湖-汕头","vehicleNo":"浙G12370","Chauffer":"张凯","volumn":5,"weight":8,"yf":66,"accWz":0,"chaufferMb":"16276665366","webidCode":1001,"webidCodeStr":"义乌后湖","ewebidCode":1003,"ewebidCodeStr":"汕头","accNow":0,"accArrSum":0,"accBack":0,"accTansSum":0,"accDaiShou":0,"accYk":0,"ykRemake":null,"sendOpeMan":"义乌后湖","createMan":"义乌后湖","arrivedDate":"1900-01-01 00:00:00","hous":0,"accdfYk":null,"vusetypeStr":null,"peizai":"已完毕","isScan":0,"unloadingDate":"1900-01-01 00:00:00","qty":3,"weightJs":0}]},{"data":[{"id":445,"billno":"10010000115","shipper":"宋学宝","consignee":"非常慢","webidCode":1001,"webidCodeStr":"义乌后湖","ewebidCode":1003,"ewebidCodeStr":"汕头","Product":"家电,,","unLoadQty":0,"loadQty":0,"qty":0,"totalQty":3,"weight":8,"volumn":5,"inoneVehicleFlag":"GX1001-20210105-001"}]}]
     */

    private int code;
    private String msg;
    private List<DataBeanX> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            private int id = -1;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

        }
    }
}
