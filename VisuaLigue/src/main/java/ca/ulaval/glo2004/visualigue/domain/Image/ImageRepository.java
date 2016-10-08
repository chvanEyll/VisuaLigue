package ca.ulaval.glo2004.visualigue.domain.Image;

public interface ImageRepository {

    void persist(PersistentImageRef imageRef) throws ImagePersistenceException;

}
