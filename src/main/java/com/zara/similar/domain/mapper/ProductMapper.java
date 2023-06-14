package com.zara.similar.domain.mapper;

import com.zara.similar.application.ProductDto;
import com.zara.similar.domain.Product;
import java.util.Objects;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  @Autowired
  private ModelMapper modelMapper;

  public ProductDto toDto(final Product entity) {

    return Objects.isNull(entity) ? null : modelMapper.map(entity, ProductDto.class);
  }

  public Product toEntity(final ProductDto dto) {

    return Objects.isNull(dto) ? null : modelMapper.map(dto, Product.class);
  }
}