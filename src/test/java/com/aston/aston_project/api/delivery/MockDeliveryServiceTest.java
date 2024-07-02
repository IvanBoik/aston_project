package com.aston.aston_project.api.delivery;

import com.aston.aston_project.api.delivery.client.MockDeliveryRequest;
import com.aston.aston_project.api.delivery.client.MockDeliveryResponseGenerator;
import com.aston.aston_project.api.delivery.client.MockDeliveryService;
import com.aston.aston_project.api.delivery.util.DeliveryResponse;
import com.aston.aston_project.api.delivery.util.DeliveryStatus;
import com.aston.aston_project.api.delivery.util.OrderInfo;
import com.aston.aston_project.api.delivery.util.UserInfo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.RetryingTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith({MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
public class MockDeliveryServiceTest {

    @Mock
    private UserInfo userInfo;

    @Mock
    private OrderInfo orderInfo;
    @Spy
    MockDeliveryResponseGenerator generator;

    @InjectMocks
    private MockDeliveryRequest request;

    @InjectMocks
    private MockDeliveryService deliveryService;

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class ApprovingPipeline {
        private static final UUID MOCKED_APPROVING_UUID = UUID.fromString("a5764857-ae35-34dc-8f25-a9c9e73aa898");

        @BeforeEach
        public void init() {
            when(generator.generateUUID()).thenReturn(MOCKED_APPROVING_UUID);
        }

        @RetryingTest(3)
        @Order(1)
        public void delivery_service_returns_on_the_way() {
            DeliveryResponse response = deliveryService.createDelivery(request).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.ON_THE_WAY);
        }

        @RetryingTest(3)
        @Order(2)
        public void delivery_service_returns_current_status() {
            DeliveryResponse response = deliveryService.getCurrentStatus(MOCKED_APPROVING_UUID).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.ON_THE_WAY);
        }

        @RetryingTest(3)
        @Order(3)
        public void delivery_service_throws_exception_when_uuid_not_present() {
            assertThrows(CompletionException.class, () -> deliveryService.getCurrentStatus(UUID.randomUUID()).join());
        }

        @RetryingTest(3)
        @Order(4)
        public void delivery_service_switching_status_to_delivered() {
            DeliveryResponse response = deliveryService.approveDelivery(MOCKED_APPROVING_UUID).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.DELIVERED);
        }

        @RetryingTest(3)
        @Order(5)
        public void delivery_service_switching_status_to_declined_returns_previous() {
            DeliveryResponse response = deliveryService.declineDelivery(MOCKED_APPROVING_UUID).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.DELIVERED);
        }

        @RetryingTest(3)
        @Order(6)
        public void delivery_throws_exception_when_declined_with_incorrect_uuid() {
            assertThrows(CompletionException.class, () -> deliveryService.declineDelivery(UUID.randomUUID()).join());
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class DecliningPipeline {
        private static final UUID MOCKED_DECLINING_UUID = UUID.fromString("2384f927-5e2f-3998-8baa-c768616287f5");

        @BeforeEach
        public void init() {
            when(generator.generateUUID()).thenReturn(MOCKED_DECLINING_UUID);
        }

        @RetryingTest(3)
        @Order(1)
        public void delivery_service_returns_on_the_way() {
            DeliveryResponse response = deliveryService.createDelivery(request).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.ON_THE_WAY);
        }

        @RetryingTest(3)
        @Order(2)
        public void delivery_service_returns_correct_status() {
            DeliveryResponse response = deliveryService.getCurrentStatus(MOCKED_DECLINING_UUID).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.ON_THE_WAY);
        }

        @RetryingTest(3)
        @Order(3)
        public void delivery_service_throws_exception_when_uuid_not_present() {
            assertThrows(CompletionException.class, () -> {
                deliveryService.getCurrentStatus(UUID.randomUUID()).join();
            });
        }

        @RetryingTest(3)
        @Order(4)
        public void delivery_service_switching_status_to_declined() {
            DeliveryResponse response = deliveryService.declineDelivery(MOCKED_DECLINING_UUID).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.DECLINED);
        }

        @RetryingTest(3)
        @Order(5)
        public void delivery_service_switching_status_to_delivered_returns_previous() {
            DeliveryResponse response = deliveryService.approveDelivery(MOCKED_DECLINING_UUID).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.DECLINED);
        }

        @RetryingTest(3)
        @Order(6)
        public void delivery_throws_exception_when_approve_with_incorrect_uuid() {
            assertThrows(CompletionException.class, () -> deliveryService.approveDelivery(UUID.randomUUID()).join());
        }
    }

    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Nested
    public class ReturnsDeclinedPipeline {
        private static final UUID MOCKED_DECLINED_PIPELINE = UUID.fromString("6b7fcbb7-1441-3c17-8580-7fc490f21e59");

        @BeforeEach
        public void init() {
            when(generator.generateUUID()).thenReturn(MOCKED_DECLINED_PIPELINE);
        }

        @RetryingTest(50)
        @Order(1)
        public void delivery_service_returns_declined() {
            DeliveryResponse response = deliveryService.createDelivery(request).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.DECLINED);
        }

        @RetryingTest(3)
        @Order(2)
        public void delivery_service_throws_exception_when_uuid_not_present() {
            assertThrows(CompletionException.class, () -> {
                deliveryService.getCurrentStatus(UUID.randomUUID()).join();
            });
        }

        @RetryingTest(3)
        @Order(3)
        public void delivery_service_switching_status_to_declined() {
            DeliveryResponse response = deliveryService.declineDelivery(MOCKED_DECLINED_PIPELINE).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.DECLINED);
        }

        @RetryingTest(3)
        @Order(4)
        public void delivery_service_switching_status_to_delivered_returns_previous() {
            DeliveryResponse response = deliveryService.approveDelivery(MOCKED_DECLINED_PIPELINE).join();
            assertThat(response.getStatus()).isEqualTo(DeliveryStatus.DECLINED);
        }

    }

    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Nested
    public class InterruptedPipeline {

        @Test
        public void createDelivery_returns_error_response() throws Exception {
            CompletableFuture<DeliveryResponse> delivery = deliveryService.createDelivery(request);
            TimeUnit.MILLISECONDS.sleep(200);
            delivery.obtrudeException(new InterruptedException());
            delivery.whenComplete((r,e)->
                    assertEquals(DeliveryStatus.OTHER,r.getStatus()));
        }


        @Test
        public void getCurrentStatus_returns_error_response() throws Exception {
            CompletableFuture<DeliveryResponse> delivery = deliveryService.getCurrentStatus(UUID.randomUUID());
            TimeUnit.MILLISECONDS.sleep(200);
            delivery.obtrudeException(new InterruptedException());
            delivery.whenComplete((r,e)->
                    assertEquals(DeliveryStatus.OTHER,r.getStatus()));
        }

        @Test
        public void approveDelivery_returns_error_response() throws Exception {
            CompletableFuture<DeliveryResponse> delivery = deliveryService.approveDelivery(UUID.randomUUID());
            TimeUnit.MILLISECONDS.sleep(200);
            delivery.obtrudeException(new InterruptedException());
            delivery.whenComplete((r,e)->
                    assertEquals(DeliveryStatus.OTHER,r.getStatus()));
        }

        @Test
        public void declineDelivery_returns_error_response() throws Exception {
            CompletableFuture<DeliveryResponse> delivery = deliveryService.declineDelivery(UUID.randomUUID());
            TimeUnit.MILLISECONDS.sleep(200);
            delivery.obtrudeException(new InterruptedException());
            delivery.whenComplete((r,e)->
                    assertEquals(DeliveryStatus.OTHER,r.getStatus()));
        }
    }
}
