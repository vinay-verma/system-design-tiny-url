package com.zemoso.systemdesignbootcamp.tinyurlapi.changelogs;

import com.zemoso.systemdesignbootcamp.tinyurlapi.utils.Constants;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "CreateUserCollectionChangelog", order = "1", author = "mongock")
public class CreateUserCollectionChangelog {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        if (!mongoTemplate.getCollectionNames().contains(Constants.COLLECTION_USERS)) {
            mongoTemplate.createCollection(Constants.COLLECTION_USERS);
        }
    }

    @RollbackExecution
    public void rollbackExecution(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection(Constants.COLLECTION_USERS);
    }
}
