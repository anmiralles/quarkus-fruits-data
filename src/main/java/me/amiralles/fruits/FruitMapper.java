package me.amiralles.fruits;

import me.amiralles.fruits.schema.FruitCreated;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.JAKARTA_CDI)
public interface FruitMapper {
  /**
   * Maps all fields from {@code fight} to a {@link me.amiralles.fruits.schema.FruitCreated}
   * @param fruit
   * @return
   */
  FruitCreated toSchema(Fruit fruit);
}
