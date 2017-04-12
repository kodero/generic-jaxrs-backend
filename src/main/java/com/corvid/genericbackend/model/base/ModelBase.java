package com.corvid.genericbackend.model.base;

import com.corvid.bes.model.AbstractModelBase;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author mokua
 *         Base class for all the entities
 */

@MappedSuperclass
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
//@EntityCallbackClass(ModelBaseListener.class)
@EntityListeners({ModelListener.class})
public abstract class ModelBase extends AbstractModelBase{

}
