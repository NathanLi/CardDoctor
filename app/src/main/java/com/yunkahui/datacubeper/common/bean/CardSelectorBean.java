package com.yunkahui.datacubeper.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CardSelectorBean implements Parcelable{

    private String bankCardName;
    private String bankCardNum;
    private String cardHolder;
    private String bankCardTel;
    private int cardId;
    private boolean checked;

    public CardSelectorBean() {
    }

    protected CardSelectorBean(Parcel in) {
        bankCardName = in.readString();
        bankCardNum = in.readString();
        cardHolder = in.readString();
        bankCardTel = in.readString();
        cardId = in.readInt();
        checked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bankCardName);
        dest.writeString(bankCardNum);
        dest.writeString(cardHolder);
        dest.writeString(bankCardTel);
        dest.writeInt(cardId);
        dest.writeByte((byte) (checked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CardSelectorBean> CREATOR = new Creator<CardSelectorBean>() {
        @Override
        public CardSelectorBean createFromParcel(Parcel in) {
            return new CardSelectorBean(in);
        }

        @Override
        public CardSelectorBean[] newArray(int size) {
            return new CardSelectorBean[size];
        }
    };

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(String bankCardNum) {
        this.bankCardNum = bankCardNum;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getBankCardTel() {
        return bankCardTel;
    }

    public void setBankCardTel(String bankCardTel) {
        this.bankCardTel = bankCardTel;
    }
}
