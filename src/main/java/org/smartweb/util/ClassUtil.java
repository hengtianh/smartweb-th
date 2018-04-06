package org.smartweb.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @PROJECT_NAME smartweb
 * @PACKAGE_NAME org.smartweb.util
 * @USER takou
 * @CREATE 2018/4/4
 **/
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * @Description  获得类加载器
     * @Param       []
     * @Return      java.lang.ClassLoader
     * @Author      takou
     * @Date        2018/4/4
     * @Time        下午8:19
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * @Description  加载指定类名的类
     * @Param       [className, isInitialized]
     * @Return      java.lang.Class
     * @Author      takou
     * @Date        2018/4/4
     * @Time        下午8:19
     */
    public static Class loadClass(String className, boolean isInitialized) {

        Class<?> cls = null;
        try {
             cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error(className + " load failure",e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * @Description 获得所有的class文件放入集合
     * @Param       [packageName]
     * @Return      java.util.Set<java.lang.Class<?>>
     * @Author      takou
     * @Date        2018/4/6
     * @Time        上午11:47
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        //根据包名获得类文件存放的位置
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            if (urls != null) {
                for (;urls.hasMoreElements();) {
                    URL url = urls.nextElement();
                    String protocol = url.getProtocol();
                    if ("file".equalsIgnoreCase(protocol)) {
                        String filePath = url.getPath().replaceAll("%20"," ");
                        //根据类文件的路径加载其下的所有类文件
                        addClass(filePath,packageName,classSet);
                    } else if ("jar".equalsIgnoreCase(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                            if (jarEntryEnumeration != null) {
                                for (;jarEntryEnumeration.hasMoreElements();) {
                                    JarEntry jarEntry = jarEntryEnumeration.nextElement();
                                    String jarEntryName =  jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0,jarEntryName.lastIndexOf("."))
                                                .replaceAll("/",".");
                                        doAddClass(className,classSet);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("get resources failure by " + packageName,e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    /**
     * @Description 根据类文件获得类全限定名
     * @Param       [packagePath, packageName, classSet]
     * @Return      void
     * @Author      takou
     * @Date        2018/4/6
     * @Time        上午11:48
     */
    public static void addClass(String packagePath, String packageName, Set<Class<?>> classSet) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return (pathname.isFile() && pathname.getName().endsWith(".class")) || pathname.isDirectory();
            }
        });
        for (File f : files) {
            String fileName = f.getName();
            if (f.isFile()) {
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                className = packageName + "." + className;
                doAddClass(className,classSet);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(subPackagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(subPackageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(subPackagePath,subPackageName,classSet);
            }

        }

    }

    /**
     * @Description 实例bean，放入容器
     * @Param       [className, set]
     * @Return      void
     * @Author      takou
     * @Date        2018/4/6
     * @Time        上午11:49
     */
    public static void doAddClass(String className, Set<Class<?>> set) {
        set.add(loadClass(className,false));
    }

    public static Object newInstance(Class<?> cls) {
        Object instance = null;
        if (cls != null) {
            try {
                instance = cls.newInstance();
            } catch (InstantiationException e) {
                LOGGER.error(cls.toString() + " instantiation init failure",e);
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                LOGGER.error(cls.toString() + " illegal access");
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

//    public static File[] getFiles(String[] filePath) {
//        File[] files = null;
//        for (String file : filePath) {
//            File f = new File(file);
//            if (f.isDirectory()) {
//                getFiles(f.list());
//            }
//            else if (f.isFile()) {
//               files = f.listFiles(new FileFilter() {
//                   public boolean accept(File pathname) {
//                       return pathname.isFile() && pathname.getName().endsWith(".class");
//                   }
//               });
//            }
//        }
//        return files;
//    }files
}
