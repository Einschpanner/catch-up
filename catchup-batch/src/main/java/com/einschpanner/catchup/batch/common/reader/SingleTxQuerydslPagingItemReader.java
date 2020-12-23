package com.einschpanner.catchup.batch.common.reader;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class SingleTxQuerydslPagingItemReader<T> extends AbstractPagingItemReader<T> {

    protected final Map<String, Object> jpaPropertyMap = new HashMap<>();
    protected EntityManager entityManager;
    protected Function<JPAQueryFactory, JPAQuery<T>> queryFunction;
    protected boolean transacted = true; // default value

    protected SingleTxQuerydslPagingItemReader() {
        setName(ClassUtils.getShortName(SingleTxQuerydslPagingItemReader.class));
    }

    public SingleTxQuerydslPagingItemReader(EntityManager entityManager,
                                            int pageSize,
                                            Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
        this(entityManager, pageSize, true, queryFunction);
    }

    public SingleTxQuerydslPagingItemReader(EntityManager entityManager,
                                            int pageSize,
                                            boolean transacted,
                                            Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
        this();
        this.entityManager = entityManager;
        this.queryFunction = queryFunction;
        setPageSize(pageSize);
        setTransacted(transacted);
    }

    /**
     * Reader의 트랜잭션격리 옵션 <br/>
     * - false: 격리 시키지 않고, Chunk 트랜잭션에 의존한다 <br/>
     * (hibernate.default_batch_fetch_size 옵션 사용가능) <br/>
     * - true: 격리 시킨다 <br/>
     * (Reader 조회 결과를 삭제하고 다시 조회했을때 삭제된게 반영되고 조회되길 원할때 사용한다.)
     */
    public void setTransacted(boolean transacted) {
        this.transacted = transacted;
    }

    @Override
    protected void doOpen() throws Exception {
        super.doOpen();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doReadPage() {
//        EntityTransaction tx = null;

        if (transacted) {
//            tx = entityManager.getTransaction();
//            tx.begin();
//
//            entityManager.flush();
            entityManager.clear();
        }

        JPQLQuery<T> query = createQuery()
                .offset(getPage() * getPageSize())
                .limit(getPageSize());

        initResults();

        if (transacted) {
            results.addAll(query.fetch());
            logger.debug("fetch size : "+ results.size());
//            if(tx != null) {
//                tx.commit();
//            }
        } else {
            List<T> queryResult = query.fetch();
            for (T entity : queryResult) {
                entityManager.detach(entity);
                results.add(entity);
            }
        }
    }

    protected JPAQuery<T> createQuery() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFunction.apply(queryFactory);
    }

    protected void initResults() {
        if (CollectionUtils.isEmpty(results)) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }
    }

    @Override
    protected void doJumpToPage(int itemIndex) {
    }

    @Override
    protected void doClose() throws Exception {
        entityManager.close();
        super.doClose();
    }
}