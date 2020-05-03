package org.gxz.znrl.entity;

import java.util.ArrayList;
import java.util.List;

public class CarrierInfoCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CarrierInfoCriteria() {
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

        public Criteria andCarrierNoIsNull() {
            addCriterion("CARRIER_NO is null");
            return (Criteria) this;
        }

        public Criteria andCarrierNoIsNotNull() {
            addCriterion("CARRIER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierNoEqualTo(String value) {
            addCriterion("CARRIER_NO =", value, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoNotEqualTo(String value) {
            addCriterion("CARRIER_NO <>", value, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoGreaterThan(String value) {
            addCriterion("CARRIER_NO >", value, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoGreaterThanOrEqualTo(String value) {
            addCriterion("CARRIER_NO >=", value, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoLessThan(String value) {
            addCriterion("CARRIER_NO <", value, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoLessThanOrEqualTo(String value) {
            addCriterion("CARRIER_NO <=", value, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoLike(String value) {
            addCriterion("CARRIER_NO like", value, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoNotLike(String value) {
            addCriterion("CARRIER_NO not like", value, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoIn(List<String> values) {
            addCriterion("CARRIER_NO in", values, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoNotIn(List<String> values) {
            addCriterion("CARRIER_NO not in", values, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoBetween(String value1, String value2) {
            addCriterion("CARRIER_NO between", value1, value2, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNoNotBetween(String value1, String value2) {
            addCriterion("CARRIER_NO not between", value1, value2, "carrierNo");
            return (Criteria) this;
        }

        public Criteria andCarrierNameIsNull() {
            addCriterion("CARRIER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCarrierNameIsNotNull() {
            addCriterion("CARRIER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierNameEqualTo(String value) {
            addCriterion("CARRIER_NAME =", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameNotEqualTo(String value) {
            addCriterion("CARRIER_NAME <>", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameGreaterThan(String value) {
            addCriterion("CARRIER_NAME >", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameGreaterThanOrEqualTo(String value) {
            addCriterion("CARRIER_NAME >=", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameLessThan(String value) {
            addCriterion("CARRIER_NAME <", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameLessThanOrEqualTo(String value) {
            addCriterion("CARRIER_NAME <=", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameLike(String value) {
            addCriterion("CARRIER_NAME like", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameNotLike(String value) {
            addCriterion("CARRIER_NAME not like", value, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameIn(List<String> values) {
            addCriterion("CARRIER_NAME in", values, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameNotIn(List<String> values) {
            addCriterion("CARRIER_NAME not in", values, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameBetween(String value1, String value2) {
            addCriterion("CARRIER_NAME between", value1, value2, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierNameNotBetween(String value1, String value2) {
            addCriterion("CARRIER_NAME not between", value1, value2, "carrierName");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrIsNull() {
            addCriterion("CARRIER_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrIsNotNull() {
            addCriterion("CARRIER_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrEqualTo(String value) {
            addCriterion("CARRIER_ADDR =", value, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrNotEqualTo(String value) {
            addCriterion("CARRIER_ADDR <>", value, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrGreaterThan(String value) {
            addCriterion("CARRIER_ADDR >", value, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrGreaterThanOrEqualTo(String value) {
            addCriterion("CARRIER_ADDR >=", value, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrLessThan(String value) {
            addCriterion("CARRIER_ADDR <", value, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrLessThanOrEqualTo(String value) {
            addCriterion("CARRIER_ADDR <=", value, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrLike(String value) {
            addCriterion("CARRIER_ADDR like", value, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrNotLike(String value) {
            addCriterion("CARRIER_ADDR not like", value, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrIn(List<String> values) {
            addCriterion("CARRIER_ADDR in", values, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrNotIn(List<String> values) {
            addCriterion("CARRIER_ADDR not in", values, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrBetween(String value1, String value2) {
            addCriterion("CARRIER_ADDR between", value1, value2, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andCarrierAddrNotBetween(String value1, String value2) {
            addCriterion("CARRIER_ADDR not between", value1, value2, "carrierAddr");
            return (Criteria) this;
        }

        public Criteria andContNumIsNull() {
            addCriterion("CONT_NUM is null");
            return (Criteria) this;
        }

        public Criteria andContNumIsNotNull() {
            addCriterion("CONT_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andContNumEqualTo(String value) {
            addCriterion("CONT_NUM =", value, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumNotEqualTo(String value) {
            addCriterion("CONT_NUM <>", value, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumGreaterThan(String value) {
            addCriterion("CONT_NUM >", value, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumGreaterThanOrEqualTo(String value) {
            addCriterion("CONT_NUM >=", value, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumLessThan(String value) {
            addCriterion("CONT_NUM <", value, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumLessThanOrEqualTo(String value) {
            addCriterion("CONT_NUM <=", value, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumLike(String value) {
            addCriterion("CONT_NUM like", value, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumNotLike(String value) {
            addCriterion("CONT_NUM not like", value, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumIn(List<String> values) {
            addCriterion("CONT_NUM in", values, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumNotIn(List<String> values) {
            addCriterion("CONT_NUM not in", values, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumBetween(String value1, String value2) {
            addCriterion("CONT_NUM between", value1, value2, "contNum");
            return (Criteria) this;
        }

        public Criteria andContNumNotBetween(String value1, String value2) {
            addCriterion("CONT_NUM not between", value1, value2, "contNum");
            return (Criteria) this;
        }

        public Criteria andContPersonIsNull() {
            addCriterion("CONT_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andContPersonIsNotNull() {
            addCriterion("CONT_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andContPersonEqualTo(String value) {
            addCriterion("CONT_PERSON =", value, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonNotEqualTo(String value) {
            addCriterion("CONT_PERSON <>", value, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonGreaterThan(String value) {
            addCriterion("CONT_PERSON >", value, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonGreaterThanOrEqualTo(String value) {
            addCriterion("CONT_PERSON >=", value, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonLessThan(String value) {
            addCriterion("CONT_PERSON <", value, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonLessThanOrEqualTo(String value) {
            addCriterion("CONT_PERSON <=", value, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonLike(String value) {
            addCriterion("CONT_PERSON like", value, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonNotLike(String value) {
            addCriterion("CONT_PERSON not like", value, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonIn(List<String> values) {
            addCriterion("CONT_PERSON in", values, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonNotIn(List<String> values) {
            addCriterion("CONT_PERSON not in", values, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonBetween(String value1, String value2) {
            addCriterion("CONT_PERSON between", value1, value2, "contPerson");
            return (Criteria) this;
        }

        public Criteria andContPersonNotBetween(String value1, String value2) {
            addCriterion("CONT_PERSON not between", value1, value2, "contPerson");
            return (Criteria) this;
        }

        public Criteria andZycNumIsNull() {
            addCriterion("ZYC_NUM is null");
            return (Criteria) this;
        }

        public Criteria andZycNumIsNotNull() {
            addCriterion("ZYC_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andZycNumEqualTo(Integer value) {
            addCriterion("ZYC_NUM =", value, "zycNum");
            return (Criteria) this;
        }

        public Criteria andZycNumNotEqualTo(Integer value) {
            addCriterion("ZYC_NUM <>", value, "zycNum");
            return (Criteria) this;
        }

        public Criteria andZycNumGreaterThan(Integer value) {
            addCriterion("ZYC_NUM >", value, "zycNum");
            return (Criteria) this;
        }

        public Criteria andZycNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("ZYC_NUM >=", value, "zycNum");
            return (Criteria) this;
        }

        public Criteria andZycNumLessThan(Integer value) {
            addCriterion("ZYC_NUM <", value, "zycNum");
            return (Criteria) this;
        }

        public Criteria andZycNumLessThanOrEqualTo(Integer value) {
            addCriterion("ZYC_NUM <=", value, "zycNum");
            return (Criteria) this;
        }

        public Criteria andZycNumIn(List<Integer> values) {
            addCriterion("ZYC_NUM in", values, "zycNum");
            return (Criteria) this;
        }

        public Criteria andZycNumNotIn(List<Integer> values) {
            addCriterion("ZYC_NUM not in", values, "zycNum");
            return (Criteria) this;
        }

        public Criteria andZycNumBetween(Integer value1, Integer value2) {
            addCriterion("ZYC_NUM between", value1, value2, "zycNum");
            return (Criteria) this;
        }

        public Criteria andZycNumNotBetween(Integer value1, Integer value2) {
            addCriterion("ZYC_NUM not between", value1, value2, "zycNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumIsNull() {
            addCriterion("PBC_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPbcNumIsNotNull() {
            addCriterion("PBC_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPbcNumEqualTo(Integer value) {
            addCriterion("PBC_NUM =", value, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumNotEqualTo(Integer value) {
            addCriterion("PBC_NUM <>", value, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumGreaterThan(Integer value) {
            addCriterion("PBC_NUM >", value, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("PBC_NUM >=", value, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumLessThan(Integer value) {
            addCriterion("PBC_NUM <", value, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumLessThanOrEqualTo(Integer value) {
            addCriterion("PBC_NUM <=", value, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumIn(List<Integer> values) {
            addCriterion("PBC_NUM in", values, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumNotIn(List<Integer> values) {
            addCriterion("PBC_NUM not in", values, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumBetween(Integer value1, Integer value2) {
            addCriterion("PBC_NUM between", value1, value2, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andPbcNumNotBetween(Integer value1, Integer value2) {
            addCriterion("PBC_NUM not between", value1, value2, "pbcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumIsNull() {
            addCriterion("WFC_NUM is null");
            return (Criteria) this;
        }

        public Criteria andWfcNumIsNotNull() {
            addCriterion("WFC_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andWfcNumEqualTo(Integer value) {
            addCriterion("WFC_NUM =", value, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumNotEqualTo(Integer value) {
            addCriterion("WFC_NUM <>", value, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumGreaterThan(Integer value) {
            addCriterion("WFC_NUM >", value, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("WFC_NUM >=", value, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumLessThan(Integer value) {
            addCriterion("WFC_NUM <", value, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumLessThanOrEqualTo(Integer value) {
            addCriterion("WFC_NUM <=", value, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumIn(List<Integer> values) {
            addCriterion("WFC_NUM in", values, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumNotIn(List<Integer> values) {
            addCriterion("WFC_NUM not in", values, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumBetween(Integer value1, Integer value2) {
            addCriterion("WFC_NUM between", value1, value2, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andWfcNumNotBetween(Integer value1, Integer value2) {
            addCriterion("WFC_NUM not between", value1, value2, "wfcNum");
            return (Criteria) this;
        }

        public Criteria andTscNumIsNull() {
            addCriterion("TSC_NUM is null");
            return (Criteria) this;
        }

        public Criteria andTscNumIsNotNull() {
            addCriterion("TSC_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andTscNumEqualTo(Integer value) {
            addCriterion("TSC_NUM =", value, "tscNum");
            return (Criteria) this;
        }

        public Criteria andTscNumNotEqualTo(Integer value) {
            addCriterion("TSC_NUM <>", value, "tscNum");
            return (Criteria) this;
        }

        public Criteria andTscNumGreaterThan(Integer value) {
            addCriterion("TSC_NUM >", value, "tscNum");
            return (Criteria) this;
        }

        public Criteria andTscNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("TSC_NUM >=", value, "tscNum");
            return (Criteria) this;
        }

        public Criteria andTscNumLessThan(Integer value) {
            addCriterion("TSC_NUM <", value, "tscNum");
            return (Criteria) this;
        }

        public Criteria andTscNumLessThanOrEqualTo(Integer value) {
            addCriterion("TSC_NUM <=", value, "tscNum");
            return (Criteria) this;
        }

        public Criteria andTscNumIn(List<Integer> values) {
            addCriterion("TSC_NUM in", values, "tscNum");
            return (Criteria) this;
        }

        public Criteria andTscNumNotIn(List<Integer> values) {
            addCriterion("TSC_NUM not in", values, "tscNum");
            return (Criteria) this;
        }

        public Criteria andTscNumBetween(Integer value1, Integer value2) {
            addCriterion("TSC_NUM between", value1, value2, "tscNum");
            return (Criteria) this;
        }

        public Criteria andTscNumNotBetween(Integer value1, Integer value2) {
            addCriterion("TSC_NUM not between", value1, value2, "tscNum");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andForShortEqualTo(String value) {
            addCriterion("FOR_SHORT =", value, "forShort");
            return (Criteria) this;
        }
        public Criteria andForShortLike(String value) {
            addCriterion("FOR_SHORT like", value, "forShort");
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