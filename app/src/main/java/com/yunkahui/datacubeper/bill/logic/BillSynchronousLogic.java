package com.yunkahui.datacubeper.bill.logic;

import android.app.Fragment;

import com.yunkahui.datacubeper.bill.ui.BillFragment;
import com.yunkahui.datacubeper.bill.ui.BillSynchronousActivity;
import com.yunkahui.datacubeper.common.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class BillSynchronousLogic {


    //判断银行是否支持,支付则返回可以输入的类型
    public static int[] judgeBank(String bankName) {

        List<Bank> bankList = new ArrayList<>();
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_USER, BillSynchronousActivity.TYPE_CARD_NUMBER}, new String[]{"中国银行", "中行"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_PHONE, BillSynchronousActivity.TYPE_USER, BillSynchronousActivity.TYPE_CARD_NUMBER}, new String[]{"中国工商银行", "工商银行", "工行", "工商"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_PHONE, BillSynchronousActivity.TYPE_USER, BillSynchronousActivity.TYPE_ID_CARD, BillSynchronousActivity.TYPE_CARD_NUMBER}, new String[]{"中国农业银行", "农业银行", "农业"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_PHONE, BillSynchronousActivity.TYPE_USER, BillSynchronousActivity.TYPE_ID_CARD}, new String[]{"民生银行", "民生"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_PHONE, BillSynchronousActivity.TYPE_USER, BillSynchronousActivity.TYPE_CARD_NUMBER}, new String[]{"广发银行", "广发"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_CARD_NUMBER}, new String[]{"交通银行", "交通"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_ID_CARD}, new String[]{"招商银行", "招商"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_PHONE}, new String[]{"中信银行", "中信"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_USER, BillSynchronousActivity.TYPE_USER, BillSynchronousActivity.TYPE_ID_CARD}, new String[]{"平安银行", "平安"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_USER, BillSynchronousActivity.TYPE_ID_CARD}, new String[]{"中国建设银行", "建设银行", "建设", "建行"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_ID_CARD_NO_PASSWORD}, new String[]{"上海浦发银行", "浦发银行", "浦发"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_USER, BillSynchronousActivity.TYPE_CARD_NUMBER}, new String[]{"兴业银行", "兴业"}));
        bankList.add(new Bank(new int[]{BillSynchronousActivity.TYPE_ID_CARD_NO_PASSWORD, BillSynchronousActivity.TYPE_CARD_NUMBER_NO_PASSWORD}, new String[]{"光大银行", "光大"}));

        for (int i = 0; i < bankList.size(); i++) {
            String[] alias = bankList.get(i).getBankAlias();
            for (int k = 0; k < alias.length; k++) {
                if (alias[k].equals(bankName)) {
                    return bankList.get(i).getType();
                }
            }
        }
        return new int[]{};
    }


    //根据类型显示银行卡所支持的查询方法
    public List<Tabs> getTabs(int[] type) {
        List<Tabs> tabsList = new ArrayList<>();
        for (int i = 0; i < type.length; i++) {
            Tabs tabs = new Tabs();
            tabs.setId(type[i]);
            switch (type[i]) {
                case BillSynchronousActivity.TYPE_CARD_NUMBER:
                    tabs.setTitle("卡号");
                    break;
                case BillSynchronousActivity.TYPE_USER:
                    tabs.setTitle("用户名");
                    break;
                case BillSynchronousActivity.TYPE_ID_CARD:
                    tabs.setTitle("身份证");
                    break;
                case BillSynchronousActivity.TYPE_PHONE:
                    tabs.setTitle("手机号");
                    break;
                case BillSynchronousActivity.TYPE_ID_CARD_NO_PASSWORD:
                    tabs.setTitle("身份证");
                    break;
                case BillSynchronousActivity.TYPE_CARD_NUMBER_NO_PASSWORD:
                    tabs.setTitle("卡号");
                    break;
            }
            tabsList.add(tabs);
        }
        return tabsList;
    }

    public static class Bank {
        private int id;
        private int[] type;
        private String[] bankAlias;

        public Bank(int[] type, String[] bankAlias) {
            this.type = type;
            this.bankAlias = bankAlias;
        }

        public Bank(int id, int[] type, String[] bankAlias) {
            this.id = id;
            this.type = type;
            this.bankAlias = bankAlias;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int[] getType() {
            return type;
        }

        public void setType(int[] type) {
            this.type = type;
        }

        public String[] getBankAlias() {
            return bankAlias;
        }

        public void setBankAlias(String[] bankAlias) {
            this.bankAlias = bankAlias;
        }
    }

    public class Tabs {
        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


}
