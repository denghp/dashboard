package com.ace.console.extra.aop;

import com.ace.console.cache.BaseCacheAspect;
import com.ace.console.utils.Constants;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 用户缓存切面
 * 缓存实现
 * 1、username/email/mobile------>id
 * 2、id------->Model
 *
 * @Author: denghp
 * @Date: 10/20/14
 * @Time: 2:47 PM
 */
@Component
@Aspect
public class ResourceCacheAspect extends BaseCacheAspect {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public ResourceCacheAspect() {
        setCacheName(Constants.DEFAULT_MM_CACHE_NAME);
    }

    private static final String ID_KEY_PREFIX = "id_";

    /**
     * 匹配用户Service
     */
    @Pointcut(value = "target(com.ace.console.service.sys.ResourceService)")
    private void resourceServicePointcut() {
    }

    /**
     * only put
     * 如 新增 修改 登录 改密 改状态  只有涉及修改即可
     */
    @Pointcut(value =
            "execution(* save(..)) " +
                    "|| execution(* update(..)) ")
    private void resourceCacheEvictAllPointcut() {
    }


    /**
     * evict 比如删除
     */
    @Pointcut(value = "(execution(* delete(*))) && args(arg)", argNames = "arg")
    private void cacheEvictPointcut(Object arg) {
    }

    /**
     * put 或 get
     * 比如查询
     */
//    @Pointcut(value =
//            "(execution(* selectById(*)))")
//    private void cacheablePointcut() {
//
//    }




    private String idKey(String id) {
        return ID_KEY_PREFIX + id;
    }
}
