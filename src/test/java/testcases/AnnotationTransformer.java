package testcases;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

public class AnnotationTransformer implements IAnnotationTransformer {
	@Override
	public void transform(ITestAnnotation annotation, 
                                                Class testClass, 
                                                Constructor testConstructor, 
                                                Method testMethod) {
		// TODO Auto-generated method stub
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
