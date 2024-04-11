package tinyfingers.simplilearn.foodieapp.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Converter
public class CollectionToStringConverter implements AttributeConverter<Collection<String>, String> {

  @Override
  public String convertToDatabaseColumn(Collection<String> strings) {
    if (strings == null) return null;
    return String.join(",", strings);
  }

  @Override
  public Set<String> convertToEntityAttribute(String s) {
    if (s == null) return null;
    return new HashSet<>(Set.of(s.trim().split("\\s*,\\s*")));
  }
}
