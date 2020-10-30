package com.einschpanner.catchup.batch.common.reader;

import org.springframework.batch.item.database.JpaPagingItemReader;

public class JpaNoOffsetPagingItemReader<T> extends JpaPagingItemReader<T> {
    @Override
    public int getPage() {
        return 0;
    }
}