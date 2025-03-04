package com.ginkgooai.core.common.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.ginkgooai.core.common.constant.ContextsConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ContextUtils extends ConcurrentHashMap<String, Object> implements ContextsConstant {

    protected  static Class<? extends ContextUtils> contextUtilsClass = ContextUtils.class;

    protected static final TransmittableThreadLocal<? extends ContextUtils> CONTEXT =
            new TransmittableThreadLocal() {
                @Override
                protected ContextUtils initialValue() {
                    try {
                        return contextUtilsClass.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        throw new IllegalStateException("Cannot create ContextUtils instance", e);

                    }
                }

                @Override
                protected Object childValue(Object parentValue){
                    if (parentValue instanceof ContextUtils){
                        ContextUtils contextUtils = new ContextUtils();
                        contextUtils.putAll((ContextUtils) parentValue);
                        return contextUtils;
                    }
                    return parentValue;
                }
            };

    public static ContextUtils get() {
        return CONTEXT.get();
    }

    public void clearAll(){
        this.clear();
        CONTEXT.remove();
    }

    public static <T> T get(String key, Class<T> clazz, T defaultValue) {
        return Optional.ofNullable(CONTEXT.get())
                .map(ctx -> Optional.ofNullable(ctx.get(key))
                        .filter(clazz::isInstance)
                        .map(clazz::cast)
                        .orElse(defaultValue))
                .orElse(defaultValue);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, null);
    }

    public static void set(String key, Object value) {
        if (ObjectUtils.isEmpty(value)){
            return;
        }
        CONTEXT.get().put(key, value);
    }

    public String getWorkspaceId(){
        return get(WORKSPACE_ID, String.class);
    }
}
