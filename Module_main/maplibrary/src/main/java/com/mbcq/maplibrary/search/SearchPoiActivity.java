package com.mbcq.maplibrary.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.google.gson.Gson;
import com.mbcq.baselibrary.gson.GsonUtils;
import com.mbcq.baselibrary.view.SingleClick;
import com.mbcq.maplibrary.LocationTestActivity;
import com.mbcq.maplibrary.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchPoiActivity extends AppCompatActivity implements TextWatcher,
        Inputtips.InputtipsListener, AdapterView.OnItemClickListener, View.OnTouchListener, View.OnClickListener, PoiSearch.OnPoiSearchListener {
    private AutoCompleteTextView mKeywordText;
    private ListView resultList;
    private List<Tip> mCurrentTipList;
    private SearchResultAdapter resultAdapter;
    private ProgressBar loadingBar;
    private TextView tvMsg;
    private Poi selectedPoi;
    private String city = "北京市";
    private int pointType = 0;
    private final int RESULT_CODE = 5543;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search_poi);
        findViews();
        resultList.setOnItemClickListener(this);
        resultList.setOnTouchListener(this);
        tvMsg.setVisibility(View.GONE);
        mKeywordText.addTextChangedListener(this);
        mKeywordText.requestFocus();
//        Bundle bundle = getIntent().getExtras();
//        pointType = bundle.getInt("pointType", -1);
    }


    private void findViews() {
        mKeywordText = (AutoCompleteTextView) findViewById(R.id.search_input);
        resultList = (ListView) findViewById(R.id.resultList);
        loadingBar = (ProgressBar) findViewById(R.id.search_loading);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        findViewById(R.id.back_iv).setOnClickListener(new SingleClick() {
            @Override
            protected void onSingleClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            {
                if (tvMsg.getVisibility() == View.VISIBLE) {
                    tvMsg.setVisibility(View.GONE);
                }
                String newText = s.toString().trim();
                if (!TextUtils.isEmpty(newText)) {
                    setLoadingVisible(true);
                    InputtipsQuery inputquery = new InputtipsQuery(newText, city);
                    Inputtips inputTips = new Inputtips(getApplicationContext(), inputquery);
                    inputTips.setInputtipsListener(this);
                    inputTips.requestInputtipsAsyn();
                } else {
                    resultList.setVisibility(View.GONE);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void setLoadingVisible(boolean isVisible) {
        if (isVisible) {
            loadingBar.setVisibility(View.VISIBLE);
        } else {
            loadingBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击提示后再次进行搜索，获取POI出入口信息
        if (mCurrentTipList != null) {
            Tip tip = (Tip) parent.getItemAtPosition(position);
            selectedPoi = new Poi(tip.getName(), new LatLng(tip.getPoint().getLatitude(), tip.getPoint().getLongitude()), tip.getPoiID());
            if (!TextUtils.isEmpty(selectedPoi.getPoiId())) {
                PoiSearch.Query query = new PoiSearch.Query(selectedPoi.getName(), "", city);
                query.setDistanceSort(false);
                query.requireSubPois(true);
                PoiSearch poiSearch = new PoiSearch(getApplicationContext(), query);
                poiSearch.setOnPoiSearchListener(this);
                poiSearch.searchPOIIdAsyn(selectedPoi.getPoiId());
            }
        }
    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        setLoadingVisible(false);
        try {
            if (rCode == 1000) {
                mCurrentTipList = new ArrayList<Tip>();
                for (Tip tip : tipList) {
                    if (null == tip.getPoint()) {
                        continue;
                    }
                    mCurrentTipList.add(tip);
                }

                if (null == mCurrentTipList || mCurrentTipList.isEmpty()) {
                    tvMsg.setText("抱歉，没有搜索到结果，请换个关键词试试");
                    tvMsg.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                } else {
                    resultList.setVisibility(View.VISIBLE);
                    resultAdapter = new SearchResultAdapter(getApplicationContext(), mCurrentTipList);
                    resultList.setAdapter(resultAdapter);
                    resultAdapter.notifyDataSetChanged();
                }
            } else {
                tvMsg.setText("出错了，请稍后重试");
                tvMsg.setVisibility(View.VISIBLE);
            }
        } catch (Throwable e) {
            tvMsg.setText("出错了，请稍后重试");
            tvMsg.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int errorCode) {
        try {
            LatLng latLng = null;
            int code = 0;
            if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
                if (poiItem == null) {
                    return;
                }
                LatLonPoint exitP = poiItem.getExit();
                LatLonPoint enterP = poiItem.getEnter();
                if (pointType == 0) {
                    code = 100;
                    if (exitP != null) {
                        latLng = new LatLng(exitP.getLatitude(), exitP.getLongitude());
                    } else {
                        if (enterP != null) {
                            latLng = new LatLng(enterP.getLatitude(), enterP.getLongitude());
                        }
                    }
                }
                if (pointType == 1) {
                    code = 200;
                    if (enterP != null) {
                        latLng = new LatLng(enterP.getLatitude(), enterP.getLongitude());
                    }
                }
            }
            Poi poi;
            if (latLng != null) {
                poi = new Poi(selectedPoi.getName(), latLng, selectedPoi.getPoiId());
            } else {
                poi = selectedPoi;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", poi.getName());
            jsonObject.put("latitude", poi.getCoordinate().latitude);
            jsonObject.put("longitude", poi.getCoordinate().longitude);
            Intent intent = new Intent(this, LocationTestActivity.class);
            intent.putExtra("poi", GsonUtils.toPrettyFormat(jsonObject));
            setResult(RESULT_CODE, intent);
            finish();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
