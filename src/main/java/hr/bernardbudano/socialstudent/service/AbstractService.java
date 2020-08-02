package hr.bernardbudano.socialstudent.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class AbstractService<Entity, Repository extends PagingAndSortingRepository<Entity, Long>> {

    protected final Repository repository;

    public AbstractService(final Repository repository){
        this.repository = repository;
    }

    public Page<Entity> getPaged(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Entity> getPaged(final Integer page, final Integer size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Entity create(final Entity entity) {
        return repository.save(entity);
    }

    public void delete(final Entity entity) {
        repository.delete(entity);
    }
}
