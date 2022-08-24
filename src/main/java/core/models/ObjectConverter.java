/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author asimkaymak
 */
public class ObjectConverter {
    public static List<?> convertObjectToList(Object obj) {
    List<?> list = new ArrayList<>();
    if (obj.getClass().isArray()) {
        list = Arrays.asList((Object[])obj);
    } else if (obj instanceof Collection) {
        list = new ArrayList<>((Collection<?>)obj);
    }
    return list;
}
}
