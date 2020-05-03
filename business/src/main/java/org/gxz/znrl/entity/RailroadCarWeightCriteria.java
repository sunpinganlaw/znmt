package org.gxz.znrl.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RailroadCarWeightCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RailroadCarWeightCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        public void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andRailroadCarIdIsNull() {
            addCriterion("RAILROAD_CAR_ID is null");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdIsNotNull() {
            addCriterion("RAILROAD_CAR_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdEqualTo(Integer value) {
            addCriterion("RAILROAD_CAR_ID =", value, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdNotEqualTo(Integer value) {
            addCriterion("RAILROAD_CAR_ID <>", value, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdGreaterThan(Integer value) {
            addCriterion("RAILROAD_CAR_ID >", value, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("RAILROAD_CAR_ID >=", value, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdLessThan(Integer value) {
            addCriterion("RAILROAD_CAR_ID <", value, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdLessThanOrEqualTo(Integer value) {
            addCriterion("RAILROAD_CAR_ID <=", value, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdIn(List<Integer> values) {
            addCriterion("RAILROAD_CAR_ID in", values, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdNotIn(List<Integer> values) {
            addCriterion("RAILROAD_CAR_ID not in", values, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdBetween(Integer value1, Integer value2) {
            addCriterion("RAILROAD_CAR_ID between", value1, value2, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarIdNotBetween(Integer value1, Integer value2) {
            addCriterion("RAILROAD_CAR_ID not between", value1, value2, "railroadCarId");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeIsNull() {
            addCriterion("RAILROAD_CAR_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeIsNotNull() {
            addCriterion("RAILROAD_CAR_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeEqualTo(String value) {
            addCriterion("RAILROAD_CAR_TYPE =", value, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeNotEqualTo(String value) {
            addCriterion("RAILROAD_CAR_TYPE <>", value, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeGreaterThan(String value) {
            addCriterion("RAILROAD_CAR_TYPE >", value, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeGreaterThanOrEqualTo(String value) {
            addCriterion("RAILROAD_CAR_TYPE >=", value, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeLessThan(String value) {
            addCriterion("RAILROAD_CAR_TYPE <", value, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeLessThanOrEqualTo(String value) {
            addCriterion("RAILROAD_CAR_TYPE <=", value, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeLike(String value) {
            addCriterion("RAILROAD_CAR_TYPE like", value, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeNotLike(String value) {
            addCriterion("RAILROAD_CAR_TYPE not like", value, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeIn(List<String> values) {
            addCriterion("RAILROAD_CAR_TYPE in", values, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeNotIn(List<String> values) {
            addCriterion("RAILROAD_CAR_TYPE not in", values, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeBetween(String value1, String value2) {
            addCriterion("RAILROAD_CAR_TYPE between", value1, value2, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarTypeNotBetween(String value1, String value2) {
            addCriterion("RAILROAD_CAR_TYPE not between", value1, value2, "railroadCarType");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightIsNull() {
            addCriterion("RAILROAD_CAR_WEIGHT is null");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightIsNotNull() {
            addCriterion("RAILROAD_CAR_WEIGHT is not null");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightEqualTo(BigDecimal value) {
            addCriterion("RAILROAD_CAR_WEIGHT =", value, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightNotEqualTo(BigDecimal value) {
            addCriterion("RAILROAD_CAR_WEIGHT <>", value, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightGreaterThan(BigDecimal value) {
            addCriterion("RAILROAD_CAR_WEIGHT >", value, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RAILROAD_CAR_WEIGHT >=", value, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightLessThan(BigDecimal value) {
            addCriterion("RAILROAD_CAR_WEIGHT <", value, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RAILROAD_CAR_WEIGHT <=", value, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightIn(List<BigDecimal> values) {
            addCriterion("RAILROAD_CAR_WEIGHT in", values, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightNotIn(List<BigDecimal> values) {
            addCriterion("RAILROAD_CAR_WEIGHT not in", values, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RAILROAD_CAR_WEIGHT between", value1, value2, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andRailroadCarWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RAILROAD_CAR_WEIGHT not between", value1, value2, "railroadCarWeight");
            return (Criteria) this;
        }

        public Criteria andOpCodeIsNull() {
            addCriterion("OP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andOpCodeIsNotNull() {
            addCriterion("OP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andOpCodeEqualTo(Long value) {
            addCriterion("OP_CODE =", value, "opCode");
            return (Criteria) this;
        }

        public Criteria andOpCodeNotEqualTo(Long value) {
            addCriterion("OP_CODE <>", value, "opCode");
            return (Criteria) this;
        }

        public Criteria andOpCodeGreaterThan(Long value) {
            addCriterion("OP_CODE >", value, "opCode");
            return (Criteria) this;
        }

        public Criteria andOpCodeGreaterThanOrEqualTo(Long value) {
            addCriterion("OP_CODE >=", value, "opCode");
            return (Criteria) this;
        }

        public Criteria andOpCodeLessThan(Long value) {
            addCriterion("OP_CODE <", value, "opCode");
            return (Criteria) this;
        }

        public Criteria andOpCodeLessThanOrEqualTo(Long value) {
            addCriterion("OP_CODE <=", value, "opCode");
            return (Criteria) this;
        }

        public Criteria andOpCodeIn(List<Long> values) {
            addCriterion("OP_CODE in", values, "opCode");
            return (Criteria) this;
        }

        public Criteria andOpCodeNotIn(List<Long> values) {
            addCriterion("OP_CODE not in", values, "opCode");
            return (Criteria) this;
        }

        public Criteria andOpCodeBetween(Long value1, Long value2) {
            addCriterion("OP_CODE between", value1, value2, "opCode");
            return (Criteria) this;
        }

        public Criteria andOpCodeNotBetween(Long value1, Long value2) {
            addCriterion("OP_CODE not between", value1, value2, "opCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeIsNull() {
            addCriterion("UPDATE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeIsNotNull() {
            addCriterion("UPDATE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeEqualTo(Long value) {
            addCriterion("UPDATE_CODE =", value, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeNotEqualTo(Long value) {
            addCriterion("UPDATE_CODE <>", value, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeGreaterThan(Long value) {
            addCriterion("UPDATE_CODE >", value, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeGreaterThanOrEqualTo(Long value) {
            addCriterion("UPDATE_CODE >=", value, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeLessThan(Long value) {
            addCriterion("UPDATE_CODE <", value, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeLessThanOrEqualTo(Long value) {
            addCriterion("UPDATE_CODE <=", value, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeIn(List<Long> values) {
            addCriterion("UPDATE_CODE in", values, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeNotIn(List<Long> values) {
            addCriterion("UPDATE_CODE not in", values, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeBetween(Long value1, Long value2) {
            addCriterion("UPDATE_CODE between", value1, value2, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateCodeNotBetween(Long value1, Long value2) {
            addCriterion("UPDATE_CODE not between", value1, value2, "updateCode");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("UPDATE_DATE =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("UPDATE_DATE <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("UPDATE_DATE >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("UPDATE_DATE <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("UPDATE_DATE in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("UPDATE_DATE not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}