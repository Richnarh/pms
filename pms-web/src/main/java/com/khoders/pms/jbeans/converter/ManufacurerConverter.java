package com.khoders.pms.jbeans.converter;

import com.khoders.pms.entities.Manufacturer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import org.omnifaces.converter.SelectItemsConverter;

/**
 *
 * @author richa
 */
@FacesConverter(forClass = Manufacturer.class)
public class ManufacurerConverter extends SelectItemsConverter
{
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null)
        {
            return null;
        }
        return ((Manufacturer) value).getId();
    } 
}
