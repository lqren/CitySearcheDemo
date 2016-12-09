package com.project.citysearchedemo.bean;

import java.util.List;

/**
 */
public class CitiesBean {

    /**
     * alifName : C
     * addressList : [{"id":37,"name":"潮州"}]
     */

    private List<DatasBean> datas;

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private String alifName;
        /**
         * id : 37
         * name : 潮州
         */

        private List<AddressListBean> addressList;

        public String getAlifName() {
            return alifName;
        }

        public void setAlifName(String alifName) {
            this.alifName = alifName;
        }

        public List<AddressListBean> getAddressList() {
            return addressList;
        }

        public void setAddressList(List<AddressListBean> addressList) {
            this.addressList = addressList;
        }

        public static class AddressListBean {
            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
