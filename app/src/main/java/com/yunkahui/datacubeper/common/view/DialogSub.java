package com.yunkahui.datacubeper.common.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.yunkahui.datacubeper.R;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by clint on 2017/12/21.
 */

public class DialogSub {
    private Context context;
    private ArrayList<String> optItem1 = new ArrayList<>();
    private ArrayList<ArrayList<String>> optItem2 = new ArrayList<>();

    public DialogSub(Context context) {
        this.context = context;
    }

    public void showLocalCityPicker(final CityPickerListener l) {
        if (optItem1.size() == 0) {
            StringBuilder builder = new StringBuilder();
            try {
                AssetManager assetManager = context.getAssets();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("provice_city.json")));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }

                ArrayList<String> tmpList1 = new ArrayList<>();
                ArrayList<ArrayList<String>> tmpList2 = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(builder.toString());
                for (int index = 0; index < jsonArray.length(); index++) {
                    tmpList1.add(jsonArray.optJSONObject(index).optString("provinceName"));

                    ArrayList<String> tmpList3 = new ArrayList<>();
                    JSONArray cityList = jsonArray.optJSONObject(index).optJSONArray("citys");
                    for (int cIndex = 0; cIndex < cityList.length(); cIndex++) {
                        tmpList3.add(cityList.optJSONObject(cIndex).optString("citysName"));
                    }
                    tmpList2.add(tmpList3);
                }
                optItem1.addAll(tmpList1);
                optItem2.addAll(tmpList2);
                OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                    @Override public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        if (l != null) {
                            l.picker(optItem1.get(options1), optItem2.get(options1).get(options2));
                        }
                    }
                }).setTitleText("城市选择").setDividerColor(Color.BLACK).setTextColorCenter(Color.BLACK).setSelectOptions(15, 0)
                        .build();
                optionsPickerView.setPicker(optItem1, optItem2);
                optionsPickerView.show();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {

            OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
                @Override public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    if (l != null) {
                        l.picker(optItem1.get(options1), optItem2.get(options1).get(options2));
                    }
                }
            }).setTitleText("城市选择").setDividerColor(Color.BLACK).setTextColorCenter(Color.BLACK).setSelectOptions(15, 0)
                    .build();
            optionsPickerView.setPicker(optItem1, optItem2);
            optionsPickerView.show();
        }
    }


    public OptionsPickerView createCityPicker(final CityPickerListener l) {
        requestData();

        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (l != null) {
                    l.picker(optItem1.get(options1), optItem2.get(options1).get(options2));
                }
            }
        }).setTitleText("城市选择").setDividerColor(Color.BLACK).setTextColorCenter(Color.BLACK).setSelectOptions(4, 3)
                .build();
        optionsPickerView.setPicker(optItem1, optItem2);
        return optionsPickerView;
    }

    private void requestData() {
        StringBuilder builder = new StringBuilder();

        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("simple_cities_pro_city.json")));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            ArrayList<String> tmpList1 = new ArrayList<>();
            ArrayList<ArrayList<String>> tmpList2 = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(builder.toString());
            for (int index = 0; index < jsonArray.length(); index++) {
                tmpList1.add(jsonArray.optJSONObject(index).optString("name"));

                ArrayList<String> tmpList3 = new ArrayList<>();
                JSONArray cityList = jsonArray.optJSONObject(index).optJSONArray("cityList");
                for (int cIndex = 0; cIndex < cityList.length(); cIndex++) {
                    tmpList3.add(cityList.optJSONObject(cIndex).optString("name"));
                }
                tmpList2.add(tmpList3);
            }
            optItem1.addAll(tmpList1);
            optItem2.addAll(tmpList2);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface CityPickerListener {
        void picker(String province, String city);
    }
}
