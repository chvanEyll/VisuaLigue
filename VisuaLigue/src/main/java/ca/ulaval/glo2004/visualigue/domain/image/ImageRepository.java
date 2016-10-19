package ca.ulaval.glo2004.visualigue.domain.image;

import java.util.UUID;

public interface ImageRepository {

    UUID persist(String sourceImagePathName);

    String get(UUID uuid);

    void delete(UUID uuid);

    void clear();
}
