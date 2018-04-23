package com.yunkahui.datacubeper.common.bean;

import java.util.List;

public class GeneratePlan {


    /**
     * planningList : [{"date":1524585600000,"details":[{"sort":1,"repayMoney":590,"repayment":{"time":1524621420172,"money":590},"consumption":[{"time":1524624960172,"money":277,"mccType":"休闲会所类"},{"time":1524628380172,"money":280,"mccType":"票务类"}]}]},{"date":1524672000000,"details":[{"sort":2,"repayMoney":610,"repayment":{"time":1524711404569,"money":610},"consumption":[{"time":1524714884569,"money":201,"mccType":"保健美容类"},{"time":1524717644569,"money":209,"mccType":"票务类"},{"time":1524720104569,"money":208,"mccType":"服装类"}]}]}]
     * planningAdditional : {"repayDate":1524844800000,"repayTotalMoney":1200,"repayTotalCount":2,"consumeTotalCount":5,"maxInRepaymentList":610,"executePeriodBegin":"18年04月25日","executePeriodEnd":"18年04月26日","serviceCharge":"","hlbShouldBalance":"","version":""}
     */

    private PlanningAdditionalBean planningAdditional;
    private List<PlanningListBean> planningList;

    public PlanningAdditionalBean getPlanningAdditional() {
        return planningAdditional;
    }

    public void setPlanningAdditional(PlanningAdditionalBean planningAdditional) {
        this.planningAdditional = planningAdditional;
    }

    public List<PlanningListBean> getPlanningList() {
        return planningList;
    }

    public void setPlanningList(List<PlanningListBean> planningList) {
        this.planningList = planningList;
    }

    public static class PlanningAdditionalBean {
        /**
         * repayDate : 1524844800000
         * repayTotalMoney : 1200
         * repayTotalCount : 2
         * consumeTotalCount : 5
         * maxInRepaymentList : 610
         * executePeriodBegin : 18年04月25日
         * executePeriodEnd : 18年04月26日
         * serviceCharge :
         * hlbShouldBalance :
         * version :
         */

        private long repayDate;
        private int repayTotalMoney;
        private int repayTotalCount;
        private int consumeTotalCount;
        private int maxInRepaymentList;
        private String executePeriodBegin;
        private String executePeriodEnd;
        private String serviceCharge;
        private String hlbShouldBalance;
        private String version;

        public long getRepayDate() {
            return repayDate;
        }

        public void setRepayDate(long repayDate) {
            this.repayDate = repayDate;
        }

        public int getRepayTotalMoney() {
            return repayTotalMoney;
        }

        public void setRepayTotalMoney(int repayTotalMoney) {
            this.repayTotalMoney = repayTotalMoney;
        }

        public int getRepayTotalCount() {
            return repayTotalCount;
        }

        public void setRepayTotalCount(int repayTotalCount) {
            this.repayTotalCount = repayTotalCount;
        }

        public int getConsumeTotalCount() {
            return consumeTotalCount;
        }

        public void setConsumeTotalCount(int consumeTotalCount) {
            this.consumeTotalCount = consumeTotalCount;
        }

        public int getMaxInRepaymentList() {
            return maxInRepaymentList;
        }

        public void setMaxInRepaymentList(int maxInRepaymentList) {
            this.maxInRepaymentList = maxInRepaymentList;
        }

        public String getExecutePeriodBegin() {
            return executePeriodBegin;
        }

        public void setExecutePeriodBegin(String executePeriodBegin) {
            this.executePeriodBegin = executePeriodBegin;
        }

        public String getExecutePeriodEnd() {
            return executePeriodEnd;
        }

        public void setExecutePeriodEnd(String executePeriodEnd) {
            this.executePeriodEnd = executePeriodEnd;
        }

        public String getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(String serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public String getHlbShouldBalance() {
            return hlbShouldBalance;
        }

        public void setHlbShouldBalance(String hlbShouldBalance) {
            this.hlbShouldBalance = hlbShouldBalance;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public static class PlanningListBean {
        /**
         * date : 1524585600000
         * details : [{"sort":1,"repayMoney":590,"repayment":{"time":1524621420172,"money":590},"consumption":[{"time":1524624960172,"money":277,"mccType":"休闲会所类"},{"time":1524628380172,"money":280,"mccType":"票务类"}]}]
         */

        private long date;
        private List<DetailsBean> details;

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public List<DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean {
            /**
             * sort : 1
             * repayMoney : 590
             * repayment : {"time":1524621420172,"money":590}
             * consumption : [{"time":1524624960172,"money":277,"mccType":"休闲会所类"},{"time":1524628380172,"money":280,"mccType":"票务类"}]
             */

            private int sort;
            private int repayMoney;
            private RepaymentBean repayment;
            private List<ConsumptionBean> consumption;

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getRepayMoney() {
                return repayMoney;
            }

            public void setRepayMoney(int repayMoney) {
                this.repayMoney = repayMoney;
            }

            public RepaymentBean getRepayment() {
                return repayment;
            }

            public void setRepayment(RepaymentBean repayment) {
                this.repayment = repayment;
            }

            public List<ConsumptionBean> getConsumption() {
                return consumption;
            }

            public void setConsumption(List<ConsumptionBean> consumption) {
                this.consumption = consumption;
            }

            public static class RepaymentBean {
                /**
                 * time : 1524621420172
                 * money : 590
                 */

                private long time;
                private int money;

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getMoney() {
                    return money;
                }

                public void setMoney(int money) {
                    this.money = money;
                }
            }

            public static class ConsumptionBean {
                /**
                 * time : 1524624960172
                 * money : 277
                 * mccType : 休闲会所类
                 */

                private long time;
                private int money;
                private String mccType;

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getMoney() {
                    return money;
                }

                public void setMoney(int money) {
                    this.money = money;
                }

                public String getMccType() {
                    return mccType;
                }

                public void setMccType(String mccType) {
                    this.mccType = mccType;
                }
            }
        }
    }
}
