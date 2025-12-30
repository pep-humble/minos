package com.pep.base.mvc.customize;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * 响应视图对象
 *
 * @author gang.liu
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(name = "ResponseResult 响应视图对象", description = "响应视图对象")
public class ResponseResult {

    /**
     * 是否成功
     */
    @JSONField(ordinal = 1)
    @JsonProperty(index = 2)
    @Schema(description = "是否成功")
    private int code = HttpStatus.OK.value();

    /**
     * 提示信息
     * 若业务执行失败，则给出具体的错误信息
     */
    @Schema(description = "提示信息")
    @JsonAlias({"msg", "message"})
    @JsonProperty(value = "msg", index = 2)
    @JSONField(name = "msg", ordinal = 2, alternateNames = {"msg", "message"})
    private String msg = "成功";

    /**
     * 业务数据
     */
    @Schema(description = "业务数据")
    private Object data;

}