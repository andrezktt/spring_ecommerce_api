package com.andrezktt.spring_ecommerce_api.service;

import com.andrezktt.spring_ecommerce_api.dto.OrderResponseDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Async
    public void sendOrderConfirmation(OrderResponseDTO orderDetails) {
        System.out.println("========================================================");
        System.out.println("INICIANDO TAREFA LENTA (ENVIO DE E-MAIL)...");
        System.out.println("Thread " + Thread.currentThread().getName());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("E-mail de confirmação para o pedido #" + orderDetails.orderId() + " enviado para o cliente ID #" + orderDetails.customerId());
        System.out.println("TAREFA LENTA CONCLUÍDA!");
        System.out.println("========================================================");
    }
}
