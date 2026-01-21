package com.pep.minos.system.form.rbac;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.util.Set;

/**
 * 树节点多个编号表单
 *
 * @author liu.gang
 */
@Data
@Accessors(chain = true)
@FieldNameConstants(asEnum = true)
@Schema(description = "树节点多个编号表单")
public class MenuTreeIdsForm {

    /**
     * 编号集合
     */
    @NotEmpty
    @Size(max = 500)
    @Schema(description = "节点Id数组")
    private Set<@NotBlank String> ids;
}