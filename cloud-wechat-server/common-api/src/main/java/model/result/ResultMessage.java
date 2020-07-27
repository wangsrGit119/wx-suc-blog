package model.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author WJL
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class  ResultMessage<T>  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;
    private T data;


    public static ResultMessage  success(Object data){
        ResultMessage resultMessage = ResultMessage.builder()
                .code(200)
                .message("success")
                .data(data)
                .build();
        return resultMessage;
    }

    public ResultMessage failed(Integer code , String message){
        ResultMessage resultMessage = ResultMessage.builder()
                .code(code)
                .message(message)
                .build();
        return resultMessage;
    }

}
