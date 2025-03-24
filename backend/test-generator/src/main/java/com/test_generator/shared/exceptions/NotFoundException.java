package com.test_generator.shared.exceptions;

import static com.test_generator.shared.constants.Constants.*;

public class NotFoundException extends RuntimeException {

    public NotFoundException (String entity, Integer id){
        super(
                String.format("%s" + WITH_ID + "%d" + NOT_FOUND
                        , entity, id)
        );
    }

    public NotFoundException (String entity, String name){
        super(
                String.format("%s" + WITH_NAME + "%s" + NOT_FOUND
                        , entity, name)
        );
    }

    public NotFoundException (String entity, String entityName, String relationship, String relationshipName){
        super(
                String.format("%s" + WITH_NAME + "%s" + AND_RELATED_WITH + "%s" + WITH_NAME + "%s" + NOT_FOUND
                        , entity, entityName, relationship, relationshipName)
        );
    }
}
