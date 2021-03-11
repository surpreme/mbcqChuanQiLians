package com.mbcq.accountlibrary.activity.house;

import java.util.List;

public class AuthorityMenuBean {

    /**
     * title : APP_运营
     * authCode : 140000
     * spread : false
     * icon :
     * list : [{"title":"发货管理","url":"/order","authCode":"140001","list":[{"authCode":"141001","title":"受理开单","url":"/order/AcceptBillingActivity","list":[]},{"authCode":"141002","title":"运单记录","url":"/order/WaybillRecordActivity","list":[]},{"authCode":"141003","title":"上门提货","url":"/order","list":[]},{"authCode":"141004","title":"改单申请","url":"/order","list":[]},{"authCode":"141005","title":"运单标签补打","url":"/order","list":[]},{"authCode":"141006","title":"发货库存","url":"/order","list":[]},{"authCode":"141007","title":"沿途配载","url":"/order","list":[]},{"authCode":"141008","title":"外转","url":"/order","list":[]}]},{"title":"到货管理","url":"/order","authCode":"140002","list":[]}]
     */

    private String title;
    private String authCode;
    private boolean spread;
    private String icon;
    private List<ListBeanX> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<ListBeanX> getList() {
        return list;
    }

    public void setList(List<ListBeanX> list) {
        this.list = list;
    }

    public static class ListBeanX {
        /**
         * title : 发货管理
         * url : /order
         * authCode : 140001
         * list : [{"authCode":"141001","title":"受理开单","url":"/order/AcceptBillingActivity","list":[]},{"authCode":"141002","title":"运单记录","url":"/order/WaybillRecordActivity","list":[]},{"authCode":"141003","title":"上门提货","url":"/order","list":[]},{"authCode":"141004","title":"改单申请","url":"/order","list":[]},{"authCode":"141005","title":"运单标签补打","url":"/order","list":[]},{"authCode":"141006","title":"发货库存","url":"/order","list":[]},{"authCode":"141007","title":"沿途配载","url":"/order","list":[]},{"authCode":"141008","title":"外转","url":"/order","list":[]}]
         */

        private String title;
        private String url;
        private String authCode;
        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * authCode : 141001
             * title : 受理开单
             * url : /order/AcceptBillingActivity
             * list : []
             */

            private String authCode;
            private String title;
            private String url;
            private List<?> list;

            public String getAuthCode() {
                return authCode;
            }

            public void setAuthCode(String authCode) {
                this.authCode = authCode;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }
        }
    }
}
