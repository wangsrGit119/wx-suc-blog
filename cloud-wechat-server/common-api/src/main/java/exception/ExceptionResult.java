package exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wjl
 * @description:
 * @time: 2019/12/10 0010 13:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ExceptionResult {
    private String requestId;
    private String code;
    private String message;
    private String path;
    private final Map<String,Object> data = new HashMap();
}

