package com.zerobase.cms.order.domain.product;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductItemForm {

    private Long productId;
    private String name;
    private Integer price;
    private Integer count;
}
