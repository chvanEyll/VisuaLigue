package ca.ulaval.glo2004.visualigue.domain.image;

import ca.ulaval.glo2004.visualigue.domain.resource.PersistentResource;

public interface ImageRepository {

    void persist(PersistentResource imageResource) throws ImagePersistenceException;

    void delete(PersistentResource imageResource);
}
