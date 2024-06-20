package com.aston.aston_project.service.productList;

public interface ProductListService {

    Integer findTotalProductCountByIdUser(Long user_id, String product_name);
}
