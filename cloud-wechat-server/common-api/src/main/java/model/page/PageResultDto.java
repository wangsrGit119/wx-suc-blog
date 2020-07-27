package model.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author WJL
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResultDto<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<T> records;
    private long total;
    private long size;
    private long current;




}
