package com.khoders.pms.entities;

import com.khoders.pms.entities.system.UserAccountRecord;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author richard
 */
@Entity
@Table(name = "unit_measurement")
public class UnitMeasurement extends UserAccountRecord implements Serializable
{
    @Column(name = "units")
    private String units;

    public String getUnits()
    {
        return units;
    }

    public void setUnits(String units)
    {
        this.units = units;
    }

    @Override
    public String toString()
    {
        return units;
    }
}
