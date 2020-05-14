package guru.sfg.beer.order.service.services.beer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.sfg.beer.order.service.web.model.BeerDto;

@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = true)
@Service
public class BeerServiceImpl implements BeerService {

    public final String BEER_PATH_V1 = "/api/v1/beer/";
    public final String BEER_UPC_PATH_V1 = "/api/v1/beerUpc/";
    private final RestTemplate restTemplate;

    private String beerServiceHost;

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }

    public BeerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID beerId) {
        return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + beerId, BeerDto.class));            
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String beerUpc) {
        System.out.println("get beer by upc: " + beerUpc);
        return Optional.of(restTemplate.getForObject(beerServiceHost + BEER_UPC_PATH_V1 + beerUpc, BeerDto.class));
    }
    
}