package com.mbcq.commonlibrary;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.mbcq.baselibrary.util.log.LogUtils;

public class GaoDeLoacationUtils {
    //切换城市
 /*   private LatLng getLatlon(String cityName) {
        //构造 GeocodeSearch 对象，并设置监听。
        GeocodeSearch geocodeSearch = new GeocodeSearch(getActivity());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            //------------------------坐标转地址/坐标转地址的监听回调-----------------------
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i == 1000) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null &&
                            geocodeResult.getGeocodeAddressList().size() > 0) {
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                        String adcode = geocodeAddress.getAdcode();//区域编码

                        LogUtils.e("地理编码", geocodeAddress.getAdcode() + "");
                        LogUtils.e("纬度latitude", latitude + "");
                        LogUtils.e("经度longititude", longititude + "");

                        LatLng lng = new LatLng(latitude, longititude);
                        return lng;
                    } else {
                      LogUtils.e("地理编码转换异常");
                    }
                }
            }
        });

        GeocodeQuery geocodeQuery = new GeocodeQuery(cityName.trim(), "30000");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }*/

}
