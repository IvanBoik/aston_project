package com.aston.aston_project.api.delivery.util;

import java.util.Map;

public interface OrderInfo {

    Map<String,Integer> getProducts();

    String getPharmacyAddress();
}
