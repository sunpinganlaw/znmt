package org.gxz.znrl.entity;

/**
 * 需要分页的查询的entity才要继承该基本的BaseEntity，否则可以不继承
 * Created by xieyt on 14-11-28.
 */
public class BaseEntity {

    public int beginRowIndex;
    public int endRowIndex;
    public String  sort;
    public String order;

    //pageIndex;第xx页 3
    //pageSize;每页的记录数 3
    public void setPageRowIndex(int pageIndex, int pageSize) {
        this.beginRowIndex = (pageIndex - 1) * pageSize + 1;
        this.endRowIndex = pageIndex * pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
