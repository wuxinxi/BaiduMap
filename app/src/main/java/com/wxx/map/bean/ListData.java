package com.wxx.map.bean;

import java.util.List;

/**
 * 作者：Tangren_ on 2017/4/1 10:49.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class ListData {

    /**
     * status : 0
     * total : 19
     * size : 10
     * contents : [{"status":0,"uid":2042622617,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490937860,"city":"深圳市","location":[113.947305,22.551528],"address":"广东省深圳市南山区高新中二道10号","title":"深圳软件园-一期","coord_type":3,"direction":"东南","type":0,"distance":323,"weight":0},{"status":0,"uid":2042625867,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490937964,"city":"深圳市","location":[113.951248,22.549033],"address":"广东省深圳市南山区科发路10号","title":"维用科技大厦","coord_type":3,"direction":"西北","type":0,"distance":169,"weight":0},{"status":0,"uid":2042625939,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490937974,"city":"深圳市","location":[113.950412,22.549392],"address":"广东省深圳市南山区科智西路1号","title":"园西工业区","coord_type":3,"direction":"西北","type":0,"distance":82,"weight":0},{"status":0,"uid":2042626014,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490937984,"city":"深圳市","location":[113.950192,22.549296],"address":"广东省深圳市南山区科智西路1号","title":"园西工业区","coord_type":3,"direction":"北","type":0,"distance":84,"weight":0},{"status":0,"uid":2042626092,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490937994,"city":"深圳市","location":[113.950111,22.548746],"address":"广东省深圳市南山区科发路9-1","title":"科苑西小区","coord_type":3,"direction":"北","type":0,"distance":143,"weight":0},{"status":0,"uid":2042626185,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490938006,"city":"深圳市","location":[113.949986,22.548362],"address":"广东省深圳市南山区科发路9-5","title":"科苑居","coord_type":3,"direction":"北","type":0,"distance":185,"weight":0},{"status":0,"uid":2042626244,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490938014,"city":"深圳市","location":[113.950754,22.548278],"address":"广东省深圳市南山区科发路7-19-2","title":"科苑西小区","coord_type":3,"direction":"北","type":0,"distance":209,"weight":0},{"status":0,"uid":2042626289,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490938022,"city":"深圳市","location":[113.950767,22.54802],"address":"广东省深圳市南山区科发路7号","title":"科苑西小区","coord_type":3,"direction":"北","type":0,"distance":236,"weight":0},{"status":0,"uid":2042626462,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490938043,"city":"深圳市","location":[113.951823,22.548065],"address":"广东省深圳市南山区科苑路3号","title":"科苑西小区","coord_type":3,"direction":"西北","type":0,"distance":287,"weight":0},{"status":0,"uid":2042626558,"province":"广东省","geotable_id":165733,"district":"南山区","create_time":1490938055,"city":"深圳市","location":[113.95136,22.548666],"address":"广东省深圳市南山区科发路5-2","title":"科苑西小区","coord_type":3,"direction":"西北","type":0,"distance":205,"weight":0}]
     */

    private int status;
    private int total;
    private int size;
    private List<ContentsBean> contents;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "ListData{" +
                "status=" + status +
                ", total=" + total +
                ", size=" + size +
                ", contents=" + contents +
                '}';
    }

    public static class ContentsBean {
        /**
         * status : 0
         * uid : 2042622617
         * province : 广东省
         * geotable_id : 165733
         * district : 南山区
         * create_time : 1490937860
         * city : 深圳市
         * location : [113.947305,22.551528]
         * address : 广东省深圳市南山区高新中二道10号
         * title : 深圳软件园-一期
         * coord_type : 3
         * direction : 东南
         * type : 0
         * distance : 323
         * weight : 0
         */

        private int status;
        private int uid;
        private String province;
        private int geotable_id;
        private String district;
        private int create_time;
        private String city;
        private String address;
        private String title;
        private int coord_type;
        private String direction;
        private int type;
        private int distance;
        private int weight;
        private List<Double> location;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getGeotable_id() {
            return geotable_id;
        }

        public void setGeotable_id(int geotable_id) {
            this.geotable_id = geotable_id;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCoord_type() {
            return coord_type;
        }

        public void setCoord_type(int coord_type) {
            this.coord_type = coord_type;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public List<Double> getLocation() {
            return location;
        }

        public void setLocation(List<Double> location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return "ContentsBean{" +
                    "status=" + status +
                    ", uid=" + uid +
                    ", province='" + province + '\'' +
                    ", geotable_id=" + geotable_id +
                    ", district='" + district + '\'' +
                    ", create_time=" + create_time +
                    ", city='" + city + '\'' +
                    ", address='" + address + '\'' +
                    ", title='" + title + '\'' +
                    ", coord_type=" + coord_type +
                    ", direction='" + direction + '\'' +
                    ", type=" + type +
                    ", distance=" + distance +
                    ", weight=" + weight +
                    ", location=" + location +
                    '}';
        }
    }
}
