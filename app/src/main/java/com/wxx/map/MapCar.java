package com.wxx.map;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Tangren_ on 2017/3/29 12:53.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class MapCar implements Parcelable {
    private double latitude;
    private double longitude;
    private int imgId;
    private String name;

    static List<MapCar> infos = new ArrayList<MapCar>();

    static {
        infos.add(new MapCar(22.5489710000, 113.9512500000, R.mipmap.ic_mb, "摩拜单车001"));
        infos.add(new MapCar(22.5460020000, 113.9410840000, R.mipmap.ic_mb, "摩拜单车002"));
        infos.add(new MapCar(22.5489510000, 113.9500220000, R.mipmap.ic_mb, "摩拜单车003"));
        infos.add(new MapCar(22.5493970000, 113.9504940000, R.mipmap.ic_mb, "摩拜单车004"));
        infos.add(new MapCar(22.5495730000, 113.9506060000, R.mipmap.ic_mb, "摩拜单车005"));
        infos.add(new MapCar(22.5496980000, 113.9502240000, R.mipmap.ic_mb, "摩拜单车006"));

        infos.add(new MapCar(22.5498730000,113.9497210000, R.mipmap.ic_mb, "摩拜单车007"));
        infos.add(new MapCar(22.5499650000,113.9485980000, R.mipmap.ic_mb, "摩拜单车008"));
        infos.add(new MapCar(22.5493560000,113.9499140000, R.mipmap.ic_mb, "摩拜单车009"));
        infos.add(new MapCar(22.5489590000,113.9514730000, R.mipmap.ic_mb, "摩拜单车0010"));
        infos.add(new MapCar(22.5486630000,113.9515450000, R.mipmap.ic_mb, "摩拜单车0011"));

        infos.add(new MapCar(22.5849200000,113.8857650000, R.mipmap.ic_mb, "摩拜单车0012"));
        infos.add(new MapCar(22.5835690000,113.8842200000, R.mipmap.ic_mb, "摩拜单车0013"));
        infos.add(new MapCar(22.5821450000,113.8861960000, R.mipmap.ic_mb, "摩拜单车0014"));
        infos.add(new MapCar(22.5817900000,113.8844630000, R.mipmap.ic_mb, "摩拜单车0015"));
        infos.add(new MapCar(22.5813580000,113.8839190000, R.mipmap.ic_mb, "摩拜单车0016"));

        infos.add(new MapCar(22.5810410000,113.8845160000, R.mipmap.ic_mb, "摩拜单车0017"));
        infos.add(new MapCar(22.5811000000,113.8851590000, R.mipmap.ic_mb, "摩拜单车0018"));
        infos.add(new MapCar(22.5807200000,113.8848890000, R.mipmap.ic_mb, "摩拜单车0019"));
        infos.add(new MapCar(22.5804360000,113.8845120000, R.mipmap.ic_mb, "摩拜单车0020"));
        infos.add(new MapCar(22.5803910000,113.8841260000, R.mipmap.ic_mb, "摩拜单车0021"));

        infos.add(new MapCar(22.5803650000,113.8820280000, R.mipmap.ic_mb, "摩拜单车0022"));
        infos.add(new MapCar(22.5796480000,113.8830430000, R.mipmap.ic_mb, "摩拜单车0023"));
        infos.add(new MapCar(22.5817590000,113.8852890000, R.mipmap.ic_mb, "摩拜单车0024"));
        infos.add(new MapCar(22.5832080000,113.8902880000, R.mipmap.ic_mb, "摩拜单车0025"));
        infos.add(new MapCar(22.5839090000,113.8899110000, R.mipmap.ic_mb, "摩拜单车0026"));
    }

    public MapCar(double latitude, double longitude, int imgId, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgId = imgId;
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public MapCar setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public MapCar setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public int getImgId() {
        return imgId;
    }

    public MapCar setImgId(int imgId) {
        this.imgId = imgId;
        return this;
    }

    public String getName() {
        return name;
    }

    public MapCar setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeInt(this.imgId);
        dest.writeString(this.name);
    }

    public MapCar() {
    }

    protected MapCar(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.imgId = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<MapCar> CREATOR = new Parcelable.Creator<MapCar>() {
        @Override
        public MapCar createFromParcel(Parcel source) {
            return new MapCar(source);
        }

        @Override
        public MapCar[] newArray(int size) {
            return new MapCar[size];
        }
    };

    @Override
    public String toString() {
        return "MapCar{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                '}';
    }
}
