package com.analytics.data.service.impl;

import com.analytics.data.dto.CarPostDTO;
import com.analytics.data.entity.BrandAnalyticsEntity;
import com.analytics.data.entity.CarModelAnalyticsEntity;
import com.analytics.data.entity.CarModelPriceEntity;
import com.analytics.data.repository.BrandAnalyticsRepository;
import com.analytics.data.repository.CarModelAnalyticsRepository;
import com.analytics.data.repository.CarPriceAnalyticsRepository;
import com.analytics.data.service.PostAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostAnalyticsServiceImpl implements PostAnalyticsService {
    private final BrandAnalyticsRepository brandAnalyticsRepository;
    private final CarModelAnalyticsRepository carModelAnalyticsRepository;
    private final CarPriceAnalyticsRepository carPriceAnalyticsRepository;

    @Override
    public void saveDataAnalytics(CarPostDTO carPostDTO) {
        this.saveBrandAnalytics(carPostDTO.getBrand());
        this.saveCarModelAnalytics(carPostDTO.getModel());
        this.saveCarModelPriceAnalytics(carPostDTO.getModel(), carPostDTO.getPrice());
    }

    private void saveCarModelPriceAnalytics(String carModel, Double price) {
        var carModelPriceEntity = new CarModelPriceEntity();
        carModelPriceEntity.setModel(carModel);
        carModelPriceEntity.setPrice(price);
        this.carPriceAnalyticsRepository.save(carModelPriceEntity);
    }

    private void saveCarModelAnalytics(String carModel) {
        var carModelAnalyticsEntity = new CarModelAnalyticsEntity();
        this.carModelAnalyticsRepository.findByModel(carModel).ifPresentOrElse(item -> {
            item.setPosts(item.getPosts() + 1);
            this.carModelAnalyticsRepository.save(item);
        }, () -> {
            carModelAnalyticsEntity.setModel(carModel);
            carModelAnalyticsEntity.setPosts(1L);
            this.carModelAnalyticsRepository.save(carModelAnalyticsEntity);
        });
    }

    private void saveBrandAnalytics(String brand) {
        var brandAnalyticsEntity = new BrandAnalyticsEntity();
        this.brandAnalyticsRepository.findByBrand(brand).ifPresentOrElse(item -> {
            item.setPosts(item.getPosts() + 1);
            this.brandAnalyticsRepository.save(item);
        }, () -> {
            brandAnalyticsEntity.setBrand(brand);
            brandAnalyticsEntity.setPosts(1L);
            this.brandAnalyticsRepository.save(brandAnalyticsEntity);
        });
    }
}
