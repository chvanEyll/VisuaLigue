package ca.ulaval.glo2004.visualigue.domain.image;

import java.util.UUID;

public interface ImageRepository {

    UUID persist(String sourceImagePathName);

    UUID replace(UUID oldUUID, String newSourceImagePathName);

    String get(UUID uuid);

    void delete(UUID uuid);

    void clear();
}
