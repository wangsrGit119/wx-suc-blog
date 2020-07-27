package enums;

import lombok.Getter;

/**
 * @author WJL
 */

@Getter
public enum CommonEnum {

    /** 图片类型 **/
    /**
     * 封面图片
     */
    FILE_TYPE_COVER(1,"封面图片"),
    /**
     * 文章内图片
     */
    FILE_TYPE_ARTICLE_CONTENT(0,"文章内图片"),



    /** 文章类型 **/


    /**
     * 置顶文章
     */
    ARTICLE_TYPE_EXCELLENT(1,"置顶文章"),

    /**
     * 普通文章
     */
    ARTICLE_TYPE_ORDINARY(2,"普通文章");



    private int key;
    private String value;
    CommonEnum(int key, String value){
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(int key){
        for (CommonEnum daoResultEnum : CommonEnum.values()) {
            if(key == daoResultEnum.getKey() ){
                return daoResultEnum.getValue();
            }
        }
        return null;
    }


}
