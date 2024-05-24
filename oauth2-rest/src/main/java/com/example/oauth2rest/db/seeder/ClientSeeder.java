package com.example.oauth2service.clirunner.db.seeder;

import com.example.oauth2service.clirunner.db.factory.ClientFactory;
import com.example.oauth2service.port.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientSeeder {

    private final ClientFactory clientFactory;
    private final ClientRepository clientRepository;

    public void seed(String ...args) {
        var client = clientFactory.make();
        clientRepository.save(client);
    }
}
