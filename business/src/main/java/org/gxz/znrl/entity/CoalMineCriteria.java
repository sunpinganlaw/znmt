package org.gxz.znrl.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CoalMineCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CoalMineCriteria() {
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

        public Criteria andMineNoIsNull() {
            addCriterion("MINE_NO is null");
            return (Criteria) this;
        }

        public Criteria andMineNoIsNotNull() {
            addCriterion("MINE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andMineNoEqualTo(String value) {
            addCriterion("MINE_NO =", value, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoNotEqualTo(String value) {
            addCriterion("MINE_NO <>", value, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoGreaterThan(String value) {
            addCriterion("MINE_NO >", value, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoGreaterThanOrEqualTo(String value) {
            addCriterion("MINE_NO >=", value, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoLessThan(String value) {
            addCriterion("MINE_NO <", value, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoLessThanOrEqualTo(String value) {
            addCriterion("MINE_NO <=", value, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoLike(String value) {
            addCriterion("MINE_NO like", value, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoNotLike(String value) {
            addCriterion("MINE_NO not like", value, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoIn(List<String> values) {
            addCriterion("MINE_NO in", values, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoNotIn(List<String> values) {
            addCriterion("MINE_NO not in", values, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoBetween(String value1, String value2) {
            addCriterion("MINE_NO between", value1, value2, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNoNotBetween(String value1, String value2) {
            addCriterion("MINE_NO not between", value1, value2, "mineNo");
            return (Criteria) this;
        }

        public Criteria andMineNameIsNull() {
            addCriterion("MINE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMineNameIsNotNull() {
            addCriterion("MINE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMineNameEqualTo(String value) {
            addCriterion("MINE_NAME =", value, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameNotEqualTo(String value) {
            addCriterion("MINE_NAME <>", value, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameGreaterThan(String value) {
            addCriterion("MINE_NAME >", value, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameGreaterThanOrEqualTo(String value) {
            addCriterion("MINE_NAME >=", value, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameLessThan(String value) {
            addCriterion("MINE_NAME <", value, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameLessThanOrEqualTo(String value) {
            addCriterion("MINE_NAME <=", value, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameLike(String value) {
            addCriterion("MINE_NAME like", value, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameNotLike(String value) {
            addCriterion("MINE_NAME not like", value, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameIn(List<String> values) {
            addCriterion("MINE_NAME in", values, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameNotIn(List<String> values) {
            addCriterion("MINE_NAME not in", values, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameBetween(String value1, String value2) {
            addCriterion("MINE_NAME between", value1, value2, "mineName");
            return (Criteria) this;
        }

        public Criteria andMineNameNotBetween(String value1, String value2) {
            addCriterion("MINE_NAME not between", value1, value2, "mineName");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("AREA is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("AREA is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("AREA =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("AREA <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("AREA >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("AREA >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("AREA <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("AREA <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("AREA like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("AREA not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("AREA in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("AREA not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("AREA between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("AREA not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andTransDistIsNull() {
            addCriterion("TRANS_DIST is null");
            return (Criteria) this;
        }

        public Criteria andTransDistIsNotNull() {
            addCriterion("TRANS_DIST is not null");
            return (Criteria) this;
        }

        public Criteria andTransDistEqualTo(BigDecimal value) {
            addCriterion("TRANS_DIST =", value, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransDistNotEqualTo(BigDecimal value) {
            addCriterion("TRANS_DIST <>", value, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransDistGreaterThan(BigDecimal value) {
            addCriterion("TRANS_DIST >", value, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransDistGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_DIST >=", value, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransDistLessThan(BigDecimal value) {
            addCriterion("TRANS_DIST <", value, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransDistLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_DIST <=", value, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransDistIn(List<BigDecimal> values) {
            addCriterion("TRANS_DIST in", values, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransDistNotIn(List<BigDecimal> values) {
            addCriterion("TRANS_DIST not in", values, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransDistBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_DIST between", value1, value2, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransDistNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_DIST not between", value1, value2, "transDist");
            return (Criteria) this;
        }

        public Criteria andTransTimeIsNull() {
            addCriterion("TRANS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andTransTimeIsNotNull() {
            addCriterion("TRANS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTransTimeEqualTo(BigDecimal value) {
            addCriterion("TRANS_TIME =", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotEqualTo(BigDecimal value) {
            addCriterion("TRANS_TIME <>", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeGreaterThan(BigDecimal value) {
            addCriterion("TRANS_TIME >", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_TIME >=", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeLessThan(BigDecimal value) {
            addCriterion("TRANS_TIME <", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANS_TIME <=", value, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeIn(List<BigDecimal> values) {
            addCriterion("TRANS_TIME in", values, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotIn(List<BigDecimal> values) {
            addCriterion("TRANS_TIME not in", values, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_TIME between", value1, value2, "transTime");
            return (Criteria) this;
        }

        public Criteria andTransTimeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANS_TIME not between", value1, value2, "transTime");
            return (Criteria) this;
        }

        public Criteria andYearOutpIsNull() {
            addCriterion("YEAR_OUTP is null");
            return (Criteria) this;
        }

        public Criteria andYearOutpIsNotNull() {
            addCriterion("YEAR_OUTP is not null");
            return (Criteria) this;
        }

        public Criteria andYearOutpEqualTo(BigDecimal value) {
            addCriterion("YEAR_OUTP =", value, "yearOutp");
            return (Criteria) this;
        }

        public Criteria andYearOutpNotEqualTo(BigDecimal value) {
            addCriterion("YEAR_OUTP <>", value, "yearOutp");
            return (Criteria) this;
        }

        public Criteria andYearOutpGreaterThan(BigDecimal value) {
            addCriterion("YEAR_OUTP >", value, "yearOutp");
            return (Criteria) this;
        }

        public Criteria andYearOutpGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("YEAR_OUTP >=", value, "yearOutp");
            return (Criteria) this;
        }

        public Criteria andYearOutpLessThan(BigDecimal value) {
            addCriterion("YEAR_OUTP <", value, "yearOutp");
            return (Criteria) this;
        }

        public Criteria andYearOutpLessThanOrEqualTo(BigDecimal value) {
            addCriterion("YEAR_OUTP <=", value, "yearOutp");
            return (Criteria) this;
        }

        public Criteria andYearOutpIn(List<BigDecimal> values) {
            addCriterion("YEAR_OUTP in", values, "yearOutp");
            return (Criteria) this;
        }

        public Criteria andYearOutpNotIn(List<BigDecimal> values) {
            addCriterion("YEAR_OUTP not in", values, "yearOutp");
            return (Criteria) this;
        }

        public Criteria andYearOutpBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("YEAR_OUTP between", value1, value2, "yearOutp");
            return (Criteria) this;
        }

        public Criteria andYearOutpNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("YEAR_OUTP not between", value1, value2, "yearOutp");
            return (Criteria) this;
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

        public Criteria andTransModeIsNull() {
            addCriterion("TRANS_MODE is null");
            return (Criteria) this;
        }

        public Criteria andTransModeIsNotNull() {
            addCriterion("TRANS_MODE is not null");
            return (Criteria) this;
        }

        public Criteria andTransModeEqualTo(String value) {
            addCriterion("TRANS_MODE =", value, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeNotEqualTo(String value) {
            addCriterion("TRANS_MODE <>", value, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeGreaterThan(String value) {
            addCriterion("TRANS_MODE >", value, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeGreaterThanOrEqualTo(String value) {
            addCriterion("TRANS_MODE >=", value, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeLessThan(String value) {
            addCriterion("TRANS_MODE <", value, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeLessThanOrEqualTo(String value) {
            addCriterion("TRANS_MODE <=", value, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeLike(String value) {
            addCriterion("TRANS_MODE like", value, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeNotLike(String value) {
            addCriterion("TRANS_MODE not like", value, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeIn(List<String> values) {
            addCriterion("TRANS_MODE in", values, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeNotIn(List<String> values) {
            addCriterion("TRANS_MODE not in", values, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeBetween(String value1, String value2) {
            addCriterion("TRANS_MODE between", value1, value2, "transMode");
            return (Criteria) this;
        }

        public Criteria andTransModeNotBetween(String value1, String value2) {
            addCriterion("TRANS_MODE not between", value1, value2, "transMode");
            return (Criteria) this;
        }

        public Criteria andMinePropIsNull() {
            addCriterion("MINE_PROP is null");
            return (Criteria) this;
        }

        public Criteria andMinePropIsNotNull() {
            addCriterion("MINE_PROP is not null");
            return (Criteria) this;
        }

        public Criteria andMinePropEqualTo(String value) {
            addCriterion("MINE_PROP =", value, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropNotEqualTo(String value) {
            addCriterion("MINE_PROP <>", value, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropGreaterThan(String value) {
            addCriterion("MINE_PROP >", value, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropGreaterThanOrEqualTo(String value) {
            addCriterion("MINE_PROP >=", value, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropLessThan(String value) {
            addCriterion("MINE_PROP <", value, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropLessThanOrEqualTo(String value) {
            addCriterion("MINE_PROP <=", value, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropLike(String value) {
            addCriterion("MINE_PROP like", value, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropNotLike(String value) {
            addCriterion("MINE_PROP not like", value, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropIn(List<String> values) {
            addCriterion("MINE_PROP in", values, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropNotIn(List<String> values) {
            addCriterion("MINE_PROP not in", values, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropBetween(String value1, String value2) {
            addCriterion("MINE_PROP between", value1, value2, "mineProp");
            return (Criteria) this;
        }

        public Criteria andMinePropNotBetween(String value1, String value2) {
            addCriterion("MINE_PROP not between", value1, value2, "mineProp");
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