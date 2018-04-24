package com.yunkahui.datacubeper.test.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.incrementadapter.IncrementAdapter;
import com.ct.incrementadapter.IncrementHolder;
import com.ct.incrementadapter.IndexPath;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.DataUtils;
import com.yunkahui.datacubeper.common.utils.LogUtils;
import com.yunkahui.datacubeper.common.utils.SizeUtil;
import com.yunkahui.datacubeper.common.view.chart.ChartAxis;
import com.yunkahui.datacubeper.common.view.chart.ChartCircleView;
import com.yunkahui.datacubeper.common.view.chart.ChartLinearView;
import com.yunkahui.datacubeper.common.view.chart.ChartScoreView;
import com.yunkahui.datacubeper.common.view.chart.ChartSingleView;
import com.yunkahui.datacubeper.common.view.chart.Score;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TestResultActivity extends AppCompatActivity implements IActivityStatusBar{

    private JSONObject dataJson;
    private IncrementAdapter incrementAdapter;
    private Integer[] itemNumbers = {1, 4, 1, 1, 1, 1, 3, 3, 3, 1, 3, 3, 1, 1};
    private Integer[] itemLayouts = {R.layout.test_result_recycler_item1, R.layout.test_result_recycler_item2,
            R.layout.test_result_recycler_item3, R.layout.test_result_recycler_item4, R.layout.test_result_recycler_item5,
            R.layout.test_result_recycler_item6, R.layout.test_result_recycler_item2, R.layout.test_result_recycler_item2,
            R.layout.test_result_recycler_item2, R.layout.test_result_recycler_item7, R.layout.test_result_recycler_item8,
            R.layout.test_result_recycler_item8, R.layout.test_result_recycler_item9, R.layout.test_result_recycler_item10
    };
    private long time;
    private static final String TAG = TestResultActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    public static void actionStart(Context from, String data, long createTime) {
        Intent intent = new Intent(from, TestResultActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("time", createTime);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test_result);
        super.onCreate(savedInstanceState);
        initBasicData();
    }

    private void initBasicData() {
        this.setTitle("卡·测评报告");
        time = getIntent().getLongExtra("time", System.currentTimeMillis());
        try {
            if (getIntent().getStringExtra("data") == null) {
                StringBuilder builder = new StringBuilder();
                AssetManager assetManager = getAssets();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("credit_spend.json")));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                JSONObject jsonObject = new JSONObject(builder.toString());
                dataJson = jsonObject.optJSONObject("result");
            } else {
                dataJson = new JSONObject(getIntent().getStringExtra("data"));
            }
            LogUtils.e("text-result->"+dataJson.toString());
            recyclerView = (RecyclerView) findViewById(R.id.show_recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            incrementAdapter = new IncrementAdapter(new IncrementAdapter.IncrementItem() {
                @Override
                public Integer numberOfItemInSection(Integer section) {
                    return itemNumbers[section];
                }

                @Override
                public Integer itemLayoutForIndexPath(IndexPath indexPath) {
                    LogUtils.e("布局indexPath.getSection()="+indexPath.getSection());
                    return itemLayouts[indexPath.getSection()];
                }

                @Override
                public void willDisplayItem(IndexPath indexPath, IncrementHolder holder) {
                    LogUtils.e("indexPath.getSection()="+indexPath.getSection());
                    switch (indexPath.getSection()) {
                        case 0: {
                            showDataForCard(holder);
                        }
                        break;
                        case 1: {
                            showDataForPaySituation(holder, indexPath);
                        }
                        break;
                        case 2: {
                            showDataForPayNum(holder);
                        }
                        break;
                        case 3: {
                            showDataForPayState(holder);
                        }
                        break;
                        case 4: {
                            showDataForPayStable(holder);
                        }
                        break;
                        case 5: {
                            showDataForPayArea(holder);
                        }
                        break;
                        case 6:
                        case 7:
                        case 8: {
                            showDataForPayType(holder, indexPath);
                        }
                        break;
                        case 9: {
                            showDataForPayRank(holder);
                        }
                        break;
                        case 10: {
                            showDataForPayWeekend(holder, indexPath);
                        }
                        break;
                        case 11: {
                            showDataForPayMidNight(holder, indexPath);
                        }
                        break;
                        case 12: {
                            showDataForHoliday(holder);
                        }
                        break;
                        case 13: {
                            showDataForPayAreaUsual(holder);
                        }
                    }
                }
            });
            incrementAdapter.setIncrementSection(new IncrementAdapter.IncrementSection() {

                @Override
                public Integer numberOfSection() {
                    return itemNumbers.length;
                }
            });
            incrementAdapter.setIncrementHead(new IncrementAdapter.IncrementHead() {
                @Override
                public Integer sectionHeadLayoutForSection(Integer section) {
                    Integer layout = null;
                    switch (section) {
                        case 1:
                        case 6:
                        case 7:
                        case 8: {
                            layout = R.layout.test_result_recycler_head1;
                        }
                        break;
                        case 2:
                        case 4:
                        case 9:
                        case 12:
                        case 13: {
                            layout = R.layout.test_result_recycler_other;
                        }
                        break;
                        case 3: {
                            layout = R.layout.test_result_recycler_head2;
                        }
                        break;
                        case 5: {
                            layout = R.layout.test_result_recycler_head3;
                        }
                        break;
                        case 10:
                        case 11: {
                            layout = R.layout.test_result_recycler_head4;
                        }
                        break;

                    }
                    return layout;
                }

                @Override
                public void willDisplayHead(Integer section, IncrementHolder holder) {
                    switch (section) {
                        case 1: {
                            holder.getView(R.id.show_top).setVisibility(View.VISIBLE);
                            holder.setText(R.id.show_title, "交易概况统计").setText(R.id.show_detail, "").setText(R.id.tv_left, "统计周期").setText(R.id.show_right, "交易金额(元)");
                        }
                        break;
                        case 2: {
                            holder.setText(R.id.show_title, "每月交易笔数").setText(R.id.show_detail, "");
                        }
                        break;
                        case 3: {
                            String mess;
                            int TB217 = dataJson.optInt("TB217");
                            switch (TB217) {
                                case 1: {
                                    mess = "中低消费强度";
                                }
                                break;
                                case 2: {
                                    mess = "中高消费强度";
                                }
                                break;
                                case 3: {
                                    mess = "高消费强度";
                                }
                                break;
                                default: {
                                    mess = "低消费强度";
                                }
                                break;
                            }
                            holder.setText(R.id.show_title, "每月消费情况");
                            holder.setText(R.id.show_other, mess);
                        }
                        break;
                        case 4: {
                            holder.setText(R.id.show_title, "交易稳定性指数").setText(R.id.show_detail, "周期：近6个月");
                        }
                        break;
                        case 5: {
                            String mess;
                            switch (dataJson.optString("CT003")) {
                                case "1": {
                                    mess = "有";
                                }
                                break;
                                case "0": {
                                    mess = "无";
                                }
                                break;
                                default: {
                                    mess = "-";
                                }
                                break;
                            }
                            holder.setText(R.id.show_state, mess);
                        }
                        break;
                        case 6: {
                            holder.getView(R.id.show_top).setVisibility(View.VISIBLE);
                            holder.setText(R.id.show_title, "消费类别统计").setText(R.id.show_detail, "周期：近6个月").setText(R.id.tv_left, "消费类别").setText(R.id.show_right, "消费金额(元)");
                        }
                        break;
                        case 7: {
                            holder.getView(R.id.show_top).setVisibility(View.GONE);
                            holder.setText(R.id.tv_left, "休闲娱乐类").setText(R.id.show_right, "消费金额(元)");
                        }
                        break;
                        case 8: {
                            holder.getView(R.id.show_top).setVisibility(View.GONE);
                            holder.setText(R.id.tv_left, "商旅出行类").setText(R.id.show_right, "消费金额(元)");
                        }
                        break;
                        case 9: {
                            holder.setText(R.id.show_title, "交易的MCC排名").setText(R.id.show_detail, "周期：近6个月");
                        }
                        break;
                        case 12: {
                            holder.setText(R.id.show_title, "节假日消费分析").setText(R.id.show_detail, "");
                        }
                        break;
                        case 13: {
                            holder.setText(R.id.show_title, "常用城市消费排名").setText(R.id.show_detail, "周期：近6个月");
                        }
                        break;
                        case 10: {
                            holder.setText(R.id.show_title, "周末时间消费分布")
                                    .setText(R.id.tv_left, "周末时间")
                                    .setText(R.id.show_middle, "消费笔数")
                                    .setText(R.id.show_right, "消费金额(元)");
                        }
                        break;
                        case 11: {
                            holder.setText(R.id.show_title, "午夜时间消费分布")
                                    .setText(R.id.tv_left, "午夜时间")
                                    .setText(R.id.show_middle, "消费金额")
                                    .setText(R.id.show_right, "消费金额占比");
                        }
                        break;

                    }
                }
            });
            incrementAdapter.setIncrementFoot(new IncrementAdapter.IncrementFoot() {
                @Override
                public Integer sectionFootLayoutForSection(Integer section) {
                    Integer layout = null;
                    switch (section) {
                        case 1:
                        case 10:
                        case 11: {
                            layout = R.layout.test_result_recycler_foot1;
                        }
                        break;
                        case 8: {
                            layout = R.layout.test_result_recycler_foot2;
                        }
                        break;
                    }
                    return layout;
                }

                @Override
                public void willDisplayFoot(Integer section, IncrementHolder holder) {

                }
            });
            recyclerView.setAdapter(incrementAdapter);
            incrementAdapter.notifyAllDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    常用城市消费排名
     */
    private void showDataForPayAreaUsual(IncrementHolder holder) {
        String TB268 = TextUtils.isEmpty(dataJson.optString("TB268")) ? "-" : dataJson.optString("TB268");
        String TB269 = TextUtils.isEmpty(dataJson.optString("TB269")) ? "-" : dataJson.optString("TB269");
        String TB270 = TextUtils.isEmpty(dataJson.optString("TB270")) ? "-" : dataJson.optString("TB270");

        float TB263 = (float) dataJson.optDouble("TB263");
        float TB265 = (float) dataJson.optDouble("TB265");
        float TB267 = (float) dataJson.optDouble("TB267");

        float sum = TB263 + TB265 + TB267;

        holder.setText(R.id.textView1, TB268).setText(R.id.textView2, TB269).setText(R.id.textView3, TB270);
        if (!TB268.equals("-")) {
            ChartCircleView circleView = holder.getView(R.id.space1);
            circleView.setLightRatio(TB263 / sum);
            circleView.setCenterTitle(String.format("￥%.2f\nNO.1", TB263));
            circleView.showAni();
        } else {
            ChartCircleView circleView = holder.getView(R.id.space1);
            circleView.setCenterTitle("没有数据");
        }
        if (!TB269.equals("-")) {
            ChartCircleView circleView = holder.getView(R.id.space2);
            circleView.setLightRatio(TB265 / sum);
            circleView.setCenterTitle(String.format("￥%.2f\nNO.2", TB265));
            circleView.showAni();
        } else {
            ChartCircleView circleView = holder.getView(R.id.space2);
            circleView.setCenterTitle("没有数据");
        }
        if (!TB270.equals("-")) {
            ChartCircleView circleView = holder.getView(R.id.space3);
            circleView.setLightRatio(TB267 / sum);
            circleView.setCenterTitle(String.format("￥%.2f\nNO.3", TB267));
            circleView.showAni();
        } else {
            ChartCircleView circleView = holder.getView(R.id.space3);
            circleView.setCenterTitle("没有数据");
        }

    }

    /*
    周末时间消费分布
     */
    private void showDataForPayWeekend(IncrementHolder holder, IndexPath indexPath) {
        String[][] mess = {
                {"近1个月", "TT080", "TT067"},
                {"近第2个月", "TT081", "TT068"},
                {"近第3个月", "TT082", "TT069"}
        };
        holder.setText(R.id.show_time, mess[indexPath.getRow()][0]);
        holder.setText(R.id.show_num, dataJson.optString(mess[indexPath.getRow()][1]) + "笔");
        holder.setText(R.id.show_ratio, dataJson.optString(mess[indexPath.getRow()][2]));
    }

    /*
    节假日消费分析
     */
    private void showDataForHoliday(IncrementHolder holder) {
        ChartLinearView linearView = ((ChartLinearView) holder.getView(R.id.show_linear));
        if (linearView.isShow()) {
            linearView.showAni();
        } else {
            float[] moneys = new float[7];
            float[] numbers = new float[7];

            float maxMoney = 0;
            float maxNumber = 0;

            for (int index = 0; index < 7; index++) {
                float money = (float) dataJson.optDouble(String.format("TT0%02d", index * 2 + 1));
                moneys[index] = money;
                int number = dataJson.optInt(String.format("TT0%02d", index * 2 + 2));
                numbers[index] = number;
                maxMoney = Math.max(maxMoney, money);
                maxNumber = Math.max(maxNumber, number);
            }
            int base = 5, mCapacity = 0, nCapacity = 0;

            mCapacity = (int) ((maxMoney / 5) + 1);
            nCapacity = (int) ((maxNumber / 5) + 1);
            String[] times = {"元旦", "春节", "清明", "劳动节", "端午", "中秋", "国庆"};


            linearView.addAxis(new ChartAxis.Builder(0, base, mCapacity).startOffset(SizeUtil.transFromDip(4))
                    .unit("").auxiliary(true).create(), true);
            linearView.addAxis(new ChartAxis.Builder(0, base, nCapacity).startOffset(SizeUtil.transFromDip(4)).isInteger(true)
                    .unit("笔").auxiliary(true).create(), false);
            linearView.addAxis(new ChartAxis.Builder(Arrays.asList(times))
                    .startPadding(-1).endPadding(-1)
                    .startOffset(SizeUtil.transFromDip(5)).endOffset(SizeUtil.transFromDip(5))
                    .create(), true);
            linearView.addPillars(moneys, Color.parseColor("#5AC8FA"), true);
            linearView.addPillars(numbers, Color.parseColor("#FF9500"), false);

            linearView.showAni();
        }
    }

    /*
    午夜时间消费分布
     */
    private void showDataForPayMidNight(IncrementHolder holder, IndexPath indexPath) {
        String[][] mess = {
                {"近1个月", "TT093", "TT099"},
                {"近6个月", "TT094", "TT100"},
                {"近12个月", "TT095", "TT101"}
        };
        holder.setText(R.id.show_time, mess[indexPath.getRow()][0]);
        holder.setText(R.id.show_num, dataJson.optString(mess[indexPath.getRow()][1]));
        holder.setText(R.id.show_ratio, String.format("%.2f%%", dataJson.optDouble(mess[indexPath.getRow()][2])));
    }

    private void showDataForPayRank(IncrementHolder holder) {
        holder.setText(R.id.show_type, dataJson.optString("MC287"))
                .setText(R.id.show_code, dataJson.optString("MC292"))
                .setText(R.id.show_mcc, dataJson.optString("MC292"))
                .setText(R.id.show_num, dataJson.optString("MC293"))
                .setText(R.id.show_money, dataJson.optString("MC294"));
    }

    private void showDataForCard(IncrementHolder holder) {
        holder.setText(R.id.show_type, dataJson.optString("CA002"));
        ((GradientDrawable) holder.getView(R.id.show_type).getBackground()).setColor(Color.parseColor("#F5A623"));
        TextView genre1 = holder.getView(R.id.show_genre1);
        TextView genre2 = holder.getView(R.id.show_genre2);
        TextView genre3 = holder.getView(R.id.show_genre3);

        int TB215 = dataJson.optInt("TB215", 0);
        switch (TB215) {
            case 0: {
                genre1.setVisibility(View.GONE);
            }
            break;
            case 1: {
                genre1.setText("低消费人群");
            }
            break;
            case 2: {
                genre1.setText("中消费人群");
            }
            break;
            case 3: {
                genre1.setText("高消费人群");
            }
            break;
        }
        int TB216 = dataJson.optInt("TB216", 0);
        switch (TB216) {
            case 0: {
                genre2.setVisibility(View.GONE);
            }
            break;
            case 1: {
                genre2.setText("低活跃度");
            }
            break;
            case 2: {
                genre2.setText("中低活跃度");
            }
            break;
            case 3: {
                genre2.setText("中高活跃度");
            }
            break;
            case 4: {
                genre2.setText("高活跃度");
            }
            break;
        }
        int CA008 = dataJson.optInt("CA008", 0);
        switch (CA008) {
            case 0: {
                genre3.setVisibility(View.GONE);
            }
            break;
            case 1: {
                genre3.setText("高端客户");
            }
            break;
        }

        String CA005 = dataJson.optString("CA005");
        Integer bankIconID = (DataUtils.getBankIconMap().containsKey(CA005) ? DataUtils.getBankIconMap().get(CA005) : R.mipmap.bank_other);
        holder.setImageResource(R.id.show_img, bankIconID);
        holder.setText(R.id.show_code, dataJson.optString("CA006"))
                .setText(R.id.show_bank, dataJson.optString("CA005"))
                .setText(R.id.tv_mess, dataJson.optString("CA004"))
                .setText(R.id.show_name, dataJson.optString("cardholder"))
                .setText(R.id.show_num, DataUtils.transBankCardNum(dataJson.optString("bankcard"), 4));
        if (getIntent().getStringExtra("data") == null) {
            holder.getView(R.id.show_sample).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.show_sample).setVisibility(View.GONE);
        }

    }

    private void showDataForPayType(IncrementHolder holder, IndexPath indexPath) {
        ChartSingleView singleView = holder.getView(R.id.show_single);
        singleView.setLightRatio(0);
        singleView.setCornerRadius(0);

        String[][] titles = {{"餐饮类", "珠宝类", "加油站"}, {"KTV", "休闲娱乐", "保健美容"}, {"航空", "铁路", "住宿"}};
        String[][] numbers = {{"MC005", "MC056", "MC274"}, {"MC026", "MC016", "MC038"}, {"MC170", "MC176", "MC188"}};
        String[][] moneys = {{"MC002", "MC053", "MC276"}, {"MC023", "MC013", "MC035"}, {"MC167", "MC173", "MC185"}};
        String[] lightColors = {"#FF5856D6", "#FFFF9500", "#FF5AC8FA"};
        String[] darkColors = {"#335856D6", "#33FF9500", "#335AC8FA"};

        int number = dataJson.optInt(numbers[indexPath.getSection() - 6][indexPath.getRow()]);
        float money = (float) dataJson.optDouble(moneys[indexPath.getSection() - 6][indexPath.getRow()]);

        if (number >= 0) {
            holder.setText(R.id.show_num, number + "笔");
        }
        if (money >= 0) {
            holder.setText(R.id.show_money, money + "");
        }
        holder.setText(R.id.show_title, titles[indexPath.getSection() - 6][indexPath.getRow()]);
        singleView.setLightColor(Color.parseColor(lightColors[indexPath.getSection() - 6]));
        singleView.setDarkColor(Color.parseColor(darkColors[indexPath.getSection() - 6]));

        float sumMoney = 0;
        for (int index = 0; index < moneys[indexPath.getSection() - 6].length; index++) {
            sumMoney += (float) dataJson.optDouble(moneys[indexPath.getSection() - 6][index]);
        }
        singleView.setLightRatio(money / sumMoney);
        singleView.showAni();
    }

    private void showDataForPaySituation(IncrementHolder holder, IndexPath indexPath) {
        ChartSingleView singleView = holder.getView(R.id.show_single);
        singleView.setCornerRadius(50);
        singleView.setLightRatio(0);

        double sum = 0;

        double TB001 = dataJson.optDouble("TB001", -1);
        double TB002 = dataJson.optDouble("TB002", -1);
        double TB003 = dataJson.optDouble("TB003", -1);
        double TB004 = dataJson.optDouble("TB004", -1);

        if (TB001 >= 0) {
            sum += TB001;
        }
        if (TB002 >= 0) {
            sum += TB002;
        }
        if (TB003 >= 0) {
            sum += TB003;
        }
        if (TB004 >= 0) {
            sum += TB004;
        }

        int TB016 = dataJson.optInt("TB016", -1);
        int TB017 = dataJson.optInt("TB017", -1);
        int TB018 = dataJson.optInt("TB018", -1);
        int TB019 = dataJson.optInt("TB019", -1);

        LogUtils.e("indexPath.getRow()="+indexPath.getRow());
        switch (indexPath.getRow()) {
            case 0: {
                holder.setText(R.id.show_title, "近1个月");
                singleView.setLightColor(Color.parseColor("#FF4A90E2"));
                singleView.setDarkColor(Color.parseColor("#334A90E2"));
                if (sum != 0 && TB001 >= 0) {
                    singleView.setLightRatio((float) (TB001 / sum));
                }
                if (TB001 >= 0) {
                    holder.setText(R.id.show_money, TB001 + "");
                }
                if (TB016 >= 0) {
                    holder.setText(R.id.show_num, TB016 + "笔");
                }

            }
            break;
            case 1: {
                holder.setText(R.id.show_title, "近3个月");
                singleView.setLightColor(Color.parseColor("#FFF5A623"));
                singleView.setDarkColor(Color.parseColor("#33F5A623"));
                if (sum != 0 && TB002 >= 0) {
                    singleView.setLightRatio((float) (TB002 / sum));
                }
                if (TB002 >= 0) {
                    holder.setText(R.id.show_money, TB002 + "");
                }
                if (TB017 >= 0) {
                    holder.setText(R.id.show_num, TB017 + "笔");
                }
            }
            break;
            case 2: {
                holder.setText(R.id.show_title, "近6个月");
                singleView.setLightColor(Color.parseColor("#FF7ED321"));
                singleView.setDarkColor(Color.parseColor("#337ED321"));
                if (sum != 0 && TB003 >= 0) {
                    singleView.setLightRatio((float) (TB003 / sum));
                }
                if (TB003 >= 0) {
                    holder.setText(R.id.show_money, TB003 + "");
                }
                if (TB018 >= 0) {
                    holder.setText(R.id.show_num, TB018 + "笔");
                }
            }
            break;
            case 3: {
                holder.setText(R.id.show_title, "近12个月");
                singleView.setLightColor(Color.parseColor("#FF5AC8FA"));
                singleView.setDarkColor(Color.parseColor("#335AC8FA"));
                if (sum != 0 && TB004 >= 0) {
                    singleView.setLightRatio((float) (TB004 / sum));
                }
                if (TB004 >= 0) {
                    holder.setText(R.id.show_money, TB004 + "");
                }
                if (TB019 >= 0) {
                    holder.setText(R.id.show_num, TB019 + "笔");
                }
            }
            break;
        }
        singleView.showAni();
    }

    private void showDataForPayNum(IncrementHolder holder) {

        ChartLinearView linearView = ((ChartLinearView) holder.getView(R.id.show_linear));
        if (linearView.isShow()) {
            linearView.showAni();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            Log.d(TAG, "showDataForPayNum: " + calendar.get(Calendar.MONTH));

            List<String> timeList = new ArrayList<>();
            List<Integer> timeLessList = new ArrayList<>();
            float[] points = new float[6];
            float maxNum = 0;
            for (int index = 0; index < 6; index++) {
                calendar.add(Calendar.MONTH, -1);
                timeList.add(calendar.get(Calendar.MONTH) + 1 + "月");
                timeLessList.add(calendar.get(Calendar.MONTH) + 1);
                String key = "TB0" + (20 + index);
                points[index] = dataJson.optInt(key);
                maxNum = Math.max(maxNum, points[index]);
            }

            String showMaxMess = "-";
            if (maxNum != 0) {
                StringBuilder builder = new StringBuilder();
                for (int index = 0; index < points.length; index++) {
                    if (points[index] == maxNum) {
                        if (builder.length() == 0) {
                            builder.append("" + timeLessList.get(index));
                        } else {
                            builder.append("," + timeLessList.get(index));
                        }
                    }
                }
                showMaxMess = builder.toString();
            }

            holder.setText(R.id.show_title, showMaxMess + "月份");
            int base = 5, capacity = 0;

            capacity = (int) ((maxNum / 5) + 1);
            linearView.addAxis(new ChartAxis.Builder(0, base, capacity).isInteger(true)
                    .unit("笔").auxiliary(true).startOffset(SizeUtil.transFromDip(4)).create(), true);
            linearView.addAxis(new ChartAxis.Builder(0, base, capacity).isInteger(true)
                    .unit("笔").auxiliary(true).startOffset(SizeUtil.transFromDip(4)).create(), false);
            linearView.addAxis(new ChartAxis.Builder(timeList)
                    .startPadding(-1).endPadding(-1).auxiliary(true)
                    .startOffset(SizeUtil.transFromDip(5)).endOffset(SizeUtil.transFromDip(5)).create(), true);
            linearView.addPoints(points, Color.parseColor("#4A90E2"), true);
            linearView.showAni();
        }
    }

    /*
    显示交易稳定性数据
     */
    private void showDataForPayStable(IncrementHolder holder) {
        ChartScoreView scoreView = holder.getView(R.id.show_score);
        List<Score> scores = new ArrayList<>();
        scores.add(new Score(Color.parseColor("#FF3B30"), false, "1", "低"));
        scores.add(new Score(Color.parseColor("#FF9500"), false, "2", "中"));
        scores.add(new Score(Color.parseColor("#FFCC00"), false, "3", "高"));
        scores.add(new Score(Color.parseColor("#D8D8D8"), false, "4", "无交易"));

        scoreView.setmScores(scores);
        int TB122 = dataJson.optInt("TB122", 0);

        String[] titles = {"低稳定性", "中稳定性", "高稳定性", "无交易"};
        String[] messs = {"1500<近6个月交易金额的标准差", "300<近6个月交易金额的标准差<=1500", "近6个月交易金额的标准差<=300", ""};
        scores.get(3 - TB122).setSelect(true);

        holder.setText(R.id.show_state, titles[3 - TB122]);
        holder.setText(R.id.tv_mess, messs[3 - TB122]);
    }

    /*
    显示交易地域分析
     */
    private void showDataForPayArea(IncrementHolder holder) {
        holder.setText(R.id.show_genre1, TextUtils.isEmpty(dataJson.optString("TB150")) ? "-" : dataJson.optString("TB150"));
        holder.setText(R.id.show_genre2, TextUtils.isEmpty(dataJson.optString("TB151")) ? "-" : dataJson.optString("TB151"));
        holder.setText(R.id.show_genre3, TextUtils.isEmpty(dataJson.optString("TB153")) ? "-" : dataJson.optString("TB153"));
    }

    private void showDataForPayState(IncrementHolder holder) {
        ChartLinearView linearView = holder.getView(R.id.show_linear);
        ChartCircleView circleView = holder.getView(R.id.show_circle);
        if (linearView.isShow()) {
            linearView.showAni();
            circleView.showAni();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);

            String[] times = new String[6];
            float[] moneys = new float[6];
            float[] numbers = new float[6];

            float maxMoney = 0, maxNumber = 0;
            for (int index = 0; index < 6; index++) {
                calendar.add(Calendar.MONTH, -1);
                times[index] = calendar.get(Calendar.MONTH) + 1 + "月";
                moneys[index] = (float) dataJson.optDouble("TB0" + (34 + index));
                numbers[index] = dataJson.optInt("TB0" + (48 + index));

                maxMoney = Math.max(maxMoney, moneys[index]);
                maxNumber = Math.max(maxNumber, numbers[index]);
            }

            int base = 5, mCapacity = 0, nCapacity = 0;

            nCapacity = (int) ((maxNumber / 5) + 1);
            mCapacity = (int) ((maxMoney / 5) + 1);

            linearView.addAxis(new ChartAxis.Builder(0, base, mCapacity).startOffset(SizeUtil.transFromDip(4)).isInteger(true)
                    .unit("").auxiliary(true).create(), true);
            linearView.addAxis(new ChartAxis.Builder(0, base, nCapacity).startOffset(SizeUtil.transFromDip(4)).isInteger(true)
                    .unit("笔").auxiliary(true).create(), false);
            linearView.addAxis(new ChartAxis.Builder(Arrays.asList(times))
                    .startPadding(-1).endPadding(-1)
                    .startOffset(SizeUtil.transFromDip(5)).endOffset(SizeUtil.transFromDip(5))
                    .create(), true);
            linearView.addPillars(moneys, Color.parseColor("#7ED321"), true);
            linearView.addPoints(numbers, Color.parseColor("#F5A623"), false);

            circleView.setCenterTitle(String.format("%.2f笔", dataJson.optDouble("TB063")));
            if (maxNumber != 0) {
                circleView.setLightRatio((float) dataJson.optDouble("TB063") / maxNumber);
            }
            holder.setText(R.id.show_money, "￥" + dataJson.optString("TB068"));

            circleView.showAni();
            linearView.showAni();
        }
    }

    public Bitmap shotRecyclerView() {
        Bitmap bigBitmap = null;
        if (incrementAdapter != null) {
            int size = incrementAdapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                IncrementHolder holder = incrementAdapter.createViewHolder(recyclerView, incrementAdapter.getItemViewType(i));
                Log.d(TAG, "shotRecyclerView: " + holder.getAdapterPosition());
                incrementAdapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(recyclerView.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = recyclerView.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }

    private void screenShots() {
        Bitmap bitmap = shotRecyclerView();
        if (bitmap != null) {
            try {
                File fileDir = getExternalFilesDir(null);
                fileDir.mkdirs();
                File file = new File(fileDir, "share.png");
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.putExtra("Kdescription", getString(R.string.share_sub_run));
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                startActivity(Intent.createChooser(intent, "分享"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "分享失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(1,1,1,"分享").setIcon(R.drawable.icon_share_white).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            break;
//            case 1: {
//                screenShots();
//            } break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }
}
