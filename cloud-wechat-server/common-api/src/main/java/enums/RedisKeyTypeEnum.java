package enums;

import lombok.Getter;

/**
 * @author WJL
 */

@Getter
public enum RedisKeyTypeEnum {

    /**
     * key praise:article
     */
    PRAISE_ARTICLE_ID(1,"praise:articleId:"),

    /**
     * key scan:article
     */
    SCAN_ARTICLE_ID(1,"scan:articleId:"),
    /**
     * key
     */
    USER_ID(2,"userId:");

    private int key;
    private String value;
    RedisKeyTypeEnum(int key, String value){
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(int key){
        for (RedisKeyTypeEnum daoResultEnum : RedisKeyTypeEnum.values()) {
            if(key == daoResultEnum.getKey() ){
                return daoResultEnum.getValue();
            }
        }
        return null;
    }


}
