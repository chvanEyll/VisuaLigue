package ca.ulaval.glo2004.visualigue.domain.image;

public interface ImageRepository {

    String persist(String sourceImagePathName);

    String replace(String oldUUID, String newSourceImagePathName);

    String get(String uuid);

    void delete(String uuid);

    void clear();
}
