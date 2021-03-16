package br.com.wlmfincatti;

import br.com.wlmfincatti.service.OrderProducer;

public class StarterApp {

    public static void main(String[] args) {
        new OrderProducer().send();
    }

}
