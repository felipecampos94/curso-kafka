package com.store.car.service.impl;

import com.store.car.dto.CarPostDTO;
import com.store.car.entity.CarPostEntity;
import com.store.car.repository.CarPostRepository;
import com.store.car.repository.OwnerPostRepository;
import com.store.car.service.CarPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CarPostServiceImpl implements CarPostService {

    private final CarPostRepository carPostRepository;
    private final OwnerPostRepository ownerPostRepository;

    @Override
    public void newPostDetails(CarPostDTO carPostDTO) {
        var carPostEntity = mapCarDtoToEntity(carPostDTO);
        this.carPostRepository.save(carPostEntity);
    }


    @Override
    public List<CarPostDTO> getCarSales() {
        List<CarPostDTO> listCarSales = new ArrayList<>();
        this.carPostRepository.findAll().forEach(item -> listCarSales.add(mapCarEntityToDTO(item)));
        return listCarSales;
    }


    @Override
    public void changeCarSale(CarPostDTO carPostDTO, Long postId) {
        this.carPostRepository.findById(postId).ifPresentOrElse(item -> {
                    item.setDescription(carPostDTO.getDescription());
                    item.setContact(carPostDTO.getContact());
                    item.setPrice(carPostDTO.getPrice());
                    item.setBrand(carPostDTO.getBrand());
                    item.setEngineVersion(carPostDTO.getEngineVersion());
                    item.setModel(carPostDTO.getModel());

                    this.carPostRepository.save(item);
                }, () -> {
                    throw new NoSuchElementException();
                }
        );
    }

    @Override
    public void removeCarSale(Long postId) {
        this.carPostRepository.deleteById(postId);
    }

    private CarPostDTO mapCarEntityToDTO(CarPostEntity carPostEntity) {
        return CarPostDTO.builder()
                .brand(carPostEntity.getBrand())
                .city(carPostEntity.getCity())
                .model(carPostEntity.getModel())
                .description(carPostEntity.getDescription())
                .engineVersion(carPostEntity.getEngineVersion())
                .createDate(carPostEntity.getCreateDate())
                .ownerName(carPostEntity.getOwnerPost().getName())
                .price(carPostEntity.getPrice())
                .build();
    }

    private CarPostEntity mapCarDtoToEntity(CarPostDTO carPostDTO) {
        var carPostEntity = new CarPostEntity();
        ownerPostRepository.findById(carPostDTO.getOwnerId()).ifPresentOrElse(item -> {
            carPostEntity.setOwnerPost(item);
            carPostEntity.setContact(item.getContactNumber());
        }, () -> {
            throw new RuntimeException();
        });

        carPostEntity.setModel(carPostDTO.getModel());
        carPostEntity.setBrand(carPostDTO.getBrand());
        carPostEntity.setPrice(carPostDTO.getPrice());
        carPostEntity.setCity(carPostDTO.getCity());
        carPostEntity.setDescription(carPostDTO.getDescription());
        carPostEntity.setEngineVersion(carPostDTO.getEngineVersion());
        carPostEntity.setCreateDate(String.valueOf(new Date()));
        return carPostEntity;
    }

}
