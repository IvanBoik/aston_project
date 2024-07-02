package com.aston.aston_project.chain;

import com.aston.aston_project.api.payment.util.PaymentApi;
import com.aston.aston_project.api.payment.util.PaymentResponse;
import com.aston.aston_project.dto.order.OrderCreateRequestDto;
import com.aston.aston_project.entity.*;
import com.aston.aston_project.entity.en.OrderPaymentEnum;
import com.aston.aston_project.entity.en.PaymentTypeEnum;
import com.aston.aston_project.repository.PharmacyRepository;
import com.aston.aston_project.repository.ProductRepository;
import com.aston.aston_project.util.exception.BalanceProcessingException;
import com.aston.aston_project.util.exception.IncorrectDataException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
@Data
public class PaymentOrderFilter implements OrderFilter {
    private ProductRepository productRepository;
    private PharmacyRepository pharmacyRepository;

    @Override
    public void process(User user, Order order, OrderCreateRequestDto request) {
        List<Product> products = order.getProductList().stream().map(ProductList::getProduct).toList();
        BigDecimal price = products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        OrderPaymentEnum paymentType = request.getPaymentType();
        order.setPaymentType(OrderPaymentType.builder().name(paymentType).build());
        if (paymentType == OrderPaymentEnum.CARD) {
            Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId()).get();
            BigDecimal userBalance = user.getBalance().subtract(price);
            if (userBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new BalanceProcessingException("User balance not enough to pay order");
            }
            user.setBalance(userBalance);
            pharmacy.setBalance(pharmacy.getBalance().add(price));
            Payment payment = Payment.builder()
                    .amount(price)
                    .time(LocalTime.now())
                    .to(pharmacy)
                    .from(user)
                    .paymentType(new PaymentType(PaymentTypeEnum.PAYED))
                    .date(LocalDate.now())
                    .build();
            order.setPayments(List.of(payment));
        }
    }
}
