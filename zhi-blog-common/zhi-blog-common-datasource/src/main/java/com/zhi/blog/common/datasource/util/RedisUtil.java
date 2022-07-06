package com.zhi.blog.common.datasource.util;

import com.zhi.blog.common.core.util.CoreUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Ted
 * @date 2022/7/5
 **/
@RequiredArgsConstructor
@Component
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * key 操作
     * @param key not null
     * @return result
     */
    public boolean del(String key) {
        try {
            if (CoreUtil.isEmpty(key)) {
                return false;
            }
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }

    public boolean expire(String key, long second) {
        if (CoreUtil.isEmpty(key)) {
            return false;
        }
        if (second > 0) {
            return true;
        }
        try {
            return Optional.ofNullable(redisTemplate.expire(key, second, TimeUnit.SECONDS))
                    .orElse(false);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }

    public boolean expireAt(String key, LocalDateTime localDateTime) {
        if (CoreUtil.isEmpty(key)) {
            return false;
        }
        if (localDateTime.isBefore(LocalDateTime.now())) {
            return false;
        }
        try {
            return Optional.ofNullable(redisTemplate.expireAt(key
                            , localDateTime.toInstant(ZoneOffset.of(ZoneOffset.systemDefault().getId()))))
                    .orElse(false);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }

    /**
     * String 操作
     * @param key not null
     * @return Object
     */
    public Object get(String key) {
        if (CoreUtil.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key, Object value) {
        return set(key, value, -1);
    }

    public boolean set(String key, Object value, long second) {
        try {
            if (CoreUtil.isEmpty(key)) {
                return false;
            }
            if(second <= 0) {
                redisTemplate.opsForValue().set(key, value);
            } else {
                redisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }

    /**
     * hash 操作
     * @param key cache key
     * @param hashKey item key
     * @param value value
     * @param second second
     * @return result
     */
    public boolean hashSet(String key, String hashKey, Object value, long second) {

        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return expire(key, second);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }

    public Map<Object, Object> hashGet(String key) {
        if (CoreUtil.isEmpty(key)) {
            return null;
        }
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return null;
    }

    public Object hashItemGet(String key, String hashKey) {
        if (CoreUtil.isEmpty(key) || CoreUtil.isEmpty(hashKey)) {
            return null;
        }
        try {
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return null;
    }

    /**
     * set 操作
     * @param key not null
     * @return nullable
     */
    public Set<Object> setGet(String key) {
        if (CoreUtil.isEmpty(key)) {
            return null;
        }
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return null;
    }

    public boolean setSet(String key, long second, Object... values) {
        if (CoreUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.opsForSet().add(key, values);
            return expire(key, second);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }

    public boolean setRemove(String key, Object... values) {
        if (CoreUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.opsForSet().remove(key, values);
            return true;
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }

    public boolean setValueExist(String key, Object value) {
        if (CoreUtil.isEmpty(key)) {
            return false;
        }
        try {
            return Optional.ofNullable(redisTemplate.opsForSet().isMember(key, value)).orElse(false);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }

    public List<Object> listGet(String key) {
        if (CoreUtil.isEmpty(key)) {
            return null;
        }
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return null;
    }

    public Object listGetItem(String key, long index) {
        if (CoreUtil.isEmpty(key)) {
            return null;
        }
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return null;
    }

    public boolean listSet(String key, Object value, long second) {
        if (CoreUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return expire(key, second);
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }

    public boolean listRemove(String key, Object value) {
        if (CoreUtil.isEmpty(key)) {
            return false;
        }
        try {
            redisTemplate.opsForList().remove(key, Long.MAX_VALUE, value);
            return true;
        } catch (Exception e) {
            CoreUtil.error(e);
        }
        return false;
    }


}
