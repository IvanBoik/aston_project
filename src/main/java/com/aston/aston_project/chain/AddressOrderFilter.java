package com.aston.aston_project.chain;

import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.Address;
import com.aston.aston_project.entity.Order;
import com.aston.aston_project.entity.Pharmacy;
import com.aston.aston_project.entity.User;
import com.aston.aston_project.entity.en.OrderTypeEnum;
import com.aston.aston_project.repository.PharmacyRepository;
import com.aston.aston_project.util.exception.NotFoundDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class AddressOrderFilter implements OrderFilter{
    private PharmacyRepository pharmacyRepository;
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
        order.setAddress(address);
    }
}
