package com.corvid.tulip.admin.model.base;

import com.corvid.bes.model.AbstractModelBase;

import javax.persistence.*;

/**
 * @author mokua
 * Base class for all the entities
 */

@MappedSuperclass
@EntityListeners({ModelListener.class})
public abstract class ModelBase extends AbstractModelBase{

}
