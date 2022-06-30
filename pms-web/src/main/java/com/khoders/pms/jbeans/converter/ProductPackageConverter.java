package com.khoders.pms.jbeans.converter;

import com.khoders.pms.entities.ProductPackage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.omnifaces.converter.SelectItemsConverter;

/**
 *
 * @author richa
 */
@FacesConverter(forClass = ProductPackage.class)
public class ProductPackageConverter extends SelectItemsConverter
{
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null)
        {
            return null;
        }
        return ((ProductPackage) value).getId();
    } 
}
