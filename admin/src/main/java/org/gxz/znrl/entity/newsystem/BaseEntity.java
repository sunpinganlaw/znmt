package org.gxz.znrl.entity.newsystem;


public class BaseEntity {

    public int beginRowIndex;
    public int endRowIndex;

    //pageIndex;第xx页 3
    //pageSize;每页的记录数 3
    public void setPageRowIndex(int pageIndex, int pageSize) {
        this.beginRowIndex = (pageIndex - 1) * pageSize + 1;
        this.endRowIndex = pageIndex * pageSize;
    }

}
