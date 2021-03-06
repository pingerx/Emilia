package ceuilisa.mirai.response;

import java.util.List;

public class BackResponse<T> {


    /**
     * message : 成功添加收藏
     * datas : []
     */

    private String message;
    private List<T> datas;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
