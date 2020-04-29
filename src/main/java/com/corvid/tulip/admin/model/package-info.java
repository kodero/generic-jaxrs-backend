@org.hibernate.annotations.FilterDefs({
        @org.hibernate.annotations.FilterDef(
                name = "filterByDeleted",
                defaultCondition = "deleted <> 1"
        ),
})

/*
    MySQL 5.x requires a fake sequence which Hibernate can provide with a custom table.
 */
@GenericGenerator(
        name = "lemrUUIDGenerator",
        strategy = "uuid2"
)

package com.corvid.tulip.admin.model;

import org.hibernate.annotations.GenericGenerator;
