package guru.sfg.beer.order.service.web.mappers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.beer.BeerService;
import guru.sfg.beer.order.service.web.model.BeerDto;
import guru.sfg.beer.order.service.web.model.BeerOrderLineDto;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {
    
    private BeerService beerService;
    private BeerOrderLineMapper mapper;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setBeerMapper(BeerOrderLineMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine beerOrderLine){

        BeerOrderLineDto orderLineDto = mapper.beerOrderLineToDto(beerOrderLine);
        Optional<BeerDto> optionalBeer = beerService.getBeerByUpc(orderLineDto.getUpc());

        optionalBeer.ifPresent(beerDto -> {
            orderLineDto.setBeerName(beerDto.getBeerName());
            orderLineDto.setBeerStyle(beerDto.getBeerStyle());
            orderLineDto.setPrice(beerDto.getPrice());
        });

        return orderLineDto;
    }

    
    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto){
        return mapper.dtoToBeerOrderLine(dto);
    }    
}