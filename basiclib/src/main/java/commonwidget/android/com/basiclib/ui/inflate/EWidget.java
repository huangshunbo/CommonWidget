package commonwidget.android.com.basiclib.ui.inflate;

import android.support.annotation.IdRes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EWidget {
    @IdRes int id() default -1;
    @IdRes int parentId() default 0;
}
