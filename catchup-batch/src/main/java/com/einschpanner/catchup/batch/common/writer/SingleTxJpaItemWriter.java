package com.einschpanner.catchup.batch.common.writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.List;

public class SingleTxJpaItemWriter<T> implements ItemWriter<T>, InitializingBean {
    protected static final Log logger = LogFactory.getLog(SingleTxJpaItemWriter.class);

    private EntityManager entityManager;
    private boolean usePersist = false;

    /**
     * Set the EntityManager to be used internally.
     *
     * @param entityManager the entityManagerFactory to set
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Set whether the EntityManager should perform a persist instead of a merge.
     *
     * @param usePersist whether to use persist instead of merge.
     */
    public void setUsePersist(boolean usePersist) {
        this.usePersist = usePersist;
    }

    /**
     * Check mandatory properties - there must be an entityManagerFactory.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(entityManager, "An EntityManager is required");
    }

    /**
     * Merge all provided items that aren't already in the persistence context
     * and then flush the entity manager.
     *
     * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
     */
    @Override
    public void write(List<? extends T> items) {
        doWrite(entityManager, items);
        entityManager.flush();
    }

    /**
     * Do perform the actual write operation. This can be overridden in a
     * subclass if necessary.
     *
     * @param entityManager the EntityManager to use for the operation
     * @param items the list of items to use for the write
     */
    protected void doWrite(EntityManager entityManager, List<? extends T> items) {

        if (logger.isDebugEnabled()) {
            logger.debug("Writing to JPA with " + items.size() + " items.");
        }

        if (!items.isEmpty()) {
            long addedToContextCount = 0;
            for (T item : items) {
//                if (((User) item).getUserId().equals(6L)) throw new RuntimeException("다 rollback 시켜줘");
                if (!entityManager.contains(item)) { // Always pass
                    if(usePersist) {
                        entityManager.persist(item);
                    }
                    else {
                        entityManager.merge(item);
                    }
                    addedToContextCount++;
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug(addedToContextCount + " entities " + (usePersist ? " persisted." : "merged."));
                logger.debug((items.size() - addedToContextCount) + " entities found in persistence context.");
            }
        }

    }
}

