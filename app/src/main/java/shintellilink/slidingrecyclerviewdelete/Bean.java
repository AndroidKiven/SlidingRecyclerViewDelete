package shintellilink.slidingrecyclerviewdelete;

import java.io.Serializable;

/**
 * Created by Intellilink-Kiven on 2017/1/13.
 */

public class Bean implements Serializable {
    String itemContent;

    public Bean(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }
}
