package tmall.util;

/**
 * @Package: tmall.util
 * @Author: WangXu
 * @CreateDate: 2018/3/19 15:06
 * @Version: 1.0
 */

public class Page {
    //开始数量
    int start;
    //每页显示数量
    int count;
    //总量
    int total;
    String param;

    public Page(int start, int count) {
        super();
        this.start = start;
        this.count = count;
    }

    public int getTotalPage() {
        int totalPage;
        if (total % count == 0) {
            totalPage = total / count;
        } else {
            totalPage = total / count + 1;
        }
        if (totalPage == 0) {
            totalPage = 1;
        }
        return totalPage;
    }

    public int getLast() {
        int last;
        if (total % count == 0) {
            last = total - count;
        } else {
            last = total - total % count;
        }
        if (last < 0) {
            last = 0;
        }
        return last;
    }

    public boolean isHasNext() {
        return start != getLast();
    }

    public boolean isHasPrevious() {
        return start != 0;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


}
