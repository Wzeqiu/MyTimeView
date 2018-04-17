package com.example.wangzeqiu.mytimeview.views.editpicture;

/**
 * @author zeqiu.wang
 * @date 2018/4/17
 */
public class EditBlock {
    public static final int TYPE_FILTER = 0x010101;
    public static final int TYPE_CUT = TYPE_FILTER + 1;
    private String data;
    private int res;
    private int type;

    public EditBlock(String data, int res, int type) {
        this.data = data;
        this.res = res;
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EditBlock{" +
                "data='" + data + '\'' +
                ", res=" + res +
                ", type=" + type +
                '}';
    }
}
