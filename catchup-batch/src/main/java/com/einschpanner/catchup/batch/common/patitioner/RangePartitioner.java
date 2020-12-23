package com.einschpanner.catchup.batch.common.patitioner;

import com.einschpanner.catchup.domain.user.dao.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.einschpanner.catchup.domain.user.domain.QUser.user;

@Component
@RequiredArgsConstructor
@Slf4j
public class RangePartitioner implements Partitioner {
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        int min = minId().intValue();
        int max = maxId().intValue();
        int pageSize = (max - min) / gridSize + 1;

        log.debug("Min: {}, Max : {}, PageSize : {}", min, max, pageSize);

        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;
        int start = min;
        int end = start + pageSize - 1;

        while (start <= max)
        {
            ExecutionContext context = new ExecutionContext();

            if(end >= max) {
                end = max;
            }

            context.putString("name", "task-thread-" + number);
            context.putInt("min", start);
            context.putInt("max", end);
            result.put("partition-" + number, context);

            log.debug("CREATED PARTITION: {}, RANGE FROM {}, TO {}",
                    context.getString("name"),
                    context.getInt("min"),
                    context.getInt("max"));

            start += pageSize;
            end += pageSize;

            number++;
        }
        return result;
    }

    private Long minId(){
        return queryFactory.selectFrom(user)
                .select(user.userId.min())
                .fetchOne();
    }

    private Long maxId(){
        return queryFactory.selectFrom(user)
                .select(user.userId.max())
                .fetchOne();
    }
}
