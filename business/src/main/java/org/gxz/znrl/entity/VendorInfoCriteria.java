package org.gxz.znrl.entity;

import java.util.ArrayList;
import java.util.List;

public class VendorInfoCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VendorInfoCriteria() {
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

        public Criteria andVenNoIsNull() {
            addCriterion("VEN_NO is null");
            return (Criteria) this;
        }

        public Criteria andVenNoIsNotNull() {
            addCriterion("VEN_NO is not null");
            return (Criteria) this;
        }

        public Criteria andVenNoEqualTo(String value) {
            addCriterion("VEN_NO =", value, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoNotEqualTo(String value) {
            addCriterion("VEN_NO <>", value, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoGreaterThan(String value) {
            addCriterion("VEN_NO >", value, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoGreaterThanOrEqualTo(String value) {
            addCriterion("VEN_NO >=", value, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoLessThan(String value) {
            addCriterion("VEN_NO <", value, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoLessThanOrEqualTo(String value) {
            addCriterion("VEN_NO <=", value, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoLike(String value) {
            addCriterion("VEN_NO like", value, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoNotLike(String value) {
            addCriterion("VEN_NO not like", value, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoIn(List<String> values) {
            addCriterion("VEN_NO in", values, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoNotIn(List<String> values) {
            addCriterion("VEN_NO not in", values, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoBetween(String value1, String value2) {
            addCriterion("VEN_NO between", value1, value2, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNoNotBetween(String value1, String value2) {
            addCriterion("VEN_NO not between", value1, value2, "venNo");
            return (Criteria) this;
        }

        public Criteria andVenNameIsNull() {
            addCriterion("VEN_NAME is null");
            return (Criteria) this;
        }

        public Criteria andVenNameIsNotNull() {
            addCriterion("VEN_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andVenNameEqualTo(String value) {
            addCriterion("VEN_NAME =", value, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameNotEqualTo(String value) {
            addCriterion("VEN_NAME <>", value, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameGreaterThan(String value) {
            addCriterion("VEN_NAME >", value, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameGreaterThanOrEqualTo(String value) {
            addCriterion("VEN_NAME >=", value, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameLessThan(String value) {
            addCriterion("VEN_NAME <", value, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameLessThanOrEqualTo(String value) {
            addCriterion("VEN_NAME <=", value, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameLike(String value) {
            addCriterion("VEN_NAME like", value, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameNotLike(String value) {
            addCriterion("VEN_NAME not like", value, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameIn(List<String> values) {
            addCriterion("VEN_NAME in", values, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameNotIn(List<String> values) {
            addCriterion("VEN_NAME not in", values, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameBetween(String value1, String value2) {
            addCriterion("VEN_NAME between", value1, value2, "venName");
            return (Criteria) this;
        }

        public Criteria andVenNameNotBetween(String value1, String value2) {
            addCriterion("VEN_NAME not between", value1, value2, "venName");
            return (Criteria) this;
        }

        public Criteria andLealPersonIsNull() {
            addCriterion("LEAL_PERSON is null");
            return (Criteria) this;
        }

        public Criteria andLealPersonIsNotNull() {
            addCriterion("LEAL_PERSON is not null");
            return (Criteria) this;
        }

        public Criteria andLealPersonEqualTo(String value) {
            addCriterion("LEAL_PERSON =", value, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonNotEqualTo(String value) {
            addCriterion("LEAL_PERSON <>", value, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonGreaterThan(String value) {
            addCriterion("LEAL_PERSON >", value, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonGreaterThanOrEqualTo(String value) {
            addCriterion("LEAL_PERSON >=", value, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonLessThan(String value) {
            addCriterion("LEAL_PERSON <", value, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonLessThanOrEqualTo(String value) {
            addCriterion("LEAL_PERSON <=", value, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonLike(String value) {
            addCriterion("LEAL_PERSON like", value, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonNotLike(String value) {
            addCriterion("LEAL_PERSON not like", value, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonIn(List<String> values) {
            addCriterion("LEAL_PERSON in", values, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonNotIn(List<String> values) {
            addCriterion("LEAL_PERSON not in", values, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonBetween(String value1, String value2) {
            addCriterion("LEAL_PERSON between", value1, value2, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andLealPersonNotBetween(String value1, String value2) {
            addCriterion("LEAL_PERSON not between", value1, value2, "lealPerson");
            return (Criteria) this;
        }

        public Criteria andVenAddrIsNull() {
            addCriterion("VEN_ADDR is null");
            return (Criteria) this;
        }

        public Criteria andVenAddrIsNotNull() {
            addCriterion("VEN_ADDR is not null");
            return (Criteria) this;
        }

        public Criteria andVenAddrEqualTo(String value) {
            addCriterion("VEN_ADDR =", value, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrNotEqualTo(String value) {
            addCriterion("VEN_ADDR <>", value, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrGreaterThan(String value) {
            addCriterion("VEN_ADDR >", value, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrGreaterThanOrEqualTo(String value) {
            addCriterion("VEN_ADDR >=", value, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrLessThan(String value) {
            addCriterion("VEN_ADDR <", value, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrLessThanOrEqualTo(String value) {
            addCriterion("VEN_ADDR <=", value, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrLike(String value) {
            addCriterion("VEN_ADDR like", value, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrNotLike(String value) {
            addCriterion("VEN_ADDR not like", value, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrIn(List<String> values) {
            addCriterion("VEN_ADDR in", values, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrNotIn(List<String> values) {
            addCriterion("VEN_ADDR not in", values, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrBetween(String value1, String value2) {
            addCriterion("VEN_ADDR between", value1, value2, "venAddr");
            return (Criteria) this;
        }

        public Criteria andVenAddrNotBetween(String value1, String value2) {
            addCriterion("VEN_ADDR not between", value1, value2, "venAddr");
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