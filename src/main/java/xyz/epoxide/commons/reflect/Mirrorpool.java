package xyz.epoxide.commons.reflect;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Mirrorpool {
    
    /**
     * Gets a list of classes that are in a package. This is done by looking at the actual
     * files.
     * 
     * @param packageName The name of the package to search through.
     * @return A list of classes found in the specified package.
     */
    public static List<Class<?>> getClasses (String packageName) throws ClassNotFoundException {       
        final String packagePath = packageName.replace('.', '/');
        final URL packageURL = Thread.currentThread().getContextClassLoader().getResource(packagePath);
        
        if (packageURL == null)
            throw new IllegalArgumentException(String.format("Could not load from %s. Does %s exist?", packagePath, packageName));
            
        final File packageDirectory = new File(packageURL.getFile());
        final List<Class<?>> classList = new ArrayList<Class<?>>();
        
        for (final File file : packageDirectory.listFiles())
            classList.addAll(getClasses(file, packageName));
            
        return classList;
    }
    
    /**
     * Recursively gets all class files in a directory.
     * 
     * @param file The file to search through. If this is a class, it will be added itself.
     * @param packageName The package name for the file being looked at.
     * @return A list of classes found in the specified package.
     */
    private static List<Class<?>> getClasses (File file, String packageName) throws ClassNotFoundException {       
        final List<Class<?>> classList = new ArrayList<Class<?>>();
        final String fileName = packageName + '.' + file.getName();
        
        if (file.isDirectory())
            for (final File subFile : file.listFiles())
                classList.addAll(getClasses(subFile, fileName));
                
        else if (fileName.endsWith(".class"))
            classList.add(Class.forName(fileName.substring(0, fileName.length() - ".class".length())));
            
        return classList;
    }
}