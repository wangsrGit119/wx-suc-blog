package enums;

import lombok.Getter;

/**
 * @author WJL
 */

@Getter
public enum DaoResultEnum {

    /**
     * success
     */
    SUCCESS(1,"success"),
    /**
     * fail
     */
    FAIL(0,"failed");



    private int key;
    private String value;
    DaoResultEnum(int key, String value){
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(int key){
        for (DaoResultEnum daoResultEnum : DaoResultEnum.values()) {
            if(key == daoResultEnum.getKey() ){
                return daoResultEnum.getValue();
            }
        }
        return null;
    }


}
