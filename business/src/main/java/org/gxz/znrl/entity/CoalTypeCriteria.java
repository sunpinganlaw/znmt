package org.gxz.znrl.entity;

import java.util.ArrayList;
import java.util.List;

public class CoalTypeCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CoalTypeCriteria() {
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

        public Criteria andCoalNoIsNull() {
            addCriterion("COAL_NO is null");
            return (Criteria) this;
        }

        public Criteria andCoalNoIsNotNull() {
            addCriterion("COAL_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCoalNoEqualTo(String value) {
            addCriterion("COAL_NO =", value, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoNotEqualTo(String value) {
            addCriterion("COAL_NO <>", value, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoGreaterThan(String value) {
            addCriterion("COAL_NO >", value, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoGreaterThanOrEqualTo(String value) {
            addCriterion("COAL_NO >=", value, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoLessThan(String value) {
            addCriterion("COAL_NO <", value, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoLessThanOrEqualTo(String value) {
            addCriterion("COAL_NO <=", value, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoLike(String value) {
            addCriterion("COAL_NO like", value, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoNotLike(String value) {
            addCriterion("COAL_NO not like", value, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoIn(List<String> values) {
            addCriterion("COAL_NO in", values, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoNotIn(List<String> values) {
            addCriterion("COAL_NO not in", values, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoBetween(String value1, String value2) {
            addCriterion("COAL_NO between", value1, value2, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNoNotBetween(String value1, String value2) {
            addCriterion("COAL_NO not between", value1, value2, "coalNo");
            return (Criteria) this;
        }

        public Criteria andCoalNameIsNull() {
            addCriterion("COAL_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCoalNameIsNotNull() {
            addCriterion("COAL_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCoalNameEqualTo(String value) {
            addCriterion("COAL_NAME =", value, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameNotEqualTo(String value) {
            addCriterion("COAL_NAME <>", value, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameGreaterThan(String value) {
            addCriterion("COAL_NAME >", value, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameGreaterThanOrEqualTo(String value) {
            addCriterion("COAL_NAME >=", value, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameLessThan(String value) {
            addCriterion("COAL_NAME <", value, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameLessThanOrEqualTo(String value) {
            addCriterion("COAL_NAME <=", value, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameLike(String value) {
            addCriterion("COAL_NAME like", value, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameNotLike(String value) {
            addCriterion("COAL_NAME not like", value, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameIn(List<String> values) {
            addCriterion("COAL_NAME in", values, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameNotIn(List<String> values) {
            addCriterion("COAL_NAME not in", values, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameBetween(String value1, String value2) {
            addCriterion("COAL_NAME between", value1, value2, "coalName");
            return (Criteria) this;
        }

        public Criteria andCoalNameNotBetween(String value1, String value2) {
            addCriterion("COAL_NAME not between", value1, value2, "coalName");
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