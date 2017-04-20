package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	public String field();
	public boolean primarykey() default false;
	public String type() default "varchar(60)" ;
	public boolean defaultNull() default true;
	
}
