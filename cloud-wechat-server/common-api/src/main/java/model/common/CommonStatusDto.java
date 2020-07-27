package model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wjl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonStatusDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String type;
}
