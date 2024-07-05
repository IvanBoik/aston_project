package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.repository.OrderTypeRepository;
import com.aston.aston_project.repository.PharmacyRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * filter which setting order address and check if address correctly set from request
 * @author K. Zemlyakov
 */
@Component
@AllArgsConstructor
public class AddressOrderFilter implements OrderFilter{
    private PharmacyRepository pharmacyRepository;
    private OrderTypeRepository orderTypeRepository;
    @Override
    public void process(User user, Order order, OrderCreateRequestDto request) {
        Address address;
        if(request.getType() == OrderTypeEnum.DELIVERY) {
            address = user.getAddresses()
                    .stream().filter(a -> Objects.equals(a.getId(), request.getAddressId()))
                    .findAny()
                    .orElseThrow(() -> new NotFoundDataException("User address with that id is not found"));
        }else{
            Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                    .orElseThrow(() -> new NotFoundDataException("Pharmacy with that id not found"));
            address = pharmacy.getAddress();
        }
        OrderType orderType = orderTypeRepository.findByName(request.getType()).orElse(OrderType.builder().name(request.getType()).build());
        order.setType(orderType);
        order.setAddress(address);
    }
}
