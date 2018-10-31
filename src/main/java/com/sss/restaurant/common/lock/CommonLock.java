package com.sss.restaurant.common.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁
 */
public class CommonLock {

    // 库存锁
    public static final Lock STOCK_LOCK = new ReentrantLock();

}
